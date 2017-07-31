package com.huluxia.module.topic;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.l;
import com.huluxia.module.n;

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
