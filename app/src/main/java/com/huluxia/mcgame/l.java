package com.huluxia.mcgame;

import com.huluxia.mcsdk.DTSDKManagerEx;

/* compiled from: DTGameTime */
public class l {
    private static volatile boolean aeH = false;
    private static volatile int aeI = 0;
    private static volatile int aeJ = 0;

    public static int wx() {
        return wD();
    }

    public static void gt(int paramInt) {
        if (g.wc() == 1) {
            gu(paramInt);
            bm(true);
        }
    }

    public static void wy() {
        if (wB()) {
            gw(wC());
            bm(false);
        }
        gv(wE());
    }

    public static void wz() {
        gv(wE());
    }

    public static void wA() {
        gv(wE());
    }

    private static boolean wB() {
        return aeH;
    }

    private static void bm(boolean bUpdateGameTime) {
        aeH = bUpdateGameTime;
    }

    private static int wC() {
        return aeI;
    }

    private static void gu(int nGameTimeVal) {
        aeI = nGameTimeVal;
    }

    private static int wD() {
        return aeJ;
    }

    private static void gv(int nCurGameTime) {
        aeJ = nCurGameTime;
    }

    private static int wE() {
        try {
            return (int) DTSDKManagerEx.Cr();
        } catch (Exception e) {
            return -1;
        }
    }

    private static void gw(int paramInt) {
        try {
            DTSDKManagerEx.ag((long) paramInt);
        } catch (Exception e) {
        }
    }
}
