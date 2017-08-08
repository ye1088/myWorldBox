package com.MCWorld.mcgame;

import com.MCWorld.mcsdk.DTSDKManagerEx;
import com.MCWorld.mcsdk.dtlib.h;

/* compiled from: DTPlayerPos */
public class y {
    private static final int AXIS_X = 0;
    private static final int AXIS_Y = 1;
    private static final int AXIS_Z = 2;
    private static volatile boolean afM = false;
    private static volatile float afN;
    private static volatile float afO;
    private static volatile float afP;
    private static volatile float afQ;
    private static volatile float afR;
    private static volatile float afS;
    private static volatile float afT;
    private static volatile float afU;
    private static volatile float afV;

    public static boolean a(float x, float y, float z) {
        if (g.wc() != 1) {
            return false;
        }
        if (x == 0.0f && y == 0.0f && z == 0.0f) {
            return false;
        }
        A(x);
        B(y);
        C(z);
        bE(true);
        return true;
    }

    public static float gU(int index) {
        if (g.wc() == 1) {
            if (index == 0) {
                return yj();
            }
            if (index == 1) {
                return yk();
            }
            if (index == 2) {
                return yl();
            }
        }
        return 0.0f;
    }

    public static float gV(int index) {
        if (g.wc() == 1) {
            if (index == 0) {
                return yg();
            }
            if (index == 1) {
                return yh();
            }
            if (index == 2) {
                return yi();
            }
        }
        return 0.0f;
    }

    public static void ye() {
        if (yf()) {
            ae.gY(0);
            yq();
            bE(false);
        }
        yp();
    }

    private static boolean yf() {
        return afM;
    }

    private static void bE(boolean bUpdatePlayerPos) {
        afM = bUpdatePlayerPos;
    }

    public static float yg() {
        return afT;
    }

    public static void r(float death_x) {
        afT = death_x;
    }

    public static float yh() {
        return afU;
    }

    public static void s(float death_y) {
        afU = death_y;
    }

    public static float yi() {
        return afV;
    }

    public static void t(float death_z) {
        afV = death_z;
    }

    public static float yj() {
        return afN;
    }

    public static void u(float cur_x) {
        afN = cur_x;
    }

    public static float yk() {
        return afO;
    }

    public static void v(float cur_y) {
        afO = cur_y;
    }

    public static float yl() {
        return afP;
    }

    public static void w(float cur_z) {
        afP = cur_z;
    }

    public static float ym() {
        return afQ;
    }

    public static void A(float dst_x) {
        afQ = dst_x;
    }

    public static float yn() {
        return afR;
    }

    public static void B(float dst_y) {
        afR = dst_y;
    }

    public static float yo() {
        return afS;
    }

    public static void C(float dst_z) {
        afS = dst_z;
    }

    private static void yp() {
        u(DTSDKManagerEx.ik(0));
        v(DTSDKManagerEx.ik(1));
        w(DTSDKManagerEx.ik(2));
        if (v.xJ() <= 0) {
            r(yj());
            s(yk());
            t(yl());
        }
    }

    private static void yq() {
        float x = ym();
        float y = yn();
        float z = yo();
        if (x != 0.0f || y != 0.0f || z != 0.0f) {
            if (h.CW().CX() == 0) {
                DTSDKManagerEx.a(DTSDKManagerEx.Cp(), x, y, z);
            } else if (h.CW().CX() == 1) {
                DTSDKManagerEx.a(DTSDKManagerEx.Cq(), x, y, z);
            } else if (h.CW().CX() == 2) {
                DTSDKManagerEx.a(DTSDKManagerEx.Cq(), x, y, z);
            } else if (h.CW().CX() == 3) {
                DTSDKManagerEx.a(DTSDKManagerEx.Cq(), x, y, z);
            } else if (h.CW().CX() == 5 || h.CW().CX() == 7) {
                DTSDKManagerEx.a(DTSDKManagerEx.Cq(), x, y, z);
            }
        }
    }
}
