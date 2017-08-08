package hlx.module.userguide;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.n;

/* compiled from: UserGuideModule */
class b$2 implements ErrorListener {
    b$2() {
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(n.class, n.awO, new Object[]{Boolean.valueOf(false), null});
    }
}
