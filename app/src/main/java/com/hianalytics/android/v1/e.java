package com.hianalytics.android.v1;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.TelephonyManager;
import com.hianalytics.android.a.a.a;
import com.hianalytics.android.a.a.c;

public class e {
    public static String H(Context context) {
        return c.a(context, "sessioncontext").getString("session_id", "");
    }

    public static String I(Context context) {
        return c.a(context, "sessioncontext").getString("refer_id", "");
    }

    public static void J(Context context) {
        Editor edit = c.a(context, "sessioncontext").edit();
        edit.remove("session_id");
        edit.remove("refer_id");
        edit.commit();
    }

    public static void K(Context context) {
        Editor edit = c.a(context, "sessioncontext").edit();
        edit.remove("refer_id");
        edit.commit();
    }

    private static String L(Context context) {
        return String.valueOf(System.currentTimeMillis() + a.b(((TelephonyManager) context.getSystemService(com.MCWorld.data.profile.a.qe)).getDeviceId()));
    }

    public static void i(Context context, String str) {
        Editor edit = c.a(context, "sessioncontext").edit();
        edit.putString("session_id", str);
        edit.commit();
    }

    public static void init(Context context, String str) {
        SharedPreferences a = c.a(context, "sessioncontext");
        String L = L(context);
        Editor edit = a.edit();
        edit.remove("session_id");
        edit.remove("refer_id");
        edit.putString("session_id", L);
        edit.putString("refer_id", str);
        edit.commit();
    }

    public static void j(Context context, String str) {
        Editor edit = c.a(context, "sessioncontext").edit();
        edit.putString("refer_id", str);
        edit.commit();
    }
}
