package com.xiaomi.xmpush.thrift;

import java.io.Serializable;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.thrift.b;
import org.apache.thrift.protocol.c;
import org.apache.thrift.protocol.f;
import org.apache.thrift.protocol.g;
import org.apache.thrift.protocol.i;
import org.apache.thrift.protocol.k;

public class l implements Serializable, Cloneable, b<l, a> {
    public static final Map<a, org.apache.thrift.meta_data.b> c;
    private static final k d = new k("XmPushActionCheckClientInfo");
    private static final c e = new c("miscConfigVersion", (byte) 8, (short) 1);
    private static final c f = new c("pluginConfigVersion", (byte) 8, (short) 2);
    public int a;
    public int b;
    private BitSet g = new BitSet(2);

    public enum a {
        MISC_CONFIG_VERSION((short) 1, "miscConfigVersion"),
        PLUGIN_CONFIG_VERSION((short) 2, "pluginConfigVersion");
        
        private static final Map<String, a> c = null;
        private final short d;
        private final String e;

        static {
            c = new HashMap();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                c.put(aVar.a(), aVar);
            }
        }

        private a(short s, String str) {
            this.d = s;
            this.e = str;
        }

        public String a() {
            return this.e;
        }
    }

    static {
        Map enumMap = new EnumMap(a.class);
        enumMap.put(a.MISC_CONFIG_VERSION, new org.apache.thrift.meta_data.b("miscConfigVersion", (byte) 1, new org.apache.thrift.meta_data.c((byte) 8)));
        enumMap.put(a.PLUGIN_CONFIG_VERSION, new org.apache.thrift.meta_data.b("pluginConfigVersion", (byte) 1, new org.apache.thrift.meta_data.c((byte) 8)));
        c = Collections.unmodifiableMap(enumMap);
        org.apache.thrift.meta_data.b.a(l.class, c);
    }

    public l a(int i) {
        this.a = i;
        a(true);
        return this;
    }

    public void a(f fVar) {
        fVar.g();
        while (true) {
            c i = fVar.i();
            if (i.b == (byte) 0) {
                fVar.h();
                if (!a()) {
                    throw new g("Required field 'miscConfigVersion' was not found in serialized data! Struct: " + toString());
                } else if (b()) {
                    c();
                    return;
                } else {
                    throw new g("Required field 'pluginConfigVersion' was not found in serialized data! Struct: " + toString());
                }
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
                    if (i.b != (byte) 8) {
                        i.a(fVar, i.b);
                        break;
                    }
                    this.b = fVar.t();
                    b(true);
                    break;
                default:
                    i.a(fVar, i.b);
                    break;
            }
            fVar.j();
        }
    }

    public void a(boolean z) {
        this.g.set(0, z);
    }

    public boolean a() {
        return this.g.get(0);
    }

    public boolean a(l lVar) {
        return lVar != null && this.a == lVar.a && this.b == lVar.b;
    }

    public int b(l lVar) {
        if (!getClass().equals(lVar.getClass())) {
            return getClass().getName().compareTo(lVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(lVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            compareTo = org.apache.thrift.c.a(this.a, lVar.a);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(lVar.b()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (b()) {
            compareTo = org.apache.thrift.c.a(this.b, lVar.b);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        return 0;
    }

    public l b(int i) {
        this.b = i;
        b(true);
        return this;
    }

    public void b(f fVar) {
        c();
        fVar.a(d);
        fVar.a(e);
        fVar.a(this.a);
        fVar.b();
        fVar.a(f);
        fVar.a(this.b);
        fVar.b();
        fVar.c();
        fVar.a();
    }

    public void b(boolean z) {
        this.g.set(1, z);
    }

    public boolean b() {
        return this.g.get(1);
    }

    public void c() {
    }

    public /* synthetic */ int compareTo(Object obj) {
        return b((l) obj);
    }

    public boolean equals(Object obj) {
        return (obj != null && (obj instanceof l)) ? a((l) obj) : false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("XmPushActionCheckClientInfo(");
        stringBuilder.append("miscConfigVersion:");
        stringBuilder.append(this.a);
        stringBuilder.append(", ");
        stringBuilder.append("pluginConfigVersion:");
        stringBuilder.append(this.b);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
