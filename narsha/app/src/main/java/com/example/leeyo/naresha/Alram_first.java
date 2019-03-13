package com.example.leeyo.naresha;

import android.support.annotation.NonNull;
import java.io.Serializable;

public class Alram_first implements Serializable, Comparable<Alram_first> {
    private String am_pm;
    private String hour;
    private String minute;
    private String isFor;
    private String count;
    private boolean isOn;

    Alram_first(String am_pm, String hour, String minute, String isFor, String count, boolean isOn) {
        this.am_pm = am_pm;
        this.hour = hour;
        this.minute = minute;
        this.isFor = isFor;
        this.count = count;
        this.isOn = isOn;

    }

    Alram_first() {

    }

    public String getAm_pm() {
        return am_pm;

    }

    public void setAm_pm(String am_pm) {
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

    public boolean isOn() {
        return isOn;

    }

    public void setOn(boolean on) {
        isOn = on;

    }

    public String getam_pm() {
        return am_pm;

    }

    public String getCount() {
        return count;

    }

    public void setCount(String count) {
        this.count = count;

    }

    @Override
    public int compareTo(@NonNull Alram_first o) {
        return 0;

    }
}
