package com.MCWorld.module.profile;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: ProfileModule */
class g$16 implements ErrorListener {
    final /* synthetic */ g aCs;
    final /* synthetic */ String aCw;

    g$16(g this$0, String str) {
        this.aCs = this$0;
        this.aCw = str;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, h.arw, new Object[]{Boolean.valueOf(false), null, this.aCw});
    }
}
