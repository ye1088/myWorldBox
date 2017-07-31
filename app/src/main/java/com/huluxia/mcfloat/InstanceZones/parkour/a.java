package com.huluxia.mcfloat.InstanceZones.parkour;

import com.huluxia.framework.base.log.HLog;
import com.huluxia.mcfloat.InstanceZones.structrue.d;
import com.huluxia.mcgame.g;
import com.huluxia.mcinterface.h;
import com.huluxia.mcinterface.j;
import com.huluxia.mcsdk.DTSDKManagerEx;
import hlx.launch.game.c;
import hlx.utils.f;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/* compiled from: InsZoneParkourEnv */
public class a {
    private static a YE;
    private int NA;
    private int NB;
    private int Nz;
    private int YA;
    private int YB;
    private int YC;
    private List<d> YD;
    private int Yr;
    private int Ys;
    private int Yt;
    private int Yu;
    private int Yv;
    private int Yw;
    private int Yx;
    private int Yy;
    private int Yz;

    public void tG() {
        this.Nz = f.R(h.zF()) + 3;
        this.NA = YE.YC - 6;
        this.NB = f.R(h.zH()) - (this.Yu / 2);
        this.Yr = f.R(h.zF());
        this.Ys = (int) h.zG();
        this.Yt = f.R(h.zH());
        h.D(a(this.Yr, this.Ys, this.Yt, 20, 0, false));
        h.D(a(this.Nz, this.NA, this.NB, this.YD));
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

    private LinkedList<hlx.utils.a> a(LinkedList<hlx.utils.a> safeList) {
        return safeList;
    }

    private ArrayList<j> a(int inputX, int inputY, int inputZ, List<d> trapList) {
        if (trapList == null) {
            return null;
        }
        int i;
        int _widthIncre;
        int _lengthIncre;
        ArrayList<j> inList = new ArrayList();
        int _height = -4;
        for (i = 0; i < 4; i++) {
            _height++;
            for (_widthIncre = 0; _widthIncre < this.Yv; _widthIncre++) {
                for (_lengthIncre = 0; _lengthIncre < this.Yu; _lengthIncre++) {
                    inList.add(new j(this.Yy, this.Yz, inputX + _widthIncre, inputY + _height, inputZ + _lengthIncre));
                }
            }
        }
        _height++;
        int curTrapPos = 0;
        _lengthIncre = 0;
        while (_lengthIncre < this.Yu) {
            _widthIncre = 0;
            while (_widthIncre < this.Yv) {
                if (curTrapPos < trapList.size() && ((d) trapList.get(curTrapPos)).YG == _lengthIncre && ((d) trapList.get(curTrapPos)).YH == _widthIncre) {
                    hlx.utils.a _tmpBlock = sP();
                    inList.add(new j(_tmpBlock.YG, _tmpBlock.YH, inputX + _widthIncre, inputY + _height, inputZ + _lengthIncre));
                    curTrapPos++;
                } else {
                    inList.add(new j(this.Yw, this.Yx, inputX + _widthIncre, inputY + _height, inputZ + _lengthIncre));
                }
                _widthIncre++;
            }
            _lengthIncre++;
        }
        for (i = 0; i < 4; i++) {
            _height++;
            for (_widthIncre = 0; _widthIncre < this.Yv; _widthIncre++) {
                for (_lengthIncre = 0; _lengthIncre < this.Yu; _lengthIncre++) {
                    inList.add(new j(this.YA, this.YB, inputX + _widthIncre, inputY + _height, inputZ + _lengthIncre));
                }
            }
        }
        for (int hight = 6; hight >= -5; hight--) {
            for (_lengthIncre = 0; _lengthIncre <= this.Yu; _lengthIncre++) {
                inList.add(new j(this.YA, this.YB, inputX - 1, inputY + hight, inputZ + _lengthIncre));
                inList.add(new j(this.YA, this.YB, inputX - 2, inputY + hight, inputZ + _lengthIncre));
                inList.add(new j(this.YA, this.YB, inputX - 3, inputY + hight, inputZ + _lengthIncre));
                inList.add(new j(this.YA, this.YB, inputX - 4, inputY + hight, inputZ + _lengthIncre));
            }
            for (_widthIncre = -2; _widthIncre <= this.Yv; _widthIncre++) {
                inList.add(new j(this.YA, this.YB, (inputX - 1) + _widthIncre, inputY + hight, inputZ - 1));
                inList.add(new j(this.YA, this.YB, (inputX - 1) + _widthIncre, inputY + hight, inputZ - 2));
                inList.add(new j(this.YA, this.YB, (inputX - 1) + _widthIncre, inputY + hight, inputZ - 3));
                inList.add(new j(this.YA, this.YB, (inputX - 1) + _widthIncre, inputY + hight, inputZ - 4));
                inList.add(new j(this.YA, this.YB, (inputX + 1) + _widthIncre, inputY + hight, this.Yu + inputZ));
                inList.add(new j(this.YA, this.YB, (inputX + 1) + _widthIncre, inputY + hight, (this.Yu + inputZ) + 1));
                inList.add(new j(this.YA, this.YB, (inputX + 1) + _widthIncre, inputY + hight, (this.Yu + inputZ) + 2));
                inList.add(new j(this.YA, this.YB, (inputX + 1) + _widthIncre, inputY + hight, (this.Yu + inputZ) + 3));
            }
        }
        return inList;
    }

    private hlx.utils.a sP() {
        int pos = new Random().nextInt(100);
        if (pos % 2 == 0) {
            return new hlx.utils.a(159, 14);
        }
        if (pos % 3 == 0) {
            return new hlx.utils.a(159, 4);
        }
        if (pos % 5 == 0) {
            return new hlx.utils.a(159, 5);
        }
        return new hlx.utils.a(159, 11);
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
        h.b(((float) this.Nz) + 2.0f, ((float) this.NA) + 4.0f, ((float) this.NB) + 1.0f);
    }

    public boolean sQ() {
        int _tmpRoleX = f.R(h.zF());
        int _tmpRoleY = (int) h.zG();
        int _tmpRoleZ = f.R(h.zH());
        if (_tmpRoleY < this.NA || this.NA + 6 < _tmpRoleY) {
            return true;
        }
        if (_tmpRoleX < this.Nz - 1 || (this.Nz + this.Yv) + 1 < _tmpRoleX) {
            return true;
        }
        if (_tmpRoleZ < this.NB - 1 || (this.NB + this.Yu) + 1 < _tmpRoleZ) {
            return true;
        }
        return false;
    }

    public boolean tI() {
        sA();
        int _tmpRoleX = f.R(h.zF());
        int _tmpRoleY = (int) h.zG();
        int _tmpRoleZ = f.R(h.zH());
        int a0 = (this.Nz + 72) - 1;
        int b0 = this.NB - 1;
        if (this.NA > _tmpRoleY || _tmpRoleY > this.NA + 6 || a0 > _tmpRoleX || _tmpRoleX > a0 + 5 || b0 > _tmpRoleZ || _tmpRoleZ > b0 + 3) {
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
        h.D(k(this.Nz, this.NA, this.NB));
    }

    private ArrayList<j> k(int inputX, int inputY, int inputZ) {
        ArrayList<j> inList = new ArrayList();
        inList.addAll(a(this.Yr, this.Ys, this.Yt, 0, 0, true));
        int a0 = (this.Nz + 72) - 1;
        int b0 = this.NB - 1;
        int _height = 3;
        while (_height > 0) {
            int _widthIncre = 0;
            while (_widthIncre < this.Yv) {
                int _lengthIncre = 0;
                while (_lengthIncre < this.Yu) {
                    if (this.NA > inputY + _height || inputY + _height > this.NA + 6 || a0 > inputX + _widthIncre || inputX + _widthIncre > a0 + 5 || b0 > inputZ + _lengthIncre || inputZ + _lengthIncre > b0 + 3 || !tI()) {
                        inList.add(new j(this.YA, this.YB, inputX + _widthIncre, inputY + _height, inputZ + _lengthIncre));
                    }
                    _lengthIncre++;
                }
                _widthIncre++;
            }
            _height--;
        }
        return inList;
    }

    public void b(int width, int lenth, int heigh, List<d> routeBlock) {
        YE.Yu = width;
        YE.Yv = lenth;
        YE.YC = heigh;
        YE.YD = routeBlock;
    }

    public static synchronized a tK() {
        a aVar;
        synchronized (a.class) {
            if (YE == null) {
                YE = new a();
                YE.Yw = 0;
                YE.Yx = 0;
                YE.Yy = 0;
                YE.Yz = 0;
                YE.YA = 0;
                YE.YB = 0;
            }
            aVar = YE;
        }
        return aVar;
    }
}
