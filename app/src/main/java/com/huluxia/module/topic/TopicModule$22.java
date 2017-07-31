package com.huluxia.module.topic;

import com.huluxia.data.topic.b;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.n;

class TopicModule$22 implements Listener<String> {
    final /* synthetic */ TopicModule aCF;

    TopicModule$22(TopicModule this$0) {
        this.aCF = this$0;
    }

    public void onResponse(String response) {
        try {
            b info = (b) Json.parseJsonObject(response, b.class);
            EventNotifyCenter.notifyEvent(n.class, 2306, new Object[]{Boolean.valueOf(true), info});
        } catch (Exception e) {
            HLog.error(this, "requestCreditTransfer e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, 2306, new Object[]{Boolean.valueOf(false), null});
        }
    }
}
