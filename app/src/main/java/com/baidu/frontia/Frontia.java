package com.baidu.frontia;

import android.content.Context;
import android.util.Log;
import com.baidu.frontia.api.FrontiaPush;
import com.baidu.frontia.base.impl.FrontiaImpl;

public class Frontia {
    private static final String a = "Frontia";
    private static FrontiaImpl b = null;
    private static final String c = "1";

    public static String getApiKey() {
        return b.getAppKey();
    }

    public static String getFrontiaVersion() {
        return "1";
    }

    public static FrontiaPush getPush() {
        FrontiaPush newInstance = FrontiaPush.newInstance(b.getAppContext());
        newInstance.init(b.getAppKey());
        return newInstance;
    }

    public static boolean init(Context context, String str) {
        if (context == null || str == null) {
            return false;
        }
        b = FrontiaImpl.get();
        if (b == null) {
            return false;
        }
        b.setAppContext(context.getApplicationContext());
        b.setAppKey(str);
        b.start();
        Log.d("frontia", "frontia init");
        a.a(context, str);
        return true;
    }

    public static void setSlientUpdateEnabled(boolean z) {
        b.setCheckForUpdatesEnabled(z);
    }
}
