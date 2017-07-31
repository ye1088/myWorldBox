package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$35 implements Listener<w> {
    final /* synthetic */ z axD;
    final /* synthetic */ int val$id;

    z$35(z this$0, int i) {
        this.axD = this$0;
        this.val$id = i;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((w) obj);
    }

    public void a(w response) {
        if (response == null || !response.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, h.asu, new Object[]{Integer.valueOf(this.val$id), Boolean.valueOf(false), response});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, h.asu, new Object[]{Integer.valueOf(this.val$id), Boolean.valueOf(true), response});
    }
}
