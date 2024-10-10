package com.capstoneandroid.capstonedesign.item;

import android.content.Context;

public class AlarmItem {
    String emoji;
    String title;
    String date;

    public AlarmItem(Context context, String emoji, String title, String date) {
        this.emoji = emoji;
        this.title = title;
        this.date = date;
    }

    public String getEmoji() {return emoji;}
    public String getTitle() {return title;}
    public String getDate() {return date;}

    public void setEmoji(String emoji) {this.emoji=emoji;}
    public void setTitle(String title) {this.title=title;}
    public void setDate(String date) {this.date=date;}
}
