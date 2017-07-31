package com.huluxia.module.profile;

import android.content.Context;
import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;

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
