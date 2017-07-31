package com.umeng.analytics.onlineconfig;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.g;
import java.util.Iterator;
import org.json.JSONObject;
import u.aly.bi;
import u.aly.bj;
import u.aly.bo;
import u.aly.bp;
import u.aly.bv;

/* compiled from: OnlineConfigAgent */
public class a {
    public static final String a = "type";
    public static final String b = "package";
    public static final String c = "channel";
    public static final String d = "idmd5";
    public static final String e = "version_code";
    public static final String f = "appkey";
    public static final String g = "sdk_version";
    private final String h = "last_config_time";
    private final String i = "report_policy";
    private final String j = "online_config";
    private UmengOnlineConfigureListener k = null;
    private c l = null;
    private long m = 0;

    /* compiled from: OnlineConfigAgent */
    public class a extends bp {
        final /* synthetic */ a a;
        private JSONObject e;

        public a(a aVar, JSONObject jSONObject) {
            this.a = aVar;
            super(null);
            this.e = jSONObject;
        }

        public JSONObject a() {
            return this.e;
        }

        public String b() {
            return this.d;
        }
    }

    /* compiled from: OnlineConfigAgent */
    public class b extends bo implements Runnable {
        Context a;
        final /* synthetic */ a b;

        public b(a aVar, Context context) {
            this.b = aVar;
            this.a = context.getApplicationContext();
        }

        public void run() {
            if (!AnalyticsConfig.UPDATE_IN_MAIN_PROCESS || com.umeng.analytics.b.a(this.a)) {
                try {
                    b();
                } catch (Exception e) {
                    this.b.a(null);
                    bj.c(com.umeng.analytics.a.e, "reques update error", e);
                }
            }
        }

        public boolean a() {
            return false;
        }

        private void b() {
            bp aVar = new a(this.b, this.b.b(this.a));
            String[] strArr = com.umeng.analytics.a.g;
            b bVar = null;
            for (String a : strArr) {
                aVar.a(a);
                bVar = (b) a(aVar, b.class);
                if (bVar != null) {
                    break;
                }
            }
            if (bVar == null) {
                this.b.a(null);
            } else if (bVar.b) {
                if (this.b.l != null) {
                    this.b.l.a(bVar.c, (long) bVar.d);
                }
                this.b.a(this.a, bVar);
                this.b.b(this.a, bVar);
                this.b.a(bVar.a);
            } else {
                this.b.a(null);
            }
        }
    }

    public void a(Context context) {
        if (context == null) {
            try {
                bj.b(com.umeng.analytics.a.e, "unexpected null context in updateOnlineConfig");
            } catch (Exception e) {
                bj.b(com.umeng.analytics.a.e, "exception in updateOnlineConfig");
            }
        } else if (bj.a && bi.w(context)) {
            new Thread(new b(this, context.getApplicationContext())).start();
        } else {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - this.m > 3600000) {
                this.m = currentTimeMillis;
                new Thread(new b(this, context.getApplicationContext())).start();
            }
        }
    }

    public void a(UmengOnlineConfigureListener umengOnlineConfigureListener) {
        this.k = umengOnlineConfigureListener;
    }

    public void a() {
        this.k = null;
    }

    public void a(c cVar) {
        this.l = cVar;
    }

    public void b() {
        this.l = null;
    }

    private void a(JSONObject jSONObject) {
        if (this.k != null) {
            this.k.onDataReceived(jSONObject);
        }
    }

    private JSONObject b(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", "online_config");
            jSONObject.put(f, AnalyticsConfig.getAppkey(context));
            jSONObject.put(e, bi.c(context));
            jSONObject.put(b, bi.u(context));
            jSONObject.put(g, com.umeng.analytics.a.c);
            jSONObject.put(d, bv.b(bi.f(context)));
            jSONObject.put("channel", AnalyticsConfig.getChannel(context));
            jSONObject.put("report_policy", g.a(context).a()[0]);
            jSONObject.put("last_config_time", c(context));
            return jSONObject;
        } catch (Exception e) {
            bj.b(com.umeng.analytics.a.e, "exception in onlineConfigInternal");
            return null;
        }
    }

    private String c(Context context) {
        return g.a(context).g().getString(com.umeng.analytics.a.j, "");
    }

    private void a(Context context, b bVar) {
        Editor edit = g.a(context).g().edit();
        if (!TextUtils.isEmpty(bVar.e)) {
            edit.putString(com.umeng.analytics.a.j, bVar.e);
            edit.commit();
        }
        if (bVar.c != -1) {
            g.a(context).a(bVar.c, bVar.d);
        }
    }

    private void b(Context context, b bVar) {
        if (bVar.a != null && bVar.a.length() != 0) {
            Editor edit = g.a(context).g().edit();
            try {
                JSONObject jSONObject = bVar.a;
                Iterator keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String str = (String) keys.next();
                    edit.putString(str, jSONObject.getString(str));
                }
                edit.commit();
                bj.a(com.umeng.analytics.a.e, "get online setting params: " + jSONObject);
            } catch (Exception e) {
                bj.c(com.umeng.analytics.a.e, "save online config params", e);
            }
        }
    }
}
