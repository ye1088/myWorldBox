package com.MCWorld.mcfloat.InstanceZones.routestructure;

import com.MCWorld.mcfloat.InstanceZones.structrue.a;
import java.util.ArrayList;
import java.util.List;

/* compiled from: RouteSinglePara */
public class d {
    private static final int YL = -1;
    private static final int YM = -1;
    private static final int YN = -1;
    private static final int YO = -1;
    public int XM = -1;
    private List<a> XV = new ArrayList();
    private List<b> XW = new ArrayList();
    public int YK = -1;
    public int mLength = -1;
    public int mWidth = -1;

    public void setWidth(int width) {
        this.mWidth = width;
    }

    public boolean tL() {
        if (this.mWidth == -1) {
            return true;
        }
        return false;
    }

    public void setLength(int length) {
        this.mLength = length;
    }

    public boolean tM() {
        if (this.mLength == -1) {
            return true;
        }
        return false;
    }

    public void eM(int high) {
        this.XM = high;
    }

    public boolean tN() {
        if (this.XM == -1) {
            return true;
        }
        return false;
    }

    public void eO(int chalengeTime) {
        this.YK = chalengeTime;
    }

    public boolean tO() {
        if (this.YK == -1) {
            return true;
        }
        return false;
    }

    public void a(b routeBlock) {
        this.XW.add(routeBlock);
    }

    public List<b> tz() {
        return this.XW;
    }

    public void a(a tips) {
        this.XV.add(tips);
    }

    public a fv(int index) {
        if (index < this.XV.size()) {
            return (a) this.XV.get(index);
        }
        return null;
    }
}
