package com.huluxia.module.topic;

import android.content.Context;
import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;

/* compiled from: TopicModule2 */
class k$16 implements ErrorListener {
    final /* synthetic */ long aCE;
    final /* synthetic */ k aCN;
    final /* synthetic */ Context val$context;
    final /* synthetic */ int val$position;

    k$16(k this$0, int i, long j, Context context) {
        this.aCN = this$0;
        this.val$position = i;
        this.aCE = j;
        this.val$context = context;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, 768, new Object[]{Boolean.valueOf(false), null, Integer.valueOf(this.val$position), Long.valueOf(this.aCE), this.val$context});
    }
}
