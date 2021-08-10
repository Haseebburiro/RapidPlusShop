package com.rapidplus.shop.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
import com.rapidplus.shop.imagecompressor.Compressor;
import com.rapidplus.shop.model.AuthToken;
import com.rapidplus.shop.model.Profile;
import com.rapidplus.shop.model.ServerError;
import com.rapidplus.shop.model.Timing;
import com.rapidplus.shop.network.ApiClient;
import com.rapidplus.shop.network.ApiInterface;
import com.rapidplus.shop.utils.Constants;
import com.rapidplus.shop.utils.Utils;

import org.json.JSONObject;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantTimingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, ProfileListener {

    @BindView(R.id.txt_open_time)
    TextView txtOpenTime;
    @BindView(R.id.open_time_lay)
    LinearLayout openTimeLay;
    @BindView(R.id.txt_close_time)
    TextView txtCloseTime;
    @BindView(R.id.close_time_lay)
    LinearLayout closeTimeLay;
    @BindView(R.id.every_day_time_lay)
    LinearLayout everyDayTimeLay;
    @BindView(R.id.mon_txt_open_time)
    TextView monTxtOpenTime;
    @BindView(R.id.mon_txt_close_time)
    TextView monTxtCloseTime;
    @BindView(R.id.mon_time_lay)
    LinearLayout monTimeLay;
    @BindView(R.id.tue_txt_open_time)
    TextView tueTxtOpenTime;
    @BindView(R.id.tue_txt_close_time)
    TextView tueTxtCloseTime;
    @BindView(R.id.tue_time_lay)
    LinearLayout tueTimeLay;
    @BindView(R.id.wed_txt_open_time)
    TextView wedTxtOpenTime;
    @BindView(R.id.wed_txt_close_time)
    TextView wedTxtCloseTime;
    @BindView(R.id.wed_lay)
    LinearLayout wedLay;
    @BindView(R.id.thur_txt_open_time)
    TextView thurTxtOpenTime;
    @BindView(R.id.thur_txt_close_time)
    TextView thurTxtCloseTime;
    @BindView(R.id.thur_lay)
    LinearLayout thurLay;
    @BindView(R.id.frid_txt_open_time)
    TextView fridTxtOpenTime;
    @BindView(R.id.frid_txt_close_time)
    TextView fridTxtCloseTime;
    @BindView(R.id.frid_lay)
    LinearLayout fridLay;
    @BindView(R.id.sat_txt_open_time)
    TextView satTxtOpenTime;
    @BindView(R.id.sat_txt_close_time)
    TextView satTxtCloseTime;
    @BindView(R.id.sat_lay)
    LinearLayout satLay;

    @BindView(R.id.sun_txt_open_time)
    TextView sunTxtOpenTime;
    @BindView(R.id.sun_txt_close_time)
    TextView sunTxtCloseTime;
    @BindView(R.id.sun_lay)
    LinearLayout sunLay;
    @BindView(R.id.every_day_lay)
    LinearLayout everyDayLay;
    @BindView(R.id.every_day_switch)
    Switch everyDaySwitch;
    @BindView(R.id.monday_check)
    CheckBox mondayCheck;
    @BindView(R.id.tueday_check)
    CheckBox tuedayCheck;
    @BindView(R.id.thursday_check)
    CheckBox thursdayCheck;
    @BindView(R.id.friday_check)
    CheckBox fridayCheck;
    @BindView(R.id.saturday_check)
    CheckBox saturdayCheck;
    @BindView(R.id.sunday_check)
    CheckBox sundayCheck;
    @BindView(R.id.wednesday_check)
    CheckBox wednesdayCheck;
    @BindView(R.id.confirm_btn)
    Button confirmBtn;
    @BindView(R.id.LLBottomLay)
    LinearLayout LLBottomLay;

    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.llToolbar)
    LinearLayout llToolbar;
    @BindView(R.id.llLogoSection)
    LinearLayout llLogoSection;

    CustomDialog customDialog;
    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

    String strFrom = "Register";
    ConnectionHelper connectionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_timing);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("from")) {
            strFrom = bundle.getString("from");
        }
        customDialog = new CustomDialog(this);
        //Handle via edit screen
        everyDaySwitch.setChecked(true);
        everyDayTimeLay.setVisibility(View.VISIBLE);
        everyDayLay.setVisibility(View.GONE);
        everyDaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    everyDayTimeLay.setVisibility(View.VISIBLE);
                    everyDayLay.setVisibility(View.GONE);
                } else {
                    everyDayLay.setVisibility(View.VISIBLE);
                    everyDayTimeLay.setVisibility(View.GONE);
                }
            }

        });

        connectionHelper = new ConnectionHelper(this);

        mondayCheck.setOnCheckedChangeListener(this);
        tuedayCheck.setOnCheckedChangeListener(this);
        wednesdayCheck.setOnCheckedChangeListener(this);
        thursdayCheck.setOnCheckedChangeListener(this);
        fridayCheck.setOnCheckedChangeListener(this);
        saturdayCheck.setOnCheckedChangeListener(this);
        sundayCheck.setOnCheckedChangeListener(this);

        //If this screen called from setting fragment we are reusing this screen to update timing in profile api
        if (!strFrom.equalsIgnoreCase("Register")) {
            //dont't make it gone since it will hide the entire layout in screen
            LLBottomLay.setVisibility(View.INVISIBLE);
            confirmBtn.setText(R.string.action_save);
            llToolbar.setVisibility(View.VISIBLE);
            llLogoSection.setVisibility(View.GONE);

            title.setText(getString(R.string.edit_timing));
            backImg.setVisibility(View.VISIBLE);

            callProfile();
        } else {
            llToolbar.setVisibility(View.GONE);
            llLogoSection.setVisibility(View.VISIBLE);
            LLBottomLay.setVisibility(View.VISIBLE);
            confirmBtn.setText(R.string.register);
        }


    }


    private void callProfile() {
        if (connectionHelper.isConnectingToInternet()) {
            customDialog.show();
            new GetProfile(apiInterface, this);
        } else {
            Utils.displayMessage(this, getResources().getString(R.string.oops_no_internet));
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.monday_check:
                monTimeLay.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                break;
            case R.id.tueday_check:
                tueTimeLay.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                break;
            case R.id.wednesday_check:
                wedLay.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                break;
            case R.id.thursday_check:
                thurLay.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                break;
            case R.id.friday_check:
                fridLay.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                break;
            case R.id.saturday_check:
                satLay.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                break;
            case R.id.sunday_check:
                sunLay.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                break;
        }
    }

    public void signUp() {

        if (GlobalData.registerMap.isEmpty()) {
            Toast.makeText(this, getString(R.string.please_enter_all_fields), Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, RequestBody> map = GlobalData.registerMap;

        if (everyDaySwitch.isChecked()) {
            if (txtOpenTime.getText().toString().isEmpty()) {
                Utils.displayMessage(this, getString(R.string.invalid_open_time));
                return;
            }
            if (txtCloseTime.getText().toString().isEmpty()) {
                Utils.displayMessage(this, getString(R.string.invalid_close_time));
                return;
            }
            map.put("day[]", RequestBody.create(MediaType.parse("text/plain"), "ALL"));
            map.put("hours_opening[ALL]", RequestBody.create(MediaType.parse("text/plain"), txtOpenTime.getText().toString()));
            map.put("hours_closing[ALL]", RequestBody.create(MediaType.parse("text/plain"), txtCloseTime.getText().toString()));
        } else {
            if (mondayCheck.isChecked()) {
                map.put("day[0]", RequestBody.create(MediaType.parse("text/plain"), "MON"));
                map.put("hours_opening[MON]", RequestBody.create(MediaType.parse("text/plain"), monTxtOpenTime.getText().toString()));
                map.put("hours_closing[MON]", RequestBody.create(MediaType.parse("text/plain"), monTxtCloseTime.getText().toString()));
            }
            if (tuedayCheck.isChecked()) {
                map.put("day[1]", RequestBody.create(MediaType.parse("text/plain"), "TUE"));
                map.put("hours_opening[TUE]", RequestBody.create(MediaType.parse("text/plain"), tueTxtOpenTime.getText().toString()));
                map.put("hours_closing[TUE]", RequestBody.create(MediaType.parse("text/plain"), tueTxtCloseTime.getText().toString()));
            }
            if (wednesdayCheck.isChecked()) {
                map.put("day[2]", RequestBody.create(MediaType.parse("text/plain"), "WED"));
                map.put("hours_opening[WED]", RequestBody.create(MediaType.parse("text/plain"), wedTxtOpenTime.getText().toString()));
                map.put("hours_closing[WED]", RequestBody.create(MediaType.parse("text/plain"), wedTxtCloseTime.getText().toString()));
            }
            if (thursdayCheck.isChecked()) {
                map.put("day[3]", RequestBody.create(MediaType.parse("text/plain"), "THU"));
                map.put("hours_opening[THU]", RequestBody.create(MediaType.parse("text/plain"), thurTxtOpenTime.getText().toString()));
                map.put("hours_closing[THU]", RequestBody.create(MediaType.parse("text/plain"), thurTxtCloseTime.getText().toString()));
            }
            if (fridayCheck.isChecked()) {
                map.put("day[4]", RequestBody.create(MediaType.parse("text/plain"), "FRI"));
                map.put("hours_opening[FRI]", RequestBody.create(MediaType.parse("text/plain"), fridTxtOpenTime.getText().toString()));
                map.put("hours_closing[FRI]", RequestBody.create(MediaType.parse("text/plain"), fridTxtCloseTime.getText().toString()));
            }

            if (saturdayCheck.isChecked()) {
                map.put("day[5]", RequestBody.create(MediaType.parse("text/plain"), "SAT"));
                map.put("hours_opening[SAT]", RequestBody.create(MediaType.parse("text/plain"), satTxtOpenTime.getText().toString()));
                map.put("hours_closing[SAT]", RequestBody.create(MediaType.parse("text/plain"), satTxtCloseTime.getText().toString()));
            }
            if (sundayCheck.isChecked()) {
                map.put("day[6]", RequestBody.create(MediaType.parse("text/plain"), "SUN"));
                map.put("hours_opening[SUN]", RequestBody.create(MediaType.parse("text/plain"), sunTxtOpenTime.getText().toString()));
                map.put("hours_closing[SUN]", RequestBody.create(MediaType.parse("text/plain"), sunTxtCloseTime.getText().toString()));
            }
        }

        customDialog.show();
        MultipartBody.Part filePart1 = null;
        if (GlobalData.REGISTER_AVATAR != null) {
            try {
                GlobalData.REGISTER_AVATAR = new Compressor(this).compressToFile(GlobalData.REGISTER_AVATAR);
            } catch (IOException e) {
                e.printStackTrace();
            }
            filePart1 = MultipartBody.Part.createFormData("avatar", GlobalData.REGISTER_AVATAR.getName(),
                    RequestBody.create(MediaType.parse("image/*"), GlobalData.REGISTER_AVATAR));
        }

        MultipartBody.Part filePart2 = null;
        if (GlobalData.REGISTER_SHOP_BANNER != null) {
            try {
                GlobalData.REGISTER_SHOP_BANNER = new Compressor(this).compressToFile(GlobalData.REGISTER_SHOP_BANNER);
            } catch (IOException e) {
                e.printStackTrace();
            }
            filePart2 = MultipartBody.Part.createFormData("default_banner", GlobalData.REGISTER_SHOP_BANNER.getName(),
                    RequestBody.create(MediaType.parse("image/*"), GlobalData.REGISTER_SHOP_BANNER));
        }

        Call<Profile> call = apiInterface.signUp(map, filePart1, filePart2);
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(@NonNull Call<Profile> call, @NonNull Response<Profile> response) {
                if (response.isSuccessful()) callLogin();
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        if (jObjError.optString("email") != null)
                            Toast.makeText(getApplicationContext(), jObjError.optString("email"), Toast.LENGTH_LONG).show();
                        else if (jObjError.optString("error") != null)
                            Toast.makeText(getApplicationContext(), jObjError.optString("error"), Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getApplicationContext(), jObjError.optString("phone"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    customDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Profile> call, @NonNull Throwable t) {
                customDialog.dismiss();
                Utils.displayMessage(RestaurantTimingActivity.this, getString(R.string.something_went_wrong));
            }
        });
    }


    private void callLogin() {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", GlobalData.email);
        map.put("password", GlobalData.password);
        map.put("grant_type", "password");
        map.put("client_id", AppConfigure.CLIENT_ID);
        map.put("client_secret", AppConfigure.CLIENT_SECRET);
        map.put("guard", "shops");
        login(map);
    }

    private void login(HashMap<String, String> map) {
        // customDialog.show();
        Call<AuthToken> call = apiInterface.login(map);
        call.enqueue(new Callback<AuthToken>() {
            @Override
            public void onResponse(@NonNull Call<AuthToken> call, @NonNull Response<AuthToken> response) {
                if (response.isSuccessful()) {
                    SharedHelper.putKey(RestaurantTimingActivity.this, "access_token", response.body().getTokenType() + " " + response.body().getAccessToken());
                    GlobalData.accessToken = SharedHelper.getKey(RestaurantTimingActivity.this, "access_token");
                    new GetProfile(apiInterface, RestaurantTimingActivity.this);
                } else {
                    try {
                        ServerError serverError = new Gson().fromJson(response.errorBody().charStream(), ServerError.class);
                        Utils.displayMessage(RestaurantTimingActivity.this, serverError.getError());
                    } catch (JsonSyntaxException e) {
                        Utils.displayMessage(RestaurantTimingActivity.this, getString(R.string.something_went_wrong));
                    }

                    customDialog.dismiss();
                }
                //customDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<AuthToken> call, @NonNull Throwable t) {
                customDialog.dismiss();
                Utils.displayMessage(RestaurantTimingActivity.this, getString(R.string.something_went_wrong));
            }
        });

    }


    @OnClick({R.id.confirm_btn})
    public void onViewClicked() {
        if (connectionHelper.isConnectingToInternet()) {
            if (!strFrom.equalsIgnoreCase("Register")) {
                updateProfile();
            } else {
                signUp();
            }
        } else {
            Utils.displayMessage(RestaurantTimingActivity.this, getString(R.string.oops_no_internet));
        }
    }

    private void updateProfile() {
        HashMap<String, RequestBody> map = new HashMap<>();

        if (everyDaySwitch.isChecked()) {
            if (txtOpenTime.getText().toString().isEmpty()) {
                Utils.displayMessage(this, getString(R.string.invalid_open_time));
                return;
            }
            if (txtCloseTime.getText().toString().isEmpty()) {
                Utils.displayMessage(this, getString(R.string.invalid_close_time));
                return;
            }
            map.put("day[]", RequestBody.create(MediaType.parse("text/plain"), "ALL"));
            map.put("hours_opening[ALL]", RequestBody.create(MediaType.parse("text/plain"), txtOpenTime.getText().toString()));
            map.put("hours_closing[ALL]", RequestBody.create(MediaType.parse("text/plain"), txtCloseTime.getText().toString()));
        } else {
            if (mondayCheck.isChecked()) {
                map.put("day[0]", RequestBody.create(MediaType.parse("text/plain"), "MON"));
                map.put("hours_opening[MON]", RequestBody.create(MediaType.parse("text/plain"), monTxtOpenTime.getText().toString()));
                map.put("hours_closing[MON]", RequestBody.create(MediaType.parse("text/plain"), monTxtCloseTime.getText().toString()));
            }
            if (tuedayCheck.isChecked()) {
                map.put("day[1]", RequestBody.create(MediaType.parse("text/plain"), "TUE"));
                map.put("hours_opening[TUE]", RequestBody.create(MediaType.parse("text/plain"), tueTxtOpenTime.getText().toString()));
                map.put("hours_closing[TUE]", RequestBody.create(MediaType.parse("text/plain"), tueTxtCloseTime.getText().toString()));
            }
            if (wednesdayCheck.isChecked()) {
                map.put("day[2]", RequestBody.create(MediaType.parse("text/plain"), "WED"));
                map.put("hours_opening[WED]", RequestBody.create(MediaType.parse("text/plain"), wedTxtOpenTime.getText().toString()));
                map.put("hours_closing[WED]", RequestBody.create(MediaType.parse("text/plain"), wedTxtCloseTime.getText().toString()));
            }
            if (thursdayCheck.isChecked()) {
                map.put("day[3]", RequestBody.create(MediaType.parse("text/plain"), "THU"));
                map.put("hours_opening[THU]", RequestBody.create(MediaType.parse("text/plain"), thurTxtOpenTime.getText().toString()));
                map.put("hours_closing[THU]", RequestBody.create(MediaType.parse("text/plain"), thurTxtCloseTime.getText().toString()));
            }
            if (fridayCheck.isChecked()) {
                map.put("day[4]", RequestBody.create(MediaType.parse("text/plain"), "FRI"));
                map.put("hours_opening[FRI]", RequestBody.create(MediaType.parse("text/plain"), fridTxtOpenTime.getText().toString()));
                map.put("hours_closing[FRI]", RequestBody.create(MediaType.parse("text/plain"), fridTxtCloseTime.getText().toString()));
            }
            if (saturdayCheck.isChecked()) {
                map.put("day[5]", RequestBody.create(MediaType.parse("text/plain"), "SAT"));
                map.put("hours_opening[SAT]", RequestBody.create(MediaType.parse("text/plain"), satTxtOpenTime.getText().toString()));
                map.put("hours_closing[SAT]", RequestBody.create(MediaType.parse("text/plain"), satTxtCloseTime.getText().toString()));
            }
            if (sundayCheck.isChecked()) {
                map.put("day[6]", RequestBody.create(MediaType.parse("text/plain"), "SUN"));
                map.put("hours_opening[SUN]", RequestBody.create(MediaType.parse("text/plain"), sunTxtOpenTime.getText().toString()));
                map.put("hours_closing[SUN]", RequestBody.create(MediaType.parse("text/plain"), sunTxtCloseTime.getText().toString()));
            }
        }

        customDialog.show();
        updateProfile(map);

    }

    private void updateProfile(HashMap<String, RequestBody> map) {
        customDialog.show();
        int id = Integer.parseInt(SharedHelper.getKey(RestaurantTimingActivity.this, Constants.PREF.PROFILE_ID));
        Call<Profile> call = null;
        call = apiInterface.updateProfile(id, map);
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                customDialog.dismiss();
                if (response.body() != null) {
                    Utils.displayMessage(RestaurantTimingActivity.this, getString(R.string.restaurant_timing_updated_successfully));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onBackPressed();
                        }
                    }, 1000);

                } else {
                    Utils.displayMessage(RestaurantTimingActivity.this, getString(R.string.something_went_wrong));
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                customDialog.dismiss();
                Utils.displayMessage(RestaurantTimingActivity.this, getString(R.string.something_went_wrong));
            }
        });
    }


    private void timePicker(final TextView view) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                view.setText(MessageFormat.format("{0}:{1}", String.format("%02d", selectedHour), String.format("%02d", selectedMinute)));
            }
        }, hour, minute, true);
        mTimePicker.show();
    }

    @OnClick({R.id.txt_open_time, R.id.txt_close_time, R.id.mon_txt_open_time, R.id.mon_txt_close_time,
            R.id.tue_txt_open_time, R.id.tue_txt_close_time, R.id.wed_txt_open_time, R.id.wed_txt_close_time,
            R.id.thur_txt_open_time, R.id.thur_txt_close_time, R.id.frid_txt_open_time, R.id.frid_txt_close_time,
            R.id.sat_txt_open_time, R.id.sat_txt_close_time, R.id.sun_txt_open_time, R.id.sun_txt_close_time, R.id.back_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_open_time:
                timePicker(txtOpenTime);
                break;
            case R.id.txt_close_time:
                timePicker(txtCloseTime);
                break;
            case R.id.mon_txt_open_time:
                timePicker(monTxtOpenTime);
                break;
            case R.id.mon_txt_close_time:
                timePicker(monTxtCloseTime);
                break;
            case R.id.tue_txt_open_time:
                timePicker(tueTxtOpenTime);
                break;
            case R.id.tue_txt_close_time:
                timePicker(tueTxtCloseTime);
                break;
            case R.id.wed_txt_open_time:
                timePicker(wedTxtOpenTime);
                break;
            case R.id.wed_txt_close_time:
                timePicker(wedTxtCloseTime);
                break;
            case R.id.thur_txt_open_time:
                timePicker(thurTxtOpenTime);
                break;
            case R.id.thur_txt_close_time:
                timePicker(thurTxtCloseTime);
                break;
            case R.id.frid_txt_open_time:
                timePicker(fridTxtOpenTime);
                break;
            case R.id.frid_txt_close_time:
                timePicker(fridTxtCloseTime);
                break;
            case R.id.sat_txt_open_time:
                timePicker(satTxtOpenTime);
                break;
            case R.id.sat_txt_close_time:
                timePicker(satTxtCloseTime);
                break;
            case R.id.sun_txt_open_time:
                timePicker(sunTxtOpenTime);
                break;
            case R.id.sun_txt_close_time:
                timePicker(sunTxtCloseTime);
                break;
            case R.id.back_img:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onSuccess(Profile profile) {
        customDialog.dismiss();
        if (strFrom.equalsIgnoreCase("Register")) {
            SharedHelper.putKey(RestaurantTimingActivity.this, "logged", "true");
            GlobalData.profile = profile;
            startActivity(new Intent(RestaurantTimingActivity.this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        } else {
            List<Timing> timingList = profile.getTimings();
            updateUI(timingList);
        }

    }

    private void updateUI(List<Timing> timingList) {
        if (timingList.size() == 1 && timingList.get(0).getDay().equalsIgnoreCase("ALL")) {
            everyDaySwitch.setChecked(true);
            txtOpenTime.setText(timingList.get(0).getStartTime());
            txtCloseTime.setText(timingList.get(0).getEndTime());
        } else {
            everyDaySwitch.setChecked(false);
            for (int i = 0; i < timingList.size(); i++) {
                Timing timing = timingList.get(i);
                switch (timingList.get(i).getDay()) {
                    case "MON":
                        mondayCheck.setChecked(true);
                        monTxtOpenTime.setText(timing.getStartTime());
                        monTxtCloseTime.setText(timing.getEndTime());
                        break;

                    case "TUE":
                        tuedayCheck.setChecked(true);
                        tueTxtOpenTime.setText(timing.getStartTime());
                        tueTxtCloseTime.setText(timing.getEndTime());
                        break;

                    case "WED":
                        wednesdayCheck.setChecked(true);
                        wedTxtOpenTime.setText(timing.getStartTime());
                        wedTxtCloseTime.setText(timing.getEndTime());
                        break;

                    case "THU":
                        thursdayCheck.setChecked(true);
                        thurTxtOpenTime.setText(timing.getStartTime());
                        thurTxtCloseTime.setText(timing.getEndTime());
                        break;

                    case "FRI":
                        fridayCheck.setChecked(true);
                        fridTxtOpenTime.setText(timing.getStartTime());
                        fridTxtCloseTime.setText(timing.getEndTime());
                        break;

                    case "SAT":
                        saturdayCheck.setChecked(true);
                        satTxtOpenTime.setText(timing.getStartTime());
                        satTxtCloseTime.setText(timing.getEndTime());
                        break;

                    case "SUN":
                        sundayCheck.setChecked(true);
                        sunTxtOpenTime.setText(timing.getStartTime());
                        sunTxtCloseTime.setText(timing.getEndTime());
                        break;
                }
            }
        }
    }

    @Override
    public void onFailure(String error) {
        customDialog.dismiss();
        if (error.isEmpty())
            Utils.displayMessage(RestaurantTimingActivity.this, getString(R.string.something_went_wrong));
        else Utils.displayMessage(RestaurantTimingActivity.this, error);
    }
}
