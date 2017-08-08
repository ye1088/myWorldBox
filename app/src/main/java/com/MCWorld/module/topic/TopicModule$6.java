package com.MCWorld.module.topic;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.n;

class TopicModule$6 implements ErrorListener {
    final /* synthetic */ TopicModule aCF;

    TopicModule$6(TopicModule this$0) {
        this.aCF = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error(this, "requestComplaint onErrorResponse e = " + error, new Object[0]);
        EventNotifyCenter.notifyEvent(n.class, n.arp, new Object[]{Boolean.valueOf(false)});
    }
}
