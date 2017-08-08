package com.MCWorld.module.profile;

import android.content.Context;
import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: ProfileModule */
class g$14 implements ErrorListener {
    final /* synthetic */ g aCs;
    final /* synthetic */ int aCv;
    final /* synthetic */ Context val$context;

    g$14(g this$0, int i, Context context) {
        this.aCs = this$0;
        this.aCv = i;
        this.val$context = context;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, h.arv, new Object[]{Boolean.valueOf(false), null, Integer.valueOf(this.aCv), this.val$context});
    }
}
