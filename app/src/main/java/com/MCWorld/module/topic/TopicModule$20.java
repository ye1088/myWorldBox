package com.MCWorld.module.topic;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.f;
import com.MCWorld.module.n;

class TopicModule$20 implements Listener<String> {
    final /* synthetic */ TopicModule aCF;

    TopicModule$20(TopicModule this$0) {
        this.aCF = this$0;
    }

    public void onResponse(String response) {
        try {
            f info = (f) Json.parseJsonObject(response, f.class);
            if (info == null || !info.isSucc()) {
                EventNotifyCenter.notifyEvent(n.class, n.awL, new Object[]{Boolean.valueOf(false), null});
                return;
            }
            EventNotifyCenter.notifyEvent(n.class, n.awL, new Object[]{Boolean.valueOf(true), info.list});
        } catch (Exception e) {
            EventNotifyCenter.notifyEvent(n.class, n.awL, new Object[]{Boolean.valueOf(false), null});
        }
    }
}
