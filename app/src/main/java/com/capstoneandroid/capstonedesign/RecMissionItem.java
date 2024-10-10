package com.capstoneandroid.capstonedesign;

public class RecMissionItem {
    String icon, title, ment;

    public RecMissionItem(String icon, String title, String ment) {
        this.icon = icon;
        this.title = title;
        this.ment = ment;
    }

    public String getIcon() {
        return icon;
    }
    public String getTitle() {
        return title;
    }
    public String getMent() {
        return ment;
    }
    public void setIcon() {
        this.icon = icon;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setMent(String ment) {
        this.ment = ment;
    }
}
