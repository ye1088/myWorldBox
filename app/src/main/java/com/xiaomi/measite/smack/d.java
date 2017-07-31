package com.xiaomi.measite.smack;

import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.smack.f;
import java.util.Date;

class d implements f {
    final /* synthetic */ a a;

    d(a aVar) {
        this.a = aVar;
    }

    public void a(com.xiaomi.smack.packet.d dVar) {
        if (a.a) {
            b.c("SMACK " + a.a(this.a).format(new Date()) + " RCV PKT (" + a.b(this.a).hashCode() + "): " + dVar.a());
        }
    }
}
