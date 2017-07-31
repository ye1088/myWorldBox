package com.baidu.android.pushservice;

import android.app.Activity;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;

class PushManager$4 implements Runnable {
    final /* synthetic */ Activity a;

    PushManager$4(Activity activity) {
        this.a = activity;
    }

    public void run() {
        BridgePushManager.activityStarted(this.a);
    }
}
