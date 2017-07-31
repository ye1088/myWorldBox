package com.huawei.android.pushagent.b.a.a.a;

import android.content.Context;
import android.os.Bundle;
import com.huawei.android.pushagent.a.b;
import com.huawei.android.pushagent.a.c;
import com.huawei.android.pushagent.a.d;
import com.huawei.android.pushagent.b.a.a$a;
import com.huawei.android.pushagent.c.a.e;
import java.net.InetSocketAddress;
import java.util.Date;

public class a extends com.huawei.android.pushagent.b.a.a.a {
    public a(d dVar, Context context) {
        super(dVar, context, new b(context), a.class.getSimpleName());
        f();
    }

    public void a(com.huawei.android.pushagent.b.a.a.c.a aVar, Bundle bundle) {
        e.a("PushLogAC2705", "enter PollingConnectEntity:notifyEvent(" + aVar + ",bd:" + bundle + ")");
        switch (aVar) {
            case SocketEvent_CONNECTED:
                this.e.a();
                this.e.a(System.currentTimeMillis());
                try {
                    a(new com.huawei.android.pushagent.a.a.a(com.huawei.android.pushagent.b.b.a.a(this.d).E()));
                    if (this.c != null) {
                        this.c.c().setSoTimeout((int) (com.huawei.android.pushagent.b.b.a.a(this.d).v() * 1000));
                        return;
                    }
                    return;
                } catch (Throwable e) {
                    e.c("PushLogAC2705", "call send cause:" + e.toString(), e);
                    return;
                }
            case SocketEvent_MSG_RECEIVED:
                b bVar = (b) bundle.getSerializable("push_msg");
                if (bVar == null) {
                    e.b("PushLogAC2705", "push_msg is null");
                    return;
                }
                e.b("PushLogAC2705", "received polling Msg:" + bVar.getClass().getSimpleName());
                if (bVar instanceof com.huawei.android.pushagent.a.a.b) {
                    com.huawei.android.pushagent.a.a.b bVar2 = (com.huawei.android.pushagent.a.a.b) bVar;
                    if (bVar2.d() < (byte) 0 || bVar2.d() > a$a.values().length) {
                        e.d("PushLogAC2705", "received mode:" + bVar2.d() + " cannot be recongnized");
                        return;
                    }
                    a$a com_huawei_android_pushagent_b_a_a_a = a$a.values()[bVar2.d()];
                    com.huawei.android.pushagent.b.a.a.a(this.d).a(com_huawei_android_pushagent_b_a_a_a);
                    this.e.b(((long) bVar2.f()) * 1000);
                    if (bVar2.e() || com_huawei_android_pushagent_b_a_a_a == a$a.ConnectEntity_Push) {
                        try {
                            com.huawei.android.pushagent.b.a.a.e().a(true, bVar2.e());
                        } catch (Throwable e2) {
                            e.c("PushLogAC2705", e2.toString(), e2);
                        }
                    }
                    if (this.c != null) {
                        try {
                            this.c.a();
                            return;
                        } catch (Throwable e22) {
                            e.c("PushLogAC2705", "call channel close cause:" + e22.toString(), e22);
                            return;
                        }
                    }
                    return;
                }
                return;
            default:
                return;
        }
    }

    public synchronized void a(boolean z) throws c {
        e.a("PushLogAC2705", "enter PollingConnectEntity:connect(forceCon:" + z + ")");
        this.e.b();
        if (com.huawei.android.pushagent.b.b.a.a(this.d).X()) {
            if (a()) {
                e.b("PushLogAC2705", "Polling aready connect, just wait Rsp!");
            } else {
                if (!z) {
                    if (System.currentTimeMillis() < this.e.d() + this.e.b(false) && System.currentTimeMillis() > this.e.d()) {
                        e.b("PushLogAC2705", "cannot connect, heartBeatInterval:" + this.e.b(false) + " lastCntTime:" + new Date(this.e.d()));
                    }
                }
                if (com.huawei.android.pushagent.c.a.b.a(this.d) == -1) {
                    e.b("PushLogAC2705", "no network, so cannot connect Polling");
                } else if (this.b == null || !this.b.isAlive()) {
                    e.a("PushLogAC2705", "begin to create new socket, so close socket");
                    b();
                    d();
                    InetSocketAddress b = com.huawei.android.pushagent.b.b.a.a(this.d).b(false);
                    if (b != null) {
                        e.a("PushLogAC2705", "get pollingSrvAddr:" + b);
                        this.a.a = b.getAddress().getHostAddress();
                        this.a.b = b.getPort();
                        this.b = new c(this);
                        this.b.start();
                    } else {
                        e.d("PushLogAC2705", "no valid pollingSrvAddr, just wait!!");
                    }
                } else {
                    e.b("PushLogAC2705", "aready in connect, just wait!! srvSocket:" + this.b.toString());
                }
            }
        }
    }

    public synchronized void a(boolean z, boolean z2) throws c {
        a(z);
    }

    public a$a e() {
        return a$a.ConnectEntity_Polling;
    }

    public boolean f() {
        if (this.a == null) {
            this.a = new d("", -1, false, com.huawei.android.pushagent.b.a.b.b.a.ChannelType_Normal);
        }
        return true;
    }
}
