package com.rapidplus.shop.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.rapidplus.shop.R;
import com.rapidplus.shop.helper.ConnectionHelper;
import com.rapidplus.shop.helper.CustomDialog;
import com.rapidplus.shop.helper.SharedHelper;
import com.rapidplus.shop.imagecompressor.Compressor;
import com.rapidplus.shop.model.Category;
import com.rapidplus.shop.model.Image;
import com.rapidplus.shop.network.ApiClient;
import com.rapidplus.shop.network.ApiInterface;
import com.rapidplus.shop.utils.Constants;
import com.rapidplus.shop.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rapidplus.shop.application.MyApplication.ASK_MULTIPLE_PERMISSION_REQUEST_CODE;

public class AddCategoryActivity extends AppCompatActivity {

    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_addons_name)
    EditText etAddonsName;
    @BindView(R.id.et_description)
    EditText etDescription;
    @BindView(R.id.status_spin)
    MaterialSpinner statusSpin;
    @BindView(R.id.category_img)
    ImageView categoryImg;
    @BindView(R.id.save_btn)
    Button saveBtn;
    @BindView(R.id.category_order_picker)
    EditText categoryOrderPicker;

    Context context;
    Activity activity;
    ConnectionHelper connectionHelper;
    CustomDialog customDialog;
    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    File categoryImageFile;
    ArrayList<String> lstItems = new ArrayList<>();
    String strCategoryName, strDescription, strCategoryOrder, strStatus;
    private Category categoryDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        ButterKnife.bind(this);

        title.setText(getString(R.string.add_category));
        backImg.setVisibility(View.VISIBLE);
        context = AddCategoryActivity.this;
        activity = AddCategoryActivity.this;
        connectionHelper = new ConnectionHelper(context);
        customDialog = new CustomDialog(context);
        setSpinnerAdpater();

        if (getIntent() != null && getIntent().getExtras() != null) {
            Category category = (Category) getIntent().getParcelableExtra("Category");
            if (category != null) {
                setCategoryDetails(category);
            }
        }


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

    }

    private void setSpinnerAdpater() {
        lstItems.add(getString(R.string.enable));
        lstItems.add(getString(R.string.disable));
        statusSpin.setItems(lstItems);
        statusSpin.setOnItemSelectedListener((view, position, id, item) -> {

        });
    }

    @OnClick({R.id.back_img, R.id.category_img, R.id.save_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                onBackPressed();
                break;
            case R.id.category_img:
                galleryIntent();
                break;
            case R.id.save_btn:
                if (validateFields()) {
                    if (connectionHelper.isConnectingToInternet()) {
                        String shop_id = SharedHelper.getKey(this, Constants.PREF.PROFILE_ID);
                        HashMap<String, RequestBody> params = new HashMap<>();
                        params.put("name", RequestBody.create(MediaType.parse("text/plain"), strCategoryName));
                        params.put("description", RequestBody.create(MediaType.parse("text/plain"), strDescription));
                        params.put("status", RequestBody.create(MediaType.parse("text/plain"), strStatus.toLowerCase()));
                        params.put("shop_id", RequestBody.create(MediaType.parse("text/plain"), shop_id));
                        params.put("position", RequestBody.create(MediaType.parse("text/plain"), strCategoryOrder));
                        addCategory(params);
                    } else {
                        Utils.displayMessage(this, getString(R.string.oops_no_internet));
                    }
                }

                break;
        }
    }

    private boolean validateFields() {

        strCategoryName = etAddonsName.getText().toString().trim();
        strDescription = etDescription.getText().toString().trim();
        strCategoryOrder = categoryOrderPicker.getText().toString().trim();
        String status = lstItems.get(statusSpin.getSelectedIndex());
        if (status.equalsIgnoreCase(getString(R.string.enable))) {
            strStatus = "enabled";
        } else {
            strStatus = "disabled";
        }
//        strStatus = lstItems.get(statusSpin.getSelectedIndex());

        if (strCategoryName.isEmpty()) {
            Utils.displayMessage(this, getString(R.string.please_enter_category_name));
            return false;
        } else if (strDescription.isEmpty()) {
            Utils.displayMessage(this, getString(R.string.please_enter_category_description));
            return false;
        } else if (strStatus.isEmpty()) {
            Utils.displayMessage(this, getString(R.string.please_select_status));
            return false;
        }
//        else if (categoryImageFile == null) {
//            Utils.displayMessage(this, getString(R.string.please_select_category_image));
//            return false;
//        }
        return true;
    }

    private void addCategory(HashMap<String, RequestBody> params) {
        customDialog.show();
        MultipartBody.Part filePart = null;
        if (categoryImageFile != null) {
            try {
                categoryImageFile = new Compressor(this).compressToFile(categoryImageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            filePart = MultipartBody.Part.createFormData("image", categoryImageFile.getName(),
                    RequestBody.create(MediaType.parse("image/*"), categoryImageFile));
        }
        Call<Category> call = null;
        if (categoryDetails != null) {
            params.put("_method", RequestBody.create(MediaType.parse("text/plain"), "PATCH"));
            call = apiInterface.updateCategory(categoryDetails.getId(), params, filePart);
        } else {
            call = apiInterface.addCategory(params, filePart);
        }

        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                customDialog.dismiss();
                if (response.isSuccessful()) {
                    Utils.displayMessage(AddCategoryActivity.this, getString(R.string.category_added_successfully));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onBackPressed();
                        }
                    }, 1000);
                } else {
                    Utils.displayMessage(AddCategoryActivity.this, "failed");
                }

            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                customDialog.dismiss();
                Utils.displayMessage(AddCategoryActivity.this, t.toString());
            }
        });
    }

    private void setCategoryDetails(Category categoryDetails) {
        this.categoryDetails = categoryDetails;
        title.setText(R.string.edit_category);

        if (categoryDetails.getName() != null)
            etAddonsName.setText(categoryDetails.getName());
        if (categoryDetails.getDescription() != null)
            etDescription.setText(categoryDetails.getDescription());

        String categoryOrder = String.valueOf(categoryDetails.getPosition());
        if (categoryOrder != null && !categoryOrder.equalsIgnoreCase("null"))
            categoryOrderPicker.setText(categoryOrder);
        else
            categoryOrderPicker.setText("");

        if (categoryDetails.getImages() != null && categoryDetails.getImages().size() > 0) {
            List<Image> images = categoryDetails.getImages();
            if (images != null && images.size() > 0) {
                String img = images.get(0).getUrl();
                /*Glide.with(context).load(img)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_place_holder_image).error(R.drawable.ic_place_holder_image).dontAnimate()).into(categoryImg);*/

                Glide.with(this)
                        .asBitmap()
                        .load(img)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                categoryImg.setImageBitmap(resource);
                                categoryImageFile = Utils.storeInFile(context, resource, "category_image.jpg", "jpeg");
                            }
                        });
            }
        }

        if (categoryDetails.getStatus() != null) {
            if (categoryDetails.getStatus().equalsIgnoreCase(getString(R.string.enable)))
                statusSpin.setSelectedIndex(0);
            else
                statusSpin.setSelectedIndex(1);
        }
    }

    private void galleryIntent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                EasyImage.openChooserWithDocuments(AddCategoryActivity.this, "Select", 0);
            else
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, ASK_MULTIPLE_PERMISSION_REQUEST_CODE);
        } else EasyImage.openChooserWithDocuments(AddCategoryActivity.this, "Select", 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ASK_MULTIPLE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean permission1 = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean permission2 = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (permission1 && permission2) galleryIntent();
                    else
                        Toast.makeText(getApplicationContext(), "Please give permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
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
                categoryImageFile = imageFiles.get(0);
                Glide
                        .with(context)
                        .load(imageFiles.get(0))
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.ic_place_holder_image)
                                .error(R.drawable.ic_place_holder_image).dontAnimate())
                        .into(categoryImg);
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {

            }
        });
    }

}
