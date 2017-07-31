package com.tencent.stat.common;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import com.tencent.stat.StatConfig;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import org.json.JSONException;
import org.json.JSONObject;

public class Env {
    private static JSONObject attr_json = null;
    static BasicEnv basicEnv;
    String conn = null;
    Integer tel_network = null;

    static class BasicEnv {
        String appVersion;
        String channel;
        DisplayMetrics display;
        int jb;
        String language;
        String manufacture;
        String model;
        String operator;
        int osVersion;
        String sd;
        String sdkVersion;
        String timezone;

        private BasicEnv(Context context) {
            this.sdkVersion = StatConstants.VERSION;
            this.osVersion = VERSION.SDK_INT;
            this.model = Build.MODEL;
            this.manufacture = Build.MANUFACTURER;
            this.language = Locale.getDefault().getLanguage();
            this.jb = 0;
            this.display = StatCommonHelper.getDisplayMetrics(context);
            this.appVersion = StatCommonHelper.getCurAppVersion(context);
            this.channel = StatConfig.getInstallChannel(context);
            this.operator = StatCommonHelper.getSimOperator(context);
            this.timezone = TimeZone.getDefault().getID();
            this.jb = StatCommonHelper.hasRootAccess(context);
            this.sd = StatCommonHelper.getExternalStorageInfo(context);
        }

        void encode(JSONObject jSONObject) throws JSONException {
            jSONObject.put("sr", this.display.widthPixels + WebSocketServerHandshaker.SUB_PROTOCOL_WILDCARD + this.display.heightPixels);
            StatCommonHelper.jsonPut(jSONObject, "av", this.appVersion);
            StatCommonHelper.jsonPut(jSONObject, "ch", this.channel);
            StatCommonHelper.jsonPut(jSONObject, "mf", this.manufacture);
            StatCommonHelper.jsonPut(jSONObject, "sv", this.sdkVersion);
            StatCommonHelper.jsonPut(jSONObject, "ov", Integer.toString(this.osVersion));
            jSONObject.put("os", 1);
            StatCommonHelper.jsonPut(jSONObject, "op", this.operator);
            StatCommonHelper.jsonPut(jSONObject, "lg", this.language);
            StatCommonHelper.jsonPut(jSONObject, "md", this.model);
            StatCommonHelper.jsonPut(jSONObject, "tz", this.timezone);
            if (this.jb != 0) {
                jSONObject.put("jb", this.jb);
            }
            StatCommonHelper.jsonPut(jSONObject, "sd", this.sd);
        }
    }

    public Env(Context context) {
        getBasicEnv(context);
        this.tel_network = StatCommonHelper.getTelephonyNetworkType(context.getApplicationContext());
        this.conn = StatCommonHelper.getLinkedWay(context);
    }

    public static void appendEnvAttr(Context context, Map<String, String> map) throws JSONException {
        if (map != null) {
            if (attr_json == null) {
                attr_json = new JSONObject();
            }
            for (Entry entry : map.entrySet()) {
                attr_json.put((String) entry.getKey(), entry.getValue());
            }
        }
    }

    static BasicEnv getBasicEnv(Context context) {
        if (basicEnv == null) {
            basicEnv = new BasicEnv(context.getApplicationContext());
        }
        return basicEnv;
    }

    public void encode(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        if (basicEnv != null) {
            basicEnv.encode(jSONObject2);
        }
        StatCommonHelper.jsonPut(jSONObject2, "cn", this.conn);
        if (this.tel_network != null) {
            jSONObject2.put("tn", this.tel_network);
        }
        jSONObject.put("ev", jSONObject2);
        if (attr_json != null && attr_json.length() > 0) {
            jSONObject.put("eva", attr_json);
        }
    }
}
