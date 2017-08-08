package com.MCWorld.module;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: SeedModule */
class u$2 implements ErrorListener {
    final /* synthetic */ int axo;

    u$2(int i) {
        this.axo = i;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error(this, "requestSeed onErrorResponse e = " + error, new Object[0]);
        EventNotifyCenter.notifyEvent(n.class, 298, new Object[]{Boolean.valueOf(false), Integer.valueOf(this.axo), null});
    }
}
