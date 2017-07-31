package com.huluxia.module.topic;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.n;

class TopicModule$3 implements Listener<String> {
    final /* synthetic */ TopicModule aCF;
    final /* synthetic */ long aCG;
    final /* synthetic */ long aCH;

    TopicModule$3(TopicModule this$0, long j, long j2) {
        this.aCF = this$0;
        this.aCG = j;
        this.aCH = j2;
    }

    public void onResponse(String response) {
        try {
            EventNotifyCenter.notifyEvent(n.class, n.awt, new Object[]{Boolean.valueOf(true), Long.valueOf(this.aCG), Long.valueOf(this.aCH)});
        } catch (Exception e) {
            HLog.error(this, "requestCommentRemove e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, n.awt, new Object[]{Boolean.valueOf(false), Long.valueOf(this.aCG), Long.valueOf(this.aCH)});
        }
    }
}
