package com.xiaomi.measite.smack;

import com.xiaomi.smack.util.f;
import java.util.Date;

class b implements f {
    final /* synthetic */ a a;

    b(a aVar) {
        this.a = aVar;
    }

    public void a(String str) {
        com.xiaomi.channel.commonutils.logger.b.c("SMACK " + a.a(this.a).format(new Date()) + " RCV  (" + a.b(this.a).hashCode() + "): " + str);
    }
}
