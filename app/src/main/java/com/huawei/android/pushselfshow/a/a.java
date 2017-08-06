package com.huawei.android.pushselfshow.a;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.baidu.android.pushservice.PushConstants;
import com.huawei.android.pushagent.c.a.e;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.tencent.smtt.sdk.WebView;

public class a {
    private static final String[] a = new String[]{com.huluxia.data.profile.a.qe, "url", "email", PushConstants.EXTRA_APP, "cosa", "rp"};
    private Context b;
    private com.huawei.android.pushselfshow.b.a c;

    public a(Context context, com.huawei.android.pushselfshow.b.a aVar) {
        this.b = context;
        this.c = aVar;
    }

    public static boolean a(String str) {
        for (String equals : a) {
            if (equals.equals(str)) {
                return true;
            }
        }
        return false;
    }

    private void b() {
        e.a("PushSelfShowLog", "enter launchUrl");
        try {
            if (!(this.c.G == 0 || this.c.H == null || this.c.H.length() <= 0)) {
                if (this.c.B.indexOf("?") != -1) {
                    this.c.B += "&" + this.c.H + SimpleComparison.EQUAL_TO_OPERATION + com.huawei.android.pushselfshow.utils.a.a(com.huawei.android.pushselfshow.utils.a.b(this.b));
                } else {
                    this.c.B += "?" + this.c.H + SimpleComparison.EQUAL_TO_OPERATION + com.huawei.android.pushselfshow.utils.a.a(com.huawei.android.pushselfshow.utils.a.b(this.b));
                }
            }
            e.a("PushSelfShowLog", "url =" + this.c.B);
            if (this.c.F == 0) {
                String str = this.c.B;
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW").setFlags(268435456).setData(Uri.parse(str));
                this.b.startActivity(intent);
                return;
            }
            this.c.C = this.c.B;
            this.c.E = "text/html";
            this.c.D = "html";
            g();
        } catch (Throwable e) {
            e.c("PushSelfShowLog", e.toString(), e);
        }
    }

