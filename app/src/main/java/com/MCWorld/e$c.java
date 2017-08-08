package com.MCWorld;

import com.MCWorld.framework.base.utils.UtilsFunction;

/* compiled from: CrashCollector */
class e$c implements e$a {
    private e$c() {
    }

    public boolean t(String crash) {
        if (!UtilsFunction.empty(crash) && crash.indexOf("MinecraftClient10getGuiData") >= 0) {
            return true;
        }
        return false;
    }

    public String getText() {
        return "_ZN15MinecraftClient10getGuiDataEv";
    }
}
