package com.rapidplus.shop.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.rapidplus.shop.R;
import com.rapidplus.shop.helper.ConnectionHelper;
import com.rapidplus.shop.helper.CustomDialog;
import com.rapidplus.shop.helper.SharedHelper;
import com.rapidplus.shop.model.CompleteCancel;
import com.rapidplus.shop.model.RevenueResponse;
import com.rapidplus.shop.model.ServerError;
import com.rapidplus.shop.network.ApiClient;
import com.rapidplus.shop.network.ApiInterface;
import com.rapidplus.shop.utils.Constants;
import com.rapidplus.shop.utils.Utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RevenueFragment extends Fragment {


    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tvTotalRevenue)
    TextView tvTotalRevenue;
    @BindView(R.id.tvOrderReceived)
    TextView tvOrderReceived;
    @BindView(R.id.tvOrderDelievered)
    TextView tvOrderDelievered;
    @BindView(R.id.tvTodayEarnings)
    TextView tvTodayEarnings;
    @BindView(R.id.tvMonthlyEarnings)
    TextView tvMonthlyEarnings;
    @BindView(R.id.chart)
    BarChart mChart;
    Unbinder unbinder;

    Context context;
    Activity activity;
    ConnectionHelper connectionHelper;
    CustomDialog customDialog;
    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    String TAG = "RevenueFragment";

    public RevenueFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_revenue, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUp();
    }

    private void setUp() {
        title.setText(getString(R.string.revenue));
        context = getContext();
        activity = getActivity();

        connectionHelper = new ConnectionHelper(context);
        customDialog = new CustomDialog(context);

    }


    private void prepareBarChart(List<CompleteCancel> completeCancelList) {
        ArrayList monthsList = new ArrayList<>();
        for (int i = 0; i < completeCancelList.size(); i++) {
            monthsList.add(completeCancelList.get(i).getMonth());
        }


        ArrayList entriesGroup1 = new ArrayList<>();
        ArrayList entriesGroup2 = new ArrayList<>();

// fill the lists
        for (int i = 0; i < monthsList.size(); i++) {
            entriesGroup1.add(new BarEntry(i, Float.parseFloat(completeCancelList.get(i).getDelivered())));
            entriesGroup2.add(new BarEntry(i, Float.parseFloat(completeCancelList.get(i).getCancelled())));
            //entriesGroup1.add(new BarEntry(i, 10));
            //entriesGroup2.add(new BarEntry(i, 5));
        }

        float barWidth = 0.3f;
        float barSpace = 0f;
        float groupSpace = 0.4f;


        mChart.setDescription(null);
        mChart.setPinchZoom(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);

        BarDataSet set1, set2;
        set1 = new BarDataSet(entriesGroup1, getResources().getString(R.string.order_delivered));
        set1.setColor(context.getResources().getColor(R.color.colorAccent));
        set1.setDrawValues(false);

        set2 = new BarDataSet(entriesGroup2, getResources().getString(R.string.order_cancelled));
        set2.setColor(context.getResources().getColor(R.color.colorPrimary));
        set2.setDrawValues(false);

        BarData data = new BarData(set1, set2);
        data.setValueFormatter(new LargeValueFormatter());
        mChart.setData(data);
        mChart.getBarData().setBarWidth(barWidth);
        mChart.getXAxis().setAxisMinimum(0);
        mChart.getXAxis().setAxisMaximum(0 + mChart.getBarData().getGroupWidth(groupSpace, barSpace) * monthsList.size());
        mChart.groupBars(0, groupSpace, barSpace);
        mChart.getData().setHighlightEnabled(false);
        mChart.invalidate();

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setYOffset(0f);
        l.setXOffset(0f);
        l.setYEntrySpace(0f);
        //l.setTextSize(8f);


        //X-axis
        XAxis xAxis = mChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(monthsList.size());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(monthsList));
        //Y-axis
        mChart.getAxisRight().setEnabled(false);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(true);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f);

        mChart.setScaleMinima((float) 12 / 17f, 1f);
        mChart.zoom(-10f, 0f, 0, 0);

    }


    @Override
    public void onResume() {
        super.onResume();
        getRevenueDetails();
    }

    private void getRevenueDetails() {
        customDialog.show();
        Call<RevenueResponse> call = apiInterface.getRevenueDetails();
        call.enqueue(new Callback<RevenueResponse>() {
            @Override
            public void onResponse(Call<RevenueResponse> call, Response<RevenueResponse> response) {
                customDialog.dismiss();
                if (response.isSuccessful()) {
                    updateUI(response.body());
                } else {
                    try {
                        ServerError serverError = new Gson().fromJson(response.errorBody().charStream(), ServerError.class);
                        Utils.displayMessage(activity, serverError.getError());
                    } catch (JsonSyntaxException e) {
                        Utils.displayMessage(activity, getString(R.string.something_went_wrong));
                    }

                }
            }

            @Override
            public void onFailure(Call<RevenueResponse> call, Throwable t) {
                customDialog.dismiss();
                Utils.displayMessage(activity, getString(R.string.something_went_wrong));
            }
        });
    }

    private void updateUI(RevenueResponse response) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        String currency = SharedHelper.getKey(getActivity(), Constants.PREF.CURRENCY);

        String total_revenue = currency + formatter.format(response.getTotalRevenue());
        String order_received = response.getOrderReceivedToday() + "";
        String order_develievered = response.getOrderDeliveredToday() + "";
        String today_earnings = currency + formatter.format(response.getOrderIncomeToday());
        String monthly_earnings = currency + formatter.format(response.getOrderIncomeMonthly());

        tvTotalRevenue.setText(total_revenue);
        tvOrderReceived.setText(order_received);
        tvOrderDelievered.setText(order_develievered);
        tvTodayEarnings.setText(today_earnings);
        tvMonthlyEarnings.setText(monthly_earnings);

        //prepareBarChart(response.getCompleteCancel());
        prepareBarChart(response.getCompleteCancel());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
