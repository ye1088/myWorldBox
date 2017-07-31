package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;

class PushManager$28 implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;
    final /* synthetic */ int c;
    final /* synthetic */ int d;

    PushManager$28(Context context, String str, int i, int i2) {
        this.a = context;
        this.b = str;
        this.c = i;
        this.d = i2;
    }

    public void run() {
        BridgePushManager.fetchGroupMessages(this.a, this.b, this.c, this.d);
    }
}
