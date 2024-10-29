package com.capstoneandroid.capstonedesign.item;

import android.content.Context;

public class WishExpectedItem {
    String emoji, title, dday, date;

    public WishExpectedItem(Context context, String emoji, String title, String dday, String date) {
        this.emoji = emoji;
        this.title = title;
        this.dday = dday;
        this.date = date;
    }

    public WishExpectedItem(String emoji, String title, String date) {
        this.emoji = emoji;
        this.title = title;
        this.date = date;
    }

    public String getEmoji() {
        return emoji;
    }
    public String getTitle() {
        return title;
    }
    public String getDday() {
        return dday;
    }
    public String getDate() {
        return date;
    }
    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDday(String dday) {
        this.dday = dday;
    }
    public void setDate(String date) {
        this.date = date;
    }
}