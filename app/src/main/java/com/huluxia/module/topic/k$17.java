package com.huluxia.module.topic;

import android.content.Context;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.a;
import com.huluxia.module.h;

/* compiled from: TopicModule2 */
class k$17 implements Listener<j> {
    final /* synthetic */ long aCE;
    final /* synthetic */ k aCN;
    final /* synthetic */ Context val$context;
    final /* synthetic */ int val$position;

    k$17(k this$0, int i, long j, Context context) {
        this.aCN = this$0;
        this.val$position = i;
        this.aCE = j;
        this.val$context = context;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((j) obj);
    }

    public void a(j info) {
        if (a.isDataSucc(info)) {
            EventNotifyCenter.notifyEvent(h.class, 768, new Object[]{Boolean.valueOf(true), info, Integer.valueOf(this.val$position), Long.valueOf(this.aCE), this.val$context});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, 768, new Object[]{Boolean.valueOf(false), info, Integer.valueOf(this.val$position), Long.valueOf(this.aCE), this.val$context});
    }
}
