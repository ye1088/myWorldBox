package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;

class PushManager$23 implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;

    PushManager$23(Context context, String str) {
        this.a = context;
        this.b = str;
    }

    public void run() {
        BridgePushManager.initFromAKSK(this.a, this.b);
    }
}
