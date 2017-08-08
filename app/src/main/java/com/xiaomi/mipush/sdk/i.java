package com.xiaomi.mipush.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.text.TextUtils;
import com.MCWorld.framework.BaseHttpMgr;
import com.xiaomi.push.service.r;
import com.xiaomi.push.service.v;
import com.xiaomi.push.service.z;
import com.xiaomi.xmpush.thrift.a;
import com.xiaomi.xmpush.thrift.aa;
import com.xiaomi.xmpush.thrift.ac;
import com.xiaomi.xmpush.thrift.ad;
import com.xiaomi.xmpush.thrift.f;
import com.xiaomi.xmpush.thrift.h;
import com.xiaomi.xmpush.thrift.k;
import com.xiaomi.xmpush.thrift.n;
import com.xiaomi.xmpush.thrift.o;
import com.xiaomi.xmpush.thrift.p;
import com.xiaomi.xmpush.thrift.q;
import com.xiaomi.xmpush.thrift.t;
import com.xiaomi.xmpush.thrift.w;
import com.xiaomi.xmpush.thrift.y;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TimeZone;

public class i {
    private static i a = null;
    private static Queue<String> c;
    private static Object d = new Object();
    private Context b;

    private i(Context context) {
        this.b = context.getApplicationContext();
        if (this.b == null) {
            this.b = context;
        }
    }

    public static Intent a(Context context, String str, Map<String, String> map) {
        Intent launchIntentForPackage;
        URISyntaxException e;
        MalformedURLException malformedURLException;
        if (map == null || !map.containsKey("notify_effect")) {
            return null;
        }
        String str2 = (String) map.get("notify_effect");
        if (z.a.equals(str2)) {
            try {
                launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(str);
            } catch (Exception e2) {
                b.d("Cause: " + e2.getMessage());
                launchIntentForPackage = null;
            }
        } else {
            Intent intent;
            if (z.b.equals(str2)) {
                if (map.containsKey("intent_uri")) {
                    str2 = (String) map.get("intent_uri");
                    if (str2 != null) {
                        try {
                            launchIntentForPackage = Intent.parseUri(str2, 1);
                            try {
                                launchIntentForPackage.setPackage(str);
                            } catch (URISyntaxException e3) {
                                e = e3;
                                b.d("Cause: " + e.getMessage());
                                if (launchIntentForPackage == null) {
                                    return null;
                                }
                                launchIntentForPackage.addFlags(268435456);
                                try {
                                    return context.getPackageManager().resolveActivity(launchIntentForPackage, 65536) != null ? null : launchIntentForPackage;
                                } catch (Exception e22) {
                                    b.d("Cause: " + e22.getMessage());
                                    return null;
                                }
                            }
                        } catch (URISyntaxException e4) {
                            e = e4;
                            launchIntentForPackage = null;
                            b.d("Cause: " + e.getMessage());
                            if (launchIntentForPackage == null) {
                                return null;
                            }
                            launchIntentForPackage.addFlags(268435456);
                            if (context.getPackageManager().resolveActivity(launchIntentForPackage, 65536) != null) {
                            }
                        }
                    }
                    launchIntentForPackage = null;
                } else if (map.containsKey("class_name")) {
                    str2 = (String) map.get("class_name");
                    intent = new Intent();
                    intent.setComponent(new ComponentName(str, str2));
                    try {
                        if (map.containsKey("intent_flag")) {
                            intent.setFlags(Integer.parseInt((String) map.get("intent_flag")));
                        }
                    } catch (NumberFormatException e5) {
                        b.d("Cause by intent_flag: " + e5.getMessage());
                    }
                    launchIntentForPackage = intent;
                }
            } else if (z.c.equals(str2)) {
                str2 = (String) map.get("web_uri");
                if (str2 != null) {
                    str2 = str2.trim();
                    String str3 = (str2.startsWith("http://") || str2.startsWith("https://")) ? str2 : "http://" + str2;
                    try {
                        str2 = new URL(str3).getProtocol();
                        if ("http".equals(str2) || "https".equals(str2)) {
                            launchIntentForPackage = new Intent("android.intent.action.VIEW");
                            try {
                                launchIntentForPackage.setData(Uri.parse(str3));
                            } catch (MalformedURLException e6) {
                                MalformedURLException malformedURLException2 = e6;
                                intent = launchIntentForPackage;
                                malformedURLException = malformedURLException2;
                                b.d("Cause: " + malformedURLException.getMessage());
                                launchIntentForPackage = intent;
                                if (launchIntentForPackage == null) {
                                    return null;
                                }
                                launchIntentForPackage.addFlags(268435456);
                                if (context.getPackageManager().resolveActivity(launchIntentForPackage, 65536) != null) {
                                }
                            }
                        }
                        launchIntentForPackage = null;
                    } catch (MalformedURLException e7) {
                        malformedURLException = e7;
                        intent = null;
                        b.d("Cause: " + malformedURLException.getMessage());
                        launchIntentForPackage = intent;
                        if (launchIntentForPackage == null) {
                            return null;
                        }
                        launchIntentForPackage.addFlags(268435456);
                        if (context.getPackageManager().resolveActivity(launchIntentForPackage, 65536) != null) {
                        }
                    }
                }
            }
            launchIntentForPackage = null;
        }
        if (launchIntentForPackage == null) {
            return null;
        }
        launchIntentForPackage.addFlags(268435456);
        if (context.getPackageManager().resolveActivity(launchIntentForPackage, 65536) != null) {
        }
    }

