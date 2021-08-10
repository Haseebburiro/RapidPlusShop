package com.rapidplus.shop.model;

/**
 * Created by Tamil on 3/16/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Dispute {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("order_disputehelp_id")
    @Expose
    private Integer orderDisputehelpId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("transporter_id")
    @Expose
    private Integer transporterId;
    @SerializedName("shop_id")
    @Expose
    private Integer shopId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("created_to")
    @Expose
    private String createdTo;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("dispute_help")
    @Expose
    private Object disputeHelp;
    @SerializedName("disputecomment")
    @Expose
    private List<Object> disputecomment = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderDisputehelpId() {
        return orderDisputehelpId;
    }

    public void setOrderDisputehelpId(Integer orderDisputehelpId) {
        this.orderDisputehelpId = orderDisputehelpId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTransporterId() {
        return transporterId;
    }

    public void setTransporterId(Integer transporterId) {
        this.transporterId = transporterId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
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

    public String getCreatedTo() {
        return createdTo;
    }

    public void setCreatedTo(String createdTo) {
        this.createdTo = createdTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public Object getDisputeHelp() {
        return disputeHelp;
    }

    public void setDisputeHelp(Object disputeHelp) {
        this.disputeHelp = disputeHelp;
    }

    public List<Object> getDisputecomment() {
        return disputecomment;
    }

    public void setDisputecomment(List<Object> disputecomment) {
        this.disputecomment = disputecomment;
    }

}
