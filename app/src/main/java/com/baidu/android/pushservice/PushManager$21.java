package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;
import java.util.List;

class PushManager$21 implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ List b;

    PushManager$21(Context context, List list) {
        this.a = context;
        this.b = list;
    }

    public void run() {
        BridgePushManager.delTags(this.a, this.b);
    }
}
