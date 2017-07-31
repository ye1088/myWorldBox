package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;

class PushManager$20 implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;

    PushManager$20(Context context, String str) {
        this.a = context;
        this.b = str;
    }

    public void run() {
        BridgePushManager.listLappTags(this.a, this.b);
    }
}
