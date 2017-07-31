package com.huluxia.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import com.huluxia.HTApplication;
import com.huluxia.data.profile.a;
import com.huluxia.framework.base.widget.UtilsAndroid;

/* compiled from: UtilsAndroid */
public class o {
    private static String versionName;

    public static String getDeviceId() {
        return UtilsAndroid.fetchDeviceId();
    }

    public static String getVersionName() {
        if (versionName == null) {
            versionName = fetchVersionName();
        }
        return versionName;
    }

    @SuppressLint({"NewApi"})
    public static void bV(String szContent) {
        Context context = HTApplication.getAppContext();
        if (VERSION.SDK_INT >= 11) {
            ((ClipboardManager) context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", szContent));
        } else {
            ((android.text.ClipboardManager) context.getSystemService("clipboard")).setText(szContent);
        }
    }

    public static String getVersion() {
        return VERSION.RELEASE;
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String fetchVersionName() {
        String appVersion = "0.0.0";
        try {
            return HTApplication.getAppContext().getPackageManager().getPackageInfo(HTApplication.getAppContext().getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return appVersion;
        }
    }

    public static String qe() {
        return ((TelephonyManager) HTApplication.getAppContext().getSystemService(a.qe)).getDeviceId();
    }

    public static String ff() {
        String version = null;
        try {
            version = HTApplication.getAppContext().getPackageManager().getPackageInfo(HTApplication.getAppContext().getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return version == null ? "0.0" : version;
    }

    @TargetApi(16)
    public static String u(Activity actvity) {
        if (VERSION.SDK_INT < 16) {
            return "0";
        }
        ActivityManager am = (ActivityManager) actvity.getSystemService("activity");
        MemoryInfo mi = new MemoryInfo();
        am.getMemoryInfo(mi);
        return aw.bA(mi.totalMem);
    }

    @TargetApi(16)
    public static long v(Activity actvity) {
        if (VERSION.SDK_INT < 16) {
            return 0;
        }
        ActivityManager am = (ActivityManager) actvity.getSystemService("activity");
        MemoryInfo mi = new MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.totalMem;
    }
}
