package com.baidu.android.pushservice;

import android.app.Activity;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;

class PushManager$5 implements Runnable {
    final /* synthetic */ Activity a;

    PushManager$5(Activity activity) {
        this.a = activity;
    }

    public void run() {
        BridgePushManager.activityStoped(this.a);
    }
}