    private a a(o oVar, boolean z, byte[] bArr) {
        a aVar = null;
        try {
            org.apache.thrift.b a = h.a(this.b, oVar);
            if (a == null) {
                b.d("receiving an un-recognized message. " + oVar.a);
                return null;
            }
            b.c("receive a_isRightVersion message." + a);
            a a2 = oVar.a();
            b.a("processing a_isRightVersion message, action=" + a2);
            List list;
            switch (1.a[a2.ordinal()]) {
                case 1:
                    if (!a.a(this.b).l() || z) {
                        w wVar = (w) a;
                        h l = wVar.l();
                        if (l == null) {
                            b.d("receive an empty message without push content, drop it");
                            return null;
                        }
                        String b;
                        if (z) {
                            if (r.b(oVar)) {
                                MiPushClient.reportIgnoreRegMessageClicked(this.b, l.b(), oVar.m(), oVar.f, l.d());
                            } else {
                                MiPushClient.reportMessageClicked(this.b, l.b(), oVar.m(), l.d());
                            }
                        }
                        if (!z) {
                            if (!TextUtils.isEmpty(wVar.j()) && MiPushClient.aliasSetTime(this.b, wVar.j()) < 0) {
                                MiPushClient.addAlias(this.b, wVar.j());
                            } else if (!TextUtils.isEmpty(wVar.h()) && MiPushClient.topicSubscribedTime(this.b, wVar.h()) < 0) {
                                MiPushClient.addTopic(this.b, wVar.h());
                            }
                        }
                        CharSequence charSequence = (oVar.h == null || oVar.h.s() == null) ? null : (String) oVar.h.j.get("jobkey");
                        if (TextUtils.isEmpty(charSequence)) {
                            b = l.b();
                        } else {
                            CharSequence charSequence2 = charSequence;
                        }
                        if (z || !a(this.b, b)) {
                            Serializable generateMessage = PushMessageHelper.generateMessage(wVar, oVar.m(), z);
                            if (generateMessage.getPassThrough() == 0 && !z && r.a(generateMessage.getExtra())) {
                                r.a(this.b, oVar, bArr);
                                return null;
                            }
                            b.a("receive a_isRightVersion message, msgid=" + l.b() + ", jobkey=" + b);
                            if (z && generateMessage.getExtra() != null && generateMessage.getExtra().containsKey("notify_effect")) {
                                Map extra = generateMessage.getExtra();
                                String str = (String) extra.get("notify_effect");
                                if (r.b(oVar)) {
                                    Intent a3 = a(this.b, oVar.f, extra);
                                    if (a3 == null) {
                                        b.a("Getting Intent fail from ignore reg message. ");
                                        return null;
                                    }
                                    Object f = l.f();
                                    if (!TextUtils.isEmpty(f)) {
                                        a3.putExtra("payload", f);
                                    }
                                    this.b.startActivity(a3);
                                    return null;
                                }
                                Intent a4 = a(this.b, this.b.getPackageName(), extra);
                                if (a4 == null) {
                                    return null;
                                }
                                if (!str.equals(z.c)) {
                                    a4.putExtra(PushMessageHelper.KEY_MESSAGE, generateMessage);
                                }
                                this.b.startActivity(a4);
                                return null;
                            }
                            Serializable serializable = generateMessage;
                        } else {
                            b.a("drop a_isRightVersion duplicate message, key=" + b);
                        }
                        if (oVar.m() != null || z) {
                            return aVar;
                        }
                        a(wVar, oVar);
                        return aVar;
                    }
                    b.a("receive a_isRightVersion message in pause state. drop it");
                    return null;
                case 2:
                    t tVar = (t) a;
                    if (tVar.f == 0) {
                        a.a(this.b).b(tVar.h, tVar.i);
                    }
                    if (TextUtils.isEmpty(tVar.h)) {
                        list = null;
                    } else {
                        list = new ArrayList();
                        list.add(tVar.h);
                    }
                    aVar = PushMessageHelper.generateCommandMessage(MiPushClient.COMMAND_REGISTER, list, tVar.f, tVar.g, null);
                    j.a(this.b).d();
                    return aVar;
                case 3:
                    if (((aa) a).f == 0) {
                        a.a(this.b).h();
                        MiPushClient.clearExtras(this.b);
                    }
                    PushMessageHandler.a();
                    return null;
                case 4:
                    y yVar = (y) a;
                    if (yVar.f == 0) {
                        MiPushClient.addTopic(this.b, yVar.h());
                    }
                    if (TextUtils.isEmpty(yVar.h())) {
                        list = null;
                    } else {
                        list = new ArrayList();
                        list.add(yVar.h());
                    }
                    return PushMessageHelper.generateCommandMessage(MiPushClient.COMMAND_SUBSCRIBE_TOPIC, list, yVar.f, yVar.g, yVar.k());
                case 5:
                    ac acVar = (ac) a;
                    if (acVar.f == 0) {
                        MiPushClient.removeTopic(this.b, acVar.h());
                    }
                    if (TextUtils.isEmpty(acVar.h())) {
                        list = null;
                    } else {
                        list = new ArrayList();
                        list.add(acVar.h());
                    }
                    return PushMessageHelper.generateCommandMessage(MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC, list, acVar.f, acVar.g, acVar.k());
                case 6:
                    n nVar = (n) a;
                    Object e = nVar.e();
                    List k = nVar.k();
                    if (nVar.g == 0) {
                        if (TextUtils.equals(e, MiPushClient.COMMAND_SET_ACCEPT_TIME) && k != null && k.size() > 1) {
                            MiPushClient.addAcceptTime(this.b, (String) k.get(0), (String) k.get(1));
                            if ("00:00".equals(k.get(0)) && "00:00".equals(k.get(1))) {
                                a.a(this.b).a(true);
                            } else {
                                a.a(this.b).a(false);
                            }
                            list = a(TimeZone.getTimeZone("GMT+08"), TimeZone.getDefault(), k);
                            return PushMessageHelper.generateCommandMessage(e, list, nVar.g, nVar.h, nVar.m());
                        } else if (TextUtils.equals(e, MiPushClient.COMMAND_SET_ALIAS) && k != null && k.size() > 0) {
                            MiPushClient.addAlias(this.b, (String) k.get(0));
                            list = k;
                            return PushMessageHelper.generateCommandMessage(e, list, nVar.g, nVar.h, nVar.m());
                        } else if (TextUtils.equals(e, MiPushClient.COMMAND_UNSET_ALIAS) && k != null && k.size() > 0) {
                            MiPushClient.removeAlias(this.b, (String) k.get(0));
                            list = k;
                            return PushMessageHelper.generateCommandMessage(e, list, nVar.g, nVar.h, nVar.m());
                        } else if (TextUtils.equals(e, MiPushClient.COMMAND_SET_ACCOUNT) && k != null && k.size() > 0) {
                            MiPushClient.addAccount(this.b, (String) k.get(0));
                            list = k;
                            return PushMessageHelper.generateCommandMessage(e, list, nVar.g, nVar.h, nVar.m());
                        } else if (TextUtils.equals(e, MiPushClient.COMMAND_UNSET_ACCOUNT) && k != null && k.size() > 0) {
                            MiPushClient.removeAccount(this.b, (String) k.get(0));
                        }
                    }
                    list = k;
                    return PushMessageHelper.generateCommandMessage(e, list, nVar.g, nVar.h, nVar.m());
                case 7:
                    com.xiaomi.xmpush.thrift.r rVar = (com.xiaomi.xmpush.thrift.r) a;
                    if ("registration id expired".equalsIgnoreCase(rVar.e)) {
                        MiPushClient.reInitialize(this.b);
                        return null;
                    } else if ("client_info_update_ok".equalsIgnoreCase(rVar.e)) {
                        if (rVar.h() == null || !rVar.h().containsKey(BaseHttpMgr.PARAM_APP_VERSION)) {
                            return null;
                        }
                        a.a(this.b).a((String) rVar.h().get(BaseHttpMgr.PARAM_APP_VERSION));
                        return null;
                    } else if ("awake_app".equalsIgnoreCase(rVar.e)) {
                        if (rVar.h() == null || !rVar.h().containsKey("packages")) {
                            return null;
                        }
                        MiPushClient.awakeApps(this.b, ((String) rVar.h().get("packages")).split(MiPushClient.ACCEPT_TIME_SEPARATOR));
                        return null;
                    } else if (f.l.p.equalsIgnoreCase(rVar.e)) {
                        r1 = new q();
                        try {
                            ad.a(r1, rVar.l());
                            com.xiaomi.push.service.w.a(v.a(this.b), r1);
                            return null;
                        } catch (Throwable e2) {
                            b.a(e2);
                            return null;
                        }
                    } else if (!f.m.p.equalsIgnoreCase(rVar.e)) {
                        return null;
                    } else {
                        r1 = new p();
                        try {
                            ad.a(r1, rVar.l());
                            com.xiaomi.push.service.w.a(v.a(this.b), r1);
                            return null;
                        } catch (Throwable e22) {
                            b.a(e22);
                            return null;
                        }
                    }
                default:
                    return null;
            }
        } catch (Throwable e222) {
            b.a(e222);
            b.d("receive a_isRightVersion message which action string is not valid. is the reg expired?");
            return null;
        }
    }

