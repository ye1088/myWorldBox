package com.baidu.mapapi;

public class MKStep {
    private GeoPoint a;
    private String b;
    private int c;

    int a() {
        return this.c;
    }

    void a(int i) {
        this.c = i;
    }

    void a(GeoPoint geoPoint) {
        this.a = geoPoint;
    }

    void a(String str) {
        this.b = str;
    }

    public String getContent() {
        return this.b;
    }

    public GeoPoint getPoint() {
        return this.a;
    }
}
