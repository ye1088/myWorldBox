package com.baidu.android.pushservice.apiproxy;

import android.content.Context;
import android.content.Intent;
import com.baidu.android.pushservice.internal.PushConstants;

public class BridgePushConstants {
    public static Intent createMethodIntent(Context context) {
        return PushConstants.createMethodIntent(context);
    }

    public static void restartPushService(Context context) {
        PushConstants.restartPushService(context);
    }

    public static void startPushService(Context context) {
        PushConstants.startPushService(context);
    }
}
