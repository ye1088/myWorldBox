package com.huawei.android.pushagent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushagent.c.a.h;
import com.huawei.android.pushagent.c.c.b;

public class PushEventReceiver extends BroadcastReceiver {
    private static void a(Context context, Intent intent) {
        try {
            if (!"com.huawei.android.pushagent".equals(context.getPackageName())) {
                e.a("PushLogAC2705", "run invokePluginReport");
                Class cls = Class.forName("com.huawei.android.pushagent.plugin.tools.PushPluginsBroadcastMgr");
                cls.getMethod("handleEvent", new Class[]{Context.class, Intent.class}).invoke(cls, new Object[]{context, intent});
            }
        } catch (ClassNotFoundException e) {
            e.a("PushLogAC2705", "ClassNotFoundException:" + e.toString());
        } catch (Throwable e2) {
            e.c("PushLogAC2705", e2.toString(), e2);
        } catch (Throwable e22) {
            e.c("PushLogAC2705", e22.toString(), e22);
        } catch (Throwable e222) {
            e.c("PushLogAC2705", e222.toString(), e222);
        } catch (Throwable e2222) {
            e.c("PushLogAC2705", e2222.toString(), e2222);
        }
    }

    private static void b(Context context, Intent intent) {
        boolean a = new h(context, "push_switch").a("notify_msg_enable");
        e.a("PushLogAC2705", "closePush_Notify:" + a);
        if (!a) {
            try {
                e.b("PushLogAC2705", "run push selfshow");
                Class cls = Class.forName("com.huawei.android.pushselfshow.SelfShowReceiver");
                Object newInstance = cls.getConstructor(new Class[0]).newInstance(new Object[0]);
                cls.getDeclaredMethod("onReceive", new Class[]{Context.class, Intent.class}).invoke(newInstance, new Object[]{context, intent});
            } catch (ClassNotFoundException e) {
                e.b("PushLogAC2705", "ClassNotFoundException:" + e.toString());
            } catch (Throwable e2) {
                e.c("PushLogAC2705", e2.toString(), e2);
            } catch (Throwable e22) {
                e.c("PushLogAC2705", e22.toString(), e22);
            } catch (Throwable e222) {
                e.c("PushLogAC2705", e222.toString(), e222);
            } catch (Throwable e2222) {
                e.c("PushLogAC2705", e2222.toString(), e2222);
            } catch (Throwable e22222) {
                e.c("PushLogAC2705", e22222.toString(), e22222);
            }
        }
    }

    private static void c(Context context, Intent intent) {
        try {
            e.a("PushLogAC2705", "run PushProxy.handleEvent ");
            b.a(context, intent);
        } catch (Throwable e) {
            e.c("PushLogAC2705", e.toString(), e);
        }
    }

    public void onReceive(Context context, Intent intent) {
        if (context == null || intent == null) {
            e.a("PushLogAC2705", "context== null or intent == null");
            return;
        }
        e.a(context);
        String action = intent.getAction();
        e.b("PushLogAC2705", "action is " + action);
        if (("com.huawei.intent.action.PUSH".equals(action) && intent.hasExtra("selfshow_info")) || "android.intent.action.PACKAGE_ADDED".equals(action)) {
            b(context, intent);
            if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
                c(context, intent);
            }
        } else if ("com.huawei.android.push.PLUGIN".equals(action)) {
            a(context, intent);
        } else if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
            c(context, intent);
            a(context, intent);
        } else {
            c(context, intent);
        }
    }
}
