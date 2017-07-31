package com.xiaomi.smack;

import com.xiaomi.push.service.XMPushService.g;

class m extends g {
    final /* synthetic */ long b;
    final /* synthetic */ l c;

    m(l lVar, int i, long j) {
        this.c = lVar;
        this.b = j;
        super(i);
    }

    public void a() {
        if (this.c.i() && !this.c.a(this.b)) {
            this.c.y.a(22, null);
            this.c.y.a(true);
        }
    }

    public String b() {
        return "check the ping-pong." + this.b;
    }
}
