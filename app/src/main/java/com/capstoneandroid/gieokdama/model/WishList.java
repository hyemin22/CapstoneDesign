package com.capstoneandroid.gieokdama.model;

public class WishList {
    private Long userId;
    private String title;
    private String startDate;
    private String endDate;
    private Integer category;
    private String emoji;
    private Boolean alarm;
    private String memo;
    private Boolean completed;

    public WishList(Long userId, String title, String startDate,
                    String endDate, Integer category, String emoji, Boolean alarm,
                    String memo, Boolean completed) {
        this.userId = userId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.emoji = emoji;
        this.alarm = alarm;
        this.memo = memo;
        this.completed = completed;
    }
}
