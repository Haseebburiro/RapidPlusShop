package com.rapidplus.shop.messages;

import java.io.File;

public class ProductMessage {
    private String strProductName = "";
    private String strProductDescription = "";
    private String strProductStatus = "";
    private String strProductOrder = "0";
    private String strProductCategory = "";
    private File productImageFile;
    private File featuredImageFile;
    private String strCuisineId = "";

    private String strSelectedFoodType = "";

    public String getStrCuisineId() {
        return strCuisineId;
    }

    public void setStrCuisineId(String strCuisineId) {
        this.strCuisineId = strCuisineId;
    }

    public String getStrProductName() {
        return strProductName;
    }

    public void setStrProductName(String strProductName) {
        this.strProductName = strProductName;
    }

    public String getStrProductDescription() {
        return strProductDescription;
    }

    public void setStrProductDescription(String strProductDescription) {
        this.strProductDescription = strProductDescription;
    }

    public String getStrProductStatus() {
        return strProductStatus;
    }

    public void setStrProductStatus(String strProductStatus) {
        this.strProductStatus = strProductStatus;
    }

    public String getStrProductOrder() {
        return strProductOrder;
    }

    public void setStrProductOrder(String strProductOrder) {
        this.strProductOrder = strProductOrder;
    }

    public String getStrProductCategory() {
        return strProductCategory;
    }

    public void setStrProductCategory(String strProductCategory) {
        this.strProductCategory = strProductCategory;
    }

    public File getProductImageFile() {
        return productImageFile;
    }

    public void setProductImageFile(File productImageFile) {
        this.productImageFile = productImageFile;
    }

    public File getFeaturedImageFile() {
        return featuredImageFile;
    }

    public void setFeaturedImageFile(File featuredImageFile) {
        this.featuredImageFile = featuredImageFile;
    }

    public String getStrSelectedFoodType() {
        return strSelectedFoodType;
    }

    public void setStrSelectedFoodType(String strSelectedFoodType) {
        this.strSelectedFoodType = strSelectedFoodType;
    }

}
