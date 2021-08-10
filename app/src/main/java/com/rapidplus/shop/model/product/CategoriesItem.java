package com.rapidplus.shop.model.product;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class CategoriesItem implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<CategoriesItem> CREATOR = new Parcelable.Creator<CategoriesItem>() {
        @Override
        public CategoriesItem createFromParcel(Parcel in) {
            return new CategoriesItem(in);
        }

        @Override
        public CategoriesItem[] newArray(int size) {
            return new CategoriesItem[size];
        }
    };
    @SerializedName("shop_id")
    private int shopId;
    @SerializedName("parent_id")
    private int parentId;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("pivot")
    private Pivot pivot;
    @SerializedName("id")
    private int id;
    @SerializedName("position")
    private String position;
    @SerializedName("status")
    private String status;

    protected CategoriesItem(Parcel in) {
        shopId = in.readInt();
        parentId = in.readInt();
        name = in.readString();
        description = in.readString();
        pivot = (Pivot) in.readValue(Pivot.class.getClassLoader());
        id = in.readInt();
        position = in.readString();
        status = in.readString();
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Pivot getPivot() {
        return pivot;
    }

    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(shopId);
        dest.writeInt(parentId);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeValue(pivot);
        dest.writeInt(id);
        dest.writeString(position);
        dest.writeString(status);
    }
}