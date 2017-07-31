package com.xiaomi.push.service;

import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.XMPushService.g;
import com.xiaomi.xmpush.thrift.o;

final class m extends g {
    final /* synthetic */ XMPushService b;
    final /* synthetic */ o c;

    m(int i, XMPushService xMPushService, o oVar) {
        this.b = xMPushService;
        this.c = oVar;
        super(i);
    }

    public void a() {
        try {
            this.b.a(k.a(this.b, this.c));
        } catch (Throwable e) {
            b.a(e);
            this.b.a(10, e);
        }
    }

    public String b() {
        return "send ack message for message.";
    }
}
