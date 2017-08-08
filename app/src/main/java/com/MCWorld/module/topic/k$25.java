package com.MCWorld.module.topic;

import com.MCWorld.data.topic.TopicItem;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;
import com.MCWorld.module.w;

/* compiled from: TopicModule2 */
class k$25 implements Listener<w> {
    final /* synthetic */ k aCN;
    final /* synthetic */ TopicItem aCQ;
    final /* synthetic */ boolean aCR;

    k$25(k this$0, TopicItem topicItem, boolean z) {
        this.aCN = this$0;
        this.aCQ = topicItem;
        this.aCR = z;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((w) obj);
    }

    public void a(w info) {
        if (info == null || !info.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, h.aru, new Object[]{Boolean.valueOf(false), info, Long.valueOf(this.aCQ.getPostID()), Boolean.valueOf(this.aCR)});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, h.aru, new Object[]{Boolean.valueOf(true), info, Long.valueOf(this.aCQ.getPostID()), Boolean.valueOf(this.aCR)});
    }
}
