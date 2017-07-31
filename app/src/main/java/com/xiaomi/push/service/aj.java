package com.xiaomi.push.service;

import com.xiaomi.push.service.XMPushService.g;

class aj extends g {
    final /* synthetic */ XMPushService b;

    aj(XMPushService xMPushService, int i) {
        this.b = xMPushService;
        super(i);
    }

    public void a() {
        if (this.b.f()) {
            this.b.a(18, null);
        }
    }

    public String b() {
        return "disconnect because of connecting timeout";
    }
}
