package com.capstoneandroid.gieokdama.item;

public class AlbumItem {

    Long id;
    String title;
    Integer color;

    public AlbumItem(String title, Integer color) {
        this.title = title;
        this.color = color;
    }

    public AlbumItem(Long id, String title, Integer color) {
        this.id = id;
        this.title = title;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    public Integer getColor() {
        return color;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setColor(Integer color) {
        this.color = color;
    }
}
