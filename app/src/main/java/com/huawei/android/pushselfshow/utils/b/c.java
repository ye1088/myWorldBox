package com.huawei.android.pushselfshow.utils.b;

import com.huawei.android.pushagent.c.a.e;

class c implements Runnable {
    final /* synthetic */ b a;

    c(b bVar) {
        this.a = bVar;
    }

    public void run() {
        try {
            if (!(this.a.b == null || this.a.c == null)) {
                String a = this.a.a(this.a.b, this.a.c, this.a.d);
                e.a("PushSelfShowLog", "getDownloadFileWithHandler success, and localfile =  " + a);
                if (a != null) {
                    this.a.a(a);
                    return;
                }
            }
        } catch (Throwable e) {
            e.c("PushSelfShowLog", "getDownloadFileWithHandler failed ", e);
        }
        this.a.c();
    }
}
