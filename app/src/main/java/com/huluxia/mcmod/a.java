package com.huluxia.mcmod;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build.VERSION;
import com.huluxia.mcgame.g;
import java.util.List;

/* compiled from: DTModManager */
public class a {
    private static Context mAppContext = null;

    public static void init(Context ctx) {
        mAppContext = ctx;
        aU(mAppContext);
    }

    public static void aT(Context paramContext) {
        if (g.wl()) {
            ActivityManager localActivityManager = (ActivityManager) paramContext.getSystemService("activity");
            List localList = localActivityManager.getRunningAppProcesses();
            if (localList != null) {
                for (int i = 0; i < localList.size(); i++) {
                    RunningAppProcessInfo localRunningAppProcessInfo = (RunningAppProcessInfo) localList.get(i);
                    String[] arrayOfString = localRunningAppProcessInfo.pkgList;
                    if (localRunningAppProcessInfo.importance >= 100) {
                        for (int j = 0; j < arrayOfString.length; j++) {
                            if (VERSION.SDK_INT <= 8) {
                                localActivityManager.restartPackage(arrayOfString[j]);
                            } else {
                                localActivityManager.killBackgroundProcesses(arrayOfString[j]);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void aU(Context ctx) {
        for (ApplicationInfo localApplicationInfo : ctx.getPackageManager().getInstalledApplications(128)) {
            if (localApplicationInfo.metaData != null && localApplicationInfo.metaData.getString("net.zhuoweizhang.mcpelauncher.api.nativelibname") == null) {
            }
        }
    }
}
