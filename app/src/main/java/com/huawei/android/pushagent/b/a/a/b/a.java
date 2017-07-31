package com.huawei.android.pushagent.b.a.a.b;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.huawei.android.pushagent.PushService;
import com.huawei.android.pushagent.a.b.f;
import com.huawei.android.pushagent.a.b.k;
import com.huawei.android.pushagent.a.d;
import com.huawei.android.pushagent.b.a.a$a;
import com.huawei.android.pushagent.b.b.c;
import com.huawei.android.pushagent.b.d.b;
import com.huawei.android.pushagent.c.a.e;
import java.net.InetSocketAddress;

public class a extends com.huawei.android.pushagent.b.a.a.a {
    boolean g = false;

    public a(d dVar, Context context) {
        super(dVar, context, new b(context), a.class.getSimpleName());
        f();
    }

    public void a(com.huawei.android.pushagent.b.a.a.c.a aVar, Bundle bundle) {
        int a = c.a(this.d, "tryConnectPushSevTimes", 0);
        int a2 = c.a(this.d, "lastConnectPushSrvMethodIdx", 0);
        e.a("PushLogAC2705", "enter PushConnectEntity. notifyEvent is " + aVar + ", " + " tryConnectPushSevTimes:" + a + " lastConnctIdx:" + a2);
        switch (aVar) {
            case SocketEvent_CONNECTING:
                PushService.a(new Intent("com.huawei.android.push.intent.CONNECTING"));
                return;
            case SocketEvent_CONNECTED:
                this.e.a();
                this.e.a(System.currentTimeMillis());
                b.a(this.d).a(this.d, b.b.SOCKET_CONNECTED, new Bundle());
                c.a(this.d, new com.huawei.android.pushagent.a.a("lastcontectsucc_time", Long.class, Long.valueOf(System.currentTimeMillis())));
                Intent intent = new Intent("com.huawei.android.push.intent.CONNECTED");
                if (bundle != null) {
                    intent.putExtras(bundle);
                }
                PushService.a(intent);
                return;
            case SocketEvent_CLOSE:
                bundle.putInt("connect_mode", e().ordinal());
                PushService.a(new Intent("com.huawei.android.push.intent.CHANNEL_CLOSED").putExtras(bundle));
                if (com.huawei.android.pushagent.b.a.a.c() == e()) {
                    com.huawei.android.pushagent.c.a.a.a(this.d, "com.huawei.android.push.intent.HEARTBEAT_RSP_TIMEOUT");
                    b.a(this.d).a(this.d, b.b.SOCKET_CLOSE, bundle);
                }
                if (!this.g) {
                    int i = a + 1;
                    e.b("PushLogAC2705", "channel is not Regist, tryConnectPushSevTimes add to " + i);
                    c.a(this.d, new com.huawei.android.pushagent.a.a("tryConnectPushSevTimes", Integer.class, Integer.valueOf(i)));
                    c.a(this.d, new com.huawei.android.pushagent.a.a("lastConnectPushSrvMethodIdx", Integer.class, Integer.valueOf(a2)));
                    return;
                }
                return;
            case SocketEvent_MSG_RECEIVED:
                com.huawei.android.pushagent.a.b bVar = (com.huawei.android.pushagent.a.b) bundle.getSerializable("push_msg");
                if (bVar == null) {
                    e.b("PushLogAC2705", "push_msg is null");
                    return;
                }
                e.a("PushLogAC2705", "received pushSrv Msg:" + com.huawei.android.pushagent.c.a.a(bVar.a()));
                if (bVar.a() == (byte) -45 || bVar.a() == (byte) -33) {
                    this.g = true;
                    c.a(this.d, new com.huawei.android.pushagent.a.a("lastConnectPushSrvMethodIdx", Integer.class, Integer.valueOf(b(a, a2))));
                    c.a(this.d, new com.huawei.android.pushagent.a.a("tryConnectPushSevTimes", Integer.class, Integer.valueOf(0)));
                } else if ((bVar instanceof f) || (bVar instanceof k)) {
                    com.huawei.android.pushagent.c.a.a.a(this.d, "com.huawei.android.push.intent.HEARTBEAT_RSP_TIMEOUT");
                    this.e.c(false);
                }
                this.e.a();
                Intent intent2 = new Intent("com.huawei.android.push.intent.MSG_RECEIVED");
                intent2.putExtra("push_msg", bVar);
                PushService.a(intent2);
                return;
            default:
                return;
        }
    }