    private a a(o oVar, byte[] bArr) {
        String str = null;
        try {
            org.apache.thrift.b a = h.a(this.b, oVar);
            if (a == null) {
                b.d("message arrived: receiving an un-recognized message. " + oVar.a);
                return null;
            }
            b.c("message arrived: receive a_isRightVersion message." + a);
            a a2 = oVar.a();
            b.a("message arrived: processing an arrived message, action=" + a2);
            switch (1.a[a2.ordinal()]) {
                case 1:
                    w wVar = (w) a;
                    h l = wVar.l();
                    if (l == null) {
                        b.d("message arrived: receive an empty message without push content, drop it");
                        return null;
                    }
                    if (!(oVar.h == null || oVar.h.s() == null)) {
                        str = (String) oVar.h.j.get("jobkey");
                    }
                    MiPushMessage generateMessage = PushMessageHelper.generateMessage(wVar, oVar.m(), false);
                    generateMessage.setArrivedMessage(true);
                    b.a("message arrived: receive a_isRightVersion message, msgid=" + l.b() + ", jobkey=" + str);
                    return generateMessage;
                default:
                    return null;
            }
        } catch (Throwable e) {
            b.a(e);
            b.d("message arrived: receive a_isRightVersion message which action string is not valid. is the reg expired?");
            return null;
        }
    }

