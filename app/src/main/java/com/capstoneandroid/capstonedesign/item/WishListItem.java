package com.capstoneandroid.capstonedesign.item;

import android.content.Context;

public class WishListItem {
    Long id;
    String title, startDate, endDate, emoji, memo, completedDate, dday;
    Integer category;
    boolean alarm;

    public WishListItem(Context context, Long id, String title,
                        String startDate, String endDate,
                        Integer category, String emoji, boolean alarm,
                        String memo, String completedDate, String dday) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.emoji = emoji;
        this.alarm = alarm;
        this.memo = memo;
        this.completedDate = completedDate;
        this.dday = dday;
    }

    public WishListItem(Long id, String title, String startDate,
                        String endDate, String emoji, String memo,
                        Integer category, boolean alarm) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.emoji = emoji;
        this.memo = memo;
        this.category = category;
        this.alarm = alarm;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getEmoji() {
        return emoji;
    }

    public String getMemo() {
        return memo;
    }

    public String getCompletedDate() {
        return completedDate;
    }

    public String getDday() {
        return dday;
    }

    public Integer getCategory() {
        return category;
    }

    public boolean getAlarm() {
        return alarm;
    }
}