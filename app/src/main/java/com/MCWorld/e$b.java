package com.MCWorld;

import com.MCWorld.framework.base.utils.UtilsFunction;

/* compiled from: CrashCollector */
class e$b implements e$a {
    private e$b() {
    }

    public boolean t(String crash) {
        if (!UtilsFunction.empty(crash) && crash.indexOf("IllegalFormatException") >= 0) {
            return true;
        }
        return false;
    }

    public String getText() {
        return "IllegalFormatException";
    }
}
