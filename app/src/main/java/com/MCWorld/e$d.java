package com.MCWorld;

import com.MCWorld.framework.base.utils.UtilsFunction;

/* compiled from: CrashCollector */
class e$d implements e$a {
    private e$d() {
    }

    public boolean t(String crash) {
        if (!UtilsFunction.empty(crash) && crash.indexOf("NullPointerException") >= 0) {
            return true;
        }
        return false;
    }

    public String getText() {
        return "NullPointerException";
    }
}
