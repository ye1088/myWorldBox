package com.MCWorld.widget.pushserver;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.MCWorld.HTApplication;
import com.MCWorld.data.j;
import com.MCWorld.data.message.MsgCounts;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.utils.UtilsSystem;
import com.MCWorld.r;
import com.MCWorld.service.h;
import com.MCWorld.service.i;
import com.MCWorld.widget.Constants;
import com.MCWorld.widget.Constants.PushMsgType;
import io.netty.handler.codec.rtsp.RtspHeaders.Values;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: PushClient */
public class a {
    protected static final String ACTION_LOGIN = "com.baidu.pushdemo.action.LOGIN";
    public static final String ACTION_MESSAGE = "com.baiud.pushdemo.action.MESSAGE";
    public static final String ACTION_RESPONSE = "bccsclient.action.RESPONSE";
    protected static final String EXTRA_ACCESS_TOKEN = "access_token";
    public static final String EXTRA_MESSAGE = "message";
    public static final String TAG = a.class.getSimpleName();
    public static final int bFb = 1;
    public static final String bFc = "method";
    public static final String bFd = "content";
    public static final String bFe = "errcode";
    public static final String bFf = "bccsclient.action.SHOW_MESSAGE";
    public static String bFg = "";
    public static final int bFh = 0;
    public static final int bFi = 1;
    public static final int bFj = 2;
    private static final String bFk = "push_0";
    private static a bFl = null;

    public static synchronized a PV() {
        a aVar;
        synchronized (a.class) {
            if (bFl == null) {
                bFl = new a();
            }
            aVar = bFl;
        }
        return aVar;
    }

    public static List<String> gS(String originalText) {
        if (originalText == null || originalText.equals("")) {
            return null;
        }
        List<String> tags = new ArrayList();
        int indexOfComma = originalText.indexOf(44);
        while (indexOfComma != -1) {
            tags.add(originalText.substring(0, indexOfComma));
            originalText = originalText.substring(indexOfComma + 1);
            indexOfComma = originalText.indexOf(44);
        }
        tags.add(originalText);
        return tags;
    }

    public static String bM(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("log_text", "");
    }

    public static void Q(Context context, String text) {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("log_text", text);
        editor.commit();
    }

    public void c(int model, String message, int notifyid) {
        JSONException e;
        HLog.info(TAG, "message %s", message);
        if (!TextUtils.isEmpty(message)) {
            try {
                JSONObject jSONObject = new JSONObject(message);
                try {
                    int ver = jSONObject.optInt("ver", 0);
                    if (ver == 0 || ver <= 1) {
                        String title = jSONObject.optString("title", null);
                        int type = jSONObject.optInt("type", PushMsgType.INVALID.Value());
                        int notice = jSONObject.optInt("notice", 1);
                        int harry = jSONObject.optInt("harry", 0);
                        int sound = jSONObject.optInt("sound", 1);
                        int shake = jSONObject.optInt("shake", 1);
                        Long id;
                        String content;
                        if (type == PushMsgType.COUNTS.Value()) {
                            id = Long.valueOf(jSONObject.optLong("key", -1));
                            JSONObject countsJson = jSONObject.optJSONObject("counts");
                            content = jSONObject.optString("content", null);
                            if (id.longValue() != -1 && countsJson != null && j.ep().ey() && j.ep().getUserid() == id.longValue()) {
                                a(id.longValue(), title, content, new MsgCounts(countsJson), notice, harry, sound, shake);
                            }
                        } else if (type == PushMsgType.KICK.Value()) {
                            id = Long.valueOf(jSONObject.optLong("key", -1));
                            content = jSONObject.optString("content", null);
                            if (id.longValue() != -1 && content != null && j.ep().ey() && j.ep().getUserid() == id.longValue()) {
                                i.i(id.longValue(), content);
                            }
                        } else if (type == PushMsgType.MODE.Value()) {
                            int mode = jSONObject.optInt(Values.MODE, 1);
                            if (mode == 1 || mode == 2) {
                                j.ep().P(mode);
                            }
                        } else if (type == PushMsgType.TOPIC.Value()) {
                            id = jSONObject.optLong("key", 0);
                            content = jSONObject.optString("content", null);
                            if (!(title == null || content == null || id == 0)) {
                                h.EC().a((CharSequence) title, (CharSequence) content, id, model, type, 0);
                            }
                            r.ck().a(type, id, 0, 0);
                        } else if (type == PushMsgType.MCRES.Value()) {
                            id = jSONObject.optLong("key", 0);
                            long resid = jSONObject.optLong("assistkey", 0);
                            int mcrestype = jSONObject.optInt("mcrestype", 0);
                            content = jSONObject.optString("content", null);
                            if (!(title == null || content == null || id == 0)) {
                                h.EC().a(title, content, id, resid, model, type, mcrestype);
                            }
                            r.ck().a(type, id, resid, mcrestype);
                        }
                    }
                } catch (JSONException e2) {
                    e = e2;
                    JSONObject jSONObject2 = jSONObject;
                    e.printStackTrace();
                    HLog.error(TAG, "JSONException %s", message);
                }
            } catch (JSONException e3) {
                e = e3;
                e.printStackTrace();
                HLog.error(TAG, "JSONException %s", message);
            }
        }
    }

