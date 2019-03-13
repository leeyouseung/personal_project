package com.example.leeyo.naresha;

public class ChonItem {
    private String Chon;
    private String date_chon;
    private String check_chon;
    private String Average_chon;

    ChonItem(String Chon, String date_chon, String check_chon) {
        this.Chon = Chon;
        this.date_chon = date_chon;
        this.check_chon = check_chon;
        this.Average_chon = Average_chon;

    }

    public String getChon() {
        return Chon;

    }

    public void setChon(String chon) {
        Chon = chon;

    }

    public String getDate_chon() {
        return date_chon;

    }

    public void setDate_chon(String date_chon) {
        this.date_chon = date_chon;

    }

    public String getCheck_chon() {
        return check_chon;

    }

    public void setCheck_chon(String check_chon) {
        this.check_chon = check_chon;

    }

    public String getAverage_chon() {
        return Average_chon;
    }

    public void setAverage_chon(String average_chon) {
        this.Average_chon = average_chon;
    }
}
