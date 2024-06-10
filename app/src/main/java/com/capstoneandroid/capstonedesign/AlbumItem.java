package com.capstoneandroid.capstonedesign;

public class AlbumItem {

    String title;
    int emoji;
    int image1;
    int image2;
    int image3;
    String name;

    public AlbumItem(String title, int emoji, int image1, int image2, int image3, String name) {
        this.title = title;
        this.emoji = emoji;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public int getEmoji() {
        return emoji;
    }

    public int getImage1() {
        return image1;
    }

    public int getImage2() {
        return image2;
    }

    public int getImage3() {
        return image3;
    }
    public String getName() {
        return name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEmoji(int emoji) {
        this.emoji = emoji;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
    }

    public void setImage2(int image2) {
        this.image2 = image2;
    }

    public void setImage3(int image3) {
        this.image3 = image3;
    }

    public void setName(String name) {
        this.title = name;
    }
}
