package com.baidu.mapapi;

import java.util.ArrayList;

public class MKRoute {
    public static final int ROUTE_TYPE_BUS_LINE = 3;
    public static final int ROUTE_TYPE_DRIVING = 1;
    public static final int ROUTE_TYPE_UNKNOW = 0;
    public static final int ROUTE_TYPE_WALKING = 2;
    ArrayList<ArrayList<GeoPoint>> a;
    private int b;
    private int c;
    private int d;
    private GeoPoint e;
    private GeoPoint f;
    private ArrayList<ArrayList<GeoPoint>> g;
    private ArrayList<MKStep> h;
    private String i;

    String a() {
        return this.i;
    }

    void a(int i) {
        this.c = i;
    }

    void a(GeoPoint geoPoint) {
        this.e = geoPoint;
    }

    void a(String str) {
        this.i = str;
    }

    void a(ArrayList<MKStep> arrayList) {
        this.h = arrayList;
    }

    void b(int i) {
        this.d = i;
    }

    void b(GeoPoint geoPoint) {
        this.f = geoPoint;
    }

    void b(ArrayList<ArrayList<GeoPoint>> arrayList) {
        this.g = arrayList;
    }

    public ArrayList<ArrayList<GeoPoint>> getArrayPoints() {
        return this.g;
    }

    public int getDistance() {
        return this.c;
    }

    public GeoPoint getEnd() {
        return this.f;
    }

    public int getIndex() {
        return this.b;
    }

    public int getNumSteps() {
        return this.h != null ? this.h.size() : 0;
    }

    public int getRouteType() {
        return this.d;
    }

    public GeoPoint getStart() {
        return this.e;
    }

    public MKStep getStep(int i) {
        return this.h != null ? (MKStep) this.h.get(i) : null;
    }
}
