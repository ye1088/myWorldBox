package com.MCWorld.module;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: JsModule */
class i$9 implements ErrorListener {
    final /* synthetic */ i ato;

    i$9(i this$0) {
        this.ato = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error(this, "requestJsDetail onErrorResponse e = " + error, new Object[0]);
        EventNotifyCenter.notifyEvent(n.class, 1282, new Object[]{Boolean.valueOf(false), null});
    }
}
