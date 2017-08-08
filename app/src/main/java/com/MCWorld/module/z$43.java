package com.MCWorld.module;

import com.MCWorld.data.studio.b;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$43 implements Listener<b> {
    final /* synthetic */ int axB;
    final /* synthetic */ z axD;

    z$43(z this$0, int i) {
        this.axD = this$0;
        this.axB = i;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((b) obj);
    }

    public void a(b response) {
        if (response == null || !response.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, h.asz, new Object[]{Integer.valueOf(this.axB), Boolean.valueOf(false), response});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, h.asz, new Object[]{Integer.valueOf(this.axB), Boolean.valueOf(true), response});
    }
}
