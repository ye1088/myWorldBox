package com.MCWorld.mcfloat.InstanceZones.config;

import com.MCWorld.mcfloat.InstanceZones.structrue.a;
import com.MCWorld.mcfloat.InstanceZones.structrue.c;
import com.MCWorld.mcfloat.InstanceZones.structrue.d;
import java.util.ArrayList;
import java.util.List;

/* compiled from: InsZoneAllPara */
public class b {
    private List<c> XK;

    public void b(c para) {
        if (this.XK == null) {
            this.XK = new ArrayList();
        }
        this.XK.add(para);
    }

    public int dU(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).mWidth)) {
            return ((c) this.XK.get(missionIndex)).mWidth;
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).mWidth;
        }
        return 0;
    }

    public int dV(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).mLength)) {
            return ((c) this.XK.get(missionIndex)).mLength;
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).mLength;
        }
        return 0;
    }

    public int dW(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).XM)) {
            return ((c) this.XK.get(missionIndex)).XM;
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).XM;
        }
        return 0;
    }

    public int dX(int missionIndex) {
        if (missionIndex >= this.XK.size() || c.eJ(((c) this.XK.get(missionIndex)).XN)) {
            return 0;
        }
        return ((c) this.XK.get(missionIndex)).XN;
    }

    public int dY(int missionIndex) {
        if (missionIndex >= this.XK.size() || c.eJ(((c) this.XK.get(missionIndex)).XO)) {
            return 0;
        }
        return ((c) this.XK.get(missionIndex)).XO;
    }

    public int dZ(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).XP)) {
            return ((c) this.XK.get(missionIndex)).XP;
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).XP;
        }
        return 0;
    }

    public int ea(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).XQ)) {
            return ((c) this.XK.get(missionIndex)).XQ;
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).XQ;
        }
        return 0;
    }

    public int eb(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).XS)) {
            return ((c) this.XK.get(missionIndex)).XS;
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).XS;
        }
        return 0;
    }

    public int ec(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).XR)) {
            return ((c) this.XK.get(missionIndex)).XR;
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).XR;
        }
        return 0;
    }

    public List<d> ed(int missionIndex) {
        if (missionIndex < this.XK.size()) {
            return ((c) this.XK.get(missionIndex)).tz();
        }
        return null;
    }

    public List<a> ee(int missionIndex) {
        if (missionIndex < this.XK.size()) {
            return ((c) this.XK.get(missionIndex)).ty();
        }
        return null;
    }

    public List<c> ef(int missionIndex) {
        if (missionIndex < this.XK.size()) {
            return ((c) this.XK.get(missionIndex)).tA();
        }
        return null;
    }

    public List<com.MCWorld.mcfb.b> eg(int missionIndex) {
        if (missionIndex < this.XK.size() && ((c) this.XK.get(missionIndex)).sW() != null) {
            return ((c) this.XK.get(missionIndex)).sW();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).sW();
        }
        return null;
    }

    public List<com.MCWorld.mcfb.b> eh(int missionIndex) {
        if (missionIndex < this.XK.size() && ((c) this.XK.get(missionIndex)).sX() != null) {
            return ((c) this.XK.get(missionIndex)).sX();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).sX();
        }
        return null;
    }

    public List<com.MCWorld.mcfb.b> ei(int missionIndex) {
        if (missionIndex < this.XK.size() && ((c) this.XK.get(missionIndex)).sY() != null) {
            return ((c) this.XK.get(missionIndex)).sY();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).sY();
        }
        return null;
    }

    public int ej(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).sZ())) {
            return ((c) this.XK.get(missionIndex)).sZ();
        }
        if (this.XK.size() <= 0 || c.eJ(((c) this.XK.get(0)).sZ())) {
            return 0;
        }
        return ((c) this.XK.get(0)).sZ();
    }

    public int ek(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).ta())) {
            return ((c) this.XK.get(missionIndex)).ta();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).ta();
        }
        return 0;
    }

    public int el(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).tb())) {
            return ((c) this.XK.get(missionIndex)).tb();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).tb();
        }
        return 0;
    }

    public int em(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).tc())) {
            return ((c) this.XK.get(missionIndex)).tc();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).tc();
        }
        return 0;
    }

    public int en(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).td())) {
            return ((c) this.XK.get(missionIndex)).td();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).td();
        }
        return 0;
    }

    public int eo(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).te())) {
            return ((c) this.XK.get(missionIndex)).te();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).te();
        }
        return 0;
    }

    public int ep(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).tf())) {
            return ((c) this.XK.get(missionIndex)).tf();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).tf();
        }
        return 0;
    }

    public int eq(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).tg())) {
            return ((c) this.XK.get(missionIndex)).tg();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).tg();
        }
        return 0;
    }

    public int er(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).th())) {
            return ((c) this.XK.get(missionIndex)).th();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).th();
        }
        return 0;
    }

    public int es(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).ti())) {
            return ((c) this.XK.get(missionIndex)).ti();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).ti();
        }
        return 0;
    }

    public int et(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).tj())) {
            return ((c) this.XK.get(missionIndex)).tj();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).tj();
        }
        return 0;
    }

    public int eu(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).tk())) {
            return ((c) this.XK.get(missionIndex)).tk();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).tk();
        }
        return 0;
    }

    public int ev(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).tl())) {
            return ((c) this.XK.get(missionIndex)).tl();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).tl();
        }
        return 0;
    }

    public int ew(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).tm())) {
            return ((c) this.XK.get(missionIndex)).tm();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).tm();
        }
        return 0;
    }

    public int ex(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).tn())) {
            return ((c) this.XK.get(missionIndex)).tn();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).tn();
        }
        return 0;
    }

    public int ey(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).to())) {
            return ((c) this.XK.get(missionIndex)).to();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).to();
        }
        return 0;
    }

    public int ez(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).tp())) {
            return ((c) this.XK.get(missionIndex)).tp();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).tp();
        }
        return 0;
    }

    public int eA(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).tq())) {
            return ((c) this.XK.get(missionIndex)).tq();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).tq();
        }
        return 0;
    }

    public int eB(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).tr())) {
            return ((c) this.XK.get(missionIndex)).tr();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).tr();
        }
        return 0;
    }

    public int eC(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).ts())) {
            return ((c) this.XK.get(missionIndex)).ts();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).ts();
        }
        return 0;
    }

    public int eD(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).tt())) {
            return ((c) this.XK.get(missionIndex)).tt();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).tt();
        }
        return 0;
    }

    public int eE(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).tu())) {
            return ((c) this.XK.get(missionIndex)).tu();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).tu();
        }
        return 0;
    }

    public int eF(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).tv())) {
            return ((c) this.XK.get(missionIndex)).tv();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).tv();
        }
        return 0;
    }

    public int eG(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).tw())) {
            return ((c) this.XK.get(missionIndex)).tw();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).tw();
        }
        return 0;
    }

    public int eH(int missionIndex) {
        if (missionIndex < this.XK.size() && !c.eJ(((c) this.XK.get(missionIndex)).tx())) {
            return ((c) this.XK.get(missionIndex)).tx();
        }
        if (this.XK.size() > 0) {
            return ((c) this.XK.get(0)).tx();
        }
        return 0;
    }

    public c g(int curMissionIndex, c missonPara) {
        if (this.XK == null || this.XK.size() == 0) {
            return null;
        }
        if (missonPara == null) {
            missonPara = new c();
            missonPara.XT = new a();
            missonPara.XU = new com.MCWorld.mcfloat.InstanceZones.structrue.b(-1);
        }
        missonPara.mWidth = dU(curMissionIndex);
        missonPara.mLength = dV(curMissionIndex);
        missonPara.XM = dW(curMissionIndex);
        missonPara.XN = dV(curMissionIndex);
        missonPara.XO = dY(curMissionIndex);
        missonPara.XP = dZ(curMissionIndex);
        missonPara.XQ = ea(curMissionIndex);
        missonPara.XR = ec(curMissionIndex);
        missonPara.XS = eb(curMissionIndex);
        missonPara.XT.XJ = ei(curMissionIndex);
        missonPara.XT.XI = eh(curMissionIndex);
        missonPara.XT.XH = eg(curMissionIndex);
        missonPara.XU.eR(ej(curMissionIndex));
        missonPara.XU.eT(el(curMissionIndex));
        missonPara.XU.eU(em(curMissionIndex));
        missonPara.XU.eV(en(curMissionIndex));
        missonPara.XU.eW(eo(curMissionIndex));
        missonPara.XU.eX(ep(curMissionIndex));
        missonPara.XU.eY(eq(curMissionIndex));
        missonPara.XU.eZ(er(curMissionIndex));
        missonPara.XU.fa(es(curMissionIndex));
        missonPara.XU.fb(et(curMissionIndex));
        missonPara.XU.fc(eu(curMissionIndex));
        missonPara.XU.fe(ev(curMissionIndex));
        missonPara.XU.ff(ew(curMissionIndex));
        missonPara.XU.fg(ex(curMissionIndex));
        missonPara.XU.fh(ey(curMissionIndex));
        missonPara.XU.fi(ez(curMissionIndex));
        missonPara.XU.fj(eA(curMissionIndex));
        missonPara.XU.fk(eB(curMissionIndex));
        missonPara.XU.fl(eC(curMissionIndex));
        missonPara.XU.fm(eD(curMissionIndex));
        missonPara.XU.fn(eE(curMissionIndex));
        missonPara.XU.fo(eF(curMissionIndex));
        missonPara.XU.fp(eG(curMissionIndex));
        missonPara.XU.eS(ek(curMissionIndex));
        missonPara.XU.fq(eH(curMissionIndex));
        missonPara.XV = ee(curMissionIndex);
        missonPara.XW = ed(curMissionIndex);
        missonPara.XX = ef(curMissionIndex);
        return missonPara;
    }

    public boolean eI(int missionIndex) {
        return missionIndex < this.XK.size();
    }
}
