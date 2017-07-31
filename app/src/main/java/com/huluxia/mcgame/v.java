package com.huluxia.mcgame;

import com.huluxia.mcinterface.e;
import com.huluxia.mcinterface.h;
import com.huluxia.mcsdk.DTSDKManagerEx;

/* compiled from: DTPlayerInvincible */
public class v {
    private static boolean afA = false;
    private static int afw = 0;
    private static volatile int afx = 0;
    private static final int afy = 30000;
    private static final int afz = 60;

    public static int xJ() {
        return afw;
    }

    public static void bB(boolean bUpdatePlayerInvincible) {
        if (g.wc() == 1) {
            afA = bUpdatePlayerInvincible;
        }
    }

    public static void xK() {
        if (m.getGameType() == 0) {
            xN();
            if (h.zx() == 0) {
                gL(DTSDKManagerEx.io(DTSDKManagerEx.Cp()));
            } else if (h.zx() == 1) {
                gL(DTSDKManagerEx.al(DTSDKManagerEx.Cq()));
            } else if (h.zx() == 2 || h.zx() == 3 || h.zx() == 5 || h.zx() == 7) {
                gL(DTSDKManagerEx.al(DTSDKManagerEx.Cq()));
            }
        }
    }

    private static void gL(int bloodVal) {
        afw = bloodVal;
    }

    public static int xL() {
        return afx;
    }

    public static void d(int lockBloodVal, boolean lock) {
        bB(lock);
        if (lock) {
            afx = lockBloodVal;
        }
        if (m.getGameType() != 1) {
            if (lock) {
                afx = lockBloodVal;
                DTSDKManagerEx.ii(1);
                return;
            }
            if (com.huluxia.mcsdk.dtlib.h.CW().CX() == 2 || com.huluxia.mcsdk.dtlib.h.CW().CX() == 3 || com.huluxia.mcsdk.dtlib.h.CW().CX() == 5 || com.huluxia.mcsdk.dtlib.h.CW().CX() == 7) {
                DTSDKManagerEx.ii(0);
                if (lockBloodVal > 0) {
                    DTSDKManagerEx.i(DTSDKManagerEx.Cq(), lockBloodVal);
                    return;
                }
            }
            if (xJ() > 0 || lockBloodVal > 0) {
                DTSDKManagerEx.il(lockBloodVal);
                if (lockBloodVal <= 0) {
                    s.bt(true);
                    h.zM().se();
                }
            }
        }
    }

    public static void init() {
        if (com.huluxia.mcsdk.dtlib.h.CW().CX() == 0) {
            e.ahz = afy;
        } else if (com.huluxia.mcsdk.dtlib.h.CW().CX() == 1) {
            e.ahz = 60;
        } else if (com.huluxia.mcsdk.dtlib.h.CW().CX() == 2) {
            e.ahz = 20;
        }
    }

    private static boolean xM() {
        return afA;
    }

    private static void xN() {
        try {
            if (!xM()) {
                return;
            }
            if (com.huluxia.mcsdk.dtlib.h.CW().CX() == 0) {
                DTSDKManagerEx.il(xL());
            } else if (com.huluxia.mcsdk.dtlib.h.CW().CX() == 1) {
                DTSDKManagerEx.il(xL());
            } else if (com.huluxia.mcsdk.dtlib.h.CW().CX() != 2) {
            }
        } catch (Exception e) {
        }
    }
}
