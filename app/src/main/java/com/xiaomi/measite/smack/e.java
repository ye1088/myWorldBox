package com.xiaomi.measite.smack;

import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.smack.a;
import com.xiaomi.smack.d;
import java.util.Date;

class e implements d {
    final /* synthetic */ a a;

    e(a aVar) {
        this.a = aVar;
    }

    public void a(a aVar) {
        b.c("SMACK " + a.a(this.a).format(new Date()) + " Connection reconnected (" + a.b(this.a).hashCode() + ")");
    }

    public void a(a aVar, int i, Exception exception) {
        b.c("SMACK " + a.a(this.a).format(new Date()) + " Connection closed (" + a.b(this.a).hashCode() + ")");
    }

    public void a(a aVar, Exception exception) {
        b.c("SMACK " + a.a(this.a).format(new Date()) + " Reconnection failed due to an exception (" + a.b(this.a).hashCode() + ")");
        exception.printStackTrace();
    }

    public void b(a aVar) {
        b.c("SMACK " + a.a(this.a).format(new Date()) + " Connection started (" + a.b(this.a).hashCode() + ")");
    }
}
