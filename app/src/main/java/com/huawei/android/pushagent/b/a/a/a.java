package com.huawei.android.pushagent.b.a.a;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import com.huawei.android.pushagent.PushService;
import com.huawei.android.pushagent.a.c;
import com.huawei.android.pushagent.a.d;
import com.huawei.android.pushagent.b.a.a$a;
import com.huawei.android.pushagent.b.a.b.b;
import com.huawei.android.pushagent.c.a.e;
import java.net.Socket;

public abstract class a {
    public d a;
    public c b;
    public b c;
    public Context d;
    public b e;
    protected WakeLock f = null;
    private PowerManager g;

    enum a {
        CONNECT_METHOD_DIRECT_TrsPort,
        CONNECT_METHOD_DIRECT_DefaultPort,
        CONNECT_METHOD_Proxy_TrsPort,
        CONNECT_METHOD_Proxy_DefaultPort
    }

    public a(d dVar, Context context, b bVar, String str) {
        this.d = context;
        this.a = dVar;
        this.e = bVar;
        this.g = (PowerManager) context.getSystemService("power");
    }

    protected d a(int i, int i2) {
        switch (a.values()[b(i, i2)]) {
            case CONNECT_METHOD_DIRECT_TrsPort:
                return new d(this.a.a, this.a.b, false, this.a.c);
            case CONNECT_METHOD_DIRECT_DefaultPort:
                return new d(this.a.a, 443, false, this.a.c);
            case CONNECT_METHOD_Proxy_TrsPort:
                return new d(this.a.a, 443, true, this.a.c);
            case CONNECT_METHOD_Proxy_DefaultPort:
                return new d(this.a.a, this.a.b, true, this.a.c);
            default:
                return null;
        }
    }

    public abstract void a(com.huawei.android.pushagent.b.a.a.c.a aVar, Bundle bundle);

    public abstract void a(boolean z) throws c;

    public abstract void a(boolean z, boolean z2) throws c;

    public boolean a() {
        return this.c != null && this.c.b();
    }

    public synchronized boolean a(com.huawei.android.pushagent.a.b bVar) throws Exception {
        boolean z = false;
        synchronized (this) {
            if (this.c == null || this.c.c() == null) {
                e.d("PushLogAC2705", "when send pushMsg, channel is null， curCls:" + getClass().getSimpleName());
            } else {
                if (com.huawei.android.pushagent.b.a.a.c() == e()) {
                    this.c.c().setSoTimeout(0);
                } else {
                    this.c.c().setSoTimeout((int) (this.e.b(false) + com.huawei.android.pushagent.b.b.a.a(this.d).Q()));
                }
                byte[] bArr = null;
                if (bVar != null) {
                    bArr = bVar.b();
                } else {
                    e.d("PushLogAC2705", "pushMsg = null, send fail");
                }
                if (bArr == null || bArr.length == 0) {
                    e.b("PushLogAC2705", "when send PushMsg, encode Len is null");
                } else {
                    e.b("PushLogAC2705", "read to Send:" + com.huawei.android.pushagent.c.a.a(bVar.a()));
                    if (this.c.a(bArr)) {
                        PushService.a(new Intent("com.huawei.android.push.intent.MSG_SENT").putExtra("push_msg", bVar));
                        z = true;
                    } else {
                        e.d("PushLogAC2705", "call channel.send false!!");
                    }
                }
            }
        }
        return z;
    }

    protected int b(int i, int i2) {
        return Math.abs(i + i2) % a.values().length;
    }

    protected synchronized void b() {
        this.f = this.g.newWakeLock(1, "mWakeLockForThread");
        this.f.setReferenceCounted(false);
        this.f.acquire(1000);
    }

    public Socket c() {
        return this.c != null ? this.c.c() : null;
    }

    public void d() {
        if (this.c != null) {
            try {
                this.c.a();
                this.c = null;
            } catch (Throwable e) {
                e.c("PushLogAC2705", "call channel.close() cause:" + e.toString(), e);
            }
            if (this.b != null) {
                this.b.interrupt();
                this.b = null;
            }
        }
    }

    public abstract a$a e();

    public String toString() {
        return this.a.toString() + " " + this.e.toString();
    }
}
