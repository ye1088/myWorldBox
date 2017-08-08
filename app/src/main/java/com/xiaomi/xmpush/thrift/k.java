package com.xiaomi.xmpush.thrift;

import com.tencent.open.SocialConstants;
import java.io.Serializable;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.thrift.b;
import org.apache.thrift.meta_data.g;
import org.apache.thrift.protocol.c;
import org.apache.thrift.protocol.f;
import org.apache.thrift.protocol.i;

public class k implements Serializable, Cloneable, b<k, a> {
    private static final c A = new c(SocialConstants.TYPE_REQUEST, (byte) 12, (short) 8);
    private static final c B = new c("packageName", (byte) 11, (short) 9);
    private static final c C = new c("category", (byte) 11, (short) 10);
    private static final c D = new c("isOnline", (byte) 2, (short) 11);
    private static final c E = new c("regId", (byte) 11, (short) 12);
    private static final c F = new c("callbackUrl", (byte) 11, (short) 13);
    private static final c G = new c("userAccount", (byte) 11, (short) 14);
    private static final c H = new c("deviceStatus", (byte) 6, (short) 15);
    private static final c I = new c("imeiMd5", (byte) 11, (short) 20);
    private static final c J = new c("deviceId", (byte) 11, (short) 21);
    public static final Map<a, org.apache.thrift.meta_data.b> r;
    private static final org.apache.thrift.protocol.k s = new org.apache.thrift.protocol.k("XmPushActionAckMessage");
    private static final c t = new c("debug", (byte) 11, (short) 1);
    private static final c u = new c("target", (byte) 12, (short) 2);
    private static final c v = new c("id", (byte) 11, (short) 3);
    private static final c w = new c("appId", (byte) 11, (short) 4);
    private static final c x = new c("messageTs", (byte) 10, (short) 5);
    private static final c y = new c(r.gO, (byte) 11, (short) 6);
    private static final c z = new c("aliasName", (byte) 11, (short) 7);
    private BitSet K = new BitSet(3);
    public String a;
    public j b;
    public String c;
    public String d;
    public long e;
    public String f;
    public String g;
    public w h;
    public String i;
    public String j;
    public boolean k = false;
    public String l;
    public String m;
    public String n;
    public short o;
    public String p;
    public String q;

    public enum a {
        DEBUG((short) 1, "debug"),
        TARGET((short) 2, "target"),
        ID((short) 3, "id"),
        APP_ID((short) 4, "appId"),
        MESSAGE_TS((short) 5, "messageTs"),
        TOPIC((short) 6, r.gO),
        ALIAS_NAME((short) 7, "aliasName"),
        REQUEST((short) 8, SocialConstants.TYPE_REQUEST),
        PACKAGE_NAME((short) 9, "packageName"),
        CATEGORY((short) 10, "category"),
        IS_ONLINE((short) 11, "isOnline"),
        REG_ID((short) 12, "regId"),
        CALLBACK_URL((short) 13, "callbackUrl"),
        USER_ACCOUNT((short) 14, "userAccount"),
        DEVICE_STATUS((short) 15, "deviceStatus"),
        IMEI_MD5((short) 20, "imeiMd5"),
        DEVICE_ID((short) 21, "deviceId");
        
        private static final Map<String, a> r = null;
        private final short s;
        private final String t;

        static {
            r = new HashMap();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                r.put(aVar.a(), aVar);
            }
        }

        private a(short s, String str) {
            this.s = s;
            this.t = str;
        }

