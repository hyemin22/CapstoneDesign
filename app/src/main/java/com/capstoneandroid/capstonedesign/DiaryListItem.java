package com.capstoneandroid.capstonedesign;

import android.content.Context;

import java.util.List;

public class DiaryListItem {

    List<Integer> imageIds;
    String title, date;

    public DiaryListItem(Context context, List<Integer> imageIds, String title, String date) {
        this.imageIds = imageIds;
        this.title = title;
        this.date = date;
    }

    public List<Integer> getImageIds() {
        return imageIds;
    }
    public String getTitle() {
        return title;
    }
    public String getDate() {
        return date;
    }

    public void setImageIds(List<Integer> imageIds) {
        this.imageIds = imageIds;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
