package com.huluxia.module.topic;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.n;
import com.huluxia.module.w;

class TopicModule$18 implements Listener<String> {
    final /* synthetic */ TopicModule aCF;

    TopicModule$18(TopicModule this$0) {
        this.aCF = this$0;
    }

    public void onResponse(String response) {
        try {
            w info = (w) Json.parseJsonObject(response, w.class);
            if (info != null && info.isSucc()) {
                EventNotifyCenter.notifyEvent(n.class, n.awA, new Object[]{Boolean.valueOf(true), Boolean.valueOf(true), null});
            } else if (info == null || info.code != 104) {
                EventNotifyCenter.notifyEvent(n.class, n.awA, new Object[]{Boolean.valueOf(false), Boolean.valueOf(false), "服务器异常"});
            } else {
                EventNotifyCenter.notifyEvent(n.class, n.awA, new Object[]{Boolean.valueOf(false), Boolean.valueOf(true), info.msg});
            }
        } catch (Exception e) {
            EventNotifyCenter.notifyEvent(n.class, n.awA, new Object[]{Boolean.valueOf(false), Boolean.valueOf(false), "服务器异常"});
        }
    }
}
