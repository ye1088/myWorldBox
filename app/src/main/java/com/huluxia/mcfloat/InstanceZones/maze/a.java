package com.huluxia.mcfloat.InstanceZones.maze;

import com.huluxia.framework.base.log.HLog;
import com.huluxia.mcfb.b;
import com.huluxia.mcgame.g;
import com.huluxia.mcinterface.h;
import com.huluxia.mcinterface.j;
import com.huluxia.mcsdk.DTSDKManagerEx;
import hlx.launch.game.c;
import hlx.utils.f;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/* compiled from: InsZoneMazeEnv */
public class a {
    private static a XY;
    public static ArrayList<com.huluxia.mcgame.a> Yp = new ArrayList(2);
    private int NA;
    private int NB;
    private int Nz;
    private int XZ;
    private int Ya;
    private int Yb;
    private int Yc;
    private int Yd;
    private int Ye;
    private int Yf;
    private int Yg;
    private int Yh;
    private int Yi;
    private int Yj;
    private int Yk;
    private int Yl;
    private int Ym;
    private int Yn = 2;
    private boolean Yo = false;

    public static synchronized a tB() {
        a aVar;
        synchronized (a.class) {
            if (XY == null) {
                XY = new a();
                XY.Yc = 25;
                XY.Yd = 25;
                XY.Ye = 5;
                XY.Yf = 17;
                XY.Yg = 2;
                XY.Yh = 7;
                XY.Yi = 0;
                XY.Yj = 7;
                XY.Yk = 0;
                XY.Yl = 0;
                XY.Ym = 0;
            }
            aVar = XY;
        }
        return aVar;
    }

    private void a(ArrayList<j> inputList, int inputId, int inputDmg, int inputX, int inputY, int inputZ) {
        inputList.add(new j(inputId, inputDmg, inputX, inputY, inputZ));
    }

    private ArrayList<j> j(int inputX, int inputY, int inputZ) {
        ArrayList<j> inList = new ArrayList();
        LinkedList<hlx.utils.a> _tmpRoute = b.tF().E(0, 0);
        int index = 0;
        int _width = 0;
        while (_width < this.Yd) {
            int _length = 0;
            while (_length < this.Yc) {
                hlx.utils.a _tmp = sP();
                b(inList, _tmp.YG, _tmp.YH, inputX + (_width * 3), inputY - 1, inputZ + (_length * 3));
                int _high;
                if (index < _tmpRoute.size() && ((hlx.utils.a) _tmpRoute.get(index)).YG == _width && ((hlx.utils.a) _tmpRoute.get(index)).YH == _length) {
                    for (_high = 0; _high < this.Ye; _high++) {
                        b(inList, this.Yl, this.Ym, inputX + (_width * 3), inputY + _high, inputZ + (_length * 3));
                    }
                    index++;
                } else {
                    for (_high = 0; _high < this.Ye; _high++) {
                        b(inList, this.Yf, this.Yg, inputX + (_width * 3), inputY + _high, inputZ + (_length * 3));
                    }
                }
                b(inList, this.Yf, this.Yg, inputX + (_width * 3), inputY + this.Ye, inputZ + (_length * 3));
                _length++;
            }
            _width++;
        }
        return inList;
    }

    private void b(ArrayList<j> inList, int id, int dmg, int inputX, int inputY, int inputZ) {
        a((ArrayList) inList, id, dmg, inputX - 1, inputY, inputZ - 1);
        a((ArrayList) inList, id, dmg, inputX, inputY, inputZ - 1);
        a((ArrayList) inList, id, dmg, inputX + 1, inputY, inputZ - 1);
        a((ArrayList) inList, id, dmg, inputX - 1, inputY, inputZ);
        a((ArrayList) inList, id, dmg, inputX, inputY, inputZ);
        a((ArrayList) inList, id, dmg, inputX + 1, inputY, inputZ);
        a((ArrayList) inList, id, dmg, inputX - 1, inputY, inputZ + 1);
        a((ArrayList) inList, id, dmg, inputX, inputY, inputZ + 1);
        a((ArrayList) inList, id, dmg, inputX + 1, inputY, inputZ + 1);
    }

    private hlx.utils.a sP() {
        return new hlx.utils.a(35, new Random().nextInt(15));
    }

    public void C(int inputWide, int inputHigh) {
        this.Nz = f.R(h.zF());
        this.NA = (int) h.zG();
        this.NB = f.R(h.zH());
        h.D(a(this.Nz, this.NA, this.NB, 20, 0, false));
        h.D(j(this.Nz + 4, this.NA, this.NB));
    }

