package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;

class PushManager$33 implements Runnable {
    final /* synthetic */ Context a;

    PushManager$33(Context context) {
        this.a = context;
    }

    public void run() {
        BridgePushManager.tryConnect(this.a);
    }
}
