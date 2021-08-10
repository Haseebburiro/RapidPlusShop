package com.rapidplus.shop.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rapidplus.shop.R;
import com.rapidplus.shop.activity.EditRestaurantActivity;
import com.rapidplus.shop.adapter.SettingAdapter;
import com.rapidplus.shop.controller.GetProfile;
import com.rapidplus.shop.controller.ProfileListener;
import com.rapidplus.shop.helper.ConnectionHelper;
import com.rapidplus.shop.helper.CustomDialog;
import com.rapidplus.shop.model.Profile;
import com.rapidplus.shop.model.Setting;
import com.rapidplus.shop.network.ApiClient;
import com.rapidplus.shop.network.ApiInterface;
import com.rapidplus.shop.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SettingFragment extends Fragment implements ProfileListener {

    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.setting_rv)
    RecyclerView settingRv;
    Unbinder unbinder;

    Context context;
    Activity activity;
    List<Setting> settingArrayList;
    SettingAdapter settingAdapter;
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.profile_img)
    ImageView profileImg;

    @BindView(R.id.shop_name)
    TextView shop_name;

    @BindView(R.id.shop_cuisines)
    TextView shop_cuisines;

    @BindView(R.id.address)
    TextView address;

    ConnectionHelper connectionHelper;
    CustomDialog customDialog;

    @BindView(R.id.lnrProfile)
    LinearLayout lnrProfile;

    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = getActivity();
        activity = getActivity();
        initViews();
        return view;
    }

    private void initViews() {
        connectionHelper = new ConnectionHelper(getActivity());
        customDialog = new CustomDialog(getActivity());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        title.setText(R.string.restaurant);
        settingArrayList = new ArrayList<>();
        settingArrayList.add(new Setting(getString(R.string.history), R.drawable.ic_timer_timing_tool));
        settingArrayList.add(new Setting(getString(R.string.edit_restaurant), R.drawable.ic_edit));
        settingArrayList.add(new Setting(getString(R.string.edit_timing), R.drawable.ic_edit_time));
        settingArrayList.add(new Setting(getString(R.string.deliveries), R.drawable.ic_delivery_truck));
        settingArrayList.add(new Setting(getString(R.string.change_language), R.drawable.ic_translate));
        settingArrayList.add(new Setting(getString(R.string.change_password), R.drawable.ic_padlock));
        settingArrayList.add(new Setting(getString(R.string.logout), R.drawable.logout));
        settingArrayList.add(new Setting(getString(R.string.delete_account), R.drawable.trash));

        settingRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        settingRv.setItemAnimator(new DefaultItemAnimator());
        settingRv.setHasFixedSize(true);
        settingAdapter = new SettingAdapter(settingArrayList, context, activity);
        settingRv.setAdapter(settingAdapter);
    }


    @OnClick({R.id.lnrProfile})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.lnrProfile:
                context.startActivity(new Intent(context, EditRestaurantActivity.class));
                break;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (connectionHelper.isConnectingToInternet())
            getProfile();
        else
            Utils.displayMessage(getActivity(), getString(R.string.oops_no_internet));
    }

    private void getProfile() {
        if (connectionHelper.isConnectingToInternet()) {
            customDialog.show();
            new GetProfile(apiInterface, this);
        } else {
            Utils.displayMessage(getActivity(), getResources().getString(R.string.oops_no_internet));
        }
    }


    @Override
    public void onSuccess(Profile profile) {
        customDialog.dismiss();
        if (isAdded()) {
            Glide.with(getActivity()).load(profile.getAvatar())
                    .apply(new RequestOptions().placeholder(R.drawable.ic_place_holder_image).error(R.drawable.ic_place_holder_image).dontAnimate()).into(profileImg);
        }
        shop_name.setText(profile.getName());
        if (profile.getCuisines().size() > 1)
            shop_cuisines.setText("Multi Cuisine");
        else {
            String cuisines = profile.getCuisines().get(0).getName();
            shop_cuisines.setText(cuisines);
        }

        address.setText(profile.getMapsAddress());
    }

    @Override
    public void onFailure(String error) {
        customDialog.dismiss();
        Utils.displayMessage(getActivity(), getString(R.string.something_went_wrong));
    }
}
