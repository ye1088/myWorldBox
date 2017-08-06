package com.xiaomi.push.service;

import android.util.Base64;
import com.xiaomi.channel.commonutils.misc.f.b;
import com.xiaomi.network.HttpUtils;
import com.xiaomi.push.protobuf.a.a;
import com.xiaomi.smack.util.h;

class ah extends b {
    boolean a = false;
    final /* synthetic */ ag b;

    ah(ag agVar) {
        this.b = agVar;
    }

    public void b() {
        try {
            a b = a.b(Base64.decode(HttpUtils.a(h.a(), "http://resolver.msg.xiaomi.net/psc/?t=a_isRightVersion", null), 10));
            if (b != null) {
                ag.a(this.b, b);
                this.a = true;
                ag.a(this.b);
            }
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.a("fetch config failure: " + e.getMessage());
        }
    }

    public void c() {
        ag.a(this.b, null);
        if (this.a) {
            synchronized (this.b) {
            }
            for (ag.a a : (ag.a[]) ag.b(this.b).toArray(new ag.a[ag.b(this.b).size()])) {
                a.a(ag.c(this.b));
            }
        }
    }
}
