package com.huawei.android.pushagent.b.b;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.huawei.android.pushagent.PushService;
import com.huawei.android.pushagent.a.e;
import com.huawei.android.pushagent.b.e.b;
import com.huawei.android.pushagent.plugin.tools.BLocation;
import java.net.InetSocketAddress;
import java.util.Date;

public class a extends e {
    private static a g = null;
    public boolean a = false;
    private Thread e = null;
    private long f = 300000;

    public a(Context context) {
        super(context);
        af();
    }

    public static synchronized a a(Context context) {
        a aVar;
        synchronized (a.class) {
            if (g == null) {
                g = new a(context);
            }
            aVar = g;
        }
        return aVar;
    }

    private synchronized boolean ah() {
        boolean z;
        if (ai()) {
            com.huawei.android.pushagent.c.a.e.a(BLocation.TAG, " trsQuery thread already running, just wait!!");
            z = false;
        } else {
            this.e = new b(this, "PushTRSQuery");
            this.e.start();
            c.a(this.d, new com.huawei.android.pushagent.a.a("lastQueryTRSTime", Long.class, Long.valueOf(System.currentTimeMillis())));
            c.a(this.d, new com.huawei.android.pushagent.a.a("queryTrsTimes", Long.class, Long.valueOf(c.a(this.d, "queryTrsTimes", 0) + 1)));
            z = true;
        }
        return z;
    }

    private synchronized boolean ai() {
        boolean z;
        z = this.e != null && this.e.isAlive();
        return z;
    }

    public static void b(Context context) {
        if (g != null) {
            g.a("pushSrvValidTime", (Object) Integer.valueOf(0));
            g.a = true;
        }
    }

    private boolean b(e eVar) {
        if (eVar == null || !eVar.V()) {
            com.huawei.android.pushagent.c.a.e.d(BLocation.TAG, "in PushSrvInfo:trsRetInfo, trsRetInfo is null or invalid:" + eVar);
            return false;
        }
        com.huawei.android.pushagent.c.a.e.b(BLocation.TAG, "queryTrs success!");
        if (!a(eVar)) {
            com.huawei.android.pushagent.c.a.e.a(BLocation.TAG, "heart beat range change.");
            PushService.a(new Intent("com.huawei.android.push.intent.HEARTBEAT_RANGE_CHANGE"));
        }
        if (eVar.c.containsKey("USE_SSL")) {
            c.a(null, new com.huawei.android.pushagent.a.a("USE_SSL", Integer.class, Integer.valueOf(((Integer) eVar.c.get("USE_SSL")).intValue())));
        }
        if (!a(eVar.b())) {
            com.huawei.android.pushagent.c.a.e.b(BLocation.TAG, "belongId changed, need to reRegisterDeviceToken");
            b.c(this.d);
        }
        this.c.putAll(eVar.c);
        a("pushSrvValidTime", (Object) Long.valueOf((f() * 1000) + System.currentTimeMillis()));
        this.f = q() * 1000;
        c.a(this.d, new com.huawei.android.pushagent.a.a("queryTrsTimes", Integer.class, Integer.valueOf(0)));
        com.huawei.android.pushagent.c.a.e.a(BLocation.TAG, "write the lastQueryTRSsucc_time to the pushConfig.xml file ");
        c.a(this.d, new com.huawei.android.pushagent.a.a("lastQueryTRSsucc_time", Long.class, Long.valueOf(System.currentTimeMillis())));
        this.a = false;
        this.c.remove("PushID");
        ag();
        com.huawei.android.pushagent.b.d.b.a(this.d).a(this.d, com.huawei.android.pushagent.b.d.b.b.c, new Bundle());
        PushService.a(new Intent("com.huawei.android.push.intent.TRS_QUERY_SUCCESS").putExtra("trs_result", eVar.toString()));
        return true;
    }

    public InetSocketAddress a(boolean z) {
        boolean c = c(z);
        if (!V() || c) {
            com.huawei.android.pushagent.c.a.e.a(BLocation.TAG, "in getPushSrvAddr, have no invalid addr");
            return null;
        }
        com.huawei.android.pushagent.c.a.e.b(BLocation.TAG, "return valid PushSrvAddr");
        return new InetSocketAddress(c(), d());
    }

    public InetSocketAddress b(boolean z) {
        boolean c = c(z);
        if (!X() || c) {
            com.huawei.android.pushagent.c.a.e.d(BLocation.TAG, "in getPollingAddr, have no invalid addr");
            return null;
        }
        com.huawei.android.pushagent.c.a.e.b(BLocation.TAG, "return valid PollingSrvAddr");
        return new InetSocketAddress(C(), D());
    }

    public boolean c(boolean z) {
        if (ai()) {
            com.huawei.android.pushagent.c.a.e.a(BLocation.TAG, "trsQuery thread is running");
            return true;
        }
        long a = c.a(this.d, "lastQueryTRSTime", 0);
        long a2 = c.a(this.d, "lastQueryTRSsucc_time", 0);
        com.huawei.android.pushagent.c.a.e.a(BLocation.TAG, "isvalid:" + V() + " srvValidBefore:" + (c("pushSrvValidTime", Long.MAX_VALUE) - System.currentTimeMillis()) + " pushSrvNeedQueryTRS:" + this.a);
        if (V() && !this.a && c("pushSrvValidTime", Long.MAX_VALUE) >= System.currentTimeMillis() && System.currentTimeMillis() > a2) {
            com.huawei.android.pushagent.c.a.e.a(BLocation.TAG, " need not query TRS");
            return false;
        } else if (-1 == com.huawei.android.pushagent.c.a.b.a(this.d)) {
            com.huawei.android.pushagent.c.a.e.a(BLocation.TAG, "in queryTRSInfo no network");
            return false;
        } else {
            if (z) {
                com.huawei.android.pushagent.c.a.e.a(BLocation.TAG, "Force to Connect TRS");
            } else if (System.currentTimeMillis() - a2 < e() * 1000 && System.currentTimeMillis() > a2) {
                com.huawei.android.pushagent.c.a.e.a(BLocation.TAG, "can not contect TRS Service when  the connect more than " + e() + " sec last contected success time," + "lastQueryTRSsucc_time = " + new Date(a2));
                return false;
            } else if (System.currentTimeMillis() - a < this.f && System.currentTimeMillis() > a) {
                com.huawei.android.pushagent.c.a.e.a(BLocation.TAG, "can't connect TRS Service when the connectting time more later " + (this.f / 1000) + "sec than  last contectting time,lastQueryTRSTime =" + new Date(a));
                return false;
            } else if (c.a(this.d, "queryTrsTimes", 0) > t()) {
                this.f = r() * 1000;
            }
            return (c.a(this.d, "cloudpush_isNoDelayConnect", false) || com.huawei.android.pushagent.b.d.a.a(this.d)) ? ah() : false;
        }
    }
}
