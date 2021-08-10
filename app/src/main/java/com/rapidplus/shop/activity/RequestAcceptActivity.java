package com.rapidplus.shop.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rapidplus.shop.utils.LocaleUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.rapidplus.shop.R;
import com.rapidplus.shop.adapter.OrderProductAdapter;
import com.rapidplus.shop.helper.ConnectionHelper;
import com.rapidplus.shop.helper.CustomDialog;
import com.rapidplus.shop.helper.GlobalData;
import com.rapidplus.shop.model.Order;
import com.rapidplus.shop.model.ServerError;
import com.rapidplus.shop.network.ApiClient;
import com.rapidplus.shop.network.ApiInterface;
import com.rapidplus.shop.utils.Utils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestAcceptActivity extends AppCompatActivity {

    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.user_img)
    CircleImageView userImg;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.payment_mode)
    TextView paymentMode;
    @BindView(R.id.call_img)
    ImageView callImg;
    @BindView(R.id.order_product_rv)
    RecyclerView orderProductRv;
    @BindView(R.id.sub_total)
    TextView subTotal;
    @BindView(R.id.tv_cgst)
    TextView tv_cgst;
    @BindView(R.id.tv_sgst)
    TextView tv_sgst;
    @BindView(R.id.delivery_charges)
    TextView deliveryCharges;
    @BindView(R.id.total)
    TextView total;
    @BindView(R.id.cancel_btn)
    Button cancelBtn;
    @BindView(R.id.accept_btn)
    Button acceptBtn;
    @BindView(R.id.button_lay)
    LinearLayout buttonLay;
    @BindView(R.id.dispute_btn)
    Button disputeBtn;

    Order order;
    OrderProductAdapter orderProductAdapter;

    Context context;
    Activity activity;
    ConnectionHelper connectionHelper;
    CustomDialog customDialog;
    boolean isInternet;
    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    String TAG = "RequestAcceptActivity";
    @BindView(R.id.notes)
    TextView notes;

    @BindView(R.id.discount)
    TextView discount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_accept);
        ButterKnife.bind(this);
        context = RequestAcceptActivity.this;
        activity = RequestAcceptActivity.this;
        connectionHelper = new ConnectionHelper(context);
        isInternet = connectionHelper.isConnectingToInternet();
        customDialog = new CustomDialog(context);


        backImg.setVisibility(View.VISIBLE);

        if (GlobalData.selectedOrder != null) {
            order = GlobalData.selectedOrder;
        } else {
            finish();
            return;
        }

        title.setText(getString(R.string.order) + " " + order.getId());

        Glide.with(context).load(order.getUser().getAvatar())
                .apply(new RequestOptions().placeholder(R.drawable.delete_shop).error(R.drawable.delete_shop).dontAnimate()).into(userImg);

        String name = order.getUser().getName();
        String payment_mode = order.getInvoice().getPaymentMode();

        //No minimum character limit in register screen.
        if (name.length() > 1)
            name = name.substring(0, 1).toUpperCase() + name.substring(1);

        payment_mode = payment_mode.substring(0, 1).toUpperCase() + payment_mode.substring(1);

        userName.setText(name);
        address.setText(order.getAddress().getMapAddress());
        paymentMode.setText(payment_mode);

        if (order.getNote() != null)
            notes.setText(order.getNote());
        else
            notes.setText(getResources().getString(R.string.empty));

        orderProductAdapter = new OrderProductAdapter(context, order.getItems());
        orderProductRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        orderProductRv.setHasFixedSize(true);
        orderProductRv.setAdapter(orderProductAdapter);

        Double cgst_percentage_multiplayer = (Double.parseDouble(order.getInvoice().getCGST() + "") / 100);
        Double sgst_percentage_multiplayer = (Double.parseDouble(order.getInvoice().getSGST() + "") / 100);

        double gross_amount = order.getInvoice().getGross() - order.getInvoice().getDiscount();

        double cgst = (gross_amount * (cgst_percentage_multiplayer));
        double sgst = (gross_amount * (sgst_percentage_multiplayer));

        subTotal.setText(GlobalData.profile.getCurrency() + String.format("%.2f", order.getInvoice().getGross()));
        discount.setVisibility(View.GONE);
        tv_cgst.setVisibility(View.GONE);
        tv_sgst.setVisibility(View.GONE);
        deliveryCharges.setVisibility(View.GONE);
        total.setText(GlobalData.profile.getCurrency() + String.format("%.2f", order.getInvoice().getGross()));
