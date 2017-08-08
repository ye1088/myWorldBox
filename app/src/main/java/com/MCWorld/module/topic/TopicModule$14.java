package com.MCWorld.module.topic;

import com.MCWorld.data.category.TopicCategory;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.n;

class TopicModule$14 implements Listener<String> {
    final /* synthetic */ TopicModule aCF;

    TopicModule$14(TopicModule this$0) {
        this.aCF = this$0;
    }

    public void onResponse(String response) {
        try {
            if (((TopicCategory) Json.parseJsonObject(response, TopicCategory.class)) != null) {
                EventNotifyCenter.notifyEvent(n.class, 297, new Object[]{Boolean.valueOf(true), (TopicCategory) Json.parseJsonObject(response, TopicCategory.class)});
                return;
            }
            EventNotifyCenter.notifyEvent(n.class, 297, new Object[]{Boolean.valueOf(false), null});
        } catch (Exception e) {
            EventNotifyCenter.notifyEvent(n.class, 297, new Object[]{Boolean.valueOf(false), null});
        }
    }
}
