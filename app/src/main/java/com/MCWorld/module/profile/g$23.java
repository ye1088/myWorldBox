package com.MCWorld.module.profile;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;
import com.MCWorld.module.w;

/* compiled from: ProfileModule */
class g$23 implements Listener<String> {
    final /* synthetic */ g aCs;
    final /* synthetic */ String aCy;

    g$23(g this$0, String str) {
        this.aCs = this$0;
        this.aCy = str;
    }

    public void onResponse(String response) {
        try {
            if (((w) Json.parseJsonObject(response, w.class)).isSucc()) {
                EventNotifyCenter.notifyEvent(h.class, h.aqZ, new Object[]{Boolean.valueOf(true), this.aCy, null});
                return;
            }
            EventNotifyCenter.notifyEvent(h.class, h.aqZ, new Object[]{Boolean.valueOf(false), this.aCy, ((w) Json.parseJsonObject(response, w.class)).msg});
        } catch (Exception e) {
            EventNotifyCenter.notifyEvent(h.class, h.aqZ, new Object[]{Boolean.valueOf(false), this.aCy, "修改昵称失败\n网络问题"});
        }
    }
}
