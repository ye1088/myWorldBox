package com.huluxia.mcjavascript;

import com.huluxia.mcsdk.DTSDKManagerEx;
import com.huluxia.mcsdk.dtlib.h;
import net.zhuoweizhang.mcpelauncher.ScriptManager;

/* compiled from: DTNativeLevelApiEx */
public class l {
    public static void b(int paramInt1, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, int paramInt2) {
        if (h.CW().CX() == 0) {
            if (paramInt1 < 0 || paramInt1 > 15) {
                throw new RuntimeException("Invalid particle type " + paramInt1 + ": should be between 0 and 15");
            }
            DTSDKManagerEx.dtNativeLevelAddParticle(paramInt1, (float) paramDouble1, (float) paramDouble2, (float) paramDouble3, (float) paramDouble4, (float) paramDouble5, (float) paramDouble6, paramInt2);
        } else if (h.CW().CX() != 1 && h.CW().CX() != 2 && h.CW().CX() != 3 && h.CW().CX() != 5 && h.CW().CZ() != 1) {
        } else {
            if (paramInt1 < 0 || paramInt1 > 25) {
                throw new RuntimeException("Invalid particle type " + paramInt1 + ": should be between 0 and 25");
            }
            DTSDKManagerEx.dtNativeLevelAddParticle(paramInt1, (float) paramDouble1, (float) paramDouble2, (float) paramDouble3, (float) paramDouble4, (float) paramDouble5, (float) paramDouble6, paramInt2);
        }
    }

    public static Object b(double paramDouble1, double paramDouble2, double paramDouble3, int paramInt, String paramString) {
        if (DTSDKManagerEx.dy(paramString)) {
            paramString = null;
        }
        if (h.CW().CX() == 0) {
            return Integer.valueOf(DTSDKManagerEx.b((float) paramDouble1, (float) paramDouble2, (float) paramDouble3, paramInt, paramString));
        }
        if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
            return Long.valueOf(DTSDKManagerEx.c((float) paramDouble1, (float) paramDouble2, (float) paramDouble3, paramInt, paramString));
        }
        return Integer.valueOf(0);
    }

    public static void g(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        if (DTSDKManagerEx.C(paramInt1, paramInt2, paramInt3) != 52) {
            throw new RuntimeException("Block at " + paramInt1 + ":" + paramInt2 + ":" + paramInt3 + " is not a_isRightVersion mob spawner!");
        }
        ScriptManager.nativeSpawnerSetEntityType(paramInt1, paramInt2, paramInt3, paramInt4);
    }
}
