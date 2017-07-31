package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;

class PushManager$39 implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ int b;
    final /* synthetic */ int c;
    final /* synthetic */ int d;
    final /* synthetic */ int e;

    PushManager$39(Context context, int i, int i2, int i3, int i4) {
        this.a = context;
        this.b = i;
        this.c = i2;
        this.d = i3;
        this.e = i4;
    }

    public void run() {
        BridgePushManager.setNoDisturbMode(this.a, this.b, this.c, this.d, this.e);
    }
}
