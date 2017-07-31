package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$15 implements Listener<w> {
    z$15() {
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((w) obj);
    }

    public void a(w info) {
        if (info == null || !info.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, 788, new Object[]{Boolean.valueOf(false), info});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, 788, new Object[]{Boolean.valueOf(true), info});
    }
}
