package com.MCWorld.mcfloat.InstanceZones.trap;

import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.mcgame.g;
import com.MCWorld.mcinterface.h;
import com.MCWorld.mcinterface.j;
import com.MCWorld.mcsdk.DTSDKManagerEx;
import hlx.launch.game.c;
import hlx.utils.f;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/* compiled from: InsZoneTrapEnv */
public class a {
    private static a ZL;
    private static boolean Zs = true;
    private int NA;
    private int NB;
    private int Nz;
    private int XZ;
    private int YC;
    private int Ya;
    private int Yb;
    private int Yr;
    private int Ys;
    private int Yt;
    private int ZA;
    private int ZB;
    private int ZC;
    private int ZD;
    private int ZE;
    private int ZF;
    private int ZG;
    private int ZH;
    private int ZI;
    private int ZJ;
    private int ZK;
    private int Zt;
    private int Zu;
    private int Zv;
    private int Zw;
    private int Zx;
    private int Zy;
    private int Zz;

    public void tG() {
        this.Nz = f.R(h.zF());
        this.NA = ZL.YC;
        this.NB = f.R(h.zH());
        this.Yr = this.Nz;
        this.Ys = (int) h.zG();
        this.Yt = this.NB;
        h.D(a(this.Yr, this.Ys, this.Yt, 20, 0, false));
        h.D(a(this.Nz + 4, this.NA - 6, this.NB - (this.Zt / 2), c.tR().E(0, 0)));
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

    private ArrayList<j> a(int inputX, int inputY, int inputZ, LinkedList<hlx.utils.a> trapList) {
        if (trapList == null) {
            return null;
        }
        int hight;
        ArrayList<j> inList = new ArrayList();
        int _height = 9 - 1;
        a(inList, inputX, inputY + 8, inputZ, 20, 0, this.Zt, this.Zu);
        _height--;
        a(inList, inputX, inputY + 7, inputZ, this.ZJ, this.ZK, this.Zt, this.Zu);
        _height--;
        a(inList, inputX, inputY + 6, inputZ, this.ZJ, this.ZK, this.Zt, this.Zu);
        _height--;
        a(inList, inputX, inputY + 5, inputZ, this.ZJ, this.ZK, this.Zt, this.Zu);
        _height--;
        a(inList, inputX, inputY + 4, inputZ, this.ZJ, this.ZK, this.Zt, this.Zu);
        _height = -1 + 1;
        a(inList, inputX, inputY + 0, inputZ, this.ZF, this.ZG, this.Zt, this.Zu);
        Random random = new Random();
        _height++;
        int curTrapPos = 0;
        int _widthIncre = 0;
        while (_widthIncre < this.Zt) {
            int _lengthIncre = 0;
            while (_lengthIncre < this.Zu) {
                if (curTrapPos < trapList.size() && ((hlx.utils.a) trapList.get(curTrapPos)).YG == _widthIncre && ((hlx.utils.a) trapList.get(curTrapPos)).YH == _lengthIncre) {
                    curTrapPos++;
                    if (Zs) {
                        hlx.utils.a _tmpItem = a(random, _widthIncre, _lengthIncre);
                        inList.add(new j(_tmpItem.YG, _tmpItem.YH, inputX + _lengthIncre, inputY + 1, inputZ + _widthIncre));
                    } else {
                        inList.add(new j(this.ZB, this.ZC, inputX + _lengthIncre, inputY + 1, inputZ + _widthIncre));
                    }
                } else {
                    int pos = random.nextInt(100);
                    if (_lengthIncre != 0) {
                        inList.add(new j(this.Zz, this.ZA, inputX + _lengthIncre, inputY + 1, inputZ + _widthIncre));
                    } else if (pos == 55) {
                        inList.add(new j(this.Zz, this.ZA, inputX + _lengthIncre, inputY + 1, inputZ + _widthIncre));
                    } else {
                        inList.add(new j(this.ZB, this.ZC, inputX + _lengthIncre, inputY + 1, inputZ + _widthIncre));
                    }
                }
                _lengthIncre++;
            }
            _widthIncre++;
        }
        _height++;
        a(inList, inputX, inputY + 2, inputZ, this.Zx, this.Zy, this.Zt, this.Zu);
        _height++;
        a(inList, inputX, inputY + 3, inputZ, this.Zv, this.Zw, this.Zt, this.Zu);
        for (_lengthIncre = 0; _lengthIncre <= this.Zt; _lengthIncre++) {
            inList.add(new j(20, 0, inputX - 1, inputY + 8, inputZ + _lengthIncre));
        }
        for (hight = 7; hight >= 3; hight--) {
            for (_lengthIncre = 0; _lengthIncre <= this.Zt; _lengthIncre++) {
                inList.add(new j(this.ZD, this.ZE, inputX - 2, inputY + hight, inputZ + _lengthIncre));
                inList.add(new j(this.ZJ, this.ZK, inputX - 1, inputY + hight, inputZ + _lengthIncre));
                inList.add(new j(this.ZJ, this.ZK, inputX + this.Zu, inputY + hight, inputZ + _lengthIncre));
            }
            for (_widthIncre = -2; _widthIncre <= this.Zu; _widthIncre++) {
                inList.add(new j(this.ZD, this.ZE, (inputX - 1) + _widthIncre, inputY + hight, inputZ - 1));
                inList.add(new j(this.ZD, this.ZE, (inputX + 1) + _widthIncre, inputY + hight, inputZ + this.Zt));
            }
        }
        for (hight = 3; hight >= 2; hight--) {
            for (_lengthIncre = 0; _lengthIncre <= this.Zt; _lengthIncre++) {
                inList.add(new j(this.ZD, this.ZE, inputX - 1, inputY + hight, inputZ + _lengthIncre));
                inList.add(new j(this.ZD, this.ZE, inputX - 2, inputY + hight, inputZ + _lengthIncre));
                inList.add(new j(this.ZH, this.ZI, inputX + this.Zu, inputY + hight, inputZ + _lengthIncre));
            }
            for (_widthIncre = -2; _widthIncre <= this.Zu; _widthIncre++) {
                inList.add(new j(this.ZD, this.ZE, (inputX - 1) + _widthIncre, inputY + hight, inputZ - 1));
                inList.add(new j(this.ZD, this.ZE, (inputX + 1) + _widthIncre, inputY + hight, inputZ + this.Zt));
            }
        }
        return inList;
    }

    private void a(List<j> inList, int inputX, int inputY, int inputZ, int inputId, int inputDmg, int increWidth, int increLength) {
        for (int i = 0; i < increLength; i++) {
            for (int j = 0; j < increWidth; j++) {
                inList.add(new j(inputId, inputDmg, inputX + i, inputY, inputZ + j));
            }
        }
    }

    private hlx.utils.a a(Random random, int abscissa, int ordinate) {
        if (abscissa % 3 == 0) {
            if (random.nextInt(100) < 80) {
                return new hlx.utils.a(this.Zz, this.ZA);
            }
            return new hlx.utils.a(this.ZB, this.ZC);
        } else if (abscissa % 3 == 5) {
            if (random.nextInt(100) < 80) {
                return new hlx.utils.a(this.Zz, this.ZA);
            }
            return new hlx.utils.a(this.ZB, this.ZC);
        } else if (ordinate % 2 == 0) {
            if (random.nextInt(100) < 70) {
                return new hlx.utils.a(this.Zz, this.ZA);
            }
            return new hlx.utils.a(this.ZB, this.ZC);
        } else if (ordinate % 3 != 0) {
            return new hlx.utils.a(this.ZB, this.ZC);
        } else {
            if (random.nextInt(100) < 70) {
                return new hlx.utils.a(this.Zz, this.ZA);
            }
            return new hlx.utils.a(this.ZB, this.ZC);
        }
    }

    public void tC() {
        ArrayList<j> itemList = new ArrayList();
        int tmpX = this.Yr;
        int tmpY = this.Ys;
        int tmpZ = this.Yt;
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                int i2 = 0;
                itemList.add(new j(0, i2, (tmpX - 2) + i, tmpY - 1, (tmpZ - 2) + j));
            }
        }
        h.D(itemList);
    }

    public void tH() {
        h.b((float) (((double) ((float) this.Nz)) + 3.5d), (float) this.NA, ((float) this.NB) - 3.0f);
        this.XZ = (this.Nz + 1) + (this.Zu + 1);
        this.Ya = this.NA - 1;
    }

    public boolean sQ() {
        int _tmpRoleX = (int) h.zF();
        int _tmpRoleZ = (int) h.zH();
        int originX = this.Nz + 4;
        int originZ = this.NB - (this.Zt / 2);
        if (this.NA + 2 < ((int) h.zG())) {
            return true;
        }
        if (_tmpRoleZ < originZ || this.Zt + originZ < _tmpRoleZ) {
            return true;
        }
        return false;
    }

    public boolean tP() {
        sA();
        int _tmpRoleY = (int) h.zG();
        if (f.R(h.zF()) < this.XZ || _tmpRoleY < this.Ya) {
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

    public void tJ() {
        h.D(k(this.Nz + 4, this.NA - 6, this.NB - (this.Zt / 2)));
    }

    private ArrayList<j> k(int inputX, int inputY, int inputZ) {
        int _lengthIncre;
        int hight;
        ArrayList<j> inList = new ArrayList();
        inList.addAll(a(this.Yr, this.Ys, this.Yt, 0, 0, true));
        a(inList, inputX, inputY + 8, inputZ, this.ZJ, this.ZK, this.Zt, this.Zu);
        int _height = 3;
        while (_height >= 0) {
            int i;
            int i2 = inputY + _height;
            int i3 = _height == 0 ? this.ZD : this.ZH;
            if (_height == 0) {
                i = this.ZE;
            } else {
                i = this.ZI;
            }
            a(inList, inputX, i2, inputZ, i3, i, this.Zt, this.Zu);
            _height--;
        }
        for (_lengthIncre = 0; _lengthIncre <= this.Zt; _lengthIncre++) {
            inList.add(new j(this.ZJ, this.ZK, inputX - 1, inputY + 8, inputZ + _lengthIncre));
        }
        for (hight = 7; hight >= 4; hight--) {
            int _widthIncre;
            for (_widthIncre = 0; _widthIncre <= this.Zt; _widthIncre++) {
                inList.add(new j(this.ZJ, this.ZK, inputX - 2, inputY + hight, inputZ + _widthIncre));
            }
            for (_lengthIncre = -2; _lengthIncre <= this.Zu; _lengthIncre++) {
                inList.add(new j(this.ZJ, this.ZK, (inputX - 1) + _lengthIncre, inputY + hight, inputZ - 1));
                inList.add(new j(this.ZJ, this.ZK, (inputX + 1) + _lengthIncre, inputY + hight, this.Zt + inputZ));
            }
        }
        for (hight = 3; hight >= 1; hight--) {
            for (_widthIncre = 0; _widthIncre <= this.Zt; _widthIncre++) {
                inList.add(new j(this.ZH, this.ZI, inputX - 1, inputY + hight, inputZ + _widthIncre));
                inList.add(new j(this.ZH, this.ZI, inputX - 2, inputY + hight, inputZ + _widthIncre));
            }
            for (_lengthIncre = -2; _lengthIncre <= this.Zu; _lengthIncre++) {
                inList.add(new j(this.ZH, this.ZI, (inputX - 1) + _lengthIncre, inputY + hight, inputZ - 1));
                inList.add(new j(this.ZH, this.ZI, (inputX + 1) + _lengthIncre, inputY + hight, this.Zt + inputZ));
            }
        }
        return inList;
    }

    public static synchronized a tQ() {
        a aVar;
        synchronized (a.class) {
            if (ZL == null) {
                ZL = new a();
                ZL.Zt = 15;
                ZL.Zu = 60;
                ZL.Zv = 72;
                ZL.Zw = 0;
                ZL.Zx = 12;
                ZL.Zy = 0;
                ZL.Zz = 46;
                ZL.ZA = 0;
                ZL.ZB = 7;
                ZL.ZC = 0;
                ZL.ZF = 0;
                ZL.ZG = 0;
                ZL.ZD = 7;
                ZL.ZE = 0;
                ZL.ZH = 2;
                ZL.ZI = 0;
                ZL.ZJ = 0;
                ZL.ZK = 0;
                ZL.YC = 6;
            }
            aVar = ZL;
        }
        return aVar;
    }
}
