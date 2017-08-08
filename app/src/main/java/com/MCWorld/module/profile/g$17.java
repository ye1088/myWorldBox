package com.MCWorld.module.profile;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: ProfileModule */
class g$17 implements Listener<a> {
    final /* synthetic */ g aCs;
    final /* synthetic */ String aCw;

    g$17(g this$0, String str) {
        this.aCs = this$0;
        this.aCw = str;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((a) obj);
    }

    public void a(a info) {
        if (info == null || !info.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, h.arw, new Object[]{info, null, this.aCw});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, h.arw, new Object[]{Boolean.valueOf(true), info, this.aCw});
    }
}
