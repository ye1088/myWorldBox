package com.huawei.android.pushselfshow.richpush.html.api;

import com.huawei.android.pushagent.c.a.e;

class a implements Runnable {
    final /* synthetic */ OnlineEventsBridgeMode a;

    a(OnlineEventsBridgeMode onlineEventsBridgeMode) {
        this.a = onlineEventsBridgeMode;
    }

    public void run() {
        boolean a = this.a.c.d();
        e.a("PushSelfShowLog", "bEmptyMsg is " + a);
        if (!a) {
            this.a.a = !this.a.a;
            this.a.c.a.setNetworkAvailable(this.a.a);
            e.a("PushSelfShowLog", "setNetworkAvailable ： " + this.a.a);
        }
    }
}
