package com.MCWorld.module.profile;

import android.content.Context;
import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: ProfileModule */
class g$27 implements ErrorListener {
    final /* synthetic */ g aCs;
    final /* synthetic */ Context val$context;
    final /* synthetic */ int val$start;

    g$27(g this$0, int i, Context context) {
        this.aCs = this$0;
        this.val$start = i;
        this.val$context = context;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, h.arQ, new Object[]{Boolean.valueOf(false), null, Integer.valueOf(this.val$start), this.val$context});
    }
}
