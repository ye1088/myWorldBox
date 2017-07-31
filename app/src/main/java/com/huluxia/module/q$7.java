package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: RecommendAppModule */
class q$7 implements ErrorListener {
    final /* synthetic */ int axo;

    q$7(int i) {
        this.axo = i;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error(this, "requestRecommendApp onErrorResponse e = " + error, new Object[0]);
        EventNotifyCenter.notifyEvent(n.class, 302, new Object[]{Boolean.valueOf(false), Integer.valueOf(this.axo), null});
    }
}
