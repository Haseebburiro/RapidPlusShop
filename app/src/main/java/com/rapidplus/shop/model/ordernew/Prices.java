package com.rapidplus.shop.model.ordernew;


import com.google.gson.annotations.SerializedName;


public class Prices {

    @SerializedName("price")
    private int price;

    @SerializedName("discount")
    private int discount;

    @SerializedName("currency")
    private String currency;

    @SerializedName("id")
    private int id;

    @SerializedName("discount_type")
    private String discountType;

    @SerializedName("orignal_price")
    private int orignalPrice;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public int getOrignalPrice() {
        return orignalPrice;
    }

    public void setOrignalPrice(int orignalPrice) {
        this.orignalPrice = orignalPrice;
    }
}