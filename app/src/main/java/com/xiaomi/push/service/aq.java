package com.xiaomi.push.service;

import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.push.service.x.b.a;
import com.xiaomi.push.service.x.c;

class aq implements a {
    final /* synthetic */ XMPushService a;

    aq(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    public void a(c cVar, c cVar2, int i) {
        if (cVar2 == c.binded) {
            j.a(this.a);
            j.b(this.a);
        } else if (cVar2 == c.unbind) {
            j.a(this.a, ErrorCode.ERROR_SERVICE_UNAVAILABLE, " the push is not connected.");
        }
    }
}
