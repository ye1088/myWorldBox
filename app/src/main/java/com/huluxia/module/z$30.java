package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$30 implements Listener<w> {
    final /* synthetic */ int axB;
    final /* synthetic */ z axD;

    z$30(z this$0, int i) {
        this.axD = this$0;
        this.axB = i;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((w) obj);
    }

    public void a(w response) {
        if (response == null || !response.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, h.ass, new Object[]{Integer.valueOf(this.axB), Boolean.valueOf(false), response});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, h.ass, new Object[]{Integer.valueOf(this.axB), Boolean.valueOf(true), response});
    }
}
