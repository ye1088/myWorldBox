package com.MCWorld.module;

import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.http.HttpMgr;
import java.util.HashMap;
import java.util.Map;

/* compiled from: ServerModule */
public class v {
    private static v axs = null;
    public static final String axt = "http://192.168.1.127";

    public static synchronized v DL() {
        v vVar;
        synchronized (v.class) {
            if (axs == null) {
                axs = new v();
            }
            vVar = axs;
        }
        return vVar;
    }

    public void DM() {
        HLog.verbose(this, "requestServerStatCount start ", new Object[0]);
        HttpMgr.getInstance().performStringRequest(m.auN, new HashMap(), new 1(this), new 2(this));
    }

    public void am(int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        HttpMgr.getInstance().performStringRequest(m.auM, param, new 3(this), new 4(this));
    }

    public void an(int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        HttpMgr.getInstance().performStringRequest(m.auL, param, new 5(this), new 6(this));
    }

    public void jl(int id) {
        Map<String, String> param = new HashMap();
        param.put("id", String.valueOf(id));
        HttpMgr.getInstance().performStringRequest(m.auO, param, new 7(this), new 8(this));
    }
}
