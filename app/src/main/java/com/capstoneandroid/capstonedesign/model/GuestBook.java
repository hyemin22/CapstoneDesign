package com.capstoneandroid.capstonedesign.model;

public class GuestBook {
    private Long writerId;
    private String content;

    public GuestBook(Long writerId, String content) {
        this.writerId = writerId;
        this.content = content;
    }

    public Long getWriterId() {
        return writerId;
    }

    public String getContent() {
        return content;
    }
}
