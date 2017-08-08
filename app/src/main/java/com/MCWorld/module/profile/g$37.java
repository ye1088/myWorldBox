package com.MCWorld.module.profile;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: ProfileModule */
class g$37 implements ErrorListener {
    final /* synthetic */ g aCs;
    final /* synthetic */ int axC;

    g$37(g this$0, int i) {
        this.aCs = this$0;
        this.axC = i;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, h.arb, new Object[]{Boolean.valueOf(false), null, Integer.valueOf(this.axC)});
    }
}
