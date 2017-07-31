package com.xiaomi.xmpush.thrift;

import io.netty.handler.codec.rtsp.RtspHeaders.Values;
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
import org.apache.thrift.protocol.k;

public class h implements Serializable, Cloneable, b<h, a> {
    public static final Map<a, org.apache.thrift.meta_data.b> i;
    private static final k j = new k("PushMessage");
    private static final c k = new c("to", (byte) 12, (short) 1);
    private static final c l = new c("id", (byte) 11, (short) 2);
    private static final c m = new c("appId", (byte) 11, (short) 3);
    private static final c n = new c("payload", (byte) 11, (short) 4);
    private static final c o = new c("createAt", (byte) 10, (short) 5);
    private static final c p = new c(Values.TTL, (byte) 10, (short) 6);
    private static final c q = new c("collapseKey", (byte) 11, (short) 7);
    private static final c r = new c("packageName", (byte) 11, (short) 8);
    public j a;
    public String b;
    public String c;
    public String d;
    public long e;
    public long f;
    public String g;
    public String h;
    private BitSet s = new BitSet(2);

    public enum a {
        TO((short) 1, "to"),
        ID((short) 2, "id"),
        APP_ID((short) 3, "appId"),
        PAYLOAD((short) 4, "payload"),
        CREATE_AT((short) 5, "createAt"),
        TTL((short) 6, Values.TTL),
        COLLAPSE_KEY((short) 7, "collapseKey"),
        PACKAGE_NAME((short) 8, "packageName");
        
        private static final Map<String, a> i = null;
        private final short j;
        private final String k;

        static {
            i = new HashMap();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                i.put(aVar.a(), aVar);
            }
        }

        private a(short s, String str) {
            this.j = s;
            this.k = str;
        }

