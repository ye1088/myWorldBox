package com.MCWorld.mcgame;

import com.MCWorld.mcsdk.DTSDKManagerEx;
import com.MCWorld.mcsdk.dtlib.h;
import com.mojang.minecraftpe.MainActivity;
import net.zhuoweizhang.mcpelauncher.ScriptManager;

/* compiled from: DTChatMessage */
public class f {
    private static final boolean DEBUG = false;
    public static final String TAG = "DTChatMessage";

    public static void cx(String in_message) {
        DTSDKManagerEx.dz(in_message);
    }

    private static void cy(String str) {
        if (str != null && str.length() >= 1 && str.charAt(0) == '/') {
            DTSDKManagerEx.dC(str);
            DTSDKManagerEx.Cy();
            if (MainActivity.currentMainActivity != null) {
                MainActivity main = (MainActivity) MainActivity.currentMainActivity.get();
                if (main != null) {
                    main.updateTextboxText("");
                }
            }
        }
    }

    public static void cz(String str) {
        int v2 = 1;
        if (g.wk() == 1) {
            cy(str);
        }
        if (str != null && str.length() >= 1) {
            com.MCWorld.mcjavascript.f.callScriptMethod("chatHook", str);
            if (str.charAt(0) == '/') {
                com.MCWorld.mcjavascript.f.callScriptMethod("procCmd", str.substring(1));
                if (h.CW().CX() == 5 || h.CW().CX() == 7) {
                    String[] v1 = str.substring(1).split(" ");
                    if (v1.length <= 0 || !ScriptManager.nativeIsValidCommand(v1[0])) {
                        v2 = 0;
                    }
                    DTSDKManagerEx.Cy();
                    if (ScriptManager.isRemote || v2 != 0) {
                        return;
                    }
                }
                DTSDKManagerEx.Cy();
                if (MainActivity.currentMainActivity != null) {
                    MainActivity main = (MainActivity) MainActivity.currentMainActivity.get();
                    if (main != null) {
                        main.updateTextboxText("");
                    }
                }
            }
        }
    }
}
