package com.capstoneandroid.capstonedesign.item;

import android.content.Context;

public class ActivityItem {
    Long id;
    Integer category, district_id;
    String title, profile, main_photo, type, review_count,
            address, phone_number, open_time, closed_day;
    boolean isHeartFilled = false; //서버에서 가져오기

    public boolean isHeartFilled() {
        return isHeartFilled;
    }

    public void setHeartFilled(boolean heartFilled) {
        isHeartFilled = heartFilled;
    }

    public ActivityItem(Context context, Long id, Integer category,
                        String title, String profile,
                        String main_photo, String type, String review_count,
                        String address, Integer district_id, String phone_number,
                        String open_time, String closed_day) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.profile = profile;
        this.main_photo = main_photo;
        this.type = type;
        this.review_count = review_count;
        this.address = address;
        this.district_id = district_id;
        this.phone_number = phone_number;
        this.open_time = open_time;
        this.closed_day = closed_day;
    }

    public Long getId() {
        return id;
    }

    public Integer getCategory() {
        return category;
    }

    public Integer getDistrict_id() {
        return district_id;
    }

    public String getTitle() {
        return title;
    }

    public String getProfile() {
        return profile;
    }

    public String getMain_photo() {
        return main_photo;
    }

    public String getType() {
        return type;
    }

    public String getReview_count() {
        return review_count;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getOpen_time() {
        return open_time;
    }

    public String getClosed_day() {
        return closed_day;
    }

}