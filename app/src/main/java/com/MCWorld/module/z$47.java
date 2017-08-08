package com.MCWorld.module;

import com.MCWorld.data.profile.c;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$47 implements Listener<c> {
    final /* synthetic */ Object amk;
    final /* synthetic */ int axy;

    z$47(int i, Object obj) {
        this.axy = i;
        this.amk = obj;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((c) obj);
    }

    public void a(c info) {
        HLog.debug("StudioModule", "-->" + info.toString(), new Object[0]);
        if (info == null || !info.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, 784, new Object[]{Boolean.valueOf(false), info, Integer.valueOf(this.axy), this.amk});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, 784, new Object[]{Boolean.valueOf(true), info, Integer.valueOf(this.axy), this.amk});
    }
}
