package com.xiaomi.xmpush.thrift;

import com.huluxia.r;
import java.io.Serializable;
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

public class ab implements Serializable, Cloneable, b<ab, a> {
    public static final Map<a, org.apache.thrift.meta_data.b> h;
    private static final k i = new k("XmPushActionUnSubscription");
    private static final c j = new c("debug", (byte) 11, (short) 1);
    private static final c k = new c("target", (byte) 12, (short) 2);
    private static final c l = new c("id", (byte) 11, (short) 3);
    private static final c m = new c("appId", (byte) 11, (short) 4);
    private static final c n = new c(r.gO, (byte) 11, (short) 5);
    private static final c o = new c("packageName", (byte) 11, (short) 6);
    private static final c p = new c("category", (byte) 11, (short) 7);
    public String a;
    public j b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;

    public enum a {
        DEBUG((short) 1, "debug"),
        TARGET((short) 2, "target"),
        ID((short) 3, "id"),
        APP_ID((short) 4, "appId"),
        TOPIC((short) 5, r.gO),
        PACKAGE_NAME((short) 6, "packageName"),
        CATEGORY((short) 7, "category");
        
        private static final Map<String, a> h = null;
        private final short i;
        private final String j;

        static {
            h = new HashMap();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                h.put(aVar.a(), aVar);
            }
        }

        private a(short s, String str) {
            this.i = s;
            this.j = str;
        }

        public String a() {
            return this.j;
        }
    }

    static {
        Map enumMap = new EnumMap(a.class);
        enumMap.put(a.DEBUG, new org.apache.thrift.meta_data.b("debug", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.TARGET, new org.apache.thrift.meta_data.b("target", (byte) 2, new g((byte) 12, j.class)));
        enumMap.put(a.ID, new org.apache.thrift.meta_data.b("id", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.APP_ID, new org.apache.thrift.meta_data.b("appId", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.TOPIC, new org.apache.thrift.meta_data.b(r.gO, (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.PACKAGE_NAME, new org.apache.thrift.meta_data.b("packageName", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.CATEGORY, new org.apache.thrift.meta_data.b("category", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        h = Collections.unmodifiableMap(enumMap);
        org.apache.thrift.meta_data.b.a(ab.class, h);
    }

    public ab a(String str) {
        this.c = str;
        return this;
    }

    public void a(f fVar) {
        fVar.g();
        while (true) {
            c i = fVar.i();
            if (i.b == (byte) 0) {
                fVar.h();
                h();
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

    public boolean a(ab abVar) {
        if (abVar == null) {
            return false;
        }
        boolean a = a();
        boolean a2 = abVar.a();
        if ((a || a2) && (!a || !a2 || !this.a.equals(abVar.a))) {
            return false;
        }
        a = b();
        a2 = abVar.b();
        if ((a || a2) && (!a || !a2 || !this.b.a(abVar.b))) {
            return false;
        }
        a = c();
        a2 = abVar.c();
        if ((a || a2) && (!a || !a2 || !this.c.equals(abVar.c))) {
            return false;
        }
        a = d();
        a2 = abVar.d();
        if ((a || a2) && (!a || !a2 || !this.d.equals(abVar.d))) {
            return false;
        }
        a = e();
        a2 = abVar.e();
        if ((a || a2) && (!a || !a2 || !this.e.equals(abVar.e))) {
            return false;
        }
        a = f();
        a2 = abVar.f();
        if ((a || a2) && (!a || !a2 || !this.f.equals(abVar.f))) {
            return false;
        }
        a = g();
        a2 = abVar.g();
        return !(a || a2) || (a && a2 && this.g.equals(abVar.g));
    }

    public int b(ab abVar) {
        if (!getClass().equals(abVar.getClass())) {
            return getClass().getName().compareTo(abVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(abVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            compareTo = org.apache.thrift.c.a(this.a, abVar.a);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(abVar.b()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (b()) {
            compareTo = org.apache.thrift.c.a(this.b, abVar.b);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(c()).compareTo(Boolean.valueOf(abVar.c()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (c()) {
            compareTo = org.apache.thrift.c.a(this.c, abVar.c);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(d()).compareTo(Boolean.valueOf(abVar.d()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (d()) {
            compareTo = org.apache.thrift.c.a(this.d, abVar.d);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(e()).compareTo(Boolean.valueOf(abVar.e()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (e()) {
            compareTo = org.apache.thrift.c.a(this.e, abVar.e);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(f()).compareTo(Boolean.valueOf(abVar.f()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (f()) {
            compareTo = org.apache.thrift.c.a(this.f, abVar.f);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(g()).compareTo(Boolean.valueOf(abVar.g()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (g()) {
            compareTo = org.apache.thrift.c.a(this.g, abVar.g);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        return 0;
    }

    public ab b(String str) {
        this.d = str;
        return this;
    }

    public void b(f fVar) {
        h();
        fVar.a(i);
        if (this.a != null && a()) {
            fVar.a(j);
            fVar.a(this.a);
            fVar.b();
        }
        if (this.b != null && b()) {
            fVar.a(k);
            this.b.b(fVar);
            fVar.b();
        }
        if (this.c != null) {
            fVar.a(l);
            fVar.a(this.c);
            fVar.b();
        }
        if (this.d != null) {
            fVar.a(m);
            fVar.a(this.d);
            fVar.b();
        }
        if (this.e != null) {
            fVar.a(n);
            fVar.a(this.e);
            fVar.b();
        }
        if (this.f != null && f()) {
            fVar.a(o);
            fVar.a(this.f);
            fVar.b();
        }
        if (this.g != null && g()) {
            fVar.a(p);
            fVar.a(this.g);
            fVar.b();
        }
        fVar.c();
        fVar.a();
    }

    public boolean b() {
        return this.b != null;
    }

    public ab c(String str) {
        this.e = str;
        return this;
    }

    public boolean c() {
        return this.c != null;
    }

    public /* synthetic */ int compareTo(Object obj) {
        return b((ab) obj);
    }

    public ab d(String str) {
        this.f = str;
        return this;
    }

    public boolean d() {
        return this.d != null;
    }

    public ab e(String str) {
        this.g = str;
        return this;
    }

    public boolean e() {
        return this.e != null;
    }

    public boolean equals(Object obj) {
        return (obj != null && (obj instanceof ab)) ? a((ab) obj) : false;
    }

    public boolean f() {
        return this.f != null;
    }

    public boolean g() {
        return this.g != null;
    }

    public void h() {
        if (this.c == null) {
            throw new org.apache.thrift.protocol.g("Required field 'id' was not present! Struct: " + toString());
        } else if (this.d == null) {
            throw new org.apache.thrift.protocol.g("Required field 'appId' was not present! Struct: " + toString());
        } else if (this.e == null) {
            throw new org.apache.thrift.protocol.g("Required field 'topic' was not present! Struct: " + toString());
        }
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        Object obj = null;
        StringBuilder stringBuilder = new StringBuilder("XmPushActionUnSubscription(");
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
        stringBuilder.append("topic:");
        if (this.e == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.e);
        }
        if (f()) {
            stringBuilder.append(", ");
            stringBuilder.append("packageName:");
            if (this.f == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.f);
            }
        }
        if (g()) {
            stringBuilder.append(", ");
            stringBuilder.append("category:");
            if (this.g == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.g);
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
