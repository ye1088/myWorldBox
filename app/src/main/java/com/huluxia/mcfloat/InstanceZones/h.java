package com.huluxia.mcfloat.InstanceZones;

import com.huluxia.mcinterface.j;
import hlx.utils.a;
import java.util.ArrayList;
import java.util.Random;

/* compiled from: InstanceZonesEnv */
public class h {
    private static h XG;
    private int NA;
    private int NB;
    private int ND;
    private int NF;
    private int NG;
    private int NL;
    private int NM;
    private int NN;
    private int NP;
    private int NQ;
    private int NR;
    private int NT;
    private int NU;
    private int NV;
    private int NW;
    private int NX;
    private int NY;
    private int Nz;

    public static synchronized h sN() {
        h hVar;
        synchronized (h.class) {
            if (XG == null) {
                XG = new h();
                XG.ND = -1;
                XG.NF = 10;
                XG.NG = 15;
            }
            hVar = XG;
        }
        return hVar;
    }

    public void i(int inputX, int inputY, int inputZ) {
        this.Nz = inputX;
        this.NA = inputY;
        this.NB = inputZ;
    }

    private void a(int inputCorner01PosX, int inputCorner01PosY, int inputCorner01PosZ, int inputCorner02PosX, int inputCorner02PosY, int inputCorner02PosZ, int inputCorner03PosX, int inputCorner03PosY, int inputCorner03PosZ, int inputCorner04PosX, int inputCorner04PosY, int inputCorner04PosZ) {
        com.huluxia.mcinterface.h.b(this.Nz, this.NA, this.NB, inputCorner01PosX, inputCorner01PosY, inputCorner01PosZ, inputCorner02PosX, inputCorner02PosY, inputCorner02PosZ, inputCorner03PosX, inputCorner03PosY, inputCorner03PosZ, inputCorner04PosX, inputCorner04PosY, inputCorner04PosZ, 3);
    }

    private ArrayList<j> sO() {
        int nStartX = this.Nz - this.NG;
        int nEndX = this.Nz + this.NG;
        int nStartZ = this.NB - this.NG;
        int nEndZ = this.NB + this.NG;
        int nFloorY = this.NA - 2;
        int nFloorToopY = this.NA + this.NF;
        ArrayList<j> inList = new ArrayList();
        this.NL = nStartX;
        this.NN = nStartZ;
        this.NM = nFloorY + 1;
        this.NP = nStartX;
        this.NR = nEndZ;
        this.NQ = nFloorY + 1;
        this.NT = nEndX;
        this.NV = nStartZ;
        this.NU = nFloorY + 1;
        this.NW = nEndX;
        this.NY = nEndZ;
        this.NX = nFloorY + 1;
        a(this.NL, this.NM, this.NN, this.NP, this.NQ, this.NR, this.NT, this.NU, this.NV, this.NW, this.NX, this.NY);
        int k = 0;
        int i = nStartX;
        while (i < nEndX) {
            int j = nStartZ;
            int k2 = k;
            while (j < nEndZ) {
                a _tmpBlock = sP();
                inList.add(new j(_tmpBlock.YG, _tmpBlock.YH, i, nFloorY, j));
                j++;
                k2++;
            }
            i++;
            k = k2;
        }
        for (i = nStartX + 1; i < nEndX; i++) {
            for (j = nStartZ + 1; j < nEndZ; j++) {
                for (k = nFloorY + 1; k < ((this.NF + nFloorY) - this.ND) + 1; k++) {
                    inList.add(new j(0, 0, i, k, j));
                }
            }
        }
        inList.add(new j(323, 0, this.NL + 3, this.NM, this.NN + 3, 0));
        for (j = this.ND; j < this.NF; j++) {
            for (i = this.NG * -1; i < this.NG; i++) {
                a _tmp = dT(j);
                inList.add(new j(_tmp.YG, _tmp.YH, this.Nz + i, this.NA + j, this.NB + this.NG, 0));
                inList.add(new j(_tmp.YG, _tmp.YH, this.Nz + i, this.NA + j, this.NB - this.NG, 0));
                inList.add(new j(_tmp.YG, _tmp.YH, this.Nz - this.NG, this.NA + j, this.NB + i, 0));
                inList.add(new j(_tmp.YG, _tmp.YH, this.Nz + this.NG, this.NA + j, this.NB - i, 0));
            }
        }
        for (i = nStartX; i < nEndX; i++) {
            for (j = nStartZ; j < nEndZ; j++) {
                _tmpBlock = sP();
                inList.add(new j(_tmpBlock.YG, _tmpBlock.YH, i, nFloorToopY, j, 0));
            }
        }
        return inList;
    }

    private a dT(int pos) {
        if (pos >= 0 && pos < 3) {
            return new a(159, 14);
        }
        if (3 <= pos && pos < 8) {
            return new a(159, 4);
        }
        if (8 > pos || pos >= 13) {
            return new a(159, 11);
        }
        return new a(159, 5);
    }

