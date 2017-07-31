package com.huluxia.studio.utils;

/* compiled from: PastRankTimeData */
public class b$a {
    public String aGW;
    public int month;
    public int year;

    public b$a(int year, int month) {
        String temp;
        this.year = year;
        this.month = month;
        if (month < 10) {
            temp = "0" + month;
        } else {
            temp = String.valueOf(month);
        }
        this.aGW = year + "-" + temp + "-01";
    }
}
