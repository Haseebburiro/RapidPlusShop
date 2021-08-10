package com.rapidplus.shop.model;

/**
 * Created by Tamil on 1/2/2018.
 */

public class House {

    String addressHeader;
    String address;
    String status;
    String space;
    public House(String addressHeader, String address, String status, String space) {
        this.addressHeader = addressHeader;
        this.address = address;
        this.status = status;
        this.space = space;
    }

    public String getAddressHeader() {
        return addressHeader;
    }

    public void setAddressHeader(String addressHeader) {
        this.addressHeader = addressHeader;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }


}
