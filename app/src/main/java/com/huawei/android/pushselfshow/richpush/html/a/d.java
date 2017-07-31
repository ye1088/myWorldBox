package com.huawei.android.pushselfshow.richpush.html.a;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushselfshow.richpush.html.api.NativeToJsMessageQueue;
import com.huawei.android.pushselfshow.richpush.html.api.d.a;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord;
import org.json.JSONException;
import org.json.JSONObject;

public class d implements h {
    public boolean a = false;
    public int b;
    public int c;
    private NativeToJsMessageQueue d;
    private String e;
    private Activity f;

    public d(Activity activity) {
        e.e("PushSelfShowLog", "init App");
        this.f = activity;
    }

    private void a(String str, String str2, boolean z) {
        try {
            e.a("PushSelfShowLog", "enter launchApp , appPackageName =" + str + ",and msg.intentUri is " + str2 + " boolean appmarket is " + z);
            if (str == null || str.trim().length() == 0) {
                this.d.a(this.e, a.JSON_EXCEPTION, DownloadRecord.COLUMN_ERROR, null);
                return;
            }
            Intent a = com.huawei.android.pushselfshow.utils.a.a(this.f, str);
            if (a != null) {
                if (str2 != null) {
                    try {
                        Intent parseUri = Intent.parseUri(str2, 0);
                        e.e("PushSelfShowLog", "Intent.parseUri(intentUri, 0)，" + a.toURI());
                        if (!com.huawei.android.pushselfshow.utils.a.a(this.f, str, parseUri).booleanValue()) {
                            parseUri = a;
                        }
                        a = parseUri;
                    } catch (Throwable e) {
                        e.a("PushSelfShowLog", "intentUri error ", e);
                    }
                }
                if (this.a) {
                    e.e("PushSelfShowLog", " APP_OPEN startActivityForResult " + a.toURI());
                    this.f.startActivityForResult(a, this.b);
                } else {
                    e.e("PushSelfShowLog", " APP_OPEN start " + a.toURI());
                    this.f.startActivity(a);
                }
                this.d.a(this.e, a.OK, "success", null);
            } else if (z) {
                a(str);
            } else {
                e.e("PushSelfShowLog", "APP_NOT_EXIST and appmaeket is false");
                this.d.a(this.e, a.APP_NOT_EXIST, DownloadRecord.COLUMN_ERROR, null);
            }
        } catch (Throwable e2) {
            e.c("PushSelfShowLog", e2.toString(), e2);
        }
    }

    private void a(JSONObject jSONObject) {
        String string;
        boolean z;
        Throwable th;
        String str = null;
        if (jSONObject == null || !jSONObject.has("package-name")) {
            this.d.a(this.e, a.JSON_EXCEPTION, DownloadRecord.COLUMN_ERROR, null);
            return;
        }
        try {
            string = jSONObject.getString("package-name");
            try {
                if (jSONObject.has("intent-uri")) {
                    str = jSONObject.getString("intent-uri");
                }
                z = jSONObject.has("appmarket") ? jSONObject.getBoolean("appmarket") : false;
            } catch (Throwable e) {
                th = e;
                z = false;
                e.d("PushSelfShowLog", "openApp param failed ", th);
                e.b("PushSelfShowLog", "packageName is %s , appmarket is %s ,bResult is %s ", new Object[]{string, Boolean.valueOf(z), Boolean.valueOf(this.a)});
                a(string, str, z);
            }
            try {
                if (jSONObject.has("requestCode") && jSONObject.has("resultCode")) {
                    this.b = jSONObject.getInt("requestCode");
                    this.c = jSONObject.getInt("resultCode");
                    this.a = true;
                }
            } catch (Exception e2) {
                th = e2;
                e.d("PushSelfShowLog", "openApp param failed ", th);
                e.b("PushSelfShowLog", "packageName is %s , appmarket is %s ,bResult is %s ", new Object[]{string, Boolean.valueOf(z), Boolean.valueOf(this.a)});
                a(string, str, z);
            }
        } catch (Throwable e3) {
            th = e3;
            string = null;
            z = false;
            e.d("PushSelfShowLog", "openApp param failed ", th);
            e.b("PushSelfShowLog", "packageName is %s , appmarket is %s ,bResult is %s ", new Object[]{string, Boolean.valueOf(z), Boolean.valueOf(this.a)});
            a(string, str, z);
        }
        e.b("PushSelfShowLog", "packageName is %s , appmarket is %s ,bResult is %s ", new Object[]{string, Boolean.valueOf(z), Boolean.valueOf(this.a)});
        a(string, str, z);
    }

    private void b(JSONObject jSONObject) {
        if (jSONObject == null || !jSONObject.has("package-name")) {
            this.d.a(this.e, a.JSON_EXCEPTION, DownloadRecord.COLUMN_ERROR, null);
            return;
        }
        try {
            String string = jSONObject.getString("package-name");
            JSONObject jSONObject2 = new JSONObject();
            PackageManager packageManager = this.f.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(string, 0);
            String obj = packageInfo.applicationInfo.loadLabel(packageManager).toString();
            String str = packageInfo.versionName;
            int i = packageInfo.versionCode;
            jSONObject2.put("appName", obj);
            jSONObject2.put("versionCode", i);
            jSONObject2.put("versionName", str);
            this.d.a(this.e, a.OK, "success", jSONObject2);
        } catch (Throwable e) {
            e.d("PushSelfShowLog", "getAppInfo param failed ", e);
            this.d.a(this.e, a.APP_NOT_EXIST, DownloadRecord.COLUMN_ERROR, null);
        }
    }

