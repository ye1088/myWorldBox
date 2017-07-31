package com.huluxia.module.topic;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.n;

class TopicModule$12 implements ErrorListener {
    final /* synthetic */ long aCE;
    final /* synthetic */ TopicModule aCF;

    TopicModule$12(TopicModule this$0, long j) {
        this.aCF = this$0;
        this.aCE = j;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error(this, "requestTopicDetail onErrorResponse e = " + error, new Object[0]);
        EventNotifyCenter.notifyEvent(n.class, 2304, new Object[]{Boolean.valueOf(false), null, Long.valueOf(this.aCE)});
    }
}
