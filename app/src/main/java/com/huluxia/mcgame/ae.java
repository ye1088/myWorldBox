package com.huluxia.mcgame;

import com.huluxia.mcsdk.DTSDKManagerEx;
import com.huluxia.mcsdk.dtlib.h;
import com.huluxia.mcsdk.log.a;

/* compiled from: DTSprintRun */
public class ae {
    private static volatile int ags = 0;
    private static float agt = 0.0f;
    private static float agu = 0.0f;
    private static float agv = 0.0f;
    private static float agw = 0.0f;
    private static int agx = 0;
    private static volatile float agy = 0.0f;

    public static void gX(int sprintRunSpeed) {
        agx = sprintRunSpeed;
        if (agx == 1) {
            agy = 1.0f;
        } else if (agx == 2) {
            agy = 1.2f;
        } else {
            agy = 1.0f;
        }
    }

    public static void yH() {
        yK();
    }

    public static int yI() {
        return ags;
    }

    public static void gY(int sprintRun_Pyramid) {
        ags = sprintRun_Pyramid;
    }

    public static int yJ() {
        return agx;
    }

    private static void yK() {
        try {
            if (h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5) {
                if (yJ() == 1) {
                    if (ags == 1) {
                        agt = DTSDKManagerEx.ik(0);
                        agu = DTSDKManagerEx.ik(2);
                        ags++;
                    } else if (ags == 3) {
                        ags = 1;
                        agv = DTSDKManagerEx.ik(0) - agt;
                        agw = DTSDKManagerEx.ik(2) - agu;
                        DTSDKManagerEx.a(DTSDKManagerEx.Cq(), agv * agy, 0);
                        DTSDKManagerEx.a(DTSDKManagerEx.Cq(), agw * agy, 2);
                        agv = 0.0f;
                        agw = 0.0f;
                    }
                    if (ags != 1) {
                        ags++;
                    }
                }
            } else if (agx == 1 || agx == 2) {
                if (agx == 1) {
                    if (DTSDKManagerEx.dtNativePlayerIsFlying()) {
                        agy = 1.0f;
                    } else {
                        agy = 1.0f;
                    }
                } else if (agx == 2) {
                    if (DTSDKManagerEx.dtNativePlayerIsFlying()) {
                        agy = 1.1f;
                    } else {
                        agy = 1.4f;
                    }
                }
                if (h.CW().CX() == 0) {
                    if (ags == 1) {
                        agt = DTSDKManagerEx.ik(0);
                        agu = DTSDKManagerEx.ik(2);
                        ags++;
                    } else if (ags == 3) {
                        ags = 1;
                        agv = DTSDKManagerEx.ik(0) - agt;
                        agw = DTSDKManagerEx.ik(2) - agu;
                        DTSDKManagerEx.a(DTSDKManagerEx.Cp(), agv * agy, 0);
                        DTSDKManagerEx.a(DTSDKManagerEx.Cp(), agw * agy, 2);
                        agv = 0.0f;
                        agw = 0.0f;
                    }
                    if (ags != 1) {
                        ags++;
                    }
                } else if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CX() == 7) {
                    if (ags == 1) {
                        agt = DTSDKManagerEx.ik(0);
                        agu = DTSDKManagerEx.ik(2);
                        ags++;
                    } else if (ags == 3) {
                        ags = 1;
                        agv = DTSDKManagerEx.ik(0) - agt;
                        agw = DTSDKManagerEx.ik(2) - agu;
                        DTSDKManagerEx.a(DTSDKManagerEx.Cq(), agv * agy, 0);
                        DTSDKManagerEx.a(DTSDKManagerEx.Cq(), agw * agy, 2);
                        agv = 0.0f;
                        agw = 0.0f;
                    }
                    if (ags != 1) {
                        ags++;
                    }
                }
            } else {
                ags = 0;
            }
        } catch (Exception e) {
            a.verbose("TAG", "DTPrint Error ... \n", new Object[0]);
        }
    }
}
