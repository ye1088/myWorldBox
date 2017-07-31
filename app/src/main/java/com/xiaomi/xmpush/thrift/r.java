package com.xiaomi.xmpush.thrift;

import java.io.Serializable;
import java.nio.ByteBuffer;
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

public class r implements Serializable, Cloneable, b<r, a> {
    public static final Map<a, org.apache.thrift.meta_data.b> l;
    private static final k m = new k("XmPushActionNotification");
    private static final c n = new c("debug", (byte) 11, (short) 1);
    private static final c o = new c("target", (byte) 12, (short) 2);
    private static final c p = new c("id", (byte) 11, (short) 3);
    private static final c q = new c("appId", (byte) 11, (short) 4);
    private static final c r = new c("type", (byte) 11, (short) 5);
    private static final c s = new c("requireAck", (byte) 2, (short) 6);
    private static final c t = new c("payload", (byte) 11, (short) 7);
    private static final c u = new c("extra", (byte) 13, (short) 8);
    private static final c v = new c("packageName", (byte) 11, (short) 9);
    private static final c w = new c("category", (byte) 11, (short) 10);
    private static final c x = new c("binaryExtra", (byte) 11, (short) 14);
    public String a;
    public j b;
    public String c;
    public String d;
    public String e;
    public boolean f;
    public String g;
    public Map<String, String> h;
    public String i;
    public String j;
    public ByteBuffer k;
    private BitSet y;

