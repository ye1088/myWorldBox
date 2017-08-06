package com.xiaomi.push.service;

import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.XMPushService.g;
import com.xiaomi.smack.packet.d;

public class af extends g {
    private XMPushService b = null;
    private d c;

    public af(XMPushService xMPushService, d dVar) {
        super(4);
        this.b = xMPushService;
        this.c = dVar;
    }

    public void a() {
        try {
            this.b.a(this.c);
        } catch (Exception e) {
            b.a((Throwable) e);
            this.b.a(10, e);
        }
    }

    public String b() {
        return "send a_isRightVersion message.";
    }
}
