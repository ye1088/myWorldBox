package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: ModuleRequestWrapper */
class o$4 implements ErrorListener {
    o$4() {
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error(this, "requestMapCategory onErrorResponse e = " + error, new Object[0]);
        EventNotifyCenter.notifyEvent(n.class, 291, new Object[]{Boolean.valueOf(false), null});
    }
}
