package com.rapidplus.shop.model.ordernew;


import com.google.gson.annotations.SerializedName;


public class Reviewrating {

    @SerializedName("shop_id")
    private Object shopId;

    @SerializedName("shop_rating")
    private Object shopRating;

    @SerializedName("shop_comment")
    private Object shopComment;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("transporter_comment")
    private Object transporterComment;

    @SerializedName("transporter_rating")
    private int transporterRating;

    @SerializedName("id")
    private int id;

    @SerializedName("order_id")
    private int orderId;

    @SerializedName("user_comment")
    private Object userComment;

    @SerializedName("transporter_id")
    private int transporterId;

    @SerializedName("user_rating")
    private int userRating;

    public Object getShopId() {
        return shopId;
    }

    public void setShopId(Object shopId) {
        this.shopId = shopId;
    }

    public Object getShopRating() {
        return shopRating;
    }

    public void setShopRating(Object shopRating) {
        this.shopRating = shopRating;
    }

    public Object getShopComment() {
        return shopComment;
    }

    public void setShopComment(Object shopComment) {
        this.shopComment = shopComment;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Object getTransporterComment() {
        return transporterComment;
    }

    public void setTransporterComment(Object transporterComment) {
        this.transporterComment = transporterComment;
    }

    public int getTransporterRating() {
        return transporterRating;
    }

    public void setTransporterRating(int transporterRating) {
        this.transporterRating = transporterRating;
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

    public Object getUserComment() {
        return userComment;
    }

    public void setUserComment(Object userComment) {
        this.userComment = userComment;
    }

    public int getTransporterId() {
        return transporterId;
    }

    public void setTransporterId(int transporterId) {
        this.transporterId = transporterId;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }
}