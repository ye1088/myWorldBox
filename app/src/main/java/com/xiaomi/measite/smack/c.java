package com.xiaomi.measite.smack;

import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.smack.util.m;
import java.util.Date;

class c implements m {
    final /* synthetic */ a a;

    c(a aVar) {
        this.a = aVar;
    }

    public void a(String str) {
        b.c("SMACK " + a.a(this.a).format(new Date()) + " SENT (" + a.b(this.a).hashCode() + "): " + str);
    }
}
