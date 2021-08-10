package com.rapidplus.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.rapidplus.shop.R;
import com.rapidplus.shop.adapter.ProductAddOnsAdapter;
import com.rapidplus.shop.helper.ConnectionHelper;
import com.rapidplus.shop.helper.CustomDialog;
import com.rapidplus.shop.model.Addon;
import com.rapidplus.shop.model.ServerError;
import com.rapidplus.shop.network.ApiClient;
import com.rapidplus.shop.network.ApiInterface;
import com.rapidplus.shop.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAddOnListActivity extends AppCompatActivity {

    @BindView(R.id.back_img)
    ImageView imgBack;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rvAddOns)
    RecyclerView rvAddOns;


    ConnectionHelper connectionHelper;
    CustomDialog customDialog;
    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    ArrayList<Addon> listAddOns = new ArrayList<>();
    ArrayList<Addon> listSelectedAddOns = new ArrayList<>();
    ArrayList<Addon> listReceivedAddOns = new ArrayList<>();

    ProductAddOnsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add_on_list);
        ButterKnife.bind(this);

        setUp();
    }

    private void setUp() {
        connectionHelper = new ConnectionHelper(this);
        customDialog = new CustomDialog(this);

        title.setText(R.string.select_addons);
        imgBack.setVisibility(View.VISIBLE);

        rvAddOns.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvAddOns.setHasFixedSize(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            listReceivedAddOns = bundle.getParcelableArrayList("addon");
        }
    }

    @OnClick({R.id.btnSave, R.id.back_img})
    public void onSave(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                if (adapter != null) {
                    if (adapter.verifyPrice()) {
                        listSelectedAddOns = adapter.getSelectAddOnList();
                        if (listSelectedAddOns.size() > 0) {
                            showSuccessAndGoBack();
                        } else {
                            goBackToScreen();
                        }
                    }

                } else {
                    setResult(RESULT_OK);
                    finish();
                }

                break;

            case R.id.back_img:
                onBackPressed();
                break;
        }
    }

    private void showSuccessAndGoBack() {
        Utils.displayMessage(ProductAddOnListActivity.this, getResources().getString(R.string.addons_updated_successfully));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goBackToScreen();
            }
        }, 1000);
    }

    private void goBackToScreen() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("addon", listSelectedAddOns);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (connectionHelper.isConnectingToInternet()) {
            getAddOnList();
        } else {
            Utils.displayMessage(this, getString(R.string.oops_no_internet));
        }
    }

    private void getAddOnList() {
        customDialog.show();
        Call<List<Addon>> call = apiInterface.getAddons();
        call.enqueue(new Callback<List<Addon>>() {
            @Override
            public void onResponse(Call<List<Addon>> call, Response<List<Addon>> response) {
                customDialog.dismiss();
                if (response.isSuccessful()) {
                    listAddOns.clear();
                    listAddOns.addAll(response.body());
                    if (listReceivedAddOns.size() > 0) {
                        prepareList();
                    } else {
                        setUpAdapter(listAddOns);
                    }
                } else {
                    Gson gson = new Gson();
                    try {
                        ServerError serverError = gson.fromJson(response.errorBody().charStream(), ServerError.class);
                        Utils.displayMessage(ProductAddOnListActivity.this, serverError.getError());
                    } catch (JsonSyntaxException e) {
                        Utils.displayMessage(ProductAddOnListActivity.this, getString(R.string.something_went_wrong));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Addon>> call, Throwable t) {
                customDialog.dismiss();
                Utils.displayMessage(ProductAddOnListActivity.this, getString(R.string.something_went_wrong));
            }
        });
    }

    private void prepareList() {
        ArrayList<Addon> listAddOnsNew = new ArrayList<>();

        for (Addon addon : listAddOns) {
            for (Addon addonReceived : listReceivedAddOns) {
                if (addonReceived.getAddon() != null && addon.getId() == addonReceived.getAddon().getId()) {
                    addon.setPrice(addonReceived.getPrice());
                    addon.setChecked(true);
                }
            }
            listAddOnsNew.add(addon);
        }


        setUpAdapter(listAddOnsNew);
    }

    private void setUpAdapter(List<Addon> listAddOnsTemp) {
        if (adapter == null) {
            adapter = new ProductAddOnsAdapter(listAddOnsTemp, this);
            rvAddOns.setAdapter(adapter);
        } else {
            adapter.setList(listAddOnsTemp);
            adapter.notifyDataSetChanged();
        }
    }


}
