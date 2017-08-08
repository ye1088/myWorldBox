package com.MCWorld.module;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$33 implements ErrorListener {
    final /* synthetic */ z axD;
    final /* synthetic */ int val$id;

    z$33(z this$0, int i) {
        this.axD = this$0;
        this.val$id = i;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, h.asu, new Object[]{Integer.valueOf(this.val$id), Boolean.valueOf(false), null});
    }
}