    public static i a(Context context) {
        if (a == null) {
            a = new i(context);
        }
        return a;
    }

    private void a(o oVar) {
        com.xiaomi.xmpush.thrift.i m = oVar.m();
        org.apache.thrift.b kVar = new k();
        kVar.b(oVar.h());
        kVar.a(m.b());
        kVar.a(m.d());
        if (!TextUtils.isEmpty(m.f())) {
            kVar.c(m.f());
        }
        kVar.a(ad.a(this.b, oVar.f));
        j.a(this.b).a(kVar, a.AckMessage, false, oVar.m());
    }

    private void a(w wVar, o oVar) {
        com.xiaomi.xmpush.thrift.i m = oVar.m();
        org.apache.thrift.b kVar = new k();
        kVar.b(wVar.e());
        kVar.a(wVar.c());
        kVar.a(wVar.l().h());
        if (!TextUtils.isEmpty(wVar.h())) {
            kVar.c(wVar.h());
        }
        if (!TextUtils.isEmpty(wVar.j())) {
            kVar.d(wVar.j());
        }
        kVar.a(ad.a(this.b, oVar.f));
        j.a(this.b).a(kVar, a.AckMessage, m);
    }

    private static boolean a(Context context, String str) {
        boolean z = false;
        synchronized (d) {
            SharedPreferences j = a.a(context).j();
            if (c == null) {
                String[] split = j.getString("pref_msg_ids", "").split(MiPushClient.ACCEPT_TIME_SEPARATOR);
                c = new LinkedList();
                for (Object add : split) {
                    c.add(add);
                }
            }
            if (c.contains(str)) {
                z = true;
            } else {
                c.add(str);
                if (c.size() > 25) {
                    c.poll();
                }
                String a = d.a(c, MiPushClient.ACCEPT_TIME_SEPARATOR);
                Editor edit = j.edit();
                edit.putString("pref_msg_ids", a);
                edit.commit();
            }
        }
        return z;
    }

