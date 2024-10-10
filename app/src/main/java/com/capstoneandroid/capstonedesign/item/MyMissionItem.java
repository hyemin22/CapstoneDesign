package com.capstoneandroid.capstonedesign.item;

public class MyMissionItem {
    String emoji, title, cycle, percent, count, goal;

    public MyMissionItem(String emoji, String title, String cycle, String percent, String count, String goal) {
        this.emoji = emoji;
        this.title = title;
        this.cycle = cycle;
        this.percent = percent;
        this.count = count;
        this.goal = goal;
    }

    public String getEmoji() {
        return emoji;
    }
    public String getTitle() {
        return title;
    }
    public String getCycle() {
        return cycle;
    }
    public String getPercent() {
        return percent;
    }
    public String getCount() {
        return count;
    }
    public String getGoal() {
        return goal;
    }
    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setCycle(String cycle) {
        this.cycle = cycle;
    }
    public void setPercent(String percent) {
        this.percent = percent;
    }
    public void setCount(String count) {
        this.count = count;
    }
    public void setGoal(String goal) {
        this.goal = goal;
    }
}