    private a sP() {
        int pos = new Random().nextInt(100);
        if (pos % 2 == 0) {
            return new a(159, 14);
        }
        if (pos % 3 == 0) {
            return new a(159, 4);
        }
        if (pos % 5 == 0) {
            return new a(159, 5);
        }
        return new a(159, 11);
    }

    public boolean sQ() {
        int _tmpRoleX = (int) com.huluxia.mcinterface.h.zF();
        int _tmpRoleY = (int) com.huluxia.mcinterface.h.zG();
        int _tmpRoleZ = (int) com.huluxia.mcinterface.h.zH();
        int _EndX = this.Nz + this.NG;
        int _FloorY = this.NA - 3;
        int _FloorToopY = this.NA + this.NF;
        int _StartZ = this.NB - this.NG;
        int _EndZ = this.NB + this.NG;
        if (_tmpRoleX < this.Nz - this.NG || _EndX < _tmpRoleX) {
            return true;
        }
        if (_tmpRoleY < _FloorY || _FloorToopY < _tmpRoleY) {
            return true;
        }
        if (_tmpRoleZ < _StartZ || _EndZ < _tmpRoleZ) {
            return true;
        }
        return false;
    }

    public void C(int inputWide, int inputHigh) {
        if (inputWide > 0 && inputHigh > 0) {
            this.NF = inputHigh / 2;
            this.NG = inputWide / 2;
        }
        com.huluxia.mcinterface.h.D(sO());
    }

    private ArrayList<j> sR() {
        int j;
        int i;
        int nStartX = this.Nz - this.NG;
        int nEndX = this.Nz + this.NG;
        int nStartZ = this.NB - this.NG;
        int nEndZ = this.NB + this.NG;
        int nFloorY = this.NA - 3;
        int nFloorToopY = this.NA + this.NF;
        ArrayList<j> inList = new ArrayList();
        for (j = this.ND; j < this.NF; j++) {
            for (i = this.NG * -1; i < this.NG; i++) {
                inList.add(new j(0, 0, this.Nz + i, this.NA + j, this.NB + this.NG));
                inList.add(new j(0, 0, this.Nz + i, this.NA + j, this.NB - this.NG));
            }
        }
        j = this.ND;
        while (j < this.NF) {
            for (i = this.NG * -1; i < this.NG; i++) {
                inList.add(new j(0, 0, this.Nz - this.NG, this.NA + j, this.NB + i));
                inList.add(new j(0, 0, this.Nz + this.NG, this.NA + j, this.NB - i));
            }
            j++;
        }
        inList.add(new j(0, 0, this.NL + 3, this.NM, this.NN + 3, 0));
        int k = 0;
        int i2 = nStartX;
        int i3 = j;
        while (i2 <= nEndX) {
            i3 = nStartZ;
            int k2 = k;
            while (i3 <= nEndZ) {
                inList.add(new j(2, 0, i2, nFloorY, i3));
                inList.add(new j(2, 0, i2, nFloorY + 1, i3));
                i3++;
                k2++;
            }
            i2++;
            k = k2;
        }
        for (i2 = nStartX + 1; i2 < nEndX; i2++) {
            for (i3 = nStartZ + 1; i3 < nEndZ; i3++) {
                for (k = nFloorY + 2; k < nFloorY + 7; k++) {
                    inList.add(new j(0, 0, i2, k, i3));
                }
            }
        }
        for (i2 = nStartX; i2 < nEndX; i2++) {
            for (i3 = nStartZ; i3 < nEndZ; i3++) {
                inList.add(new j(0, 0, i2, nFloorToopY, i3));
            }
        }
        return inList;
    }

    public void sS() {
        int nStartX = this.Nz - this.NG;
        int nEndX = this.Nz + this.NG;
        int nStartZ = this.NB - this.NG;
        int nEndZ = this.NB + this.NG;
        int nFloorY = this.NA - 2;
        int nFloorToopY = this.NA + this.NF;
        ArrayList<j> inList = new ArrayList();
        for (int i = nStartX + 1; i < nEndX; i++) {
            for (int j = nStartZ + 1; j < nEndZ; j++) {
                inList.add(new j(0, 0, i, nFloorY + 1, j));
            }
        }
        com.huluxia.mcinterface.h.D(inList);
    }

    public void sT() {
        com.huluxia.mcinterface.h.D(sR());
    }

    private ArrayList<j> sU() {
        int nStartX = this.Nz - this.NG;
        int nEndX = this.Nz + this.NG;
        int nStartZ = this.NB - this.NG;
        int nEndZ = this.NB + this.NG;
        int nFloorY = this.NA - 2;
        ArrayList<j> inList = new ArrayList();
        for (int i = nStartX + 1; i < nEndX; i++) {
            for (int j = nStartZ + 1; j < nEndZ; j++) {
                for (int k = nFloorY + 2; k < nFloorY + 5; k++) {
                    inList.add(new j(0, 0, i, k, j));
                }
            }
        }
        return inList;
    }

    public void sV() {
        com.huluxia.mcinterface.h.D(sU());
        com.huluxia.mcinterface.h.qi();
    }
}
