package com.huluxia.module.topic;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.huluxia.data.UserBaseInfo;
import com.huluxia.data.j;
import com.huluxia.data.topic.TopicItem;
import com.huluxia.framework.base.http.io.impl.request.GsonObjRequestBuilder;
import com.huluxia.framework.base.http.io.impl.request.StringRequestBuilder;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.http.HttpMgr;
import com.huluxia.module.ab;
import com.huluxia.module.w;
import com.huluxia.r;
import com.huluxia.t;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.tools.ant.types.selectors.ContainsSelector;

/* compiled from: TopicModule2 */
public class k {
    private static k aCJ = null;
    private static final String aCK = "http://upload.huluxia.net/upload/image/avatar";
    private static final String eI = "http://upload/video";
    private final String TAG = "TopicModule2";
    private Handler mHandler;
    private HandlerThread thread;

    public static synchronized k Ej() {
        k kVar;
        synchronized (k.class) {
            if (aCJ == null) {
                aCJ = new k();
            }
            kVar = aCJ;
        }
        return kVar;
    }

    public void b(long topicId, int start, int count) {
    }

    public void a(long cat_id, long tag_id, int sort_by, String start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", start);
        param.put("count", String.valueOf(count));
        param.put("cat_id", String.valueOf(cat_id));
        param.put("tag_id", String.valueOf(tag_id));
        param.put("sort_by", String.valueOf(sort_by));
        HttpMgr.getInstance().performStringRequest(ab.azQ, param, new 1(this, start, cat_id, tag_id), new 12(this, start, cat_id, tag_id));
    }

    public void c(long cat_id, int start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        param.put("cat_id", String.valueOf(cat_id));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.ayW, h.class).setParams(param)).setSuccListener(new 21(this)).setErrListener(new 20(this)).execute();
    }

    public void ba(long post_id) {
    }

    public void a(long catId, String keyword, String start, int count) {
        Map<String, String> param = new HashMap();
        param.put("start", start);
        param.put("count", String.valueOf(count));
        param.put("cat_id", String.valueOf(catId));
        param.put("keyword", String.valueOf(keyword));
        HttpMgr.getInstance().performStringRequest(ab.azV, param, new 22(this, start), new 23(this, start));
    }

    public void a(TopicItem topicItem, boolean auth) {
        if (topicItem != null) {
            Map<String, String> param = new HashMap();
            param.put("type_id", String.valueOf(auth ? 1 : 0));
            param.put("post_id", String.valueOf(topicItem.getPostID()));
            ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.azU, w.class).setParams(param)).setSuccListener(new 25(this, topicItem, auth)).setErrListener(new 24(this, topicItem, auth)).execute();
        }
    }

    public void Ek() {
    }

    public void a(Context context, long cat_id, String tag, boolean next, Object clickitem) {
        if (j.ep().ey()) {
            a(cat_id, tag, 1, next, clickitem);
        } else {
            t.an(context);
        }
    }

    public void b(Context context, long cat_id, String tag, boolean next, Object clickitem) {
        if (j.ep().ey()) {
            a(cat_id, tag, 2, next, clickitem);
        } else {
            t.an(context);
        }
    }

    private void a(long cat_id, String tag, int type_id, boolean next, Object clickitem) {
        Map<String, String> param = new HashMap();
        param.put("cat_id", String.valueOf(cat_id));
        param.put("type_id", String.valueOf(type_id));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.aBp, g.class).setParams(param)).setSuccListener(new 2(this, type_id, cat_id, tag, next, clickitem)).setErrListener(new 26(this, tag, next, clickitem)).execute();
    }

    public void a(long post_id, long comment_id, String text, String patcha, List<String> images) {
        Map<String, String> param = new HashMap();
        param.put("post_id", String.valueOf(post_id));
        param.put("comment_id", String.valueOf(comment_id));
        param.put(ContainsSelector.CONTAINS_KEY, text);
        param.put("patcha", patcha);
        String image = "";
        for (String szfid : images) {
            image = image + szfid + MiPushClient.ACCEPT_TIME_SEPARATOR;
        }
        param.put("images", image);
        HttpMgr.getInstance().performPostStringRequest(ab.aBr, null, param, new 3(this), new 4(this), true, false, true, 10000);
    }

    public void a(String title, String detail, long cat_id, long tag_id, int type, String patcha, String voice, List<String> images, List<UserBaseInfo> remindUsers) {
        Map<String, String> param = new HashMap();
        param.put("cat_id", String.valueOf(cat_id));
        param.put("tag_id", String.valueOf(tag_id));
        param.put("type", String.valueOf(type));
        param.put("title", title);
        param.put(r.gQ, detail);
        param.put("patcha", patcha);
        param.put("voice", voice);
        String image = "";
        for (String szfid : images) {
            image = image + szfid + MiPushClient.ACCEPT_TIME_SEPARATOR;
        }
        param.put("images", image);
        if (!UtilsFunction.empty((Collection) remindUsers)) {
            String remindUsersStr = "";
            for (UserBaseInfo info : remindUsers) {
                remindUsersStr = remindUsersStr + info.userID + MiPushClient.ACCEPT_TIME_SEPARATOR;
            }
            param.put("user_ids", remindUsersStr);
        }
        HttpMgr.getInstance().performPostStringRequest(ab.azN, null, param, new 5(this), new 6(this), true, false, true, 10000);
    }

    public void El() {
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.ayZ, m.class).setSuccListener(new 8(this))).setErrListener(new 7(this)).execute();
    }

    public void ke(int id) {
        Map<String, String> param = new HashMap();
        param.put("fum_id", String.valueOf(id));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.ayY, c.class).setParams(param)).setSuccListener(new 10(this, id)).setErrListener(new 9(this, id)).execute();
    }

    public void i(int id, boolean subscribe) {
        Map<String, String> param = new HashMap();
        param.put("cat_ids", String.valueOf(id));
        ((StringRequestBuilder) ((StringRequestBuilder) ((StringRequestBuilder) HttpMgr.getInstance().getStringReqBuilder(subscribe ? ab.azc : ab.azd).setParams(param)).setSuccListener(new 13(this))).setErrListener(new 11(this))).execute();
    }

    public void a(long post_id, int page_no, int page_size, boolean isFloor, int position, Context context) {
        HashMap<String, String> param = new HashMap();
        param.put("post_id", String.valueOf(post_id));
        param.put("page_no", String.valueOf(page_no));
        param.put("page_size", String.valueOf(page_size));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(isFloor ? ab.azP : ab.azO, j.class).setCache(false)).setSuccListener(new 15(this, position, context)).setErrListener(new 14(this, position, context)).setParams(param).execute();
    }

    public void b(long post_id, int page_no, int page_size, boolean isFloor, int position, Context context) {
        HashMap<String, String> param = new HashMap();
        param.put("post_id", String.valueOf(post_id));
        param.put("page_no", String.valueOf(page_no));
        param.put("page_size", String.valueOf(page_size));
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(isFloor ? ab.azP : ab.azO, j.class).setCache(false)).setSuccListener(new 17(this, position, post_id, context)).setErrListener(new 16(this, position, post_id, context)).setParams(param).execute();
    }

    public void at(Object context) {
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(ab.azY, l.class).setCache(false)).setSuccListener(new 19(this, context)).setErrListener(new 18(this, context)).execute();
    }
}
