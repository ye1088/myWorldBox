package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.r;
import com.huluxia.r.a;

/* compiled from: UploadModule */
class aa$3 implements ErrorListener {
    final /* synthetic */ aa axK;
    final /* synthetic */ r axP;

    aa$3(aa this$0, r rVar) {
        this.axK = this$0;
        this.axP = rVar;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.verbose("UploadModule", "update error:[%s]", new Object[]{error.toString()});
        EventNotifyCenter.notifyEvent(n.class, n.awD, new Object[]{Boolean.valueOf(false), "更新帖子失败\n网络问题"});
        r.ck().j(a.jH, "resTyep:" + this.axP.axr + " - VolleyError:" + error.toString());
    }
}
