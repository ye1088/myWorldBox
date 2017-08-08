package com.xiaomi.push.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.Process;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.network.Fallback;
import com.xiaomi.network.HostManager;
import com.xiaomi.smack.p;
import com.xiaomi.xmpush.thrift.ad;
import com.xiaomi.xmpush.thrift.o;
import com.xiaomi.xmpush.thrift.r;
import com.xiaomi.xmpush.thrift.s;
import io.netty.handler.traffic.AbstractTrafficShapingHandler;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class XMPushService extends Service implements com.xiaomi.smack.d {
    public static int a = 1;
    private static final int f = Process.myPid();
    private com.xiaomi.smack.b b;
    private ae c;
    private d d;
    private long e = 0;
    private com.xiaomi.smack.l g;
    private com.xiaomi.smack.a h;
    private b i;
    private PacketSync j = null;
    private e k = null;
    private com.xiaomi.smack.f l = new ai(this);

    public static abstract class g extends com.xiaomi.push.service.e.b {
        public g(int i) {
            super(i);
        }

        public abstract void a();

        public abstract String b();

        public void run() {
            if (!(this.a == 4 || this.a == 8)) {
                com.xiaomi.channel.commonutils.logger.b.a("JOB: " + b());
            }
            a();
        }
    }

    class a extends g {
        com.xiaomi.push.service.x.b b = null;
        final /* synthetic */ XMPushService c;

        public a(XMPushService xMPushService, com.xiaomi.push.service.x.b bVar) {
            this.c = xMPushService;
            super(9);
            this.b = bVar;
        }

        public void a() {
            try {
                if (this.c.e()) {
                    com.xiaomi.push.service.x.b b = x.a().b(this.b.h, this.b.b);
                    if (b == null) {
                        com.xiaomi.channel.commonutils.logger.b.a("ignore bind because the channel " + this.b.h + " is removed ");
                        return;
                    } else if (b.m == com.xiaomi.push.service.x.c.unbind) {
                        b.a(com.xiaomi.push.service.x.c.binding, 0, 0, null, null);
                        this.c.h.a(b);
                        com.xiaomi.stats.g.a(this.c, b);
                        return;
                    } else {
                        com.xiaomi.channel.commonutils.logger.b.a("trying duplicate bind, ingore! " + b.m);
                        return;
                    }
                }
                com.xiaomi.channel.commonutils.logger.b.d("trying bind while the connection is not created, quit!");
            } catch (Exception e) {
                com.xiaomi.channel.commonutils.logger.b.a((Throwable) e);
                this.c.a(10, e);
            }
        }

        public String b() {
            return "bind the client. " + this.b.h;
        }
    }

    static class b extends g {
        private final com.xiaomi.push.service.x.b b;

        public b(com.xiaomi.push.service.x.b bVar) {
            super(12);
            this.b = bVar;
        }

        public void a() {
            this.b.a(com.xiaomi.push.service.x.c.unbind, 1, 21, null, null);
        }

        public String b() {
            return "bind time out. chid=" + this.b.h;
        }

        public boolean equals(Object obj) {
            return !(obj instanceof b) ? false : TextUtils.equals(((b) obj).b.h, this.b.h);
        }

        public int hashCode() {
            return this.b.h.hashCode();
        }
    }

    public class c extends g {
        final /* synthetic */ XMPushService b;

        c(XMPushService xMPushService) {
            this.b = xMPushService;
            super(1);
        }

        public void a() {
            if (this.b.a()) {
                this.b.o();
            } else {
                com.xiaomi.channel.commonutils.logger.b.a("should not connect. quit the job.");
            }
        }

        public String b() {
            return "do reconnect..";
        }
    }

    class d extends BroadcastReceiver {
        final /* synthetic */ XMPushService a;

        d(XMPushService xMPushService) {
            this.a = xMPushService;
        }

        public void onReceive(Context context, Intent intent) {
            this.a.k();
        }
    }

    public class e extends g {
        public int b;
        public Exception c;
        final /* synthetic */ XMPushService d;

        e(XMPushService xMPushService, int i, Exception exception) {
            this.d = xMPushService;
            super(2);
            this.b = i;
            this.c = exception;
        }

        public void a() {
            this.d.a(this.b, this.c);
        }

        public String b() {
            return "disconnect the connection.";
        }
    }

    class f extends g {
        final /* synthetic */ XMPushService b;
        private Intent c = null;

        public f(XMPushService xMPushService, Intent intent) {
            this.b = xMPushService;
            super(15);
            this.c = intent;
        }

        public void a() {
            this.b.a(this.c);
        }

        public String b() {
            return "Handle intent action = " + this.c.getAction();
        }
    }

    class h extends g {
        final /* synthetic */ XMPushService b;

        public h(XMPushService xMPushService) {
            this.b = xMPushService;
            super(5);
        }

        public void a() {
            this.b.k.b();
        }

        public String b() {
            return "ask the job queue to quit";
        }
    }

    public class i extends Binder {
        final /* synthetic */ XMPushService a;

        public i(XMPushService xMPushService) {
            this.a = xMPushService;
        }
    }

    class j extends g {
        final /* synthetic */ XMPushService b;
        private com.xiaomi.smack.packet.d c = null;

        public j(XMPushService xMPushService, com.xiaomi.smack.packet.d dVar) {
            this.b = xMPushService;
            super(8);
            this.c = dVar;
        }

        public void a() {
            this.b.j.a(this.c);
        }

        public String b() {
            return "receive a_isRightVersion message.";
        }
    }

    class k extends g {
        final /* synthetic */ XMPushService b;

        public k(XMPushService xMPushService) {
            this.b = xMPushService;
            super(4);
        }

        public void a() {
            if (this.b.e()) {
                try {
                    com.xiaomi.stats.g.a();
                    this.b.h.n();
                } catch (Exception e) {
                    com.xiaomi.channel.commonutils.logger.b.a((Throwable) e);
                    this.b.a(10, e);
                }
            }
        }

        public String b() {
            return "send ping..";
        }
    }

    class l extends g {
        com.xiaomi.push.service.x.b b = null;
        final /* synthetic */ XMPushService c;

        public l(XMPushService xMPushService, com.xiaomi.push.service.x.b bVar) {
            this.c = xMPushService;
            super(4);
            this.b = bVar;
        }

        public void a() {
            try {
                this.b.a(com.xiaomi.push.service.x.c.unbind, 1, 16, null, null);
                this.c.h.a(this.b.h, this.b.b);
                this.b.a(com.xiaomi.push.service.x.c.binding, 1, 16, null, null);
                this.c.h.a(this.b);
            } catch (Exception e) {
                com.xiaomi.channel.commonutils.logger.b.a((Throwable) e);
                this.c.a(10, e);
            }
        }

        public String b() {
            return "rebind the client. " + this.b.h;
        }
    }

    class m extends g {
        final /* synthetic */ XMPushService b;

        m(XMPushService xMPushService) {
            this.b = xMPushService;
            super(3);
        }

        public void a() {
            this.b.a(11, null);
            if (this.b.a()) {
                this.b.o();
            }
        }

        public String b() {
            return "reset the connection.";
        }
    }

    class n extends g {
        com.xiaomi.push.service.x.b b = null;
        int c;
        String d;
        String e;
        final /* synthetic */ XMPushService f;

        public n(XMPushService xMPushService, com.xiaomi.push.service.x.b bVar, int i, String str, String str2) {
            this.f = xMPushService;
            super(9);
            this.b = bVar;
            this.c = i;
            this.d = str;
            this.e = str2;
        }

        public void a() {
            if (!(this.b.m == com.xiaomi.push.service.x.c.unbind || this.f.h == null)) {
                try {
                    this.f.h.a(this.b.h, this.b.b);
                } catch (Exception e) {
                    com.xiaomi.channel.commonutils.logger.b.a((Throwable) e);
                    this.f.a(10, e);
                }
            }
            this.b.a(com.xiaomi.push.service.x.c.unbind, this.c, 0, this.e, this.d);
        }

        public String b() {
            return "unbind the channel. " + this.b.h;
        }
    }

    static {
        HostManager.addReservedHost("app.chat.xiaomi.net", "app.chat.xiaomi.net");
        HostManager.addReservedHost("app.chat.xiaomi.net", "42.62.94.2:443");
        HostManager.addReservedHost("app.chat.xiaomi.net", "114.54.23.2");
        HostManager.addReservedHost("app.chat.xiaomi.net", "111.13.142.2");
        HostManager.addReservedHost("app.chat.xiaomi.net", "111.206.200.2");
        com.xiaomi.smack.l.a = true;
    }

    @TargetApi(11)
    public static Notification a(Context context) {
        Intent intent = new Intent(context, XMPushService.class);
        if (VERSION.SDK_INT >= 11) {
            Builder builder = new Builder(context);
            builder.setSmallIcon(context.getApplicationInfo().icon);
            builder.setContentTitle("Push Service");
            builder.setContentText("Push Service");
            builder.setContentIntent(PendingIntent.getActivity(context, 0, intent, 0));
            return builder.getNotification();
        }
        Notification notification = new Notification();
        notification.setLatestEventInfo(context, "Push Service", "Push Service", PendingIntent.getService(context, 0, intent, 0));
        return notification;
    }

    public static com.xiaomi.smack.packet.c a(f fVar, Context context, o oVar) {
        try {
            com.xiaomi.smack.packet.c cVar = new com.xiaomi.smack.packet.c();
            cVar.l("5");
            cVar.m("xiaomi.com");
            cVar.n(fVar.a);
            cVar.b(true);
            cVar.f("push");
            cVar.o(oVar.f);
            String str = fVar.a;
            oVar.g.b = str.substring(0, str.indexOf("@"));
            oVar.g.d = str.substring(str.indexOf("/") + 1);
            String valueOf = String.valueOf(com.xiaomi.channel.commonutils.string.a.a(ad.a(ad.a(fVar.c, cVar.k()), ad.a(oVar))));
            com.xiaomi.smack.packet.a aVar = new com.xiaomi.smack.packet.a("s", null, (String[]) null, (String[]) null);
            aVar.b(valueOf);
            cVar.a(aVar);
            com.xiaomi.channel.commonutils.logger.b.a("try send mi push message. packagename:" + oVar.f + " action:" + oVar.a);
            return cVar;
        } catch (Throwable e) {
            com.xiaomi.channel.commonutils.logger.b.a(e);
            return null;
        }
    }

    private com.xiaomi.smack.packet.c a(com.xiaomi.smack.packet.c cVar, String str) {
        byte[] a = ad.a(str, cVar.k());
        com.xiaomi.smack.packet.c cVar2 = new com.xiaomi.smack.packet.c();
        cVar2.n(cVar.n());
        cVar2.m(cVar.m());
        cVar2.k(cVar.k());
        cVar2.l(cVar.l());
        cVar2.b(true);
        String a2 = ad.a(a, com.xiaomi.smack.util.g.c(cVar.a()));
        com.xiaomi.smack.packet.a aVar = new com.xiaomi.smack.packet.a("s", null, (String[]) null, (String[]) null);
        aVar.b(a2);
        cVar2.a(aVar);
        return cVar2;
    }

    private com.xiaomi.smack.packet.d a(com.xiaomi.smack.packet.d dVar, String str, String str2, boolean z) {
        x a = x.a();
        List b = a.b(str);
        if (b.isEmpty()) {
            com.xiaomi.channel.commonutils.logger.b.a("open channel should be called first before sending a_isRightVersion packet, pkg=" + str);
        } else {
            dVar.o(str);
            String l = dVar.l();
            if (TextUtils.isEmpty(l)) {
                l = (String) b.get(0);
                dVar.l(l);
            }
            com.xiaomi.push.service.x.b b2 = a.b(l, dVar.n());
            if (!e()) {
                com.xiaomi.channel.commonutils.logger.b.a("drop a_isRightVersion packet as the channel is not connected, chid=" + l);
            } else if (b2 == null || b2.m != com.xiaomi.push.service.x.c.binded) {
                com.xiaomi.channel.commonutils.logger.b.a("drop a_isRightVersion packet as the channel is not opened, chid=" + l);
            } else if (TextUtils.equals(str2, b2.j)) {
                return ((dVar instanceof com.xiaomi.smack.packet.c) && z) ? a((com.xiaomi.smack.packet.c) dVar, b2.i) : dVar;
            } else {
                com.xiaomi.channel.commonutils.logger.b.a("invalid session. " + str2);
            }
        }
        return null;
    }

    public static <T extends org.apache.thrift.b<T, ?>> o a(String str, String str2, T t, com.xiaomi.xmpush.thrift.a aVar) {
        byte[] a = ad.a(t);
        o oVar = new o();
        com.xiaomi.xmpush.thrift.j jVar = new com.xiaomi.xmpush.thrift.j();
        jVar.a = 5;
        jVar.b = "fakeid";
        oVar.a(jVar);
        oVar.a(ByteBuffer.wrap(a));
        oVar.a(aVar);
        oVar.c(true);
        oVar.b(str);
        oVar.a(false);
        oVar.a(str2);
        return oVar;
    }

    private String a(String str) {
        return "<iq id='0' chid='0' type='get'><ping>%1$s%2$s</ping></iq>";
    }

    private void a(Intent intent) {
        com.xiaomi.push.service.x.b bVar = null;
        boolean z = true;
        boolean z2 = false;
        x a = x.a();
        String stringExtra;
        if (z.d.equalsIgnoreCase(intent.getAction()) || z.j.equalsIgnoreCase(intent.getAction())) {
            stringExtra = intent.getStringExtra(z.q);
            if (TextUtils.isEmpty(intent.getStringExtra(z.u))) {
                com.xiaomi.channel.commonutils.logger.b.a("security is empty. ignore.");
            } else if (stringExtra != null) {
                boolean a2 = a(stringExtra, intent);
                com.xiaomi.push.service.x.b b = b(stringExtra, intent);
                if (!com.xiaomi.channel.commonutils.network.d.d(this)) {
                    this.i.a(this, b, false, 2, null);
                } else if (!e()) {
                    a(true);
                } else if (b.m == com.xiaomi.push.service.x.c.unbind) {
                    c(new a(this, b));
                } else if (a2) {
                    c(new l(this, b));
                } else if (b.m == com.xiaomi.push.service.x.c.binding) {
                    com.xiaomi.channel.commonutils.logger.b.a(String.format("the client is binding. %1$s %2$s.", new Object[]{b.h, b.b}));
                } else if (b.m == com.xiaomi.push.service.x.c.binded) {
                    this.i.a(this, b, true, 0, null);
                }
            } else {
                com.xiaomi.channel.commonutils.logger.b.d("channel id is empty, do nothing!");
            }
        } else if (z.i.equalsIgnoreCase(intent.getAction())) {
            stringExtra = intent.getStringExtra(z.y);
            r2 = intent.getStringExtra(z.q);
            Object stringExtra2 = intent.getStringExtra(z.p);
            com.xiaomi.channel.commonutils.logger.b.a("Service called closechannel chid = " + r2 + " userId = " + stringExtra2);
            if (TextUtils.isEmpty(r2)) {
                for (String stringExtra3 : a.b(stringExtra3)) {
                    a(stringExtra3, 2);
                }
            } else if (TextUtils.isEmpty(stringExtra2)) {
                a(r2, 2);
            } else {
                a(r2, stringExtra2, 2, null, null);
            }
        } else if (z.e.equalsIgnoreCase(intent.getAction())) {
            stringExtra3 = intent.getStringExtra(z.y);
            r1 = intent.getStringExtra(z.B);
            Bundle bundleExtra = intent.getBundleExtra("ext_packet");
            com.xiaomi.smack.packet.d a3 = a(new com.xiaomi.smack.packet.c(bundleExtra), stringExtra3, r1, intent.getBooleanExtra("ext_encrypt", true));
            if (a3 != null) {
                c(new af(this, a3));
            }
        } else if (z.g.equalsIgnoreCase(intent.getAction())) {
            r1 = intent.getStringExtra(z.y);
            r2 = intent.getStringExtra(z.B);
            Parcelable[] parcelableArrayExtra = intent.getParcelableArrayExtra("ext_packets");
            com.xiaomi.smack.packet.c[] cVarArr = new com.xiaomi.smack.packet.c[parcelableArrayExtra.length];
            boolean booleanExtra = intent.getBooleanExtra("ext_encrypt", true);
            while (r3 < parcelableArrayExtra.length) {
                cVarArr[r3] = new com.xiaomi.smack.packet.c((Bundle) parcelableArrayExtra[r3]);
                cVarArr[r3] = (com.xiaomi.smack.packet.c) a(cVarArr[r3], r1, r2, booleanExtra);
                if (cVarArr[r3] != null) {
                    r3++;
                } else {
                    return;
                }
            }
            c(new a(this, cVarArr));
        } else if (z.f.equalsIgnoreCase(intent.getAction())) {
            stringExtra3 = intent.getStringExtra(z.y);
            r1 = intent.getStringExtra(z.B);
            r4 = new com.xiaomi.smack.packet.b(intent.getBundleExtra("ext_packet"));
            if (a(r4, stringExtra3, r1, false) != null) {
                c(new af(this, r4));
            }
        } else if (z.h.equalsIgnoreCase(intent.getAction())) {
            stringExtra3 = intent.getStringExtra(z.y);
            r1 = intent.getStringExtra(z.B);
            r4 = new com.xiaomi.smack.packet.f(intent.getBundleExtra("ext_packet"));
            if (a(r4, stringExtra3, r1, false) != null) {
                c(new af(this, r4));
            }
        } else if (z.k.equals(intent.getAction())) {
            stringExtra3 = intent.getStringExtra(z.q);
            r1 = intent.getStringExtra(z.p);
            if (stringExtra3 != null) {
                com.xiaomi.channel.commonutils.logger.b.a("request reset connection from chid = " + stringExtra3);
                com.xiaomi.push.service.x.b b2 = x.a().b(stringExtra3, r1);
                if (b2 != null && b2.i.equals(intent.getStringExtra(z.u)) && b2.m == com.xiaomi.push.service.x.c.binded) {
                    com.xiaomi.smack.a g = g();
                    if (g == null || !g.a(System.currentTimeMillis() - AbstractTrafficShapingHandler.DEFAULT_MAX_TIME)) {
                        c(new m(this));
                    }
                }
            }
        } else if (z.l.equals(intent.getAction())) {
            stringExtra3 = intent.getStringExtra(z.y);
            List b3 = a.b(stringExtra3);
            if (b3.isEmpty()) {
                com.xiaomi.channel.commonutils.logger.b.a("open channel should be called first before update info, pkg=" + stringExtra3);
                return;
            }
            stringExtra3 = intent.getStringExtra(z.q);
            Object stringExtra4 = intent.getStringExtra(z.p);
            if (TextUtils.isEmpty(stringExtra3)) {
                stringExtra3 = (String) b3.get(0);
            }
            if (TextUtils.isEmpty(stringExtra4)) {
                r0 = a.c(stringExtra3);
                if (!(r0 == null || r0.isEmpty())) {
                    bVar = (com.xiaomi.push.service.x.b) r0.iterator().next();
                }
            } else {
                bVar = a.b(stringExtra3, stringExtra4);
            }
            if (bVar != null) {
                if (intent.hasExtra(z.w)) {
                    bVar.f = intent.getStringExtra(z.w);
                }
                if (intent.hasExtra(z.x)) {
                    bVar.g = intent.getStringExtra(z.x);
                }
            }
        } else if ("com.xiaomi.mipush.REGISTER_APP".equals(intent.getAction())) {
            if (ab.a(getApplicationContext()).a() && ab.a(getApplicationContext()).b() == 0) {
                com.xiaomi.channel.commonutils.logger.b.a("register without being provisioned. " + intent.getStringExtra("mipush_app_package"));
                return;
            }
            byte[] byteArrayExtra = intent.getByteArrayExtra("mipush_payload");
            String stringExtra5 = intent.getStringExtra("mipush_app_package");
            boolean booleanExtra2 = intent.getBooleanExtra("mipush_env_chanage", false);
            r3 = intent.getIntExtra("mipush_env_type", 1);
            h.a((Context) this).c(stringExtra5);
            if (!booleanExtra2 || "com.xiaomi.xmsf".equals(getPackageName())) {
                a(byteArrayExtra, stringExtra5);
            } else {
                c(new ao(this, 14, r3, byteArrayExtra, stringExtra5));
            }
        } else if ("com.xiaomi.mipush.SEND_MESSAGE".equals(intent.getAction()) || "com.xiaomi.mipush.UNREGISTER_APP".equals(intent.getAction())) {
            r1 = intent.getStringExtra("mipush_app_package");
            byte[] byteArrayExtra2 = intent.getByteArrayExtra("mipush_payload");
            z2 = intent.getBooleanExtra("com.xiaomi.mipush.MESSAGE_CACHE", true);
            r0 = x.a().c("5");
            if ("com.xiaomi.mipush.UNREGISTER_APP".equals(intent.getAction())) {
                h.a((Context) this).b(r1);
            }
            if (r0.isEmpty()) {
                if (z2) {
                    j.b(r1, byteArrayExtra2);
                }
            } else if (((com.xiaomi.push.service.x.b) r0.iterator().next()).m == com.xiaomi.push.service.x.c.binded) {
                c(new ap(this, 4, r1, byteArrayExtra2));
            } else if (z2) {
                j.b(r1, byteArrayExtra2);
            }
        } else if (ac.a.equals(intent.getAction())) {
            stringExtra3 = intent.getStringExtra("uninstall_pkg_name");
            if (stringExtra3 != null && !TextUtils.isEmpty(stringExtra3.trim())) {
                try {
                    getPackageManager().getPackageInfo(stringExtra3, 8192);
                    z = false;
                } catch (NameNotFoundException e) {
                }
                if ("com.xiaomi.channel".equals(stringExtra3) && !x.a().c("1").isEmpty() && r9) {
                    a("1", 0);
                    com.xiaomi.channel.commonutils.logger.b.a("close the miliao channel as the app is uninstalled.");
                    return;
                }
                SharedPreferences sharedPreferences = getSharedPreferences("pref_registered_pkg_names", 0);
                r2 = sharedPreferences.getString(stringExtra3, null);
                if (!TextUtils.isEmpty(r2) && r9) {
                    Editor edit = sharedPreferences.edit();
                    edit.remove(stringExtra3);
                    edit.commit();
                    if (r.e(this, stringExtra3)) {
                        r.d(this, stringExtra3);
                    }
                    r.b(this, stringExtra3);
                    if (e() && r2 != null) {
                        try {
                            a(a(stringExtra3, r2));
                            com.xiaomi.channel.commonutils.logger.b.a("uninstall " + stringExtra3 + " msg sent");
                        } catch (Exception e2) {
                            com.xiaomi.channel.commonutils.logger.b.d("Fail to send Message: " + e2.getMessage());
                            a(10, e2);
                        }
                    }
                }
            }
        } else if ("com.xiaomi.mipush.CLEAR_NOTIFICATION".equals(intent.getAction())) {
            stringExtra3 = intent.getStringExtra(z.y);
            r1 = intent.getIntExtra(z.z, 0);
            if (!TextUtils.isEmpty(stringExtra3)) {
                if (r1 >= 0) {
                    r.a((Context) this, stringExtra3, r1);
                } else if (r1 == -1) {
                    r.b(this, stringExtra3);
                }
            }
        } else if ("com.xiaomi.mipush.SET_NOTIFICATION_TYPE".equals(intent.getAction())) {
            r2 = intent.getStringExtra(z.y);
            CharSequence stringExtra6 = intent.getStringExtra(z.C);
            CharSequence b4;
            if (intent.hasExtra(z.A)) {
                r1 = intent.getIntExtra(z.A, 0);
                b4 = com.xiaomi.channel.commonutils.string.c.b(r2 + r1);
            } else {
                b4 = com.xiaomi.channel.commonutils.string.c.b(r2);
                r1 = 0;
                z2 = true;
            }
            if (TextUtils.isEmpty(r2) || !TextUtils.equals(stringExtra6, r0)) {
                com.xiaomi.channel.commonutils.logger.b.d("invalid notification for " + r2);
            } else if (z2) {
                r.d(this, r2);
            } else {
                r.b((Context) this, r2, r1);
            }
        }
    }

    private void a(String str, int i) {
        Collection<com.xiaomi.push.service.x.b> c = x.a().c(str);
        if (c != null) {
            for (com.xiaomi.push.service.x.b bVar : c) {
                if (bVar != null) {
                    a(new n(this, bVar, i, null, null));
                }
            }
        }
        x.a().a(str);
    }

    private boolean a(String str, Intent intent) {
        com.xiaomi.push.service.x.b b = x.a().b(str, intent.getStringExtra(z.p));
        boolean z = false;
        if (b == null || str == null) {
            return false;
        }
        Object stringExtra = intent.getStringExtra(z.B);
        String stringExtra2 = intent.getStringExtra(z.u);
        if (!(TextUtils.isEmpty(b.j) || TextUtils.equals(stringExtra, b.j))) {
            com.xiaomi.channel.commonutils.logger.b.a("session changed. old session=" + b.j + ", new session=" + stringExtra + " chid = " + str);
            z = true;
        }
        if (stringExtra2.equals(b.i)) {
            return z;
        }
        com.xiaomi.channel.commonutils.logger.b.a("security changed. chid = " + str + " sechash = " + com.xiaomi.channel.commonutils.string.c.a(stringExtra2));
        return true;
    }

    private com.xiaomi.push.service.x.b b(String str, Intent intent) {
        com.xiaomi.push.service.x.b b = x.a().b(str, intent.getStringExtra(z.p));
        if (b == null) {
            b = new com.xiaomi.push.service.x.b(this);
        }
        b.h = intent.getStringExtra(z.q);
        b.b = intent.getStringExtra(z.p);
        b.c = intent.getStringExtra(z.s);
        b.a = intent.getStringExtra(z.y);
        b.f = intent.getStringExtra(z.w);
        b.g = intent.getStringExtra(z.x);
        b.e = intent.getBooleanExtra(z.v, false);
        b.i = intent.getStringExtra(z.u);
        b.j = intent.getStringExtra(z.B);
        b.d = intent.getStringExtra(z.t);
        b.k = this.i;
        b.l = getApplicationContext();
        x.a().a(b);
        return b;
    }

    private void c(g gVar) {
        this.k.a((com.xiaomi.push.service.e.b) gVar);
    }

    private void j() {
        if (g.a(getApplicationContext()) != null) {
            com.xiaomi.push.service.x.b a = g.a(getApplicationContext()).a(this);
            a(a);
            x.a().a(a);
            if (com.xiaomi.channel.commonutils.network.d.d(getApplicationContext())) {
                a(true);
            }
        }
    }

    private void k() {
        NetworkInfo activeNetworkInfo;
        try {
            activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Throwable e) {
            com.xiaomi.channel.commonutils.logger.b.a(e);
            activeNetworkInfo = null;
        }
        if (activeNetworkInfo != null) {
            com.xiaomi.channel.commonutils.logger.b.a("network changed, " + activeNetworkInfo.toString());
        } else {
            com.xiaomi.channel.commonutils.logger.b.a("network changed, no active network");
        }
        if (com.xiaomi.stats.e.b() != null) {
            com.xiaomi.stats.e.b().b();
        }
        this.g.r();
        if (com.xiaomi.channel.commonutils.network.d.d(this)) {
            if (e() && l()) {
                m();
            }
            if (!(e() || f())) {
                this.k.b(1);
                a(new c(this));
            }
            com.xiaomi.push.log.b.a((Context) this).a();
        } else {
            a(new e(this, 2, null));
        }
        n();
    }

    private boolean l() {
        return System.currentTimeMillis() - this.e >= com.MCWorld.video.recorder.b.bpg;
    }

    private void m() {
        this.e = System.currentTimeMillis();
        if (this.k.d()) {
            com.xiaomi.channel.commonutils.logger.b.d("ERROR, the job controller is blocked.");
            x.a().a((Context) this, 14);
            stopSelf();
        } else if (!e()) {
            a(true);
        } else if (this.h.q() || com.xiaomi.channel.commonutils.network.d.e(this)) {
            a(new k(this));
        } else {
            a(new e(this, 17, null));
            a(true);
        }
    }

    private void n() {
        if (!a()) {
            com.xiaomi.push.service.timers.a.a();
        } else if (!com.xiaomi.push.service.timers.a.b()) {
            com.xiaomi.push.service.timers.a.a(true);
        }
    }

    private void o() {
        if (this.h != null && this.h.h()) {
            com.xiaomi.channel.commonutils.logger.b.d("try to connect while connecting.");
        } else if (this.h == null || !this.h.i()) {
            this.b.b(com.xiaomi.channel.commonutils.network.d.f(this));
            p();
            if (this.h == null) {
                x.a().a((Context) this);
                sendBroadcast(new Intent("miui.intent.action.NETWORK_BLOCKED"));
                return;
            }
            sendBroadcast(new Intent("miui.intent.action.NETWORK_CONNECTED"));
        } else {
            com.xiaomi.channel.commonutils.logger.b.d("try to connect while is connected.");
        }
    }

    private void p() {
        try {
            this.g.a(this.l, new as(this));
            this.g.t();
            this.h = this.g;
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.a("fail to create xmpp connection", (Throwable) e);
            this.g.a(new com.xiaomi.smack.packet.f(com.xiaomi.smack.packet.f.b.unavailable), 3, e);
        }
    }

    private void q() {
        if (VERSION.SDK_INT < 18) {
            startForeground(f, new Notification());
        } else {
            bindService(new Intent(this, XMJobService.class), new ak(this), 1);
        }
    }

    public com.xiaomi.smack.l a(com.xiaomi.smack.b bVar) {
        return new com.xiaomi.smack.l(this, bVar);
    }

    public com.xiaomi.smack.packet.c a(byte[] bArr) {
        o oVar = new o();
        try {
            ad.a((org.apache.thrift.b) oVar, bArr);
            return a(g.a(this), (Context) this, oVar);
        } catch (Throwable e) {
            com.xiaomi.channel.commonutils.logger.b.a(e);
            return null;
        }
    }

    public o a(String str, String str2) {
        org.apache.thrift.b rVar = new r();
        rVar.b(str2);
        rVar.c("app_uninstalled");
        rVar.a(com.xiaomi.smack.packet.d.j());
        rVar.a(false);
        return a(str, str2, rVar, com.xiaomi.xmpush.thrift.a.Notification);
    }

    public void a(int i) {
        this.k.b(i);
    }

    public void a(int i, Exception exception) {
        com.xiaomi.channel.commonutils.logger.b.a("disconnect " + hashCode() + ", " + (this.h == null ? null : Integer.valueOf(this.h.hashCode())));
        if (this.h != null) {
            this.h.a(new com.xiaomi.smack.packet.f(com.xiaomi.smack.packet.f.b.unavailable), i, exception);
            this.h = null;
        }
        a(7);
        a(4);
        x.a().a((Context) this, i);
    }

    public void a(g gVar) {
        a(gVar, 0);
    }

    public void a(g gVar, long j) {
        this.k.a((com.xiaomi.push.service.e.b) gVar, j);
    }

    public void a(com.xiaomi.push.service.x.b bVar) {
        bVar.a(new aq(this));
    }

    public void a(com.xiaomi.smack.a aVar) {
        this.c.a();
        Iterator it = x.a().b().iterator();
        while (it.hasNext()) {
            a(new a(this, (com.xiaomi.push.service.x.b) it.next()));
        }
    }

    public void a(com.xiaomi.smack.a aVar, int i, Exception exception) {
        a(false);
    }

    public void a(com.xiaomi.smack.a aVar, Exception exception) {
        a(false);
    }

    public void a(com.xiaomi.smack.packet.d dVar) {
        if (this.h != null) {
            this.h.a(dVar);
            return;
        }
        throw new p("try send msg while connection is null.");
    }

    public void a(o oVar) {
        if (this.h != null) {
            com.xiaomi.smack.packet.d a = a(g.a(this), (Context) this, oVar);
            if (a != null) {
                this.h.a(a);
                return;
            }
            return;
        }
        throw new p("try send msg while connection is null.");
    }

    public void a(String str, String str2, int i, String str3, String str4) {
        com.xiaomi.push.service.x.b b = x.a().b(str, str2);
        if (b != null) {
            a(new n(this, b, i, str4, str3));
        }
        x.a().a(str, str2);
    }

    public void a(String str, byte[] bArr) {
        if (this.h != null) {
            com.xiaomi.smack.packet.d a = a(bArr);
            if (a != null) {
                this.h.a(a);
                return;
            } else {
                j.a(this, str, bArr, ErrorCode.ERROR_INVALID_PAYLOAD, "not a_isRightVersion valid message");
                return;
            }
        }
        throw new p("try send msg while connection is null.");
    }

    public void a(boolean z) {
        this.c.a(z);
    }

    public void a(byte[] bArr, String str) {
        if (bArr == null) {
            j.a(this, str, bArr, ErrorCode.ERROR_INVALID_PAYLOAD, "null payload");
            com.xiaomi.channel.commonutils.logger.b.a("register request without payload");
            return;
        }
        org.apache.thrift.b oVar = new o();
        try {
            ad.a(oVar, bArr);
            if (oVar.a == com.xiaomi.xmpush.thrift.a.Registration) {
                org.apache.thrift.b sVar = new s();
                try {
                    ad.a(sVar, oVar.f());
                    j.a(oVar.j(), bArr);
                    a(new i(this, oVar.j(), sVar.d(), sVar.h(), bArr));
                    return;
                } catch (Throwable e) {
                    com.xiaomi.channel.commonutils.logger.b.a(e);
                    j.a(this, str, bArr, ErrorCode.ERROR_INVALID_PAYLOAD, " data action error.");
                    return;
                }
            }
            j.a(this, str, bArr, ErrorCode.ERROR_INVALID_PAYLOAD, " registration action required.");
            com.xiaomi.channel.commonutils.logger.b.a("register request with invalid payload");
        } catch (Throwable e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            j.a(this, str, bArr, ErrorCode.ERROR_INVALID_PAYLOAD, " data container error.");
        }
    }

    public void a(com.xiaomi.smack.packet.d[] dVarArr) {
        if (this.h != null) {
            this.h.a(dVarArr);
            return;
        }
        throw new p("try send msg while connection is null.");
    }

    public boolean a() {
        return com.xiaomi.channel.commonutils.network.d.d(this) && x.a().c() > 0 && !b();
    }

    public void b(g gVar) {
        this.k.a(gVar.a, (com.xiaomi.push.service.e.b) gVar);
    }

    public void b(com.xiaomi.push.service.x.b bVar) {
        if (bVar != null) {
            long a = bVar.a();
            com.xiaomi.channel.commonutils.logger.b.a("schedule rebind job in " + (a / 1000));
            a(new a(this, bVar), a);
        }
    }

    public void b(com.xiaomi.smack.a aVar) {
        com.xiaomi.channel.commonutils.logger.b.c("begin to connect...");
    }

    public boolean b() {
        try {
            Class cls = Class.forName("miui.os.Build");
            return cls.getField("IS_CM_CUSTOMIZATION_TEST").getBoolean(null) || cls.getField("IS_CU_CUSTOMIZATION_TEST").getBoolean(null);
        } catch (Throwable th) {
            return false;
        }
    }

    public boolean b(int i) {
        return this.k.a(i);
    }

    public b c() {
        return new b();
    }

    public b d() {
        return this.i;
    }

    public boolean e() {
        return this.h != null && this.h.i();
    }

    public boolean f() {
        return this.h != null && this.h.h();
    }

    public com.xiaomi.smack.a g() {
        return this.h;
    }

    public void h() {
        a(new aj(this, 10), (long) AbstractTrafficShapingHandler.DEFAULT_MAX_TIME);
    }

    public IBinder onBind(Intent intent) {
        return new i(this);
    }

    public void onCreate() {
        super.onCreate();
        com.xiaomi.smack.util.h.a(this);
        f a = g.a(this);
        if (a != null) {
            com.xiaomi.channel.commonutils.misc.a.a(a.g);
        }
        aa.a(this);
        this.b = new al(this, null, 5222, "xiaomi.com", null);
        this.b.a(true);
        this.g = a(this.b);
        this.g.b(a("xiaomi.com"));
        Fallback fallback = new Fallback("mibind.chat.gslb.mi-idc.com");
        this.i = c();
        try {
            if (TextUtils.equals((String) Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class}).invoke(null, new Object[]{"sys.boot_completed"}), "1")) {
                this.i.a((Context) this);
            }
        } catch (Throwable e) {
            com.xiaomi.channel.commonutils.logger.b.a(e);
        }
        com.xiaomi.push.service.timers.a.a((Context) this);
        this.g.a((com.xiaomi.smack.d) this);
        this.j = new PacketSync(this);
        this.c = new ae(this);
        new c().a();
        this.k = new e("Connection Controller Thread");
        a(new am(this, 11));
        x a2 = x.a();
        a2.e();
        a2.a(new an(this));
        this.d = new d(this);
        registerReceiver(this.d, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        if (!TextUtils.equals(getPackageName(), "com.xiaomi.xmsf")) {
            q();
        }
        com.xiaomi.channel.commonutils.logger.b.a("XMPushService created pid = " + f);
    }

    public void onDestroy() {
        unregisterReceiver(this.d);
        this.k.c();
        a(new ar(this, 2));
        a(new h(this));
        x.a().e();
        x.a().a((Context) this, 15);
        x.a().d();
        this.g.b((com.xiaomi.smack.d) this);
        ag.a().b();
        com.xiaomi.push.service.timers.a.a();
        super.onDestroy();
        com.xiaomi.channel.commonutils.logger.b.a("Service destroyed");
    }

    public void onStart(Intent intent, int i) {
        if (intent == null) {
            com.xiaomi.channel.commonutils.logger.b.d("onStart() with intent NULL");
        } else {
            com.xiaomi.channel.commonutils.logger.b.c(String.format("onStart() with intent.Action = %s, chid = %s", new Object[]{intent.getAction(), intent.getStringExtra(z.q)}));
        }
        if (intent != null && intent.getAction() != null) {
            if ("com.xiaomi.push.timer".equalsIgnoreCase(intent.getAction())) {
                com.xiaomi.channel.commonutils.logger.b.a("Service called on timer");
                m();
            } else if ("com.xiaomi.push.check_alive".equalsIgnoreCase(intent.getAction())) {
                com.xiaomi.channel.commonutils.logger.b.a("Service called on check alive.");
                if (l()) {
                    m();
                }
            } else if (!"com.xiaomi.push.network_status_changed".equalsIgnoreCase(intent.getAction())) {
                a(new f(this, intent));
            }
        }
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        onStart(intent, i2);
        return a;
    }
}
