package com.rapidplus.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.rapidplus.shop.R;
import com.rapidplus.shop.helper.ConnectionHelper;
import com.rapidplus.shop.helper.CustomDialog;
import com.rapidplus.shop.model.ResetPasswordResponse;
import com.rapidplus.shop.model.ServerError;
import com.rapidplus.shop.network.ApiClient;
import com.rapidplus.shop.network.ApiInterface;
import com.rapidplus.shop.utils.Utils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {

    private static final String TAG = "ResetPasswordActivity";


    @BindView(R.id.change_btn)
    Button btnChange;

    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;

    @BindView(R.id.et_new_password_eye_img)
    ImageView etNewPasswordEyeImg;
    @BindView(R.id.et_confirm_password_eye_img)
    ImageView etConfirmPasswordEyeImg;


    ConnectionHelper connectionHelper;
    CustomDialog customDialog;

    String strUserId, strNewPassword, strConfirmPassword;


    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);

        setUp();
    }


    private void setUp() {
        connectionHelper = new ConnectionHelper(this);
        customDialog = new CustomDialog(this);

        etNewPasswordEyeImg.setTag(1);
        etConfirmPasswordEyeImg.setTag(1);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            strUserId = bundle.getString("id");
        }
    }

    @OnClick({R.id.change_btn, R.id.et_new_password_eye_img, R.id.et_confirm_password_eye_img, R.id.txt_login})
    public void Submit(View view) {
        switch (view.getId()) {
            case R.id.change_btn:
                if (validateInput()) {
                    if (connectionHelper.isConnectingToInternet()) {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("id", strUserId);
                        params.put("password", strNewPassword);
                        params.put("password_confirmation", strConfirmPassword);
                        resetPassword(params);
                    } else {
                        Utils.displayMessage(this, getString(R.string.oops_no_internet));
                    }

                }
                break;

            case R.id.et_new_password_eye_img:
                if (etNewPasswordEyeImg.getTag().equals(1)) {
                    etNewPassword.setTransformationMethod(null);
                    etNewPasswordEyeImg.setImageResource(R.drawable.ic_eye_close);
                    etNewPasswordEyeImg.setTag(0);
                } else {
                    etNewPassword.setTransformationMethod(new PasswordTransformationMethod());
                    etNewPasswordEyeImg.setImageResource(R.drawable.ic_eye_open);
                    etNewPasswordEyeImg.setTag(1);
                }
                break;
            case R.id.et_confirm_password_eye_img:
                if (etConfirmPasswordEyeImg.getTag().equals(1)) {
                    etConfirmPassword.setTransformationMethod(null);
                    etConfirmPasswordEyeImg.setImageResource(R.drawable.ic_eye_close);
                    etConfirmPasswordEyeImg.setTag(0);
                } else {
                    etConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());
                    etConfirmPasswordEyeImg.setImageResource(R.drawable.ic_eye_open);
                    etConfirmPasswordEyeImg.setTag(1);
                }
                break;

            case R.id.txt_login:
                redirectToLogin();
                break;
        }
    }

    private void resetPassword(HashMap<String, String> map) {
        customDialog.show();
        Call<ResetPasswordResponse> call = apiInterface.resetPassword(map);
        call.enqueue(new Callback<ResetPasswordResponse>() {
            @Override
            public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {
                customDialog.dismiss();
                if (response.isSuccessful()) {
                    Utils.displayMessage(ResetPasswordActivity.this, response.body().getMessage());
                    new Handler(getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            redirectToLogin();
                        }
                    }, 1000);
                } else {
                    try {
                        ServerError serverError = new Gson().fromJson(response.errorBody().charStream(), ServerError.class);
                        Utils.displayMessage(ResetPasswordActivity.this, serverError.getError());
                    } catch (JsonSyntaxException e) {
                        Utils.displayMessage(ResetPasswordActivity.this, getString(R.string.something_went_wrong));
                    }

                }
            }

            @Override
            public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {
                customDialog.dismiss();
                Utils.displayMessage(ResetPasswordActivity.this, getString(R.string.something_went_wrong));
            }
        });
    }

    private void redirectToLogin() {
        startActivity(new Intent(this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    private boolean validateInput() {
        strNewPassword = etNewPassword.getText().toString().trim();
        strConfirmPassword = etConfirmPassword.getText().toString().trim();

        if (strNewPassword.isEmpty()) {
            Utils.displayMessage(this, getString(R.string.please_new_password));
            return false;
        } else if (strConfirmPassword.isEmpty()) {
            Utils.displayMessage(this, getString(R.string.please_enter_confirm_password));
            return false;
        }
        return true;
    }


}
