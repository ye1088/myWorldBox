package com.tencent.mm.sdk.platformtools;

import java.util.HashMap;

public final class SystemProperty {
    private static final HashMap<String, String> bl = new HashMap();

    private SystemProperty() {
    }

    public static String getProperty(String str) {
        return (String) bl.get(str);
    }

    public static void setProperty(String str, String str2) {
        bl.put(str, str2);
    }
}
