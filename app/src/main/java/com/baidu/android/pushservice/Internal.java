package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgeInternal;

public class Internal {
    public static void createBdussIntent(final Context context) {
        LoadExecutor.excuteMethod(new Runnable() {
            public void run() {
                BridgeInternal.createBdussIntent(context);
            }
        }, context);
    }

    public static void disablePushConnection(final Context context) {
        LoadExecutor.excuteMethod(new Runnable() {
            public void run() {
                BridgeInternal.disablePushConnection(context);
            }
        }, context);
    }

    public static void disablePushService(final Context context) {
        LoadExecutor.excuteMethod(new Runnable() {
            public void run() {
                BridgeInternal.disablePushService(context);
            }
        }, context);
    }

    public static void enablePushConnection(final Context context) {
        LoadExecutor.excuteMethod(new Runnable() {
            public void run() {
                BridgeInternal.enablePushConnection(context);
            }
        }, context);
    }

    public static void enablePushService(final Context context) {
        LoadExecutor.excuteMethod(new Runnable() {
            public void run() {
                BridgeInternal.enablePushService(context);
            }
        }, context);
    }
}