        public String a() {
            return this.k;
        }
    }

    static {
        Map enumMap = new EnumMap(a.class);
        enumMap.put(a.TO, new org.apache.thrift.meta_data.b("to", (byte) 2, new g((byte) 12, j.class)));
        enumMap.put(a.ID, new org.apache.thrift.meta_data.b("id", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.APP_ID, new org.apache.thrift.meta_data.b("appId", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.PAYLOAD, new org.apache.thrift.meta_data.b("payload", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.CREATE_AT, new org.apache.thrift.meta_data.b("createAt", (byte) 2, new org.apache.thrift.meta_data.c((byte) 10)));
        enumMap.put(a.TTL, new org.apache.thrift.meta_data.b(Values.TTL, (byte) 2, new org.apache.thrift.meta_data.c((byte) 10)));
        enumMap.put(a.COLLAPSE_KEY, new org.apache.thrift.meta_data.b("collapseKey", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.PACKAGE_NAME, new org.apache.thrift.meta_data.b("packageName", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        i = Collections.unmodifiableMap(enumMap);
        org.apache.thrift.meta_data.b.a(h.class, i);
    }

    public void a(f fVar) {
        fVar.g();
        while (true) {
            c i = fVar.i();
            if (i.b == (byte) 0) {
                fVar.h();
                m();
                return;
            }
            switch (i.c) {
                case (short) 1:
                    if (i.b != (byte) 12) {
                        i.a(fVar, i.b);
                        break;
                    }
                    this.a = new j();
                    this.a.a(fVar);
                    break;
                case (short) 2:
                    if (i.b != (byte) 11) {
                        i.a(fVar, i.b);
                        break;
                    } else {
                        this.b = fVar.w();
                        break;
                    }
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
                    if (i.b != (byte) 10) {
                        i.a(fVar, i.b);
                        break;
                    }
                    this.f = fVar.u();
                    b(true);
                    break;
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
                default:
                    i.a(fVar, i.b);
                    break;
            }
            fVar.j();
        }
    }

    public void a(boolean z) {
        this.s.set(0, z);
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(h hVar) {
        if (hVar == null) {
            return false;
        }
        boolean a = a();
        boolean a2 = hVar.a();
        if ((a || a2) && (!a || !a2 || !this.a.a(hVar.a))) {
            return false;
        }
        a = c();
        a2 = hVar.c();
        if ((a || a2) && (!a || !a2 || !this.b.equals(hVar.b))) {
            return false;
        }
        a = e();
        a2 = hVar.e();
        if ((a || a2) && (!a || !a2 || !this.c.equals(hVar.c))) {
            return false;
        }
        a = g();
        a2 = hVar.g();
        if ((a || a2) && (!a || !a2 || !this.d.equals(hVar.d))) {
            return false;
        }
        a = i();
        a2 = hVar.i();
        if ((a || a2) && (!a || !a2 || this.e != hVar.e)) {
            return false;
        }
        a = j();
        a2 = hVar.j();
        if ((a || a2) && (!a || !a2 || this.f != hVar.f)) {
            return false;
        }
        a = k();
        a2 = hVar.k();
        if ((a || a2) && (!a || !a2 || !this.g.equals(hVar.g))) {
            return false;
        }
        a = l();
        a2 = hVar.l();
        return !(a || a2) || (a && a2 && this.h.equals(hVar.h));
    }

    public int b(h hVar) {
        if (!getClass().equals(hVar.getClass())) {
            return getClass().getName().compareTo(hVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(hVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            compareTo = org.apache.thrift.c.a(this.a, hVar.a);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(c()).compareTo(Boolean.valueOf(hVar.c()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (c()) {
            compareTo = org.apache.thrift.c.a(this.b, hVar.b);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(e()).compareTo(Boolean.valueOf(hVar.e()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (e()) {
            compareTo = org.apache.thrift.c.a(this.c, hVar.c);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(g()).compareTo(Boolean.valueOf(hVar.g()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (g()) {
            compareTo = org.apache.thrift.c.a(this.d, hVar.d);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(i()).compareTo(Boolean.valueOf(hVar.i()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (i()) {
            compareTo = org.apache.thrift.c.a(this.e, hVar.e);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(j()).compareTo(Boolean.valueOf(hVar.j()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (j()) {
            compareTo = org.apache.thrift.c.a(this.f, hVar.f);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(k()).compareTo(Boolean.valueOf(hVar.k()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (k()) {
            compareTo = org.apache.thrift.c.a(this.g, hVar.g);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(l()).compareTo(Boolean.valueOf(hVar.l()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (l()) {
            compareTo = org.apache.thrift.c.a(this.h, hVar.h);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        return 0;
    }

    public String b() {
        return this.b;
    }

    public void b(f fVar) {
        m();
        fVar.a(j);
        if (this.a != null && a()) {
            fVar.a(k);
            this.a.b(fVar);
            fVar.b();
        }
        if (this.b != null) {
            fVar.a(l);
            fVar.a(this.b);
            fVar.b();
        }
        if (this.c != null) {
            fVar.a(m);
            fVar.a(this.c);
            fVar.b();
        }
        if (this.d != null) {
            fVar.a(n);
            fVar.a(this.d);
            fVar.b();
        }
        if (i()) {
            fVar.a(o);
            fVar.a(this.e);
            fVar.b();
        }
        if (j()) {
            fVar.a(p);
            fVar.a(this.f);
            fVar.b();
        }
        if (this.g != null && k()) {
            fVar.a(q);
            fVar.a(this.g);
            fVar.b();
        }
        if (this.h != null && l()) {
            fVar.a(r);
            fVar.a(this.h);
            fVar.b();
        }
        fVar.c();
        fVar.a();
    }

    public void b(boolean z) {
        this.s.set(1, z);
    }

    public boolean c() {
        return this.b != null;
    }

    public /* synthetic */ int compareTo(Object obj) {
        return b((h) obj);
    }

    public String d() {
        return this.c;
    }

    public boolean e() {
        return this.c != null;
    }

    public boolean equals(Object obj) {
        return (obj != null && (obj instanceof h)) ? a((h) obj) : false;
    }

    public String f() {
        return this.d;
    }

    public boolean g() {
        return this.d != null;
    }

    public long h() {
        return this.e;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.s.get(0);
    }

    public boolean j() {
        return this.s.get(1);
    }

    public boolean k() {
        return this.g != null;
    }

    public boolean l() {
        return this.h != null;
    }

    public void m() {
        if (this.b == null) {
            throw new org.apache.thrift.protocol.g("Required field 'id' was not present! Struct: " + toString());
        } else if (this.c == null) {
            throw new org.apache.thrift.protocol.g("Required field 'appId' was not present! Struct: " + toString());
        } else if (this.d == null) {
            throw new org.apache.thrift.protocol.g("Required field 'payload' was not present! Struct: " + toString());
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("PushMessage(");
        Object obj = 1;
        if (a()) {
            stringBuilder.append("to:");
            if (this.a == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.a);
            }
            obj = null;
        }
        if (obj == null) {
            stringBuilder.append(", ");
        }
        stringBuilder.append("id:");
        if (this.b == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.b);
        }
        stringBuilder.append(", ");
        stringBuilder.append("appId:");
        if (this.c == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.c);
        }
        stringBuilder.append(", ");
        stringBuilder.append("payload:");
        if (this.d == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.d);
        }
        if (i()) {
            stringBuilder.append(", ");
            stringBuilder.append("createAt:");
            stringBuilder.append(this.e);
        }
        if (j()) {
            stringBuilder.append(", ");
            stringBuilder.append("ttl:");
            stringBuilder.append(this.f);
        }
        if (k()) {
            stringBuilder.append(", ");
            stringBuilder.append("collapseKey:");
            if (this.g == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.g);
            }
        }
        if (l()) {
            stringBuilder.append(", ");
            stringBuilder.append("packageName:");
            if (this.h == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.h);
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
