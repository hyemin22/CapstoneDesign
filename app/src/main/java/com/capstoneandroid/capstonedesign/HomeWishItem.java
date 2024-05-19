package com.capstoneandroid.capstonedesign;

import android.content.Context;

public class HomeWishItem {
    String title;
    String date;
    String dday;
    int backgroundTint;
    int textColor;

    public HomeWishItem(Context context, String title, String date, String dday, int backgroundTint, int textColor) {
        this.title = title;
        this.date = date;
        this.dday = dday;
        this.backgroundTint = context.getResources().getColor(backgroundTint);
        this.textColor = context.getResources().getColor(textColor);
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
    public int getBackgroundTint() {
        return backgroundTint;
    }
    public int getTextColor() {
        return textColor;
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
    public void setBackgroundTint(int color) {
        this.backgroundTint = color;
    }
    public void setTextColor(int color) {
        this.textColor = color;
    }
}