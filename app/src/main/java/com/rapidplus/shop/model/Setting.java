package com.rapidplus.shop.model;

/**
 * Created by Tamil on 1/2/2018.
 */

public class Setting {

    String title;
    Integer icon;

    public Setting(String title, Integer icon) {
        this.title = title;
        this.icon = icon;
    }


    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
