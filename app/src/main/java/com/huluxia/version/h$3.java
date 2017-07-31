package com.huluxia.version;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;

/* compiled from: VersionModule */
class h$3 implements Listener<String> {
    final /* synthetic */ h boB;
    final /* synthetic */ String val$tag;

    h$3(h this$0, String str) {
        this.boB = this$0;
        this.val$tag = str;
    }

    public void onResponse(String response) {
        try {
            f infos = (f) Json.parseJsonObject(response, f.class);
            if (infos == null || !infos.isSucc()) {
                EventNotifyCenter.notifyEvent(h.class, 773, new Object[]{Boolean.valueOf(false), null, this.val$tag});
                return;
            }
            EventNotifyCenter.notifyEvent(h.class, 773, new Object[]{Boolean.valueOf(true), infos.versionInfo, this.val$tag});
        } catch (Exception e) {
            HLog.error(this, "requestVersionInfo e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(h.class, 773, new Object[]{Boolean.valueOf(false), null, this.val$tag});
        }
    }
}
