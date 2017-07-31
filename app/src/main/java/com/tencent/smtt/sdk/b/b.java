package com.tencent.smtt.sdk.b;

import android.content.Context;
import android.net.ConnectivityManager;

public class b {
    private static Context a;
    private static ConnectivityManager b;

    public static ConnectivityManager a() {
        if (b == null && a != null) {
            b = (ConnectivityManager) a.getSystemService("connectivity");
        }
        return b;
    }

    public static void a(Context context) {
        a = context;
    }
}
