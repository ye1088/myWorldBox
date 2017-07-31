package com.huawei.android.pushagent.plugin.tools;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushagent.plugin.PushPlugins;
import com.huawei.android.pushagent.plugin.a.f;
import com.huawei.android.pushagent.plugin.c;
import com.huawei.android.pushagent.plugin.c.b;
import java.util.List;

public class PushPluginsBroadcastMgr {
    private static String a = BLocation.TAG;

    private static class a implements Runnable {
        private b a;
        private Context b;

        private a(Context context, b bVar) {
            this.a = bVar;
            this.b = context;
        }

        public void run() {
            try {
                String a = this.a.a(this.b);
                if (TextUtils.isEmpty(a)) {
                    e.b(PushPluginsBroadcastMgr.a, "plus data is null, cannot report:" + this.a.b());
                } else {
                    new c(this.b).a(a, this.a.a(), this.a.c());
                }
            } catch (Throwable e) {
                e.c(PushPluginsBroadcastMgr.a, "report plugin data error:" + e.getMessage(), e);
            }
        }
    }

    public static void handleEvent(Context context, Intent intent) {
        try {
            String action = intent.getAction();
            b a = b.a(context);
            List<b> a2 = a.a();
            if ("com.huawei.android.push.PLUGIN".equals(action) && intent.hasExtra("plusAlarm")) {
                e.b(a, "alarm reached , prepare to report message");
                if (a2.isEmpty()) {
                    a.c(context);
                    return;
                }
                for (b bVar : a2) {
                    if ((bVar.c(context) + bVar.b(context)) - 5000 < System.currentTimeMillis()) {
                        if (f.LBS.b() == bVar.a()) {
                            BLocation.getInstance(context).requestLocation();
                        }
                        bVar.b(context, System.currentTimeMillis());
                        new Thread(new a(context, bVar)).start();
                    } else {
                        e.b(a, "plus cycle has not reached :" + bVar.b());
                    }
                }
            } else if ("com.huawei.android.push.PLUGIN".equals(action) && intent.hasExtra("plusReport")) {
                Bundle bundleExtra = intent.getBundleExtra("plusReport");
                if (bundleExtra == null) {
                    e.b(a, "plusReport not found in intent");
                    return;
                }
                int i = bundleExtra.getInt("plusType");
                int i2 = bundleExtra.getInt("operType");
                e.a(a, "receive plugin broadcast, plusType:" + i + ",operType:" + i2);
                if (i2 == 0) {
                    new PushPlugins(context).closeCyclePlus(i);
                } else if (1 == i2) {
                    long j = bundleExtra.getLong("cycle");
                    new PushPlugins(context).reportPlus(i, bundleExtra.getString("content"), j);
                }
            } else if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
                e.a(a, "network change");
                if (a2.isEmpty()) {
                    e.b(a, "no cycle task need to run");
                    return;
                }
                Object obj = null;
                for (b bVar2 : a2) {
                    Object obj2;
                    if (bVar2.c(context) + bVar2.b(context) < System.currentTimeMillis()) {
                        bVar2.b(context, System.currentTimeMillis());
                        obj2 = 1;
                    } else {
                        e.a(a, "plus cycle has not reached :" + bVar2.b());
                        obj2 = obj;
                    }
                    obj = obj2;
                }
                if (obj != null) {
                    com.huawei.android.pushagent.c.a.a.a(context, new Intent("com.huawei.android.push.PLUGIN").putExtra("plusAlarm", "alarm").setPackage(context.getPackageName()), b.a(context).b(context));
                }
            }
        } catch (Throwable e) {
            e.c(a, e.getMessage(), e);
        }
    }
}
