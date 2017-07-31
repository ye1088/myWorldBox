package com.huawei.android.pushagent;

import android.content.Intent;
import com.huawei.android.pushagent.b.a;
import com.huawei.android.pushagent.c.a.e;

final class b$a implements Runnable {
    final /* synthetic */ b a;
    private a b;
    private Intent c;

    public b$a(b bVar, a aVar, Intent intent) {
        this.a = bVar;
        this.b = aVar;
        this.c = intent;
    }

    public void run() {
        try {
            this.b.a(b.b(this.a), this.c);
        } catch (Throwable e) {
            e.c("PushLogAC2705", "ReceiverDispatcher: call Receiver:" + this.b.getClass().getSimpleName() + ", intent:" + this.c + " failed:" + e.toString(), e);
        }
    }
}
