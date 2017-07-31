package com.huluxia.mcjavascript;

import com.huluxia.mcjsmanager.b;
import com.huluxia.mcsdk.DTSDKManager.c;
import com.huluxia.mcsdk.DTSDKManagerEx;
import com.huluxia.mcsdk.dtlib.h;

/* compiled from: DTNativeBlockApiEx */
public class i {
    public static Object Ar() {
        if (h.CW().CX() == 0) {
            return Integer.valueOf(DTSDKManagerEx.Cp());
        }
        if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
            return Long.valueOf(DTSDKManagerEx.Cq());
        }
        return Integer.valueOf(0);
    }

    public static void a(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, int paramInt2) {
        if (h.CW().CX() == 0) {
            DTSDKManagerEx.a(paramInt, (float) paramDouble1, (float) paramDouble2, (float) paramDouble3, (float) paramDouble4, (float) paramDouble5, (float) paramDouble6);
        } else if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
            DTSDKManagerEx.a(paramInt, (float) paramDouble1, (float) paramDouble2, (float) paramDouble3, (float) paramDouble4, (float) paramDouble5, (float) paramDouble6, paramInt2);
        }
    }

    public static void a(int paramInt, c localTextureRequests, String paramString) {
        try {
            b.a(paramInt, 0, localTextureRequests.anM[0], paramString, 2);
        } catch (Exception e) {
        }
    }
}
