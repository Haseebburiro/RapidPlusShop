package com.rapidplus.shop.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.rapidplus.shop.R;
import com.rapidplus.shop.config.AppConfigure;
import com.rapidplus.shop.controller.GetProfile;
import com.rapidplus.shop.controller.ProfileListener;
import com.rapidplus.shop.helper.ConnectionHelper;
import com.rapidplus.shop.helper.CustomDialog;
import com.rapidplus.shop.helper.GlobalData;
import com.rapidplus.shop.helper.SharedHelper;
import com.rapidplus.shop.model.AuthToken;
import com.rapidplus.shop.model.Profile;
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

import static com.rapidplus.shop.utils.TextUtils.isValidEmail;

public class LoginActivity extends AppCompatActivity implements ProfileListener {

    @BindView(R.id.txt_forgot_password)
    TextView txtForgotPassword;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_password_eye_img)
    ImageView etPasswordEyeImg;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.txt_register)
    TextView txtRegister;
    @BindView(R.id.bottom_lay)
    LinearLayout bottomLay;

    Context context;
    Activity activity;
    ConnectionHelper connectionHelper;
    CustomDialog customDialog;
    boolean isInternetAvailable;
    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    String TAG = "LoginActivity";
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        context = LoginActivity.this;
        activity = LoginActivity.this;
        connectionHelper = new ConnectionHelper(context);
        isInternetAvailable = connectionHelper.isConnectingToInternet();
        customDialog = new CustomDialog(context);

        etPasswordEyeImg.setTag(1);

//        if (BuildConfig.DEBUG){
//            etEmail.setText("sundaram@appoets.com");
//            etPassword.setText("1234567");
//        }

    }

    @OnClick({R.id.et_password_eye_img, R.id.login_btn, R.id.txt_register, R.id.txt_forgot_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_password_eye_img:
                if (etPasswordEyeImg.getTag().equals(1)) {
                    etPassword.setTransformationMethod(null);
                    etPasswordEyeImg.setImageResource(R.drawable.ic_eye_close);
                    etPasswordEyeImg.setTag(0);
                } else {
                    etPassword.setTransformationMethod(new PasswordTransformationMethod());
                    etPasswordEyeImg.setImageResource(R.drawable.ic_eye_open);
                    etPasswordEyeImg.setTag(1);
                }
                break;
            case R.id.login_btn:
                validateLogin();
                break;
            case R.id.txt_register:
                startActivity(new Intent(context, RegisterActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                finish();
                break;
            case R.id.txt_forgot_password:
                startActivity(new Intent(context, ForgotPassword.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
        }
    }

    private void validateLogin() {
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        if (email.isEmpty())
            Utils.displayMessage(activity, getResources().getString(R.string.please_enter_mail_id));
        else if (!isValidEmail(email))
            Utils.displayMessage(activity, getResources().getString(R.string.please_enter_valid_mail_id));
        else if (password.isEmpty())
            Utils.displayMessage(activity, getResources().getString(R.string.please_enter_password));
        else {
            if (isInternetAvailable) {
                HashMap<String, String> map = new HashMap<>();
                map.put("username", email);
                map.put("password", password);
                map.put("grant_type", "password");
                map.put("client_id", AppConfigure.CLIENT_ID);
                map.put("client_secret", AppConfigure.CLIENT_SECRET);
                map.put("guard", "shops");
                login(map);
            } else {
                Utils.displayMessage(activity, getResources().getString(R.string.oops_no_internet));
            }

        }
    }

    private void login(HashMap<String, String> map) {
        customDialog.show();
        Call<AuthToken> call = apiInterface.login(map);
        call.enqueue(new Callback<AuthToken>() {
            @Override
            public void onResponse(@NonNull Call<AuthToken> call, @NonNull Response<AuthToken> response) {
                if (response.isSuccessful()) {
                    SharedHelper.putKey(context, "access_token", response.body().getTokenType() + " " + response.body().getAccessToken());
                    GlobalData.accessToken = SharedHelper.getKey(context, "access_token");
                    new GetProfile(apiInterface, LoginActivity.this);
                } else {
                    try {
                        ServerError serverError = new Gson().fromJson(response.errorBody().charStream(), ServerError.class);
                        if (serverError.getError().contains("invalid")) {
                            Utils.displayMessage(activity, getString(R.string.invalid_credentials));
                        } else {
                            Utils.displayMessage(activity, getString(R.string.something_went_wrong));
                        }

                    } catch (JsonSyntaxException e) {
                        Utils.displayMessage(activity, getString(R.string.something_went_wrong));
                    }

                    customDialog.dismiss();
                }
                //customDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<AuthToken> call, @NonNull Throwable t) {
                customDialog.dismiss();
                Utils.displayMessage(activity, getString(R.string.something_went_wrong));
            }
        });

    }

    @Override
    public void onSuccess(Profile profile) {
        customDialog.dismiss();
        SharedHelper.putKey(context, "logged", "true");
        GlobalData.profile = profile;
        startActivity(new Intent(context, HomeActivity.class));
        finish();
    }

    @Override
    public void onFailure(String error) {
        customDialog.dismiss();
        if (error.isEmpty())
            Utils.displayMessage(activity, getString(R.string.something_went_wrong));
        else Utils.displayMessage(activity, error);
    }
}
