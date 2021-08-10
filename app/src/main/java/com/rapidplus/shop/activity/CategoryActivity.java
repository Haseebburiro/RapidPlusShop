package com.rapidplus.shop.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.rapidplus.shop.R;
import com.rapidplus.shop.adapter.CategoryAdapter;
import com.rapidplus.shop.helper.ConnectionHelper;
import com.rapidplus.shop.helper.CustomDialog;
import com.rapidplus.shop.model.Category;
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

public class CategoryActivity extends AppCompatActivity implements CategoryAdapter.CategoryAdapterListener {


    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.category_rv)
    RecyclerView categoryRv;
    @BindView(R.id.add_category_btn)
    Button addCategoryBtn;

    @BindView(R.id.llNoRecords)
    LinearLayout llNoRecords;

    Context context;
    Activity activity;
    ConnectionHelper connectionHelper;
    CustomDialog customDialog;
    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    String TAG = "CategoryActivity";

    List<Category> categoryList;
    CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        title.setText(R.string.category_list);
        backImg.setVisibility(View.VISIBLE);
        context = CategoryActivity.this;
        activity = CategoryActivity.this;
        connectionHelper = new ConnectionHelper(context);
        customDialog = new CustomDialog(context);

        categoryList = new ArrayList<>();

    }

    private void setUpAdapter() {
        if (categoryAdapter == null) {
            categoryAdapter = new CategoryAdapter(categoryList, context);
            categoryRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            categoryRv.setHasFixedSize(true);
            categoryRv.setAdapter(categoryAdapter);
            categoryAdapter.setCategoryAdapterListener(this);
        } else {
            categoryAdapter.setList(categoryList);
            categoryAdapter.notifyDataSetChanged();
        }

        if (categoryList.size() > 0) {
            llNoRecords.setVisibility(View.GONE);
            categoryRv.setVisibility(View.VISIBLE);
        } else {
            llNoRecords.setVisibility(View.VISIBLE);
            categoryRv.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (connectionHelper.isConnectingToInternet())
            getCategory();
        else
            Utils.displayMessage(activity, getString(R.string.oops_no_internet));
    }

    @OnClick({R.id.back_img, R.id.add_category_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                onBackPressed();
                break;
            case R.id.add_category_btn:
                startActivity(new Intent(context, AddCategoryActivity.class));
                break;
        }
    }

    private void getCategory() {
        customDialog.show();
        Call<List<Category>> call = apiInterface.getCategory();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
                customDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        categoryList = response.body();
                        if (categoryList != null) {
                            setUpAdapter();
                        }
                    }
                } else {
                    Gson gson = new Gson();
                    try {
                        ServerError serverError = gson.fromJson(response.errorBody().charStream(), ServerError.class);
                        Utils.displayMessage(activity, serverError.getError());
                    } catch (JsonSyntaxException e) {
                        Utils.displayMessage(activity, getString(R.string.something_went_wrong));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable t) {
                customDialog.dismiss();
                Utils.displayMessage(activity, getString(R.string.something_went_wrong));
            }
        });
    }

    @Override
    public void onCategoryClick(Category category) {
        if (category != null) {
            Intent intent = new Intent(this, AddCategoryActivity.class);
            intent.putExtra("Category", category);
            startActivity(intent);
        }
    }

    @Override
    public void onCategoryDeleteClick(Category category) {
        if (category != null)
            deleteCategory(category);
    }

    public void deleteCategory(final Category addon) {
        customDialog.show();
        Call<List<Category>> call = apiInterface.deleteCategory(addon.getId());
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
                customDialog.dismiss();
                if (response.isSuccessful()) {
                    categoryAdapter.remove(addon);
                } else {
                    Gson gson = new Gson();
                    try {
                        ServerError serverError = gson.fromJson(response.errorBody().charStream(), ServerError.class);
                        Utils.displayMessage(activity, serverError.getError());
                    } catch (JsonSyntaxException e) {
                        Utils.displayMessage(activity, getString(R.string.something_went_wrong));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable t) {
                customDialog.dismiss();
                Utils.displayMessage(activity, getString(R.string.something_went_wrong));
            }
        });
    }
}
