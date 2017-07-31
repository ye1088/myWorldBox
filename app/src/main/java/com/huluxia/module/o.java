package com.huluxia.module;

import com.huluxia.framework.base.http.io.impl.request.GsonObjRequestBuilder;
import com.huluxia.framework.http.HttpMgr;
import hlx.module.resources.b;
import java.util.HashMap;
import java.util.Map;

/* compiled from: ModuleRequestWrapper */
public class o {
    public static void N(int inputRequestCode, int inputStart, int inputCount) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(inputStart));
        param.put("count", String.valueOf(inputCount));
        HttpMgr.getInstance().performStringRequest(m.auL, param, new 1(inputRequestCode), new 12(inputRequestCode));
    }

    public static void O(int inputRequestCode, int inputStart, int inputCount) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(inputStart));
        param.put("count", String.valueOf(inputCount));
        HttpMgr.getInstance().performStringRequest(m.auM, param, new 14(inputRequestCode), new 15(inputRequestCode));
    }

    public static void a(int srcTag, String url, Map<String, String> param) {
        HttpMgr.getInstance().performStringRequest(url, param, new 16(srcTag), new 17(srcTag));
    }

    public static void b(int srcTag, String url, Map<String, String> param) {
        HttpMgr.getInstance().performStringRequest(url, param, new 18(srcTag), new 19(srcTag));
    }

    public static void f(int srcTag, String url) {
        HttpMgr.getInstance().performStringRequest(url, new HashMap(), new 20(srcTag), new 2(srcTag));
    }

    public static void dY(String url) {
        HttpMgr.getInstance().performStringRequest(url, new HashMap(), new 3(), new 4());
    }

    public static void p(int srcTag, int cateId, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        param.put("cat_id", String.valueOf(cateId));
        a(srcTag, m.auQ, param);
    }

    public static void q(int srcTag, int cateId, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        param.put("cat_id", String.valueOf(cateId));
        d(srcTag, m.atH, param);
    }

    public static void r(int srcTag, int subjectId, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        param.put("topic_id", String.valueOf(subjectId));
        b(srcTag, m.atI, param);
    }

    public static void P(int srcTag, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        e(srcTag, m.atA, param);
    }

    public static void Q(int srcTag, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        d(srcTag, m.atC, param);
    }

    public static void R(int srcTag, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        c(srcTag, m.atB, param);
    }

    public static void je(int srcTag) {
        f(srcTag, m.atZ);
    }

    public static void a(int srcTag, String strVerFilter, int cateId, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("versionFilter", strVerFilter);
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        param.put("cat_id", String.valueOf(cateId));
        d(srcTag, m.auj, param);
    }

    public static void s(int srcTag, int subjectId, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        param.put("topic_id", String.valueOf(subjectId));
        b(srcTag, m.aui, param);
    }

    public static void a(int srcTag, String strVerFilter, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        e(srcTag, m.aul, param);
    }

    public static void b(int srcTag, String strVerFilter, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("versionFilter", strVerFilter);
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        c(srcTag, m.aum, param);
    }

    public static void c(int srcTag, String strVerFilter, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("versionFilter", strVerFilter);
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        d(srcTag, m.aue, param);
    }

    public static void jf(int srcTag) {
        f(srcTag, m.auh);
    }

    public static void b(int srcTag, String strVerFilter, int cateId, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("versionFilter", strVerFilter);
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        param.put("cat_id", String.valueOf(cateId));
        d(srcTag, m.auD, param);
    }

    public static void t(int srcTag, int subjectId, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        param.put("topic_id", String.valueOf(subjectId));
        b(srcTag, m.auC, param);
    }

    public static void d(int srcTag, String strVerFilter, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        e(srcTag, m.auF, param);
    }

    public static void e(int srcTag, String strVerFilter, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("versionFilter", strVerFilter);
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        c(srcTag, m.auG, param);
    }

    public static void f(int srcTag, String strVerFilter, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("versionFilter", strVerFilter);
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        d(srcTag, m.aux, param);
    }

    public static void jg(int srcTag) {
        f(srcTag, m.auB);
    }

    public static void c(int srcTag, String strVerFilter, int cateId, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("versionFilter", strVerFilter);
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        param.put("cat_id", String.valueOf(cateId));
        d(srcTag, m.aut, param);
    }

    public static void u(int srcTag, int subjectId, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        param.put("topic_id", String.valueOf(subjectId));
        b(srcTag, m.aus, param);
    }

    public static void g(int srcTag, String strVerFilter, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        e(srcTag, m.auv, param);
    }

    public static void h(int srcTag, String strVerFilter, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("versionFilter", strVerFilter);
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        c(srcTag, m.auw, param);
    }

    public static void i(int srcTag, String strVerFilter, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("versionFilter", strVerFilter);
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        d(srcTag, m.aun, param);
    }

    public static void jh(int srcTag) {
        f(srcTag, m.aur);
    }

    public static void DH() {
        dY(m.axZ);
    }

    public static void c(int srcTag, String url, Map<String, String> param) {
        HttpMgr.getInstance().performStringRequest(url, param, new 5(srcTag), new 6(srcTag));
    }

    public static void d(int srcTag, String url, Map<String, String> param) {
        HttpMgr.getInstance().performStringRequest(url, param, new 7(srcTag), new 8(srcTag));
    }

    public static void e(int srcTag, String url, Map<String, String> param) {
        HttpMgr.getInstance().performStringRequest(url, param, new 9(srcTag), new 10(srcTag));
    }

    public static void DI() {
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.axZ, b.class).setSuccListener(new 13())).setErrListener(new 11()).execute();
    }
}
