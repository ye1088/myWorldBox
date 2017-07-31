package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;

class PushManager$40 implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ int b;
    final /* synthetic */ String c;

    PushManager$40(Context context, int i, String str) {
        this.a = context;
        this.b = i;
        this.c = str;
    }

    public void run() {
        BridgePushManager.startWork(this.a, this.b, this.c);
    }
}
