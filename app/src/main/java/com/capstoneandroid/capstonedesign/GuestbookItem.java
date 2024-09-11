package com.capstoneandroid.capstonedesign;

public class GuestbookItem {
    int profileImage;
    String message;
    String username;

    public GuestbookItem(int profileImage, String message, String username) {
        this.profileImage = profileImage;
        this.message = message;
        this.username = username;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}