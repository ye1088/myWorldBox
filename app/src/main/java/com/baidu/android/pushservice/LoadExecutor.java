package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.silentupdate.SilentManager;

public class LoadExecutor {
    private static final String a = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCYAFbG0oYmKgh6o7BhZIHf1njBpZXqyWBnYz2ip3Wp+s97OeA/pTe8xebuGJHwq4xbsGQrJWepIbUVrdjm6JRmdvuJhar7/hC/UNnUkJgYdYl10OZKlvcFFgK3V7XGBPplXldDnhbgscna3JG8U3025WSxZCP5vy/8cfxsEoVx5QIDAQAB";

    public static void excuteMethod(final Runnable runnable, final Context context) {
        if (isPushLibLoaded(context)) {
            runnable.run();
        } else {
            new Thread() {
                public void run() {
                    if (LoadExecutor.loadPush(context)) {
                        runnable.run();
                    }
                }
            }.start();
        }
    }

    public static boolean isPushLibLoaded(Context context) {
        try {
            context.getClassLoader().loadClass("com.baidu.android.pushservice.internal.PushManager");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static synchronized boolean loadPush(Context context) {
        boolean z = true;
        synchronized (LoadExecutor.class) {
            try {
                context.getClassLoader().loadClass("com.baidu.android.pushservice.internal.PushManager");
            } catch (ClassNotFoundException e) {
                SilentManager.setKey(a);
                try {
                    SilentManager.loadLib(context, "frontia_plugin", "plugin-deploy.jar");
                    context.getClassLoader().loadClass("com.baidu.android.pushservice.internal.PushManager");
                } catch (Exception e2) {
                    z = false;
                }
            }
        }
        return z;
    }
}
