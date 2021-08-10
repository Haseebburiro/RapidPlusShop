package com.rapidplus.shop.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.rapidplus.shop.R;
import com.rapidplus.shop.activity.HistoryActivity;
import com.rapidplus.shop.adapter.HistoryAdapter;
import com.rapidplus.shop.model.IncomingOrders;
import com.rapidplus.shop.model.Order;
import com.rapidplus.shop.model.ServerError;
import com.rapidplus.shop.network.ApiClient;
import com.rapidplus.shop.network.ApiInterface;
import com.rapidplus.shop.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingVisitFragment extends Fragment {

    @BindView(R.id.upcoming_rv)
    RecyclerView upcomingRv;

    @BindView(R.id.llNoRecords)
    LinearLayout llNoRecords;

    Unbinder unbinder;
    Context context;
    Activity activity;

    HistoryAdapter historyAdapter;
    List<Order> orderList = new ArrayList<>();
    private ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

    public UpcomingVisitFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (activity == null)
            activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming_visit, container, false);
        unbinder = ButterKnife.bind(this, view);
        setupAdapter();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getOnGoingOrders();
    }

    private void setupAdapter() {
        historyAdapter = new HistoryAdapter(orderList, context);
        upcomingRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        upcomingRv.setHasFixedSize(true);
        upcomingRv.setAdapter(historyAdapter);
    }

    private void getOnGoingOrders() {
        HistoryActivity.showDialog();
        Call<IncomingOrders> call = apiInterface.getIncomingOrders("processing");
        call.enqueue(new Callback<IncomingOrders>() {
            @Override
            public void onResponse(Call<IncomingOrders> call, Response<IncomingOrders> response) {
                HistoryActivity.dismissDialog();
                if (response.isSuccessful()) {
                    orderList.clear();
                    if (response.body() != null) {
                        IncomingOrders incomingOrders = response.body();
                        if (incomingOrders != null && incomingOrders.getOrders() != null && incomingOrders.getOrders().size() > 0) {
                            llNoRecords.setVisibility(View.GONE);
                            upcomingRv.setVisibility(View.VISIBLE);
                            orderList.addAll(incomingOrders.getOrders());
                            historyAdapter.setList(orderList);
                            historyAdapter.notifyDataSetChanged();
                        } else {
                            llNoRecords.setVisibility(View.VISIBLE);
                            upcomingRv.setVisibility(View.GONE);
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
            public void onFailure(Call<IncomingOrders> call, Throwable t) {
                HistoryActivity.dismissDialog();
                Utils.displayMessage(activity, getString(R.string.something_went_wrong));
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
