package com.MCWorld.module;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$48 implements ErrorListener {
    final /* synthetic */ int atr;
    final /* synthetic */ int axy;

    z$48(int i, int i2) {
        this.axy = i;
        this.atr = i2;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, 790, new Object[]{Integer.valueOf(this.axy), Integer.valueOf(this.atr), Boolean.valueOf(false), null});
    }
}
