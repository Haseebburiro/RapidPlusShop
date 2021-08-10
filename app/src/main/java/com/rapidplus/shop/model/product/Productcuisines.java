package com.rapidplus.shop.model.product;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Productcuisines implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Productcuisines> CREATOR = new Parcelable.Creator<Productcuisines>() {
        @Override
        public Productcuisines createFromParcel(Parcel in) {
            return new Productcuisines(in);
        }

        @Override
        public Productcuisines[] newArray(int size) {
            return new Productcuisines[size];
        }
    };
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private int id;

    protected Productcuisines(Parcel in) {
        name = in.readString();
        id = in.readInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
    }
}