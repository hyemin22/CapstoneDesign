package com.capstoneandroid.capstonedesign.item;

public class DiaryLikeItem {
    Integer type;
    Long count;

    public DiaryLikeItem(Integer type, Long count) {
        this.type = type;
        this.count = count;
    }

    public Integer getType() {
        return type;
    }

    public Long getCount() {
        return count;
    }
}
