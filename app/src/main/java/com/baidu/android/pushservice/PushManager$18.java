package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;

class PushManager$18 implements Runnable {
    final /* synthetic */ Context a;

    PushManager$18(Context context) {
        this.a = context;
    }

    public void run() {
        BridgePushManager.listTags(this.a);
    }
}
