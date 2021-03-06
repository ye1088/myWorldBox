package com.MCWorld.module;

import com.MCWorld.data.home.a;
import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.http.HttpMgr;
import java.util.HashMap;

/* compiled from: HomeModule */
public class g {
    private static g aqp;

    public static synchronized g Dz() {
        g gVar;
        synchronized (g.class) {
            if (aqp == null) {
                aqp = new g();
            }
            gVar = aqp;
        }
        return gVar;
    }

    public void DA() {
        HttpMgr.getInstance().performStringRequest(m.aty, new HashMap(), new Listener<String>(this) {
            final /* synthetic */ g aqq;

            {
                this.aqq = this$0;
            }

            public void onResponse(String response) {
                try {
                    new a().aA(response);
                    EventNotifyCenter.notifyEvent(n.class, n.avY, new Object[]{homeInfo});
                } catch (Exception e) {
                    HLog.error(this, "requestHomeInfo e = " + e + ", response = " + response, new Object[0]);
                }
            }
        }, new ErrorListener(this) {
            final /* synthetic */ g aqq;

            {
                this.aqq = this$0;
            }

            public void onErrorResponse(VolleyError error) {
                HLog.error(this, "requestHomeInfo onErrorResponse e = " + error, new Object[0]);
                EventNotifyCenter.notifyEvent(n.class, n.avY, new Object[]{(a) null});
            }
        });
    }
}
