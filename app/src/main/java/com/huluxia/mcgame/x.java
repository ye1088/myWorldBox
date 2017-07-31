package com.huluxia.mcgame;

import com.huluxia.aa.mcspirit;

/* compiled from: DTPlayerLevel */
public class x {
    private static volatile int afH = 0;
    private static volatile int afI = 0;
    private static volatile boolean afJ = false;
    private static volatile int afK = 0;
    private static int afL;

    public static void gP(int level) {
        gR(level);
        bD(true);
    }

    public static void xX() {
        xY();
    }

    private static void xY() {
        if (yc()) {
            bD(false);
            mcspirit.r();
            if (afH > 0) {
                gQ(afH);
                mcspirit.q(afH);
            }
            afI = mcspirit.p();
        }
    }

    public static int xZ() {
        return afK;
    }

    public static void gQ(int mLastPlayerLevel) {
        afK = mLastPlayerLevel;
    }

    public static int ya() {
        return afH;
    }

    public static void gR(int mSetPlayerLevel) {
        afH = mSetPlayerLevel;
    }

    public static int yb() {
        return afI;
    }

    public static void gS(int mCurPlayerLevel) {
        afI = mCurPlayerLevel;
    }

    public static boolean yc() {
        return afJ;
    }

    public static void bD(boolean bRefreshPlayerLevel) {
        afJ = bRefreshPlayerLevel;
    }

    public static int yd() {
        return afL;
    }

    public static void gT(int inputDeathLevel) {
        afL = inputDeathLevel;
    }
}
