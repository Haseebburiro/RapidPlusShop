package com.rapidplus.shop.model;

/**
 * Created by Tamil on 3/16/2018.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Addon implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Addon> CREATOR = new Parcelable.Creator<Addon>() {
        @Override
        public Addon createFromParcel(Parcel in) {
            return new Addon(in);
        }

        @Override
        public Addon[] newArray(int size) {
            return new Addon[size];
        }
    };
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("shop_id")
    @Expose
    private Integer shopId;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("addon")
    @Expose
    private Addon addon;
    private String price = "";
    private boolean isChecked;

    protected Addon(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        name = in.readString();
        shopId = in.readByte() == 0x00 ? null : in.readInt();
        deletedAt = (Object) in.readValue(Object.class.getClassLoader());
        price = in.readString();
        isChecked = in.readByte() != 0x00;
        addon = (Addon) in.readValue(Addon.class.getClassLoader());
    }

    public Addon getAddon() {
        return addon;
    }

    public void setAddon(Addon addon) {
        this.addon = addon;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getPrice() {
        if (price == null)
            price = "";
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        dest.writeString(name);
        if (shopId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(shopId);
        }
        dest.writeValue(deletedAt);
        dest.writeString(price);
        dest.writeByte((byte) (isChecked ? 0x01 : 0x00));
        dest.writeValue(addon);
    }

}
