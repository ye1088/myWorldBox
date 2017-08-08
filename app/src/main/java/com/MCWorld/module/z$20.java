package com.MCWorld.module;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$20 implements ErrorListener {
    final /* synthetic */ int axA;
    final /* synthetic */ int axB;
    final /* synthetic */ int val$position;

    z$20(int i, int i2, int i3) {
        this.val$position = i;
        this.axA = i2;
        this.axB = i3;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, 789, new Object[]{Boolean.valueOf(false), null, Integer.valueOf(this.val$position), Integer.valueOf(this.axA), Integer.valueOf(this.axB)});
    }
}
