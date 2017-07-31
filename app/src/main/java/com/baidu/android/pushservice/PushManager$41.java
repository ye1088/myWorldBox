package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;

class PushManager$41 implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;
    final /* synthetic */ int c;

    PushManager$41(Context context, String str, int i) {
        this.a = context;
        this.b = str;
        this.c = i;
    }

    public void run() {
        BridgePushManager.sdkStartWork(this.a, this.b, this.c);
    }
}
