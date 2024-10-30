package com.capstoneandroid.capstonedesign.item;

import android.content.Context;

public class WishCompletedItem {
    String emoji, title, date;

    public WishCompletedItem(Context context, String emoji, String title, String date) {
        this.emoji = emoji;
        this.title = title;
        this.date = date;
    }

    public WishCompletedItem(String emoji, String title, String date) {
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
    public String getDate() {
        return date;
    }
    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDate(String date) {
        this.date = date;
    }
}