    public synchronized void a(boolean z) throws com.huawei.android.pushagent.a.c {
        a(z, false);
    }

    public synchronized void a(boolean z, boolean z2) throws com.huawei.android.pushagent.a.c {
        try {
            e.a("PushLogAC2705", "enter PushConnectEntity:connect(isForceToConnPushSrv:" + z + ")");
            this.e.b();
            if (com.huawei.android.pushagent.b.b.a.a(this.d).V()) {
                if (com.huawei.android.pushagent.c.a.b.a(this.d) == -1) {
                    e.d("PushLogAC2705", "no network, so cannot connect");
                } else {
                    if (c.a(this.d, "cloudpush_isNoDelayConnect", false)) {
                        z = true;
                    }
                    if (!a()) {
                        int a = c.a(this.d, "tryConnectPushSevTimes", 0);
                        long b = b.a(this.d).b(this.d);
                        if (b <= 0) {
                            e.a("PushLogAC2705", "no limit to connect pushsvr");
                            if (this.b == null || !this.b.isAlive()) {
                                e.a("PushLogAC2705", "begin to create new socket, so close socket");
                                b();
                                d();
                                e.a("PushLogAC2705", "IS_NODELAY_CONNECT:" + c.a(this.d, "cloudpush_isNoDelayConnect", false) + " hasMsg:" + z2);
                                if (c.a(this.d, "cloudpush_isNoDelayConnect", false) || z2 || com.huawei.android.pushagent.b.d.a.a(this.d, 1)) {
                                    this.g = false;
                                    int a2 = c.a(this.d, "lastConnectPushSrvMethodIdx", 0);
                                    InetSocketAddress a3 = com.huawei.android.pushagent.b.b.a.a(this.d).a(z);
                                    if (a3 != null) {
                                        e.a("PushLogAC2705", "get pushSrvAddr:" + a3);
                                        this.a.a = a3.getAddress().getHostAddress();
                                        this.a.b = a3.getPort();
                                        this.a.c = c.b(this.d);
                                        this.a = a(a2, a);
                                        this.b = new c(this);
                                        this.b.start();
                                    } else {
                                        e.a("PushLogAC2705", "no valid pushSrvAddr, just wait!!");
                                    }
                                } else {
                                    com.huawei.android.pushagent.b.a.a.a(this.d).a(a$a.ConnectEntity_Polling);
                                    com.huawei.android.pushagent.b.a.a.a(this.d).a(a$a.ConnectEntity_Polling, false);
                                }
                            } else {
                                e.b("PushLogAC2705", "It is in connecting...");
                            }
                        } else {
                            com.huawei.android.pushagent.b.a.a.a(this.d).a(b);
                        }
                    } else if (z) {
                        e.a("PushLogAC2705", "hasConnect, but isForceToConnPushSrv:" + z + ", so send heartBeat");
                        this.e.g();
                    } else {
                        e.a("PushLogAC2705", "aready connect, need not connect more");
                    }
                }
            }
        } catch (Throwable e) {
            throw new com.huawei.android.pushagent.a.c(e);
        }
    }

    public a$a e() {
        return a$a.ConnectEntity_Push;
    }

    public boolean f() {
        if (this.a == null) {
            this.a = new d("", -1, false, c.b(this.d));
        }
        return true;
    }
}
