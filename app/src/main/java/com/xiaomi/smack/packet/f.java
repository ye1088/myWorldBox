package com.xiaomi.smack.packet;

import android.os.Bundle;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.xiaomi.smack.util.g;

public class f extends d {
    private b c = b.available;
    private String d = null;
    private int e = Integer.MIN_VALUE;
    private a f = null;

    public enum b {
        available,
        unavailable,
        subscribe,
        subscribed,
        unsubscribe,
        unsubscribed,
        error,
        probe
    }

    public f(Bundle bundle) {
        super(bundle);
        if (bundle.containsKey("ext_pres_type")) {
            this.c = b.valueOf(bundle.getString("ext_pres_type"));
        }
        if (bundle.containsKey("ext_pres_status")) {
            this.d = bundle.getString("ext_pres_status");
        }
        if (bundle.containsKey("ext_pres_prio")) {
            this.e = bundle.getInt("ext_pres_prio");
        }
        if (bundle.containsKey("ext_pres_mode")) {
            this.f = a.valueOf(bundle.getString("ext_pres_mode"));
        }
    }

    public f(b bVar) {
        a(bVar);
    }

    public String a() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<presence");
        if (t() != null) {
            stringBuilder.append(" xmlns=\"").append(t()).append("\"");
        }
        if (k() != null) {
            stringBuilder.append(" id=\"").append(k()).append("\"");
        }
        if (m() != null) {
            stringBuilder.append(" to=\"").append(g.a(m())).append("\"");
        }
        if (n() != null) {
            stringBuilder.append(" from=\"").append(g.a(n())).append("\"");
        }
        if (l() != null) {
            stringBuilder.append(" chid=\"").append(g.a(l())).append("\"");
        }
        if (this.c != null) {
            stringBuilder.append(" type=\"").append(this.c).append("\"");
        }
        stringBuilder.append(SimpleComparison.GREATER_THAN_OPERATION);
        if (this.d != null) {
            stringBuilder.append("<status>").append(g.a(this.d)).append("</status>");
        }
        if (this.e != Integer.MIN_VALUE) {
            stringBuilder.append("<priority>").append(this.e).append("</priority>");
        }
        if (!(this.f == null || this.f == a.b)) {
            stringBuilder.append("<show>").append(this.f).append("</show>");
        }
        stringBuilder.append(s());
        h p = p();
        if (p != null) {
            stringBuilder.append(p.d());
        }
        stringBuilder.append("</presence>");
        return stringBuilder.toString();
    }

    public void a(int i) {
        if (i < -128 || i > 128) {
            throw new IllegalArgumentException("Priority value " + i + " is not valid. Valid range is -128 through 128.");
        }
        this.e = i;
    }

    public void a(a aVar) {
        this.f = aVar;
    }

    public void a(b bVar) {
        if (bVar == null) {
            throw new NullPointerException("Type cannot be null");
        }
        this.c = bVar;
    }

    public void a(String str) {
        this.d = str;
    }

    public Bundle c() {
        Bundle c = super.c();
        if (this.c != null) {
            c.putString("ext_pres_type", this.c.toString());
        }
        if (this.d != null) {
            c.putString("ext_pres_status", this.d);
        }
        if (this.e != Integer.MIN_VALUE) {
            c.putInt("ext_pres_prio", this.e);
        }
        if (!(this.f == null || this.f == a.b)) {
            c.putString("ext_pres_mode", this.f.toString());
        }
        return c;
    }
}
