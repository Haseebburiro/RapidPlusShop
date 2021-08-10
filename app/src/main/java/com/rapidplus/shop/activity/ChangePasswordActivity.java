package com.rapidplus.shop.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.rapidplus.shop.R;
import com.rapidplus.shop.helper.ConnectionHelper;
import com.rapidplus.shop.helper.CustomDialog;
import com.rapidplus.shop.model.ChangePassword;
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

public class ChangePasswordActivity extends AppCompatActivity {


    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_current_password)
    EditText etCurrentPassword;
    @BindView(R.id.et_current_password_eye_img)
    ImageView etCurrentPasswordEyeImg;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_password_eye_img)
    ImageView etPasswordEyeImg;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;
    @BindView(R.id.et_confirm_password_eye_img)
    ImageView etConfirmPasswordEyeImg;
    @BindView(R.id.save_btn)
    Button saveBtn;


    Context context;
    Activity activity;
    ConnectionHelper connectionHelper;
    CustomDialog customDialog;
    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    String TAG = "ChangePasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);

        context = ChangePasswordActivity.this;
        activity = ChangePasswordActivity.this;
        connectionHelper = new ConnectionHelper(context);
        customDialog = new CustomDialog(context);

        backImg.setVisibility(View.VISIBLE);
        title.setText(getString(R.string.change_password));

        etPasswordEyeImg.setTag(1);
        etCurrentPasswordEyeImg.setTag(1);
        etConfirmPasswordEyeImg.setTag(1);


    }

    @OnClick({R.id.back_img, R.id.et_current_password_eye_img, R.id.et_password_eye_img, R.id.et_confirm_password_eye_img, R.id.save_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                onBackPressed();
                break;
            case R.id.et_current_password_eye_img:
                if (etCurrentPasswordEyeImg.getTag().equals(1)) {
                    etCurrentPassword.setTransformationMethod(null);
                    etCurrentPasswordEyeImg.setImageResource(R.drawable.ic_eye_close);
                    etCurrentPasswordEyeImg.setTag(0);
                } else {
                    etCurrentPassword.setTransformationMethod(new PasswordTransformationMethod());
                    etCurrentPasswordEyeImg.setImageResource(R.drawable.ic_eye_open);
                    etCurrentPasswordEyeImg.setTag(1);
                }

                break;
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
            case R.id.save_btn:
                if (connectionHelper.isConnectingToInternet())
                    changePassword();
                else
                    Utils.displayMessage(activity, getResources().getString(R.string.oops_no_internet));
                break;
        }
    }

    private void changePassword() {
        String strCurrentPassword = etCurrentPassword.getText().toString().trim();
        String strPassword = etPassword.getText().toString().trim();
        String strConfirmPassword = etConfirmPassword.getText().toString().trim();

        if (strCurrentPassword.isEmpty()) {
            Utils.displayMessage(ChangePasswordActivity.this, getResources().getString(R.string.please_enter_password));
        } else if (strCurrentPassword.length() < 6) {
            Utils.displayMessage(ChangePasswordActivity.this, getResources().getString(R.string.please_enter_minimum_length_password));
        } else if (strPassword.isEmpty()) {
            Utils.displayMessage(ChangePasswordActivity.this, getResources().getString(R.string.please_enter_new_password));
        } else if (strPassword.length() < 6 || strCurrentPassword.length() < 6) {
            Utils.displayMessage(ChangePasswordActivity.this, getResources().getString(R.string.please_enter_minimum_length_password));
        } else if (strConfirmPassword.isEmpty()) {
            Utils.displayMessage(ChangePasswordActivity.this, getResources().getString(R.string.please_enter_new_password));
        } else if (!strConfirmPassword.equals(strPassword)) {
            Utils.displayMessage(ChangePasswordActivity.this, getResources().getString(R.string.password_and_confirm_password_doesnot_match));
        } else {
            HashMap<String, String> map = new HashMap<>();
            map.put("password_old", strCurrentPassword);
            map.put("password", strPassword);
            map.put("password_confirmation", strConfirmPassword);
            callChangePassword(map);
        }
    }

    private void callChangePassword(HashMap<String, String> params) {
        customDialog.show();
        Call<ChangePassword> call = apiInterface.changePassword(params);
        call.enqueue(new Callback<ChangePassword>() {
            @Override
            public void onResponse(Call<ChangePassword> call, Response<ChangePassword> response) {
                customDialog.dismiss();
                if (response.isSuccessful()) {
                    Utils.displayMessage(ChangePasswordActivity.this, getString(R.string.password_updated_successfully));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onBackPressed();
                        }
                    }, 1000);
                } else {
                    try {
                        ServerError serverError = new Gson().fromJson(response.errorBody().charStream(), ServerError.class);
                        if (serverError.getError() != null && serverError.getError().contains("incorrect_password")) {
                            Utils.displayMessage(activity, "Incorrect Password");
                        } else {
                            Utils.displayMessage(activity, serverError.getError());
                        }
                    } catch (JsonSyntaxException e) {
                        Utils.displayMessage(activity, getString(R.string.something_went_wrong));
                    }
                }
            }

            @Override
            public void onFailure(Call<ChangePassword> call, Throwable t) {
                customDialog.dismiss();
                Utils.displayMessage(activity, getString(R.string.something_went_wrong));
            }
        });
    }


}
