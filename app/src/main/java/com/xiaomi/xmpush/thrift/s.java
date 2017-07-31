package com.xiaomi.xmpush.thrift;

import java.io.Serializable;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.thrift.b;
import org.apache.thrift.meta_data.e;
import org.apache.thrift.meta_data.g;
import org.apache.thrift.protocol.c;
import org.apache.thrift.protocol.f;
import org.apache.thrift.protocol.i;
import org.apache.thrift.protocol.k;

public class s implements Serializable, Cloneable, b<s, a> {
    private static final c A = new c("id", (byte) 11, (short) 3);
    private static final c B = new c("appId", (byte) 11, (short) 4);
    private static final c C = new c("appVersion", (byte) 11, (short) 5);
    private static final c D = new c("packageName", (byte) 11, (short) 6);
    private static final c E = new c("token", (byte) 11, (short) 7);
    private static final c F = new c("deviceId", (byte) 11, (short) 8);
    private static final c G = new c("aliasName", (byte) 11, (short) 9);
    private static final c H = new c("sdkVersion", (byte) 11, (short) 10);
    private static final c I = new c("regId", (byte) 11, (short) 11);
    private static final c J = new c("pushSdkVersionName", (byte) 11, (short) 12);
    private static final c K = new c("pushSdkVersionCode", (byte) 8, (short) 13);
    private static final c L = new c("appVersionCode", (byte) 8, (short) 14);
    private static final c M = new c("androidId", (byte) 11, (short) 15);
    private static final c N = new c("imei", (byte) 11, (short) 16);
    private static final c O = new c("serial", (byte) 11, (short) 17);
    private static final c P = new c("imeiMd5", (byte) 11, (short) 18);
    private static final c Q = new c("spaceId", (byte) 8, (short) 19);
    private static final c R = new c("connectionAttrs", (byte) 13, (short) 100);
    private static final c S = new c("cleanOldRegInfo", (byte) 2, (short) 101);
    private static final c T = new c("oldRegId", (byte) 11, (short) 102);
    public static final Map<a, org.apache.thrift.meta_data.b> w;
    private static final k x = new k("XmPushActionRegistration");
    private static final c y = new c("debug", (byte) 11, (short) 1);
    private static final c z = new c("target", (byte) 12, (short) 2);
    private BitSet U = new BitSet(4);
    public String a;
    public j b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;
    public String j;
    public String k;
    public String l;
    public int m;
    public int n;
    public String o;
    public String p;
    public String q;
    public String r;
    public int s;
    public Map<String, String> t;
    public boolean u = false;
    public String v;

