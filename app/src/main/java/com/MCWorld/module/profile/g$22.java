package com.MCWorld.module.profile;

import android.content.Context;
import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: ProfileModule */
class g$22 implements ErrorListener {
    final /* synthetic */ g aCs;
    final /* synthetic */ int aCx;
    final /* synthetic */ Context val$context;

    g$22(g this$0, int i, Context context) {
        this.aCs = this$0;
        this.aCx = i;
        this.val$context = context;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, h.ary, new Object[]{Boolean.valueOf(false), null, Integer.valueOf(this.aCx), this.val$context});
    }
}
