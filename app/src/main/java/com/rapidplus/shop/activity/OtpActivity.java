package com.rapidplus.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.rapidplus.shop.R;
import com.rapidplus.shop.helper.ConnectionHelper;
import com.rapidplus.shop.helper.CustomDialog;
import com.rapidplus.shop.model.ForgotPasswordResponse;
import com.rapidplus.shop.model.ServerError;
import com.rapidplus.shop.network.ApiClient;
import com.rapidplus.shop.network.ApiInterface;
import com.rapidplus.shop.utils.Utils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.philio.pinentry.PinEntryView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {
    private static final String TAG = "OtpActivity";

    @BindView(R.id.otp_value)
    PinEntryView otpValue;

    @BindView(R.id.otp_continue)
    Button btnOtpContinue;

    @BindView(R.id.tvEmail)
    TextView tvEmail;

    String strEmail, strOtpValue;

    ConnectionHelper connectionHelper;
    CustomDialog customDialog;

    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        ButterKnife.bind(this);

        setUp();
    }

    private void setUp() {
        connectionHelper = new ConnectionHelper(this);
        customDialog = new CustomDialog(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            strEmail = bundle.getString("email");
            tvEmail.setText(strEmail);
        }
    }


    @OnClick({R.id.otp_continue})
    public void Submit(View view) {
        switch (view.getId()) {
            case R.id.otp_continue:
                if (validateInput()) {
                    if (connectionHelper.isConnectingToInternet()) {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("email", strEmail);
                        params.put("otp", strOtpValue);
                        verifyOTP(params);
                    } else {
                        Utils.displayMessage(this, getString(R.string.oops_no_internet));
                    }

                }

                break;
        }
    }

    private void verifyOTP(HashMap<String, String> map) {
        customDialog.show();
        Call<ForgotPasswordResponse> call = apiInterface.verifyOTP(map);
        call.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                customDialog.dismiss();
                if (response.isSuccessful()) {
                    String user_id = response.body().getUser().getId() + "";
                    Utils.displayMessage(OtpActivity.this, response.body().getMessage());
                    new Handler(getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            redirectToResetPassword(user_id);
                        }
                    }, 1000);
                } else {
                    try {
                        ServerError serverError = new Gson().fromJson(response.errorBody().charStream(), ServerError.class);
                        Utils.displayMessage(OtpActivity.this, serverError.getError());
                    } catch (JsonSyntaxException e) {
                        Utils.displayMessage(OtpActivity.this, getString(R.string.something_went_wrong));
                    }

                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                customDialog.dismiss();
                Utils.displayMessage(OtpActivity.this, getString(R.string.something_went_wrong));
            }
        });
    }

    private void redirectToResetPassword(String user_id) {
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        intent.putExtra("id", user_id);
        startActivity(intent);
    }


    private boolean validateInput() {
        strOtpValue = otpValue.getText().toString().trim();
        if (strOtpValue.isEmpty()) {
            Utils.displayMessage(this, getString(R.string.please_enter_otp));
            return false;
        }
        return true;
    }
}
