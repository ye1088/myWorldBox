package com.MCWorld.framework.base.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;

public class UtilsVersion {
    public static final String BBS_VERSION = "1.8";
    public static final int SHOW_IN_HOME = 1;
    public static final int SHOW_IN_SETTING = 2;

    public static String getVersionString(Context context) {
        PackageInfo packInfo = null;
        try {
            packInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo == null ? "unknow" : packInfo.versionName;
    }

    public static int getVersionCode(Context context) {
        PackageInfo packInfo = null;
        try {
            packInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo == null ? -1 : packInfo.versionCode;
    }

    public static boolean hasFroyo() {
        return VERSION.SDK_INT >= 8;
    }

    public static boolean hasGingerbread() {
        return VERSION.SDK_INT >= 9;
    }

    public static boolean hasHoneycomb() {
        return VERSION.SDK_INT >= 11;
    }

    public static boolean hasHoneycombMR1() {
        return VERSION.SDK_INT >= 12;
    }

    public static boolean hasICS() {
        return VERSION.SDK_INT >= 14;
    }

    public static boolean hasJellyBean() {
        return VERSION.SDK_INT >= 16;
    }

    public static boolean hasJellyBeanMr1() {
        return VERSION.SDK_INT >= 17;
    }

    public static boolean hasJellyBeanMr2() {
        return VERSION.SDK_INT >= 18;
    }

    public static boolean hasKitKat() {
        return VERSION.SDK_INT >= 19;
    }

    public static boolean hasLolliPop() {
        return VERSION.SDK_INT >= 21;
    }

    public static boolean hasMarshmallow() {
        return VERSION.SDK_INT >= 23;
    }

    public static boolean hasNouga() {
        return VERSION.SDK_INT >= 24;
    }
}
