package com.huluxia.mcjavascript;

import com.huluxia.mcsdk.DTSDKManager;
import com.huluxia.mcsdk.DTSDKManager.b;
import com.huluxia.mcsdk.dtlib.h;
import com.mojang.minecraftpe.MainActivity;
import java.io.InputStream;
import net.zhuoweizhang.mcpelauncher.ScriptManager;

/* compiled from: DTNativeModPEApiEx */
public class m {
    public static void D(String paramString, int paramInt) {
        ScriptManager.nativeDumpVtable("_ZTV" + paramString.length() + paramString, paramInt);
    }

    public static byte[] df(String paramString) {
        if (MainActivity.currentMainActivity != null) {
            MainActivity localMainActivity = (MainActivity) MainActivity.currentMainActivity.get();
            if (localMainActivity != null) {
                return localMainActivity.getFileDataBytes(paramString);
            }
        }
        return null;
    }

    public static InputStream dg(String paramString) {
        if (MainActivity.currentMainActivity != null) {
            MainActivity localMainActivity = (MainActivity) MainActivity.currentMainActivity.get();
            if (localMainActivity != null) {
                return localMainActivity.getInputStreamForAsset(paramString);
            }
        }
        return null;
    }

    public static void At() {
        if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
            try {
                ScriptManager.clearTextureOverrides();
            } catch (Exception e) {
            }
        }
    }

    public static void a(String levelDir, String levelName, String levelSeed, int gamemode) {
        try {
            if (h.CW().CX() == 0) {
                if (levelDir.equals(DTSDKManager.worldDir)) {
                    System.err.println("Attempted to load level that is already loaded - ignore");
                    return;
                }
                DTSDKManager.requestLeaveGame = true;
                DTSDKManager.requestSelectLevel = new b();
                DTSDKManager.requestSelectLevel.dir = levelDir;
                if (DTSDKManager.isValidStringParameter(levelName)) {
                    DTSDKManager.requestSelectLevel.name = levelName;
                    DTSDKManager.requestSelectLevel.anK = levelSeed;
                    DTSDKManager.requestSelectLevel.anJ = gamemode;
                }
            } else if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                throw new RuntimeException("FIXME 0.11");
            }
        } catch (Exception e) {
        }
    }

    public static void a(int paramInt1, int paramInt2, String paramString1, String paramString2) {
        try {
            com.huluxia.mcjsmanager.b.a(paramInt1, paramInt2, paramString1, paramString2, 0);
        } catch (Exception e) {
        }
    }

    public static void b(int paramInt1, int paramInt2, String paramString1, String paramString2) {
        try {
            com.huluxia.mcjsmanager.b.a(paramInt1, paramInt2, paramString1, paramString2, 1);
        } catch (Exception e) {
        }
    }
}
