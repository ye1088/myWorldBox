package com.hianalytics.android.v1;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.hianalytics.android.a.a.c;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import com.xiaomi.mipush.sdk.MiPushClient;
import io.netty.util.internal.StringUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

public final class d implements Runnable {
    private Context a;
    private int b;
    private long c;

    public d(Context context, int i, long j) {
        this.a = context;
        this.b = i;
        this.c = j;
    }

    private static void a(Context context, SharedPreferences sharedPreferences, long j) {
        Editor edit = sharedPreferences.edit();
        String valueOf = String.valueOf(new StringBuilder(String.valueOf(j)).append(a.b(((TelephonyManager) context.getSystemService(com.MCWorld.data.profile.a.qe)).getDeviceId())).toString());
        edit.remove("session_id");
        edit.remove("refer_id");
        edit.putString("session_id", valueOf);
        edit.putString("refer_id", "");
        edit.putLong("end_millis", j);
        edit.commit();
    }

    private void a(SharedPreferences sharedPreferences) {
        Editor edit = sharedPreferences.edit();
        edit.putLong("last_millis", this.c);
        edit.commit();
    }

    private void c(SharedPreferences sharedPreferences) {
        JSONObject jSONObject = new JSONObject();
        Context context = this.a;
        StringBuffer stringBuffer = new StringBuffer("");
        SharedPreferences a = c.a(context, "sessioncontext");
        String string = a.getString("session_id", "");
        if ("".equals(string)) {
            string = a.b(((TelephonyManager) context.getSystemService(com.MCWorld.data.profile.a.qe)).getDeviceId());
            long currentTimeMillis = System.currentTimeMillis();
            string = String.valueOf(new StringBuilder(String.valueOf(currentTimeMillis)).append(string).toString());
            Editor edit = a.edit();
            edit.putString("session_id", string);
            edit.putLong("end_millis", currentTimeMillis);
            edit.commit();
        }
        String str = string;
        String string2 = a.getString("refer_id", "");
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(com.MCWorld.data.profile.a.qe);
        if (telephonyManager == null) {
            a.h();
            Object obj = null;
        } else {
            stringBuffer.append(a.F(context)[0]).append(MiPushClient.ACCEPT_TIME_SEPARATOR).append(telephonyManager.getNetworkOperatorName().replace(StringUtil.COMMA, '&')).append(MiPushClient.ACCEPT_TIME_SEPARATOR).append(str).append(MiPushClient.ACCEPT_TIME_SEPARATOR).append(string2);
            str = stringBuffer.toString();
        }
        if (obj != null) {
            try {
                String[] split;
                JSONArray jSONArray;
                boolean z;
                if (sharedPreferences.getString("activities", "").trim().length() > 0) {
                    split = sharedPreferences.getString("activities", "").split(";");
                    jSONArray = new JSONArray();
                    for (Object put : split) {
                        jSONArray.put(put);
                    }
                    jSONObject.put("b", jSONArray);
                    z = false;
                } else {
                    z = true;
                }
                if (sharedPreferences.getString("events", "").trim().length() > 0) {
                    split = sharedPreferences.getString("events", "").split(";");
                    jSONArray = new JSONArray();
                    for (Object put2 : split) {
                        jSONArray.put(put2);
                    }
                    jSONObject.put("e", jSONArray);
                    z = false;
                }
                jSONObject.put("h", obj);
                jSONObject.put("type", "termination");
                Handler bo = a.bo();
                if (bo != null) {
                    bo.post(new c(this.a, jSONObject, z));
                }
                a.h();
            } catch (Throwable e) {
                Log.e("HiAnalytics", "onTerminate: JSONException.", e);
                e.printStackTrace();
            }
        }
        Editor edit2 = sharedPreferences.edit();
        edit2.putString("activities", "");
        edit2.remove("events");
        edit2.commit();
    }

    private boolean d(SharedPreferences sharedPreferences) {
        return this.c - sharedPreferences.getLong("last_millis", -1) > a.bl().longValue() * 1000;
    }

    public final void run() {
        try {
            Context context = this.a;
            long j = this.c;
            SharedPreferences a = c.a(context, "sessioncontext");
            if ("".equals(a.getString("session_id", ""))) {
                a(context, a, j);
            } else if (j - a.getLong("end_millis", 0) > a.bn().longValue() * 1000) {
                a(context, a, j);
            } else {
                Editor edit = a.edit();
                edit.putLong("end_millis", j);
                edit.commit();
            }
            if (this.b == 0) {
                Context context2 = this.a;
                if (this.a != context2) {
                    a.h();
                    return;
                }
                this.a = context2;
                SharedPreferences a2 = c.a(context2, DownloadRecord.COLUMN_STATE);
                if (a2 != null) {
                    long j2 = a2.getLong("last_millis", -1);
                    if (j2 == -1) {
                        a.h();
                    } else {
                        long j3 = this.c - j2;
                        long j4 = a2.getLong("duration", 0);
                        Editor edit2 = a2.edit();
                        Object string = a2.getString("activities", "");
                        String name = context2.getClass().getName();
                        if (!"".equals(string)) {
                            string = new StringBuilder(String.valueOf(string)).append(";").toString();
                        }
                        String stringBuilder = new StringBuilder(String.valueOf(string)).append(name).append(MiPushClient.ACCEPT_TIME_SEPARATOR).append(new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.US).format(new Date(j2))).append(MiPushClient.ACCEPT_TIME_SEPARATOR).append(j3 / 1000).toString();
                        edit2.remove("activities");
                        edit2.putString("activities", stringBuilder);
                        edit2.putLong("duration", j4 + j3);
                        edit2.commit();
                    }
                    if (d(a2)) {
                        c(a2);
                        a(a2);
                    } else if (a.d(context2)) {
                        c(a2);
                        a(a2);
                    }
                }
            } else if (this.b == 1) {
                context = this.a;
                this.a = context;
                r0 = c.a(context, DownloadRecord.COLUMN_STATE);
                if (r0 != null && d(r0)) {
                    c(r0);
                    a(r0);
                }
            } else if (this.b == 2) {
                r0 = c.a(this.a, DownloadRecord.COLUMN_STATE);
                if (r0 != null) {
                    c(r0);
                }
            }
        } catch (Exception e) {
            "SessionThread.run() throw exception:" + e.getMessage();
            a.h();
            e.printStackTrace();
        }
    }
}
