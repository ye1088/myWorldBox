package com.MCWorld.module.profile;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: ProfileModule */
class g$10 implements ErrorListener {
    final /* synthetic */ String MZ;
    final /* synthetic */ String aCk;
    final /* synthetic */ g aCs;
    final /* synthetic */ String aCu;

    g$10(g this$0, String str, String str2, String str3) {
        this.aCs = this$0;
        this.aCk = str;
        this.MZ = str2;
        this.aCu = str3;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, h.aro, new Object[]{Boolean.valueOf(false), null, this.aCk, this.MZ, this.aCu});
        HLog.error(this, "requestQinfo onErrorResponse e = " + error, new Object[0]);
    }
}
