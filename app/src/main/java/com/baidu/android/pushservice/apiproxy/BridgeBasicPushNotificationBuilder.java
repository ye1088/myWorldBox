package com.baidu.android.pushservice.apiproxy;

import android.app.Notification;
import android.content.Context;
import com.baidu.android.pushservice.internal.BasicPushNotificationBuilder;

public class BridgeBasicPushNotificationBuilder {
    private BasicPushNotificationBuilder a;

    public BridgeBasicPushNotificationBuilder() {
        this.a = new BasicPushNotificationBuilder();
    }

    public BridgeBasicPushNotificationBuilder(BasicPushNotificationBuilder basicPushNotificationBuilder) {
        this.a = basicPushNotificationBuilder;
    }

    public Notification construct(Context context) {
        return this.a.construct(context);
    }

    public BasicPushNotificationBuilder getInner() {
        return this.a;
    }
}
