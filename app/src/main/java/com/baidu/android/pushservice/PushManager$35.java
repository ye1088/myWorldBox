package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;

class PushManager$35 implements Runnable {
    final /* synthetic */ Context a;

    PushManager$35(Context context) {
        this.a = context;
    }

    public void run() {
        BridgePushManager.enableLbs(this.a);
    }
}
