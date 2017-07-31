package com.baidu.mapapi;

import java.util.ArrayList;

public class MKTransitRouteResult {
    private MKPlanNode a;
    private MKPlanNode b;
    private ArrayList<MKTransitRoutePlan> c;
    private MKRouteAddrResult d;

    void a(MKPlanNode mKPlanNode) {
        this.a = mKPlanNode;
    }

    void a(MKRouteAddrResult mKRouteAddrResult) {
        this.d = mKRouteAddrResult;
    }

    void a(ArrayList<MKTransitRoutePlan> arrayList) {
        this.c = arrayList;
    }

    void b(MKPlanNode mKPlanNode) {
        this.b = mKPlanNode;
    }

    public MKRouteAddrResult getAddrResult() {
        return this.d;
    }

    public MKPlanNode getEnd() {
        return this.b;
    }

    public int getNumPlan() {
        return this.c != null ? this.c.size() : 0;
    }

    public MKTransitRoutePlan getPlan(int i) {
        return this.c != null ? (MKTransitRoutePlan) this.c.get(i) : null;
    }

    public MKPlanNode getStart() {
        return this.a;
    }
}
