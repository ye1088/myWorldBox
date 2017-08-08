package com.MCWorld.module.news;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: NewsModule */
class i$7 implements Listener<k> {
    final /* synthetic */ i aCh;

    i$7(i this$0) {
        this.aCh = this$0;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((k) obj);
    }

    public void a(k info) {
        HLog.debug(this, "requestNewsList response %s", new Object[]{info});
        if (info == null || !info.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, 1024, new Object[]{Boolean.valueOf(false), info});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, 1024, new Object[]{Boolean.valueOf(true), info});
    }
}
