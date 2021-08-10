package com.rapidplus.shop.controller;

import android.provider.Settings;
import android.support.annotation.NonNull;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.rapidplus.shop.application.MyApplication;
import com.rapidplus.shop.helper.SharedHelper;
import com.rapidplus.shop.model.Profile;
import com.rapidplus.shop.model.ServerError;
import com.rapidplus.shop.network.ApiInterface;
import com.rapidplus.shop.utils.Constants;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Tamil on 3/16/2018.
 */

public class GetProfile {

    public GetProfile(ApiInterface apiInterface, final ProfileListener profileListener) {
        String device_id = Settings.Secure.getString(MyApplication.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);
        String device_type = "Android";
        String device_token = FirebaseInstanceId.getInstance().getToken();

        HashMap<String, String> params = new HashMap<>();
        params.put("device_id", device_id);
        params.put("device_type", device_type);
        params.put("device_token", device_token);
        Call<Profile> call = apiInterface.getProfile(params);
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(@NonNull Call<Profile> call, @NonNull Response<Profile> response) {
                if (response.isSuccessful()) {
                    SharedHelper.putKey(MyApplication.getInstance(), Constants.PREF.PROFILE_ID, "" + response.body().getId());
                    SharedHelper.putKey(MyApplication.getInstance(), Constants.PREF.CURRENCY, "" + response.body().getCurrency());
                    profileListener.onSuccess(response.body());
                } else try {
                    ServerError serverError = new Gson().fromJson(response.errorBody().charStream(), ServerError.class);
                    profileListener.onFailure(serverError.getError());
                } catch (JsonSyntaxException e) {
                    profileListener.onFailure("");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Profile> call, @NonNull Throwable t) {
                profileListener.onFailure("");
            }
        });
    }
}
