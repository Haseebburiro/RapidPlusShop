package com.rapidplus.shop.model.ordernew;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class OrderResponse {

    @SerializedName("Order")
    private Order order;

    @SerializedName("Cart")
    private List<CartItem> cart;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<CartItem> getCart() {
        return cart;
    }

    public void setCart(List<CartItem> cart) {
        this.cart = cart;
    }
}