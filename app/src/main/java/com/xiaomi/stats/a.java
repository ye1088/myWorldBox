package com.xiaomi.stats;

import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.x.b;
import com.xiaomi.push.service.x.c;

class a implements com.xiaomi.push.service.x.b.a {
    private XMPushService a;
    private b b;
    private com.xiaomi.smack.a c;
    private c d;
    private int e;
    private boolean f = false;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[c.values().length];

        static {
            try {
                a[c.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[c.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[c.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    a(XMPushService xMPushService, b bVar) {
        this.a = xMPushService;
        this.d = c.b;
        this.b = bVar;
    }

    private void b() {
        this.b.b(this);
    }

    private void c() {
        b();
        if (this.f && this.e != 11) {
            com.xiaomi.push.thrift.b f = e.a().f();
            switch (AnonymousClass1.a[this.d.ordinal()]) {
                case 1:
                    if (this.e != 17) {
                        if (this.e != 21) {
                            try {
                                a c = c.c(e.b().a());
                                f.b = c.a.a();
                                f.c(c.b);
                                break;
                            } catch (NullPointerException e) {
                                f = null;
                                break;
                            }
                        }
                        f.b = com.xiaomi.push.thrift.a.P.a();
                        break;
                    }
                    f.b = com.xiaomi.push.thrift.a.I.a();
                    break;
                case 3:
                    f.b = com.xiaomi.push.thrift.a.E.a();
                    break;
            }
            if (f != null) {
                f.b(this.c.c());
                f.d(this.b.b);
                f.c = 1;
                try {
                    f.a((byte) Integer.parseInt(this.b.h));
                } catch (NumberFormatException e2) {
                }
                e.a().a(f);
            }
        }
    }

    void a() {
        this.b.a(this);
        this.c = this.a.g();
    }

    public void a(c cVar, c cVar2, int i) {
        if (!this.f && cVar == c.b) {
            this.d = cVar2;
            this.e = i;
            this.f = true;
        }
        this.a.a(new b(this, 4));
    }
}
