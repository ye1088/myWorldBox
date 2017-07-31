package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;

class PushManager$9 implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ int b;

    PushManager$9(Context context, int i) {
        this.a = context;
        this.b = i;
    }

    public void run() {
        BridgePushManager.bind(this.a, this.b);
    }
}
