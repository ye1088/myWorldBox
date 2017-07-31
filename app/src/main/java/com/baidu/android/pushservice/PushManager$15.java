package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;

class PushManager$15 implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;
    final /* synthetic */ String d;
    final /* synthetic */ String e;

    PushManager$15(Context context, String str, String str2, String str3, String str4) {
        this.a = context;
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = str4;
    }

    public void run() {
        BridgePushManager.sendMsgToUser(this.a, this.b, this.c, this.d, this.e);
    }
}
