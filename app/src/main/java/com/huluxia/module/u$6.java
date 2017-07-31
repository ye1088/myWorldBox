package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: SeedModule */
class u$6 implements ErrorListener {
    final /* synthetic */ int axo;

    u$6(int i) {
        this.axo = i;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error(this, "requestSeedDetail onErrorResponse e = " + error, new Object[0]);
        EventNotifyCenter.notifyEvent(n.class, 299, new Object[]{Boolean.valueOf(false), Integer.valueOf(this.axo), null});
    }
}
