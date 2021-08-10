package com.rapidplus.shop.model;

/**
 * Created by Tamil on 3/16/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IncomingOrders {

    @SerializedName("Orders")
    @Expose
    private List<Order> orders = null;
    @SerializedName("tot_incom_resp")
    @Expose
    private Integer totIncomResp;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Integer getTotIncomResp() {
        return totIncomResp;
    }

    public void setTotIncomResp(Integer totIncomResp) {
        this.totIncomResp = totIncomResp;
    }

}
