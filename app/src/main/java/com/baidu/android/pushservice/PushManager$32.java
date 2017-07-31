package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;

class PushManager$32 implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ PushNotificationBuilder b;

    PushManager$32(Context context, PushNotificationBuilder pushNotificationBuilder) {
        this.a = context;
        this.b = pushNotificationBuilder;
    }

    public void run() {
        BridgePushManager.setMediaNotificationBuilder(this.a, this.b.getInner());
    }
}
