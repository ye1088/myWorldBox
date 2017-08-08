package com.MCWorld.version;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: VersionModule */
class h$4 implements ErrorListener {
    final /* synthetic */ h boB;
    final /* synthetic */ String val$tag;

    h$4(h this$0, String str) {
        this.boB = this$0;
        this.val$tag = str;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, 773, new Object[]{Boolean.valueOf(false), null, this.val$tag});
        HLog.error(this, "requestVersionInfo onErrorResponse e = " + error, new Object[0]);
    }
}
