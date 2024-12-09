package com.capstoneandroid.gieokdama.model;

public class Mission {
    private Long id;
    private String title;
    private String emoji;
    private Integer cycle;
    private String repeat_day;
    private Integer repeat_time;
    private boolean alarm;
    private String alarm_time;

    public Mission(Long id, String title, String emoji,
                   Integer cycle, String repeat_day, Integer repeat_time,
                   boolean alarm, String alarm_time) {
        this.id = id;
        this.title = title;
        this.emoji = emoji;
        this.cycle = cycle;
        this.repeat_day = repeat_day;
        this.repeat_time = repeat_time;
        this.alarm = alarm;
        this.alarm_time = alarm_time;
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

    public Integer getCycle() {
        return cycle;
    }

    public String getRepeat_day() {
        return repeat_day;
    }

    public Integer getRepeat_time() {
        return repeat_time;
    }

    public boolean getAlarm() {
        return alarm;
    }

    public String getAlarm_time() {
        return alarm_time;
    }
}
