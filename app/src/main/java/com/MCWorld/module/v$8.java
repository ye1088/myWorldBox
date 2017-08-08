package com.MCWorld.module;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: ServerModule */
class v$8 implements ErrorListener {
    final /* synthetic */ v axu;

    v$8(v this$0) {
        this.axu = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error(this, "requestServerInfoItem onErrorResponse e = " + error, new Object[0]);
        EventNotifyCenter.notifyEvent(n.class, 258, new Object[]{Boolean.valueOf(false), null});
    }
}
