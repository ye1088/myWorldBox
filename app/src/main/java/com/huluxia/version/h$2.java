package com.huluxia.version;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;

/* compiled from: VersionModule */
class h$2 implements Listener<a> {
    final /* synthetic */ h boB;

    h$2(h this$0) {
        this.boB = this$0;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((a) obj);
    }

    public void a(a info) {
        if (info == null || !info.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, 774, new Object[]{Boolean.valueOf(false), info});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, 774, new Object[]{Boolean.valueOf(true), info});
    }
}
