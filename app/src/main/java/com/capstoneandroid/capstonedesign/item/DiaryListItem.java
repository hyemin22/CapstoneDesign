package com.capstoneandroid.capstonedesign.item;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class DiaryListItem {
    Long id;
    String title, diary_date, content, address, album_title, user_character, user_nickname;
    String photo1, photo2, photo3, photo4, photo5, photo6, photo7, photo8, photo9, photo10;
    Double latitude, longitude;

    public DiaryListItem(Context context, Long id, String title, String diary_date,
                         String photo1, String photo2, String photo3, String photo4,
                         String photo5, String photo6, String photo7, String photo8,
                         String photo9, String photo10,
                         String content, String address,
                         Double latitude, Double longitude, String album_title,
                         String user_character, String user_nickname) {
        this.id = id;
        this.title = title;
        this.diary_date = diary_date;
        this.photo1 = photo1;
        this.photo2 = photo2;
        this.photo3 = photo3;
        this.photo4 = photo4;
        this.photo5 = photo5;
        this.photo6 = photo6;
        this.photo7 = photo7;
        this.photo8 = photo8;
        this.photo9 = photo9;
        this.photo10 = photo10;
        this.content = content;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.album_title = album_title;
        this.user_character = user_character;
        this.user_nickname = user_nickname;
    }

    public DiaryListItem(Context context, Long id, Double latitude, Double longitude, String photo1) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photo1 = photo1;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDiary_date() {
        return diary_date;
    }

    public String getContent() {
        return content;
    }

    public String getAddress() {
        return address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getAlbum_title() {
        return album_title;
    }

    public String getUser_character() {
        return user_character;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public String getPhoto1() {
        return photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public String getPhoto3() {
        return photo3;
    }

    public String getPhoto4() {
        return photo4;
    }

    public String getPhoto5() {
        return photo5;
    }

    public String getPhoto6() {
        return photo6;
    }

    public String getPhoto7() {
        return photo7;
    }

    public String getPhoto8() {
        return photo8;
    }

    public String getPhoto9() {
        return photo9;
    }

    public String getPhoto10() {
        return photo10;
    }

    // 이미지 경로 저장한 리스트 반환
    public List<String> getImagePaths() {
        List<String> imageIds = new ArrayList<>();
        if (photo1 != null) imageIds.add(photo1);
        if (photo2 != null) imageIds.add(photo2);
        if (photo3 != null) imageIds.add(photo3);
        if (photo4 != null) imageIds.add(photo4);
        if (photo5 != null) imageIds.add(photo5);
        if (photo6 != null) imageIds.add(photo6);
        if (photo7 != null) imageIds.add(photo7);
        if (photo8 != null) imageIds.add(photo8);
        if (photo9 != null) imageIds.add(photo9);
        if (photo10 != null) imageIds.add(photo10);
        return imageIds;
    }
}
