package com.rapidplus.shop.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.rapidplus.shop.R;
import com.rapidplus.shop.adapter.ProductsAdapter;
import com.rapidplus.shop.helper.ConnectionHelper;
import com.rapidplus.shop.helper.CustomDialog;
import com.rapidplus.shop.model.ProductModel;
import com.rapidplus.shop.model.ServerError;
import com.rapidplus.shop.model.product.ProductResponse;
import com.rapidplus.shop.network.ApiClient;
import com.rapidplus.shop.network.ApiInterface;
import com.rapidplus.shop.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends AppCompatActivity implements ProductsAdapter.ProductAdapterListener {

    private static final String TAG = "ProductsActivity";
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.products_rv)
    RecyclerView productsRv;
    @BindView(R.id.add_products_btn)
    Button addProductsBtn;
    @BindView(R.id.llNoRecords)
    LinearLayout llNoRecords;
    Context context;
    Activity activity;
    ConnectionHelper connectionHelper;
    CustomDialog customDialog;
    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    List<ProductModel> listProductModel;
    ProductsAdapter productsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        ButterKnife.bind(this);

        title.setText(getString(R.string.products));
        backImg.setVisibility(View.VISIBLE);
        context = ProductsActivity.this;
        activity = ProductsActivity.this;
        connectionHelper = new ConnectionHelper(context);
        customDialog = new CustomDialog(context);
        listProductModel = new ArrayList<>();
    }


    @Override
    protected void onResume() {
        super.onResume();
        refreshProductList();
    }


    private void getProductList() {
        Call<List<ProductResponse>> call = apiInterface.getProductList();
        call.enqueue(new Callback<List<ProductResponse>>() {
            @Override
            public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {
                if (response.isSuccessful()) {
                    updateUI(response.body());
                } else {
                    customDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(context, jObjError.optString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
                customDialog.dismiss();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateUI(List<ProductResponse> productList) {
        if (productList.size() > 0) {
            llNoRecords.setVisibility(View.GONE);
            productsRv.setVisibility(View.VISIBLE);
//            customDialog.dismiss();
//            setUpAdapter();
            new Thread(new ArrangeDataTask(productList)).start();
        } else {
            if (!listProductModel.isEmpty()) {
                listProductModel.clear();
            }
            setUpAdapter();
            llNoRecords.setVisibility(View.VISIBLE);
            productsRv.setVisibility(View.GONE);
            customDialog.dismiss();
        }
    }

    private void setUpAdapter() {
        if (productsAdapter == null) {
            productsAdapter = new ProductsAdapter(context, listProductModel);
            productsRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            productsRv.setHasFixedSize(true);
            productsRv.setAdapter(productsAdapter);
            productsAdapter.setProductAdapterListener(this);
        } else {
            productsAdapter.setList(listProductModel);
            productsAdapter.notifyDataSetChanged();
        }

    }

    @OnClick({R.id.back_img, R.id.add_products_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                onBackPressed();
                break;
            case R.id.add_products_btn:
                startActivity(new Intent(context, AddProductActivity.class));
                break;
        }
    }

    @Override
    public void onProductClick(ProductResponse product) {
        Intent intent = new Intent(context, AddProductActivity.class);
        intent.putExtra("product_data", product);
        startActivity(intent);
    }

    @Override
    public void onProductDeleteClick(ProductResponse product) {
        customDialog.show();
        int product_id = product.getId();

        Call<ResponseBody> call = apiInterface.deleteProduct(product_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                customDialog.dismiss();
                if (response.isSuccessful()) {
                    refreshProductList();
                } else {
                    Gson gson = new Gson();
                    try {
                        ServerError serverError = gson.fromJson(response.errorBody().charStream(), ServerError.class);
                        Utils.displayMessage(ProductsActivity.this, serverError.getError());
                    } catch (JsonSyntaxException e) {
                        Utils.displayMessage(ProductsActivity.this, getString(R.string.something_went_wrong));
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                customDialog.dismiss();
                Utils.displayMessage(ProductsActivity.this, getString(R.string.something_went_wrong));
            }
        });
    }

    private void refreshProductList() {
        if (connectionHelper.isConnectingToInternet()) {
            //getCategory();
            customDialog.show();
            getProductList();
        } else {
            Utils.displayMessage(this, getString(R.string.oops_no_internet));
        }
    }

    class ArrangeDataTask implements Runnable {
        List<ProductResponse> productList;
        Set<String> hash_header = new HashSet<String>();

        public ArrangeDataTask(List<ProductResponse> productList) {
            this.productList = productList;
        }

        @Override
        public void run() {

            if (!listProductModel.isEmpty()) {
                listProductModel.clear();
            }

            //Header name received here
            for (int i = 0; i < productList.size(); i++) {
                if (productList.get(i).getCategories().size() > 0)
                    hash_header.add(productList.get(i).getCategories().get(0).getName());
            }

            Iterator<String> iterator = hash_header.iterator();

            while (iterator.hasNext()) {
                String header = iterator.next();
                ProductModel model = new ProductModel();
                model.setHeader(header);
                List<ProductResponse> lstTempProduct = new ArrayList<>();
                for (int j = 0; j < productList.size(); j++) {
                    if (productList.get(j).getCategories().size() > 0) {
                        if (productList.get(j).getCategories().get(0).getName().equalsIgnoreCase(header)) {
                            lstTempProduct.add(productList.get(j));
                        }
                    }

                    if (j == (productList.size() - 1)) {
                        model.setProductList(lstTempProduct);
                    }

                }

                listProductModel.add(model);

                if (!iterator.hasNext()) {
                    //Last iteration
                    new Handler(getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            customDialog.dismiss();
                            setUpAdapter();
                        }
                    });
                }
            }
        }
    }


}
