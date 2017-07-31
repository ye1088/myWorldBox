package com.huluxia.module;

import com.huluxia.data.studio.b;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$32 implements Listener<b> {
    final /* synthetic */ int axB;
    final /* synthetic */ z axD;

    z$32(z this$0, int i) {
        this.axD = this$0;
        this.axB = i;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((b) obj);
    }

    public void a(b response) {
        if (response == null || !response.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, h.ast, new Object[]{Integer.valueOf(this.axB), Boolean.valueOf(false), response});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, h.ast, new Object[]{Integer.valueOf(this.axB), Boolean.valueOf(true), response});
    }
}
