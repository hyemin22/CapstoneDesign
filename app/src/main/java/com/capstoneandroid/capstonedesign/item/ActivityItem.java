package com.capstoneandroid.capstonedesign.item;

import android.content.Context;

public class ActivityItem {
    Integer profile, image1, image2, image3;
    String title, type, num, tag1, tag2, tag3;
    private boolean isHeartFilled;

    public ActivityItem(Context context, Integer profile, String title,
                        String type, String num,
                        Integer image1, Integer image2, Integer image3,
                        String tag1, String tag2, String tag3) {
        this.profile = profile;
        this.title = title;
        this.type = type;
        this.num = num;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.tag3 = tag3;
        this.isHeartFilled = false;
    }

    public Integer getProfile() {
        return profile;
    }
    public String getTitle() {
        return title;
    }
    public String getType() {
        return type;
    }
    public String getNum() {
        return num;
    }
    public Integer getImage1() {
        return image1;
    }
    public Integer getImage2() {
        return image2;
    }
    public Integer getImage3() {
        return image3;
    }
    public String getTag1() {
        return tag1;
    }
    public String getTag2() {
        return tag2;
    }
    public String getTag3() {
        return tag3;
    }

    public boolean isHeartFilled() {
        return isHeartFilled;
    }
    public void setProfile(Integer profile) {
        this.profile = profile;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setNum(String num) {
        this.num = num;
    }
    public void setImage1(Integer image1) {
        this.image1 = image1;
    }
    public void setImage2(Integer image2) {
        this.image2 = image2;
    }
    public void setImage3(Integer image3) {
        this.image3 = image3;
    }
    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }
    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }
    public void setTag3(String tag3) {
        this.tag3 = tag3;
    }

    public void setHeartFilled(boolean heartFilled) {
        isHeartFilled = heartFilled;
    }
}
