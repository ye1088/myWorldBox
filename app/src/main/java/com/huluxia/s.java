package com.huluxia;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import com.huluxia.controller.c;
import com.huluxia.db.a;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.GameInfo;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: StatisticsDownload */
public class s {
    private static final String TAG = "StatisticsDownload";
    private static final String lD = "com.vectorunit.green";
    private static final long lE = 1001;
    private static s lF;
    private SharedPreferences lG_sp;
    private Map<String, GameInfo> lH;
    private BroadcastReceiver lI = new BroadcastReceiver(this) {
        final /* synthetic */ s lK;

        {
            this.lK = this$0;
        }

        public void onReceive(Context context, Intent intent) {
            try {
                if ("com.vectorunit.green".equals(intent.getDataString().substring(8))) {
                    this.lK.dy();
                }
            } catch (Exception e) {
            }
        }
    };
    private CallbackHandler lJ = new 3(this);
    private CallbackHandler mCallback = new 2(this);

    private s(Context context) {
        this.lG_sp = context.getSharedPreferences("downloadList", 0);
        this.lH = Collections.synchronizedMap(new HashMap());
        for (Entry<String, ?> entry : this.lG_sp.getAll().entrySet()) {
            this.lH.put(entry.getKey(), (GameInfo) Json.parseJsonObject((String) entry.getValue(), GameInfo.class));
        }
        EventNotifyCenter.add(c.class, this.mCallback);
        EventNotifyCenter.add(a.class, this.lJ);
        IntentFilter filter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        filter.addDataScheme(com.umeng.analytics.onlineconfig.a.b);
        context.getApplicationContext().registerReceiver(this.lI, filter);
    }

    public static synchronized s ae(Context context) {
        s sVar;
        synchronized (s.class) {
            if (lF == null) {
                lF = new s(context.getApplicationContext());
            }
            sVar = lF;
        }
        return sVar;
    }

    public void a(String url, GameInfo gameInfo) {
        if (!this.lH.containsKey(url)) {
            this.lH.put(url, gameInfo);
            this.lG_sp.edit().putString(url, Json.toJson(gameInfo)).apply();
            r.ck().b(gameInfo.appid, "game", gameInfo.tongjiPage);
            HLog.verbose(TAG, "downloadBegin:" + gameInfo.toString() + " page:" + gameInfo.tongjiPage, new Object[0]);
        }
    }

    public void S(String url) {
        GameInfo gameInfo = (GameInfo) this.lH.get(url);
        if (gameInfo != null) {
            r.ck().c(gameInfo.appid, "game", gameInfo.tongjiPage);
            T(url);
            HLog.verbose(TAG, "downComplete:" + gameInfo.toString() + " page:" + gameInfo.tongjiPage, new Object[0]);
        }
    }

    public void T(String url) {
        this.lH.remove(url);
        if (this.lG_sp.contains(url)) {
            this.lG_sp.edit().remove(url).apply();
        }
        HLog.verbose(TAG, "removeDownload:" + url, new Object[0]);
    }

    public void dy() {
        r.ck().d(1001, "game");
        HLog.error(TAG, "installComplete:1001", new Object[0]);
    }
}
