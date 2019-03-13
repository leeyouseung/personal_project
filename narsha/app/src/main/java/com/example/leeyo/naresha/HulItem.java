package com.example.leeyo.naresha;

public class HulItem {
    private String Max_hul;
    private String Min_hul;
    private String date_hul;
    private String check_hul;
    private String Average_hul;

    HulItem(String Max_hul, String Min_hul, String Average_hul, String check_hul, String date_hul) {
        this.Max_hul = Max_hul;
        this.Min_hul = Min_hul;
        this.date_hul = date_hul;
        this.check_hul = check_hul;
        this.Average_hul = Average_hul;
    }

    public String getMax_hul() {
        return Max_hul;
    }

    public void setMax_hul(String max_hul) {
        this.Max_hul = max_hul;
    }

    public String getMin_hul() {
        return Min_hul;
    }

    public void setMin_hul(String min_hul) {
        this.Min_hul = min_hul;
    }

    public String getDate_hul() {
        return date_hul;
    }

    public void setDate_hul(String date_hul) {
        this.date_hul = date_hul;
    }

    public String getCheck_hul() {
        return check_hul;
    }

    public void setCheck_hul(String check_hul) {
        this.check_hul = check_hul;
    }

    public String getAverage_hul() {
        return Average_hul;
    }

    public void setAverage_hul(String average_hul) {
        this.Average_hul = average_hul;
    }
}
