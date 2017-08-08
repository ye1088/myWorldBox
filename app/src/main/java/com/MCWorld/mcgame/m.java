package com.MCWorld.mcgame;

import com.MCWorld.mcsdk.DTSDKManagerEx;
import com.MCWorld.mcsdk.dtlib.h;

/* compiled from: DTGameType */
public class m {
    private static volatile boolean aeK = false;
    private static volatile boolean aeL = false;
    private static int aeM = 0;
    private static volatile int aeN = 0;

    public static int getGameType() {
        return wL();
    }

    public static void setGameType(int paramInt) {
        if (g.wc() != 1) {
            return;
        }
        if (h.CW().CX() == 0) {
            t.bx(DTSDKManagerEx.dtNativePlayerIsFlying());
            c(paramInt, false);
            bn(true);
            wH();
        } else if (h.CW().CX() == 1) {
            t.bx(DTSDKManagerEx.dtNativePlayerIsFlying());
            c(paramInt, false);
            bo(true);
            wH();
        } else if (h.CW().CX() == 2) {
            c(paramInt, true);
            gy(paramInt);
            bn(true);
        } else if (h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CX() == 7) {
            c(paramInt, true);
            gy(paramInt);
            bn(true);
        }
    }

    public static boolean wF() {
        if (h.CW().CX() == 0) {
            if (wI() && g.wd() == 0) {
                t.gI(2);
                bn(false);
            }
        } else if (h.CW().CX() == 1) {
            if (wJ()) {
                t.gI(2);
                bo(false);
            }
        } else if (h.CW().CX() == 2) {
            if (wI() && g.wd() == 0) {
                t.gI(1);
                bn(false);
            }
        } else if ((h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CX() == 7) && wI() && g.wd() == 0) {
            t.gI(1);
            bn(false);
        }
        return false;
    }

    public static void wG() {
        wH();
    }

    public static void wH() {
        gy(wM());
    }

    private static boolean wI() {
        return aeK;
    }

    private static void bn(boolean bUpdateGameType) {
        aeK = bUpdateGameType;
    }

    public static boolean wJ() {
        return aeL;
    }

    public static void bo(boolean updateGameTypeV11) {
        aeL = updateGameTypeV11;
    }

    private static int wK() {
        return aeM;
    }

    private static void gx(int nGameTypeVal) {
        aeM = nGameTypeVal;
    }

    public static int wL() {
        return aeN;
    }

    private static void gy(int nCurGameType) {
        aeN = nCurGameType;
    }

    private static int wM() {
        try {
            return DTSDKManagerEx.Cs();
        } catch (Exception e) {
            return -1;
        }
    }

    private static void c(int paramInt, boolean useDTSDK) {
        try {
            DTSDKManagerEx.g(paramInt, useDTSDK);
        } catch (Exception e) {
        }
    }
}
