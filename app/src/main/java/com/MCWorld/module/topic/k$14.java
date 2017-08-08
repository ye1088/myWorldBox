package com.MCWorld.module.topic;

import android.content.Context;
import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: TopicModule2 */
class k$14 implements ErrorListener {
    final /* synthetic */ k aCN;
    final /* synthetic */ Context val$context;
    final /* synthetic */ int val$position;

    k$14(k this$0, int i, Context context) {
        this.aCN = this$0;
        this.val$position = i;
        this.val$context = context;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, 768, new Object[]{Boolean.valueOf(false), null, Integer.valueOf(this.val$position), this.val$context});
    }
}
