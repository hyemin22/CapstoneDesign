package com.capstoneandroid.capstonedesign;

public class HomeWishItem {
    String title;
    String date;
    String dday;

    public HomeWishItem(String title, String date, String dday) {
        this.title = title;
        this.date = date;
        this.dday = dday;
    }

    public String getTitle() {
        return title;
    }
    public String getDate() {
        return date;
    }
    public String getDday() {
        return dday;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setDday(String dday) {
        this.dday = dday;
    }
}