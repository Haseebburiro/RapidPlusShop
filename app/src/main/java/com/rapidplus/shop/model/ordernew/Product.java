package com.rapidplus.shop.model.ordernew;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Product {

    @SerializedName("featured")
    private int featured;

    @SerializedName("images")
    private List<Object> images;

    @SerializedName("max_quantity")
    private int maxQuantity;

    @SerializedName("description")
    private String description;

    @SerializedName("cuisine_id")
    private int cuisineId;

    @SerializedName("avalability")
    private int avalability;

    @SerializedName("food_type")
    private String foodType;

    @SerializedName("shop_id")
    private int shopId;

    @SerializedName("addon_status")
    private int addonStatus;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("position")
    private int position;

    @SerializedName("prices")
    private Prices prices;

    @SerializedName("status")
    private String status;

    public int getFeatured() {
        return featured;
    }

    public void setFeatured(int featured) {
        this.featured = featured;
    }

    public List<Object> getImages() {
        return images;
    }

    public void setImages(List<Object> images) {
        this.images = images;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCuisineId() {
        return cuisineId;
    }

    public void setCuisineId(int cuisineId) {
        this.cuisineId = cuisineId;
    }

    public int getAvalability() {
        return avalability;
    }

    public void setAvalability(int avalability) {
        this.avalability = avalability;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getAddonStatus() {
        return addonStatus;
    }

    public void setAddonStatus(int addonStatus) {
        this.addonStatus = addonStatus;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Prices getPrices() {
        return prices;
    }

    public void setPrices(Prices prices) {
        this.prices = prices;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}