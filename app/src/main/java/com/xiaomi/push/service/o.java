package com.xiaomi.push.service;

import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.XMPushService.g;

final class o extends g {
    final /* synthetic */ XMPushService b;
    final /* synthetic */ com.xiaomi.xmpush.thrift.o c;

    o(int i, XMPushService xMPushService, com.xiaomi.xmpush.thrift.o oVar) {
        this.b = xMPushService;
        this.c = oVar;
        super(i);
    }

    public void a() {
        try {
            com.xiaomi.xmpush.thrift.o a = k.a(this.b, this.c);
            a.m().a("miui_message_unrecognized", "1");
            this.b.a(a);
        } catch (Throwable e) {
            b.a(e);
            this.b.a(10, e);
        }
    }

    public String b() {
        return "send ack message for unrecognized new miui message.";
    }
}
