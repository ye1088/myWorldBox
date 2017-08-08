package com.MCWorld.module;

import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.http.HttpMgr;
import java.util.HashMap;
import java.util.Map;

/* compiled from: WoodModule */
public class ac {
    private static ac aBC;

    public static synchronized ac DT() {
        ac acVar;
        synchronized (ac.class) {
            if (aBC == null) {
                aBC = new ac();
            }
            acVar = aBC;
        }
        return acVar;
    }

    public void aq(int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        HttpMgr.getInstance().performStringRequest(m.aux, param, new 1(this), new 2(this));
    }

    public void aO(long id) {
        Map<String, String> param = new HashMap();
        param.put("wood_id", String.valueOf(id));
        HttpMgr.getInstance().performStringRequest(m.auy, param, new 3(this), new 4(this));
    }

    public void jw(int mapId) {
        Map<String, String> param = new HashMap();
        param.put("wood_id", String.valueOf(mapId));
        HttpMgr.getInstance().performStringRequest(m.auz, param, new 5(this), new 6(this));
    }

    public void d(int start, int count, String keyword) {
        HLog.verbose(this, "requestWoodSearch start = " + start, new Object[0]);
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        param.put("keyword", keyword);
        HttpMgr.getInstance().performStringRequest(m.auA, param, new 7(this), new 8(this));
    }
}
