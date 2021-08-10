package com.rapidplus.shop.messages;

public class FilterDialogFragmentMessage {
    private int delieveryPersonId;
    private String fromDate;
    private String toDate;

    private int selectePos;
    private String formattedFromDate;
    private String formattedToDate;


    public int getSelectePos() {
        return selectePos;
    }

    public void setSelectePos(int selectePos) {
        this.selectePos = selectePos;
    }

    public String getFormattedFromDate() {
        return formattedFromDate;
    }

    public void setFormattedFromDate(String formattedFromDate) {
        this.formattedFromDate = formattedFromDate;
    }

    public String getFormattedToDate() {
        return formattedToDate;
    }

    public void setFormattedToDate(String formattedToDate) {
        this.formattedToDate = formattedToDate;
    }

    public int getDelieveryPersonId() {
        return delieveryPersonId;
    }

    public void setDelieveryPersonId(int delieveryPersonId) {
        this.delieveryPersonId = delieveryPersonId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public boolean isEmpty() {
        return (delieveryPersonId == 0 && fromDate.isEmpty() && toDate.isEmpty());
    }

    public void clear() {
        delieveryPersonId = 0;
        fromDate = "";
        toDate = "";
        selectePos = 0;
        formattedFromDate = "";
        formattedToDate = "";
    }
}
