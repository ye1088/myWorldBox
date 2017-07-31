package com.huluxia.module.news;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;

/* compiled from: NewsModule */
class i$2 implements Listener<b> {
    final /* synthetic */ i aCh;

    i$2(i this$0) {
        this.aCh = this$0;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((b) obj);
    }

    public void a(b info) {
        if (info == null || !info.isSucc()) {
            String msg = info == null ? "访问失败" : info.msg;
            EventNotifyCenter.notifyEvent(h.class, h.asF, new Object[]{Boolean.valueOf(false), Boolean.valueOf(false), msg});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, h.asF, new Object[]{Boolean.valueOf(true), Boolean.valueOf(info.isFavorite()), null});
    }
}
