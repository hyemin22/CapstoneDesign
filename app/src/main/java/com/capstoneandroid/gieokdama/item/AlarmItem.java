package com.capstoneandroid.gieokdama.item;

import android.content.Context;

public class AlarmItem {
    String emoji, title, created_at;
    String sender_name, anonymous_name, receiver_name, content;

    public AlarmItem(Context context, String emoji, String title, String created_at) {
        this.emoji = emoji;
        this.title = title;
        this.created_at = created_at;
    }

    public String getEmoji() {
        return emoji;
    }

    public String getTitle() {
        return title;
    }

    public String getCreated_at() {
        return created_at;
    }
}
