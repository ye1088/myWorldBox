package hlx.module.news;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.http.HttpMgr;
import com.huluxia.module.ab;
import com.huluxia.module.n;
import java.util.HashMap;

/* compiled from: NewsModule */
public class d {
    public static void ar(int start, int pageSize) {
        HashMap<String, String> params = new HashMap();
        params.put("start", String.valueOf(start));
        params.put("count", String.valueOf(pageSize));
        HttpMgr.getInstance().performStringRequest(ab.aAL, params, new Listener<String>() {
            public void onResponse(String response) {
                try {
                    c info = (c) Json.parseJsonObject(response, c.class);
                    if (info == null || !info.isSucc()) {
                        EventNotifyCenter.notifyEvent(n.class, n.awM, new Object[]{Boolean.valueOf(false), null});
                        return;
                    }
                    EventNotifyCenter.notifyEvent(n.class, n.awM, new Object[]{Boolean.valueOf(true), info});
                } catch (Exception e) {
                    EventNotifyCenter.notifyEvent(n.class, n.awM, new Object[]{Boolean.valueOf(false), null});
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                EventNotifyCenter.notifyEvent(n.class, n.awM, new Object[]{Boolean.valueOf(false), null});
            }
        });
    }

    public static void TS() {
        HttpMgr.getInstance().performStringRequest(ab.aAM, new Listener<String>() {
            public void onResponse(String response) {
                try {
                    b info = (b) Json.parseJsonObject(response, b.class);
                    if (info == null || !info.isSucc()) {
                        EventNotifyCenter.notifyEvent(n.class, n.awN, new Object[]{Boolean.valueOf(false), null});
                        return;
                    }
                    EventNotifyCenter.notifyEvent(n.class, n.awN, new Object[]{Boolean.valueOf(true), info});
                } catch (Exception e) {
                    EventNotifyCenter.notifyEvent(n.class, n.awN, new Object[]{Boolean.valueOf(false), null});
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                EventNotifyCenter.notifyEvent(n.class, n.awN, new Object[]{Boolean.valueOf(false), null});
            }
        });
    }
}
