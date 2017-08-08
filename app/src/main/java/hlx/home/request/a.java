package hlx.home.request;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.http.HttpMgr;
import com.MCWorld.module.m;
import com.MCWorld.module.n;
import java.util.HashMap;

/* compiled from: MCHomeRequest */
public class a {
    private static a bQR;

    public static synchronized a Se() {
        a aVar;
        synchronized (a.class) {
            if (bQR == null) {
                bQR = new a();
            }
            aVar = bQR;
        }
        return aVar;
    }

    public void DA() {
        HttpMgr.getInstance().performStringRequest(m.aty, new HashMap(), new Listener<String>(this) {
            final /* synthetic */ a bQS;

            {
                this.bQS = this$0;
            }

            public void onResponse(String response) {
                try {
                    new com.MCWorld.data.home.a().aA(response);
                    EventNotifyCenter.notifyEvent(n.class, n.avY, new Object[]{homeInfo});
                } catch (Exception e) {
                    HLog.error(this, "requestHomeInfo e = " + e + ", response = " + response, new Object[0]);
                }
            }
        }, new ErrorListener(this) {
            final /* synthetic */ a bQS;

            {
                this.bQS = this$0;
            }

            public void onErrorResponse(VolleyError error) {
                HLog.error(this, "requestHomeInfo onErrorResponse e = " + error, new Object[0]);
                EventNotifyCenter.notifyEvent(n.class, n.avY, new Object[]{(com.MCWorld.data.home.a) null});
            }
        });
    }
}
