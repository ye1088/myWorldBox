package com.MCWorld.module;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.r;
import com.MCWorld.r.a;

/* compiled from: UploadModule */
class aa$8 implements ErrorListener {
    final /* synthetic */ int atr;
    final /* synthetic */ aa axK;

    aa$8(aa this$0, int i) {
        this.axK = this$0;
        this.atr = i;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(n.class, n.awy, new Object[]{Boolean.valueOf(false), "上传检查失败\n网络出错"});
        r.ck().j(a.jF, "resTyep:" + this.atr + " - VolleyError:" + error.toString());
    }
}
