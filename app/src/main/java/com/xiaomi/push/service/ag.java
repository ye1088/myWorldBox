package com.xiaomi.push.service;

import android.content.SharedPreferences;
import com.xiaomi.channel.commonutils.misc.f.b;
import com.xiaomi.smack.util.h;
import com.xiaomi.smack.util.i;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ag {
    private static String a;
    private static ag e = new ag();
    private List<a> b = new ArrayList();
    private com.xiaomi.push.protobuf.a.a c;
    private b d;

    public static abstract class a {
        public void a(com.xiaomi.push.protobuf.a.a aVar) {
        }

        public void a(com.xiaomi.push.protobuf.b.a aVar) {
        }
    }

    static {
        e.g();
    }

    private ag() {
    }

    public static ag a() {
        return e;
    }

    public static synchronized String e() {
        String str;
        synchronized (ag.class) {
            if (a == null) {
                SharedPreferences sharedPreferences = h.a().getSharedPreferences("XMPushServiceConfig", 0);
                a = sharedPreferences.getString("DeviceUUID", null);
                if (a == null) {
                    a = h.b();
                    if (a != null) {
                        sharedPreferences.edit().putString("DeviceUUID", a).commit();
                    }
                }
            }
            str = a;
        }
        return str;
    }

    private void f() {
        if (this.d == null) {
            this.d = new ah(this);
            i.a(this.d);
        }
    }

    private void g() {
        Exception exception;
        Throwable th;
        InputStream inputStream = null;
        try {
            InputStream bufferedInputStream;
            if (this.c != null) {
                bufferedInputStream = new BufferedInputStream(h.a().openFileInput("XMCloudCfg"));
                try {
                    this.c = com.xiaomi.push.protobuf.a.a.c(com.google.protobuf.micro.a.a(bufferedInputStream));
                    bufferedInputStream.close();
                } catch (Exception e) {
                    Exception exception2 = e;
                    inputStream = bufferedInputStream;
                    exception = exception2;
                    try {
                        com.xiaomi.channel.commonutils.logger.b.a("save config failure: " + exception.getMessage());
                        com.xiaomi.channel.commonutils.file.a.a(inputStream);
                    } catch (Throwable th2) {
                        th = th2;
                        com.xiaomi.channel.commonutils.file.a.a(inputStream);
                        throw th;
                    }
                } catch (Throwable th3) {
                    Throwable th4 = th3;
                    inputStream = bufferedInputStream;
                    th = th4;
                    com.xiaomi.channel.commonutils.file.a.a(inputStream);
                    throw th;
                }
            }
            bufferedInputStream = null;
            com.xiaomi.channel.commonutils.file.a.a(bufferedInputStream);
        } catch (Exception e2) {
            exception = e2;
            com.xiaomi.channel.commonutils.logger.b.a("save config failure: " + exception.getMessage());
            com.xiaomi.channel.commonutils.file.a.a(inputStream);
        }
    }

    private void h() {
        try {
            if (this.c != null) {
                OutputStream bufferedOutputStream = new BufferedOutputStream(h.a().openFileOutput("XMCloudCfg", 0));
                com.google.protobuf.micro.b a = com.google.protobuf.micro.b.a(bufferedOutputStream);
                this.c.a(a);
                a.a();
                bufferedOutputStream.close();
            }
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.a("save config failure: " + e.getMessage());
        }
    }

    void a(com.xiaomi.push.protobuf.b.a aVar) {
        if (aVar.h() && aVar.g() > c()) {
            f();
        }
        synchronized (this) {
        }
        for (a a : (a[]) this.b.toArray(new a[this.b.size()])) {
            a.a(aVar);
        }
    }

    public synchronized void a(a aVar) {
        this.b.add(aVar);
    }

    synchronized void b() {
        this.b.clear();
    }

    int c() {
        return this.c != null ? this.c.c() : 0;
    }

    public com.xiaomi.push.protobuf.a.a d() {
        return this.c;
    }
}
