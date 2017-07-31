package com.xiaomi.xmpush.thrift;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.thrift.b;
import org.apache.thrift.meta_data.d;
import org.apache.thrift.meta_data.g;
import org.apache.thrift.protocol.c;
import org.apache.thrift.protocol.f;
import org.apache.thrift.protocol.i;
import org.apache.thrift.protocol.k;

public class e implements Serializable, Cloneable, b<e, a> {
    public static final Map<a, org.apache.thrift.meta_data.b> d;
    private static final k e = new k("NormalConfig");
    private static final c f = new c("version", (byte) 8, (short) 1);
    private static final c g = new c("configItems", (byte) 15, (short) 2);
    private static final c h = new c("type", (byte) 8, (short) 3);
    public int a;
    public List<g> b;
    public c c;
    private BitSet i = new BitSet(1);

    public enum a {
        VERSION((short) 1, "version"),
        CONFIG_ITEMS((short) 2, "configItems"),
        TYPE((short) 3, "type");
        
        private static final Map<String, a> d = null;
        private final short e;
        private final String f;

        static {
            d = new HashMap();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                d.put(aVar.a(), aVar);
            }
        }

        private a(short s, String str) {
            this.e = s;
            this.f = str;
        }

        public String a() {
            return this.f;
        }
    }

    static {
        Map enumMap = new EnumMap(a.class);
        enumMap.put(a.VERSION, new org.apache.thrift.meta_data.b("version", (byte) 1, new org.apache.thrift.meta_data.c((byte) 8)));
        enumMap.put(a.CONFIG_ITEMS, new org.apache.thrift.meta_data.b("configItems", (byte) 1, new d((byte) 15, new g((byte) 12, g.class))));
        enumMap.put(a.TYPE, new org.apache.thrift.meta_data.b("type", (byte) 1, new org.apache.thrift.meta_data.a((byte) 16, c.class)));
        d = Collections.unmodifiableMap(enumMap);
        org.apache.thrift.meta_data.b.a(e.class, d);
    }

    public int a() {
        return this.a;
    }

    public void a(f fVar) {
        fVar.g();
        while (true) {
            c i = fVar.i();
            if (i.b == (byte) 0) {
                fVar.h();
                if (b()) {
                    f();
                    return;
                }
                throw new org.apache.thrift.protocol.g("Required field 'version' was not found in serialized data! Struct: " + toString());
            }
            switch (i.c) {
                case (short) 1:
                    if (i.b != (byte) 8) {
                        i.a(fVar, i.b);
                        break;
                    }
                    this.a = fVar.t();
                    a(true);
                    break;
                case (short) 2:
                    if (i.b != (byte) 15) {
                        i.a(fVar, i.b);
                        break;
                    }
                    org.apache.thrift.protocol.d m = fVar.m();
                    this.b = new ArrayList(m.b);
                    for (int i2 = 0; i2 < m.b; i2++) {
                        g gVar = new g();
                        gVar.a(fVar);
                        this.b.add(gVar);
                    }
                    fVar.n();
                    break;
                case (short) 3:
                    if (i.b != (byte) 8) {
                        i.a(fVar, i.b);
                        break;
                    } else {
                        this.c = c.a(fVar.t());
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
        this.i.set(0, z);
    }

    public boolean a(e eVar) {
        if (eVar == null || this.a != eVar.a) {
            return false;
        }
        boolean c = c();
        boolean c2 = eVar.c();
        if ((c || c2) && (!c || !c2 || !this.b.equals(eVar.b))) {
            return false;
        }
        c = e();
        c2 = eVar.e();
        return !(c || c2) || (c && c2 && this.c.equals(eVar.c));
    }

    public int b(e eVar) {
        if (!getClass().equals(eVar.getClass())) {
            return getClass().getName().compareTo(eVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(eVar.b()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (b()) {
            compareTo = org.apache.thrift.c.a(this.a, eVar.a);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(c()).compareTo(Boolean.valueOf(eVar.c()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (c()) {
            compareTo = org.apache.thrift.c.a(this.b, eVar.b);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(e()).compareTo(Boolean.valueOf(eVar.e()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (e()) {
            compareTo = org.apache.thrift.c.a(this.c, eVar.c);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        return 0;
    }

    public void b(f fVar) {
        f();
        fVar.a(e);
        fVar.a(f);
        fVar.a(this.a);
        fVar.b();
        if (this.b != null) {
            fVar.a(g);
            fVar.a(new org.apache.thrift.protocol.d((byte) 12, this.b.size()));
            for (g b : this.b) {
                b.b(fVar);
            }
            fVar.e();
            fVar.b();
        }
        if (this.c != null) {
            fVar.a(h);
            fVar.a(this.c.a());
            fVar.b();
        }
        fVar.c();
        fVar.a();
    }

    public boolean b() {
        return this.i.get(0);
    }

    public boolean c() {
        return this.b != null;
    }

    public /* synthetic */ int compareTo(Object obj) {
        return b((e) obj);
    }

    public c d() {
        return this.c;
    }

    public boolean e() {
        return this.c != null;
    }

    public boolean equals(Object obj) {
        return (obj != null && (obj instanceof e)) ? a((e) obj) : false;
    }

    public void f() {
        if (this.b == null) {
            throw new org.apache.thrift.protocol.g("Required field 'configItems' was not present! Struct: " + toString());
        } else if (this.c == null) {
            throw new org.apache.thrift.protocol.g("Required field 'type' was not present! Struct: " + toString());
        }
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("NormalConfig(");
        stringBuilder.append("version:");
        stringBuilder.append(this.a);
        stringBuilder.append(", ");
        stringBuilder.append("configItems:");
        if (this.b == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.b);
        }
        stringBuilder.append(", ");
        stringBuilder.append("type:");
        if (this.c == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.c);
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
