package com.MCWorld.module.topic;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: TopicModule2 */
class k$10 implements Listener<c> {
    final /* synthetic */ k aCN;
    final /* synthetic */ int val$id;

    k$10(k this$0, int i) {
        this.aCN = this$0;
        this.val$id = i;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((c) obj);
    }

    public void a(c category) {
        boolean z;
        Class cls = h.class;
        Object[] objArr = new Object[3];
        if (category != null) {
            z = true;
        } else {
            z = false;
        }
        objArr[0] = Boolean.valueOf(z);
        objArr[1] = category;
        objArr[2] = Integer.valueOf(this.val$id);
        EventNotifyCenter.notifyEventUiThread(cls, h.arP, objArr);
    }
}
