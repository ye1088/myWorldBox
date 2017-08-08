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
import java.util.Map.Entry;
import org.apache.thrift.b;
import org.apache.thrift.meta_data.e;
import org.apache.thrift.protocol.c;
import org.apache.thrift.protocol.f;
import org.apache.thrift.protocol.g;
import org.apache.thrift.protocol.k;

public class i implements Serializable, Cloneable, b<i, a> {
    public static final Map<a, org.apache.thrift.meta_data.b> m;
    private static final k n = new k("PushMetaInfo");
    private static final c o = new c("id", (byte) 11, (short) 1);
    private static final c p = new c("messageTs", (byte) 10, (short) 2);
    private static final c q = new c(r.gO, (byte) 11, (short) 3);
    private static final c r = new c("title", (byte) 11, (short) 4);
    private static final c s = new c(SocialConstants.PARAM_COMMENT, (byte) 11, (short) 5);
    private static final c t = new c("notifyType", (byte) 8, (short) 6);
    private static final c u = new c("url", (byte) 11, (short) 7);
    private static final c v = new c("passThrough", (byte) 8, (short) 8);
    private static final c w = new c("notifyId", (byte) 8, (short) 9);
    private static final c x = new c("extra", (byte) 13, (short) 10);
    private static final c y = new c("internal", (byte) 13, (short) 11);
    private static final c z = new c("ignoreRegInfo", (byte) 2, (short) 12);
    private BitSet A;
    public String a;
    public long b;
    public String c;
    public String d;
    public String e;
    public int f;
    public String g;
    public int h;
    public int i;
    public Map<String, String> j;
    public Map<String, String> k;
    public boolean l;

    public enum a {
        ID((short) 1, "id"),
        MESSAGE_TS((short) 2, "messageTs"),
        TOPIC((short) 3, r.gO),
        TITLE((short) 4, "title"),
        DESCRIPTION((short) 5, SocialConstants.PARAM_COMMENT),
        NOTIFY_TYPE((short) 6, "notifyType"),
        URL((short) 7, "url"),
        PASS_THROUGH((short) 8, "passThrough"),
        NOTIFY_ID((short) 9, "notifyId"),
        EXTRA((short) 10, "extra"),
        INTERNAL((short) 11, "internal"),
        IGNORE_REG_INFO((short) 12, "ignoreRegInfo");
        
        private static final Map<String, a> m = null;
        private final short n;
        private final String o;

        static {
            m = new HashMap();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                m.put(aVar.a(), aVar);
            }
        }

        private a(short s, String str) {
            this.n = s;
            this.o = str;
        }

