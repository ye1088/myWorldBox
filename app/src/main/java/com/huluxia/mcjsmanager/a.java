package com.huluxia.mcjsmanager;

import com.huluxia.mcjavascript.d;
import com.huluxia.mcjavascript.e;
import com.huluxia.mcjavascript.f;

/* compiled from: DTExAPI */
public class a {
    public static void Ay() {
        Az();
    }

    private static void Az() {
        for (int i = 0; i < d.ajz.size(); i++) {
            e pTmpDTJSItem = (e) d.ajz.get(i);
            if (true == pTmpDTJSItem.isValid() && 2 == pTmpDTJSItem.Ai()) {
                int nEncodeMode = pTmpDTJSItem.Ah();
                boolean bEnabled;
                String scriptName;
                if (1 == nEncodeMode) {
                    bEnabled = pTmpDTJSItem.Ak();
                    scriptName = pTmpDTJSItem.getName();
                    if (bEnabled) {
                        f.c(scriptName, "enable_script_func", new Object[0]);
                    } else {
                        f.c(scriptName, "disable_script_func", new Object[0]);
                        f.c(scriptName, "leaveGame", new Object[0]);
                    }
                } else if (nEncodeMode == 0) {
                    bEnabled = pTmpDTJSItem.Ak();
                    scriptName = pTmpDTJSItem.getName();
                    if (!bEnabled) {
                        f.c(scriptName, "leaveGame", new Object[0]);
                    }
                }
            }
        }
    }
}
