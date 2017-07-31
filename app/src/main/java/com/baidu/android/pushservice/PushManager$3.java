package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;

class PushManager$3 implements Runnable {
    final /* synthetic */ Context a;

    PushManager$3(Context context) {
        this.a = context;
    }

    public void run() {
        BridgePushManager.resumeWork(this.a);
    }
}
