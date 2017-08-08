package hlx.module.userguide;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.n;

/* compiled from: UserGuideModule */
class b$1 implements Listener<String> {
    b$1() {
    }

    public void onResponse(String response) {
        try {
            a info = (a) Json.parseJsonObject(response, a.class);
            if (info == null || !info.isSucc()) {
                EventNotifyCenter.notifyEvent(n.class, n.awO, new Object[]{Boolean.valueOf(false), null});
                return;
            }
            EventNotifyCenter.notifyEvent(n.class, n.awO, new Object[]{Boolean.valueOf(true), info});
        } catch (Exception e) {
            EventNotifyCenter.notifyEvent(n.class, n.awO, new Object[]{Boolean.valueOf(false), null});
        }
    }
}
