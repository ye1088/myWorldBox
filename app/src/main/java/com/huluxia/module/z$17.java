package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$17 implements Listener<w> {
    final /* synthetic */ int axy;

    z$17(int i) {
        this.axy = i;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((w) obj);
    }

    public void a(w info) {
        if (info == null || !info.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, 785, new Object[]{Boolean.valueOf(false), info, Integer.valueOf(this.axy)});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, 785, new Object[]{Boolean.valueOf(true), info, Integer.valueOf(this.axy)});
    }
}