    public void d(int model, String message, int notifyid) {
        JSONException e;
        HLog.info(TAG, "message %s", message);
        if (!TextUtils.isEmpty(message)) {
            try {
                JSONObject jSONObject = new JSONObject(message);
                try {
                    int ver = jSONObject.optInt("ver", 0);
                    if (ver == 0 || ver <= 1) {
                        String title = jSONObject.optString("title", null);
                        int type = jSONObject.optInt("type", PushMsgType.INVALID.Value());
                        int notice = jSONObject.optInt("notice", 1);
                        int harry = jSONObject.optInt("harry", 0);
                        int sound = jSONObject.optInt("sound", 1);
                        int shake = jSONObject.optInt("shake", 1);
                        Long id;
                        if (type == PushMsgType.COUNTS.Value()) {
                            id = Long.valueOf(jSONObject.optLong("key", -1));
                            JSONObject countsJson = jSONObject.optJSONObject("counts");
                            if (id.longValue() != -1 && countsJson != null && j.ep().ey() && j.ep().getUserid() == id.longValue()) {
                                a(id.longValue(), new MsgCounts(countsJson), notice, harry, sound, shake);
                            }
                        } else if (type == PushMsgType.KICK.Value()) {
                            id = Long.valueOf(jSONObject.optLong("key", -1));
                            String content = jSONObject.optString("content", null);
                            if (id.longValue() != -1 && content != null && j.ep().ey() && j.ep().getUserid() == id.longValue()) {
                                i.i(id.longValue(), content);
                            }
                        } else if (type == PushMsgType.TOPIC.Value()) {
                            r.ck().a(type, jSONObject.optLong("key", 0), 0, 0);
                        } else if (type == PushMsgType.MCRES.Value()) {
                            r.ck().a(type, jSONObject.optLong("key", 0), jSONObject.optLong("assistkey", 0), jSONObject.optInt("mcrestype", 0));
                        }
                    }
                } catch (JSONException e2) {
                    e = e2;
                    JSONObject jSONObject2 = jSONObject;
                    e.printStackTrace();
                    HLog.error(TAG, "JSONException %s", message);
                }
            } catch (JSONException e3) {
                e = e3;
                e.printStackTrace();
                HLog.error(TAG, "JSONException %s", message);
            }
        }
    }

    public void e(int model, String message, int notifyid) {
        JSONException e;
        HLog.info(TAG, "message onMessageClicked %s", message);
        if (!TextUtils.isEmpty(message)) {
            try {
                JSONObject customJson = new JSONObject(message);
                try {
                    int ver = customJson.optInt("ver", 0);
                    if (ver == 0 || ver <= 1) {
                        String title = customJson.optString("title", null);
                        int type = customJson.optInt("type", PushMsgType.INVALID.Value());
                        Intent in;
                        if (type == PushMsgType.COUNTS.Value()) {
                            Long id = Long.valueOf(customJson.optLong("key", -1));
                            JSONObject countsJson = customJson.optJSONObject("counts");
                            if (id.longValue() != -1 && countsJson != null && j.ep().ey() && j.ep().getUserid() == id.longValue()) {
                                MsgCounts data = new MsgCounts(countsJson);
                                HLog.info(TAG, "message to open MessageHistoryActivity %s", message);
                                in = new Intent();
                                in.putExtra("currentIdx", 0);
                                in.putExtra("nextintent", "MessageHistoryActivity");
                                in.putExtra("msgCounts", data);
                                in.addFlags(268435456);
                                in.setComponent(new ComponentName(HTApplication.getAppContext(), "hlx.home.main.HomeActivity"));
                                HTApplication.getAppContext().startActivity(in);
                            }
                        } else if (type == PushMsgType.TOPIC.Value()) {
                            id = customJson.optLong("key", 0);
                            content = customJson.optString("content", null);
                            if (title != null && content != null && id != 0) {
                                in = new Intent();
                                in.putExtra("nextintent", "TopicDetailActivity");
                                in.putExtra("id", id);
                                in.putExtra("type", type);
                                in.putExtra(Constants.brO, model);
                                in.addFlags(268435456);
                                in.setComponent(new ComponentName(HTApplication.getAppContext(), "hlx.home.main.HomeActivity"));
                                HTApplication.getAppContext().startActivity(in);
                                r.ck().i(model, type);
                            }
                        } else if (type == PushMsgType.MCRES.Value()) {
                            id = customJson.optLong("key", 0);
                            long resid = customJson.optLong("assistkey", 0);
                            int mcrestype = customJson.optInt("mcrestype", 0);
                            content = customJson.optString("content", null);
                            if (title != null && content != null && id != 0) {
                                in = new Intent();
                                in.putExtra("nextintent", "ResTopicDetailActivity");
                                in.putExtra("id", id);
                                in.putExtra("resid", resid);
                                in.putExtra("type", type);
                                in.putExtra("mcrestype", mcrestype);
                                in.putExtra(Constants.brO, model);
                                in.addFlags(268435456);
                                in.setComponent(new ComponentName(HTApplication.getAppContext(), "hlx.home.main.HomeActivity"));
                                HTApplication.getAppContext().startActivity(in);
                                r.ck().i(model, type);
                            }
                        }
                    }
                } catch (JSONException e2) {
                    e = e2;
                    JSONObject jSONObject = customJson;
                    e.printStackTrace();
                    HLog.error(TAG, "JSONException %s", message);
                }
            } catch (JSONException e3) {
                e = e3;
                e.printStackTrace();
                HLog.error(TAG, "JSONException %s", message);
            }
        }
    }