        public String a() {
            return this.o;
        }
    }

    static {
        Map enumMap = new EnumMap(a.class);
        enumMap.put(a.ID, new org.apache.thrift.meta_data.b("id", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.MESSAGE_TS, new org.apache.thrift.meta_data.b("messageTs", (byte) 1, new org.apache.thrift.meta_data.c((byte) 10)));
        enumMap.put(a.TOPIC, new org.apache.thrift.meta_data.b(r.gO, (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.TITLE, new org.apache.thrift.meta_data.b("title", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.DESCRIPTION, new org.apache.thrift.meta_data.b(SocialConstants.PARAM_COMMENT, (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.NOTIFY_TYPE, new org.apache.thrift.meta_data.b("notifyType", (byte) 2, new org.apache.thrift.meta_data.c((byte) 8)));
        enumMap.put(a.URL, new org.apache.thrift.meta_data.b("url", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.PASS_THROUGH, new org.apache.thrift.meta_data.b("passThrough", (byte) 2, new org.apache.thrift.meta_data.c((byte) 8)));
        enumMap.put(a.NOTIFY_ID, new org.apache.thrift.meta_data.b("notifyId", (byte) 2, new org.apache.thrift.meta_data.c((byte) 8)));
        enumMap.put(a.EXTRA, new org.apache.thrift.meta_data.b("extra", (byte) 2, new e((byte) 13, new org.apache.thrift.meta_data.c((byte) 11), new org.apache.thrift.meta_data.c((byte) 11))));
        enumMap.put(a.INTERNAL, new org.apache.thrift.meta_data.b("internal", (byte) 2, new e((byte) 13, new org.apache.thrift.meta_data.c((byte) 11), new org.apache.thrift.meta_data.c((byte) 11))));
        enumMap.put(a.IGNORE_REG_INFO, new org.apache.thrift.meta_data.b("ignoreRegInfo", (byte) 2, new org.apache.thrift.meta_data.c((byte) 2)));
        m = Collections.unmodifiableMap(enumMap);
        org.apache.thrift.meta_data.b.a(i.class, m);
    }

    public i() {
        this.A = new BitSet(5);
        this.l = false;
    }

    public i(i iVar) {
        Map hashMap;
        this.A = new BitSet(5);
        this.A.clear();
        this.A.or(iVar.A);
        if (iVar.c()) {
            this.a = iVar.a;
        }
        this.b = iVar.b;
        if (iVar.g()) {
            this.c = iVar.c;
        }
        if (iVar.i()) {
            this.d = iVar.d;
        }
        if (iVar.k()) {
            this.e = iVar.e;
        }
        this.f = iVar.f;
        if (iVar.n()) {
            this.g = iVar.g;
        }
        this.h = iVar.h;
        this.i = iVar.i;
        if (iVar.t()) {
            hashMap = new HashMap();
            for (Entry entry : iVar.j.entrySet()) {
                hashMap.put((String) entry.getKey(), (String) entry.getValue());
            }
            this.j = hashMap;
        }
        if (iVar.u()) {
            hashMap = new HashMap();
            for (Entry entry2 : iVar.k.entrySet()) {
                hashMap.put((String) entry2.getKey(), (String) entry2.getValue());
            }
            this.k = hashMap;
        }
        this.l = iVar.l;
    }

    public i a() {
        return new i(this);
    }

    public i a(int i) {
        this.f = i;
        b(true);
        return this;
    }

    public i a(String str) {
        this.a = str;
        return this;
    }

    public i a(Map<String, String> map) {
        this.j = map;
        return this;
    }

    public void a(String str, String str2) {
        if (this.j == null) {
            this.j = new HashMap();
        }
        this.j.put(str, str2);
    }

    public void a(f fVar) {
        fVar.g();
        while (true) {
            c i = fVar.i();
            if (i.b == (byte) 0) {
                fVar.h();
                if (e()) {
                    x();
                    return;
                }
                throw new g("Required field 'messageTs' was not found in serialized data! Struct: " + toString());
            }
            org.apache.thrift.protocol.e k;
            int i2;
            switch (i.c) {
                case (short) 1:
                    if (i.b != (byte) 11) {
                        org.apache.thrift.protocol.i.a(fVar, i.b);
                        break;
                    } else {
                        this.a = fVar.w();
                        break;
                    }
                case (short) 2:
                    if (i.b != (byte) 10) {
                        org.apache.thrift.protocol.i.a(fVar, i.b);
                        break;
                    }
                    this.b = fVar.u();
                    a(true);
                    break;
                case (short) 3:
                    if (i.b != (byte) 11) {
                        org.apache.thrift.protocol.i.a(fVar, i.b);
                        break;
                    } else {
                        this.c = fVar.w();
                        break;
                    }
                case (short) 4:
                    if (i.b != (byte) 11) {
                        org.apache.thrift.protocol.i.a(fVar, i.b);
                        break;
                    } else {
                        this.d = fVar.w();
                        break;
                    }
                case (short) 5:
                    if (i.b != (byte) 11) {
                        org.apache.thrift.protocol.i.a(fVar, i.b);
                        break;
                    } else {
                        this.e = fVar.w();
                        break;
                    }
                case (short) 6:
                    if (i.b != (byte) 8) {
                        org.apache.thrift.protocol.i.a(fVar, i.b);
                        break;
                    }
                    this.f = fVar.t();
                    b(true);
                    break;
                case (short) 7:
                    if (i.b != (byte) 11) {
                        org.apache.thrift.protocol.i.a(fVar, i.b);
                        break;
                    } else {
                        this.g = fVar.w();
                        break;
                    }
                case (short) 8:
                    if (i.b != (byte) 8) {
                        org.apache.thrift.protocol.i.a(fVar, i.b);
                        break;
                    }
                    this.h = fVar.t();
                    c(true);
                    break;
                case (short) 9:
                    if (i.b != (byte) 8) {
                        org.apache.thrift.protocol.i.a(fVar, i.b);
                        break;
                    }
                    this.i = fVar.t();
                    d(true);
                    break;
                case (short) 10:
                    if (i.b != (byte) 13) {
                        org.apache.thrift.protocol.i.a(fVar, i.b);
                        break;
                    }
                    k = fVar.k();
                    this.j = new HashMap(k.c * 2);
                    for (i2 = 0; i2 < k.c; i2++) {
                        this.j.put(fVar.w(), fVar.w());
                    }
                    fVar.l();
                    break;
                case (short) 11:
                    if (i.b != (byte) 13) {
                        org.apache.thrift.protocol.i.a(fVar, i.b);
                        break;
                    }
                    k = fVar.k();
                    this.k = new HashMap(k.c * 2);
                    for (i2 = 0; i2 < k.c; i2++) {
                        this.k.put(fVar.w(), fVar.w());
                    }
                    fVar.l();
                    break;
                case (short) 12:
                    if (i.b != (byte) 2) {
                        org.apache.thrift.protocol.i.a(fVar, i.b);
                        break;
                    }
                    this.l = fVar.q();
                    e(true);
                    break;
                default:
                    org.apache.thrift.protocol.i.a(fVar, i.b);
                    break;
            }
            fVar.j();
        }
    }

    public void a(boolean z) {
        this.A.set(0, z);
    }

    public boolean a(i iVar) {
        if (iVar == null) {
            return false;
        }
        boolean c = c();
        boolean c2 = iVar.c();
        if (((c || c2) && (!c || !c2 || !this.a.equals(iVar.a))) || this.b != iVar.b) {
            return false;
        }
        c = g();
        c2 = iVar.g();
        if ((c || c2) && (!c || !c2 || !this.c.equals(iVar.c))) {
            return false;
        }
        c = i();
        c2 = iVar.i();
        if ((c || c2) && (!c || !c2 || !this.d.equals(iVar.d))) {
            return false;
        }
        c = k();
        c2 = iVar.k();
        if ((c || c2) && (!c || !c2 || !this.e.equals(iVar.e))) {
            return false;
        }
        c = m();
        c2 = iVar.m();
        if ((c || c2) && (!c || !c2 || this.f != iVar.f)) {
            return false;
        }
        c = n();
        c2 = iVar.n();
        if ((c || c2) && (!c || !c2 || !this.g.equals(iVar.g))) {
            return false;
        }
        c = p();
        c2 = iVar.p();
        if ((c || c2) && (!c || !c2 || this.h != iVar.h)) {
            return false;
        }
        c = r();
        c2 = iVar.r();
        if ((c || c2) && (!c || !c2 || this.i != iVar.i)) {
            return false;
        }
        c = t();
        c2 = iVar.t();
        if ((c || c2) && (!c || !c2 || !this.j.equals(iVar.j))) {
            return false;
        }
        c = u();
        c2 = iVar.u();
        if ((c || c2) && (!c || !c2 || !this.k.equals(iVar.k))) {
            return false;
        }
        c = w();
        c2 = iVar.w();
        return !(c || c2) || (c && c2 && this.l == iVar.l);
    }

    public int b(i iVar) {
        if (!getClass().equals(iVar.getClass())) {
            return getClass().getName().compareTo(iVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(c()).compareTo(Boolean.valueOf(iVar.c()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (c()) {
            compareTo = org.apache.thrift.c.a(this.a, iVar.a);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(e()).compareTo(Boolean.valueOf(iVar.e()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (e()) {
            compareTo = org.apache.thrift.c.a(this.b, iVar.b);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(g()).compareTo(Boolean.valueOf(iVar.g()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (g()) {
            compareTo = org.apache.thrift.c.a(this.c, iVar.c);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(i()).compareTo(Boolean.valueOf(iVar.i()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (i()) {
            compareTo = org.apache.thrift.c.a(this.d, iVar.d);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(k()).compareTo(Boolean.valueOf(iVar.k()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (k()) {
            compareTo = org.apache.thrift.c.a(this.e, iVar.e);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(m()).compareTo(Boolean.valueOf(iVar.m()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m()) {
            compareTo = org.apache.thrift.c.a(this.f, iVar.f);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(n()).compareTo(Boolean.valueOf(iVar.n()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (n()) {
            compareTo = org.apache.thrift.c.a(this.g, iVar.g);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(p()).compareTo(Boolean.valueOf(iVar.p()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (p()) {
            compareTo = org.apache.thrift.c.a(this.h, iVar.h);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(r()).compareTo(Boolean.valueOf(iVar.r()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (r()) {
            compareTo = org.apache.thrift.c.a(this.i, iVar.i);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(t()).compareTo(Boolean.valueOf(iVar.t()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (t()) {
            compareTo = org.apache.thrift.c.a(this.j, iVar.j);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(u()).compareTo(Boolean.valueOf(iVar.u()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (u()) {
            compareTo = org.apache.thrift.c.a(this.k, iVar.k);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        compareTo = Boolean.valueOf(w()).compareTo(Boolean.valueOf(iVar.w()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (w()) {
            compareTo = org.apache.thrift.c.a(this.l, iVar.l);
            if (compareTo != 0) {
                return compareTo;
            }
        }
        return 0;
    }

    public i b(int i) {
        this.h = i;
        c(true);
        return this;
    }

    public i b(String str) {
        this.c = str;
        return this;
    }

    public String b() {
        return this.a;
    }

    public void b(f fVar) {
        x();
        fVar.a(n);
        if (this.a != null) {
            fVar.a(o);
            fVar.a(this.a);
            fVar.b();
        }
        fVar.a(p);
        fVar.a(this.b);
        fVar.b();
        if (this.c != null && g()) {
            fVar.a(q);
            fVar.a(this.c);
            fVar.b();
        }
        if (this.d != null && i()) {
            fVar.a(r);
            fVar.a(this.d);
            fVar.b();
        }
        if (this.e != null && k()) {
            fVar.a(s);
            fVar.a(this.e);
            fVar.b();
        }
        if (m()) {
            fVar.a(t);
            fVar.a(this.f);
            fVar.b();
        }
        if (this.g != null && n()) {
            fVar.a(u);
            fVar.a(this.g);
            fVar.b();
        }
        if (p()) {
            fVar.a(v);
            fVar.a(this.h);
            fVar.b();
        }
        if (r()) {
            fVar.a(w);
            fVar.a(this.i);
            fVar.b();
        }
        if (this.j != null && t()) {
            fVar.a(x);
            fVar.a(new org.apache.thrift.protocol.e((byte) 11, (byte) 11, this.j.size()));
            for (Entry entry : this.j.entrySet()) {
                fVar.a((String) entry.getKey());
                fVar.a((String) entry.getValue());
            }
            fVar.d();
            fVar.b();
        }
        if (this.k != null && u()) {
            fVar.a(y);
            fVar.a(new org.apache.thrift.protocol.e((byte) 11, (byte) 11, this.k.size()));
            for (Entry entry2 : this.k.entrySet()) {
                fVar.a((String) entry2.getKey());
                fVar.a((String) entry2.getValue());
            }
            fVar.d();
            fVar.b();
        }
        if (w()) {
            fVar.a(z);
            fVar.a(this.l);
            fVar.b();
        }
        fVar.c();
        fVar.a();
    }

    public void b(boolean z) {
        this.A.set(1, z);
    }

    public i c(int i) {
        this.i = i;
        d(true);
        return this;
    }

    public i c(String str) {
        this.d = str;
        return this;
    }

    public void c(boolean z) {
        this.A.set(2, z);
    }

    public boolean c() {
        return this.a != null;
    }

    public /* synthetic */ int compareTo(Object obj) {
        return b((i) obj);
    }

    public long d() {
        return this.b;
    }

    public i d(String str) {
        this.e = str;
        return this;
    }

    public void d(boolean z) {
        this.A.set(3, z);
    }

    public void e(boolean z) {
        this.A.set(4, z);
    }

    public boolean e() {
        return this.A.get(0);
    }

    public boolean equals(Object obj) {
        return (obj != null && (obj instanceof i)) ? a((i) obj) : false;
    }

    public String f() {
        return this.c;
    }

    public boolean g() {
        return this.c != null;
    }

    public String h() {
        return this.d;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.d != null;
    }

    public String j() {
        return this.e;
    }

    public boolean k() {
        return this.e != null;
    }

    public int l() {
        return this.f;
    }

    public boolean m() {
        return this.A.get(1);
    }

    public boolean n() {
        return this.g != null;
    }

    public int o() {
        return this.h;
    }

    public boolean p() {
        return this.A.get(2);
    }

    public int q() {
        return this.i;
    }

    public boolean r() {
        return this.A.get(3);
    }

    public Map<String, String> s() {
        return this.j;
    }

    public boolean t() {
        return this.j != null;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("PushMetaInfo(");
        stringBuilder.append("id:");
        if (this.a == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.a);
        }
        stringBuilder.append(", ");
        stringBuilder.append("messageTs:");
        stringBuilder.append(this.b);
        if (g()) {
            stringBuilder.append(", ");
            stringBuilder.append("topic:");
            if (this.c == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.c);
            }
        }
        if (i()) {
            stringBuilder.append(", ");
            stringBuilder.append("title:");
            if (this.d == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.d);
            }
        }
        if (k()) {
            stringBuilder.append(", ");
            stringBuilder.append("description:");
            if (this.e == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.e);
            }
        }
        if (m()) {
            stringBuilder.append(", ");
            stringBuilder.append("notifyType:");
            stringBuilder.append(this.f);
        }
        if (n()) {
            stringBuilder.append(", ");
            stringBuilder.append("url:");
            if (this.g == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.g);
            }
        }
        if (p()) {
            stringBuilder.append(", ");
            stringBuilder.append("passThrough:");
            stringBuilder.append(this.h);
        }
        if (r()) {
            stringBuilder.append(", ");
            stringBuilder.append("notifyId:");
            stringBuilder.append(this.i);
        }
        if (t()) {
            stringBuilder.append(", ");
            stringBuilder.append("extra:");
            if (this.j == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.j);
            }
        }
        if (u()) {
            stringBuilder.append(", ");
            stringBuilder.append("internal:");
            if (this.k == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.k);
            }
        }
        if (w()) {
            stringBuilder.append(", ");
            stringBuilder.append("ignoreRegInfo:");
            stringBuilder.append(this.l);
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public boolean u() {
        return this.k != null;
    }

    public boolean v() {
        return this.l;
    }

    public boolean w() {
        return this.A.get(4);
    }

    public void x() {
        if (this.a == null) {
            throw new g("Required field 'id' was not present! Struct: " + toString());
        }
    }
}
