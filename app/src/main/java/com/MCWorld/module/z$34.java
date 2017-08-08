package com.MCWorld.module;

import com.MCWorld.data.profile.e;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$34 implements Listener<e> {
    final /* synthetic */ int axy;
    final /* synthetic */ int val$position;

    z$34(int i, int i2) {
        this.val$position = i;
        this.axy = i2;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((e) obj);
    }

    public void a(e info) {
        if (info == null || !info.isSucc()) {
            if (this.val$position == 257) {
                EventNotifyCenter.notifyEvent(h.class, h.arZ, new Object[]{Boolean.valueOf(false), info, Integer.valueOf(this.axy)});
            } else if (this.val$position == 258) {
                EventNotifyCenter.notifyEvent(h.class, h.asa, new Object[]{Boolean.valueOf(false), info, Integer.valueOf(this.axy)});
            }
        } else if (this.val$position == 257) {
            EventNotifyCenter.notifyEvent(h.class, h.arZ, new Object[]{Boolean.valueOf(true), info, Integer.valueOf(this.axy)});
        } else if (this.val$position == 258) {
            EventNotifyCenter.notifyEvent(h.class, h.asa, new Object[]{Boolean.valueOf(true), info, Integer.valueOf(this.axy)});
        }
    }
}
