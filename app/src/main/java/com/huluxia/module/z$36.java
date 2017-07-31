package com.huluxia.module;

import com.huluxia.data.profile.e.a;
import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$36 implements ErrorListener {
    final /* synthetic */ int axB;
    final /* synthetic */ z axD;
    final /* synthetic */ a axE;

    z$36(z this$0, int i, a aVar) {
        this.axD = this$0;
        this.axB = i;
        this.axE = aVar;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, h.asw, new Object[]{Integer.valueOf(this.axB), Boolean.valueOf(false), null, this.axE});
    }
}
