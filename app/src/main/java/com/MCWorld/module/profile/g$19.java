package com.MCWorld.module.profile;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: ProfileModule */
class g$19 implements Listener<i> {
    final /* synthetic */ g aCs;

    g$19(g this$0) {
        this.aCs = this$0;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((i) obj);
    }

    public void a(i info) {
        if (info == null || !info.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, h.art, new Object[]{Boolean.valueOf(false), info});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, h.art, new Object[]{Boolean.valueOf(true), info});
    }
}
