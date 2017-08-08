package com.MCWorld.module;

import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.http.HttpMgr;
import java.util.HashMap;
import java.util.Map;

/* compiled from: SkinModule */
public class x {
    private static x axv;

    public static synchronized x DN() {
        x xVar;
        synchronized (x.class) {
            if (axv == null) {
                axv = new x();
            }
            xVar = axv;
        }
        return xVar;
    }

    public void ao(int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        HttpMgr.getInstance().performStringRequest(m.aun, param, new 1(this), new 2(this));
    }

    public void aM(long id) {
        Map<String, String> param = new HashMap();
        param.put("skin_id", String.valueOf(id));
        HttpMgr.getInstance().performStringRequest(m.auo, param, new 3(this), new 4(this));
    }

    public void jm(int mapId) {
        Map<String, String> param = new HashMap();
        param.put("skin_id", String.valueOf(mapId));
        HttpMgr.getInstance().performStringRequest(m.aup, param, new 5(this), new 6(this));
    }

    public void c(int start, int count, String keyword) {
        HLog.verbose(this, "requestSkinSearch start = " + start, new Object[0]);
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        param.put("keyword", keyword);
        HttpMgr.getInstance().performStringRequest(m.auq, param, new 7(this), new 8(this));
    }
}
