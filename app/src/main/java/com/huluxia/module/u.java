package com.huluxia.module;

import com.huluxia.framework.http.HttpMgr;
import java.util.HashMap;
import java.util.Map;

/* compiled from: SeedModule */
public class u {
    private static void b(int inputRequestCode, Map<String, String> param, String url) {
        HttpMgr.getInstance().performStringRequest(url, param, new 1(inputRequestCode), new 2(inputRequestCode));
    }

    public static void T(int inputRequestCode, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        b(inputRequestCode, param, m.atM);
    }

    public static void b(int inputRequestCode, long cateId, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        param.put("cat_id", String.valueOf(cateId));
        b(inputRequestCode, param, m.atL);
    }

    public static void DK() {
        HttpMgr.getInstance().performStringRequest(m.atK, new 3(), new 4());
    }

    public static void b(int inputRequestCode, long id) {
        Map<String, String> param = new HashMap();
        param.put("id", String.valueOf(id));
        a(inputRequestCode, param);
    }

    private static void a(int inputRequestCode, Map<String, String> param) {
        HttpMgr.getInstance().performStringRequest(m.atN, param, new 5(inputRequestCode), new 6(inputRequestCode));
    }

    public static void aL(long seedId) {
        Map<String, String> param = new HashMap();
        param.put("seed_id", String.valueOf(seedId));
        HttpMgr.getInstance().performStringRequest(m.atO, param, new 7(), new 8());
    }
}
