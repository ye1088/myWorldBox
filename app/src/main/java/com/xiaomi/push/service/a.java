package com.xiaomi.push.service;

import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.XMPushService.g;
import com.xiaomi.smack.packet.c;

public class a extends g {
    private XMPushService b = null;
    private c[] c;

    public a(XMPushService xMPushService, c[] cVarArr) {
        super(4);
        this.b = xMPushService;
        this.c = cVarArr;
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
        return "batch send message.";
    }
}
