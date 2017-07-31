package com.baidu.android.pushservice;

import android.content.Context;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;
import java.util.List;

class PushManager$24 implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;
    final /* synthetic */ List c;

    PushManager$24(Context context, String str, List list) {
        this.a = context;
        this.b = str;
        this.c = list;
    }

    public void run() {
        BridgePushManager.delLappTags(this.a, this.b, this.c);
    }
}
