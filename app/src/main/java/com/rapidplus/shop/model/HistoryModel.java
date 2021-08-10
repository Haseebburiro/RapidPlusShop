package com.rapidplus.shop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryModel {

    @Expose
    @SerializedName("COMPLETED")
    private List<Order> COMPLETED;
    @Expose
    @SerializedName("CANCELLED")
    private List<Order> CANCELLED;

    public List<Order> getCOMPLETED() {
        return COMPLETED;
    }

    public void setCOMPLETED(List<Order> COMPLETED) {
        this.COMPLETED = COMPLETED;
    }

    public List<Order> getCANCELLED() {
        return CANCELLED;
    }

    public void setCANCELLED(List<Order> CANCELLED) {
        this.CANCELLED = CANCELLED;
    }
}
