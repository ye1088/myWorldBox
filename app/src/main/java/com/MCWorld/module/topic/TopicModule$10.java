package com.MCWorld.module.topic;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.n;

class TopicModule$10 implements ErrorListener {
    final /* synthetic */ TopicModule aCF;
    final /* synthetic */ long aCG;
    final /* synthetic */ boolean aCI;

    TopicModule$10(TopicModule this$0, long j, boolean z) {
        this.aCF = this$0;
        this.aCG = j;
        this.aCI = z;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error(this, "requestFavoriteTopic onErrorResponse e = " + error, new Object[0]);
        EventNotifyCenter.notifyEvent(n.class, n.awv, new Object[]{Boolean.valueOf(false), Long.valueOf(this.aCG), Boolean.valueOf(this.aCI)});
    }
}
