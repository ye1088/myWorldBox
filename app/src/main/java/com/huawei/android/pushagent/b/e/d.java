package com.huawei.android.pushagent.b.e;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import com.baidu.android.pushservice.PushConstants;
import com.huawei.android.pushagent.PushService;
import com.huawei.android.pushagent.a.b.g;
import com.huawei.android.pushagent.a.b.i;
import com.huawei.android.pushagent.a.b.l;
import com.huawei.android.pushagent.a.b.m;
import com.huawei.android.pushagent.a.b.n;
import com.huawei.android.pushagent.a.b.o;
import com.huawei.android.pushagent.a.b.p;
import com.huawei.android.pushagent.a.b.q;
import com.huawei.android.pushagent.b.a;
import com.huawei.android.pushagent.b.b.c;
import com.huawei.android.pushagent.c.a.a.f;
import com.huawei.android.pushagent.c.a.b;
import com.huawei.android.pushagent.c.a.h;
import com.huawei.android.pushagent.c.c.e;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

public class d extends a {
    private static List a = new ArrayList();
    private static final Object b = new Object();
    private String[] c;
    private List d = new ArrayList();
    private List e = new ArrayList();

    public d(Context context) {
        if (!e.c()) {
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "not support ctrlsocket v2 ");
        } else if (1 == e.b()) {
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "push is in socket ctrl model, only white packages app can use push");
            this.c = e.a();
        } else {
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "all apps can use push");
            this.c = new String[0];
        }
    }

    private void a(Context context) {
        if (-1 == b.a(context)) {
            com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "sendAllMessagetoServer have no net work");
        } else if (com.huawei.android.pushagent.b.a.a.e().a()) {
            com.huawei.android.pushagent.c.a.e.b("PushLogAC2705", "sendAllMessagetoServer get the client");
            ArrayList b = b.b(context);
            for (Entry key : new h(context, "pclient_unRegist_info_v2").b().entrySet()) {
                b.add(new p(f.b(context, (String) key.getKey())));
            }
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "send all client registerToken message count to Sever is " + b.size());
            if (b.size() > 0) {
                Iterator it = b.iterator();
                while (it.hasNext()) {
                    try {
                        com.huawei.android.pushagent.b.a.a.e().a((com.huawei.android.pushagent.a.b.a.b) it.next());
                    } catch (Throwable e) {
                        com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "call ChannelMgr.getPushChannel().send cause:" + e.toString(), e);
                    }
                }
                return;
            }
            com.huawei.android.pushagent.c.a.e.b("PushLogAC2705", "no more client need register and unregister");
        } else {
            com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "sendAllMessagetoServer have no channel or no connection");
        }
    }

    private void a(Context context, l lVar) {
        byte b = (byte) 0;
        com.huawei.android.pushagent.c.a.e.b("PushLogAC2705", "enter rspPushMessage");
        byte[] e = lVar.e();
        String str = "";
        if (e == null) {
            com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "token is null, error!");
            return;
        }
        String str2;
        com.huawei.android.pushagent.a.b bVar;
        try {
            str = new String(e, "UTF-8");
        } catch (Throwable e2) {
            com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", e2.toString(), e2);
        }
        String str3 = "";
        byte[] d = lVar.d();
        byte[] h = lVar.h();
        if (h == null || h.length <= 0) {
            if (b.a(context).a != null) {
                str2 = (String) b.a(context).a.get(str);
            }
            str2 = str3;
        } else {
            if (h.length == lVar.g()) {
                try {
                    str2 = new String(h, "UTF-8");
                } catch (UnsupportedEncodingException e3) {
                    com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "UnsupportedEncodingException occur");
                    str2 = str3;
                }
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "rspPushMessage from srv response pkgname is :" + str2);
            }
            str2 = str3;
        }
        String a = com.huawei.android.pushagent.c.a.a(d);
        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "rspPushMessage token =" + com.huawei.android.pushagent.c.a.a.e.a(str) + " pkgname=" + str2 + " msgId=" + a);
        m mVar = new m(d, (byte) 0);
        Object obj;
        if (com.huawei.android.pushagent.c.a.i(context).equals(str)) {
            if (b(a)) {
                com.huawei.android.pushagent.c.a.e.b("PushLogAC2705", "msgId duplicate, do not show it");
                bVar = mVar;
            } else {
                a(a);
                if (this.c == null || this.c.length <= 0) {
                    b(context, e, lVar.f());
                    obj = mVar;
                } else {
                    if (!TextUtils.isEmpty(str2)) {
                        for (Object equals : this.c) {
                            if (str2.equals(equals)) {
                                b = (byte) 1;
                                break;
                            }
                        }
                    }
                    if (b != (byte) 0) {
                        b(context, e, lVar.f());
                    } else {
                        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "push message's owner is not white app, send it when screen on");
                        this.e.add(new com.huawei.android.pushagent.a.b.a(str2, e, lVar.f()));
                    }
                    obj = mVar;
                }
            }
        } else if (str2 == null || !com.huawei.android.pushagent.c.c.d.a(context, str2)) {
            com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "pkgName" + str2 + " is null or not exist in local");
            obj = new m(d, (byte) 2);
        } else if (b(a)) {
            com.huawei.android.pushagent.c.a.e.b("PushLogAC2705", "msgId duplicate, do not sent it to other apps");
            obj = mVar;
        } else {
            a(a);
            if (this.c == null || this.c.length <= 0) {
                a(context, str2, e, lVar.f());
                obj = mVar;
            } else {
                byte b2;
                for (Object equals2 : this.c) {
                    if (str2.equals(equals2)) {
                        b2 = (byte) 1;
                        break;
                    }
                }
                b2 = (byte) 0;
                if (b2 != (byte) 0) {
                    a(context, str2, e, lVar.f());
                } else {
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "push message's owner is not white app, send it when screen on");
                    this.d.add(new com.huawei.android.pushagent.a.b.a(str2, e, lVar.f()));
                }
                obj = mVar;
            }
        }
        try {
            com.huawei.android.pushagent.b.a.a.e().a(bVar);
        } catch (Throwable e22) {
            com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "call ChannelMgr.getPushChannel().send cause:" + e22.toString(), e22);
        }
        com.huawei.android.pushagent.c.a.e.b("PushLogAC2705", "rspPushMessage the response msg is :" + bVar);
    }

    private void a(Context context, o oVar) {
        if (oVar == null) {
            com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "responseRegisterToken have a_isRightVersion wrong parm");
            return;
        }
        h hVar = new h(context, "pclient_request_info");
        if (!TextUtils.isEmpty(oVar.e())) {
            hVar.f(oVar.e());
        }
        if (oVar.f() == (byte) 1) {
            com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "responseRegisterToken FAILED:" + oVar.f());
            return;
        }
        String d = oVar.d();
        String e = oVar.e();
        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "pushSrv response register token to " + e);
        if (TextUtils.isEmpty(e) || TextUtils.isEmpty(d)) {
            com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "pushSrv response registerToken a_isRightVersion invalid message ");
            return;
        }
        if (e.c()) {
            e.a(e);
        }
        b.a(context, d, e);
        if (hVar.b().size() == 0) {
            com.huawei.android.pushagent.c.a.a.a(context, new Intent("com.huawei.action.CONNECT_PUSHSRV_POLLINGSRV").setPackage(context.getPackageName()));
            PushService.a(new Intent("com.huawei.action.CONNECT_PUSHSRV_POLLINGSRV"));
        }
        a(context, e, d);
    }

    private void a(Context context, q qVar) {
        com.huawei.android.pushagent.c.a.e.b("PushLogAC2705", "unregister token from pushsrv success");
        if (qVar == null) {
            com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "responseUnregisterToken have an wrong param");
            return;
        }
        String d = qVar.d();
        b.c(context, d);
        com.huawei.android.pushagent.c.a.f.b(context, d);
        PushService.a(new Intent("com.huawei.action.CONNECT_PUSHSRV_POLLINGSRV"));
    }

    private void a(Context context, String str) {
        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "begin to get token from pushSrv, packagename = " + str);
        String a = com.huawei.android.pushagent.c.a.a(context);
        new h(context, "pclient_request_info").a(str, "true");
        if (com.huawei.android.pushagent.b.a.a.e().a()) {
            try {
                com.huawei.android.pushagent.b.a.a.e().a(new n(a, com.huawei.android.pushagent.c.a.f(context, str)));
            } catch (Throwable e) {
                com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "call ChannelMgr.getPushChannel().send cause:" + e.toString(), e);
            }
            com.huawei.android.pushagent.c.a.a.c(context, new Intent("com.huawei.action.CONNECT_PUSHSRV_POLLINGSRV").setPackage(context.getPackageName()), com.huawei.android.pushagent.b.b.a.a(context).F() * 1000);
            return;
        }
        PushService.a(new Intent("com.huawei.action.CONNECT_PUSHSRV_PUSHSRV").setPackage(context.getPackageName()));
    }

    private synchronized void a(Context context, String str, Boolean bool) {
        Editor edit = context.getSharedPreferences("pushConfig", 4).edit();
        edit.putBoolean("cloudpush_ConnectStatus", bool.booleanValue()).commit();
        edit.putLong(str, System.currentTimeMillis()).commit();
    }

    public static void a(Context context, String str, String str2) {
        if (str != null && str2 != null) {
            try {
                Intent flags = new Intent("com.huawei.android.push.intent.REGISTRATION").setPackage(str).putExtra("device_token", str2.getBytes("UTF-8")).putExtra("belongId", com.huawei.android.pushagent.b.b.a.a(context).b()).setFlags(32);
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "send registerToken to:" + str + " token:" + com.huawei.android.pushagent.c.a.a.e.a(str2));
                context.sendBroadcast(flags);
            } catch (Throwable e) {
                com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", e.toString(), e);
            }
        }
    }

    private void a(Context context, String str, byte[] bArr, byte[] bArr2) {
        if (e.c()) {
            e.a(2, 180);
        } else {
            com.huawei.android.pushagent.c.a.a(2, 180);
        }
        Intent intent = new Intent("com.huawei.android.push.intent.RECEIVE");
        intent.setPackage(str).putExtra("msg_data", bArr2).putExtra("device_token", bArr).setFlags(32);
        context.sendBroadcast(intent);
        PushService.a(new Intent("com.huawei.android.push.intent.MSG_BROAD_TO_APP").putExtra("appName", str));
        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "broadcast pushDataRspMessage to " + str + " over");
    }

    private static void a(SharedPreferences sharedPreferences, NetworkInfo networkInfo) {
        Editor edit = sharedPreferences.edit();
        String string = sharedPreferences.getString("cloudpush_arrayOfNetEventTime", "");
        String str = "";
        if (!TextUtils.isEmpty(string) || string.equals("null")) {
            String[] split = string.split("\\,");
            int length = split.length - 1;
            StringBuffer stringBuffer = new StringBuffer();
            if (length == 16) {
                for (int i = 0; i < 15; i++) {
                    stringBuffer.append(split[i + 1] + MiPushClient.ACCEPT_TIME_SEPARATOR);
                }
                str = stringBuffer.toString();
            } else if (length < 16) {
                str = string + MiPushClient.ACCEPT_TIME_SEPARATOR;
            }
        }
        string = Integer.toString(networkInfo.getType());
        string = (networkInfo.isConnected() || networkInfo.isConnectedOrConnecting()) ? string + "-1-" + Long.toString(System.currentTimeMillis()) : string + "-0-" + Long.toString(System.currentTimeMillis());
        edit.putString("cloudpush_arrayOfNetEventTime", str + string).commit();
    }

    private void a(String str) {
        synchronized (b) {
            if (a.size() >= 10) {
                a.remove(0);
            }
            a.add(str);
        }
    }

    private void b(Context context, Intent intent) {
        com.huawei.android.pushagent.a.b.a.b bVar = (com.huawei.android.pushagent.a.b.a.b) intent.getSerializableExtra("push_msg");
        if (bVar == null) {
            com.huawei.android.pushagent.c.a.e.b("PushLogAC2705", "msg is null");
            return;
        }
        switch (bVar.a()) {
            case (byte) -96:
                com.huawei.android.pushagent.c.a.a(context, 10000);
                a(context, (l) bVar);
                return;
            case (byte) -91:
                a.a().a(context, (com.huawei.android.pushagent.a.b.b) bVar);
                return;
            case (byte) -90:
                try {
                    com.huawei.android.pushagent.b.a.a.e().a(new com.huawei.android.pushagent.a.b.b((byte) -89));
                } catch (Throwable e) {
                    com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "send serverToAgentMsgRsp error:" + e.getMessage(), e);
                }
                a.a().a(context, (com.huawei.android.pushagent.a.b.b) bVar);
                return;
            case (byte) -45:
                com.huawei.android.pushagent.a.b.d dVar = (com.huawei.android.pushagent.a.b.d) bVar;
                if (dVar.d() == (byte) 0) {
                    com.huawei.android.pushagent.c.a.e.b("PushLogAC2705", "PushCommandProcessor device register success");
                    c.a(context, new com.huawei.android.pushagent.a.a("cloudpush_arrayOfNetEventTime", String.class, ""));
                    com.huawei.android.pushagent.b.a.a.b(context).a(false);
                    com.huawei.android.pushagent.b.a.a.b(context).g();
                    a(context);
                    return;
                }
                com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "PushCommandProcessor device register fail:" + dVar.d());
                return;
            case (byte) -41:
                a(context, (q) bVar);
                return;
            case (byte) -37:
                return;
            case (byte) -35:
                com.huawei.android.pushagent.c.a.a(context, 10000);
                a(context, (o) bVar);
                return;
            case (byte) -33:
                i iVar = (i) bVar;
                if (iVar.d() == (byte) 0) {
                    com.huawei.android.pushagent.c.a.e.b("PushLogAC2705", "PushCommandProcessor device register success");
                    c.a(context, new com.huawei.android.pushagent.a.a("cloudpush_arrayOfNetEventTime", String.class, ""));
                    com.huawei.android.pushagent.b.a.a.b(context).a(false);
                    com.huawei.android.pushagent.b.a.a.b(context).g();
                    a(context);
                    return;
                }
                com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "PushCommandProcessor device register fail:" + iVar.d());
                return;
            default:
                return;
        }
    }

    private void b(Context context, String str) {
        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", str + " will be unregister.");
        String str2 = "";
        Object d = b.d(context, str);
        if (!TextUtils.isEmpty(d)) {
            try {
                com.huawei.android.pushagent.b.a.a.e().a(new p(d));
            } catch (Throwable e) {
                com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "call ChannelMgr.getPushChannel().send cause:" + e.toString(), e);
            }
        }
    }

    private void b(Context context, byte[] bArr, byte[] bArr2) {
        try {
            com.huawei.android.pushagent.c.a.e.b("PushLogAC2705", "enter deposeMessageBySelf");
            if (bArr2 == null) {
                com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "enter deposeMessageBySelf msg is null!");
                return;
            }
            String str = new String(bArr2, "UTF-8");
            if (TextUtils.isEmpty(str)) {
                com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "enter deposeMessageBySelf jsonStr is null!");
                return;
            }
            if (e.c()) {
                e.a(2, 180);
            } else {
                com.huawei.android.pushagent.c.a.a(2, 180);
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has(PushConstants.EXTRA_APP)) {
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "jsonStr has a_isRightVersion mapping for app");
                    try {
                        int i = jSONObject.getInt(PushConstants.EXTRA_APP);
                        if (1 == i) {
                            c.a(context, str);
                            return;
                        }
                        com.huawei.android.pushagent.c.a.e.b("PushLogAC2705", "the app value is not 1! it is " + i);
                        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "send selfShow message");
                        a(context, bArr, bArr2);
                    } catch (Throwable e) {
                        com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", e.toString(), e);
                    }
                } else {
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "jsonStr does not  have a_isRightVersion mapping for app");
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "send selfShow message");
                    a(context, bArr, bArr2);
                }
            } catch (JSONException e2) {
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", str + " depose failed, maybe old selfShow message");
            }
        } catch (Throwable e3) {
            com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", e3.toString(), e3);
        }
    }

    private boolean b(String str) {
        boolean contains;
        synchronized (b) {
            contains = a.contains(str);
        }
        return contains;
    }

    private com.huawei.android.pushagent.a.b.h c(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("pushConfig", 0);
        int parseInt = Integer.parseInt(com.huawei.android.pushagent.c.a.g(context));
        long j = sharedPreferences.getLong("cloudpush_off", 0);
        long j2 = sharedPreferences.getLong("cloudpush_on", 0);
        long currentTimeMillis = System.currentTimeMillis();
        int i = 0;
        try {
            int i2;
            String string = sharedPreferences.getString("cloudpush_arrayOfNetEventTime", "");
            if (!TextUtils.isEmpty(string) || string.equals("null")) {
                i = string.split("\\,").length;
            }
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "mDeviceTokenMgr.tokenMap.size:" + b.a(context).a.size());
            if (b.a(context).a.size() == 0) {
                i++;
            }
            if (i > 16) {
                i = 16;
            }
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "netEventAccount is: " + i);
            g[] gVarArr = new g[i];
            if (!TextUtils.isEmpty(string) || string.equals("null")) {
                String[] split = string.split("\\,");
                for (i2 = 0; i2 < split.length; i2++) {
                    String[] split2 = split[i2].split("\\-");
                    gVarArr[i2] = new g();
                    gVarArr[i2].a((byte) Integer.parseInt(split2[0]));
                    gVarArr[i2].b((byte) Integer.parseInt(split2[1]));
                    gVarArr[i2].a(Long.parseLong(split2[2]));
                }
            }
            if (b.a(context).a.size() == 0) {
                i2 = i - 1;
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "syncPos is: " + i2);
                if (gVarArr[i2] == null) {
                    gVarArr[i2] = new g();
                }
                gVarArr[i2].a((byte) -1);
                gVarArr[i2].b((byte) 0);
                gVarArr[i2].a(System.currentTimeMillis());
            }
            return new com.huawei.android.pushagent.a.b.h(str, (byte) b.a(context), parseInt, j, j2, currentTimeMillis, i, gVarArr);
        } catch (NumberFormatException e) {
            return new com.huawei.android.pushagent.a.b.h(str, (byte) b.a(context), parseInt, j, j2, currentTimeMillis, 0, null);
        } catch (Exception e2) {
            return new com.huawei.android.pushagent.a.b.h(str, (byte) b.a(context), parseInt, j, j2, currentTimeMillis, 0, null);
        }
    }

    private void c(Context context, Intent intent) {
        com.huawei.android.pushagent.c.a.a.a(context, new Intent("com.huawei.intent.action.PUSH_OFF").setPackage(context.getPackageName()).putExtra("Remote_Package_Name", context.getPackageName()));
        String stringExtra = intent.getStringExtra(PushConstants.PACKAGE_NAME);
        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "PushCommandProcessor: get the packageName: " + stringExtra);
        if (TextUtils.isEmpty(stringExtra)) {
            com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "PushCommandProcessor: get the wrong package name from the Client!");
        } else if (com.huawei.android.pushagent.c.c.d.a(context, stringExtra)) {
            h hVar = new h(context, "pclient_unRegist_info_v2");
            for (Entry entry : hVar.b().entrySet()) {
                if (stringExtra.equals((String) entry.getValue())) {
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", stringExtra + " need to register again");
                    hVar.f((String) entry.getKey());
                    break;
                }
            }
            if (b.a(context, stringExtra)) {
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "PushCommandProcessor: this package:" + stringExtra + " have already registered ");
                a(context, stringExtra, b.d(context, stringExtra));
                return;
            }
            a(context, stringExtra);
        } else {
            com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "rec register toke request , but the packageName:" + stringExtra + " was not install !!");
        }
    }

    private void d(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra(PushConstants.PACKAGE_NAME);
        if (TextUtils.isEmpty(stringExtra)) {
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "packagename is null, cannot deregister");
            return;
        }
        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "responseClientUnRegistration: packagename = " + stringExtra);
        new h(context, "pclient_request_info").f(stringExtra);
        String stringExtra2 = intent.getStringExtra("device_token");
        if (intent.getBooleanExtra("isTokenEncrypt", false)) {
            stringExtra2 = f.b(context, stringExtra2);
        }
        if (TextUtils.isEmpty(stringExtra2)) {
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "token is null, cannot deregister");
            return;
        }
        String d = b.d(context, stringExtra);
        if (stringExtra2.equals(d)) {
            if (e.c()) {
                e.b(stringExtra);
            }
            com.huawei.android.pushagent.c.a.f.c(context, b.d(context, stringExtra), stringExtra);
            b(context, stringExtra);
            b.b(context, stringExtra);
            return;
        }
        com.huawei.android.pushagent.c.a.e.b("PushLogAC2705", "token not match, cannot deregister. token is " + com.huawei.android.pushagent.c.a.a.e.a(stringExtra2) + ", local token is " + com.huawei.android.pushagent.c.a.a.e.a(d));
    }

    private void e(Context context, Intent intent) {
        String str = "";
        Uri data = intent.getData();
        if (data != null) {
            str = data.getSchemeSpecificPart();
        }
        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "responseAddPackage pkgName= " + str);
        if (!TextUtils.isEmpty(str)) {
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "responseAddPackage,isRegistered:" + b.a(context, str));
            if (!b.a(context, str)) {
                return;
            }
            if (com.huawei.android.pushagent.c.a.c(context, str)) {
                a(context, str);
                return;
            }
            String d = b.d(context, str);
            Intent intent2 = new Intent();
            intent2.putExtra(PushConstants.PACKAGE_NAME, str);
            intent2.putExtra("device_token", d);
            d(context, intent);
        }
    }

    private void f(Context context, Intent intent) {
        String str = "";
        Uri data = intent.getData();
        if (data != null) {
            str = data.getSchemeSpecificPart();
        }
        boolean booleanExtra = intent.getBooleanExtra("android.intent.extra.DATA_REMOVED", true);
        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "ACTION_PACKAGE_REMOVED : isRemoveData=" + booleanExtra + " remove pkgName:" + str);
        if (booleanExtra) {
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "responseRemovePackage pkgName= " + str);
            if (com.huawei.android.pushagent.c.a.c(context, str)) {
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "received pkgRemove action, but pkg:" + str + " is exist and have " + "com.huawei.android.push.intent.REGISTRATION" + ", register again");
                if (b.a(context, str)) {
                    a(context, str);
                    return;
                }
                return;
            }
            String d = b.d(context, str);
            Intent intent2 = new Intent();
            intent2.putExtra(PushConstants.PACKAGE_NAME, str);
            intent2.putExtra("device_token", d);
            d(context, intent2);
        }
    }

    public void a(Context context, Intent intent) {
        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "enter PushCommandProcessor:onReceive(intent:" + intent + " context:" + context);
        String action = intent.getAction();
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("pushConfig", 4);
            Editor edit = sharedPreferences.edit();
            NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
            if (networkInfo != null) {
                if (networkInfo.isConnected()) {
                    edit.putLong("cloudpush_net_on", System.currentTimeMillis()).commit();
                } else if (!networkInfo.isConnectedOrConnecting()) {
                    edit.putLong("cloudpush_net_off", System.currentTimeMillis()).commit();
                }
                a(sharedPreferences, networkInfo);
                return;
            }
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "networkinfo is null");
        } else if ("com.huawei.android.push.intent.CONNECTED".equals(action)) {
            a(context, "cloudpush_on", Boolean.valueOf(true));
            try {
                com.huawei.android.pushagent.b.a.a.e().a(c(context, com.huawei.android.pushagent.c.a.a(context)));
            } catch (Throwable e) {
                com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "call ChannelMgr.getPushChannel().send cause:" + e.toString(), e);
            }
        } else if ("com.huawei.android.push.intent.CHANNEL_CLOSED".equals(action)) {
            a(context, "cloudpush_off", Boolean.valueOf(false));
        } else if ("com.huawei.android.push.intent.MSG_RECEIVED".equals(action)) {
            b(context, intent);
        } else if ("com.huawei.android.push.intent.REGISTER".equals(action)) {
            c(context, intent);
        } else if ("com.huawei.android.push.intent.DEREGISTER".equals(action)) {
            d(context, intent);
        } else if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
            e(context, intent);
        } else if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
            f(context, intent);
        } else if ("android.ctrlsocket.all.allowed".equals(action)) {
            this.c = new String[0];
            com.huawei.android.pushagent.c.a.e.b("PushLogAC2705", "all packages allow to use push, send cached messages to apps");
            for (com.huawei.android.pushagent.a.b.a aVar : this.d) {
                if (!(aVar.b() == null || aVar.c() == null)) {
                    a(context, aVar.a(), aVar.b(), aVar.c());
                }
            }
            this.d.clear();
            for (com.huawei.android.pushagent.a.b.a aVar2 : this.e) {
                if (!(aVar2.b() == null || aVar2.c() == null)) {
                    b(context, aVar2.b(), aVar2.c());
                }
            }
            this.e.clear();
        } else if (!"android.scroff.ctrlsocket.status".equals(action)) {
        } else {
            if (intent.getBooleanExtra("ctrl_socket_status", false)) {
                Object stringExtra = intent.getStringExtra("ctrl_socket_list");
                com.huawei.android.pushagent.c.a.e.b("PushLogAC2705", "only whitepackages can use push:" + stringExtra);
                if (!TextUtils.isEmpty(stringExtra)) {
                    this.c = stringExtra.split("\t");
                    return;
                }
                return;
            }
            this.c = new String[0];
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "not support push in sleep model");
        }
    }

    public void a(Context context, byte[] bArr, byte[] bArr2) {
        String str = "";
        try {
            JSONObject jSONObject = new JSONObject(new String(bArr2, "UTF-8"));
            if (jSONObject.has("msgContent")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("msgContent");
                if (jSONObject2 != null && jSONObject2.has("dispPkgName")) {
                    str = jSONObject2.getString("dispPkgName");
                }
            }
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "dispkgName is " + str);
            if (TextUtils.isEmpty(str)) {
                str = "com.huawei.android.pushagent";
            }
            Intent intent;
            if (context.getPackageName().equals(str.trim())) {
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "need current package " + context.getPackageName() + " to depose selfshow msg");
                intent = new Intent("com.huawei.intent.action.PUSH");
                intent.putExtra("selfshow_info", bArr2);
                intent.putExtra("selfshow_token", bArr);
                intent.setPackage(context.getPackageName());
                context.sendBroadcast(intent);
            } else if (com.huawei.android.pushagent.c.a.a(context, "com.huawei.android.push.intent.AD_INFO").contains(str)) {
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "need old package " + str + " to depose selfshow msg");
                r1 = new Intent("com.huawei.android.push.intent.AD_INFO");
                r1.putExtra("ad_event_info", bArr2);
                r1.putExtra("ad_event_token", bArr);
                r1.setPackage(str);
                context.sendBroadcast(r1);
            } else if (com.huawei.android.pushagent.c.a.d(context, str) || com.huawei.android.pushagent.c.a.c(context, str)) {
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "try to send selfshow msg to push client ,package " + str + " to depose selfshow msg");
                r1 = new Intent("com.huawei.intent.action.PUSH");
                r1.putExtra("selfshow_info", bArr2);
                r1.putExtra("selfshow_token", bArr);
                r1.setFlags(32);
                r1.setPackage(str);
                context.sendBroadcast(r1);
            } else {
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "not find dispkgName " + str + " or is not pushclient");
                intent = new Intent("com.huawei.intent.action.PUSH");
                intent.putExtra("selfshow_info", bArr2);
                intent.putExtra("selfshow_token", bArr);
                intent.putExtra("selfshow_event_id", "5");
                intent.setPackage(context.getPackageName());
                context.sendBroadcast(intent);
            }
        } catch (JSONException e) {
            com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "deposeSelfShowMsg error:" + e.toString());
        } catch (IOException e2) {
            com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "deposeSelfShowMsg error:" + e2.toString());
        } catch (Exception e3) {
            com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "deposeSelfShowMsg error:" + e3.toString());
        }
    }
}
