package com.MCWorld.module.topic;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.l;
import com.MCWorld.module.n;

class TopicModule$16 implements Listener<String> {
    final /* synthetic */ TopicModule aCF;

    TopicModule$16(TopicModule this$0) {
        this.aCF = this$0;
    }

    public void onResponse(String response) {
        try {
            l info = (l) Json.parseJsonObject(response, l.class);
            if (info == null || !info.isSucc()) {
                EventNotifyCenter.notifyEvent(n.class, n.awz, new Object[]{Boolean.valueOf(false), Boolean.valueOf(false)});
                return;
            }
            EventNotifyCenter.notifyEvent(n.class, n.awz, new Object[]{Boolean.valueOf(true), Boolean.valueOf(info.ispower)});
        } catch (Exception e) {
            EventNotifyCenter.notifyEvent(n.class, n.awz, new Object[]{Boolean.valueOf(false), Boolean.valueOf(false)});
        }
    }
}
