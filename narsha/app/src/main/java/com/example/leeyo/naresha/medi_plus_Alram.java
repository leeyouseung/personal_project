package com.example.leeyo.naresha;

public class medi_plus_Alram {
    private String am_pm;
    private String hour;
    private String minute;

    medi_plus_Alram(String am_pm, String hour, String minute) {
        this.am_pm = am_pm;
        this.hour = hour;
        this.minute = minute;

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

}