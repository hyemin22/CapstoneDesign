package com.capstoneandroid.capstonedesign.item;

import android.content.Context;

public class MyMissionItem {
    Long id;
    String title, emoji, cycle, repeat_day, alarm_time, percent;
    Integer repeat_time, now_time, goal_time;
    boolean alarm;

    public MyMissionItem(Context context, Long id,
                         String title, String emoji, String cycle,
                         String repeat_day, Integer repeat_time,
                         Integer now_time, Integer goal_time,
                         String percent,
                         boolean alarm, String alarm_time) {
        this.id = id;
        this.title = title;
        this.emoji = emoji;
        this.cycle = cycle;
        this.repeat_day = repeat_day;
        this.alarm_time = alarm_time;
        this.repeat_time = repeat_time;
        this.now_time = now_time;
        this.goal_time = goal_time;
        this.percent = calculatePercent(now_time, goal_time);
        this.alarm = alarm;
    }

    public String calculatePercent(Integer now_time, Integer goal_time) {
        if (now_time != null && goal_time != 0) {
            double result = (double) now_time / goal_time * 100;
            String formattedResult = String.format("%.1f", result);
            return formattedResult;
        } else {
            return "0.0";
        }
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getEmoji() {
        return emoji;
    }

    public String getCycle() {
        return cycle;
    }

    public String getRepeat_day() {
        return repeat_day;
    }

    public String getAlarm_time() {
        return alarm_time;
    }

    public Integer getRepeat_time() {
        return repeat_time;
    }

    public Integer getNow_time() {
        return now_time;
    }

    public Integer getGoal_time() {
        return goal_time;
    }

    public String getPercent() {
        return percent;
    }

    public boolean getAlarm() {
        return alarm;
    }
}
