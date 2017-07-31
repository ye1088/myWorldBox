package com.baidu.android.pushservice;

import android.app.Notification;
import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgeBasicPushNotificationBuilder;

public class BasicPushNotificationBuilder {
    private BridgeBasicPushNotificationBuilder a;

    public BasicPushNotificationBuilder(final Context context) {
        new Thread(this) {
            final /* synthetic */ BasicPushNotificationBuilder b;

            public void run() {
                if (LoadExecutor.loadPush(context)) {
                    this.b.a = new BridgeBasicPushNotificationBuilder();
                }
            }
        }.start();
    }

    public BasicPushNotificationBuilder(Context context, BridgeBasicPushNotificationBuilder bridgeBasicPushNotificationBuilder) {
        this.a = bridgeBasicPushNotificationBuilder;
    }

    public Notification construct(Context context) {
        return !LoadExecutor.loadPush(context) ? null : this.a.construct(context);
    }

    public BridgeBasicPushNotificationBuilder getInner() {
        return this.a;
    }
}
