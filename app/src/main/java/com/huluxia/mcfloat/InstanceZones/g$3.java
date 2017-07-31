package com.huluxia.mcfloat.InstanceZones;

import com.huluxia.data.inszone.a;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.t;
import com.huluxia.utils.ah;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.tools.ant.util.DateUtils;

/* compiled from: InsZonesScoreBoard */
class g$3 extends CallbackHandler {
    final /* synthetic */ g XF;

    g$3(g this$0) {
        this.XF = this$0;
    }

    @MessageHandler(message = 2563)
    public void onRecvUploadMsg(boolean succ, String msg) {
        if (succ) {
            ah.KZ().a(g.a(this.XF), new a(g.e(this.XF), new SimpleDateFormat(DateUtils.ISO8601_DATE_PATTERN).format(new Date()), g.b(this.XF)));
            return;
        }
        if (g.f(this.XF) > 0) {
            g.g(this.XF);
        }
        t.n(g.d(this.XF), "抱歉！网络很傲娇，上传分数失败！");
    }
}
