package com.huluxia.module.news;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;

/* compiled from: NewsModule */
class i$11 implements Listener<e> {
    final /* synthetic */ i aCh;

    i$11(i this$0) {
        this.aCh = this$0;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((e) obj);
    }

    public void a(e info) {
        if (info == null || !info.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, 1026, new Object[]{Boolean.valueOf(false), null});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, 1026, new Object[]{Boolean.valueOf(true), info});
    }
}
