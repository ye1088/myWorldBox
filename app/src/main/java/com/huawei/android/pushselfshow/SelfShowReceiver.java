package com.huawei.android.pushselfshow;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushselfshow.b.a;
import com.huawei.android.pushselfshow.c.b;
import com.huawei.android.pushselfshow.c.d;
import com.huawei.android.pushselfshow.permission.RequestPermissionsActivity;
import java.io.File;
import org.json.JSONArray;

public class SelfShowReceiver {
    public void a(Context context, Intent intent, a aVar) {
        e.a("PushSelfShowLog", "receive a selfshow message ,the type is" + aVar.o);
        if (com.huawei.android.pushselfshow.a.a.a(aVar.o)) {
            long b = com.huawei.android.pushselfshow.utils.a.b(aVar.k);
            if (b == 0) {
                new d(context, aVar).start();
                return;
            }
            e.a("PushSelfShowLog", "waiting ……");
            intent.setPackage(context.getPackageName());
            com.huawei.android.pushselfshow.utils.a.a(context, intent, b);
            return;
        }
        com.huawei.android.pushselfshow.utils.a.a(context, "3", aVar);
    }

    public void a(Context context, Intent intent, String str, a aVar, int i) {
        e.a("PushSelfShowLog", "receive a selfshow userhandle message");
        if ("-1".equals(str)) {
            b.a(context, i);
        } else {
            b.a(context, intent);
        }
        if ("1".equals(str)) {
            new com.huawei.android.pushselfshow.a.a(context, aVar).a();
            if (aVar.n != null) {
                try {
                    JSONArray jSONArray = new JSONArray(aVar.n);
                    Intent intent2 = new Intent("com.huawei.android.push.intent.CLICK");
                    intent2.putExtra("click", jSONArray.toString()).setPackage(context.getPackageName()).setFlags(32);
                    context.sendBroadcast(intent2);
                } catch (Exception e) {
                    e.d("PushSelfShowLog", "message.extras is not a json format,err info " + e.toString());
                }
            }
        }
        com.huawei.android.pushselfshow.utils.a.a(context, str, aVar);
    }

    public void onReceive(Context context, Intent intent) {
        int i = 0;
        if (context == null || intent == null) {
            try {
                e.a("PushSelfShowLog", "enter SelfShowReceiver receiver, context or intent is null");
                return;
            } catch (Throwable e) {
                e.a("PushSelfShowLog", e.toString(), e);
                return;
            }
        }
        e.a(context);
        String action = intent.getAction();
        if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
            Uri data = intent.getData();
            if (data != null) {
                Object schemeSpecificPart = data.getSchemeSpecificPart();
                e.e("PushSelfShowLog", "receive package add ,the pkgName is " + schemeSpecificPart);
                if (!TextUtils.isEmpty(schemeSpecificPart)) {
                    new a(context, schemeSpecificPart).start();
                }
            }
        } else if (!"com.huawei.intent.action.PUSH".equals(action)) {
        } else {
            if (RequestPermissionsActivity.a(context)) {
                e.b("PushSelfShowLog", "needStartPermissionActivity");
                RequestPermissionsActivity.a(context, intent);
                return;
            }
            String str = null;
            if (intent.hasExtra("selfshow_info")) {
                byte[] byteArrayExtra = intent.getByteArrayExtra("selfshow_info");
                if (intent.hasExtra("selfshow_token")) {
                    byte[] byteArrayExtra2 = intent.getByteArrayExtra("selfshow_token");
                    if (intent.hasExtra("selfshow_event_id")) {
                        str = intent.getStringExtra("selfshow_event_id");
                    }
                    if (intent.hasExtra("selfshow_notify_id")) {
                        i = intent.getIntExtra("selfshow_notify_id", 0);
                    }
                    a aVar = new a(byteArrayExtra, byteArrayExtra2);
                    if (aVar.b()) {
                        e.a("PushSelfShowLog", " onReceive the msg id = " + aVar.l + ",and cmd is" + aVar.o + ",and the eventId is " + str);
                        if (str == null) {
                            a(context, intent, aVar);
                        } else {
                            a(context, intent, str, aVar, i);
                        }
                        com.huawei.android.pushselfshow.utils.a.b(new File(com.huawei.android.pushselfshow.utils.b.b.a(context)));
                        return;
                    }
                    e.a("PushSelfShowLog", "parseMessage failed");
                }
            }
        }
    }
}
