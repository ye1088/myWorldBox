package com.MCWorld.module;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: MapModule */
class k$17 implements ErrorListener {
    final /* synthetic */ k atq;

    k$17(k this$0) {
        this.atq = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error(this, "requestMapRanking onErrorResponse e = " + error, new Object[0]);
        EventNotifyCenter.notifyEvent(n.class, 515, new Object[]{Boolean.valueOf(false), null});
    }
}
