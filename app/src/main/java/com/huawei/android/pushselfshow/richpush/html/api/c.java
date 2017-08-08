package com.huawei.android.pushselfshow.richpush.html.api;

import android.app.Activity;
import android.content.Intent;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushselfshow.richpush.html.a.a;
import com.huawei.android.pushselfshow.richpush.html.a.d;
import com.huawei.android.pushselfshow.richpush.html.a.f;
import com.huawei.android.pushselfshow.richpush.html.a.h;
import com.huawei.android.pushselfshow.richpush.html.a.i;
import com.huawei.android.pushselfshow.richpush.html.a.j;
import com.huawei.android.pushselfshow.richpush.html.a.k;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import java.util.HashMap;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

public class c {
    public HashMap a = new HashMap();

    public c(Activity activity, boolean z) {
        try {
            this.a.clear();
            this.a.put("Audio", new f(activity));
            this.a.put("Video", new k(activity));
            this.a.put("App", new d(activity));
            this.a.put("Geo", new j(activity));
            this.a.put("Accelerometer", new a(activity));
            this.a.put("Device", new i(activity, z));
        } catch (Throwable e) {
            e.c("PluginManager", e.toString(), e);
        }
    }

    public String a(String str, String str2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject(str2);
            if (jSONObject2.has("method")) {
                String string = jSONObject2.getString("method");
                e.a("PluginManager", "method is " + string);
                jSONObject2 = jSONObject2.has("options") ? jSONObject2.getJSONObject("options") : jSONObject;
                if (!this.a.containsKey(str)) {
                    return d.a(d.a.SERVICE_NOT_FOUND_EXCEPTION).toString();
                }
                e.a("PluginManager", "plugins.containsKey(" + str + ") ");
                return ((h) this.a.get(str)).a(string, jSONObject2);
            }
            e.a("PluginManager", "method is null");
            return d.a(d.a.METHOD_NOT_FOUND_EXCEPTION).toString();
        } catch (JSONException e) {
            return d.a(d.a.JSON_EXCEPTION).toString();
        }
    }

    public void a() {
        for (Entry entry : this.a.entrySet()) {
            String str = (String) entry.getKey();
            h hVar = (h) entry.getValue();
            e.e("PluginManager", "call plugin: " + str + " reset");
            hVar.d();
        }
    }

    public void a(int i, int i2, Intent intent) {
        for (Entry entry : this.a.entrySet()) {
            String str = (String) entry.getKey();
            h hVar = (h) entry.getValue();
            e.e("PluginManager", "call plugin: " + str + " reset");
            hVar.a(i, i2, intent);
        }
    }

    public void a(String str, String str2, NativeToJsMessageQueue nativeToJsMessageQueue) {
        String str3;
        if (nativeToJsMessageQueue == null) {
            e.a("PluginManager", "plugin.exec,jsMessageQueue is null");
            return;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            String str4;
            JSONObject jSONObject2 = new JSONObject(str2);
            if (jSONObject2.has("callbackId")) {
                String string = jSONObject2.getString("callbackId");
                try {
                    e.a("PluginManager", "callbackId is " + string);
                    str4 = string;
                } catch (JSONException e) {
                    str3 = string;
                    nativeToJsMessageQueue.a(str3, d.a.JSON_EXCEPTION, DownloadRecord.COLUMN_ERROR, null);
                }
            }
            str4 = null;
            try {
                if (jSONObject2.has("method")) {
                    String string2 = jSONObject2.getString("method");
                    e.a("PluginManager", "method is " + string2);
                    JSONObject jSONObject3 = jSONObject2.has("options") ? jSONObject2.getJSONObject("options") : jSONObject;
                    if (this.a.containsKey(str)) {
                        e.a("PluginManager", "plugins.containsKey(" + str + ") ");
                        ((h) this.a.get(str)).a(nativeToJsMessageQueue, string2, str4, jSONObject3);
                        return;
                    }
                    nativeToJsMessageQueue.a(str4, d.a.SERVICE_NOT_FOUND_EXCEPTION, DownloadRecord.COLUMN_ERROR, null);
                    return;
                }
                e.a("PluginManager", "method is null");
                nativeToJsMessageQueue.a(str4, d.a.METHOD_NOT_FOUND_EXCEPTION, DownloadRecord.COLUMN_ERROR, null);
            } catch (JSONException e2) {
                str3 = str4;
                nativeToJsMessageQueue.a(str3, d.a.JSON_EXCEPTION, DownloadRecord.COLUMN_ERROR, null);
            }
        } catch (JSONException e3) {
            str3 = null;
            nativeToJsMessageQueue.a(str3, d.a.JSON_EXCEPTION, DownloadRecord.COLUMN_ERROR, null);
        }
    }

    public void b() {
        for (Entry entry : this.a.entrySet()) {
            String str = (String) entry.getKey();
            h hVar = (h) entry.getValue();
            e.e("PluginManager", "call plugin: " + str + " reset");
            hVar.b();
        }
    }

    public void c() {
        for (Entry entry : this.a.entrySet()) {
            String str = (String) entry.getKey();
            h hVar = (h) entry.getValue();
            e.e("PluginManager", "call plugin: " + str + " reset");
            hVar.c();
        }
    }
}
