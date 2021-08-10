package com.rapidplus.shop.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.rapidplus.shop.model.Cursines;
import com.rapidplus.shop.utils.LocaleUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.rapidplus.shop.R;
import com.rapidplus.shop.fragment.CuisineSelectFragment;
import com.rapidplus.shop.helper.ConnectionHelper;
import com.rapidplus.shop.helper.CustomDialog;
import com.rapidplus.shop.messages.ProductMessage;
import com.rapidplus.shop.model.Category;
import com.rapidplus.shop.model.Cuisine;
import com.rapidplus.shop.model.Image;
import com.rapidplus.shop.model.Product;
import com.rapidplus.shop.model.ServerError;
import com.rapidplus.shop.model.product.ProductResponse;
import com.rapidplus.shop.network.ApiClient;
import com.rapidplus.shop.network.ApiInterface;
import com.rapidplus.shop.utils.Constants;
import com.rapidplus.shop.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rapidplus.shop.application.MyApplication.ASK_MULTIPLE_PERMISSION_REQUEST_CODE;

public class AddProductActivity extends AppCompatActivity {

    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_product_name)
    EditText etProductName;
    @BindView(R.id.et_description)
    EditText etDescription;
    @BindView(R.id.et_product_order)
    EditText etProductOrder;
    @BindView(R.id.status_spin)
    MaterialSpinner statusSpin;
    @BindView(R.id.category_spin)
    MaterialSpinner categorySpin;
    @BindView(R.id.product_img)
    ImageView productImg;
    @BindView(R.id.fetured_img)
    ImageView featuredImg;
    @BindView(R.id.fetured_img_lay)
    LinearLayout feturedImgLay;
    @BindView(R.id.next_btn)
    Button nextBtn;
    @BindView(R.id.cuisine)
    TextView cuisine;
    @BindView(R.id.rlProductImage)
    RelativeLayout rlProductImage;
    @BindView(R.id.rlFeaturedImage)
    RelativeLayout rlFeaturedImage;
    @BindView(R.id.rbYes)
    RadioButton rbYes;
    @BindView(R.id.rbNo)
    RadioButton rbNo;
    @BindView(R.id.rbVeg)
    RadioButton rbVeg;
    @BindView(R.id.rbNonVeg)
    RadioButton rbNonVeg;

    Context context;
    Activity activity;
    ConnectionHelper connectionHelper;
    CustomDialog customDialog;
    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    String TAG = "AddProductActivity";
    int PRODUCT_IMAGE_TYPE = 0;
    int FEATURE_IMAGE_TYPE = 1;
    String strProductName, strProductDescription, strStatus = "Enabled", strProductOrder = "0", strCategory;
    List<Category> listCategory;
    ArrayList<String> lstCategoryNames = new ArrayList<String>();
    HashMap<String, Integer> hshCategory = new HashMap<>();
    ArrayList<String> lstStatus = new ArrayList<String>();
    File productImageFile, featuredImageFile;
    ProductResponse productResponse;
    int selected_pos = 0;
    private String foodType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ButterKnife.bind(this);
        setUp();

    }

    @SuppressLint("ClickableViewAccessibility")
    private void setUp() {
        CuisineSelectFragment.CUISINES.clear();
        title.setText(getString(R.string.add_product));
        backImg.setVisibility(View.VISIBLE);
        context = AddProductActivity.this;
        activity = AddProductActivity.this;
        connectionHelper = new ConnectionHelper(context);
        customDialog = new CustomDialog(context);

        setStatusSpinner();

        if (connectionHelper.isConnectingToInternet())
            getCategory();
        else
            Utils.displayMessage(this, getString(R.string.oops_no_internet));


        etDescription.setOnTouchListener((v, event) -> {
            if (v.getId() == R.id.et_description) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
            }
            return false;
        });

        //Default
        rlFeaturedImage.setClickable(false);
        rlFeaturedImage.setAlpha(0.5f);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title.setText(R.string.edit_product);
            productResponse = bundle.getParcelable("product_data");
            etProductName.setText(productResponse.getName());
            etDescription.setText(productResponse.getDescription());
            if (productResponse.getStatus().equalsIgnoreCase("enabled")) {
                statusSpin.setSelectedIndex(0);
            } else {
                statusSpin.setSelectedIndex(1);
            }

            etProductOrder.setText(productResponse.getPosition() + "");


            if (productResponse.getImages() != null &&
                    productResponse.getImages().size() > 0) {
                List<Image> imageList = productResponse.getImages();
                String url = imageList.get(0).getUrl();

                Glide.with(this)
                        .asBitmap()
                        .load(url)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                productImg.setImageBitmap(resource);
                                productImageFile = Utils.storeInFile(context, resource, "product_image.png", "png");
                            }
                        });

               /* Glide.with(context).load(url)
                        .apply(new RequestOptions().centerCrop().placeholder(R.drawable.delete_shop).error(R.drawable.delete_shop).dontAnimate()).into(productImg);*/

            }

            if (productResponse.getFeaturedImages() != null &&
                    productResponse.getFeaturedImages().size() > 0) {
                List<Image> imageList = productResponse.getFeaturedImages();
                String url = imageList.get(0).getUrl();

                Glide.with(this)
                        .asBitmap()
                        .load(url)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                featuredImg.setImageBitmap(resource);
                                featuredImageFile = Utils.storeInFile(context, resource, "featured_image.png", "png");
                            }
                        });

               /* Glide.with(context).load(url)
                        .apply(new RequestOptions().centerCrop().placeholder(R.drawable.delete_shop).error(R.drawable.delete_shop).dontAnimate()).into(productImg);*/

            }

            if (productResponse.getFoodType() != null &&
                    productResponse.getFoodType().equals(Constants.VEG)) {
                rbVeg.setChecked(true);
                rbNonVeg.setChecked(false);
            } else {
                rbVeg.setChecked(false);
                rbNonVeg.setChecked(true);
            }

            if (productResponse.getFeatured() == 1) {
                //debug
                //rbYes.setChecked(false);
                rbYes.setChecked(true);
                rbNo.setChecked(false);
                rlFeaturedImage.setClickable(true);
                rlFeaturedImage.setAlpha(1.0f);
            } else {
                rbYes.setChecked(false);
                rbNo.setChecked(true);
                rlFeaturedImage.setClickable(false);
                rlFeaturedImage.setAlpha(0.5f);
            }

            if (productResponse.getProductcuisines() != null) {
                Cuisine data = new Cuisine();
                data.setId(productResponse.getProductcuisines().getId());
                data.setName(productResponse.getProductcuisines().getName());
                CuisineSelectFragment.CUISINES.add(data);
                cuisine.setText(productResponse.getProductcuisines().getName());
            }
        }

        rbYes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                rlFeaturedImage.setClickable(true);
                rlFeaturedImage.setAlpha(1.0f);
            } else {
                rlFeaturedImage.setClickable(false);
                rlFeaturedImage.setAlpha(0.5f);
            }
        });
        rbVeg.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                rbVeg.setChecked(true);
                rbNonVeg.setChecked(false);
                foodType = Constants.VEG;
            }
        });
        rbNonVeg.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                rbNonVeg.setChecked(true);
                rbVeg.setChecked(false);
                foodType = Constants.NON_VEG;
            }
        });
    }


    private void setStatusSpinner() {
        lstStatus.add("Enabled");
        lstStatus.add("Disabled");
        statusSpin.setItems(lstStatus);
        statusSpin.setOnItemSelectedListener(new CommonOnItemSelectListener());
    }

    private void setCategorySpinner() {
        customDialog.dismiss();
        if (productResponse != null && productResponse.getCategories().size() > 0) {
            selected_pos = lstCategoryNames.indexOf(productResponse.getCategories().get(0).getName());
        }
        categorySpin.setItems(lstCategoryNames);
        categorySpin.setOnItemSelectedListener(new CommonOnItemSelectListener());
        if (selected_pos != 0 && selected_pos != -1) {
            categorySpin.setSelectedIndex(selected_pos);
            strCategory = hshCategory.get(lstCategoryNames.get(selected_pos)) + "";
        }
    }

    @OnClick({R.id.back_img, R.id.rlProductImage, R.id.rlFeaturedImage, R.id.next_btn, R.id.cuisine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                onBackPressed();
                break;

            case R.id.rlProductImage:
                galleryIntent(PRODUCT_IMAGE_TYPE);
                break;

            case R.id.rlFeaturedImage:
                galleryIntent(FEATURE_IMAGE_TYPE);
                break;

            case R.id.next_btn:
                if (validateProductDetails()) {
                    goToNextPage();
                }
                break;

            case R.id.cuisine:
                new CuisineSelectFragment(true).show(getSupportFragmentManager(), "cuisineSelectFragment");
                break;
        }
    }

    private void getCategory() {
        customDialog.show();
        Call<List<Category>> call = apiInterface.getCategory();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        listCategory = response.body();
                        //default option
                        lstCategoryNames.add(getString(R.string.product_select_catefory));
                        hshCategory.put(getString(R.string.product_select_catefory), 0);
                        if (listCategory.size() > 0) {
                            for (int i = 0; i < listCategory.size(); i++) {
                                Category category = listCategory.get(i);
                                List<Product> product = category.getProducts();
                                for(int j = 0; j <product.size(); j++) {
                                    foodType = product.get(j).getFoodType();
                                }
                                lstCategoryNames.add(listCategory.get(i).getName());
                                hshCategory.put(listCategory.get(i).getName(), listCategory.get(i).getId());
                                if (i == (listCategory.size() - 1)) {
                                    setCategorySpinner();
                                }
                            }
                        } else {
                            setCategorySpinner();
                        }

                    }
                } else {
                    customDialog.dismiss();
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

    private void goToNextPage() {
        ProductMessage message = new ProductMessage();
        message.setStrProductName(strProductName);
        message.setStrProductDescription(strProductDescription);
        message.setStrProductStatus(strStatus.equals("Enabled") ? "1" : "0");
        message.setStrProductCategory(strCategory);
        message.setStrProductOrder(strProductOrder);
        message.setFeaturedImageFile(featuredImageFile);
        message.setProductImageFile(productImageFile);
        if (foodType.equals(Constants.VEG)) {
            message.setStrSelectedFoodType(Constants.VEG);
        } else {
            message.setStrSelectedFoodType(Constants.NON_VEG);
        }
        message.setStrCuisineId(CuisineSelectFragment.CUISINES.get(0).getId() + "");
        ProductAddOnActivity.setMessage(message);
        if (productResponse != null) {
            Intent intent = new Intent(context, ProductAddOnActivity.class);
            intent.putExtra("product_data", productResponse);
            startActivity(intent);
        } else {
            startActivity(new Intent(this, ProductAddOnActivity.class));
        }

    }

    private void galleryIntent(int type) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                EasyImage.openChooserWithDocuments(AddProductActivity.this, "Select", type);
            else
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, ASK_MULTIPLE_PERMISSION_REQUEST_CODE);
        } else EasyImage.openChooserWithDocuments(AddProductActivity.this, "Select", type);
    }

    private boolean validateProductDetails() {
        strProductName = etProductName.getText().toString().trim();
        strProductDescription = etDescription.getText().toString().trim();
        strProductOrder = etProductOrder.getText().toString().trim();
        if (strProductOrder.equals("")) {
            strProductOrder = "0";
        }

        if (strProductName == null || strProductName.isEmpty()) {
            Utils.displayMessage(this, getString(R.string.error_msg_product_name));
            return false;
        } else if (strProductDescription == null || strProductDescription.isEmpty()) {
            Utils.displayMessage(this, getString(R.string.error_msg_product_description));
            return false;
        } else if (CuisineSelectFragment.CUISINES.isEmpty()) {
            Utils.displayMessage(activity, getResources().getString(R.string.invalid_cuisine));
            return false;
        } else if (strProductOrder == null || strProductOrder.isEmpty()) {
            Utils.displayMessage(this, getString(R.string.error_msg_product_order));
            return false;
        } else if (strCategory == null || strCategory.isEmpty()) {
            Utils.displayMessage(activity, getResources().getString(R.string.error_msg_product_category));
            return false;
        } else if (!rbVeg.isChecked() && !rbNonVeg.isChecked()) {
            Utils.displayMessage(activity, getResources().getString(R.string.error_msg_selected_food_type));
            return false;
        }
//        else if (productImageFile == null) {
//            Utils.displayMessage(activity, getString(R.string.please_select_product_image));
//            return false;
//        } else if (rbYes.isChecked() && featuredImageFile == null) {
//            Utils.displayMessage(activity, getResources().getString(R.string.error_msg_product_select_featured_image));
//            return false;
//        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                e.printStackTrace();
            }

            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                if (type == PRODUCT_IMAGE_TYPE) {
                    productImageFile = imageFiles.get(0);
                    Glide.with(AddProductActivity.this)
                            .load(imageFiles.get(0))
                            .apply(new RequestOptions()
                                    .placeholder(R.mipmap.ic_launcher)
                                    .error(R.mipmap.ic_launcher).dontAnimate())
                            .into(productImg);
                } else if (type == FEATURE_IMAGE_TYPE) {
                    featuredImageFile = imageFiles.get(0);
                    Glide.with(AddProductActivity.this)
                            .load(imageFiles.get(0))
                            .apply(new RequestOptions()
                                    .placeholder(R.mipmap.ic_launcher)
                                    .error(R.mipmap.ic_launcher).dontAnimate())
                            .into(featuredImg);
                }

            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {

            }
        });
    }

    public void bindCuisine() {
        StringBuilder cuisneStr = new StringBuilder();
        for (int i = 0; i < CuisineSelectFragment.CUISINES.size(); i++) {
            if (i == 0)
                cuisneStr.append(CuisineSelectFragment.CUISINES.get(i).getName());
            else
                cuisneStr.append(",").append(CuisineSelectFragment.CUISINES.get(i).getName());
        }

        cuisine.setText(cuisneStr.toString());
    }

    class CommonOnItemSelectListener implements MaterialSpinner.OnItemSelectedListener {
        @Override
        public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
            switch (view.getId()) {
                case R.id.status_spin:
                    strStatus = lstStatus.get(position);
                    break;

                case R.id.category_spin:
                    strCategory = "" + hshCategory.get(lstCategoryNames.get(position));
                    if (strCategory.equalsIgnoreCase("0")) {
                        strCategory = "";
                    }
                    break;
            }
        }
    }
}
