package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;

class PushManager$2 implements Runnable {
    final /* synthetic */ Context a;

    PushManager$2(Context context) {
        this.a = context;
    }

    public void run() {
        BridgePushManager.stopWork(this.a);
    }
}
