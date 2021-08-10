package com.rapidplus.shop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompleteCancel {

    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("shop_id")
    @Expose
    private String shopId;
    @SerializedName("delivered")
    @Expose
    private String delivered;
    @SerializedName("cancelled")
    @Expose
    private String cancelled;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getDelivered() {
        return delivered;
    }

    public void setDelivered(String delivered) {
        this.delivered = delivered;
    }

    public String getCancelled() {
        return cancelled;
    }

    public void setCancelled(String cancelled) {
        this.cancelled = cancelled;
    }

}
