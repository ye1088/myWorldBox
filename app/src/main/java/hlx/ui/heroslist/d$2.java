package hlx.ui.heroslist;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.n;
import com.MCWorld.r;
import hlx.data.tongji.a;

/* compiled from: RankingModuleWrapper */
class d$2 implements ErrorListener {
    final /* synthetic */ d bYA;

    d$2(d this$0) {
        this.bYA = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        r.ck().j(a.bOl, "VolleyError:" + error.toString());
        EventNotifyCenter.notifyEvent(n.class, n.awF, new Object[]{Boolean.valueOf(false), "VolleyError"});
    }
}
