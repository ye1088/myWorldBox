package com.huawei.android.pushagent.b.b;

import com.huawei.android.pushagent.a.e;
import com.huawei.android.pushagent.c.c.f;
import com.huawei.android.pushagent.plugin.tools.BLocation;

class b extends Thread {
    final /* synthetic */ a a;

    b(a aVar, String str) {
        this.a = aVar;
        super(str);
    }

    public void run() {
        try {
            e a = f.a(a.a(this.a), this.a.b());
            if (a == null) {
                a = new e(a.b(this.a));
            }
            if (a.V()) {
                a.a(this.a, a);
            } else {
                com.huawei.android.pushagent.c.a.e.b(BLocation.TAG, "query trs error:" + this.a.a());
            }
        } catch (Throwable e) {
            com.huawei.android.pushagent.c.a.e.c(BLocation.TAG, e.toString(), e);
        }
    }
}
