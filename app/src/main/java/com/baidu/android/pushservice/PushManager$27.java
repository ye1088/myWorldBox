package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;

class PushManager$27 implements Runnable {
    final /* synthetic */ Context a;

    PushManager$27(Context context) {
        this.a = context;
    }

    public void run() {
        BridgePushManager.getGroupList(this.a);
    }
}
