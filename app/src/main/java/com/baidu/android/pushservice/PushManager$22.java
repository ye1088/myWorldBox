package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;
import java.util.List;

class PushManager$22 implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;
    final /* synthetic */ List c;

    PushManager$22(Context context, String str, List list) {
        this.a = context;
        this.b = str;
        this.c = list;
    }

    public void run() {
        BridgePushManager.delSDKTags(this.a, this.b, this.c);
    }
}
