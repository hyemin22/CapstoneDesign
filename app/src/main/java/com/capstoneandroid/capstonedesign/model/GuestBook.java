package com.capstoneandroid.capstonedesign.model;

public class GuestBook {
    private Long id;
    private String content;

    public GuestBook(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
