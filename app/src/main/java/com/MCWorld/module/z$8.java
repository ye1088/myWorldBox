package com.MCWorld.module;

import com.MCWorld.data.studio.e;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$8 implements Listener<e> {
    final /* synthetic */ int axz;

    z$8(int i) {
        this.axz = i;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((e) obj);
    }

    public void a(e response) {
        if (response == null || !response.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, h.asp, new Object[]{Integer.valueOf(this.axz), Boolean.valueOf(false), null});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, h.asp, new Object[]{Integer.valueOf(this.axz), Boolean.valueOf(true), response});
    }
}
