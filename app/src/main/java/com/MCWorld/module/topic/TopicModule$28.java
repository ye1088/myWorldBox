package com.MCWorld.module.topic;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.n;

class TopicModule$28 implements Listener<String> {
    final /* synthetic */ TopicModule aCF;
    final /* synthetic */ long aCG;

    TopicModule$28(TopicModule this$0, long j) {
        this.aCF = this$0;
        this.aCG = j;
    }

    public void onResponse(String response) {
        try {
            EventNotifyCenter.notifyEvent(n.class, n.aws, new Object[]{Boolean.valueOf(true), Long.valueOf(this.aCG)});
        } catch (Exception e) {
            HLog.error(this, "requestTopicRemove e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, n.aws, new Object[]{Boolean.valueOf(false), Long.valueOf(this.aCG)});
        }
    }
}
