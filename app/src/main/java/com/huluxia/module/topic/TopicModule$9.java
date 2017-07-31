package com.huluxia.module.topic;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.n;

class TopicModule$9 implements Listener<String> {
    final /* synthetic */ TopicModule aCF;
    final /* synthetic */ long aCG;
    final /* synthetic */ boolean aCI;

    TopicModule$9(TopicModule this$0, long j, boolean z) {
        this.aCF = this$0;
        this.aCG = j;
        this.aCI = z;
    }

    public void onResponse(String response) {
        try {
            EventNotifyCenter.notifyEvent(n.class, n.awv, new Object[]{Boolean.valueOf(true), Long.valueOf(this.aCG), Boolean.valueOf(this.aCI)});
        } catch (Exception e) {
            HLog.error(this, "requestFavoriteTopic e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, n.awv, new Object[]{Boolean.valueOf(false), Long.valueOf(this.aCG), Boolean.valueOf(this.aCI)});
        }
    }
}
