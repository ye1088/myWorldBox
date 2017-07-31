package com.huluxia.module;

import com.huluxia.data.map.i;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.r;
import com.huluxia.r.a;

/* compiled from: UploadModule */
class aa$7 implements Listener<String> {
    final /* synthetic */ int atr;
    final /* synthetic */ aa axK;

    aa$7(aa this$0, int i) {
        this.axK = this$0;
        this.atr = i;
    }

    public void onResponse(String response) {
        i info = (i) Json.parseJsonObject(response, i.class);
        if (aa.DS()) {
            HLog.verbose("TAG", " LS Printf [%s]!", new Object[]{info.msg.toString()});
        }
        if (info.isSucc()) {
            EventNotifyCenter.notifyEvent(n.class, n.awy, new Object[]{Boolean.valueOf(true), info.msg});
            return;
        }
        r.ck().j(a.jF, "resTyep:" + this.atr + " - rec status:" + info.status + " - code:" + info.code);
        EventNotifyCenter.notifyEvent(n.class, n.awy, new Object[]{Boolean.valueOf(false), info.msg});
    }
}
