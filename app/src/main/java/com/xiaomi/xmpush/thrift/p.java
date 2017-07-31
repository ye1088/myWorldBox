package com.xiaomi.xmpush.thrift;

import java.io.Serializable;
import java.util.ArrayList;
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

public class p implements Serializable, Cloneable, b<p, a> {
    public static final Map<a, org.apache.thrift.meta_data.b> b;
    private static final k c = new k("XmPushActionCustomConfig");
    private static final c d = new c("customConfigs", (byte) 15, (short) 1);
    public List<g> a;

    public enum a {
        CUSTOM_CONFIGS((short) 1, "customConfigs");
        
        private static final Map<String, a> b = null;
        private final short c;
        private final String d;

        static {
            b = new HashMap();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                b.put(aVar.a(), aVar);
            }
        }

        private a(short s, String str) {
            this.c = s;
            this.d = str;
        }

        public String a() {
            return this.d;
        }
    }

    static {
        Map enumMap = new EnumMap(a.class);
        enumMap.put(a.CUSTOM_CONFIGS, new org.apache.thrift.meta_data.b("customConfigs", (byte) 1, new d((byte) 15, new g((byte) 12, g.class))));
        b = Collections.unmodifiableMap(enumMap);
        org.apache.thrift.meta_data.b.a(p.class, b);
    }

    public List<g> a() {
        return this.a;
    }

    public void a(f fVar) {
        fVar.g();
        while (true) {
            c i = fVar.i();
            if (i.b == (byte) 0) {
                fVar.h();
                c();
                return;
            }
            switch (i.c) {
                case (short) 1:
                    if (i.b != (byte) 15) {
                        i.a(fVar, i.b);
                        break;
                    }
                    org.apache.thrift.protocol.d m = fVar.m();
                    this.a = new ArrayList(m.b);
                    for (int i2 = 0; i2 < m.b; i2++) {
                        g gVar = new g();
                        gVar.a(fVar);
                        this.a.add(gVar);
                    }
                    fVar.n();
                    break;
                default:
                    i.a(fVar, i.b);
                    break;
            }
            fVar.j();
        }
    }

    public boolean a(p pVar) {
        if (pVar == null) {
            return false;
        }
        boolean b = b();
        boolean b2 = pVar.b();
        return !(b || b2) || (b && b2 && this.a.equals(pVar.a));
    }

    public int b(p pVar) {
        if (!getClass().equals(pVar.getClass())) {
            return getClass().getName().compareTo(pVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(pVar.b()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (b()) {
            compareTo = org.apache.thrift.c.a(this.a, pVar.a);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        return 0;
    }

    public void b(f fVar) {
        c();
        fVar.a(c);
        if (this.a != null) {
            fVar.a(d);
            fVar.a(new org.apache.thrift.protocol.d((byte) 12, this.a.size()));
            for (g b : this.a) {
                b.b(fVar);
            }
            fVar.e();
            fVar.b();
        }
        fVar.c();
        fVar.a();
    }

    public boolean b() {
        return this.a != null;
    }

    public void c() {
        if (this.a == null) {
            throw new org.apache.thrift.protocol.g("Required field 'customConfigs' was not present! Struct: " + toString());
        }
    }

    public /* synthetic */ int compareTo(Object obj) {
        return b((p) obj);
    }

    public boolean equals(Object obj) {
        return (obj != null && (obj instanceof p)) ? a((p) obj) : false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("XmPushActionCustomConfig(");
        stringBuilder.append("customConfigs:");
        if (this.a == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.a);
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
