package com.capstoneandroid.gieokdama.model;

public class WishCategory {
    private Long userId;
    private String name;

    public WishCategory(Long userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
