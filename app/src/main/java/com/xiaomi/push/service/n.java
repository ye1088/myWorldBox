package com.xiaomi.push.service;

import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.XMPushService.g;
import com.xiaomi.xmpush.thrift.o;

final class n extends g {
    final /* synthetic */ XMPushService b;
    final /* synthetic */ o c;

    n(int i, XMPushService xMPushService, o oVar) {
        this.b = xMPushService;
        this.c = oVar;
        super(i);
    }

    public void a() {
        try {
            o a = k.a(this.b, this.c);
            a.m().a("message_obsleted", "1");
            this.b.a(a);
        } catch (Throwable e) {
            b.a(e);
            this.b.a(10, e);
        }
    }

    public String b() {
        return "send ack message for obsleted message.";
    }
}