    public a a(Intent intent) {
        String action = intent.getAction();
        b.a("receive an intent from server, action=" + action);
        String stringExtra = intent.getStringExtra("mrt");
        if (stringExtra == null) {
            stringExtra = Long.toString(System.currentTimeMillis());
        }
        byte[] byteArrayExtra;
        if ("com.xiaomi.mipush.RECEIVE_MESSAGE".equals(action)) {
            byteArrayExtra = intent.getByteArrayExtra("mipush_payload");
            boolean booleanExtra = intent.getBooleanExtra("mipush_notified", false);
            if (byteArrayExtra == null) {
                b.d("receiving an empty message, drop");
                return null;
            }
            o oVar = new o();
            try {
                ad.a((org.apache.thrift.b) oVar, byteArrayExtra);
                a a = a.a(this.b);
                com.xiaomi.xmpush.thrift.i m = oVar.m();
                if (!(oVar.a() != a.SendMessage || m == null || a.l() || booleanExtra)) {
                    if (m != null) {
                        oVar.m().a("mrt", stringExtra);
                        oVar.m().a("mat", Long.toString(System.currentTimeMillis()));
                    }
                    a(oVar);
                }
                if (oVar.a() == a.SendMessage && !oVar.c()) {
                    if (!r.b(oVar)) {
                        action = "drop an un-encrypted messages. %1$s, %2$s";
                        Object[] objArr = new Object[2];
                        objArr[0] = oVar.j();
                        objArr[1] = m != null ? m.b() : "";
                        b.a(String.format(action, objArr));
                        return null;
                    } else if (!(booleanExtra && m.s() != null && m.s().containsKey("notify_effect"))) {
                        b.a(String.format("drop an un-encrypted messages. %1$s, %2$s", new Object[]{oVar.j(), m.b()}));
                        return null;
                    }
                }
                if (a.i() || oVar.a == a.Registration) {
                    if (!a.i() || !a.n()) {
                        return a(oVar, booleanExtra, byteArrayExtra);
                    }
                    if (oVar.a == a.UnRegistration) {
                        a.h();
                        MiPushClient.clearExtras(this.b);
                        PushMessageHandler.a();
                    } else {
                        MiPushClient.unregisterPush(this.b);
                    }
                } else if (r.b(oVar)) {
                    return a(oVar, booleanExtra, byteArrayExtra);
                } else {
                    b.d("receive message without registration. need unregister or re-register!");
                }
            } catch (Throwable e) {
                b.a(e);
            } catch (Throwable e2) {
                b.a(e2);
            }
        } else if ("com.xiaomi.mipush.ERROR".equals(action)) {
            a miPushCommandMessage = new MiPushCommandMessage();
            org.apache.thrift.b oVar2 = new o();
            try {
                byteArrayExtra = intent.getByteArrayExtra("mipush_payload");
                if (byteArrayExtra != null) {
                    ad.a(oVar2, byteArrayExtra);
                }
            } catch (org.apache.thrift.f e3) {
            }
            miPushCommandMessage.setCommand(String.valueOf(oVar2.a()));
            miPushCommandMessage.setResultCode((long) intent.getIntExtra("mipush_error_code", 0));
            miPushCommandMessage.setReason(intent.getStringExtra("mipush_error_msg"));
            b.d("receive a_isRightVersion error message. code = " + intent.getIntExtra("mipush_error_code", 0) + ", msg= " + intent.getStringExtra("mipush_error_msg"));
            return miPushCommandMessage;
        } else if ("com.xiaomi.mipush.MESSAGE_ARRIVED".equals(action)) {
            byte[] byteArrayExtra2 = intent.getByteArrayExtra("mipush_payload");
            if (byteArrayExtra2 == null) {
                b.d("message arrived: receiving an empty message, drop");
                return null;
            }
            o oVar3 = new o();
            try {
                ad.a((org.apache.thrift.b) oVar3, byteArrayExtra2);
                a a2 = a.a(this.b);
                if (r.b(oVar3)) {
                    b.d("message arrived: receive ignore reg message, ignore!");
                } else if (!a2.i()) {
                    b.d("message arrived: receive message without registration. need unregister or re-register!");
                } else if (!a2.i() || !a2.n()) {
                    return a(oVar3, byteArrayExtra2);
                } else {
                    b.d("message arrived: app info is invalidated");
                }
            } catch (Throwable e22) {
                b.a(e22);
            } catch (Throwable e222) {
                b.a(e222);
            }
        }
        return null;
    }

    public List<String> a(TimeZone timeZone, TimeZone timeZone2, List<String> list) {
        if (timeZone.equals(timeZone2)) {
            return list;
        }
        long rawOffset = (long) (((timeZone.getRawOffset() - timeZone2.getRawOffset()) / 1000) / 60);
        long parseLong = Long.parseLong(((String) list.get(0)).split(":")[0]);
        long parseLong2 = Long.parseLong(((String) list.get(0)).split(":")[1]);
        parseLong = ((((parseLong * 60) + parseLong2) - rawOffset) + 1440) % 1440;
        long parseLong3 = (((Long.parseLong(((String) list.get(1)).split(":")[1]) + (60 * Long.parseLong(((String) list.get(1)).split(":")[0]))) - rawOffset) + 1440) % 1440;
        List arrayList = new ArrayList();
        List list2 = arrayList;
        list2.add(String.format("%1$02d:%2$02d", new Object[]{Long.valueOf(parseLong / 60), Long.valueOf(parseLong % 60)}));
        list2 = arrayList;
        list2.add(String.format("%1$02d:%2$02d", new Object[]{Long.valueOf(parseLong3 / 60), Long.valueOf(parseLong3 % 60)}));
        return arrayList;
    }
}
