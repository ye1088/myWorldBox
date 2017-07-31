package com.huluxia.mconline.module;

import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.http.HttpMgr;
import com.huluxia.mconline.gameloc.http.g;
import com.huluxia.module.m;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/* compiled from: OnlineModule */
public class a {
    private static final String TAG = "OnlineModule";
    private static a amj;

    public static synchronized a Bl() {
        a aVar;
        synchronized (a.class) {
            if (amj == null) {
                amj = new a();
            }
            aVar = amj;
        }
        return aVar;
    }

    public void ap(Object ctx) {
        HttpMgr.getInstance().performPostStringRequest(m.avi, null, null, new 1(this, ctx), new 2(this, ctx), true, false);
    }

    public void c(long start, int pageSize) {
        HashMap<String, String> params = new HashMap();
        params.put("start", String.valueOf(start));
        params.put("count", String.valueOf(pageSize));
        HttpMgr.getInstance().performStringRequest(m.avj, params, new 3(this, start), new 4(this, start));
    }

    public void H(List<g> infoQueue) {
        if (!UtilsFunction.empty((Collection) infoQueue)) {
            HashMap<String, String> params = new HashMap();
            StringBuilder sbRoomID = new StringBuilder();
            for (g info : infoQueue) {
                sbRoomID.append(info.online_ip).append("_").append(info.online_port).append("_").append(info.room_no).append(MiPushClient.ACCEPT_TIME_SEPARATOR);
            }
            params.put("ids", String.valueOf(sbRoomID.toString()));
            HttpMgr.getInstance().performPostStringRequest(m.avk, null, params, new 5(this), new 6(this), true, false);
        }
    }

    public void Bm() {
        HttpMgr.getInstance().performStringRequest(m.avl, null, new 7(this), new 8(this), true, false);
    }
}