    private void a(long id, MsgCounts msgCounts, int notice, int harry, int sound, int shake) {
        if (msgCounts != null && 0 != msgCounts.getAll() && j.ep().ey() && id == j.ep().getUserid()) {
            HTApplication.i(msgCounts.getFollow());
            if (msgCounts.getFollow() > 0) {
                i.h(msgCounts.getFollow(), 0);
            }
            MsgCounts oldCounts = HTApplication.bM();
            if (oldCounts != null && oldCounts.getAll() == msgCounts.getAll() && oldCounts.getReply() == msgCounts.getReply() && oldCounts.getSys() == msgCounts.getSys()) {
                HLog.info(TAG, "oldCounts all(%d) reply(%d) sys(%d)   nowCounts all(%d) reply(%d) sys(%d)", Long.valueOf(oldCounts.getAll()), Long.valueOf(oldCounts.getReply()), Long.valueOf(oldCounts.getSys()), Long.valueOf(msgCounts.getAll()), Long.valueOf(msgCounts.getReply()), Long.valueOf(msgCounts.getSys()));
                return;
            }
            HTApplication.a(msgCounts);
            if (msgCounts.getAll() != 0) {
                i.EH();
            }
        }
    }

    private void a(long id, String title, String content, MsgCounts msgCounts, int notice, int harry, int sound, int shake) {
        if (msgCounts != null && 0 != msgCounts.getAll() && j.ep().ey() && id == j.ep().getUserid()) {
            if (notice == 0) {
                sound = 0;
                shake = 0;
            }
            if (harry == 1) {
                int hour = UtilsSystem.getNowHour();
                if (hour >= 23 || hour < 8) {
                    sound = 0;
                    shake = 0;
                    harry = 1;
                } else {
                    harry = 0;
                }
            }
            boolean isNotice = notice == 1;
            boolean isHarry = harry == 1;
            boolean isSoundOpen = sound == 1;
            boolean isVibrationOpen = shake == 1;
            HTApplication.i(msgCounts.getFollow());
            if (msgCounts.getFollow() > 0) {
                i.h(msgCounts.getFollow(), 0);
            }
            MsgCounts oldCounts = HTApplication.bM();
            if (oldCounts != null && oldCounts.getAll() == msgCounts.getAll() && oldCounts.getReply() == msgCounts.getReply() && oldCounts.getSys() == msgCounts.getSys()) {
                HLog.info(TAG, "oldCounts all(%d) reply(%d) sys(%d)   nowCounts all(%d) reply(%d) sys(%d)", Long.valueOf(oldCounts.getAll()), Long.valueOf(oldCounts.getReply()), Long.valueOf(oldCounts.getSys()), Long.valueOf(msgCounts.getAll()), Long.valueOf(msgCounts.getReply()), Long.valueOf(msgCounts.getSys()));
                return;
            }
            HTApplication.a(msgCounts);
            h.EC().ED();
            HTApplication.bC();
            if (msgCounts.getAll() != 0) {
                CharSequence szTitle;
                CharSequence szContent;
                if (UtilsFunction.empty((CharSequence) title) || UtilsFunction.empty((CharSequence) content)) {
                    szTitle = "消息提醒";
                    szContent = String.format(Locale.getDefault(), "你收到%d条新消息", new Object[]{Long.valueOf(msgCounts.getAll())});
                } else {
                    Object szTitle2 = title;
                    Object szContent2 = content;
                }
                if (!(HTApplication.bU() || !isNotice || isHarry)) {
                    h.EC().a(szTitle, szContent, msgCounts, isSoundOpen, isVibrationOpen);
                }
                i.EH();
            }
        }
    }
}
