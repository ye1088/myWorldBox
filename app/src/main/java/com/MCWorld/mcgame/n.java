package com.MCWorld.mcgame;

import com.MCWorld.mcgame.biont.a;
import com.MCWorld.mcinterface.i;
import com.MCWorld.mcsdk.DTSDKManagerEx;
import com.MCWorld.mcsdk.dtlib.h;
import java.util.ArrayList;

/* compiled from: DTMapBiont */
public class n {
    private static final boolean DEBUG = false;
    public static final int OA = 2;
    public static final int OB = 3;
    public static final int OC = 4;
    public static final int OD = 5;
    public static final int OE = 6;
    public static final int OF = 7;
    public static final int OG = 8;
    public static final int OH = 9;
    public static final int OI = 10;
    public static final int OJ = 11;
    public static final int OL = 12;
    public static final int OM = 13;
    public static final int OO = 14;
    public static final int Oy = 0;
    public static final int Oz = 1;
    private static Object aeO = new Object();
    private static boolean aeP = false;
    private static Object aeQ = new Object();
    private static boolean aeR = false;
    public static ArrayList<i> aeS = new ArrayList(27);
    private static Object aeT = new Object();
    private static boolean aeU = false;
    private static int aeV = 0;
    private static int aeW = 0;
    private static Object aeX = new Object();
    private static boolean aeY = false;
    private static a aeZ = new a();

    public static boolean wN() {
        boolean z = true;
        synchronized (aeO) {
            aeP = true;
            try {
                aeO.wait(1);
            } catch (InterruptedException e) {
                z = false;
            }
        }
        return z;
    }

    public static void wO() {
        try {
            xc();
        } catch (Exception e) {
        }
    }

    public static void wP() {
        if (aeP) {
            synchronized (aeO) {
                wO();
                aeP = false;
                aeO.notify();
            }
        }
    }

    public static ArrayList<i> wQ() {
        if (g.wc() != 1 || g.vW() == 0) {
            return null;
        }
        wN();
        return aeS;
    }

    public static boolean wR() {
        boolean z = true;
        synchronized (aeQ) {
            aeR = true;
            try {
                aeQ.wait(1000);
            } catch (InterruptedException e) {
                z = false;
            }
        }
        return z;
    }

    public static void wS() {
        try {
            wU();
        } catch (Exception e) {
        }
    }

    public static void wT() {
        if (aeR) {
            synchronized (aeQ) {
                wS();
                aeR = false;
                aeQ.notify();
            }
        }
    }

    private static void wU() {
        int i;
        int _tmpNetPlayerArrayCnt = 0;
        long[] _tmpNetPlayerArray = new long[5];
        for (i = 0; i < 5; i++) {
            _tmpNetPlayerArray[i] = 0;
        }
        if (h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CX() == 7) {
            try {
                int mapBiontSize = g.aea.size();
                for (i = 0; mapBiontSize > i; i++) {
                    if (g.aea.get(i) != null) {
                        long nlongMapBiontId = ((Long) g.aea.get(i)).longValue();
                        if (DTSDKManagerEx.ai(nlongMapBiontId) != 63) {
                            continue;
                        } else if (_tmpNetPlayerArrayCnt + 1 >= 5) {
                            break;
                        } else {
                            _tmpNetPlayerArray[_tmpNetPlayerArrayCnt] = nlongMapBiontId;
                            _tmpNetPlayerArrayCnt++;
                        }
                    }
                }
                com.MCWorld.mcgame.netuser.a.yQ().a(_tmpNetPlayerArray);
                com.MCWorld.mcgame.netuser.a.yQ().hg(_tmpNetPlayerArrayCnt);
                for (i = 0; i < 5; i++) {
                    if (_tmpNetPlayerArray[i] != 0) {
                        long _tmpEntityId = _tmpNetPlayerArray[i];
                        com.MCWorld.mcgame.netuser.a.yQ().a(_tmpEntityId, DTSDKManagerEx.e(_tmpEntityId, 0), DTSDKManagerEx.e(_tmpEntityId, 1), DTSDKManagerEx.e(_tmpEntityId, 2), DTSDKManagerEx.at(_tmpEntityId));
                    }
                }
                com.MCWorld.mcgame.netuser.a.yQ().yS();
            } catch (Exception e) {
            }
        }
    }

    public static void vH() {
        wX();
        if (h.CW().CX() == 5 || h.CW().CX() == 7) {
            wZ();
        }
    }

    public static void wV() {
        if (g.vM()) {
            wP();
        }
    }

    public static void wW() {
        if (g.vM()) {
            wT();
        }
    }

    public static void init() {
        xe();
    }

    public static void quit() {
        xd();
    }

