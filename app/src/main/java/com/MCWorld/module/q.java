package com.MCWorld.module;

import com.MCWorld.framework.http.HttpMgr;
import java.util.HashMap;
import java.util.Map;

/* compiled from: RecommendAppModule */
public class q {
    private static void a(int inputRequestCode, Map<String, String> param, String url) {
        HttpMgr.getInstance().performStringRequest(url, param, new 1(inputRequestCode), new 7(inputRequestCode));
    }

    public static void S(int inputRequestCode, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        a(inputRequestCode, param, m.atP);
    }

    public static void a(int inputRequestCode, long cateId, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        param.put("cat_id", String.valueOf(cateId));
        a(inputRequestCode, param, m.atP);
    }

    public static void DJ() {
        HttpMgr.getInstance().performStringRequest(m.atQ, new HashMap(), new 8(), new 9());
    }

    public static void aG(long recommendAppId) {
        Map<String, String> param = new HashMap();
        param.put("id", String.valueOf(recommendAppId));
        HttpMgr.getInstance().performStringRequest(m.atR, param, new 10(), new 11());
    }

    public static void aH(long recommendAppId) {
        Map<String, String> param = new HashMap();
        param.put("id", String.valueOf(recommendAppId));
        HttpMgr.getInstance().performStringRequest(m.atS, param, new 12(), new 13());
    }

    public static void aI(long recommendAppId) {
        Map<String, String> param = new HashMap();
        param.put("id", String.valueOf(recommendAppId));
        HttpMgr.getInstance().performStringRequest(m.atT, param, new 14(), new 2());
    }

    public static void aJ(long recommendAppId) {
        Map<String, String> param = new HashMap();
        param.put("id", String.valueOf(recommendAppId));
        HttpMgr.getInstance().performStringRequest(m.atU, param, new 3(), new 4());
    }

    public static void aK(long recommendAppId) {
        Map<String, String> param = new HashMap();
        param.put("id", String.valueOf(recommendAppId));
        HttpMgr.getInstance().performStringRequest(m.atV, param, new 5(), new 6());
    }
}