//        discount.setText(GlobalData.profile.getCurrency() + String.format("%.2f", order.getInvoice().getDiscount()));
//        tv_cgst.setText(GlobalData.profile.getCurrency() + String.format("%.2f", cgst));
//        tv_sgst.setText(GlobalData.profile.getCurrency() + String.format("%.2f", sgst));
//        deliveryCharges.setText(GlobalData.profile.getCurrency() + String.format("%.2f", order.getInvoice().getDeliveryCharge()));
//        total.setText(GlobalData.profile.getCurrency() + String.format("%.2f", order.getInvoice().getNet()));

        if (order.getStatus().equals("ORDERED") && order.getDispute().equals("NODISPUTE")) {
            disputeBtn.setVisibility(View.GONE);
            buttonLay.setVisibility(View.VISIBLE);
        } else {
            disputeBtn.setVisibility(View.VISIBLE);
            buttonLay.setVisibility(View.GONE);
        }


    }

    @OnClick({R.id.back_img, R.id.call_img, R.id.cancel_btn, R.id.accept_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                onBackPressed();
                break;
            case R.id.call_img:
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + order.getUser().getPhone()));
                if (dialIntent.resolveActivity(getPackageManager()) != null)
                    startActivity(dialIntent);
                else
                    Utils.displayMessage(this, "Call feature not supported");
                break;
            case R.id.cancel_btn:
                AlertDialog.Builder cancelAlert = new AlertDialog.Builder(this);
                cancelAlert.setTitle(getResources().getString(R.string.order));
                cancelAlert.setMessage(getResources().getString(R.string.are_you_sure_want_to_cancel_the_order));
                cancelAlert.setPositiveButton(getResources().getString(R.string.okay), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("status", "CANCELLED");
                        updateOrderStatus(map);
                    }
                });
                cancelAlert.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                        dialog.dismiss();
                    }
                });
                cancelAlert.show();
                break;
            case R.id.accept_btn:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.order_delivery_time, null);
                final EditText edittext = alertLayout.findViewById(R.id.edit_text);
                alert.setTitle(getResources().getString(R.string.order_delivery_time));
                alert.setView(alertLayout);
                alert.setPositiveButton(getResources().getString(R.string.okay), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String deliveryTime = edittext.getText().toString();
                        if (deliveryTime.isEmpty()) {
                            Toast.makeText(context, getResources().getString(R.string.please_enter_delivery_time), Toast.LENGTH_SHORT).show();
                        } else {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("status", "RECEIVED");
                            map.put("order_ready_time", deliveryTime);
                            updateOrderStatus(map);
                        }
                    }
                });

                alert.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                        dialog.dismiss();
                    }
                });
                alert.show();
                break;
        }
    }


    private void updateOrderStatus(HashMap<String, String> map) {
        customDialog.show();
        Call<Order> call = apiInterface.updateOrderStatus(GlobalData.selectedOrder.getId(), map);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(@NonNull Call<Order> call, Response<Order> response) {
                customDialog.dismiss();
                if (response.isSuccessful()) {
                    finish();
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
            public void onFailure(@NonNull Call<Order> call, Throwable t) {
                customDialog.dismiss();
//                Utils.displayMessage(activity, getString(R.string.something_went_wrong));
            }
        });


    }
}
