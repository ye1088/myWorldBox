package com.huawei.android.pushselfshow.richpush.html.api;

import android.app.Activity;
import android.webkit.WebView;
import com.huawei.android.pushagent.c.a.e;
import java.util.LinkedList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NativeToJsMessageQueue {
    public WebView a;
    private final LinkedList b = new LinkedList();
    private final a c;
    private final Activity d;
    private String e;

    private interface a {
        void onNativeToJsMessageAvailable();
    }

    private class OnlineEventsBridgeMode implements a {
        boolean a = true;
        final Runnable b = new a(this);
        final /* synthetic */ NativeToJsMessageQueue c;

        OnlineEventsBridgeMode(NativeToJsMessageQueue nativeToJsMessageQueue) {
            this.c = nativeToJsMessageQueue;
            e.a("PushSelfShowLog", "OnlineEventsBridgeMode() the webview is " + nativeToJsMessageQueue.a);
            nativeToJsMessageQueue.a.setNetworkAvailable(true);
        }

        public void onNativeToJsMessageAvailable() {
            this.c.d.runOnUiThread(this.b);
        }
    }

    private static class b {
        final String a;
        final d b;

        b(d dVar, String str) {
            this.a = str;
            this.b = dVar;
        }

        JSONObject a() {
            if (this.b == null) {
                return null;
            }
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("type", this.b.a());
                if (this.b.b() != null) {
                    jSONObject.put("message", this.b.b());
                }
                jSONObject.put("callbackId", this.a);
                return jSONObject;
            } catch (JSONException e) {
                return null;
            }
        }
    }

    public NativeToJsMessageQueue(Activity activity, WebView webView, String str) {
        e.a("PushSelfShowLog", "activity is " + activity);
        e.a("PushSelfShowLog", "webView is " + webView);
        e.a("PushSelfShowLog", "localPath is " + str);
        this.d = activity;
        this.a = webView;
        this.e = str;
        this.c = new OnlineEventsBridgeMode(this);
        b();
    }

    private boolean d() {
        boolean isEmpty;
        synchronized (this) {
            isEmpty = this.b.isEmpty();
        }
        return isEmpty;
    }

    public String a() {
        return this.e;
    }

    public void a(String str, com.huawei.android.pushselfshow.richpush.html.api.d.a aVar, String str2, JSONObject jSONObject) {
        try {
            e.a("PushSelfShowLog", "addPluginResult status is " + d.c()[aVar.ordinal()]);
            if (str == null) {
                e.e("JsMessageQueue", "Got plugin result with no callbackId");
                return;
            }
            b bVar = new b(jSONObject == null ? new d(str2, aVar) : new d(str2, aVar, jSONObject), str);
            synchronized (this) {
                this.b.add(bVar);
                if (this.c != null) {
                    this.c.onNativeToJsMessageAvailable();
                }
            }
        } catch (Throwable e) {
            e.d("PushSelfShowLog", "addPluginResult failed", e);
        }
    }

    public void b() {
        synchronized (this) {
            this.b.clear();
        }
    }

    public String c() {
        String str;
        synchronized (this) {
            if (this.b.isEmpty()) {
                str = null;
            } else {
                JSONArray jSONArray = new JSONArray();
                int size = this.b.size();
                for (int i = 0; i < size; i++) {
                    JSONObject a = ((b) this.b.removeFirst()).a();
                    if (a != null) {
                        jSONArray.put(a);
                    }
                }
                str = jSONArray.toString();
            }
        }
        return str;
    }
}
