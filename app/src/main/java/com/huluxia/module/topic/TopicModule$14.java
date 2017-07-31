package com.huluxia.module.topic;

import com.huluxia.data.category.TopicCategory;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.n;

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
