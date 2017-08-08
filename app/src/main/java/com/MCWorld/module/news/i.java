package com.MCWorld.module.news;

import com.MCWorld.framework.base.http.io.impl.request.GsonObjRequestBuilder;
import com.MCWorld.framework.http.HttpMgr;
import com.MCWorld.module.ab;
import com.MCWorld.module.w;
import java.util.HashMap;
import org.apache.tools.ant.types.selectors.ContainsSelector;

/* compiled from: NewsModule */
public class i {
    private static i aCg;

    public static synchronized i Ea() {
        i iVar;
        synchronized (i.class) {
            if (aCg == null) {
                aCg = new i();
            }
            iVar = aCg;
        }
        return iVar;
    }

    public void ar(int start, int count) {
        HashMap<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.aAN, k.class).setCache(false)).setSuccListener(new 7(this)).setErrListener(new 1(this)).setParams(param).execute();
    }

    public void aP(long newsId) {
        HashMap<String, String> param = new HashMap();
        param.put("news_id", String.valueOf(newsId));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.aAO, e.class).setCache(false)).setSuccListener(new 9(this)).setErrListener(new 8(this)).setParams(param).execute();
    }

    public void d(int start, long newsId) {
        HashMap<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("news_id", String.valueOf(newsId));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.aAT, e.class).setCache(false)).setSuccListener(new 11(this)).setErrListener(new 10(this)).setParams(param).execute();
    }

    public void a(long newsId, long commentId, String text, String context) {
        HashMap<String, String> param = new HashMap();
        param.put("news_id", String.valueOf(newsId));
        param.put("comment_id", String.valueOf(commentId));
        param.put(ContainsSelector.CONTAINS_KEY, text);
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.aAS, w.class).setCache(false)).setSuccListener(new 13(this, context)).setErrListener(new 12(this, context)).setParams(param).execute();
    }

    public void aQ(long newsId) {
        HashMap<String, String> param = new HashMap();
        param.put("news_id", String.valueOf(newsId));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.aAP, b.class).setCache(false)).setSuccListener(new 2(this)).setErrListener(new 14(this)).setParams(param).execute();
    }

    public void b(long newsId, boolean like) {
        HashMap<String, String> param = new HashMap();
        param.put("news_id", String.valueOf(newsId));
        String url = "";
        if (like) {
            url = ab.aAQ;
        } else {
            url = ab.aAR;
        }
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(url, w.class).setCache(false)).setSuccListener(new 4(this, like)).setErrListener(new 3(this, like)).setParams(param).execute();
    }

    public void e(int start, long mUserId) {
        HashMap<String, String> param = new HashMap();
        param.put("user_id", String.valueOf(mUserId));
        param.put("start", String.valueOf(start));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.aAU, k.class).setCache(false)).setSuccListener(new 6(this, mUserId)).setErrListener(new 5(this, mUserId)).setParams(param).execute();
    }
}
