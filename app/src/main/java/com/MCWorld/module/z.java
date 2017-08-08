package com.MCWorld.module;

import com.MCWorld.data.j;
import com.MCWorld.data.map.f;
import com.MCWorld.data.profile.c;
import com.MCWorld.data.profile.d;
import com.MCWorld.data.profile.e;
import com.MCWorld.data.profile.e.a;
import com.MCWorld.data.studio.b;
import com.MCWorld.data.studio.g;
import com.MCWorld.framework.base.http.io.impl.request.GsonObjRequestBuilder;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.http.HttpMgr;
import com.tencent.open.SocialConstants;
import java.util.HashMap;
import java.util.Map;

/* compiled from: StudioModule */
public class z {
    private final String TAG;

    private z() {
        this.TAG = "StudioModule";
    }

    public static z DO() {
        return a.DP();
    }

    public static void b(long userId, Object ctx) {
        HashMap<String, String> param = new HashMap();
        param.put("user_id", String.valueOf(userId));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.azg, y.class).setParams(param)).setSuccListener(new 12(userId, ctx)).setErrListener(new 1(userId, ctx)).execute();
    }

    public static void v(int start, int count, int studioId, int position) {
        a(start, count, studioId, j.ep().getUserid(), position);
    }

    public static void a(int start, int count, int studioId, long userId, int position) {
        HashMap<String, String> param = new HashMap();
        param.put("studio_id", String.valueOf(studioId));
        param.put("user_id", String.valueOf(userId));
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.azi, e.class).setParams(param)).setSuccListener(new 34(position, studioId)).setErrListener(new 23(position, studioId)).execute();
    }

    public static void a(int studioId, long userId, a user) {
        HashMap<String, String> param = new HashMap();
        param.put("studio_id", String.valueOf(studioId));
        param.put("user_id", String.valueOf(userId));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.azj, w.class).setParams(param)).setSuccListener(new 45(user, studioId)).setErrListener(new 44(user, studioId)).execute();
    }

    public static void b(int studioId, Object ctx) {
        HashMap<String, String> param = new HashMap();
        param.put("studio_id", String.valueOf(studioId));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.azh, c.class).setParams(param)).setSuccListener(new 47(studioId, ctx)).setErrListener(new 46(studioId, ctx)).execute();
    }

    public static void w(int studioId, int resType, int start, int count) {
        HashMap<String, String> param = new HashMap();
        param.put("id", String.valueOf(studioId));
        param.put("type_id", String.valueOf(resType));
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.azq, f.class).setParams(param)).setSuccListener(new 2(studioId, resType)).setErrListener(new 48(studioId, resType)).execute();
    }

    public static void U(int start, int count, int studioId) {
        HashMap<String, String> param = new HashMap();
        param.put("studio_id", String.valueOf(studioId));
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.azl, d.class).setParams(param)).setSuccListener(new 4(studioId)).setErrListener(new 3(studioId)).execute();
    }

    public static void V(int tag, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.azr, com.MCWorld.data.studio.a.class).setParams(param)).setSuccListener(new 6(tag)).setErrListener(new 5(tag)).execute();
    }

    public static void W(int tag, int start, int count) {
        a(tag, start, count, "");
    }

    public static void a(int tag, int start, int count, String time) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        if (!UtilsFunction.empty((CharSequence) time)) {
            param.put("begin_time", time);
        }
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.azw, com.MCWorld.data.studio.e.class).setParams(param)).setSuccListener(new 8(tag)).setErrListener(new 7(tag)).execute();
    }

    public static void jo(int tag) {
        g(tag, "");
    }

    public static void g(int tag, String time) {
        Map<String, String> param = new HashMap();
        if (!UtilsFunction.empty((CharSequence) time)) {
            param.put("studio_rank_time", time);
        }
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.azt, com.MCWorld.data.studio.f.class).setParams(param)).setSuccListener(new 10(tag)).setErrListener(new 9(tag)).execute();
    }

    public static void jp(int tag) {
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.azu, g.class).setSuccListener(new 13(tag))).setErrListener(new 11(tag)).execute();
    }

    public static void jq(int studioId) {
        HashMap<String, String> param = new HashMap();
        param.put("studio_id", String.valueOf(studioId));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.azk, w.class).setParams(param)).setSuccListener(new 15()).setErrListener(new 14()).execute();
    }

    public static void a(int studioId, String studioName, String qq, String qqGroup, String description, String imageUrl, String coverimg, String studioTags) {
        HashMap<String, String> param = new HashMap();
        param.put("studio_id", String.valueOf(studioId));
        param.put("name", studioName);
        param.put("qq", qq);
        param.put("qqgroup", qqGroup);
        param.put(SocialConstants.PARAM_COMMENT, description);
        param.put("image", imageUrl);
        param.put("coverimg", coverimg);
        param.put("studio_tags", studioTags);
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.azn, w.class).setPostParam(param)).setSuccListener(new 17(studioId)).setErrListener(new 16(studioId)).execute();
    }

    public static void a(String name, String qq, String qqgroup, String description, String icon, String tags, String coverimg, int typeId) {
        HashMap<String, String> param = new HashMap();
        param.put("name", name);
        param.put("qq", qq);
        param.put("qqgroup", qqgroup);
        param.put(SocialConstants.PARAM_COMMENT, description);
        param.put("image", icon);
        param.put("coverimg", coverimg);
        param.put("studio_tags", tags);
        param.put("type_id", String.valueOf(typeId));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.azv, w.class).setPostParam(param)).setSuccListener(new 19()).setErrListener(new 18()).execute();
    }

    public static void b(long id, int auditOpt, int position, int sid) {
        HashMap<String, String> param = new HashMap();
        param.put("id", String.valueOf(id));
        param.put("audit_opt", String.valueOf(auditOpt));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.azo, w.class).setParams(param)).setSuccListener(new 21(position, auditOpt, sid)).setErrListener(new 20(position, auditOpt, sid)).execute();
    }

    public static void DK() {
        HttpMgr.getInstance().performStringRequest(ab.axZ, new 22(), new 24());
    }

    public static void jr(int tag) {
        HttpMgr.getInstance().performStringRequest(ab.azs, new 25(tag), new 26(tag));
    }

    public static void X(int typeId, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("type_id", String.valueOf(typeId));
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.azy, f.class).setParams(param)).setSuccListener(new 28(typeId)).setErrListener(new 27(typeId)).execute();
    }

    public void a(int sid, String title, String content) {
        Map<String, String> param = new HashMap();
        param.put("studio_id", String.valueOf(sid));
        param.put("title", title);
        param.put("content", content);
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.azA, w.class).setPostParam(param)).setSuccListener(new 30(this, sid)).setErrListener(new 29(this, sid)).execute();
    }

    public void Y(int sid, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("studio_id", String.valueOf(sid));
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.azB, b.class).setParams(param)).setSuccListener(new 32(this, sid)).setErrListener(new 31(this, sid)).execute();
    }

    public void js(int id) {
        Map<String, String> param = new HashMap();
        param.put("id", String.valueOf(id));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.azC, w.class).setParams(param)).setSuccListener(new 35(this, id)).setErrListener(new 33(this, id)).execute();
    }

    public void b(int sid, long userId, a studioUser) {
        HashMap<String, String> param = new HashMap();
        param.put("studio_id", String.valueOf(sid));
        param.put("user_id", String.valueOf(userId));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.azE, w.class).setParams(param)).setSuccListener(new 37(this, sid, studioUser)).setErrListener(new 36(this, sid, studioUser)).execute();
    }

    public void c(int sid, long userId, a studioUser) {
        HashMap<String, String> param = new HashMap();
        param.put("studio_id", String.valueOf(sid));
        param.put("user_id", String.valueOf(userId));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.azF, w.class).setParams(param)).setSuccListener(new 39(this, sid, studioUser)).setErrListener(new 38(this, sid, studioUser)).execute();
    }

    public void c(int sid, long userId) {
        HashMap<String, String> param = new HashMap();
        param.put("studio_id", String.valueOf(sid));
        param.put("user_id", String.valueOf(userId));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.azG, w.class).setParams(param)).setSuccListener(new 41(this, sid)).setErrListener(new 40(this, sid)).execute();
    }

    public void ap(int sid, int newsId) {
        if (j.ep().ey()) {
            HashMap<String, String> param = new HashMap();
            param.put("studio_id", String.valueOf(sid));
            param.put("id", String.valueOf(newsId));
            ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.azH, b.class).setParams(param)).setSuccListener(new 43(this, sid)).setErrListener(new 42(this, sid)).execute();
        }
    }
}
