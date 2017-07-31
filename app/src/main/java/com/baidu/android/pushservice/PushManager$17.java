package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;
import java.util.List;

class PushManager$17 implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ List b;

    PushManager$17(Context context, List list) {
        this.a = context;
        this.b = list;
    }

    public void run() {
        BridgePushManager.setTags(this.a, this.b);
    }
}