    private ArrayList<j> a(int inputX, int inputY, int inputZ, int inputId, int inputDmg, boolean isClear) {
        int i;
        int j;
        int k;
        ArrayList<j> inList = new ArrayList();
        if (!isClear) {
            for (i = 0; i < 3; i++) {
                for (j = 0; j < 3; j++) {
                    for (k = 0; k < 2; k++) {
                        inList.add(new j(0, 0, (inputX - 1) + i, (inputY - 1) + k, (inputZ - 1) + j));
                    }
                }
            }
        }
        for (i = 0; i < 5; i++) {
            for (k = 0; k < 2; k++) {
                inList.add(new j(inputId, inputDmg, (inputX - 2) + i, (inputY - 1) + k, inputZ - 2));
                inList.add(new j(inputId, inputDmg, (inputX - 2) + i, (inputY - 1) + k, inputZ + 2));
            }
        }
        for (j = 0; j < 3; j++) {
            for (k = 0; k < 2; k++) {
                int i2 = inputId;
                int i3 = inputDmg;
                inList.add(new j(i2, i3, inputX - 2, (inputY - 1) + k, (inputZ - 1) + j));
                i2 = inputId;
                i3 = inputDmg;
                inList.add(new j(i2, i3, inputX + 2, (inputY - 1) + k, (inputZ - 1) + j));
            }
        }
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                inList.add(new j(2, 0, (inputX - 2) + i, inputY - 2, (inputZ - 2) + j));
                i2 = inputId;
                i3 = inputDmg;
                inList.add(new j(i2, i3, (inputX - 2) + i, inputY + 1, (inputZ - 2) + j));
            }
        }
        return inList;
    }

    public boolean sQ() {
        int _tmpRoleX = (int) h.zF();
        int _tmpRoleY = (int) h.zG();
        int _tmpRoleZ = (int) h.zH();
        int originX = this.Nz + 4;
        int originY = (this.NA + this.Ye) + 1;
        int originZ = this.NB;
        if (originY < _tmpRoleY || _tmpRoleY < this.NA) {
            return true;
        }
        if (_tmpRoleX < originX || (this.Yc * 3) + originX < _tmpRoleX) {
            return true;
        }
        if (_tmpRoleZ < originZ || (this.Yd * 3) + originZ < _tmpRoleZ) {
            return true;
        }
        return false;
    }

    public void tC() {
        ArrayList<j> itemList = new ArrayList();
        int tmpX = this.Nz;
        int tmpY = this.NA;
        int tmpZ = this.NB;
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                int i2 = 0;
                itemList.add(new j(0, i2, (tmpX - 2) + i, tmpY - 1, (tmpZ - 2) + j));
            }
        }
        h.D(itemList);
    }

    public boolean tD() {
        sA();
        int _tmpRoleX = f.R(h.zF());
        int _tmpRoleZ = f.R(h.zH());
        if (_tmpRoleX < this.XZ || _tmpRoleZ < this.Yb) {
            return false;
        }
        return true;
    }

    private void sA() {
        try {
            if (c.Sg().Sh() == 3 || c.Sg().Sh() == 4 || c.Sg().Sh() == 5 || c.Sg().Sh() == 7) {
                int _mapBiontSize = g.aea.size();
                for (int i = 0; _mapBiontSize > i; i++) {
                    long _nLongMapBionId = ((Long) g.aea.get(i)).longValue();
                    if (DTSDKManagerEx.ai(_nLongMapBionId) == 63) {
                        DTSDKManagerEx.i(_nLongMapBionId, 0);
                    }
                }
            }
        } catch (Exception e) {
            HLog.verbose("Exception", e.getMessage(), new Object[0]);
        }
    }

    public void tE() {
        ArrayList<j> inList = new ArrayList();
        for (int i = 0; i < XY.Ye; i++) {
            b(inList, this.Yf, this.Yg, this.Nz + 4, this.NA + i, this.NB + 3);
            b(inList, this.Yf, this.Yg, this.Nz + 7, this.NA + i, this.NB);
        }
        h.D(inList);
        h.b(((float) this.Nz) + 8.0f, (float) (this.NA + 2), ((float) this.NB) + 3.0f);
        this.XZ = (this.Nz + 4) + ((XY.Yc * 3) - 6);
        this.Ya = this.NA + 2;
        this.Yb = this.NB + ((XY.Yd * 3) - 6);
    }

    public void e(b inputMonsterItem) {
        if (inputMonsterItem != null) {
            ArrayList<j> _tmpFBMonsterList = new ArrayList();
            int tmpRoleY = (int) h.zG();
            Random _tmpRandom = new Random();
            for (int i = inputMonsterItem.qm(); i > 0; i--) {
                _tmpFBMonsterList.add(new j(inputMonsterItem.getId(), inputMonsterItem.ql(), this.Nz + (_tmpRandom.nextInt(this.Yc) * 3), tmpRoleY + 1, this.NB + (_tmpRandom.nextInt(this.Yd) * 3), 1));
            }
            h.D(_tmpFBMonsterList);
        }
    }

    private ArrayList<j> k(int inputX, int inputY, int inputZ) {
        ArrayList<j> inList = new ArrayList();
        inList.addAll(a(this.Nz, this.NA, this.NB, 0, 0, true));
        for (int a = 0; a < XY.Yc; a++) {
            for (int b = 0; b < XY.Yd; b++) {
                ArrayList<j> arrayList = inList;
                b(arrayList, 0, 0, inputX + (a * 3), inputY - 1, inputZ + (b * 3));
                for (int c = 0; c < XY.Ye; c++) {
                    arrayList = inList;
                    b(arrayList, 0, 0, inputX + (a * 3), inputY + c, inputZ + (b * 3));
                }
                b(inList, 0, 0, inputX + (a * 3), inputY + XY.Ye, inputZ + (b * 3));
            }
        }
        return inList;
    }

    public void sT() {
        h.D(k(this.Nz + 4, this.NA, this.NB));
    }
}
