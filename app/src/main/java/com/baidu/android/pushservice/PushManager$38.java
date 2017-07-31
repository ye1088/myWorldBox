package com.baidu.android.pushservice;

import com.baidu.android.pushservice.apiproxy.BridgePushManager;
import java.util.HashMap;

class PushManager$38 implements Runnable {
    final /* synthetic */ HashMap a;

    PushManager$38(HashMap hashMap) {
        this.a = hashMap;
    }

    public void run() {
        BridgePushManager.saveAppNotiMap(this.a);
    }
}
