package com.rapidplus.shop.model.product;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Shop implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Shop> CREATOR = new Parcelable.Creator<Shop>() {
        @Override
        public Shop createFromParcel(Parcel in) {
            return new Shop(in);
        }

        @Override
        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("rating")
    private int rating;
    @SerializedName("description")
    private String description;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("device_type")
    private String deviceType;
    @SerializedName("pure_veg")
    private int pureVeg;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("estimated_delivery_time")
    private int estimatedDeliveryTime;
    @SerializedName("id")
    private int id;
    @SerializedName("default_banner")
    private String defaultBanner;
    @SerializedName("maps_address")
    private String mapsAddress;
    @SerializedName("popular")
    private int popular;
    @SerializedName("email")
    private String email;
    @SerializedName("offer_min_amount")
    private int offerMinAmount;
    @SerializedName("longitude")
    private double longitude;
    @SerializedName("offer_percent")
    private int offerPercent;
    @SerializedName("address")
    private String address;
    @SerializedName("device_id")
    private String deviceId;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("deleted_at")
    private String deletedAt;
    @SerializedName("cuisines")
    private List<CuisinesItem> cuisines;
    @SerializedName("rating_status")
    private int ratingStatus;
    @SerializedName("phone")
    private String phone;
    @SerializedName("device_token")
    private String deviceToken;
    @SerializedName("name")
    private String name;
    @SerializedName("status")
    private String status;

    protected Shop(Parcel in) {
        latitude = in.readDouble();
        rating = in.readInt();
        description = in.readString();
        createdAt = in.readString();
        deviceType = in.readString();
        pureVeg = in.readInt();
        updatedAt = in.readString();
        estimatedDeliveryTime = in.readInt();
        id = in.readInt();
        defaultBanner = in.readString();
        mapsAddress = in.readString();
        popular = in.readInt();
        email = in.readString();
        offerMinAmount = in.readInt();
        longitude = in.readDouble();
        offerPercent = in.readInt();
        address = in.readString();
        deviceId = in.readString();
        avatar = in.readString();
        deletedAt = in.readString();
        if (in.readByte() == 0x01) {
            cuisines = new ArrayList<CuisinesItem>();
            in.readList(cuisines, CuisinesItem.class.getClassLoader());
        } else {
            cuisines = null;
        }
        ratingStatus = in.readInt();
        phone = in.readString();
        deviceToken = in.readString();
        name = in.readString();
        status = in.readString();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public int getPureVeg() {
        return pureVeg;
    }

    public void setPureVeg(int pureVeg) {
        this.pureVeg = pureVeg;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(int estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDefaultBanner() {
        return defaultBanner;
    }

    public void setDefaultBanner(String defaultBanner) {
        this.defaultBanner = defaultBanner;
    }

    public String getMapsAddress() {
        return mapsAddress;
    }

    public void setMapsAddress(String mapsAddress) {
        this.mapsAddress = mapsAddress;
    }

    public int getPopular() {
        return popular;
    }

    public void setPopular(int popular) {
        this.popular = popular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getOfferMinAmount() {
        return offerMinAmount;
    }

    public void setOfferMinAmount(int offerMinAmount) {
        this.offerMinAmount = offerMinAmount;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getOfferPercent() {
        return offerPercent;
    }

    public void setOfferPercent(int offerPercent) {
        this.offerPercent = offerPercent;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public List<CuisinesItem> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<CuisinesItem> cuisines) {
        this.cuisines = cuisines;
    }

    public int getRatingStatus() {
        return ratingStatus;
    }

    public void setRatingStatus(int ratingStatus) {
        this.ratingStatus = ratingStatus;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        dest.writeDouble(latitude);
        dest.writeInt(rating);
        dest.writeString(description);
        dest.writeString(createdAt);
        dest.writeString(deviceType);
        dest.writeInt(pureVeg);
        dest.writeString(updatedAt);
        dest.writeInt(estimatedDeliveryTime);
        dest.writeInt(id);
        dest.writeString(defaultBanner);
        dest.writeString(mapsAddress);
        dest.writeInt(popular);
        dest.writeString(email);
        dest.writeInt(offerMinAmount);
        dest.writeDouble(longitude);
        dest.writeInt(offerPercent);
        dest.writeString(address);
        dest.writeString(deviceId);
        dest.writeString(avatar);
        dest.writeString(deletedAt);
        if (cuisines == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(cuisines);
        }
        dest.writeInt(ratingStatus);
        dest.writeString(phone);
        dest.writeString(deviceToken);
        dest.writeString(name);
        dest.writeString(status);
    }
}