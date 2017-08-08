package com.MCWorld.module.news;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;
import com.MCWorld.module.w;

/* compiled from: NewsModule */
class i$4 implements Listener<w> {
    final /* synthetic */ i aCh;
    final /* synthetic */ boolean aCi;

    i$4(i this$0, boolean z) {
        this.aCh = this$0;
        this.aCi = z;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((w) obj);
    }

    public void a(w info) {
        if (info == null || !info.isSucc()) {
            String msg = info == null ? "访问错误" : info.msg;
            EventNotifyCenter.notifyEvent(h.class, 1028, new Object[]{Boolean.valueOf(false), Boolean.valueOf(this.aCi), msg});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, 1028, new Object[]{Boolean.valueOf(true), Boolean.valueOf(this.aCi), null});
    }
}