    private String c(JSONObject jSONObject) throws JSONException {
        JSONObject a;
        if (jSONObject == null || !jSONObject.has("package-name")) {
            a = com.huawei.android.pushselfshow.richpush.html.api.d.a(a.JSON_EXCEPTION);
        } else {
            try {
                String string = jSONObject.getString("package-name");
                PackageManager packageManager = this.f.getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageInfo(string, 0);
                String obj = packageInfo.applicationInfo.loadLabel(packageManager).toString();
                String str = packageInfo.versionName;
                int i = packageInfo.versionCode;
                a = com.huawei.android.pushselfshow.richpush.html.api.d.a(a.OK);
                a.put("appName", obj);
                a.put("versionCode", i);
                a.put("versionName", str);
            } catch (Throwable e) {
                e.d("PushSelfShowLog", "getAppInfo param failed ", e);
                a = com.huawei.android.pushselfshow.richpush.html.api.d.a(a.APP_NOT_EXIST);
            }
        }
        return a.toString();
    }

    public String a(String str, JSONObject jSONObject) {
        try {
            return "getAppInfo".equals(str) ? c(jSONObject) : com.huawei.android.pushselfshow.richpush.html.api.d.a(a.METHOD_NOT_FOUND_EXCEPTION).toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void a(int i, int i2, Intent intent) {
        e.e("PushSelfShowLog", "onActivityResult and requestCode is " + i + " resultCode is " + i2 + " intent data is " + intent);
        try {
            if (this.a && i2 == this.c && intent != null) {
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    for (String str : extras.keySet()) {
                        jSONObject2.put(str, extras.get(str));
                    }
                    if (jSONObject2.length() > 0) {
                        jSONObject.put("extra", jSONObject2);
                        this.d.a(this.e, a.OK, "status", jSONObject);
                    }
                }
            }
        } catch (JSONException e) {
            e.e("PushSelfShowLog", "onActivityResult error");
        } catch (Exception e2) {
            e.e("PushSelfShowLog", "onActivityResult error");
        }
    }

    public void a(NativeToJsMessageQueue nativeToJsMessageQueue, String str, String str2, JSONObject jSONObject) {
        if (nativeToJsMessageQueue == null) {
            e.a("PushSelfShowLog", "jsMessageQueue is null while run into App exec");
            return;
        }
        this.d = nativeToJsMessageQueue;
        if ("openApp".equals(str)) {
            d();
            if (str2 != null) {
                this.e = str2;
                a(jSONObject);
                return;
            }
            e.a("PushSelfShowLog", "Audio exec callback is null ");
        } else if (!"getAppInfo".equals(str)) {
            nativeToJsMessageQueue.a(str2, a.METHOD_NOT_FOUND_EXCEPTION, DownloadRecord.COLUMN_ERROR, null);
        } else if (str2 != null) {
            this.e = str2;
            b(jSONObject);
        } else {
            e.a("PushSelfShowLog", "Audio exec callback is null ");
        }
    }

    public void a(String str) {
        Intent intent;
        if (com.huawei.android.pushselfshow.utils.a.a(this.f, "com.huawei.appmarket", new Intent("com.huawei.appmarket.intent.action.AppDetail")).booleanValue()) {
            e.a("PushSelfShowLog", "app not exist && appmarkt exist ,so open appmarket");
            intent = new Intent("com.huawei.appmarket.intent.action.AppDetail");
            intent.putExtra("APP_PACKAGENAME", str);
            intent.setPackage("com.huawei.appmarket");
            intent.setFlags(402653184);
            e.a("PushSelfShowLog", "hwAppmarket only support com.huawei.appmarket.intent.action.AppDetail!");
        } else if (com.huawei.android.pushselfshow.utils.a.c(this.f).size() > 0) {
            e.a("PushSelfShowLog", "app not exist && other appmarkt exist ,so open appmarket");
            intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse("market://details?id=" + str));
            intent.setFlags(402653184);
        } else {
            e.a("PushSelfShowLog", "app not exist && other appmarkt not exist ,so do nothing");
            try {
                new e(this).start();
                intent = null;
            } catch (Exception e) {
                e.a("PushSelfShowLog", "show Toast.makeText error");
                intent = null;
            }
        }
        if (intent != null) {
            e.e("PushSelfShowLog", "intent is not null " + intent.toURI());
            this.f.startActivity(intent);
            e.e("PushSelfShowLog", "APP_OPEN_APPMARKET and open with appmaeket");
            this.d.a(this.e, a.APP_OPEN_APPMARKET, "success", null);
            return;
        }
        e.a("PushSelfShowLog", "intent is null ");
        e.e("PushSelfShowLog", "APP_OPEN_APPMARKET and not find any  appmaeket");
        this.d.a(this.e, a.APP_NOT_APPMARKET, DownloadRecord.COLUMN_ERROR, null);
    }

    public void b() {
    }

    public void c() {
        d();
    }

    public void d() {
        this.e = null;
        this.a = false;
        this.b = 0;
        this.c = 0;
    }
}
