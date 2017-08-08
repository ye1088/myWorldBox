package com.MCWorld.module.news;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: NewsModule */
class i$9 implements Listener<e> {
    final /* synthetic */ i aCh;

    i$9(i this$0) {
        this.aCh = this$0;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((e) obj);
    }

    public void a(e info) {
        if (info == null || !info.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, 1025, new Object[]{Boolean.valueOf(false), null});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, 1025, new Object[]{Boolean.valueOf(true), info});
    }
}
