package com.huluxia.mcgame;

import com.huluxia.mcsdk.DTSDKManagerEx;

/* compiled from: DTPlayerFov */
public class u {
    private static volatile int afu = 0;
    private static boolean afv = false;

    public static void gK(int playerFovScale) {
        afu = playerFovScale;
        bA(true);
    }

    public static void xF() {
        xG();
    }

    private static void xG() {
        if (xI()) {
            bA(false);
            if (xH() == 0) {
                DTSDKManagerEx.dtNativeSetFov(30.0f, true);
            } else if (xH() == 1) {
                DTSDKManagerEx.dtNativeSetFov(50.0f, true);
            } else if (xH() == 2) {
                DTSDKManagerEx.dtNativeSetFov(0.0f, false);
            } else if (xH() == 3) {
                DTSDKManagerEx.dtNativeSetFov(90.0f, true);
            } else if (xH() == 4) {
                DTSDKManagerEx.dtNativeSetFov(105.0f, true);
            }
        }
    }

    public static int xH() {
        return afu;
    }

    public static boolean xI() {
        return afv;
    }

    public static void bA(boolean refreshPlayerFov) {
        afv = refreshPlayerFov;
    }
}
