package com.huluxia.module.topic;

import com.huluxia.data.topic.f;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.n;

class TopicModule$7 implements Listener<String> {
    final /* synthetic */ TopicModule aCF;

    TopicModule$7(TopicModule this$0) {
        this.aCF = this$0;
    }

    public void onResponse(String response) {
        try {
            f info = (f) Json.parseJsonObject(response, f.class);
            EventNotifyCenter.notifyEvent(n.class, n.awu, new Object[]{Boolean.valueOf(true), info.address});
        } catch (Exception e) {
            HLog.error(this, "requestTopicShare e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, n.awu, new Object[]{Boolean.valueOf(false), null});
        }
    }
}
