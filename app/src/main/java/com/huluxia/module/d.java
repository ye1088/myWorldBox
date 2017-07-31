package com.huluxia.module;

import com.baidu.android.pushservice.PushConstants;
import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.http.HttpMgr;
import com.huluxia.r;
import java.util.HashMap;
import java.util.Map;

/* compiled from: GameDetailModule */
public class d {
    private static d aqm;

    public static synchronized d Dy() {
        d dVar;
        synchronized (d.class) {
            if (aqm == null) {
                aqm = new d();
            }
            dVar = aqm;
        }
        return dVar;
    }

    public void aE(long id) {
        Map<String, String> param = new HashMap();
        param.put(PushConstants.EXTRA_APP_ID, String.valueOf(id));
        HttpMgr.getInstance().performStringRequest(ab.auT, param, new Listener<String>(this) {
            final /* synthetic */ d aqn;

            {
                this.aqn = this$0;
            }

            public void onResponse(String response) {
                try {
                    c info = (c) Json.parseJsonObject(response, c.class);
                    if (info == null || !info.isSucc()) {
                        EventNotifyCenter.notifyEvent(h.class, 532, new Object[]{Boolean.valueOf(false), null});
                        return;
                    }
                    EventNotifyCenter.notifyEvent(h.class, 532, new Object[]{Boolean.valueOf(true), info});
                } catch (Exception e) {
                    HLog.error(this, "requestGameDetail e = " + e + ", response = " + response, new Object[0]);
                    EventNotifyCenter.notifyEvent(h.class, 532, new Object[]{Boolean.valueOf(false), null});
                }
            }
        }, new ErrorListener(this) {
            final /* synthetic */ d aqn;

            {
                this.aqn = this$0;
            }

            public void onErrorResponse(VolleyError error) {
                r.ck().cH();
                EventNotifyCenter.notifyEvent(h.class, 532, new Object[]{Boolean.valueOf(false), null});
                HLog.error(this, "requestGameDetail onErrorResponse e = " + error, new Object[0]);
            }
        });
    }
}
