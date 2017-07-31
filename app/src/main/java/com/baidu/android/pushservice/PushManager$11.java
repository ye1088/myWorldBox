package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;

class PushManager$11 implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ int b;
    final /* synthetic */ int c;

    PushManager$11(Context context, int i, int i2) {
        this.a = context;
        this.b = i;
        this.c = i2;
    }

    public void run() {
        BridgePushManager.fetchMessages(this.a, this.b, this.c);
    }
}
