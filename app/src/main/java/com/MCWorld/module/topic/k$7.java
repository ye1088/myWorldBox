package com.MCWorld.module.topic;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: TopicModule2 */
class k$7 implements ErrorListener {
    final /* synthetic */ k aCN;

    k$7(k this$0) {
        this.aCN = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error("TopicModule2", "requestZoneCategory error response " + error, new Object[0]);
        EventNotifyCenter.notifyEventUiThread(h.class, h.arO, new Object[]{Boolean.valueOf(false), null});
    }
}
