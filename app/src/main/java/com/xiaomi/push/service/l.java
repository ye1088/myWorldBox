package com.xiaomi.push.service;

import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.XMPushService.g;
import com.xiaomi.xmpush.thrift.o;

final class l extends g {
    final /* synthetic */ XMPushService b;
    final /* synthetic */ o c;

    l(int i, XMPushService xMPushService, o oVar) {
        this.b = xMPushService;
        this.c = oVar;
        super(i);
    }

    public void a() {
        try {
            this.b.a(this.b.a(this.c.j(), this.c.h()));
        } catch (Throwable e) {
            b.a(e);
            this.b.a(10, e);
        }
    }

    public String b() {
        return "send app absent message.";
    }
}
