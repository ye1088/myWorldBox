package com.huluxia.module.profile;

import com.huluxia.data.profile.ProfileInfo;
import com.huluxia.db.d;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;
import com.huluxia.module.p;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: ProfileModule */
class g$4 implements Listener<ProfileInfo> {
    final /* synthetic */ g aCs;
    final /* synthetic */ AtomicBoolean aCt;
    final /* synthetic */ long axx;

    g$4(g this$0, AtomicBoolean atomicBoolean, long j) {
        this.aCs = this$0;
        this.aCt = atomicBoolean;
        this.axx = j;
    }

    public /* synthetic */ void onResponse(Object obj) {
        c((ProfileInfo) obj);
    }

    public void c(ProfileInfo info) {
        HLog.debug(this.aCs, "requestProfileInfo " + info, new Object[0]);
        if (info == null || !info.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, h.ara, new Object[]{Boolean.valueOf(false), info, Long.valueOf(this.axx)});
            return;
        }
        d.eK().a(new p(info.getUserID(), Json.toJson(info)));
        this.aCt.set(true);
        EventNotifyCenter.notifyEvent(h.class, h.ara, new Object[]{Boolean.valueOf(true), info, Long.valueOf(this.axx)});
    }
}
