package com.huawei.android.pushagent.b.a.a.a;

import android.os.Bundle;
import com.huawei.android.pushagent.b.a.a.a;
import com.huawei.android.pushagent.b.a.b.b;
import com.huawei.android.pushagent.c.a.e;
import java.io.InputStream;
import java.io.Serializable;
import java.net.SocketException;

public class c extends com.huawei.android.pushagent.b.a.a.c {
    public c(a aVar) {
        super(aVar);
    }

    protected void b() throws Exception {
        InputStream d;
        Throwable e;
        Object obj;
        b bVar = null;
        try {
            if (this.c.c == null || this.c.c.c() == null) {
                e.d("PushLogAC2705", "no socket when in readSSLSocket");
                if (bVar != null) {
                    bVar.close();
                }
                if (this.c.c != null) {
                    this.c.c.a();
                    this.c.c = bVar;
                    return;
                }
                return;
            }
            e.a("PushLogAC2705", "socket timeout is " + this.c.c.c().getSoTimeout());
            d = this.c.c.d();
            while (!isInterrupted() && this.c.c.b()) {
                try {
                    Serializable b;
                    if (d != null) {
                        b = com.huawei.android.pushagent.a.a.a.b.b(d);
                    } else {
                        e.b("PushLogAC2705", "InputStream is null, get pollingMessage failed");
                        Object obj2 = bVar;
                    }
                    if (b != null) {
                        com.huawei.android.pushagent.c.a.b();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("push_msg", b);
                        this.c.a(com.huawei.android.pushagent.b.a.a.c.a.SocketEvent_MSG_RECEIVED, bundle);
                    }
                } catch (SocketException e2) {
                    e.a("PushLogAC2705", "SocketException:" + e2.toString());
                } catch (Throwable e3) {
                    e.c("PushLogAC2705", "call getEntityByCmdId cause:" + e3.toString(), e3);
                    throw e3;
                } catch (Exception e4) {
                    e3 = e4;
                }
            }
            if (d != null) {
                d.close();
            }
            if (this.c.c != null) {
                this.c.c.a();
                this.c.c = bVar;
            }
        } catch (Exception e5) {
            e3 = e5;
            obj = bVar;
            try {
                throw new com.huawei.android.pushagent.a.c(e3, com.huawei.android.pushagent.a.c.a.Err_Read);
            } catch (Throwable th) {
                e3 = th;
                if (d != null) {
                    d.close();
                }
                if (this.c.c != null) {
                    this.c.c.a();
                    this.c.c = bVar;
                }
                throw e3;
            }
        } catch (Throwable th2) {
            e3 = th2;
            obj = bVar;
            if (d != null) {
                d.close();
            }
            if (this.c.c != null) {
                this.c.c.a();
                this.c.c = bVar;
            }
            throw e3;
        }
    }
}
