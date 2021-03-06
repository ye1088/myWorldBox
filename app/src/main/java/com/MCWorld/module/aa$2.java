package com.MCWorld.module;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.r;
import com.MCWorld.r.a;

/* compiled from: UploadModule */
class aa$2 implements Listener<String> {
    final /* synthetic */ aa axK;
    final /* synthetic */ r axP;

    aa$2(aa this$0, r rVar) {
        this.axK = this$0;
        this.axP = rVar;
    }

    public void onResponse(String response) {
        if (aa.DS()) {
            String str = "UploadModule";
            String str2 = "LSPrint UploadModule rec UpdateForumPost [response:%s]";
            Object[] objArr = new Object[1];
            objArr[0] = response == null ? "空" : response;
            HLog.verbose(str, str2, objArr);
        }
        try {
            w info = (w) Json.parseJsonObject(response, w.class);
            if (aa.DS()) {
                HLog.verbose("TAG", " LS Printf info MSG:[%s]!", new Object[]{info.msg.toString()});
            }
            if (info.isSucc()) {
                EventNotifyCenter.notifyEvent(n.class, n.awD, new Object[]{Boolean.valueOf(true), info.msg});
                return;
            }
            EventNotifyCenter.notifyEvent(n.class, n.awD, new Object[]{Boolean.valueOf(false), info.msg});
            r.ck().j(a.jH, "resTyep:" + this.axP.axr + " - rec status:" + info.status + " - code:" + info.code);
        } catch (Exception e) {
            HLog.error(this, "requestJsInputCount e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, n.awD, new Object[]{Boolean.valueOf(false), "解析数据错误"});
            r.ck().j(a.jH, "resTyep:" + this.axP.axr + " - JsonParseError");
        }
    }
}
