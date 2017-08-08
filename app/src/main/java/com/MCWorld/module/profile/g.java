package com.MCWorld.module.profile;

import android.content.Context;
import android.support.annotation.z;
import com.MCWorld.data.profile.ProfileInfo;
import com.MCWorld.db.d;
import com.MCWorld.framework.base.async.AsyncTaskCenter;
import com.MCWorld.framework.base.http.io.impl.request.GsonObjRequestBuilder;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.http.HttpMgr;
import com.MCWorld.module.ab;
import com.MCWorld.module.p;
import com.MCWorld.module.topic.b;
import com.MCWorld.module.w;
import com.MCWorld.utils.UtilsMenu.COMPLAINT_VALUE;
import io.netty.handler.codec.rtsp.RtspHeaders.Values;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: ProfileModule */
public class g {
    private static final String TAG = "ProfileModule";
    public static final int aCo = 2;
    public static final int aCp = 3;
    public static final int aCq = 4;
    private static g aCr = null;
    public static final int tq = 0;
    public static final int tr = 1;

    private g() {
    }

    public static synchronized g Eb() {
        g gVar;
        synchronized (g.class) {
            if (aCr == null) {
                aCr = new g();
            }
            gVar = aCr;
        }
        return gVar;
    }

    public void Ec() {
        HttpMgr.getInstance().performStringRequest(ab.aAC, new 1(this), new 12(this));
    }

    public void ec(String newNick) {
        HashMap<String, String> params = new HashMap();
        params.put("nick", newNick);
        HttpMgr.getInstance().performStringRequest(ab.aAD, params, new 23(this, newNick), new 34(this, newNick));
    }

    public void jT(int typeId) {
        HashMap<String, String> param = new HashMap();
        param.put("type_id", String.valueOf(typeId));
        HttpMgr.getInstance().performStringRequest(ab.aAE, param, new 36(this, typeId), new 37(this, typeId));
    }

    public void aR(long userId) {
        HashMap<String, String> param = new HashMap();
        param.put("user_id", String.valueOf(userId));
        AtomicBoolean networkComplete = new AtomicBoolean(false);
        AsyncTaskCenter.getInstance().executeSingleThread(new 38(this, userId, networkComplete));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.aAF, ProfileInfo.class).setParams(param)).setSuccListener(new 40(this, networkComplete, userId)).setErrListener(new 39(this, userId)).execute();
    }

    public void aS(long userId) {
        HashMap<String, String> param = new HashMap();
        param.put("user_id", String.valueOf(userId));
        AtomicBoolean networkComplete = new AtomicBoolean(false);
        AsyncTaskCenter.getInstance().executeSingleThread(new 2(this, userId, networkComplete));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.aAF, ProfileInfo.class).setParams(param)).setSuccListener(new 4(this, networkComplete, userId)).setErrListener(new 3(this, userId)).execute();
    }

    public void as(int start, int count) {
        HashMap<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        HttpMgr.getInstance().performStringRequest(ab.aAG, param, new 5(this), new 6(this));
    }

    public void h(long productId, String ext) {
        HashMap<String, String> param = new HashMap();
        param.put("product_id", String.valueOf(productId));
        param.put("ext", ext);
        HttpMgr.getInstance().performStringRequest(ab.aAH, param, new 7(this), new 8(this));
    }

    public void k(String openid, String qtoken, String context) {
        String customer = "100580922";
        HashMap<String, String> param = new HashMap();
        param.put("openid", openid);
        param.put("access_token", qtoken);
        param.put("oauth_consumer_key", "100580922");
        HttpMgr.getInstance().performStringRequest(ab.aBe, param, new 9(this, context, openid, qtoken), new 10(this, context, openid, qtoken));
    }

    private void c(long file_id, long type_id, int type_comlaint) {
        HashMap<String, String> param = new HashMap();
        param.put("file_id", String.valueOf(file_id));
        param.put("type_id", String.valueOf(type_id));
        param.put("complaint_type", String.valueOf(type_comlaint));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.aAl, w.class).setParams(param)).setSuccListener(new 13(this)).setErrListener(new 11(this)).execute();
    }

    public void m(long topic_id, int complaintType) {
        c(topic_id, 2, complaintType);
    }

    public void n(long comment_id, int complaintType) {
        c(comment_id, 3, complaintType);
    }

    public void aT(long user_id) {
        c(user_id, 4, COMPLAINT_VALUE.IMAGE.Value());
    }

    @z
    public ProfileInfo aU(long userId) {
        p info = d.eK().q(userId);
        if (info == null || UtilsFunction.empty(info.json)) {
            return null;
        }
        try {
            return (ProfileInfo) Json.parseJsonObject(info.json, ProfileInfo.class);
        } catch (Exception e) {
            HLog.error(this, "getCacheProfileInfo error " + e, new Object[0]);
            return null;
        }
    }

    public void a(int newModel, Context context) {
        HashMap<String, String> param = new HashMap();
        param.put(Values.MODE, String.valueOf(newModel));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.aBs, w.class).setParams(param)).setSuccListener(new 15(this, newModel, context)).setErrListener(new 14(this, newModel, context)).execute();
    }

    public void ed(String fid) {
        HashMap<String, String> param = new HashMap();
        param.put("image", String.valueOf(fid));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.aBt, a.class).setParams(param)).setSuccListener(new 17(this, fid)).setErrListener(new 16(this, fid)).execute();
    }

    public void at(int start, int count) {
        HashMap<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.aBu, i.class).setParams(param)).setSuccListener(new 19(this)).setErrListener(new 18(this)).setCache(false).execute();
    }

    public void b(int id, Context context) {
        HashMap<String, String> param = new HashMap();
        param.put("space_id", String.valueOf(id));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.aBv, w.class).setParams(param)).setSuccListener(new 21(this, id, context)).setErrListener(new 20(this, id, context)).execute();
    }

    public void c(int spaceId, Context context) {
        HashMap<String, String> param = new HashMap();
        param.put("space_id", String.valueOf(spaceId));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.aBx, w.class).setParams(param)).setSuccListener(new 24(this, spaceId, context)).setErrListener(new 22(this, spaceId, context)).execute();
    }

    public void a(int spaceId, String code, Context context) {
        HashMap<String, String> param = new HashMap();
        param.put("space_id", String.valueOf(spaceId));
        param.put("code", code);
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.aBw, w.class).setParams(param)).setSuccListener(new 26(this, spaceId, context)).setErrListener(new 25(this, spaceId, context)).execute();
    }

    public void a(int start, int count, long uid, Context context) {
        HashMap<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        param.put("user_id", String.valueOf(uid));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.aBy, d.class).setCache(false)).setSuccListener(new 28(this, start, context)).setErrListener(new 27(this, start, context)).setParams(param).execute();
    }

    public void b(int start, int count, long uid, Context context) {
        HashMap<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        param.put("user_id", String.valueOf(uid));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.aBz, d.class).setCache(false)).setSuccListener(new 30(this, start, context)).setErrListener(new 29(this, start, context)).setParams(param).execute();
    }

    public void a(int start, int count, Context context) {
        HashMap<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.aBA, d.class).setCache(false)).setSuccListener(new 32(this, start, context)).setErrListener(new 31(this, start, context)).setParams(param).execute();
    }

    public void a(String start, int count, long mUserId) {
        HashMap<String, String> param = new HashMap();
        param.put("start", start);
        param.put("count", String.valueOf(count));
        param.put("user_id", String.valueOf(mUserId));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.aBB, b.class).setParams(param)).setSuccListener(new 35(this, start, mUserId)).setErrListener(new 33(this, start, mUserId)).execute();
    }
}
