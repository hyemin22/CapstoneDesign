package com.capstoneandroid.gieokdama.model;

public class Post {
    private Long senderId;
    private String anonymousSender;
    private Long receiverId;
    private String content;

    public Post(Long senderId, String anonymousSender, Long receiverId, String content) {
        this.senderId = senderId;
        this.anonymousSender = anonymousSender;
        this.receiverId = receiverId;
        this.content = content;
    }

    public Long getSenderId() {
        return senderId;
    }

    public String getAnonymousSender() {
        return anonymousSender;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public String getContent() {
        return content;
    }
}
