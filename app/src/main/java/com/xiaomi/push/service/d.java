package com.xiaomi.push.service;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

public class d {
    private static String a = null;
    private static String b = null;
    private static String c = null;

    public static String a() {
        return VERSION.SDK_INT > 8 ? Build.SERIAL : null;
    }

    public static String a(Context context) {
        if (b == null) {
            String c = c(context);
            String b = b(context);
            b = "a_isRightVersion-" + com.xiaomi.channel.commonutils.string.d.b(c + b + a());
        }
        return b;
    }

    @TargetApi(17)
    public static int b() {
        if (VERSION.SDK_INT < 17) {
            return -1;
        }
        Object a = a.a("android.os.UserHandle", "myUserId", new Object[0]);
        return a != null ? ((Integer) Integer.class.cast(a)).intValue() : -1;
    }

    public static String b(Context context) {
        String str = null;
        try {
            str = Secure.getString(context.getContentResolver(), "android_id");
        } catch (Throwable th) {
            b.a(th);
        }
        return str;
    }

    public static String c(Context context) {
        if (a != null) {
            return a;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(com.MCWorld.data.profile.a.qe);
            String deviceId = telephonyManager.getDeviceId();
            int i = 10;
            while (deviceId == null) {
                int i2 = i - 1;
                if (i <= 0) {
                    break;
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                deviceId = telephonyManager.getDeviceId();
                i = i2;
            }
            if (deviceId != null) {
                a = deviceId;
            }
            return deviceId;
        } catch (Throwable th) {
            b.a(th);
            return null;
        }
    }

    public static String d(Context context) {
        if (a != null) {
            return a;
        }
        try {
            String deviceId = ((TelephonyManager) context.getSystemService(com.MCWorld.data.profile.a.qe)).getDeviceId();
            if (deviceId == null) {
                return deviceId;
            }
            a = deviceId;
            return deviceId;
        } catch (Throwable th) {
            b.a(th);
            return null;
        }
    }

    public static synchronized String e(Context context) {
        String str;
        synchronized (d.class) {
            if (c != null) {
                str = c;
            } else {
                str = b(context);
                c = com.xiaomi.channel.commonutils.string.d.b(str + a());
                str = c;
            }
        }
        return str;
    }

    public static String f(Context context) {
        return ((TelephonyManager) context.getSystemService(com.MCWorld.data.profile.a.qe)).getSimOperatorName();
    }

    public static String g(Context context) {
        try {
            return ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
        } catch (Throwable e) {
            b.a(e);
            return null;
        }
    }
}
