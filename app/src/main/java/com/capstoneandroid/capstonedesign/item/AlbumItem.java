package com.capstoneandroid.capstonedesign.item;

public class AlbumItem {

    String title;
    int albumColor;

    public AlbumItem(String title, int albumColor) {
        this.title = title;
        this.albumColor = albumColor;
    }

    public String getTitle() {
        return title;
    }
    public int getAlbumColor() {
        return albumColor;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setAlbumColor(int albumColor) {
        this.albumColor = albumColor;
    }
}
