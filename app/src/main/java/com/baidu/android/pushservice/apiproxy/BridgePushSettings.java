package com.baidu.android.pushservice.apiproxy;

import android.content.Context;
import com.baidu.android.pushservice.internal.PushSettings;

public class BridgePushSettings {
    public static void enableDebugMode(Context context, boolean z) {
        PushSettings.enableDebugMode(context, z);
    }
}