        public String a() {
            return this.t;
        }
    }

    static {
        Map enumMap = new EnumMap(a.class);
        enumMap.put(a.DEBUG, new org.apache.thrift.meta_data.b("debug", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.TARGET, new org.apache.thrift.meta_data.b("target", (byte) 2, new g((byte) 12, j.class)));
        enumMap.put(a.ID, new org.apache.thrift.meta_data.b("id", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.APP_ID, new org.apache.thrift.meta_data.b("appId", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.MESSAGE_TS, new org.apache.thrift.meta_data.b("messageTs", (byte) 1, new org.apache.thrift.meta_data.c((byte) 10)));
        enumMap.put(a.TOPIC, new org.apache.thrift.meta_data.b(r.gO, (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.ALIAS_NAME, new org.apache.thrift.meta_data.b("aliasName", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.REQUEST, new org.apache.thrift.meta_data.b(SocialConstants.TYPE_REQUEST, (byte) 2, new g((byte) 12, w.class)));
        enumMap.put(a.PACKAGE_NAME, new org.apache.thrift.meta_data.b("packageName", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.CATEGORY, new org.apache.thrift.meta_data.b("category", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.IS_ONLINE, new org.apache.thrift.meta_data.b("isOnline", (byte) 2, new org.apache.thrift.meta_data.c((byte) 2)));
        enumMap.put(a.REG_ID, new org.apache.thrift.meta_data.b("regId", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.CALLBACK_URL, new org.apache.thrift.meta_data.b("callbackUrl", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.USER_ACCOUNT, new org.apache.thrift.meta_data.b("userAccount", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.DEVICE_STATUS, new org.apache.thrift.meta_data.b("deviceStatus", (byte) 2, new org.apache.thrift.meta_data.c((byte) 6)));
        enumMap.put(a.IMEI_MD5, new org.apache.thrift.meta_data.b("imeiMd5", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.DEVICE_ID, new org.apache.thrift.meta_data.b("deviceId", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        r = Collections.unmodifiableMap(enumMap);
        org.apache.thrift.meta_data.b.a(k.class, r);
    }

    public k a(long j) {
        this.e = j;
        a(true);
        return this;
    }

    public k a(String str) {
        this.c = str;
        return this;
    }

    public k a(short s) {
        this.o = s;
        c(true);
        return this;
    }

    public void a(f fVar) {
        fVar.g();
        while (true) {
            c i = fVar.i();
            if (i.b == (byte) 0) {
                fVar.h();
                if (e()) {
                    r();
                    return;
                }
                throw new org.apache.thrift.protocol.g("Required field 'messageTs' was not found in serialized data! Struct: " + toString());
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
                    if (i.b != (byte) 10) {
                        i.a(fVar, i.b);
                        break;
                    }
                    this.e = fVar.u();
                    a(true);
                    break;
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
                    if (i.b != (byte) 12) {
                        i.a(fVar, i.b);
                        break;
                    }
                    this.h = new w();
                    this.h.a(fVar);
                    break;
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
                    if (i.b != (byte) 2) {
                        i.a(fVar, i.b);
                        break;
                    }
                    this.k = fVar.q();
                    b(true);
                    break;
                case (short) 12:
                    if (i.b != (byte) 11) {
                        i.a(fVar, i.b);
                        break;
                    } else {
                        this.l = fVar.w();
                        break;
                    }
                case (short) 13:
                    if (i.b != (byte) 11) {
                        i.a(fVar, i.b);
                        break;
                    } else {
                        this.m = fVar.w();
                        break;
                    }
                case (short) 14:
                    if (i.b != (byte) 11) {
                        i.a(fVar, i.b);
                        break;
                    } else {
                        this.n = fVar.w();
                        break;
                    }
                case (short) 15:
                    if (i.b != (byte) 6) {
                        i.a(fVar, i.b);
                        break;
                    }
                    this.o = fVar.s();
                    c(true);
                    break;
                case (short) 20:
                    if (i.b != (byte) 11) {
                        i.a(fVar, i.b);
                        break;
                    } else {
                        this.p = fVar.w();
                        break;
                    }
                case (short) 21:
                    if (i.b != (byte) 11) {
                        i.a(fVar, i.b);
                        break;
                    } else {
                        this.q = fVar.w();
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
        this.K.set(0, z);
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(k kVar) {
        if (kVar == null) {
            return false;
        }
        boolean a = a();
        boolean a2 = kVar.a();
        if ((a || a2) && (!a || !a2 || !this.a.equals(kVar.a))) {
            return false;
        }
        a = b();
        a2 = kVar.b();
        if ((a || a2) && (!a || !a2 || !this.b.a(kVar.b))) {
            return false;
        }
        a = c();
        a2 = kVar.c();
        if ((a || a2) && (!a || !a2 || !this.c.equals(kVar.c))) {
            return false;
        }
        a = d();
        a2 = kVar.d();
        if (((a || a2) && (!a || !a2 || !this.d.equals(kVar.d))) || this.e != kVar.e) {
            return false;
        }
        a = f();
        a2 = kVar.f();
        if ((a || a2) && (!a || !a2 || !this.f.equals(kVar.f))) {
            return false;
        }
        a = g();
        a2 = kVar.g();
        if ((a || a2) && (!a || !a2 || !this.g.equals(kVar.g))) {
            return false;
        }
        a = h();
        a2 = kVar.h();
        if ((a || a2) && (!a || !a2 || !this.h.a(kVar.h))) {
            return false;
        }
        a = i();
        a2 = kVar.i();
        if ((a || a2) && (!a || !a2 || !this.i.equals(kVar.i))) {
            return false;
        }
        a = j();
        a2 = kVar.j();
        if ((a || a2) && (!a || !a2 || !this.j.equals(kVar.j))) {
            return false;
        }
        a = k();
        a2 = kVar.k();
        if ((a || a2) && (!a || !a2 || this.k != kVar.k)) {
            return false;
        }
        a = l();
        a2 = kVar.l();
        if ((a || a2) && (!a || !a2 || !this.l.equals(kVar.l))) {
            return false;
        }
        a = m();
        a2 = kVar.m();
        if ((a || a2) && (!a || !a2 || !this.m.equals(kVar.m))) {
            return false;
        }
        a = n();
        a2 = kVar.n();
        if ((a || a2) && (!a || !a2 || !this.n.equals(kVar.n))) {
            return false;
        }
        a = o();
        a2 = kVar.o();
        if ((a || a2) && (!a || !a2 || this.o != kVar.o)) {
            return false;
        }
        a = p();
        a2 = kVar.p();
        if ((a || a2) && (!a || !a2 || !this.p.equals(kVar.p))) {
            return false;
        }
        a = q();
        a2 = kVar.q();
        return !(a || a2) || (a && a2 && this.q.equals(kVar.q));
    }

    public int b(k kVar) {
        if (!getClass().equals(kVar.getClass())) {
            return getClass().getName().compareTo(kVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(kVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            compareTo = org.apache.thrift.c.a(this.a, kVar.a);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(kVar.b()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (b()) {
            compareTo = org.apache.thrift.c.a(this.b, kVar.b);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(c()).compareTo(Boolean.valueOf(kVar.c()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (c()) {
            compareTo = org.apache.thrift.c.a(this.c, kVar.c);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(d()).compareTo(Boolean.valueOf(kVar.d()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (d()) {
            compareTo = org.apache.thrift.c.a(this.d, kVar.d);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(e()).compareTo(Boolean.valueOf(kVar.e()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (e()) {
            compareTo = org.apache.thrift.c.a(this.e, kVar.e);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(f()).compareTo(Boolean.valueOf(kVar.f()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (f()) {
            compareTo = org.apache.thrift.c.a(this.f, kVar.f);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(g()).compareTo(Boolean.valueOf(kVar.g()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (g()) {
            compareTo = org.apache.thrift.c.a(this.g, kVar.g);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(h()).compareTo(Boolean.valueOf(kVar.h()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (h()) {
            compareTo = org.apache.thrift.c.a(this.h, kVar.h);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(i()).compareTo(Boolean.valueOf(kVar.i()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (i()) {
            compareTo = org.apache.thrift.c.a(this.i, kVar.i);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(j()).compareTo(Boolean.valueOf(kVar.j()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (j()) {
            compareTo = org.apache.thrift.c.a(this.j, kVar.j);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(k()).compareTo(Boolean.valueOf(kVar.k()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (k()) {
            compareTo = org.apache.thrift.c.a(this.k, kVar.k);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(l()).compareTo(Boolean.valueOf(kVar.l()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (l()) {
            compareTo = org.apache.thrift.c.a(this.l, kVar.l);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(m()).compareTo(Boolean.valueOf(kVar.m()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m()) {
            compareTo = org.apache.thrift.c.a(this.m, kVar.m);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(n()).compareTo(Boolean.valueOf(kVar.n()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (n()) {
            compareTo = org.apache.thrift.c.a(this.n, kVar.n);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(o()).compareTo(Boolean.valueOf(kVar.o()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (o()) {
            compareTo = org.apache.thrift.c.a(this.o, kVar.o);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(p()).compareTo(Boolean.valueOf(kVar.p()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (p()) {
            compareTo = org.apache.thrift.c.a(this.p, kVar.p);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(q()).compareTo(Boolean.valueOf(kVar.q()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (q()) {
            compareTo = org.apache.thrift.c.a(this.q, kVar.q);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        return 0;
    }

    public k b(String str) {
        this.d = str;
        return this;
    }

    public void b(f fVar) {
        r();
        fVar.a(s);
        if (this.a != null && a()) {
            fVar.a(t);
            fVar.a(this.a);
            fVar.b();
        }
        if (this.b != null && b()) {
            fVar.a(u);
            this.b.b(fVar);
            fVar.b();
        }
        if (this.c != null) {
            fVar.a(v);
            fVar.a(this.c);
            fVar.b();
        }
        if (this.d != null) {
            fVar.a(w);
            fVar.a(this.d);
            fVar.b();
        }
        fVar.a(x);
        fVar.a(this.e);
        fVar.b();
        if (this.f != null && f()) {
            fVar.a(y);
            fVar.a(this.f);
            fVar.b();
        }
        if (this.g != null && g()) {
            fVar.a(z);
            fVar.a(this.g);
            fVar.b();
        }
        if (this.h != null && h()) {
            fVar.a(A);
            this.h.b(fVar);
            fVar.b();
        }
        if (this.i != null && i()) {
            fVar.a(B);
            fVar.a(this.i);
            fVar.b();
        }
        if (this.j != null && j()) {
            fVar.a(C);
            fVar.a(this.j);
            fVar.b();
        }
        if (k()) {
            fVar.a(D);
            fVar.a(this.k);
            fVar.b();
        }
        if (this.l != null && l()) {
            fVar.a(E);
            fVar.a(this.l);
            fVar.b();
        }
        if (this.m != null && m()) {
            fVar.a(F);
            fVar.a(this.m);
            fVar.b();
        }
        if (this.n != null && n()) {
            fVar.a(G);
            fVar.a(this.n);
            fVar.b();
        }
        if (o()) {
            fVar.a(H);
            fVar.a(this.o);
            fVar.b();
        }
        if (this.p != null && p()) {
            fVar.a(I);
            fVar.a(this.p);
            fVar.b();
        }
        if (this.q != null && q()) {
            fVar.a(J);
            fVar.a(this.q);
            fVar.b();
        }
        fVar.c();
        fVar.a();
    }

    public void b(boolean z) {
        this.K.set(1, z);
    }

    public boolean b() {
        return this.b != null;
    }

    public k c(String str) {
        this.f = str;
        return this;
    }

    public void c(boolean z) {
        this.K.set(2, z);
    }

    public boolean c() {
        return this.c != null;
    }

    public /* synthetic */ int compareTo(Object obj) {
        return b((k) obj);
    }

    public k d(String str) {
        this.g = str;
        return this;
    }

    public boolean d() {
        return this.d != null;
    }

    public boolean e() {
        return this.K.get(0);
    }

    public boolean equals(Object obj) {
        return (obj != null && (obj instanceof k)) ? a((k) obj) : false;
    }

    public boolean f() {
        return this.f != null;
    }

    public boolean g() {
        return this.g != null;
    }

    public boolean h() {
        return this.h != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.i != null;
    }

    public boolean j() {
        return this.j != null;
    }

    public boolean k() {
        return this.K.get(1);
    }

    public boolean l() {
        return this.l != null;
    }

    public boolean m() {
        return this.m != null;
    }

    public boolean n() {
        return this.n != null;
    }

    public boolean o() {
        return this.K.get(2);
    }

    public boolean p() {
        return this.p != null;
    }

    public boolean q() {
        return this.q != null;
    }

    public void r() {
        if (this.c == null) {
            throw new org.apache.thrift.protocol.g("Required field 'id' was not present! Struct: " + toString());
        } else if (this.d == null) {
            throw new org.apache.thrift.protocol.g("Required field 'appId' was not present! Struct: " + toString());
        }
    }

    public String toString() {
        Object obj = null;
        StringBuilder stringBuilder = new StringBuilder("XmPushActionAckMessage(");
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
        stringBuilder.append(", ");
        stringBuilder.append("messageTs:");
        stringBuilder.append(this.e);
        if (f()) {
            stringBuilder.append(", ");
            stringBuilder.append("topic:");
            if (this.f == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.f);
            }
        }
        if (g()) {
            stringBuilder.append(", ");
            stringBuilder.append("aliasName:");
            if (this.g == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.g);
            }
        }
        if (h()) {
            stringBuilder.append(", ");
            stringBuilder.append("request:");
            if (this.h == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.h);
            }
        }
        if (i()) {
            stringBuilder.append(", ");
            stringBuilder.append("packageName:");
            if (this.i == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.i);
            }
        }
        if (j()) {
            stringBuilder.append(", ");
            stringBuilder.append("category:");
            if (this.j == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.j);
            }
        }
        if (k()) {
            stringBuilder.append(", ");
            stringBuilder.append("isOnline:");
            stringBuilder.append(this.k);
        }
        if (l()) {
            stringBuilder.append(", ");
            stringBuilder.append("regId:");
            if (this.l == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.l);
            }
        }
        if (m()) {
            stringBuilder.append(", ");
            stringBuilder.append("callbackUrl:");
            if (this.m == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.m);
            }
        }
        if (n()) {
            stringBuilder.append(", ");
            stringBuilder.append("userAccount:");
            if (this.n == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.n);
            }
        }
        if (o()) {
            stringBuilder.append(", ");
            stringBuilder.append("deviceStatus:");
            stringBuilder.append(this.o);
        }
        if (p()) {
            stringBuilder.append(", ");
            stringBuilder.append("imeiMd5:");
            if (this.p == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.p);
            }
        }
        if (q()) {
            stringBuilder.append(", ");
            stringBuilder.append("deviceId:");
            if (this.q == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.q);
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
