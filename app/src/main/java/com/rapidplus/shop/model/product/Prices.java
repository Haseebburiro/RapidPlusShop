package com.rapidplus.shop.model.product;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Prices implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Prices> CREATOR = new Parcelable.Creator<Prices>() {
        @Override
        public Prices createFromParcel(Parcel in) {
            return new Prices(in);
        }

        @Override
        public Prices[] newArray(int size) {
            return new Prices[size];
        }
    };
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
    private Double orignalPrice;

    protected Prices(Parcel in) {
        price = in.readInt();
        discount = in.readInt();
        currency = in.readString();
        id = in.readInt();
        discountType = in.readString();
        orignalPrice = in.readDouble();
    }

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

    public Double getOrignalPrice() {
        return orignalPrice;
    }

    public void setOrignalPrice(Double orignalPrice) {
        this.orignalPrice = orignalPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(price);
        dest.writeInt(discount);
        dest.writeString(currency);
        dest.writeInt(id);
        dest.writeString(discountType);
        dest.writeDouble(orignalPrice);
    }
}