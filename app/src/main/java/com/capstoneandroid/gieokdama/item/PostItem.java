package com.capstoneandroid.gieokdama.item;

import android.content.Context;

public class PostItem {
    String sender_name, anonymous_name, receiver_name, content, created_at;

    // 쪽지 가져올 때
    public PostItem(Context context, String sender_name, String anonymous_name,
                     String receiver_name, String content, String created_at) {
        this.sender_name = sender_name;
        this.anonymous_name = anonymous_name;
        this.receiver_name = receiver_name;
        this.content = content;
        this.created_at = created_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getSender_name() {
        return sender_name;
    }

    public String getAnonymous_name() {
        return anonymous_name;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public String getContent() {
        return content;
    }
}
