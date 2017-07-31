package com.huawei.android.pushagent.b.f;

import android.content.Context;
import android.content.Intent;
import com.baidu.android.pushservice.PushConstants;
import com.huawei.android.pushagent.c.a.e;

public class a extends com.huawei.android.pushagent.b.a {
    private static String a = "PushLogAC2705";
    private static boolean b = false;

    public a(Context context) {
    }

    public static void a(Context context, boolean z, String str) {
        Intent intent = new Intent();
        e.a(a, "sendStateBroadcast the current push state is: " + z);
        intent.setAction("com.huawei.intent.action.PUSH_STATE").putExtra("push_state", z).setFlags(32).setPackage(str);
        context.sendBroadcast(intent);
    }

    private static synchronized void a(boolean z) {
        synchronized (a.class) {
            b = z;
        }
    }

    public void a(Context context, Intent intent) {
        e.a(a, "enter ChannelRecorder:onReceive(intent:" + intent + " context:" + context);
        String action = intent.getAction();
        boolean a = com.huawei.android.pushagent.b.a.a.e().a();
        e.a(a, "PushState get action :" + action);
        if ("com.huawei.android.push.intent.GET_PUSH_STATE".equals(action)) {
            action = intent.getStringExtra(PushConstants.PACKAGE_NAME);
            e.a(a, "responseClinetGetPushState: get the client packageName: " + action);
            try {
                e.a(a, "current program pkgName" + context.getPackageName());
                e.a(a, "the current push curIsConnect:" + a);
                a(context, a, action);
            } catch (Exception e) {
                e.a(a, "e:" + e.toString());
            }
        }
        if (b != a) {
            a(a);
        }
    }
}
