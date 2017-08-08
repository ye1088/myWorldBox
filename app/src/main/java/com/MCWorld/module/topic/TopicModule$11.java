package com.MCWorld.module.topic;

import com.MCWorld.data.topic.d;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.n;

class TopicModule$11 implements Listener<String> {
    final /* synthetic */ TopicModule aCF;
    final /* synthetic */ long aCG;

    TopicModule$11(TopicModule this$0, long j) {
        this.aCF = this$0;
        this.aCG = j;
    }

    public void onResponse(String response) {
        try {
            boolean z;
            d info = (d) Json.parseJsonObject(response, d.class);
            Class cls = n.class;
            Object[] objArr = new Object[3];
            objArr[0] = Boolean.valueOf(true);
            objArr[1] = Long.valueOf(this.aCG);
            if (info.isFavorite == 1) {
                z = true;
            } else {
                z = false;
            }
            objArr[2] = Boolean.valueOf(z);
            EventNotifyCenter.notifyEvent(cls, n.aww, objArr);
        } catch (Exception e) {
            HLog.error(this, "requestFavoriteTopicCheck e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, n.aww, new Object[]{Boolean.valueOf(false), Long.valueOf(this.aCG), Boolean.valueOf(false)});
        }
    }
}
