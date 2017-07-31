package com.tencent.mm.sdk.platformtools;

import android.content.Context;

public final class MMApplicationContext {
    private static String am = "com.tencent.mm";
    private static Context q = null;

    private MMApplicationContext() {
    }

    public static Context getContext() {
        return q;
    }

    public static String getDefaultPreferencePath() {
        return am + "_preferences";
    }

    public static String getPackageName() {
        return am;
    }

    public static void setContext(Context context) {
        q = context;
        am = context.getPackageName();
        Log.d("MicroMsg.MMApplicationContext", "setup application context for package: " + am);
    }
}
