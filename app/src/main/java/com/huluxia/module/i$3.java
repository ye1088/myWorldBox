package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: JsModule */
class i$3 implements ErrorListener {
    final /* synthetic */ i ato;

    i$3(i this$0) {
        this.ato = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error(this, "requestJsNew onErrorResponse e = " + error, new Object[0]);
        EventNotifyCenter.notifyEvent(n.class, n.avC, new Object[]{Boolean.valueOf(false), null});
    }
}
