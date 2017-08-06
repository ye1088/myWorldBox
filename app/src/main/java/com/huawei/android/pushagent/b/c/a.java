package com.huawei.android.pushagent.b.c;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.huawei.android.pushagent.PushService;
import com.huawei.android.pushagent.b.b.c;
import com.huawei.android.pushagent.c.a.b;
import com.huawei.android.pushagent.c.a.e;
import java.io.Serializable;
import java.util.List;

public class a extends com.huawei.android.pushagent.b.a {
    int a = -1;

    public a(Context context) {
        try {
            this.a = b.a(context);
            if (com.huawei.android.pushagent.b.e.b.b(context).size() != 0) {
                a(context, new Intent("com.huawei.action.CONNECT_PUSHSRV_PUSHSRV").setPackage(context.getPackageName()));
            } else {
                a(context, new Intent("com.huawei.action.CONNECT_PUSHSRV").setPackage(context.getPackageName()));
            }
        } catch (Throwable e) {
            e.c("PushLogAC2705", "call switchChannel cause Exception", e);
        }
    }

    private void a(Context context, String str) {
        try {
            boolean a = c.a(context, "cloudpush_isNoDelayConnect", false);
            List b = com.huawei.android.pushagent.b.e.b.b(context);
            if (!a && b.size() == 0 && com.huawei.android.pushagent.b.e.b.a(context).a.size() == 0) {
                com.huawei.android.pushagent.b.a.a.a(context).b();
                e.b("PushLogAC2705", "no push client, stop push apk service");
                com.huawei.android.pushagent.c.a.a.c(context, new Intent("com.huawei.intent.action.PUSH_OFF").setPackage(context.getPackageName()).putExtra("Remote_Package_Name", context.getPackageName()), com.huawei.android.pushagent.b.b.a.a(context).P() * 1000);
                return;
            }
            if (!com.huawei.android.pushagent.b.b.a.a(context).V()) {
                e.b("PushLogAC2705", "TRS is invalid, so need to query TRS");
                com.huawei.android.pushagent.b.b.a.a(context).c(false);
            }
            if ("com.huawei.android.push.intent.TRS_QUERY_SUCCESS".equals(str)) {
                com.huawei.android.pushagent.b.a.a.f().e.f();
                com.huawei.android.pushagent.b.a.a.e().e.f();
                com.huawei.android.pushagent.b.d.a.c(context);
            }
            int a2 = b.a(context);
            if (-1 == a2 || a2 != this.a) {
                if (-1 == a2) {
                    if (c.a(context, "cloudpush_isSupportCollectSocketInfo", false) && !com.huawei.android.pushagent.b.a.a.e().a()) {
                        context.sendBroadcast(new Intent("com.huawei.android.push.intent.SOCKET_INFO").putExtra("socket_add_info", "no network.").setPackage(context.getPackageName()));
                    }
                    e.a("PushLogAC2705", "no network in ConnectMgrProcessor:connect, so close socket");
                } else {
                    if (c.a(context, "cloudpush_isSupportCollectSocketInfo", false) && !com.huawei.android.pushagent.b.a.a.e().a()) {
                        context.sendBroadcast(new Intent("com.huawei.android.push.intent.SOCKET_INFO").putExtra("socket_add_info", "network switch.").setPackage(context.getPackageName()));
                    }
                    e.a("PushLogAC2705", "net work switch from:" + this.a + " to " + a2);
                }
                try {
                    com.huawei.android.pushagent.b.a.a.a(context).b();
                } catch (Throwable e) {
                    e.c("PushLogAC2705", "call channel.close cause exceptino:" + e.toString(), e);
                } catch (com.huawei.android.pushagent.a.c e2) {
                    e.d("PushLogAC2705", "call connectSrv cause Exceptino:" + e2.toString());
                }
            }
            Object obj = this.a != a2 ? 1 : null;
            e.b("PushLogAC2705", "lastnetWorkType:" + this.a + " " + "curNetWorkType:" + a2);
            this.a = a2;
            if (a) {
                com.huawei.android.pushagent.b.a.a.a(context).a(com.huawei.android.pushagent.b.a.a.a.a);
                com.huawei.android.pushagent.b.a.a.e().a(true);
            } else if (("android.net.conn.CONNECTIVITY_CHANGE".equals(str) || "com.huawei.android.push.intent.TRS_QUERY_SUCCESS".equals(str)) && !com.huawei.android.pushagent.b.a.a.e().a() && com.huawei.android.pushagent.b.a.a.c() != com.huawei.android.pushagent.b.a.a.a.a && b.size() != 0) {
                e.b("PushLogAC2705", "received " + str + ", cur ConType:" + com.huawei.android.pushagent.b.a.a.c() + ", but have need depose size:" + b.size());
                a(context, new Intent("com.huawei.action.CONNECT_PUSHSRV_PUSHSRV"));
            } else if ("com.huawei.action.CONNECT_PUSHSRV_PUSHSRV".equals(str)) {
                e.a("PushLogAC2705", "get " + str + " so get a_isRightVersion pushSrv to connect");
                if (b.size() != 0) {
                    com.huawei.android.pushagent.b.a.a.a(context).a(com.huawei.android.pushagent.b.a.a.a.a);
                }
                com.huawei.android.pushagent.b.a.a.e().a(true);
            } else if ("com.huawei.action.CONNECT_PUSHSRV_POLLINGSRV".equals(str)) {
                e.a("PushLogAC2705", "get " + str + " so get a_isRightVersion pollingSrv to connect");
                com.huawei.android.pushagent.b.a.a.f().a(true);
            } else if (com.huawei.android.pushagent.b.a.a.a(context).d().a()) {
                e.a("PushLogAC2705", "pushChannel already connect, so needn't handle, nextSendHearBeatTime:" + com.huawei.android.pushagent.c.a.a(com.huawei.android.pushagent.b.a.a.b(context).e(), "yyyy-MM-dd HH:mm:ss SSS"));
            } else {
                e.a("PushLogAC2705", "get " + str + " so get a_isRightVersion srv to connect");
                if (obj != null) {
                    com.huawei.android.pushagent.b.d.b.a(context).a(context, com.huawei.android.pushagent.b.d.b.b.d, new Bundle());
                }
                com.huawei.android.pushagent.b.a.a.a(context).d().a(false);
            }
        } catch (com.huawei.android.pushagent.a.c e22) {
            e.d("PushLogAC2705", "call connectSrv cause Exceptino:" + e22.toString());
        } catch (Exception e3) {
            e.d("PushLogAC2705", "call connectSrv cause Exceptino:" + e3.toString());
        }
    }

