package com.huluxia.mcgame;

import com.huluxia.aa.mcspirit;
import com.huluxia.mcsdk.DTSDKManagerEx;
import com.huluxia.mcsdk.dtlib.h;

/* compiled from: DTPlayerFly */
public class t {
    private static final long UM = 1000;
    private static int afm = 0;
    private static volatile int afn = 0;
    private static volatile boolean afo = false;
    private static int afp = 0;
    private static volatile boolean afq = false;
    private static volatile boolean afr = false;
    private static volatile boolean afs = false;
    private static long aft = 0;

    public static boolean xt() {
        return xB();
    }

    public static void bu(boolean bPlayerCanFly) {
        if (g.wc() == 1) {
            bz(bPlayerCanFly);
            gI(1);
        }
    }

    public static void bv(boolean bFallWithNoDamage) {
        if (g.wc() == 1) {
            gJ(bFallWithNoDamage ? 1 : 0);
            bw(true);
        }
    }

    public static void xu() {
        xE();
    }

    public static void xv() {
        by(DTSDKManagerEx.dtNativePlayerCanFly());
    }

    public static boolean xw() {
        try {
            return DTSDKManagerEx.dtNativePlayerIsFlying();
        } catch (Exception e) {
            return false;
        }
    }

    public static void gH(int nFirstPlayerFly) {
        afm = nFirstPlayerFly;
    }

    public static int xx() {
        return afn;
    }

    public static void gI(int updatePlayerFly) {
        afn = updatePlayerFly;
    }

    public static boolean xy() {
        return afo;
    }

    public static void bw(boolean updatePlayerFallWithNoDamage) {
        afo = updatePlayerFallWithNoDamage;
    }

    public static int xz() {
        return afp;
    }

    public static void gJ(int mFallWithNoDamage) {
        afp = mFallWithNoDamage;
    }

    public static boolean xA() {
        return afs;
    }

    public static void bx(boolean bCurPlayerIsFlying) {
        afs = bCurPlayerIsFlying;
    }

    public static boolean xB() {
        return afq;
    }

    public static void by(boolean bCurPlayerCanFly) {
        afq = bCurPlayerCanFly;
    }

    public static boolean xC() {
        return afr;
    }

    public static void bz(boolean bAlterPlayerCanFly) {
        afr = bAlterPlayerCanFly;
    }

    public static long xD() {
        return aft;
    }

    public static void V(long bUpdatePlayerCanFlyConfigTick) {
        aft = bUpdatePlayerCanFlyConfigTick;
    }

    private static void xE() {
        try {
            if (xy()) {
                if (h.CW().CX() == 0 || h.CW().CX() == 1) {
                    mcspirit.c(xz());
                } else if (h.CW().CX() == 2) {
                }
                bw(false);
            }
            if (xx() == 1) {
                if (xC()) {
                    DTSDKManagerEx.dtNativePlayerSetCanFly(true);
                    DTSDKManagerEx.dtNativePlayerSetFlying(true);
                } else {
                    DTSDKManagerEx.dtNativePlayerSetCanFly(false);
                    DTSDKManagerEx.dtNativePlayerSetFlying(false);
                }
                gH(1);
                gI(0);
            } else if (xx() == 2) {
                if (!xC()) {
                    DTSDKManagerEx.dtNativePlayerSetCanFly(false);
                    DTSDKManagerEx.dtNativePlayerSetFlying(false);
                } else if (xA()) {
                    DTSDKManagerEx.dtNativePlayerSetCanFly(true);
                    DTSDKManagerEx.dtNativePlayerSetFlying(true);
                } else {
                    DTSDKManagerEx.dtNativePlayerSetCanFly(true);
                    DTSDKManagerEx.dtNativePlayerSetFlying(false);
                }
                gH(1);
                gI(0);
            }
            if (System.currentTimeMillis() > 1000 + xD()) {
                xv();
            }
        } catch (Exception e) {
        }
    }
}