    public static boolean J(int id, int count) {
        if (id == 0 || count == 0) {
            return false;
        }
        synchronized (aeT) {
            aeV = id;
            aeW = count;
            aeU = true;
            try {
                aeT.wait(100);
            } catch (InterruptedException e) {
                return false;
            }
        }
        return true;
    }

    public static void t(int id, int count, int dmg) {
        if (id != 0 && count != 0) {
            int k = 0;
            while (k < count) {
                try {
                    c.a(y.gU(0), y.gU(1), y.gU(2), id, null);
                    k++;
                } catch (Exception e) {
                    return;
                }
            }
        }
    }

    public static void wX() {
        if (aeU) {
            synchronized (aeT) {
                t(aeV, aeW, 0);
                aeU = false;
                aeT.notify();
            }
        }
    }

    public static boolean a(float pos_x, float pos_y, float pos_z, int biological_id, int inputCount, String inputName, int inputMaxHealth, int inputHat, int inputCoat, int inputTrousers, int inputShoes, int inputWeapon) {
        if (biological_id == 0 || inputCount == 0) {
            return false;
        }
        synchronized (aeX) {
            aeZ.b(pos_x, pos_y, pos_z, biological_id, inputCount, inputName, inputMaxHealth, inputHat, inputCoat, inputTrousers, inputShoes, inputWeapon);
            aeY = true;
            try {
                aeX.wait(100);
            } catch (InterruptedException e) {
                return false;
            }
        }
        return true;
    }

    public static void wY() {
        try {
            if (aeZ.getId() != 0 && aeZ.getCount() != 0) {
                for (int k = 0; k < aeZ.getCount(); k++) {
                    c.a(aeZ.getX(), aeZ.getY(), aeZ.getZ(), aeZ.getId(), aeZ.getName(), aeZ.getMaxHealth(), aeZ.yL(), aeZ.yM(), aeZ.yN(), aeZ.yO(), aeZ.yP());
                }
            }
        } catch (Exception e) {
        }
    }

    public static void wZ() {
        if (aeY) {
            synchronized (aeX) {
                wY();
                aeY = false;
                aeX.notify();
            }
        }
    }

    private static void gz(int inputBiontId) {
        int _mapBiontIndexMax = com.MCWorld.mcinterface.h.zr();
        for (int i = 0; i < _mapBiontIndexMax; i++) {
            i pTmpMCMapBiont = (i) aeS.get(i);
            if (pTmpMCMapBiont.getId() == inputBiontId) {
                pTmpMCMapBiont.zU();
                return;
            }
        }
    }

    private static void gA(int inputBiontId) {
        int _mapBiontIndexMax = com.MCWorld.mcinterface.h.zr();
        for (int i = 0; i < _mapBiontIndexMax; i++) {
            i pTmpMCMapBiont = (i) aeS.get(i);
            if (pTmpMCMapBiont.getId() == inputBiontId) {
                pTmpMCMapBiont.zT();
                return;
            }
        }
    }

    private static void xa() {
        int _mapBiontIndexMax = com.MCWorld.mcinterface.h.zr();
        for (int i = 0; i < _mapBiontIndexMax; i++) {
            ((i) aeS.get(i)).hu(0);
        }
    }

    private static void xb() {
        int _mapBiontIndexMax = com.MCWorld.mcinterface.h.zr();
        for (int i = 0; i < _mapBiontIndexMax; i++) {
            ((i) aeS.get(i)).xb();
        }
    }

    private static void xc() {
        xa();
        int mapBiontSize;
        int i;
        if (h.CW().CX() == 0) {
            mapBiontSize = g.adZ.size();
            for (i = 0; mapBiontSize > i; i++) {
                if (g.adZ.get(i) != null) {
                    gA(DTSDKManagerEx.in(((Integer) g.adZ.get(i)).intValue()));
                }
            }
        } else if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CX() == 7) {
            mapBiontSize = g.aea.size();
            for (i = 0; mapBiontSize > i; i++) {
                if (g.aea.get(i) != null) {
                    gA(DTSDKManagerEx.ai(((Long) g.aea.get(i)).longValue()));
                }
            }
        }
        xb();
    }

    private static void xd() {
        aeS.clear();
        aeS = null;
    }

    private static void xe() {
        int[] MapBiontValArray = new int[]{10, 11, 12, 13, 14, 15, 16, 32, 33, 34, 35, 36, 37, 38, 39, 41, 17, 19, 20, 21, 22, 40, 42, 43, 44, 18, 45, 23, 24, 25, 26, 27, 47, 48};
        aeS.clear();
        int _mapBiontIndexMax = com.MCWorld.mcinterface.h.zr();
        for (int i = 0; i < _mapBiontIndexMax; i++) {
            aeS.add(new i(MapBiontValArray[i], 0, 0));
        }
    }
}
