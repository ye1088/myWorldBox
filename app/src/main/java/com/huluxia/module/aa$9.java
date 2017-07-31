package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: UploadModule */
class aa$9 implements Listener<String> {
    final /* synthetic */ aa axK;

    aa$9(aa this$0) {
        this.axK = this$0;
    }

    public void onResponse(String response) {
        if (aa.DS()) {
            String str = "UploadModule";
            String str2 = "LSPrint UploadModule rec SendForumPost [response:%s]";
            Object[] objArr = new Object[1];
            objArr[0] = response == null ? "空" : response;
            HLog.verbose(str, str2, objArr);
        }
        try {
            w info = (w) Json.parseJsonObject(response, w.class);
            if (aa.DS()) {
                HLog.verbose("TAG", "LS Printf info Code:[%d]; MSG:[%s]!", new Object[]{Integer.valueOf(info.code), info.msg.toString()});
                HLog.verbose("TAG", "LS Printf response:[%s]!", new Object[]{response});
            }
            if (info.isSucc()) {
                EventNotifyCenter.notifyEvent(n.class, n.awH, new Object[]{Boolean.valueOf(true), info.msg});
            } else {
                EventNotifyCenter.notifyEvent(n.class, n.awH, new Object[]{Boolean.valueOf(false), info.msg});
            }
        } catch (Exception e) {
            HLog.error(this, "requestJsInputCount e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, n.awH, new Object[]{Boolean.valueOf(false), "解析数据错误"});
        }
    }
}
