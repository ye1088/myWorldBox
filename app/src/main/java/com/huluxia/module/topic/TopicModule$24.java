package com.huluxia.module.topic;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.n;

class TopicModule$24 implements Listener<String> {
    final /* synthetic */ TopicModule aCF;

    TopicModule$24(TopicModule this$0) {
        this.aCF = this$0;
    }

    public void onResponse(String response) {
        try {
            EventNotifyCenter.notifyEvent(n.class, n.awq, new Object[]{Boolean.valueOf(true)});
        } catch (Exception e) {
            HLog.error(this, "requestTopicLock e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, n.awq, new Object[]{Boolean.valueOf(false)});
        }
    }
}
