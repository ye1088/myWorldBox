package hlx.ui.heroslist;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.n;
import com.MCWorld.module.w;
import com.MCWorld.r;
import hlx.data.tongji.a;

/* compiled from: RankingModuleWrapper */
class d$1 implements Listener<String> {
    final /* synthetic */ d bYA;

    d$1(d this$0) {
        this.bYA = this$0;
    }

    public void onResponse(String response) {
        try {
            w info = (w) Json.parseJsonObject(response, w.class);
            if (info.isSucc()) {
                EventNotifyCenter.notifyEvent(n.class, n.awF, new Object[]{Boolean.valueOf(true), "success"});
                return;
            }
            r.ck().j(a.bOl, "rec status:" + info.status);
            EventNotifyCenter.notifyEvent(n.class, n.awF, new Object[]{Boolean.valueOf(false), "failure"});
        } catch (Exception e) {
            r.ck().j(a.bOl, "JsonParseError");
            EventNotifyCenter.notifyEvent(n.class, n.awF, new Object[]{Boolean.valueOf(false), "JsonParseError"});
        }
    }
}
