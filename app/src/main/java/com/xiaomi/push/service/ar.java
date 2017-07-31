package com.xiaomi.push.service;

import com.xiaomi.push.service.XMPushService.g;
import com.xiaomi.smack.packet.f;
import com.xiaomi.smack.packet.f.b;

class ar extends g {
    final /* synthetic */ XMPushService b;

    ar(XMPushService xMPushService, int i) {
        this.b = xMPushService;
        super(i);
    }

    public void a() {
        if (this.b.h != null) {
            this.b.h.a(new f(b.unavailable), 15, null);
            this.b.h = null;
        }
    }

    public String b() {
        return "disconnect for service destroy.";
    }
}
