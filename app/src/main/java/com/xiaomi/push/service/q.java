package com.xiaomi.push.service;

import com.huluxia.framework.base.http.toolbox.download.DownloadRecord;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.XMPushService.g;
import com.xiaomi.xmpush.thrift.o;

final class q extends g {
    final /* synthetic */ XMPushService b;
    final /* synthetic */ o c;
    final /* synthetic */ String d;
    final /* synthetic */ String e;

    q(int i, XMPushService xMPushService, o oVar, String str, String str2) {
        this.b = xMPushService;
        this.c = oVar;
        this.d = str;
        this.e = str2;
        super(i);
    }

    public void a() {
        try {
            o a = k.a(this.b, this.c);
            a.h.a(DownloadRecord.COLUMN_ERROR, this.d);
            a.h.a("reason", this.e);
            this.b.a(a);
        } catch (Throwable e) {
            b.a(e);
            this.b.a(10, e);
        }
    }

    public String b() {
        return "send wrong message ack for message.";
    }
}
