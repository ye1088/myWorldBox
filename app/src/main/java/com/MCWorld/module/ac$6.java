package com.MCWorld.module;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: WoodModule */
class ac$6 implements ErrorListener {
    final /* synthetic */ ac aBD;

    ac$6(ac this$0) {
        this.aBD = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error(this, "requestWoodDetail onErrorResponse e = " + error, new Object[0]);
        EventNotifyCenter.notifyEvent(n.class, n.avO, new Object[]{Boolean.valueOf(false), null});
    }
}
