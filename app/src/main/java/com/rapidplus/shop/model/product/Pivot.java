package com.rapidplus.shop.model.product;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Pivot implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Pivot> CREATOR = new Parcelable.Creator<Pivot>() {
        @Override
        public Pivot createFromParcel(Parcel in) {
            return new Pivot(in);
        }

        @Override
        public Pivot[] newArray(int size) {
            return new Pivot[size];
        }
    };
    @SerializedName("category_id")
    private int categoryId;
    @SerializedName("product_id")
    private int productId;

    protected Pivot(Parcel in) {
        categoryId = in.readInt();
        productId = in.readInt();
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(categoryId);
        dest.writeInt(productId);
    }
}