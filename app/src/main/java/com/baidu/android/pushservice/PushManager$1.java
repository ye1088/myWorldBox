package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;

class PushManager$1 implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;

    PushManager$1(Context context, String str, String str2) {
        this.a = context;
        this.b = str;
        this.c = str2;
    }

    public void run() {
        BridgePushManager.init(this.a, this.b, this.c);
    }
}
