package com.rapidplus.shop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RevenueResponse {

    @SerializedName("TotalRevenue")
    @Expose
    private Double totalRevenue;
    @SerializedName("OrderReceivedToday")
    @Expose
    private Integer orderReceivedToday;
    @SerializedName("OrderDeliveredToday")
    @Expose
    private Integer orderDeliveredToday;
    @SerializedName("OrderIncomeMonthly")
    @Expose
    private Double orderIncomeMonthly;
    @SerializedName("OrderIncomeToday")
    @Expose
    private Double orderIncomeToday;
    @SerializedName("complete_cancel")
    @Expose
    private List<CompleteCancel> completeCancel = null;

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Integer getOrderReceivedToday() {
        return orderReceivedToday;
    }

    public void setOrderReceivedToday(Integer orderReceivedToday) {
        this.orderReceivedToday = orderReceivedToday;
    }

    public Integer getOrderDeliveredToday() {
        return orderDeliveredToday;
    }

    public void setOrderDeliveredToday(Integer orderDeliveredToday) {
        this.orderDeliveredToday = orderDeliveredToday;
    }

    public Double getOrderIncomeMonthly() {
        return orderIncomeMonthly;
    }

    public void setOrderIncomeMonthly(Double orderIncomeMonthly) {
        this.orderIncomeMonthly = orderIncomeMonthly;
    }

    public Double getOrderIncomeToday() {
        return orderIncomeToday;
    }

    public void setOrderIncomeToday(Double orderIncomeToday) {
        this.orderIncomeToday = orderIncomeToday;
    }

    public List<CompleteCancel> getCompleteCancel() {
        return completeCancel;
    }

    public void setCompleteCancel(List<CompleteCancel> completeCancel) {
        this.completeCancel = completeCancel;
    }

}
