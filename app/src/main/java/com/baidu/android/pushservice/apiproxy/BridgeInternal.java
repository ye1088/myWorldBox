package com.baidu.android.pushservice.apiproxy;

import android.content.Context;
import com.baidu.android.pushservice.util.Internal;

public class BridgeInternal {
    public static void createBdussIntent(Context context) {
        Internal.createBdussInent(context);
    }

    public static void disablePushConnection(Context context) {
        Internal.disablePushConnection(context);
    }

    public static void disablePushService(Context context) {
        Internal.disablePushService(context);
    }

    public static void enablePushConnection(Context context) {
        Internal.enablePushConnection(context);
    }

    public static void enablePushService(Context context) {
        Internal.enablePushService(context);
    }
}
