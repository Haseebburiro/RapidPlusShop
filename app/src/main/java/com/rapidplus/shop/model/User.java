package com.rapidplus.shop.model;


import com.google.gson.annotations.SerializedName;


public class User {

    @SerializedName("stripe_cust_id")
    private Object stripeCustId;

    @SerializedName("device_id")
    private String deviceId;

    @SerializedName("wallet_balance")
    private int walletBalance;

    @SerializedName("device_type")
    private String deviceType;

    @SerializedName("otp")
    private String otp;

    @SerializedName("avatar")
    private Object avatar;

    @SerializedName("phone")
    private String phone;

    @SerializedName("social_unique_id")
    private String socialUniqueId;

    @SerializedName("device_token")
    private String deviceToken;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("login_by")
    private String loginBy;

    @SerializedName("email")
    private String email;

    @SerializedName("braintree_id")
    private Object braintreeId;

    public Object getStripeCustId() {
        return stripeCustId;
    }

    public void setStripeCustId(Object stripeCustId) {
        this.stripeCustId = stripeCustId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(int walletBalance) {
        this.walletBalance = walletBalance;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Object getAvatar() {
        return avatar;
    }

    public void setAvatar(Object avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSocialUniqueId() {
        return socialUniqueId;
    }

    public void setSocialUniqueId(String socialUniqueId) {
        this.socialUniqueId = socialUniqueId;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginBy() {
        return loginBy;
    }

    public void setLoginBy(String loginBy) {
        this.loginBy = loginBy;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getBraintreeId() {
        return braintreeId;
    }

    public void setBraintreeId(Object braintreeId) {
        this.braintreeId = braintreeId;
    }
}