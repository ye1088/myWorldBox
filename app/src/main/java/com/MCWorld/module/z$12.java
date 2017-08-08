package com.MCWorld.module;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$12 implements Listener<y> {
    final /* synthetic */ Object amk;
    final /* synthetic */ long axx;

    z$12(long j, Object obj) {
        this.axx = j;
        this.amk = obj;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((y) obj);
    }

    public void a(y info) {
        if (info == null || !info.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, 776, new Object[]{Boolean.valueOf(false), info, Long.valueOf(this.axx), this.amk});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, 776, new Object[]{Boolean.valueOf(true), info, Long.valueOf(this.axx), this.amk});
    }
}
