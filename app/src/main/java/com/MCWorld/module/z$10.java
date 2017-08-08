package com.MCWorld.module;

import com.MCWorld.data.studio.f;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

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
