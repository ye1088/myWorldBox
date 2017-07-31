package com.baidu.mapapi;

import java.util.ArrayList;

public class MKPoiResult {
    private int a = 0;
    private int b = 0;
    private int c = 0;
    private int d = 0;
    private ArrayList<MKPoiResult> e;
    private ArrayList<MKPoiInfo> f;
    private ArrayList<MKCityListInfo> g;

    void a(int i) {
        this.b = i;
    }

    void a(ArrayList<MKPoiInfo> arrayList) {
        this.f = arrayList;
    }

    void b(int i) {
        this.a = i;
    }

    void b(ArrayList<MKPoiResult> arrayList) {
        this.e = arrayList;
    }

    void c(int i) {
        this.c = i;
    }

    void c(ArrayList<MKCityListInfo> arrayList) {
        this.g = arrayList;
    }

    void d(int i) {
        this.d = i;
    }

    public ArrayList<MKPoiInfo> getAllPoi() {
        return this.f;
    }

    public MKCityListInfo getCityListInfo(int i) {
        return this.g != null ? (MKCityListInfo) this.g.get(i) : null;
    }

    public int getCityListNum() {
        return this.g != null ? this.g.size() : 0;
    }

    public int getCurrentNumPois() {
        return this.b;
    }

    public ArrayList<MKPoiResult> getMultiPoiResult() {
        return this.e;
    }

    public int getNumPages() {
        return this.c;
    }

    public int getNumPois() {
        return this.a;
    }

    public int getPageIndex() {
        return this.d;
    }

    public MKPoiInfo getPoi(int i) {
        return this.f != null ? (MKPoiInfo) this.f.get(i) : null;
    }
}
