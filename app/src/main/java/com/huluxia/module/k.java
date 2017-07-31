package com.huluxia.module;

import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.http.HttpMgr;
import java.util.HashMap;
import java.util.Map;

/* compiled from: MapModule */
public class k {
    private static k atp;

    public static synchronized k DC() {
        k kVar;
        synchronized (k.class) {
            if (atp == null) {
                atp = new k();
            }
            kVar = atp;
        }
        return kVar;
    }

    public void ak(int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        HttpMgr.getInstance().performStringRequest(m.atz, param, new 1(this), new 12(this));
    }

    public void al(int start, int count) {
        HLog.verbose(this, "requestMapRanking start = " + start, new Object[0]);
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        HttpMgr.getInstance().performStringRequest(m.atC, param, new 16(this), new 17(this));
    }

    public void a(long categoryid, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        param.put("cat_id", String.valueOf(categoryid));
        HttpMgr.getInstance().performStringRequest(m.atH, param, new 18(this), new 19(this));
    }

    public void jc(int mapId) {
        Map<String, String> param = new HashMap();
        param.put("map_id", String.valueOf(mapId));
        HttpMgr.getInstance().performStringRequest(m.atJ, param, new 20(this), new 21(this));
    }

    public void b(int start, int count, String keyword) {
        HLog.verbose(this, "requestMapSearch start = " + start, new Object[0]);
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        param.put("keyword", keyword);
        HttpMgr.getInstance().performStringRequest(m.atW, param, new 22(this), new 2(this));
    }

    public void aF(long mapId) {
        Map<String, String> param = new HashMap();
        param.put("map_id", String.valueOf(mapId));
        HttpMgr.getInstance().performStringRequest(m.atX, param, new 3(this), new 4(this));
    }

    public void DD() {
        HttpMgr.getInstance().performStringRequest(m.aub, new HashMap(), new 5(this), new 6(this));
    }

    public void DE() {
        HttpMgr.getInstance().performStringRequest(m.aua, new HashMap(), new 7(this), new 8(this));
    }

    public void DF() {
        HLog.verbose(this, "requestResCount start ", new Object[0]);
        HttpMgr.getInstance().performStringRequest(m.atY, new HashMap(), new 9(this), new 10(this));
    }

    public void DG() {
        HttpMgr.getInstance().performStringRequest(m.axZ, new HashMap(), new 11(this), new 13(this));
    }

    public static void a(int resType, int state, int type_id, int start, int count, Object ctx) {
        String uri;
        HashMap<String, String> params = new HashMap();
        params.put("start", String.valueOf(start));
        params.put("count", String.valueOf(count));
        params.put("type_id", String.valueOf(type_id));
        switch (resType) {
            case 2:
                uri = m.atE;
                break;
            case 3:
                uri = m.atG;
                break;
            case 4:
                uri = m.atF;
                break;
            default:
                uri = m.atD;
                break;
        }
        HttpMgr.getInstance().performStringRequest(uri, params, new 14(resType, state, ctx), new 15(resType, state, ctx));
    }
}