    private void c() {
        e.a("PushSelfShowLog", "enter launchCall");
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.DIAL").setData(Uri.parse(WebView.SCHEME_TEL + this.c.v)).setFlags(268435456);
            this.b.startActivity(intent);
        } catch (Throwable e) {
            e.c("PushSelfShowLog", e.toString(), e);
        }
    }

    private void d() {
        e.a("PushSelfShowLog", "enter launchMail");
        try {
            if (this.c.w != null) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SENDTO").setFlags(268435456).setData(Uri.fromParts("mailto", this.c.w, null)).putExtra("android.intent.extra.SUBJECT", this.c.x).putExtra("android.intent.extra.TEXT", this.c.y).setPackage("com.android.email");
                this.b.startActivity(intent);
            }
        } catch (Throwable e) {
            e.c("PushSelfShowLog", e.toString(), e);
        }
    }

    private void e() {
        try {
            e.a("PushSelfShowLog", "enter launchApp, appPackageName =" + this.c.z);
            if (com.huawei.android.pushselfshow.utils.a.b(this.b, this.c.z)) {
                f();
                return;
            }
            try {
                e.e("PushSelfShowLog", "insert into db message.getMsgId() is " + this.c.a() + ",message.appPackageName is " + this.c.z);
                com.huawei.android.pushselfshow.utils.a.a.a(this.b, this.c.a(), this.c.z);
            } catch (Throwable e) {
                e.d("PushSelfShowLog", "launchApp not exist ,insertAppinfo error", e);
            }
            Intent intent = null;
            if (com.huawei.android.pushselfshow.utils.a.a(this.b, "com.huawei.appmarket", new Intent("com.huawei.appmarket.intent.action.AppDetail")).booleanValue()) {
                e.a("PushSelfShowLog", "app not exist && appmarkt exist ,so open appmarket");
                intent = new Intent("com.huawei.appmarket.intent.action.AppDetail");
                intent.putExtra("APP_PACKAGENAME", this.c.z);
                intent.setPackage("com.huawei.appmarket");
                intent.setFlags(402653184);
                e.a("PushSelfShowLog", "hwAppmarket only support com.huawei.appmarket.intent.action.AppDetail!");
                com.huawei.android.pushselfshow.utils.a.a(this.b, "7", this.c);
            }
            if (intent != null) {
                e.a("PushSelfShowLog", "intent is not null " + intent.toURI());
                this.b.startActivity(intent);
                return;
            }
            e.a("PushSelfShowLog", "intent is null ");
        } catch (Exception e2) {
            e.d("PushSelfShowLog", "launchApp error:" + e2.toString());
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void f() {
        /*
        r5 = this;
        r0 = "PushSelfShowLog";
        r1 = "run into launchCosaApp ";
        com.huawei.android.pushagent.c.a_isRightVersion.e.e(r0, r1);
        r0 = "PushSelfShowLog";
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00bc }
        r1.<init>();	 Catch:{ Exception -> 0x00bc }
        r2 = "enter launchExistApp cosa, appPackageName =";
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x00bc }
        r2 = r5.c;	 Catch:{ Exception -> 0x00bc }
        r2 = r2.z;	 Catch:{ Exception -> 0x00bc }
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x00bc }
        r2 = ",and msg.intentUri is ";
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x00bc }
        r2 = r5.c;	 Catch:{ Exception -> 0x00bc }
        r2 = r2.f;	 Catch:{ Exception -> 0x00bc }
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x00bc }
        r1 = r1.toString();	 Catch:{ Exception -> 0x00bc }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r0, r1);	 Catch:{ Exception -> 0x00bc }
        r0 = r5.b;	 Catch:{ Exception -> 0x00bc }
        r1 = r5.c;	 Catch:{ Exception -> 0x00bc }
        r1 = r1.z;	 Catch:{ Exception -> 0x00bc }
        r1 = com.huawei.android.pushselfshow.utils.a_isRightVersion.a_isRightVersion(r0, r1);	 Catch:{ Exception -> 0x00bc }
        r0 = r5.c;	 Catch:{ Exception -> 0x00bc }
        r0 = r0.f;	 Catch:{ Exception -> 0x00bc }
        if (r0 == 0) goto L_0x0095;
    L_0x0046:
        r0 = r5.c;	 Catch:{ Exception -> 0x0089 }
        r0 = r0.f;	 Catch:{ Exception -> 0x0089 }
        r2 = 0;
        r0 = android.content.Intent.parseUri(r0, r2);	 Catch:{ Exception -> 0x0089 }
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0089 }
        r3.<init>();	 Catch:{ Exception -> 0x0089 }
        r4 = "Intent.parseUri(msg.intentUri, 0)，";
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x0089 }
        r4 = r1.toURI();	 Catch:{ Exception -> 0x0089 }
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x0089 }
        r3 = r3.toString();	 Catch:{ Exception -> 0x0089 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r2, r3);	 Catch:{ Exception -> 0x0089 }
        r2 = r5.b;	 Catch:{ Exception -> 0x0089 }
        r3 = r5.c;	 Catch:{ Exception -> 0x0089 }
        r3 = r3.z;	 Catch:{ Exception -> 0x0089 }
        r2 = com.huawei.android.pushselfshow.utils.a_isRightVersion.a_isRightVersion(r2, r3, r0);	 Catch:{ Exception -> 0x0089 }
        r2 = r2.booleanValue();	 Catch:{ Exception -> 0x0089 }
        if (r2 == 0) goto L_0x00f3;
    L_0x007d:
        if (r0 != 0) goto L_0x00c8;
    L_0x007f:
        r0 = "PushSelfShowLog";
        r1 = "launchCosaApp,intent == null";
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r0, r1);	 Catch:{ Exception -> 0x00bc }
    L_0x0088:
        return;
    L_0x0089:
        r0 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = "intentUri error ";
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r2, r3, r0);	 Catch:{ Exception -> 0x00bc }
        r0 = r1;
        goto L_0x007d;
    L_0x0095:
        r0 = r5.c;	 Catch:{ Exception -> 0x00bc }
        r0 = r0.A;	 Catch:{ Exception -> 0x00bc }
        if (r0 == 0) goto L_0x00f1;
    L_0x009b:
        r0 = new android.content.Intent;	 Catch:{ Exception -> 0x00bc }
        r2 = r5.c;	 Catch:{ Exception -> 0x00bc }
        r2 = r2.A;	 Catch:{ Exception -> 0x00bc }
        r0.<init>(r2);	 Catch:{ Exception -> 0x00bc }
        r2 = r5.b;	 Catch:{ Exception -> 0x00bc }
        r3 = r5.c;	 Catch:{ Exception -> 0x00bc }
        r3 = r3.z;	 Catch:{ Exception -> 0x00bc }
        r2 = com.huawei.android.pushselfshow.utils.a_isRightVersion.a_isRightVersion(r2, r3, r0);	 Catch:{ Exception -> 0x00bc }
        r2 = r2.booleanValue();	 Catch:{ Exception -> 0x00bc }
        if (r2 == 0) goto L_0x00f1;
    L_0x00b4:
        r1 = r5.c;	 Catch:{ Exception -> 0x00bc }
        r1 = r1.z;	 Catch:{ Exception -> 0x00bc }
        r0.setPackage(r1);	 Catch:{ Exception -> 0x00bc }
        goto L_0x007d;
    L_0x00bc:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = r0.toString();
        com.huawei.android.pushagent.c.a_isRightVersion.e.c(r1, r2, r0);
        goto L_0x0088;
    L_0x00c8:
        r1 = 805437440; // 0x30020000 float:4.7293724E-10 double:3.97938969E-315;
        r0.setFlags(r1);	 Catch:{ Exception -> 0x00bc }
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00bc }
        r2.<init>();	 Catch:{ Exception -> 0x00bc }
        r3 = "start ";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x00bc }
        r3 = r0.toURI();	 Catch:{ Exception -> 0x00bc }
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x00bc }
        r2 = r2.toString();	 Catch:{ Exception -> 0x00bc }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r2);	 Catch:{ Exception -> 0x00bc }
        r1 = r5.b;	 Catch:{ Exception -> 0x00bc }
        r1.startActivity(r0);	 Catch:{ Exception -> 0x00bc }
        goto L_0x0088;
    L_0x00f1:
        r0 = r1;
        goto L_0x00b4;
    L_0x00f3:
        r0 = r1;
        goto L_0x007d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.a_isRightVersion.a_isRightVersion.f():void");
    }

    private void g() {
        try {
            e.e("PushSelfShowLog", "run into launchRichPush ");
            Intent intent = new Intent("com.huawei.android.push.intent.RICHPUSH");
            intent.putExtra("type", this.c.D);
            intent.putExtra("selfshow_info", this.c.c());
            intent.putExtra("selfshow_token", this.c.d());
            intent.setFlags(268468240);
            intent.setPackage(this.b.getPackageName());
            this.b.startActivity(intent);
        } catch (Throwable e) {
            e.c("PushSelfShowLog", "launchRichPush failed", e);
        }
    }

    public void a() {
        e.a("PushSelfShowLog", "enter launchNotify()");
        if (this.b == null || this.c == null) {
            e.a("PushSelfShowLog", "launchNotify  context or msg is null");
        } else if (PushConstants.EXTRA_APP.equals(this.c.o)) {
            e();
        } else if ("cosa".equals(this.c.o)) {
            f();
        } else if ("email".equals(this.c.o)) {
            d();
        } else if (com.huluxia.data.profile.a.qe.equals(this.c.o)) {
            c();
        } else if ("rp".equals(this.c.o)) {
            g();
        } else if ("url".equals(this.c.o)) {
            b();
        } else {
            e.a("PushSelfShowLog", this.c.o + " is not exist in hShowType");
        }
    }
}
