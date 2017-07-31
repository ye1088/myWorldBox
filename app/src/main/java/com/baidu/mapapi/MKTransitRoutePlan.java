package com.baidu.mapapi;

import java.util.ArrayList;

public class MKTransitRoutePlan {
    private int a;
    private String b;
    private ArrayList<MKRoute> c;
    private ArrayList<MKLine> d;
    private GeoPoint e;
    private GeoPoint f;

    void a(int i) {
        this.a = i;
    }

    void a(GeoPoint geoPoint) {
        this.e = geoPoint;
    }

    void a(ArrayList<MKRoute> arrayList) {
        this.c = arrayList;
    }

    void b(GeoPoint geoPoint) {
        this.f = geoPoint;
    }

    public String getContent() {
        return this.b;
    }

    public int getDistance() {
        return this.a;
    }

    public GeoPoint getEnd() {
        return this.f;
    }

    public MKLine getLine(int i) {
        return this.d != null ? (MKLine) this.d.get(i) : null;
    }

    public int getNumLines() {
        return this.d != null ? this.d.size() : 0;
    }

    public int getNumRoute() {
        return this.c != null ? this.c.size() : 0;
    }

    public MKRoute getRoute(int i) {
        return this.c != null ? (MKRoute) this.c.get(i) : null;
    }

    public GeoPoint getStart() {
        return this.e;
    }

    public void setLine(ArrayList<MKLine> arrayList) {
        this.d = arrayList;
    }
}
