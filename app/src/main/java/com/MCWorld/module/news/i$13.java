package com.MCWorld.module.news;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;
import com.MCWorld.module.w;

/* compiled from: NewsModule */
class i$13 implements Listener<w> {
    final /* synthetic */ i aCh;
    final /* synthetic */ String aCk;

    i$13(i this$0, String str) {
        this.aCh = this$0;
        this.aCk = str;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((w) obj);
    }

    public void a(w info) {
        if (info == null || !info.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, 1027, new Object[]{Boolean.valueOf(false), null, this.aCk});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, 1027, new Object[]{Boolean.valueOf(true), info.msg, this.aCk});
    }
}