    static {
        Map enumMap = new EnumMap(a.class);
        enumMap.put(a.a, new org.apache.thrift.meta_data.b("debug", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.b, new org.apache.thrift.meta_data.b("target", (byte) 2, new g((byte) 12, j.class)));
        enumMap.put(a.c, new org.apache.thrift.meta_data.b("id", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.d, new org.apache.thrift.meta_data.b("appId", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.e, new org.apache.thrift.meta_data.b("appVersion", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.f, new org.apache.thrift.meta_data.b("packageName", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.g, new org.apache.thrift.meta_data.b("token", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.h, new org.apache.thrift.meta_data.b("deviceId", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.i, new org.apache.thrift.meta_data.b("aliasName", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.j, new org.apache.thrift.meta_data.b("sdkVersion", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.k, new org.apache.thrift.meta_data.b("regId", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.l, new org.apache.thrift.meta_data.b("pushSdkVersionName", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.m, new org.apache.thrift.meta_data.b("pushSdkVersionCode", (byte) 2, new org.apache.thrift.meta_data.c((byte) 8)));
        enumMap.put(a.n, new org.apache.thrift.meta_data.b("appVersionCode", (byte) 2, new org.apache.thrift.meta_data.c((byte) 8)));
        enumMap.put(a.o, new org.apache.thrift.meta_data.b("androidId", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.p, new org.apache.thrift.meta_data.b("imei", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.q, new org.apache.thrift.meta_data.b("serial", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.r, new org.apache.thrift.meta_data.b("imeiMd5", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.s, new org.apache.thrift.meta_data.b("spaceId", (byte) 2, new org.apache.thrift.meta_data.c((byte) 8)));
        enumMap.put(a.t, new org.apache.thrift.meta_data.b("connectionAttrs", (byte) 2, new e((byte) 13, new org.apache.thrift.meta_data.c((byte) 11), new org.apache.thrift.meta_data.c((byte) 11))));
        enumMap.put(a.u, new org.apache.thrift.meta_data.b("cleanOldRegInfo", (byte) 2, new org.apache.thrift.meta_data.c((byte) 2)));
        enumMap.put(a.v, new org.apache.thrift.meta_data.b("oldRegId", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        w = Collections.unmodifiableMap(enumMap);
        org.apache.thrift.meta_data.b.a(s.class, w);
    }

    public s a(int i) {
        this.m = i;
        a(true);
        return this;
    }

    public s a(String str) {
        this.c = str;
        return this;
    }

    public void a(f fVar) {
        fVar.g();
        while (true) {
            c i = fVar.i();
            if (i.b == (byte) 0) {
                fVar.h();
                y();
                return;
            }
            switch (i.c) {
                case (short) 1:
                    if (i.b != (byte) 11) {
                        i.a(fVar, i.b);
                        break;
                    } else {
                        this.a = fVar.w();
                        break;
                    }
                case (short) 2:
                    if (i.b != (byte) 12) {
                        i.a(fVar, i.b);
                        break;
                    }
                    this.b = new j();
                    this.b.a(fVar);
                    break;
                case (short) 3:
                    if (i.b != (byte) 11) {
                        i.a(fVar, i.b);
                        break;
                    } else {
                        this.c = fVar.w();
                        break;
                    }
                case (short) 4:
                    if (i.b != (byte) 11) {
                        i.a(fVar, i.b);
                        break;
                    } else {
                        this.d = fVar.w();
                        break;
                    }
                case (short) 5:
                    if (i.b != (byte) 11) {
                        i.a(fVar, i.b);
                        break;
                    } else {
                        this.e = fVar.w();
                        break;
                    }
                case (short) 6:
                    if (i.b != (byte) 11) {
                        i.a(fVar, i.b);
                        break;
                    } else {
                        this.f = fVar.w();
                        break;
                    }
                case (short) 7:
                    if (i.b != (byte) 11) {
                        i.a(fVar, i.b);
                        break;
                    } else {
                        this.g = fVar.w();
                        break;
                    }
                case (short) 8:
                    if (i.b != (byte) 11) {
                        i.a(fVar, i.b);
                        break;
                    } else {
                        this.h = fVar.w();
                        break;
                    }
                case (short) 9:
                    if (i.b != (byte) 11) {
                        i.a(fVar, i.b);
                        break;
                    } else {
                        this.i = fVar.w();
                        break;
                    }
                case (short) 10:
                    if (i.b != (byte) 11) {
                        i.a(fVar, i.b);
                        break;
                    } else {
                        this.j = fVar.w();
                        break;
                    }
                case (short) 11:
                    if (i.b != (byte) 11) {
                        i.a(fVar, i.b);
                        break;
                    } else {
                        this.k = fVar.w();
                        break;
                    }
                case (short) 12:
                    if (i.b != (byte) 11) {
                        i.a(fVar, i.b);
                        break;
                    } else {
                        this.l = fVar.w();
                        break;
                    }
                case (short) 13:
                    if (i.b != (byte) 8) {
                        i.a(fVar, i.b);
                        break;
                    }
                    this.m = fVar.t();
                    a(true);
                    break;
                case (short) 14:
                    if (i.b != (byte) 8) {
                        i.a(fVar, i.b);
                        break;
                    }
                    this.n = fVar.t();
                    b(true);
                    break;
                case (short) 15:
                    if (i.b != (byte) 11) {
                        i.a(fVar, i.b);
                        break;
                    } else {
                        this.o = fVar.w();
                        break;
                    }
                case (short) 16:
                    if (i.b != (byte) 11) {
                        i.a(fVar, i.b);
                        break;
                    } else {
                        this.p = fVar.w();
                        break;
                    }
                case (short) 17:
                    if (i.b != (byte) 11) {
                        i.a(fVar, i.b);
                        break;
                    } else {
                        this.q = fVar.w();
                        break;
                    }
                case (short) 18:
                    if (i.b != (byte) 11) {
                        i.a(fVar, i.b);
                        break;
                    } else {
                        this.r = fVar.w();
                        break;
                    }
                case (short) 19:
                    if (i.b != (byte) 8) {
                        i.a(fVar, i.b);
                        break;
                    }
                    this.s = fVar.t();
                    c(true);
                    break;
                case (short) 100:
                    if (i.b != (byte) 13) {
                        i.a(fVar, i.b);
                        break;
                    }
                    org.apache.thrift.protocol.e k = fVar.k();
                    this.t = new HashMap(k.c * 2);
                    for (int i2 = 0; i2 < k.c; i2++) {
                        this.t.put(fVar.w(), fVar.w());
                    }
                    fVar.l();
                    break;
                case (short) 101:
                    if (i.b != (byte) 2) {
                        i.a(fVar, i.b);
                        break;
                    }
                    this.u = fVar.q();
                    d(true);
                    break;
                case (short) 102:
                    if (i.b != (byte) 11) {
                        i.a(fVar, i.b);
                        break;
                    } else {
                        this.v = fVar.w();
                        break;
                    }
                default:
                    i.a(fVar, i.b);
                    break;
            }
            fVar.j();
        }
    }

    public void a(boolean z) {
        this.U.set(0, z);
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(s sVar) {
        if (sVar == null) {
            return false;
        }
        boolean a = a();
        boolean a2 = sVar.a();
        if ((a || a2) && (!a || !a2 || !this.a.equals(sVar.a))) {
            return false;
        }
        a = b();
        a2 = sVar.b();
        if ((a || a2) && (!a || !a2 || !this.b.a(sVar.b))) {
            return false;
        }
        a = c();
        a2 = sVar.c();
        if ((a || a2) && (!a || !a2 || !this.c.equals(sVar.c))) {
            return false;
        }
        a = e();
        a2 = sVar.e();
        if ((a || a2) && (!a || !a2 || !this.d.equals(sVar.d))) {
            return false;
        }
        a = f();
        a2 = sVar.f();
        if ((a || a2) && (!a || !a2 || !this.e.equals(sVar.e))) {
            return false;
        }
        a = g();
        a2 = sVar.g();
        if ((a || a2) && (!a || !a2 || !this.f.equals(sVar.f))) {
            return false;
        }
        a = i();
        a2 = sVar.i();
        if ((a || a2) && (!a || !a2 || !this.g.equals(sVar.g))) {
            return false;
        }
        a = j();
        a2 = sVar.j();
        if ((a || a2) && (!a || !a2 || !this.h.equals(sVar.h))) {
            return false;
        }
        a = k();
        a2 = sVar.k();
        if ((a || a2) && (!a || !a2 || !this.i.equals(sVar.i))) {
            return false;
        }
        a = l();
        a2 = sVar.l();
        if ((a || a2) && (!a || !a2 || !this.j.equals(sVar.j))) {
            return false;
        }
        a = m();
        a2 = sVar.m();
        if ((a || a2) && (!a || !a2 || !this.k.equals(sVar.k))) {
            return false;
        }
        a = n();
        a2 = sVar.n();
        if ((a || a2) && (!a || !a2 || !this.l.equals(sVar.l))) {
            return false;
        }
        a = o();
        a2 = sVar.o();
        if ((a || a2) && (!a || !a2 || this.m != sVar.m)) {
            return false;
        }
        a = p();
        a2 = sVar.p();
        if ((a || a2) && (!a || !a2 || this.n != sVar.n)) {
            return false;
        }
        a = q();
        a2 = sVar.q();
        if ((a || a2) && (!a || !a2 || !this.o.equals(sVar.o))) {
            return false;
        }
        a = r();
        a2 = sVar.r();
        if ((a || a2) && (!a || !a2 || !this.p.equals(sVar.p))) {
            return false;
        }
        a = s();
        a2 = sVar.s();
        if ((a || a2) && (!a || !a2 || !this.q.equals(sVar.q))) {
            return false;
        }
        a = t();
        a2 = sVar.t();
        if ((a || a2) && (!a || !a2 || !this.r.equals(sVar.r))) {
            return false;
        }
        a = u();
        a2 = sVar.u();
        if ((a || a2) && (!a || !a2 || this.s != sVar.s)) {
            return false;
        }
        a = v();
        a2 = sVar.v();
        if ((a || a2) && (!a || !a2 || !this.t.equals(sVar.t))) {
            return false;
        }
        a = w();
        a2 = sVar.w();
        if ((a || a2) && (!a || !a2 || this.u != sVar.u)) {
            return false;
        }
        a = x();
        a2 = sVar.x();
        return !(a || a2) || (a && a2 && this.v.equals(sVar.v));
    }

    public int b(s sVar) {
        if (!getClass().equals(sVar.getClass())) {
            return getClass().getName().compareTo(sVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(sVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            compareTo = org.apache.thrift.c.a(this.a, sVar.a);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(sVar.b()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (b()) {
            compareTo = org.apache.thrift.c.a(this.b, sVar.b);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(c()).compareTo(Boolean.valueOf(sVar.c()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (c()) {
            compareTo = org.apache.thrift.c.a(this.c, sVar.c);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(e()).compareTo(Boolean.valueOf(sVar.e()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (e()) {
            compareTo = org.apache.thrift.c.a(this.d, sVar.d);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(f()).compareTo(Boolean.valueOf(sVar.f()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (f()) {
            compareTo = org.apache.thrift.c.a(this.e, sVar.e);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(g()).compareTo(Boolean.valueOf(sVar.g()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (g()) {
            compareTo = org.apache.thrift.c.a(this.f, sVar.f);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(i()).compareTo(Boolean.valueOf(sVar.i()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (i()) {
            compareTo = org.apache.thrift.c.a(this.g, sVar.g);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(j()).compareTo(Boolean.valueOf(sVar.j()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (j()) {
            compareTo = org.apache.thrift.c.a(this.h, sVar.h);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(k()).compareTo(Boolean.valueOf(sVar.k()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (k()) {
            compareTo = org.apache.thrift.c.a(this.i, sVar.i);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(l()).compareTo(Boolean.valueOf(sVar.l()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (l()) {
            compareTo = org.apache.thrift.c.a(this.j, sVar.j);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(m()).compareTo(Boolean.valueOf(sVar.m()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m()) {
            compareTo = org.apache.thrift.c.a(this.k, sVar.k);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(n()).compareTo(Boolean.valueOf(sVar.n()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (n()) {
            compareTo = org.apache.thrift.c.a(this.l, sVar.l);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(o()).compareTo(Boolean.valueOf(sVar.o()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (o()) {
            compareTo = org.apache.thrift.c.a(this.m, sVar.m);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(p()).compareTo(Boolean.valueOf(sVar.p()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (p()) {
            compareTo = org.apache.thrift.c.a(this.n, sVar.n);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(q()).compareTo(Boolean.valueOf(sVar.q()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (q()) {
            compareTo = org.apache.thrift.c.a(this.o, sVar.o);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(r()).compareTo(Boolean.valueOf(sVar.r()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (r()) {
            compareTo = org.apache.thrift.c.a(this.p, sVar.p);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(s()).compareTo(Boolean.valueOf(sVar.s()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (s()) {
            compareTo = org.apache.thrift.c.a(this.q, sVar.q);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(t()).compareTo(Boolean.valueOf(sVar.t()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (t()) {
            compareTo = org.apache.thrift.c.a(this.r, sVar.r);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(u()).compareTo(Boolean.valueOf(sVar.u()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (u()) {
            compareTo = org.apache.thrift.c.a(this.s, sVar.s);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(v()).compareTo(Boolean.valueOf(sVar.v()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (v()) {
            compareTo = org.apache.thrift.c.a(this.t, sVar.t);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(w()).compareTo(Boolean.valueOf(sVar.w()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (w()) {
            compareTo = org.apache.thrift.c.a(this.u, sVar.u);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(x()).compareTo(Boolean.valueOf(sVar.x()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (x()) {
            compareTo = org.apache.thrift.c.a(this.v, sVar.v);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        return 0;
    }

    public s b(int i) {
        this.n = i;
        b(true);
        return this;
    }

    public s b(String str) {
        this.d = str;
        return this;
    }

    public void b(f fVar) {
        y();
        fVar.a(x);
        if (this.a != null && a()) {
            fVar.a(y);
            fVar.a(this.a);
            fVar.b();
        }
        if (this.b != null && b()) {
            fVar.a(z);
            this.b.b(fVar);
            fVar.b();
        }
        if (this.c != null) {
            fVar.a(A);
            fVar.a(this.c);
            fVar.b();
        }
        if (this.d != null) {
            fVar.a(B);
            fVar.a(this.d);
            fVar.b();
        }
        if (this.e != null && f()) {
            fVar.a(C);
            fVar.a(this.e);
            fVar.b();
        }
        if (this.f != null && g()) {
            fVar.a(D);
            fVar.a(this.f);
            fVar.b();
        }
        if (this.g != null) {
            fVar.a(E);
            fVar.a(this.g);
            fVar.b();
        }
        if (this.h != null && j()) {
            fVar.a(F);
            fVar.a(this.h);
            fVar.b();
        }
        if (this.i != null && k()) {
            fVar.a(G);
            fVar.a(this.i);
            fVar.b();
        }
        if (this.j != null && l()) {
            fVar.a(H);
            fVar.a(this.j);
            fVar.b();
        }
        if (this.k != null && m()) {
            fVar.a(I);
            fVar.a(this.k);
            fVar.b();
        }
        if (this.l != null && n()) {
            fVar.a(J);
            fVar.a(this.l);
            fVar.b();
        }
        if (o()) {
            fVar.a(K);
            fVar.a(this.m);
            fVar.b();
        }
        if (p()) {
            fVar.a(L);
            fVar.a(this.n);
            fVar.b();
        }
        if (this.o != null && q()) {
            fVar.a(M);
            fVar.a(this.o);
            fVar.b();
        }
        if (this.p != null && r()) {
            fVar.a(N);
            fVar.a(this.p);
            fVar.b();
        }
        if (this.q != null && s()) {
            fVar.a(O);
            fVar.a(this.q);
            fVar.b();
        }
        if (this.r != null && t()) {
            fVar.a(P);
            fVar.a(this.r);
            fVar.b();
        }
        if (u()) {
            fVar.a(Q);
            fVar.a(this.s);
            fVar.b();
        }
        if (this.t != null && v()) {
            fVar.a(R);
            fVar.a(new org.apache.thrift.protocol.e((byte) 11, (byte) 11, this.t.size()));
            for (Entry entry : this.t.entrySet()) {
                fVar.a((String) entry.getKey());
                fVar.a((String) entry.getValue());
            }
            fVar.d();
            fVar.b();
        }
        if (w()) {
            fVar.a(S);
            fVar.a(this.u);
            fVar.b();
        }
        if (this.v != null && x()) {
            fVar.a(T);
            fVar.a(this.v);
            fVar.b();
        }
        fVar.c();
        fVar.a();
    }

    public void b(boolean z) {
        this.U.set(1, z);
    }

    public boolean b() {
        return this.b != null;
    }

    public s c(int i) {
        this.s = i;
        c(true);
        return this;
    }

    public s c(String str) {
        this.e = str;
        return this;
    }

    public void c(boolean z) {
        this.U.set(2, z);
    }

    public boolean c() {
        return this.c != null;
    }

    public /* synthetic */ int compareTo(Object obj) {
        return b((s) obj);
    }

    public s d(String str) {
        this.f = str;
        return this;
    }

    public String d() {
        return this.d;
    }

    public void d(boolean z) {
        this.U.set(3, z);
    }

    public s e(String str) {
        this.g = str;
        return this;
    }

    public boolean e() {
        return this.d != null;
    }

    public boolean equals(Object obj) {
        return (obj != null && (obj instanceof s)) ? a((s) obj) : false;
    }

    public s f(String str) {
        this.h = str;
        return this;
    }

    public boolean f() {
        return this.e != null;
    }

    public s g(String str) {
        this.l = str;
        return this;
    }

    public boolean g() {
        return this.f != null;
    }

    public s h(String str) {
        this.o = str;
        return this;
    }

    public String h() {
        return this.g;
    }

    public int hashCode() {
        return 0;
    }

    public s i(String str) {
        this.p = str;
        return this;
    }

    public boolean i() {
        return this.g != null;
    }

    public s j(String str) {
        this.q = str;
        return this;
    }

    public boolean j() {
        return this.h != null;
    }

    public s k(String str) {
        this.r = str;
        return this;
    }

    public boolean k() {
        return this.i != null;
    }

    public boolean l() {
        return this.j != null;
    }

    public boolean m() {
        return this.k != null;
    }

    public boolean n() {
        return this.l != null;
    }

    public boolean o() {
        return this.U.get(0);
    }

    public boolean p() {
        return this.U.get(1);
    }

    public boolean q() {
        return this.o != null;
    }

    public boolean r() {
        return this.p != null;
    }

    public boolean s() {
        return this.q != null;
    }

    public boolean t() {
        return this.r != null;
    }

    public String toString() {
        Object obj = null;
        StringBuilder stringBuilder = new StringBuilder("XmPushActionRegistration(");
        Object obj2 = 1;
        if (a()) {
            stringBuilder.append("debug:");
            if (this.a == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.a);
            }
            obj2 = null;
        }
        if (b()) {
            if (obj2 == null) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("target:");
            if (this.b == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.b);
            }
        } else {
            obj = obj2;
        }
        if (obj == null) {
            stringBuilder.append(", ");
        }
        stringBuilder.append("id:");
        if (this.c == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.c);
        }
        stringBuilder.append(", ");
        stringBuilder.append("appId:");
        if (this.d == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.d);
        }
        if (f()) {
            stringBuilder.append(", ");
            stringBuilder.append("appVersion:");
            if (this.e == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.e);
            }
        }
        if (g()) {
            stringBuilder.append(", ");
            stringBuilder.append("packageName:");
            if (this.f == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.f);
            }
        }
        stringBuilder.append(", ");
        stringBuilder.append("token:");
        if (this.g == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.g);
        }
        if (j()) {
            stringBuilder.append(", ");
            stringBuilder.append("deviceId:");
            if (this.h == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.h);
            }
        }
        if (k()) {
            stringBuilder.append(", ");
            stringBuilder.append("aliasName:");
            if (this.i == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.i);
            }
        }
        if (l()) {
            stringBuilder.append(", ");
            stringBuilder.append("sdkVersion:");
            if (this.j == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.j);
            }
        }
        if (m()) {
            stringBuilder.append(", ");
            stringBuilder.append("regId:");
            if (this.k == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.k);
            }
        }
        if (n()) {
            stringBuilder.append(", ");
            stringBuilder.append("pushSdkVersionName:");
            if (this.l == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.l);
            }
        }
        if (o()) {
            stringBuilder.append(", ");
            stringBuilder.append("pushSdkVersionCode:");
            stringBuilder.append(this.m);
        }
        if (p()) {
            stringBuilder.append(", ");
            stringBuilder.append("appVersionCode:");
            stringBuilder.append(this.n);
        }
        if (q()) {
            stringBuilder.append(", ");
            stringBuilder.append("androidId:");
            if (this.o == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.o);
            }
        }
        if (r()) {
            stringBuilder.append(", ");
            stringBuilder.append("imei:");
            if (this.p == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.p);
            }
        }
        if (s()) {
            stringBuilder.append(", ");
            stringBuilder.append("serial:");
            if (this.q == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.q);
            }
        }
        if (t()) {
            stringBuilder.append(", ");
            stringBuilder.append("imeiMd5:");
            if (this.r == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.r);
            }
        }
        if (u()) {
            stringBuilder.append(", ");
            stringBuilder.append("spaceId:");
            stringBuilder.append(this.s);
        }
        if (v()) {
            stringBuilder.append(", ");
            stringBuilder.append("connectionAttrs:");
            if (this.t == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.t);
            }
        }
        if (w()) {
            stringBuilder.append(", ");
            stringBuilder.append("cleanOldRegInfo:");
            stringBuilder.append(this.u);
        }
        if (x()) {
            stringBuilder.append(", ");
            stringBuilder.append("oldRegId:");
            if (this.v == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.v);
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public boolean u() {
        return this.U.get(2);
    }

    public boolean v() {
        return this.t != null;
    }

    public boolean w() {
        return this.U.get(3);
    }

    public boolean x() {
        return this.v != null;
    }

    public void y() {
        if (this.c == null) {
            throw new org.apache.thrift.protocol.g("Required field 'id' was not present! Struct: " + toString());
        } else if (this.d == null) {
            throw new org.apache.thrift.protocol.g("Required field 'appId' was not present! Struct: " + toString());
        } else if (this.g == null) {
            throw new org.apache.thrift.protocol.g("Required field 'token' was not present! Struct: " + toString());
        }
    }
}
