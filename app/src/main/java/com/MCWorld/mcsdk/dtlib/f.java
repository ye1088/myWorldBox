package com.MCWorld.mcsdk.dtlib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

/* compiled from: DTInstallInfo */
public class f {
    private static f aoF;

    public static synchronized f CV() {
        f fVar;
        synchronized (f.class) {
            if (aoF == null) {
                aoF = new f();
            }
            fVar = aoF;
        }
        return fVar;
    }

    public boolean x(Context context, String packagename) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        }
        return true;
    }

    public String getVersionName(Context paramContext, String packagename) {
        try {
            return paramContext.getPackageManager().getPackageInfo(packagename, 0).versionName;
        } catch (NameNotFoundException paramContextex) {
            paramContextex.printStackTrace();
            return null;
        }
    }

    public int y(Context paramContext, String packagename) {
        int i = 0;
        try {
            return paramContext.getPackageManager().getPackageInfo(packagename, 0).versionCode;
        } catch (NameNotFoundException paramContextex) {
            paramContextex.printStackTrace();
            return i;
        }
    }
}
