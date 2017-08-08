package com.MCWorld.module;

import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.http.HttpMgr;
import java.util.HashMap;
import java.util.Map;

/* compiled from: JsModule */
public class i {
    private static i atn;

    public static synchronized i DB() {
        i iVar;
        synchronized (i.class) {
            if (atn == null) {
                atn = new i();
            }
            iVar = atn;
        }
        return iVar;
    }

    public void aj(int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        HttpMgr.getInstance().performStringRequest(m.aue, param, new 1(this), new 3(this));
    }

    public void L(String md5, String jsName) {
        Map<String, String> postParam = new HashMap();
        postParam.put("md5", String.valueOf(md5));
        postParam.put("js_name", String.valueOf(jsName));
        HttpMgr.getInstance().performPostStringRequest(m.auf, null, postParam, new 4(this), new 5(this), false, false);
    }

    public void ja(int mapId) {
        Map<String, String> param = new HashMap();
        param.put("map_id", String.valueOf(mapId));
        HttpMgr.getInstance().performStringRequest(m.atX, param, new 6(this), new 7(this));
    }

    public void jb(int mapId) {
        Map<String, String> param = new HashMap();
        param.put("map_id", String.valueOf(mapId));
        HttpMgr.getInstance().performStringRequest(m.atJ, param, new 8(this), new 9(this));
    }

    public void a(int start, int count, String keyword) {
        HLog.verbose(this, "requestJsSearch start = " + start, new Object[0]);
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        param.put("keyword", keyword);
        HttpMgr.getInstance().performStringRequest(m.aug, param, new 10(this), new 2(this));
    }
}
