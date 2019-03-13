package com.example.leeyo.naresha;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Alram implements Serializable, Comparable<Alram> {
    private String am_pm;
    private String hour;
    private String minute;
    private String isFor;
    private String count;

    Alram(String am_pm, String hour, String minute, String isFor, String count) {
        this.am_pm = am_pm;
        this.hour = hour;
        this.minute = minute;
        this.isFor = isFor;
        this.count = count;

    }

    Alram() {

    }

    public String getam_pm() {
        return am_pm;

    }

    public void setam_pm(String am_pm) {
        this.am_pm = am_pm;

    }

    public String getHour() {
        return hour;

    }

    public void setHour(String hour) {
        this.hour = hour;

    }

    public String getMinute() {
        return minute;

    }

    public void setMinute(String minute) {
        this.minute = minute;

    }

    public String getIsFor() {
        return isFor;

    }

    public void setIsFor(String isFor) {
        this.isFor = isFor;

    }

    public String getCount() {
        return count;

    }

    public void setCount(String count) {
        this.count = count;

    }

    @Override
    public int compareTo(@NonNull Alram o) {
        return 0;

    }
}
