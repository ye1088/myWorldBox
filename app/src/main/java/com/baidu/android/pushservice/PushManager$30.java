package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;

class PushManager$30 implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ PushNotificationBuilder b;

    PushManager$30(Context context, PushNotificationBuilder pushNotificationBuilder) {
        this.a = context;
        this.b = pushNotificationBuilder;
    }

    public void run() {
        BridgePushManager.setDefaultNotificationBuilder(this.a, this.b.getInner());
    }
}
