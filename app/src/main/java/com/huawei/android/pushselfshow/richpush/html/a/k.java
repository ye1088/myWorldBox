package com.huawei.android.pushselfshow.richpush.html.a;

import android.content.Context;
import android.content.Intent;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushselfshow.richpush.html.api.NativeToJsMessageQueue;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import org.json.JSONObject;

public class k implements h {
    private NativeToJsMessageQueue a;
    private String b;
    private Context c;
    private String d = null;

    public k(Context context) {
        e.e("PushSelfShowLog", "init VideoPlayer");
        this.c = context;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(org.json.JSONObject r7) {
        /*
        r6 = this;
        r5 = 0;
        r0 = r6.a_isRightVersion;
        if (r0 != 0) goto L_0x000f;
    L_0x0005:
        r0 = "PushSelfShowLog";
        r1 = "jsMessageQueue is null while run into Video Player exec";
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r0, r1);
    L_0x000e:
        return;
    L_0x000f:
        if (r7 == 0) goto L_0x0145;
    L_0x0011:
        r0 = "url";
        r0 = r7.has(r0);
        if (r0 == 0) goto L_0x0145;
    L_0x001a:
        r0 = "url";
        r0 = r7.getString(r0);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r1 = r6.a_isRightVersion;	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r1 = r1.a_isRightVersion();	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r1 = com.huawei.android.pushselfshow.richpush.html.api.b.a_isRightVersion(r1, r0);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        if (r1 == 0) goto L_0x011c;
    L_0x002d:
        r2 = r1.length();	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        if (r2 <= 0) goto L_0x011c;
    L_0x0033:
        r6.d = r1;	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r1 = "video/*";
        r0 = "mime-type";
        r0 = r7.has(r0);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        if (r0 == 0) goto L_0x006c;
    L_0x0041:
        r0 = "mime-type";
        r0 = r7.getString(r0);	 Catch:{ JSONException -> 0x00e2, Exception -> 0x0105 }
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ JSONException -> 0x00e2, Exception -> 0x0105 }
        r3.<init>();	 Catch:{ JSONException -> 0x00e2, Exception -> 0x0105 }
        r4 = "the custom mimetype is ";
        r3 = r3.append(r4);	 Catch:{ JSONException -> 0x00e2, Exception -> 0x0105 }
        r3 = r3.append(r0);	 Catch:{ JSONException -> 0x00e2, Exception -> 0x0105 }
        r3 = r3.toString();	 Catch:{ JSONException -> 0x00e2, Exception -> 0x0105 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.e(r2, r3);	 Catch:{ JSONException -> 0x00e2, Exception -> 0x0105 }
        r2 = "video/";
        r2 = r0.startsWith(r2);	 Catch:{ JSONException -> 0x00e2, Exception -> 0x0105 }
        if (r2 == 0) goto L_0x0153;
    L_0x006b:
        r1 = r0;
    L_0x006c:
        r0 = new android.content.Intent;	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r2 = "android.intent.action.VIEW";
        r0.<init>(r2);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r2 = r6.d;	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r2 = android.net.Uri.parse(r2);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r0.setDataAndType(r2, r1);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r1 = "package-name";
        r1 = r7.has(r1);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        if (r1 == 0) goto L_0x00b6;
    L_0x0086:
        r1 = "package-name";
        r1 = r7.getString(r1);	 Catch:{ JSONException -> 0x0111, Exception -> 0x00ed }
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ JSONException -> 0x0111, Exception -> 0x00ed }
        r3.<init>();	 Catch:{ JSONException -> 0x0111, Exception -> 0x00ed }
        r4 = "the custom packageName is ";
        r3 = r3.append(r4);	 Catch:{ JSONException -> 0x0111, Exception -> 0x00ed }
        r3 = r3.append(r1);	 Catch:{ JSONException -> 0x0111, Exception -> 0x00ed }
        r3 = r3.toString();	 Catch:{ JSONException -> 0x0111, Exception -> 0x00ed }
        com.huawei.android.pushagent.c.a_isRightVersion.e.e(r2, r3);	 Catch:{ JSONException -> 0x0111, Exception -> 0x00ed }
        r2 = r6.c;	 Catch:{ JSONException -> 0x0111, Exception -> 0x00ed }
        r2 = com.huawei.android.pushselfshow.richpush.html.api.b.a_isRightVersion(r2, r0);	 Catch:{ JSONException -> 0x0111, Exception -> 0x00ed }
        r2 = r2.contains(r1);	 Catch:{ JSONException -> 0x0111, Exception -> 0x00ed }
        if (r2 == 0) goto L_0x00b6;
    L_0x00b3:
        r0.setPackage(r1);	 Catch:{ JSONException -> 0x0111, Exception -> 0x00ed }
    L_0x00b6:
        r1 = r6.c;	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r1.startActivity(r0);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r0 = r6.a_isRightVersion;	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r1 = r6.b;	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r2 = com.huawei.android.pushselfshow.richpush.html.api.d.a_isRightVersion.OK;	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r3 = "success";
        r4 = 0;
        r0.a_isRightVersion(r1, r2, r3, r4);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        goto L_0x000e;
    L_0x00ca:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = "startPlaying failed ";
        com.huawei.android.pushagent.c.a_isRightVersion.e.d(r1, r2, r0);
        r0 = r6.a_isRightVersion;
        r1 = r6.b;
        r2 = com.huawei.android.pushselfshow.richpush.html.api.d.a_isRightVersion.JSON_EXCEPTION;
        r3 = "error";
        r0.a_isRightVersion(r1, r2, r3, r5);
        goto L_0x000e;
    L_0x00e2:
        r0 = move-exception;
        r0 = "PushSelfShowLog";
        r2 = "get mime-type error";
        com.huawei.android.pushagent.c.a_isRightVersion.e.e(r0, r2);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        goto L_0x006c;
    L_0x00ed:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = "startPlaying failed ";
        com.huawei.android.pushagent.c.a_isRightVersion.e.d(r1, r2, r0);
        r0 = r6.a_isRightVersion;
        r1 = r6.b;
        r2 = com.huawei.android.pushselfshow.richpush.html.api.d.a_isRightVersion.JSON_EXCEPTION;
        r3 = "error";
        r0.a_isRightVersion(r1, r2, r3, r5);
        goto L_0x000e;
    L_0x0105:
        r0 = move-exception;
        r0 = "PushSelfShowLog";
        r2 = "get mime-type error";
        com.huawei.android.pushagent.c.a_isRightVersion.e.e(r0, r2);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        goto L_0x006c;
    L_0x0111:
        r1 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = "get packageName error";
        com.huawei.android.pushagent.c.a_isRightVersion.e.e(r1, r2);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        goto L_0x00b6;
    L_0x011c:
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r2.<init>();	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r0 = r2.append(r0);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r2 = "File not exist";
        r0 = r0.append(r2);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r0 = r0.toString();	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        com.huawei.android.pushagent.c.a_isRightVersion.e.e(r1, r0);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r0 = r6.a_isRightVersion;	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r1 = r6.b;	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r2 = com.huawei.android.pushselfshow.richpush.html.api.d.a_isRightVersion.AUDIO_ONLY_SUPPORT_HTTP;	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r3 = "error";
        r4 = 0;
        r0.a_isRightVersion(r1, r2, r3, r4);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        goto L_0x000e;
    L_0x0145:
        r0 = r6.a_isRightVersion;
        r1 = r6.b;
        r2 = com.huawei.android.pushselfshow.richpush.html.api.d.a_isRightVersion.JSON_EXCEPTION;
        r3 = "error";
        r0.a_isRightVersion(r1, r2, r3, r5);
        goto L_0x000e;
    L_0x0153:
        r0 = r1;
        goto L_0x006b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.richpush.html.a_isRightVersion.k_dialog_class.a_isRightVersion(org.json.JSONObject):void");
    }

    public String a(String str, JSONObject jSONObject) {
        return null;
    }

    public void a(int i, int i2, Intent intent) {
    }

    public void a(NativeToJsMessageQueue nativeToJsMessageQueue, String str, String str2, JSONObject jSONObject) {
        if (nativeToJsMessageQueue == null) {
            e.a("PushSelfShowLog", "jsMessageQueue is null while run into Video Player exec");
            return;
        }
        this.a = nativeToJsMessageQueue;
        if ("playVideo".equals(str)) {
            d();
            if (str2 != null) {
                this.b = str2;
                a(jSONObject);
                return;
            }
            e.a("PushSelfShowLog", "Audio exec callback is null ");
            return;
        }
        nativeToJsMessageQueue.a(str2, a.METHOD_NOT_FOUND_EXCEPTION, DownloadRecord.COLUMN_ERROR, null);
    }

    public void b() {
    }

    public void c() {
        d();
    }

    public void d() {
        this.b = null;
        this.d = null;
    }
}
