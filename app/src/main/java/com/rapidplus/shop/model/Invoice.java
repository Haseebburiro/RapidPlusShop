package com.rapidplus.shop.model;


import com.google.gson.annotations.SerializedName;


public class Invoice {

    @SerializedName("payment_mode")
    private String paymentMode;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("gross")
    private Double gross;

    @SerializedName("discount")
    private Double discount;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("tax")
    private Double tax;

    @SerializedName("total_pay")
    private Double totalPay;

    @SerializedName("ripple_price")
    private String ripplePrice;

    @SerializedName("delivery_charge")
    private Double deliveryCharge;

    @SerializedName("SGST")
    private double sGST;

    @SerializedName("payable")
    private Double payable;

    @SerializedName("payment_id")
    private String paymentId;

    @SerializedName("paid")
    private Double paid;

    @SerializedName("tender_pay")
    private Double tenderPay;

    @SerializedName("CGST")
    private double cGST;

    @SerializedName("id")
    private int id;

    @SerializedName("net")
    private Double net;

    @SerializedName("order_id")
    private int orderId;

    @SerializedName("wallet_amount")
    private Double walletAmount;

    @SerializedName("status")
    private String status;

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getGross() {
        return gross;
    }

    public void setGross(Double gross) {
        this.gross = gross;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(Double totalPay) {
        this.totalPay = totalPay;
    }

    public String getRipplePrice() {
        return ripplePrice;
    }

    public void setRipplePrice(String ripplePrice) {
        this.ripplePrice = ripplePrice;
    }

    public Double getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(Double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public double getSGST() {
        return sGST;
    }

    public void setSGST(double sGST) {
        this.sGST = sGST;
    }

    public Double getPayable() {
        return payable;
    }

    public void setPayable(Double payable) {
        this.payable = payable;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Double getPaid() {
        return paid;
    }

    public void setPaid(Double paid) {
        this.paid = paid;
    }

    public Double getTenderPay() {
        return tenderPay;
    }

    public void setTenderPay(Double tenderPay) {
        this.tenderPay = tenderPay;
    }

    public double getCGST() {
        return cGST;
    }

    public void setCGST(double cGST) {
        this.cGST = cGST;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getNet() {
        return net;
    }

    public void setNet(Double net) {
        this.net = net;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Double getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(Double walletAmount) {
        this.walletAmount = walletAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}