    private boolean a(Context context, Intent intent, String str, String str2) {
        return ("com.huawei.intent.action.PUSH".equals(str) && "com.huawei.android.push.intent.HEARTBEAT_REQ".equals(str2)) || "com.huawei.android.push.intent.REFRESH_PUSH_CHANNEL".equals(str) || "android.intent.action.TIME_SET".equals(str) || "android.intent.action.TIMEZONE_CHANGED".equals(str);
    }

    private boolean a(Context context, String str, String str2) {
        return "android.net.conn.CONNECTIVITY_CHANGE".equals(str) || "com.huawei.action.CONNECT_PUSHSRV".equals(str) || "com.huawei.action.CONNECT_PUSHSRV_PUSHSRV".equals(str) || "com.huawei.action.CONNECT_PUSHSRV_POLLINGSRV".equals(str) || "com.huawei.android.push.intent.TRS_QUERY_SUCCESS".equals(str) || ("com.huawei.intent.action.PUSH".equals(str) && "com.huawei.intent.action.PUSH_ON".equals(str2));
    }

    public void a(Context context, Intent intent) {
        try {
            e.a("PushLogAC2705", "enter ConnectMgrProcessor:onReceive(intent:" + intent + " context:" + context);
            String action = intent.getAction();
            String stringExtra = intent.getStringExtra("EXTRA_INTENT_TYPE");
            if ("com.huawei.android.push.intent.HEARTBEAT_RSP_TIMEOUT".equals(action)) {
                if (c.a(context, "cloudpush_isSupportCollectSocketInfo", false)) {
                    context.sendBroadcast(new Intent("com.huawei.android.push.intent.SOCKET_INFO").putExtra("socket_add_info", "heart beat time out.").setPackage(context.getPackageName()));
                }
                com.huawei.android.pushagent.b.a.a.a(context).a(intent);
            } else if (a(context, intent, action, stringExtra)) {
                com.huawei.android.pushagent.b.a.a.a(context).a(intent);
            } else if (a(context, action, stringExtra)) {
                a(context, action);
            } else if ("com.huawei.android.push.intent.CHANNEL_CLOSED".equals(action)) {
                e.b("PushLogAC2705", "receive the channal closed action.");
                stringExtra = "";
                action = "";
                Serializable serializableExtra = intent.getSerializableExtra("push_exception");
                if (serializableExtra != null) {
                    action = serializableExtra.toString();
                }
                context.sendBroadcast(new Intent("com.huawei.android.push.intent.SOCKET_INFO").putExtra("socket_event_type", 0).putExtra("socket_next_connect_time", stringExtra).putExtra("socket_exception", action).setPackage(context.getPackageName()));
            } else if ("com.huawei.android.push.intent.CONNECTING".equals(action)) {
                context.sendBroadcast(new Intent("com.huawei.android.push.intent.SOCKET_INFO").putExtra("socket_event_type", 2).setPackage(context.getPackageName()));
            } else if ("com.huawei.android.push.intent.CONNECTED".equals(action)) {
                context.sendBroadcast(new Intent("com.huawei.android.push.intent.SOCKET_INFO").putExtra("socket_event_type", 1).setPackage(context.getPackageName()));
            } else if ("com.huawei.intent.action.PUSH_OFF".equals(action) || "com.huawei.android.push.intent.inner.STOP_SERVICE".equals(action)) {
                stringExtra = intent.getStringExtra("Remote_Package_Name");
                if (stringExtra == null || !stringExtra.equals(context.getPackageName())) {
                    e.a("PushLogAC2705", "need stop PkgName:" + stringExtra + " is not me, need not stop!");
                    return;
                }
                if (c.a(context, "cloudpush_isSupportCollectSocketInfo", false)) {
                    context.sendBroadcast(new Intent("com.huawei.android.push.intent.SOCKET_INFO").putExtra("socket_add_info", "receive push off action.").setPackage(context.getPackageName()));
                }
                com.huawei.android.pushagent.b.a.a.a(context).b();
                if ("com.huawei.intent.action.PUSH_OFF".equals(action)) {
                    PushService.b();
                }
            } else if ("android.intent.action.BATTERY_CHANGED".equals(action)) {
                int intExtra = intent.getIntExtra("status", 1);
                com.huawei.android.pushagent.b.a.a.b(context).a(intExtra);
                if (2 == intExtra || 5 == intExtra) {
                    e.a("PushLogAC2705", "current battery is charging!");
                } else {
                    e.a("PushLogAC2705", "current battery no charging :" + intExtra);
                }
            }
        } catch (Throwable e) {
            e.a("PushLogAC2705", e.toString(), e);
        }
    }
}
