package com.MCWorld.module.topic;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.n;

class TopicModule$13 implements ErrorListener {
    final /* synthetic */ TopicModule aCF;
    final /* synthetic */ long aCG;

    TopicModule$13(TopicModule this$0, long j) {
        this.aCF = this$0;
        this.aCG = j;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error(this, "requestFavoriteTopicCheck onErrorResponse e = " + error, new Object[0]);
        EventNotifyCenter.notifyEvent(n.class, n.aww, new Object[]{Boolean.valueOf(false), Long.valueOf(this.aCG), Boolean.valueOf(false)});
    }
}
