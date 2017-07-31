package com.huawei.android.pushagent;

import android.os.MessageQueue.IdleHandler;

class c implements IdleHandler {
    final /* synthetic */ b a;

    c(b bVar) {
        this.a = bVar;
    }

    public boolean queueIdle() {
        if (b.a(this.a).isHeld()) {
            b.a(this.a).release();
        }
        return true;
    }
}
