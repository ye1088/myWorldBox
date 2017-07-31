package hlx.ui.heroslist;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.n;

/* compiled from: RankingModuleWrapper */
class d$3 implements Listener<String> {
    final /* synthetic */ int aBO;
    final /* synthetic */ d bYA;
    final /* synthetic */ int bYB;
    final /* synthetic */ int val$start;

    d$3(d this$0, int i, int i2, int i3) {
        this.bYA = this$0;
        this.val$start = i;
        this.bYB = i2;
        this.aBO = i3;
    }

    public void onResponse(String response) {
        try {
            c info = (c) Json.parseJsonObject(response, c.class);
            d.a(this.bYA, this.val$start, this.bYB, info.heroRankList);
            if (info.isSucc()) {
                EventNotifyCenter.notifyEvent(n.class, n.awG, new Object[]{Boolean.valueOf(true), Integer.valueOf(this.aBO), info});
                return;
            }
            EventNotifyCenter.notifyEvent(n.class, n.awG, new Object[]{Boolean.valueOf(false), Integer.valueOf(this.aBO), null});
        } catch (Exception e) {
            EventNotifyCenter.notifyEvent(n.class, n.awG, new Object[]{Boolean.valueOf(false), Integer.valueOf(this.aBO), null});
        }
    }
}
