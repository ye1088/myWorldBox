package com.MCWorld.module.profile;

import com.MCWorld.data.profile.ProfileInfo;
import com.MCWorld.db.c;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.module.h;
import com.MCWorld.module.p;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: ProfileModule */
class g$38 implements Runnable {
    final /* synthetic */ g aCs;
    final /* synthetic */ AtomicBoolean aCt;
    final /* synthetic */ long axx;

    g$38(g this$0, long j, AtomicBoolean atomicBoolean) {
        this.aCs = this$0;
        this.axx = j;
        this.aCt = atomicBoolean;
    }

    public void run() {
        try {
            p info = c.eJ().p(this.axx);
            if (this.aCt.get()) {
                HLog.info(this, "requestProfileInfo not notify data after network request completing", new Object[0]);
            } else if (info == null || UtilsFunction.empty(info.json)) {
                HLog.verbose("ProfileModule", "ProfileDbInfo not hit in db", new Object[0]);
            } else {
                HLog.verbose("ProfileModule", "ProfileDbInfo %d hit in db %s", new Object[]{Long.valueOf(this.axx), info.json});
                ProfileInfo profileInfo = (ProfileInfo) Json.parseJsonObject(info.json, ProfileInfo.class);
                if (profileInfo == null || !profileInfo.isSucc()) {
                    EventNotifyCenter.notifyEventUiThread(h.class, h.ara, new Object[]{Boolean.valueOf(false), profileInfo, Long.valueOf(this.axx)});
                    return;
                }
                EventNotifyCenter.notifyEventUiThread(h.class, h.ara, new Object[]{Boolean.valueOf(true), profileInfo, Long.valueOf(this.axx)});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
