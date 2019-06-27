package com.example.imtpmd;

public class SettingsData {
    private String name;
    private String time;
    private String tag;

    public SettingsData(String name, String time, String tag){
        this.name = name;
        this.time = time;
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
