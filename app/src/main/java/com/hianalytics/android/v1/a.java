package com.hianalytics.android.v1;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.hianalytics.android.a.a.c;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class a implements Runnable {
    private Context a;
    private String b;
    private String c;
    private long d;

    public a(Context context, String str, String str2, long j) {
        this.a = context;
        this.b = str.replace(MiPushClient.ACCEPT_TIME_SEPARATOR, "^");
        this.c = str2.replace(MiPushClient.ACCEPT_TIME_SEPARATOR, "^");
        this.d = j;
    }

    public final void run() {
        try {
            SharedPreferences a = c.a(this.a, DownloadRecord.COLUMN_STATE);
            if (a == null) {
                com.hianalytics.android.a.a.a.h();
                return;
            }
            Object string = a.getString("events", "");
            if (!"".equals(string)) {
                string = new StringBuilder(String.valueOf(string)).append(";").toString();
            }
            String stringBuilder = new StringBuilder(String.valueOf(string)).append(this.b).append(MiPushClient.ACCEPT_TIME_SEPARATOR).append(this.c).append(MiPushClient.ACCEPT_TIME_SEPARATOR).append(new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.US).format(new Date(this.d))).toString();
            int length = stringBuilder.split(";").length;
            if (length <= com.hianalytics.android.a.a.a.d()) {
                Editor edit = a.edit();
                edit.remove("events");
                edit.putString("events", stringBuilder);
                edit.commit();
                " current event record is：" + length;
                com.hianalytics.android.a.a.a.h();
            }
            if (!com.hianalytics.android.a.a.a.d(this.a)) {
                return;
            }
            if (com.hianalytics.android.a.a.a.e()) {
                com.hianalytics.android.a.a.a.h();
                b.G(this.a);
                return;
            }
            a.edit().remove("events").commit();
        } catch (Exception e) {
            "EventThread.run() throw exception:" + e.getMessage();
            com.hianalytics.android.a.a.a.h();
            e.printStackTrace();
        }
    }
}
