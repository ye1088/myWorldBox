package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;

class PushManager$31 implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ int b;
    final /* synthetic */ PushNotificationBuilder c;

    PushManager$31(Context context, int i, PushNotificationBuilder pushNotificationBuilder) {
        this.a = context;
        this.b = i;
        this.c = pushNotificationBuilder;
    }

    public void run() {
        BridgePushManager.setNotificationBuilder(this.a, this.b, this.c.getInner());
    }
}
