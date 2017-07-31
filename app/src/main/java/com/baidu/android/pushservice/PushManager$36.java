package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;

class PushManager$36 implements Runnable {
    final /* synthetic */ Context a;

    PushManager$36(Context context) {
        this.a = context;
    }

    public void run() {
        BridgePushManager.disableLbs(this.a);
    }
}
