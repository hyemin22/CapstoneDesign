package com.capstoneandroid.capstonedesign.model;

public class DiaryNum {
    String date;
    Long count;

    public DiaryNum(String date, Long count) {
        this.date = date;
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public Long getCount() {
        return count;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
