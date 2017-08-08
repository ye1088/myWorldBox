package com.MCWorld.module.news;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: NewsModule */
class i$6 implements Listener<k> {
    final /* synthetic */ i aCh;
    final /* synthetic */ long aCj;

    i$6(i this$0, long j) {
        this.aCh = this$0;
        this.aCj = j;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((k) obj);
    }

    public void a(k info) {
        HLog.debug(this, "requestNewsList response %s", new Object[]{info});
        if (info == null || !info.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, h.asH, new Object[]{Boolean.valueOf(false), Long.valueOf(this.aCj), null});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, h.asH, new Object[]{Boolean.valueOf(true), Long.valueOf(this.aCj), info});
    }
}
