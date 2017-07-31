package com.huluxia.module.topic;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.n;

class TopicModule$19 implements ErrorListener {
    final /* synthetic */ TopicModule aCF;

    TopicModule$19(TopicModule this$0) {
        this.aCF = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(n.class, n.awA, new Object[]{Boolean.valueOf(false), Boolean.valueOf(false), "访问失败"});
    }
}
