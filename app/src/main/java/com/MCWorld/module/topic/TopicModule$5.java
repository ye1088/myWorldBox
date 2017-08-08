package com.MCWorld.module.topic;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.n;

class TopicModule$5 implements Listener<String> {
    final /* synthetic */ TopicModule aCF;

    TopicModule$5(TopicModule this$0) {
        this.aCF = this$0;
    }

    public void onResponse(String response) {
        try {
            EventNotifyCenter.notifyEvent(n.class, n.arp, new Object[]{Boolean.valueOf(true)});
        } catch (Exception e) {
            HLog.error(this, "requestComplaint e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, n.arp, new Object[]{Boolean.valueOf(false)});
        }
    }
}
