package com.rapidplus.shop.model.ordernew;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class DisputesItem {

    @SerializedName("description")
    private String description;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("type")
    private String type;

    @SerializedName("created_by")
    private String createdBy;

    @SerializedName("disputecomment")
    private List<Object> disputecomment;

    @SerializedName("shop_id")
    private int shopId;

    @SerializedName("created_to")
    private String createdTo;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("id")
    private int id;

    @SerializedName("order_id")
    private int orderId;

    @SerializedName("order_disputehelp_id")
    private int orderDisputehelpId;

    @SerializedName("dispute_help")
    private Object disputeHelp;

    @SerializedName("transporter_id")
    private int transporterId;

    @SerializedName("status")
    private String status;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public List<Object> getDisputecomment() {
        return disputecomment;
    }

    public void setDisputecomment(List<Object> disputecomment) {
        this.disputecomment = disputecomment;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getCreatedTo() {
        return createdTo;
    }

    public void setCreatedTo(String createdTo) {
        this.createdTo = createdTo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderDisputehelpId() {
        return orderDisputehelpId;
    }

    public void setOrderDisputehelpId(int orderDisputehelpId) {
        this.orderDisputehelpId = orderDisputehelpId;
    }

    public Object getDisputeHelp() {
        return disputeHelp;
    }

    public void setDisputeHelp(Object disputeHelp) {
        this.disputeHelp = disputeHelp;
    }

    public int getTransporterId() {
        return transporterId;
    }

    public void setTransporterId(int transporterId) {
        this.transporterId = transporterId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}