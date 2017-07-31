package com.huluxia.module;

import com.huluxia.data.studio.f;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$10 implements Listener<f> {
    final /* synthetic */ int axz;

    z$10(int i) {
        this.axz = i;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((f) obj);
    }

    public void a(f response) {
        if (response == null || !response.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, h.asn, new Object[]{Integer.valueOf(this.axz), Boolean.valueOf(false), null});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, h.asn, new Object[]{Integer.valueOf(this.axz), Boolean.valueOf(true), response});
    }
}
