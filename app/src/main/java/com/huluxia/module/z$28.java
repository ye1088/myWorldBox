package com.huluxia.module;

import com.huluxia.data.map.f;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$28 implements Listener<f> {
    final /* synthetic */ int axC;

    z$28(int i) {
        this.axC = i;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((f) obj);
    }

    public void a(f response) {
        if (response == null || !response.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, h.asq, new Object[]{Integer.valueOf(this.axC), Boolean.valueOf(false), response});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, h.asq, new Object[]{Integer.valueOf(this.axC), Boolean.valueOf(true), response});
    }
}
