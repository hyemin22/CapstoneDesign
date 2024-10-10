package com.capstoneandroid.capstonedesign;

public class DayMissionItem {
    int icon;
    String title;
    String progress;

    public DayMissionItem(int icon, String title, String progress) {
        this.icon = icon;
        this.title = title;
        this.progress = progress;
    }

    public int getIcon() {
        return icon;
    }
    public String getTitle() {
        return title;
    }
    public String getProgress() {
        return progress;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setProgress(String progress) {
        this.progress = progress;
    }
}
