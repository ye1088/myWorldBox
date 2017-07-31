package com.huluxia.module.profile;

import com.huluxia.data.profile.ProfileInfo;
import com.huluxia.db.c;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.module.h;
import com.huluxia.module.p;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: ProfileModule */
class g$2 implements Runnable {
    final /* synthetic */ g aCs;
    final /* synthetic */ AtomicBoolean aCt;
    final /* synthetic */ long axx;

    g$2(g this$0, long j, AtomicBoolean atomicBoolean) {
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
