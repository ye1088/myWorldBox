package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$19 implements Listener<w> {
    z$19() {
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((w) obj);
    }

    public void a(w response) {
        if (response == null || !response.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, h.asr, new Object[]{Boolean.valueOf(false), response});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, h.asr, new Object[]{Boolean.valueOf(true), response});
    }
}
