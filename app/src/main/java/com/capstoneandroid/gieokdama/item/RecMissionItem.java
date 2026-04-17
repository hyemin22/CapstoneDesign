package com.capstoneandroid.gieokdama.item;

import android.content.Context;

public class RecMissionItem {
    String emoji, title, description;

    public RecMissionItem(Context context, String icon, String title, String description) {
        this.emoji = icon;
        this.title = title;
        this.description = description;
    }

    public String getEmoji() {
        return emoji;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
}
