package com.MCWorld.mipush;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Process;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.List;

/* compiled from: MiPushManager */
public class a {
    private Context mContext;

    private a() {
    }

    public static a Dw() {
        return a.Dx();
    }

    public void init(Context context, String appid, String appkey) {
        if (aZ(context)) {
            this.mContext = context;
            MiPushClient.registerPush(context, appid, appkey);
        }
    }

    private static boolean aZ(Context context) {
        List<RunningAppProcessInfo> processInfos = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (processInfos == null) {
            return true;
        }
        String mainProcessName = context.getPackageName();
        int myPid = Process.myPid();
        for (RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public static void ba(Context context) {
        MiPushClient.clearNotification(context);
    }
}
