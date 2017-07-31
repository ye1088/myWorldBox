package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushSettings;

public class PushSettings {
    public static void enableDebugMode(final Context context, final boolean z) {
        new Thread() {
            public void run() {
                if (LoadExecutor.loadPush(context)) {
                    BridgePushSettings.enableDebugMode(context, z);
                }
            }
        }.start();
    }
}
