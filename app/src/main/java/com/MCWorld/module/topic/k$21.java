package com.MCWorld.module.topic;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: TopicModule2 */
class k$21 implements Listener<h> {
    final /* synthetic */ k aCN;

    k$21(k this$0) {
        this.aCN = this$0;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((h) obj);
    }

    public void a(h info) {
        if (info == null || !info.isSucc()) {
            EventNotifyCenter.notifyEventUiThread(h.class, h.arg, new Object[]{Boolean.valueOf(false), null});
            return;
        }
        EventNotifyCenter.notifyEventUiThread(h.class, h.arg, new Object[]{Boolean.valueOf(true), info});
    }
}
