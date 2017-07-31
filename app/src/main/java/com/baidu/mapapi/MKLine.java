package com.baidu.mapapi;

import java.util.ArrayList;

public class MKLine {
    public static final int LINE_TYPE_BUS = 0;
    public static final int LINE_TYPE_SUBWAY = 1;
    ArrayList<GeoPoint> a;
    private int b;
    private int c;
    private int d;
    private String e;
    private String f;
    private String g;
    private MKPoiInfo h;
    private MKPoiInfo i;
    private ArrayList<GeoPoint> j;

    String a() {
        return this.g;
    }

    void a(int i) {
        this.b = i;
    }

    void a(MKPoiInfo mKPoiInfo) {
        this.h = mKPoiInfo;
    }

    void a(String str) {
        this.g = str;
    }

    void a(ArrayList<GeoPoint> arrayList) {
        this.j = arrayList;
    }

    void b(int i) {
        this.c = i;
    }

    void b(MKPoiInfo mKPoiInfo) {
        this.i = mKPoiInfo;
    }

    void b(String str) {
        this.e = str;
    }

    void c(int i) {
        this.d = i;
    }

    void c(String str) {
        this.f = str;
    }

    public int getDistance() {
        return this.c;
    }

    public MKPoiInfo getGetOffStop() {
        return this.i;
    }

    public MKPoiInfo getGetOnStop() {
        return this.h;
    }

    public int getNumViaStops() {
        return this.b;
    }

    public ArrayList<GeoPoint> getPoints() {
        return this.j;
    }

    public String getTitle() {
        return this.e;
    }

    public int getType() {
        return this.d;
    }

    public String getUid() {
        return this.f;
    }
}
