package com.MCWorld.module.topic;

import com.MCWorld.data.topic.e;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.n;

class TopicModule$1 implements Listener<String> {
    final /* synthetic */ long aCE;
    final /* synthetic */ TopicModule aCF;

    TopicModule$1(TopicModule this$0, long j) {
        this.aCF = this$0;
        this.aCE = j;
    }

    public void onResponse(String response) {
        try {
            e info = (e) Json.parseJsonObject(response, e.class);
            EventNotifyCenter.notifyEvent(n.class, 2304, new Object[]{Boolean.valueOf(true), info, Long.valueOf(this.aCE)});
        } catch (Exception e) {
            EventNotifyCenter.notifyEvent(n.class, 2304, new Object[]{Boolean.valueOf(false), null, Long.valueOf(this.aCE)});
        }
    }
}
