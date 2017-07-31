package com.xiaomi.smack;

import android.text.TextUtils;
import com.tencent.connect.common.Constants;
import com.xiaomi.push.service.x.b;
import com.xiaomi.smack.packet.a;
import com.xiaomi.smack.packet.d;
import com.xiaomi.smack.util.g;
import java.util.HashMap;
import java.util.Map;

public class k$a extends d {
    public k$a(b bVar, String str, a aVar) {
        Object obj;
        Map hashMap = new HashMap();
        hashMap.put("challenge", str);
        hashMap.put("token", bVar.c);
        hashMap.put("chid", bVar.h);
        hashMap.put("from", bVar.b);
        hashMap.put("id", k());
        hashMap.put("to", "xiaomi.com");
        if (bVar.e) {
            hashMap.put("kick", "1");
        } else {
            hashMap.put("kick", "0");
        }
        if (aVar == null || aVar.l() <= 0) {
            obj = null;
        } else {
            String format = String.format("conn:%1$d,t:%2$d", new Object[]{Integer.valueOf(aVar.j()), Long.valueOf(aVar.l())});
            hashMap.put(Constants.PARAM_PLATFORM_ID, format);
            aVar.k();
            aVar.m();
            obj = format;
        }
        if (TextUtils.isEmpty(bVar.f)) {
            hashMap.put("client_attrs", "");
        } else {
            hashMap.put("client_attrs", bVar.f);
        }
        if (TextUtils.isEmpty(bVar.g)) {
            hashMap.put("cloud_attrs", "");
        } else {
            hashMap.put("cloud_attrs", bVar.g);
        }
        String a = (bVar.d.equals("XIAOMI-PASS") || bVar.d.equals("XMPUSH-PASS")) ? com.xiaomi.channel.commonutils.string.b.a(bVar.d, null, hashMap, bVar.i) : bVar.d.equals("XIAOMI-SASL") ? null : null;
        l(bVar.h);
        n(bVar.b);
        m("xiaomi.com");
        o(bVar.a);
        a aVar2 = new a("token", null, (String[]) null, (String[]) null);
        aVar2.b(bVar.c);
        a(aVar2);
        aVar2 = new a("kick", null, (String[]) null, (String[]) null);
        aVar2.b(bVar.e ? "1" : "0");
        a(aVar2);
        aVar2 = new a("sig", null, (String[]) null, (String[]) null);
        aVar2.b(a);
        a(aVar2);
        a aVar3 = new a("method", null, (String[]) null, (String[]) null);
        if (TextUtils.isEmpty(bVar.d)) {
            aVar3.b("XIAOMI-SASL");
        } else {
            aVar3.b(bVar.d);
        }
        a(aVar3);
        aVar3 = new a("client_attrs", null, (String[]) null, (String[]) null);
        aVar3.b(bVar.f == null ? "" : g.a(bVar.f));
        a(aVar3);
        aVar3 = new a("cloud_attrs", null, (String[]) null, (String[]) null);
        aVar3.b(bVar.g == null ? "" : g.a(bVar.g));
        a(aVar3);
        if (!TextUtils.isEmpty(obj)) {
            aVar3 = new a(Constants.PARAM_PLATFORM_ID, null, (String[]) null, (String[]) null);
            aVar3.b(obj);
            a(aVar3);
        }
    }

    public String a() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<bind ");
        if (k() != null) {
            stringBuilder.append("id=\"" + k() + "\" ");
        }
        if (m() != null) {
            stringBuilder.append("to=\"").append(g.a(m())).append("\" ");
        }
        if (n() != null) {
            stringBuilder.append("from=\"").append(g.a(n())).append("\" ");
        }
        if (l() != null) {
            stringBuilder.append("chid=\"").append(g.a(l())).append("\">");
        }
        if (q() != null) {
            for (a d : q()) {
                stringBuilder.append(d.d());
            }
        }
        stringBuilder.append("</bind>");
        return stringBuilder.toString();
    }
}
