package com.MCWorld.module;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$31 implements ErrorListener {
    final /* synthetic */ int axB;
    final /* synthetic */ z axD;

    z$31(z this$0, int i) {
        this.axD = this$0;
        this.axB = i;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, h.ast, new Object[]{Integer.valueOf(this.axB), Boolean.valueOf(false), null});
    }
}
