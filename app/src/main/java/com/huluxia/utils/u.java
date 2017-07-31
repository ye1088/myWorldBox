package com.huluxia.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Debug;
import java.util.List;

/* compiled from: UtilsContext */
public class u {
    protected static Context mAppContext = null;

    public static void aN(Context c) {
        mAppContext = c;
    }

    public static PackageManager getPackageManager() {
        return mAppContext.getPackageManager();
    }

    public static long qt() {
        ActivityManager am = (ActivityManager) mAppContext.getSystemService("activity");
        MemoryInfo mi = new MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem;
    }

    public static float qu() {
        return mAppContext.getResources().getDisplayMetrics().density;
    }

    public static int cY(int pid) {
        ActivityManager manager = (ActivityManager) mAppContext.getSystemService("activity");
        for (RunningAppProcessInfo info : manager.getRunningAppProcesses()) {
            if (info.pid == pid) {
                Debug.MemoryInfo[] memoryInfo = manager.getProcessMemoryInfo(new int[]{pid});
                return memoryInfo[0].getTotalSharedDirty() + memoryInfo[0].getTotalPrivateDirty();
            }
        }
        return 0;
    }

    public static int bW(String packName) {
        for (RunningAppProcessInfo info : ((ActivityManager) mAppContext.getSystemService("activity")).getRunningAppProcesses()) {
            if (info.processName.equals(packName)) {
                return info.pid;
            }
        }
        return 0;
    }

    public static String qw() {
        List<RunningAppProcessInfo> run = ((ActivityManager) mAppContext.getSystemService("activity")).getRunningAppProcesses();
        if (run.size() == 0) {
            return "";
        }
        return ((RunningAppProcessInfo) run.get(0)).processName;
    }

    public static String qx() {
        if (VERSION.SDK_INT > 19) {
            return qw();
        }
        String topPack = "";
        for (RunningTaskInfo info : ((ActivityManager) mAppContext.getSystemService("activity")).getRunningTasks(2)) {
            topPack = info.topActivity.getPackageName();
            if (!topPack.equals("com.xiaomi.gamecenter.sdk.service")) {
                return topPack;
            }
        }
        return topPack;
    }

    public static Drawable bX(String packName) {
        Drawable drawable = null;
        if (packName != null) {
            try {
                PackageManager pm = mAppContext.getPackageManager();
                drawable = pm.getApplicationInfo(packName, 8192).loadIcon(pm);
            } catch (NameNotFoundException e) {
            }
        }
        return drawable;
    }

    public static String bY(String packName) {
        if (packName == null) {
            packName = mAppContext.getPackageName();
        }
        try {
            PackageManager pm = mAppContext.getPackageManager();
            ApplicationInfo info = pm.getApplicationInfo(packName, 8192);
            if ((info.flags & 1) > 0) {
                return "";
            }
            return info.loadLabel(pm).toString();
        } catch (NameNotFoundException e) {
            return "";
        }
    }

    public static String bZ(String packName) {
        if (packName == null) {
            packName = mAppContext.getPackageName();
        }
        try {
            return mAppContext.getPackageManager().getPackageInfo(packName, 0).versionName;
        } catch (NameNotFoundException e) {
            return "";
        }
    }

    public static void C(String strName) {
        if (strName != null && strName.length() != 0) {
            try {
                Intent intent = mAppContext.getPackageManager().getLaunchIntentForPackage(strName);
                intent.setFlags(268435456);
                mAppContext.startActivity(intent);
            } catch (Exception e) {
            }
        }
    }

    public static String ca(String key) {
        try {
            return mAppContext.getPackageManager().getApplicationInfo(mAppContext.getPackageName(), 128).metaData.getString(key);
        } catch (NameNotFoundException e) {
            return "";
        }
    }

    public static void dq(String packName) {
        ((ActivityManager) mAppContext.getSystemService("activity")).killBackgroundProcesses(packName);
    }
}
