package com.MCWorld.module.topic;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.n;

class TopicModule$19 implements ErrorListener {
    final /* synthetic */ TopicModule aCF;

    TopicModule$19(TopicModule this$0) {
        this.aCF = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(n.class, n.awA, new Object[]{Boolean.valueOf(false), Boolean.valueOf(false), "访问失败"});
    }
}
