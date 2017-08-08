package com.MCWorld.mcgame;

import com.MCWorld.mcsdk.DTSDKManagerEx;
import com.MCWorld.mcsdk.dtlib.h;

/* compiled from: DTPlayerJump */
public class w {
    private static final int AXIS_Y = 1;
    private static volatile boolean afB = false;
    private static int afC = 0;
    private static float afD = 0.0f;
    private static int afE = 0;
    public static final int afF = 8;
    public static final int afG = -9;

    public static void gM(int inputHeight) {
        if (g.wc() == 1) {
            bC(true);
        }
    }

    public static void xO() {
        if (xP()) {
            xQ();
            bC(false);
        }
    }

    public static boolean xP() {
        return afB;
    }

    public static void bC(boolean startJumpFlag) {
        afB = startJumpFlag;
    }

    public static void xQ() {
        float f1;
        if (h.CW().CX() == 0) {
            int tmpPlayEnt = DTSDKManagerEx.Cp();
            f1 = DTSDKManagerEx.ab(tmpPlayEnt, 1);
            if (((double) f1) < -0.07d && ((double) f1) > -0.08d) {
                DTSDKManagerEx.a(tmpPlayEnt, 0.45f, 1);
            }
        } else if (h.CW().CX() == 1) {
            long tmpPlayEnt2 = DTSDKManagerEx.Cq();
            f1 = DTSDKManagerEx.k(tmpPlayEnt2, 1);
            if (((double) f1) < -0.07d && ((double) f1) > -0.08d) {
                DTSDKManagerEx.a(tmpPlayEnt2, 0.45f, 1);
            }
        }
    }

    private static void xR() {
        if (xU() >= 0) {
            float nTmpGapVal;
            xW();
            int nTmpJumpTick = xU();
            if (nTmpJumpTick >= 4) {
                nTmpGapVal = (float) (((double) ((float) (8 - nTmpJumpTick))) * 0.1d);
            } else if (nTmpJumpTick >= 0) {
                nTmpGapVal = (float) (((double) ((float) nTmpJumpTick)) * 0.1d);
            } else {
                return;
            }
            if (h.CW().CX() == 0) {
                DTSDKManagerEx.a(DTSDKManagerEx.Cp(), nTmpGapVal, 1);
            } else if (h.CW().CX() == 1) {
                DTSDKManagerEx.a(DTSDKManagerEx.Cq(), nTmpGapVal, 1);
            }
        } else if (xU() >= -9) {
            xW();
        }
    }

    public static int xS() {
        return afC;
    }

    public static void gN(int playerJumpHeight) {
        afC = playerJumpHeight;
    }

    public static float xT() {
        return afD;
    }

    public static void q(float fPlayerJumpExpand) {
        afD = fPlayerJumpExpand;
    }

    public static int xU() {
        return afE;
    }

    public static void xV() {
        afE = 8;
    }

    public static void gO(int nInputVal) {
        afE = nInputVal;
    }

    public static void xW() {
        afE--;
    }
}
