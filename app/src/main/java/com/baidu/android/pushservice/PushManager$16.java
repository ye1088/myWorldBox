package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;

class PushManager$16 implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;

    PushManager$16(Context context, String str) {
        this.a = context;
        this.b = str;
    }

    public void run() {
        BridgePushManager.bindGroup(this.a, this.b);
    }
}
