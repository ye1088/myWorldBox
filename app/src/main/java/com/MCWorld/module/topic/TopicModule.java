package com.MCWorld.module.topic;

import android.os.Handler;
import android.os.HandlerThread;
import com.MCWorld.framework.http.HttpMgr;
import com.MCWorld.module.ab;
import java.util.HashMap;
import java.util.Map;
import org.apache.tools.ant.types.selectors.ContainsSelector;

public class TopicModule {
    private static final String TAG = "TopicModule";
    private static TopicModule aCD = null;
    private static final String eI = "http://upload.huluxia.net/upload/file";
    private Handler mHandler;
    private HandlerThread thread;

    public static synchronized TopicModule Ef() {
        TopicModule topicModule;
        synchronized (TopicModule.class) {
            if (aCD == null) {
                aCD = new TopicModule();
            }
            topicModule = aCD;
        }
        return topicModule;
    }

    public void b(long post_id, int page_no, boolean onlyFloor) {
        Map<String, String> param = new HashMap();
        param.put("post_id", String.valueOf(post_id));
        param.put("page_no", String.valueOf(page_no));
        param.put("page_size", "20");
        HttpMgr.getInstance().performStringRequest(onlyFloor ? ab.azP : ab.azO, param, new 1(this, post_id), new 12(this, post_id));
    }

    public void a(boolean isfloor, long id, String score, String score_txt) {
        Map<String, String> postparam = new HashMap();
        postparam.put("type_id", isfloor ? "1" : "2");
        postparam.put("post_id", String.valueOf(id));
        postparam.put("score", score);
        postparam.put("score_txt", score_txt);
        postparam.put("isadmin", "0");
        HttpMgr.getInstance().performPostStringRequest(ab.aAn, null, postparam, new 22(this), new 23(this), true, true);
    }

    public void aV(long id) {
        Map<String, String> postparam = new HashMap();
        postparam.put("post_id", String.valueOf(id));
        HttpMgr.getInstance().performPostStringRequest(ab.azR, null, postparam, new 24(this), new 25(this), true, true);
    }

    public void aW(long id) {
        Map<String, String> postparam = new HashMap();
        postparam.put("post_id", String.valueOf(id));
        HttpMgr.getInstance().performPostStringRequest(ab.azS, null, postparam, new 26(this), new 27(this), true, true);
    }

    public void aX(long id) {
        Map<String, String> postparam = new HashMap();
        postparam.put("post_id", String.valueOf(id));
        HttpMgr.getInstance().performPostStringRequest(ab.azT, null, postparam, new 28(this, id), new 2(this, id), true, true);
    }

    public void g(long id, long seq) {
        Map<String, String> postparam = new HashMap();
        postparam.put("comment_id", String.valueOf(id));
        HttpMgr.getInstance().performPostStringRequest(ab.aAj, null, postparam, new 3(this, id, seq), new 4(this, id, seq), true, true);
    }

    public void c(long type_id, long file_id, int complaint_type) {
        Map<String, String> postparam = new HashMap();
        postparam.put("type_id", String.valueOf(type_id));
        postparam.put("file_id", String.valueOf(file_id));
        postparam.put("complaint_type", String.valueOf(complaint_type));
        HttpMgr.getInstance().performPostStringRequest(ab.aAl, null, postparam, new 5(this), new 6(this), true, true);
    }

    public void Eg() {
        HttpMgr.getInstance().performStringRequest(ab.azW, null, new 7(this), new 8(this));
    }

    public void c(long id, boolean isfavor) {
        Map<String, String> postparam = new HashMap();
        postparam.put("post_id", String.valueOf(id));
        HttpMgr.getInstance().performPostStringRequest(isfavor ? ab.azJ : ab.azK, null, postparam, new 9(this, id, isfavor), new 10(this, id, isfavor), true, true);
    }

    public void aY(long id) {
        Map<String, String> postparam = new HashMap();
        postparam.put("post_id", String.valueOf(id));
        HttpMgr.getInstance().performPostStringRequest(ab.azI, null, postparam, new 11(this, id), new 13(this, id), true, true);
    }

    public void aZ(long mapCategoryId) {
        Map<String, String> param = new HashMap();
        param.put("cat_id", String.valueOf(mapCategoryId));
        HttpMgr.getInstance().performStringRequest(ab.ayX, param, new 14(this), new 15(this));
    }

    public void Eh() {
        HttpMgr.getInstance().performStringRequest(ab.aAA, new 16(this), new 17(this));
    }

    public void a(boolean reviewOk, long post_id, String reason, int type_id) {
        HashMap<String, String> param = new HashMap();
        param.put("status", reviewOk ? "1" : "2");
        param.put("post_id", String.valueOf(post_id));
        param.put("type_id", String.valueOf(type_id));
        param.put(ContainsSelector.CONTAINS_KEY, reason);
        HttpMgr.getInstance().performStringRequest(ab.aAB, param, new 18(this), new 19(this));
    }

    public void Ei() {
        HttpMgr.getInstance().performStringRequest(ab.aAI, new 20(this), new 21(this));
    }
}
