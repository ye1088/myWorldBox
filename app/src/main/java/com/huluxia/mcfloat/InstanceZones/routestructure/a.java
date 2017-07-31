package com.huluxia.mcfloat.InstanceZones.routestructure;

import java.util.ArrayList;
import java.util.List;

/* compiled from: RouteAllPara */
public class a {
    private List<d> XK = new ArrayList();
    private c YF;

    public void a(c commPara) {
        this.YF = commPara;
    }

    public void a(d singlePara) {
        this.XK.add(singlePara);
    }

    public int dU(int missionIndex) {
        if (missionIndex >= this.XK.size() || ((d) this.XK.get(missionIndex)).tL()) {
            return this.YF.mWidth;
        }
        return ((d) this.XK.get(missionIndex)).mWidth;
    }

    public int dV(int missionIndex) {
        if (missionIndex >= this.XK.size() || ((d) this.XK.get(missionIndex)).tM()) {
            return this.YF.mLength;
        }
        return ((d) this.XK.get(missionIndex)).mLength;
    }

    public int dW(int missionIndex) {
        if (missionIndex >= this.XK.size() || ((d) this.XK.get(missionIndex)).tN()) {
            return this.YF.XM;
        }
        return ((d) this.XK.get(missionIndex)).XM;
    }

    public int fr(int missionIndex) {
        if (missionIndex >= this.XK.size() || ((d) this.XK.get(missionIndex)).tO()) {
            return this.YF.YK;
        }
        return ((d) this.XK.get(missionIndex)).YK;
    }

    public List<b> ed(int missionIndex) {
        if (missionIndex < this.XK.size()) {
            return ((d) this.XK.get(missionIndex)).tz();
        }
        return null;
    }

    public com.huluxia.mcfloat.InstanceZones.structrue.a F(int missionIndex, int tipsIndex) {
        if (missionIndex < this.XK.size()) {
            return ((d) this.XK.get(missionIndex)).fv(tipsIndex);
        }
        return null;
    }

    public boolean eI(int missionIndex) {
        return missionIndex < this.XK.size();
    }
}
