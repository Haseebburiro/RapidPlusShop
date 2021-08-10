package com.rapidplus.shop.model;

/**
 * Created by Tamil on 3/16/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartAddon {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_cart_id")
    @Expose
    private Integer userCartId;
    @SerializedName("addon_product_id")
    @Expose
    private Integer addonProductId;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("addon_product")
    @Expose
    private AddonProduct addonProduct;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserCartId() {
        return userCartId;
    }

    public void setUserCartId(Integer userCartId) {
        this.userCartId = userCartId;
    }

    public Integer getAddonProductId() {
        return addonProductId;
    }

    public void setAddonProductId(Integer addonProductId) {
        this.addonProductId = addonProductId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public AddonProduct getAddonProduct() {
        return addonProduct;
    }

    public void setAddonProduct(AddonProduct addonProduct) {
        this.addonProduct = addonProduct;
    }

}
