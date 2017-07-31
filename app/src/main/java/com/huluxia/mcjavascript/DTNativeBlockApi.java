package com.huluxia.mcjavascript;

import com.huluxia.mcsdk.DTSDKManager;
import com.huluxia.mcsdk.DTSDKManager.c;
import com.huluxia.mcsdk.DTSDKManagerEx;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSStaticFunction;

public class DTNativeBlockApi extends ScriptableObject {
    private static final boolean DEBUG = false;

    @JSStaticFunction
    public static void defineBlock(int paramInt, String paramString, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4) {
        if (paramInt < 0 || paramInt >= 256) {
            throw new IllegalArgumentException("Block IDs must be >= 0 and < 256");
        }
        int i = 17;
        boolean bool1 = true;
        if (paramObject2 != null && (paramObject2 instanceof Number)) {
            i = ((Number) paramObject2).intValue();
        }
        if (paramObject3 != null && (paramObject3 instanceof Boolean)) {
            bool1 = ((Boolean) paramObject3).booleanValue();
        }
        int j = 0;
        if (paramObject4 != null) {
            j = 0;
            if (paramObject4 instanceof Number) {
                j = ((Number) paramObject4).intValue();
            }
        }
        c localTextureRequests = DTSDKManager.expandTexturesArray(paramObject1);
        DTSDKManagerEx.a(localTextureRequests);
        DTSDKManagerEx.a(paramInt, paramString, localTextureRequests.anM, localTextureRequests.anL, i, bool1, j);
        i.a(paramInt, localTextureRequests, paramString);
    }

    @JSStaticFunction
    public static int getRenderType(int paramInt) {
        return DTSDKManagerEx.iD(paramInt);
    }

    @JSStaticFunction
    public static void setColor(int paramInt, Scriptable paramScriptable) {
        DTSDKManagerEx.a(paramInt, DTSDKManager.expandColorsArray(paramScriptable));
    }

    @JSStaticFunction
    public static void setDestroyTime(int paramInt, double paramDouble) {
        DTSDKManagerEx.g(paramInt, (float) paramDouble);
    }

    @JSStaticFunction
    public static void setExplosionResistance(int paramInt, double paramDouble) {
        DTSDKManagerEx.h(paramInt, (float) paramDouble);
    }

    @JSStaticFunction
    public static void setLightLevel(int paramInt1, int paramInt2) {
        DTSDKManagerEx.af(paramInt1, paramInt2);
    }

    @JSStaticFunction
    public static void setLightOpacity(int paramInt1, int paramInt2) {
        DTSDKManagerEx.ag(paramInt1, paramInt2);
    }

    @JSStaticFunction
    public static void setRenderLayer(int paramInt1, int paramInt2) {
        DTSDKManagerEx.ah(paramInt1, paramInt2);
    }

    @JSStaticFunction
    public static void setRenderType(int paramInt1, int paramInt2) {
        DTSDKManagerEx.ai(paramInt1, paramInt2);
    }

    @JSStaticFunction
    public static void setShape(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, int paramInt2) {
        i.a(paramInt, (double) ((float) paramDouble1), (double) ((float) paramDouble2), (double) ((float) paramDouble3), (double) ((float) paramDouble4), (double) ((float) paramDouble5), (double) ((float) paramDouble6), paramInt2);
    }

    public String getClassName() {
        return "Block";
    }
}
