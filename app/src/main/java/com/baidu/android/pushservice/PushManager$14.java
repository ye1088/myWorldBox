package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;

class PushManager$14 implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ String[] b;

    PushManager$14(Context context, String[] strArr) {
        this.a = context;
        this.b = strArr;
    }

    public void run() {
        BridgePushManager.deleteMessages(this.a, this.b);
    }
}
