package com.huawei.android.pushagent.b.a;

import android.content.Context;
import android.content.Intent;
import com.huawei.android.pushagent.PushService;
import com.huawei.android.pushagent.b.a.a.b;
import com.huawei.android.pushagent.b.b.c;
import com.huawei.android.pushagent.c.a.e;
import java.util.LinkedList;
import java.util.List;

public class a {
    private static a d = null;
    a a = a.b;
    com.huawei.android.pushagent.b.a.a.a[] b = new com.huawei.android.pushagent.b.a.a.a[a.values().length];
    private Context c;

    private a(Context context) {
        this.c = context;
    }

    public static synchronized a a(Context context) {
        a aVar;
        synchronized (a.class) {
            if (d != null) {
                aVar = d;
            } else if (context == null) {
                e.d("PushLogAC2705", "when init ChannelMgr g_channelMgr and context all null!!");
                aVar = null;
            } else {
                d = new a(context);
                d.g();
                aVar = d;
            }
        }
        return aVar;
    }

    public static b b(Context context) {
        return a(context).d().e;
    }

    public static a c() {
        return a(null).a;
    }

    private static void c(Context context) {
        e.a("PushLogAC2705", "enter cancelDelayAlarm");
        com.huawei.android.pushagent.c.a.a.a(context, "com.huawei.action.CONNECT_PUSHSRV");
        com.huawei.android.pushagent.c.a.a.a(context, "com.huawei.android.push.intent.HEARTBEAT_RSP_TIMEOUT");
        com.huawei.android.pushagent.c.a.a.a(context, new Intent("com.huawei.intent.action.PUSH").putExtra("EXTRA_INTENT_TYPE", "com.huawei.android.push.intent.HEARTBEAT_REQ").putExtra("heartbeat_interval", 2592000000L).setPackage(context.getPackageName()));
    }

    public static com.huawei.android.pushagent.b.a.a.a e() {
        return a(null).b[a.a.ordinal()];
    }

    public static com.huawei.android.pushagent.b.a.a.a f() {
        return a(null).b[a.b.ordinal()];
    }

    private boolean g() {
        e.a("PushLogAC2705", "begin to init ChannelMgr");
        int a = c.a(this.c, "curConnectEntity", a.b.ordinal());
        e.a("PushLogAC2705", "in cfg curConEntity:" + a);
        if (a >= 0 && a < a.values().length) {
            this.a = a.values()[a];
        }
        if (a.b == this.a && !com.huawei.android.pushagent.b.b.a.a(this.c).X() && com.huawei.android.pushagent.b.b.a.a(this.c).W()) {
            this.a = a.a;
        }
        this.b[a.a.ordinal()] = new com.huawei.android.pushagent.b.a.a.b.a(null, this.c);
        this.b[a.b.ordinal()] = new com.huawei.android.pushagent.b.a.a.a.a(null, this.c);
        return true;
    }

    public List a() {
        List linkedList = new LinkedList();
        for (com.huawei.android.pushagent.b.a.a.a aVar : this.b) {
            if (aVar.e.c() != null) {
                linkedList.add(aVar.e.c());
            }
        }
        return linkedList;
    }

    public void a(long j) {
        e.a("PushLogAC2705", "next connect pushsvr will be after " + j);
        Intent intent = new Intent("com.huawei.action.CONNECT_PUSHSRV");
        intent.setPackage(this.c.getPackageName());
        com.huawei.android.pushagent.c.a.a.c(this.c, intent, j);
    }

    public void a(Intent intent) {
        String action = intent.getAction();
        String stringExtra = intent.getStringExtra("EXTRA_INTENT_TYPE");
        if ("com.huawei.android.push.intent.HEARTBEAT_RSP_TIMEOUT".equals(action)) {
            e.b("PushLogAC2705", "time out for wait heartbeat so reconnect");
            b(this.c).c(true);
            d().d();
            if (-1 != com.huawei.android.pushagent.c.a.b.a(this.c) && c() == a.a) {
                try {
                    d().a(false);
                } catch (Throwable e) {
                    e.c("PushLogAC2705", e.toString(), e);
                }
            }
        } else if ("com.huawei.intent.action.PUSH".equals(action) && "com.huawei.android.push.intent.HEARTBEAT_REQ".equals(stringExtra)) {
            if (-1 != com.huawei.android.pushagent.c.a.b.a(this.c)) {
                com.huawei.android.pushagent.b.a.a.a d = d();
                if (d.a()) {
                    d.e.a(true);
                    d.e.g();
                    return;
                }
                PushService.a(new Intent("com.huawei.action.CONNECT_PUSHSRV").setPackage(this.c.getPackageName()));
                return;
            }
            e.d("PushLogAC2705", "when send heart beat, not net work");
            b(this.c).b();
        } else if (!"com.huawei.android.push.intent.REFRESH_PUSH_CHANNEL".equals(action) && !"android.intent.action.TIME_SET".equals(action) && !"android.intent.action.TIMEZONE_CHANGED".equals(action)) {
        } else {
            if (d().a()) {
                b(this.c).a(false);
                b(this.c).g();
            } else if (-1 != com.huawei.android.pushagent.c.a.b.a(this.c)) {
                e.a("PushLogAC2705", "received " + action + ", but not Connect, go to connect!");
                PushService.a(new Intent("com.huawei.action.CONNECT_PUSHSRV"));
            } else {
                e.b("PushLogAC2705", "no net work, when recevice :" + action + ", do nothing");
            }
        }
    }

    public void a(a aVar) {
        this.a = aVar;
        if (a.b == aVar && !com.huawei.android.pushagent.b.b.a.a(this.c).X() && com.huawei.android.pushagent.b.b.a.a(this.c).W()) {
            aVar = a.a;
        }
        c.a(this.c, new com.huawei.android.pushagent.a.a("curConnectEntity", Integer.class, Integer.valueOf(aVar.ordinal())));
    }

    public void a(a aVar, boolean z) {
        e.d("PushLogAC2705", "enter ChannelMgr:connect(entity" + aVar + ", forceCon" + z + ")");
        if (aVar != null) {
            try {
                this.b[aVar.ordinal()].a(z);
                return;
            } catch (Throwable e) {
                e.c("PushLogAC2705", e.toString(), e);
                return;
            }
        }
        e.d("PushLogAC2705", "entityMode is invalid!!");
    }

    public void b() {
        c(this.c);
        for (com.huawei.android.pushagent.b.a.a.a d : this.b) {
            d.d();
        }
    }

    public com.huawei.android.pushagent.b.a.a.a d() {
        e.a("PushLogAC2705", "enter getCurConnetEntity(curConnectType:" + this.a + ", ordinal:" + this.a.ordinal() + " curConnect:" + this.b[this.a.ordinal()].getClass().getSimpleName() + ")");
        if (a.b == this.a && !com.huawei.android.pushagent.b.b.a.a(this.c).X() && com.huawei.android.pushagent.b.b.a.a(this.c).W()) {
            e.a("PushLogAC2705", "polling srv is not ready, push is ok, so change it to Push");
            this.a = a.a;
        }
        return this.b[this.a.ordinal()];
    }
}
