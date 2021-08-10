package com.rapidplus.shop.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CartItem {

    @SerializedName("note")
    private Object note;

    @SerializedName("product")
    private Product product;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("savedforlater")
    private int savedforlater;

    @SerializedName("price")
    private int price;

    @SerializedName("product_id")
    private int productId;

    @SerializedName("id")
    private int id;

    @SerializedName("order_id")
    private int orderId;

    @SerializedName("promocode_id")
    private Object promocodeId;

    @SerializedName("cart_addons")
    private List<Object> cartAddons;

    public Object getNote() {
        return note;
    }

    public void setNote(Object note) {
        this.note = note;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSavedforlater() {
        return savedforlater;
    }

    public void setSavedforlater(int savedforlater) {
        this.savedforlater = savedforlater;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Object getPromocodeId() {
        return promocodeId;
    }

    public void setPromocodeId(Object promocodeId) {
        this.promocodeId = promocodeId;
    }

    public List<Object> getCartAddons() {
        return cartAddons;
    }

    public void setCartAddons(List<Object> cartAddons) {
        this.cartAddons = cartAddons;
    }
}