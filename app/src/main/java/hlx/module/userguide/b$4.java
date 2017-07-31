package hlx.module.userguide;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.n;

/* compiled from: UserGuideModule */
class b$4 implements ErrorListener {
    b$4() {
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(n.class, n.awP, new Object[]{Boolean.valueOf(false), null});
    }
}
