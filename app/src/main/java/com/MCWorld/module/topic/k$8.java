package com.MCWorld.module.topic;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: TopicModule2 */
class k$8 implements Listener<m> {
    final /* synthetic */ k aCN;

    k$8(k this$0) {
        this.aCN = this$0;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((m) obj);
    }

    public void a(m category) {
        boolean z;
        Class cls = h.class;
        Object[] objArr = new Object[2];
        if (category != null) {
            z = true;
        } else {
            z = false;
        }
        objArr[0] = Boolean.valueOf(z);
        objArr[1] = category;
        EventNotifyCenter.notifyEventUiThread(cls, h.arO, objArr);
    }
}
