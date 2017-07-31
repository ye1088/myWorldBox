package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;

class PushManager$13 implements Runnable {
    final /* synthetic */ Context a;

    PushManager$13(Context context) {
        this.a = context;
    }

    public void run() {
        BridgePushManager.getMessageCounts(this.a);
    }
}
