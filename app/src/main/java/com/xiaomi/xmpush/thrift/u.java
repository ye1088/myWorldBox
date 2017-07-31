package com.xiaomi.xmpush.thrift;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.thrift.b;
import org.apache.thrift.meta_data.e;
import org.apache.thrift.meta_data.g;
import org.apache.thrift.protocol.c;
import org.apache.thrift.protocol.f;
import org.apache.thrift.protocol.i;
import org.apache.thrift.protocol.k;

public class u implements Serializable, Cloneable, b<u, a> {
    public static final Map<a, org.apache.thrift.meta_data.b> g;
    private static final k h = new k("XmPushActionSendFeedback");
    private static final c i = new c("debug", (byte) 11, (short) 1);
    private static final c j = new c("target", (byte) 12, (short) 2);
    private static final c k = new c("id", (byte) 11, (short) 3);
    private static final c l = new c("appId", (byte) 11, (short) 4);
    private static final c m = new c("feedbacks", (byte) 13, (short) 5);
    private static final c n = new c("category", (byte) 11, (short) 6);
    public String a;
    public j b;
    public String c;
    public String d;
    public Map<String, String> e;
    public String f;

    public enum a {
        DEBUG((short) 1, "debug"),
        TARGET((short) 2, "target"),
        ID((short) 3, "id"),
        APP_ID((short) 4, "appId"),
        FEEDBACKS((short) 5, "feedbacks"),
        CATEGORY((short) 6, "category");
        
        private static final Map<String, a> g = null;
        private final short h;
        private final String i;

        static {
            g = new HashMap();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                g.put(aVar.a(), aVar);
            }
        }

        private a(short s, String str) {
            this.h = s;
            this.i = str;
        }

        public String a() {
            return this.i;
        }
    }

    static {
        Map enumMap = new EnumMap(a.class);
        enumMap.put(a.DEBUG, new org.apache.thrift.meta_data.b("debug", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.TARGET, new org.apache.thrift.meta_data.b("target", (byte) 2, new g((byte) 12, j.class)));
        enumMap.put(a.ID, new org.apache.thrift.meta_data.b("id", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.APP_ID, new org.apache.thrift.meta_data.b("appId", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.FEEDBACKS, new org.apache.thrift.meta_data.b("feedbacks", (byte) 2, new e((byte) 13, new org.apache.thrift.meta_data.c((byte) 11), new org.apache.thrift.meta_data.c((byte) 11))));
        enumMap.put(a.CATEGORY, new org.apache.thrift.meta_data.b("category", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        g = Collections.unmodifiableMap(enumMap);
        org.apache.thrift.meta_data.b.a(u.class, g);
    }

    public void a(f fVar) {
        fVar.g();
        while (true) {
            c i = fVar.i();
            if (i.b == (byte) 0) {
                fVar.h();
                g();
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
                    if (i.b != (byte) 13) {
                        i.a(fVar, i.b);
                        break;
                    }
                    org.apache.thrift.protocol.e k = fVar.k();
                    this.e = new HashMap(k.c * 2);
                    for (int i2 = 0; i2 < k.c; i2++) {
                        this.e.put(fVar.w(), fVar.w());
                    }
                    fVar.l();
                    break;
                case (short) 6:
                    if (i.b != (byte) 11) {
                        i.a(fVar, i.b);
                        break;
                    } else {
                        this.f = fVar.w();
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

    public boolean a(u uVar) {
        if (uVar == null) {
            return false;
        }
        boolean a = a();
        boolean a2 = uVar.a();
        if ((a || a2) && (!a || !a2 || !this.a.equals(uVar.a))) {
            return false;
        }
        a = b();
        a2 = uVar.b();
        if ((a || a2) && (!a || !a2 || !this.b.a(uVar.b))) {
            return false;
        }
        a = c();
        a2 = uVar.c();
        if ((a || a2) && (!a || !a2 || !this.c.equals(uVar.c))) {
            return false;
        }
        a = d();
        a2 = uVar.d();
        if ((a || a2) && (!a || !a2 || !this.d.equals(uVar.d))) {
            return false;
        }
        a = e();
        a2 = uVar.e();
        if ((a || a2) && (!a || !a2 || !this.e.equals(uVar.e))) {
            return false;
        }
        a = f();
        a2 = uVar.f();
        return !(a || a2) || (a && a2 && this.f.equals(uVar.f));
    }

    public int b(u uVar) {
        if (!getClass().equals(uVar.getClass())) {
            return getClass().getName().compareTo(uVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(uVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            compareTo = org.apache.thrift.c.a(this.a, uVar.a);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(uVar.b()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (b()) {
            compareTo = org.apache.thrift.c.a(this.b, uVar.b);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(c()).compareTo(Boolean.valueOf(uVar.c()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (c()) {
            compareTo = org.apache.thrift.c.a(this.c, uVar.c);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(d()).compareTo(Boolean.valueOf(uVar.d()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (d()) {
            compareTo = org.apache.thrift.c.a(this.d, uVar.d);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(e()).compareTo(Boolean.valueOf(uVar.e()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (e()) {
            compareTo = org.apache.thrift.c.a(this.e, uVar.e);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(f()).compareTo(Boolean.valueOf(uVar.f()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (f()) {
            compareTo = org.apache.thrift.c.a(this.f, uVar.f);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        return 0;
    }

    public void b(f fVar) {
        g();
        fVar.a(h);
        if (this.a != null && a()) {
            fVar.a(i);
            fVar.a(this.a);
            fVar.b();
        }
        if (this.b != null && b()) {
            fVar.a(j);
            this.b.b(fVar);
            fVar.b();
        }
        if (this.c != null) {
            fVar.a(k);
            fVar.a(this.c);
            fVar.b();
        }
        if (this.d != null) {
            fVar.a(l);
            fVar.a(this.d);
            fVar.b();
        }
        if (this.e != null && e()) {
            fVar.a(m);
            fVar.a(new org.apache.thrift.protocol.e((byte) 11, (byte) 11, this.e.size()));
            for (Entry entry : this.e.entrySet()) {
                fVar.a((String) entry.getKey());
                fVar.a((String) entry.getValue());
            }
            fVar.d();
            fVar.b();
        }
        if (this.f != null && f()) {
            fVar.a(n);
            fVar.a(this.f);
            fVar.b();
        }
        fVar.c();
        fVar.a();
    }

    public boolean b() {
        return this.b != null;
    }

    public boolean c() {
        return this.c != null;
    }

    public /* synthetic */ int compareTo(Object obj) {
        return b((u) obj);
    }

    public boolean d() {
        return this.d != null;
    }

    public boolean e() {
        return this.e != null;
    }

    public boolean equals(Object obj) {
        return (obj != null && (obj instanceof u)) ? a((u) obj) : false;
    }

    public boolean f() {
        return this.f != null;
    }

    public void g() {
        if (this.c == null) {
            throw new org.apache.thrift.protocol.g("Required field 'id' was not present! Struct: " + toString());
        } else if (this.d == null) {
            throw new org.apache.thrift.protocol.g("Required field 'appId' was not present! Struct: " + toString());
        }
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        Object obj = null;
        StringBuilder stringBuilder = new StringBuilder("XmPushActionSendFeedback(");
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
        if (e()) {
            stringBuilder.append(", ");
            stringBuilder.append("feedbacks:");
            if (this.e == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.e);
            }
        }
        if (f()) {
            stringBuilder.append(", ");
            stringBuilder.append("category:");
            if (this.f == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.f);
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
