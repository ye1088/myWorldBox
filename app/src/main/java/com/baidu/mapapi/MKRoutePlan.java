package com.baidu.mapapi;

import java.util.ArrayList;

public class MKRoutePlan {
    private int a;
    private ArrayList<MKRoute> b;

    void a(int i) {
        this.a = i;
    }

    void a(ArrayList<MKRoute> arrayList) {
        this.b = arrayList;
    }

    public int getDistance() {
        return this.a;
    }

    public int getNumRoutes() {
        return this.b != null ? this.b.size() : 0;
    }

    public MKRoute getRoute(int i) {
        return this.b != null ? (MKRoute) this.b.get(i) : null;
    }
}
