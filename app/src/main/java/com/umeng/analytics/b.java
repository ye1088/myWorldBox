package com.umeng.analytics;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;

/* compiled from: Helper */
public class b {
    public static boolean a(Context context) {
        CharSequence charSequence;
        try {
            int myPid = Process.myPid();
            String str = null;
            for (RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
                if (runningAppProcessInfo.pid == myPid) {
                    str = runningAppProcessInfo.processName.trim();
                }
            }
            charSequence = str;
        } catch (Exception e) {
            charSequence = null;
        }
        if (TextUtils.isEmpty(charSequence) || context.getPackageName().equals(charSequence)) {
            return true;
        }
        return false;
    }
}
