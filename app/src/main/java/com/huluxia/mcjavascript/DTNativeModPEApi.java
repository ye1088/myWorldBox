package com.huluxia.mcjavascript;

import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.util.Log;
import com.huluxia.mcgame.g;
import com.huluxia.mclauncher010.a;
import com.huluxia.mcsdk.DTSDKManager;
import com.huluxia.mcsdk.DTSDKManagerEx;
import com.huluxia.mcsdk.dtlib.h;
import java.io.InputStream;
import net.zhuoweizhang.mcpelauncher.ScriptManager;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSStaticFunction;

public class DTNativeModPEApi extends ScriptableObject {
    private static final boolean DEBUG = false;
    private static String currentScript = null;
    private static boolean requestLeaveGame = false;
    private static final long serialVersionUID = -1420996403572729843L;

    @JSStaticFunction
    public static String getMinecraftVersion() {
        String strVersionName = "Unknown";
        if (h.CW().CX() == 0) {
            return "0.10.5";
        }
        if (h.CW().CX() == 1) {
            return a.VERSION_NAME;
        }
        if (h.CW().CX() == 2) {
            return VERSION.SDK_INT < 11 ? "0.12.1" : "0.12.3";
        } else if (h.CW().CX() == 3) {
            return h.CW().CY() == 0 ? "0.13.0" : "0.13.1";
        } else if (h.CW().CX() == 5) {
            return "0.15.1";
        } else {
            try {
                return DTSDKManager.androidContext.getPackageManager().getPackageInfo("com.mojang.minecraftpe", 0).versionName;
            } catch (Exception localException) {
                localException.printStackTrace();
                return strVersionName;
            }
        }
    }

    @JSStaticFunction
    public static void langEdit(String paramString1, String paramString2) {
        DTSDKManagerEx.E(paramString1, paramString2);
    }

    @JSStaticFunction
    public static void leaveGame() {
        DTSDKManager.requestLeaveGame = true;
    }

    @JSStaticFunction
    public static void log(String paramString) {
        Log.i("HuLuXiaLog", paramString);
    }

    @JSStaticFunction
    public static void overrideTexture(String paramString1, String paramString2) {
        DTSDKManager.overrideTexture(paramString2, paramString1);
    }

    @JSStaticFunction
    public static String readData(String paramString) {
        return DTSDKManager.androidContext.getSharedPreferences("BlockLauncherModPEScript" + DTSDKManager.currentScript, 0).getString(paramString, "");
    }

    @JSStaticFunction
    public static void removeData(String paramString) {
        Editor localEditor = DTSDKManager.androidContext.getSharedPreferences("BlockLauncherModPEScript" + DTSDKManager.currentScript, 0).edit();
        localEditor.remove(paramString);
        localEditor.commit();
    }

    @JSStaticFunction
    public static void resetFov() {
        DTSDKManagerEx.dtNativeSetFov(0.0f, false);
    }

    @JSStaticFunction
    public static void resetImages() {
        m.At();
    }

    @JSStaticFunction
    public static void saveData(String paramString1, String paramString2) {
        Editor localEditor = DTSDKManager.androidContext.getSharedPreferences("BlockLauncherModPEScript" + DTSDKManager.currentScript, 0).edit();
        localEditor.putString(paramString1, paramString2);
        localEditor.commit();
    }

    @JSStaticFunction
    public static void selectLevel(String levelDir, String levelName, String levelSeed, int gamemode) {
        m.a(levelDir, levelName, levelSeed, gamemode);
    }

    @JSStaticFunction
    public static void setCamera(int paramInt) {
        DTSDKManagerEx.iC(paramInt);
    }

    @JSStaticFunction
    public static void setFoodItem(int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2, int paramInt4) {
        try {
            Integer.parseInt(paramString1);
            throw new IllegalArgumentException("The item icon for " + paramString2.trim() + " is not updated for 0.8.0. Please ask the script author to update");
        } catch (NumberFormatException e) {
            if (paramInt1 < 0 || paramInt1 >= ScriptManager.ITEM_ID_COUNT) {
                throw new IllegalArgumentException("Item IDs must be >= 0 and < 512");
            } else if (DTSDKManagerEx.anO == null || DTSDKManagerEx.anO.A(paramString1, paramInt2)) {
                DTSDKManagerEx.a(paramInt1, paramString1, paramInt2, paramInt3, paramString2, paramInt4);
                m.b(paramInt1, paramInt2, paramString1, paramString2);
            } else {
                throw new IllegalArgumentException("The item icon " + paramString1 + ":" + paramInt2 + " does not exist");
            }
        }
    }

    @JSStaticFunction
    public static void setFov(double paramDouble) {
        DTSDKManagerEx.dtNativeSetFov((float) paramDouble, true);
    }

    @JSStaticFunction
    public static void setGameSpeed(double paramDouble) {
        DTSDKManagerEx.G((float) paramDouble);
    }

    @JSStaticFunction
    public static void setGuiBlocks(String paramString) {
        overrideTexture("gui/gui_blocks.png", paramString);
    }

    @JSStaticFunction
    public static void setItem(int paramInt1, String paramString1, int paramInt2, String paramString2, int paramInt3) {
        try {
            Integer.parseInt(paramString1);
            throw new IllegalArgumentException("The item icon for " + paramString2.trim() + " is not updated for 0.8.0. Please ask the script author to update");
        } catch (NumberFormatException e) {
            if (h.CW().CX() == 0) {
                if (paramInt1 < 0 || paramInt1 >= 512) {
                    throw new IllegalArgumentException("Item IDs must be >= 0 and < 512");
                }
            } else if (h.CW().CX() == 1 || h.CW().CX() == 2) {
                int itemIdMaxCnt = g.vU();
                if (paramInt1 < 0 || paramInt1 >= itemIdMaxCnt) {
                    throw new IllegalArgumentException("Item IDs must be >= 0 and < xxx");
                }
            }
            if (DTSDKManagerEx.anO == null || DTSDKManagerEx.anO.A(paramString1, paramInt2)) {
                DTSDKManagerEx.a(paramInt1, paramString1, paramInt2, paramString2, paramInt3);
                m.a(paramInt1, paramInt2, paramString1, paramString2);
                return;
            }
            throw new IllegalArgumentException("The item icon " + paramString1 + ":" + paramInt2 + " does not exist");
        }
    }

    @JSStaticFunction
    public static void setItems(String paramString) {
        overrideTexture("images/items-opaque.png", paramString);
    }

    @JSStaticFunction
    public static void setTerrain(String paramString) {
        overrideTexture("images/terrain-atlas.tga", paramString);
    }

    @JSStaticFunction
    public static void showTipMessage(String paramString) {
        DTSDKManagerEx.dz(paramString);
    }

    @JSStaticFunction
    public static void takeScreenshot(String paramString) {
        com.huluxia.mcinterface.h.zC();
    }

    @JSStaticFunction
    public static void dumpVtable(String paramString, int paramInt) {
        m.D(paramString, paramInt);
    }

    @JSStaticFunction
    public static byte[] getBytesFromTexturePack(String paramString) {
        return m.df(paramString);
    }

    @JSStaticFunction
    public static InputStream openInputStreamFromTexturePack(String paramString) {
        return m.dg(paramString);
    }

    public String getClassName() {
        return "ModPE";
    }
}