    static {
        Map enumMap = new EnumMap(a.class);
        enumMap.put(a.a, new org.apache.thrift.meta_data.b("debug", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.b, new org.apache.thrift.meta_data.b("target", (byte) 2, new g((byte) 12, j.class)));
        enumMap.put(a.c, new org.apache.thrift.meta_data.b("id", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.d, new org.apache.thrift.meta_data.b("appId", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.e, new org.apache.thrift.meta_data.b("type", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.f, new org.apache.thrift.meta_data.b("requireAck", (byte) 1, new org.apache.thrift.meta_data.c((byte) 2)));
        enumMap.put(a.g, new org.apache.thrift.meta_data.b("payload", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.h, new org.apache.thrift.meta_data.b("extra", (byte) 2, new e((byte) 13, new org.apache.thrift.meta_data.c((byte) 11), new org.apache.thrift.meta_data.c((byte) 11))));
        enumMap.put(a.i, new org.apache.thrift.meta_data.b("packageName", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.j, new org.apache.thrift.meta_data.b("category", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.k, new org.apache.thrift.meta_data.b("binaryExtra", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        l = Collections.unmodifiableMap(enumMap);
        org.apache.thrift.meta_data.b.a(r.class, l);
    }

    public r() {
        this.y = new BitSet(1);
        this.f = true;
    }

    public r(String str, boolean z) {
        this();
        this.c = str;
        this.f = z;
        b(true);
    }

    public r a(String str) {
        this.c = str;
        return this;
    }

    public r a(ByteBuffer byteBuffer) {
        this.k = byteBuffer;
        return this;
    }

    public r a(Map<String, String> map) {
        this.h = map;
        return this;
    }

    public r a(boolean z) {
        this.f = z;
        b(true);
        return this;
    }

    public r a(byte[] bArr) {
        a(ByteBuffer.wrap(bArr));
        return this;
    }

    public void a(f fVar) {
        fVar.g();
        while (true) {
            c i = fVar.i();
            if (i.b == (byte) 0) {
                fVar.h();
                if (f()) {
                    n();
                    return;
                }
                throw new org.apache.thrift.protocol.g("Required field 'requireAck' was not found in serialized data! Struct: " + toString());
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
                    if (i.b != (byte) 2) {
                        i.a(fVar, i.b);
                        break;
                    }
                    this.f = fVar.q();
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
                    if (i.b != (byte) 13) {
                        i.a(fVar, i.b);
                        break;
                    }
                    org.apache.thrift.protocol.e k = fVar.k();
                    this.h = new HashMap(k.c * 2);
                    for (int i2 = 0; i2 < k.c; i2++) {
                        this.h.put(fVar.w(), fVar.w());
                    }
                    fVar.l();
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
                case (short) 14:
                    if (i.b != (byte) 11) {
                        i.a(fVar, i.b);
                        break;
                    } else {
                        this.k = fVar.x();
                        break;
                    }
                default:
                    i.a(fVar, i.b);
                    break;
            }
            fVar.j();
        }
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(r rVar) {
        if (rVar == null) {
            return false;
        }
        boolean a = a();
        boolean a2 = rVar.a();
        if ((a || a2) && (!a || !a2 || !this.a.equals(rVar.a))) {
            return false;
        }
        a = b();
        a2 = rVar.b();
        if ((a || a2) && (!a || !a2 || !this.b.a(rVar.b))) {
            return false;
        }
        a = c();
        a2 = rVar.c();
        if ((a || a2) && (!a || !a2 || !this.c.equals(rVar.c))) {
            return false;
        }
        a = d();
        a2 = rVar.d();
        if ((a || a2) && (!a || !a2 || !this.d.equals(rVar.d))) {
            return false;
        }
        a = e();
        a2 = rVar.e();
        if (((a || a2) && (!a || !a2 || !this.e.equals(rVar.e))) || this.f != rVar.f) {
            return false;
        }
        a = g();
        a2 = rVar.g();
        if ((a || a2) && (!a || !a2 || !this.g.equals(rVar.g))) {
            return false;
        }
        a = i();
        a2 = rVar.i();
        if ((a || a2) && (!a || !a2 || !this.h.equals(rVar.h))) {
            return false;
        }
        a = j();
        a2 = rVar.j();
        if ((a || a2) && (!a || !a2 || !this.i.equals(rVar.i))) {
            return false;
        }
        a = k();
        a2 = rVar.k();
        if ((a || a2) && (!a || !a2 || !this.j.equals(rVar.j))) {
            return false;
        }
        a = m();
        a2 = rVar.m();
        return !(a || a2) || (a && a2 && this.k.equals(rVar.k));
    }

    public int b(r rVar) {
        if (!getClass().equals(rVar.getClass())) {
            return getClass().getName().compareTo(rVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(rVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            compareTo = org.apache.thrift.c.a(this.a, rVar.a);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(rVar.b()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (b()) {
            compareTo = org.apache.thrift.c.a(this.b, rVar.b);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(c()).compareTo(Boolean.valueOf(rVar.c()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (c()) {
            compareTo = org.apache.thrift.c.a(this.c, rVar.c);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(d()).compareTo(Boolean.valueOf(rVar.d()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (d()) {
            compareTo = org.apache.thrift.c.a(this.d, rVar.d);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(e()).compareTo(Boolean.valueOf(rVar.e()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (e()) {
            compareTo = org.apache.thrift.c.a(this.e, rVar.e);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(f()).compareTo(Boolean.valueOf(rVar.f()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (f()) {
            compareTo = org.apache.thrift.c.a(this.f, rVar.f);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(g()).compareTo(Boolean.valueOf(rVar.g()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (g()) {
            compareTo = org.apache.thrift.c.a(this.g, rVar.g);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(i()).compareTo(Boolean.valueOf(rVar.i()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (i()) {
            compareTo = org.apache.thrift.c.a(this.h, rVar.h);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(j()).compareTo(Boolean.valueOf(rVar.j()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (j()) {
            compareTo = org.apache.thrift.c.a(this.i, rVar.i);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(k()).compareTo(Boolean.valueOf(rVar.k()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (k()) {
            compareTo = org.apache.thrift.c.a(this.j, rVar.j);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(m()).compareTo(Boolean.valueOf(rVar.m()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m()) {
            compareTo = org.apache.thrift.c.a(this.k, rVar.k);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        return 0;
    }

    public r b(String str) {
        this.d = str;
        return this;
    }

    public void b(f fVar) {
        n();
        fVar.a(m);
        if (this.a != null && a()) {
            fVar.a(n);
            fVar.a(this.a);
            fVar.b();
        }
        if (this.b != null && b()) {
            fVar.a(o);
            this.b.b(fVar);
            fVar.b();
        }
        if (this.c != null) {
            fVar.a(p);
            fVar.a(this.c);
            fVar.b();
        }
        if (this.d != null && d()) {
            fVar.a(q);
            fVar.a(this.d);
            fVar.b();
        }
        if (this.e != null && e()) {
            fVar.a(r);
            fVar.a(this.e);
            fVar.b();
        }
        fVar.a(s);
        fVar.a(this.f);
        fVar.b();
        if (this.g != null && g()) {
            fVar.a(t);
            fVar.a(this.g);
            fVar.b();
        }
        if (this.h != null && i()) {
            fVar.a(u);
            fVar.a(new org.apache.thrift.protocol.e((byte) 11, (byte) 11, this.h.size()));
            for (Entry entry : this.h.entrySet()) {
                fVar.a((String) entry.getKey());
                fVar.a((String) entry.getValue());
            }
            fVar.d();
            fVar.b();
        }
        if (this.i != null && j()) {
            fVar.a(v);
            fVar.a(this.i);
            fVar.b();
        }
        if (this.j != null && k()) {
            fVar.a(w);
            fVar.a(this.j);
            fVar.b();
        }
        if (this.k != null && m()) {
            fVar.a(x);
            fVar.a(this.k);
            fVar.b();
        }
        fVar.c();
        fVar.a();
    }

    public void b(boolean z) {
        this.y.set(0, z);
    }

    public boolean b() {
        return this.b != null;
    }

    public r c(String str) {
        this.e = str;
        return this;
    }

    public boolean c() {
        return this.c != null;
    }

    public /* synthetic */ int compareTo(Object obj) {
        return b((r) obj);
    }

    public boolean d() {
        return this.d != null;
    }

    public boolean e() {
        return this.e != null;
    }

    public boolean equals(Object obj) {
        return (obj != null && (obj instanceof r)) ? a((r) obj) : false;
    }

    public boolean f() {
        return this.y.get(0);
    }

    public boolean g() {
        return this.g != null;
    }

    public Map<String, String> h() {
        return this.h;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.h != null;
    }

    public boolean j() {
        return this.i != null;
    }

    public boolean k() {
        return this.j != null;
    }

    public byte[] l() {
        a(org.apache.thrift.c.c(this.k));
        return this.k.array();
    }

    public boolean m() {
        return this.k != null;
    }

    public void n() {
        if (this.c == null) {
            throw new org.apache.thrift.protocol.g("Required field 'id' was not present! Struct: " + toString());
        }
    }

    public String toString() {
        Object obj = null;
        StringBuilder stringBuilder = new StringBuilder("XmPushActionNotification(");
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
        if (d()) {
            stringBuilder.append(", ");
            stringBuilder.append("appId:");
            if (this.d == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.d);
            }
        }
        if (e()) {
            stringBuilder.append(", ");
            stringBuilder.append("type:");
            if (this.e == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.e);
            }
        }
        stringBuilder.append(", ");
        stringBuilder.append("requireAck:");
        stringBuilder.append(this.f);
        if (g()) {
            stringBuilder.append(", ");
            stringBuilder.append("payload:");
            if (this.g == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.g);
            }
        }
        if (i()) {
            stringBuilder.append(", ");
            stringBuilder.append("extra:");
            if (this.h == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.h);
            }
        }
        if (j()) {
            stringBuilder.append(", ");
            stringBuilder.append("packageName:");
            if (this.i == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.i);
            }
        }
        if (k()) {
            stringBuilder.append(", ");
            stringBuilder.append("category:");
            if (this.j == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.j);
            }
        }
        if (m()) {
            stringBuilder.append(", ");
            stringBuilder.append("binaryExtra:");
            if (this.k == null) {
                stringBuilder.append("null");
            } else {
                org.apache.thrift.c.a(this.k, stringBuilder);
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
