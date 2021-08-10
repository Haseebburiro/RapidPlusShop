package com.rapidplus.shop.model.ordernew;


import com.google.gson.annotations.SerializedName;


public class Shop {

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("rating")
    private int rating;

    @SerializedName("description")
    private String description;

    @SerializedName("created_at")
    private Object createdAt;

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
    private Object defaultBanner;

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
    private Object deletedAt;

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

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
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

    public Object getDefaultBanner() {
        return defaultBanner;
    }

    public void setDefaultBanner(Object defaultBanner) {
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

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
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
}