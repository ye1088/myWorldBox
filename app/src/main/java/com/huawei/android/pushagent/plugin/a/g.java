package com.huawei.android.pushagent.plugin.a;

import android.content.Context;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushagent.plugin.tools.BLocation;
import com.huawei.android.pushagent.plugin.tools.a.a;
import com.huawei.android.pushagent.plugin.tools.a.b;
import com.j256.ormlite.stmt.query.SimpleComparison;
import io.netty.handler.codec.rtsp.RtspHeaders.Values;
import org.json.JSONException;
import org.json.JSONObject;

public class g {
    private JSONObject a = new JSONObject();

    public g(String str, int i, String str2, String str3, Context context) {
        try {
            e.a(BLocation.TAG, "cmd is:" + i);
            this.a.put("token", str);
            this.a.put("cmd", i);
            this.a.put("saltHash", str2);
            this.a.put("content", str3);
            this.a.put("appVersion", b(context));
        } catch (JSONException e) {
            e.b(BLocation.TAG, "init ReportReq error:" + e.getMessage());
        }
    }

    private static String b(Context context) {
        String str = "0.0";
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Throwable e) {
            e.a(BLocation.TAG, "package not exist", e);
            return str;
        } catch (Throwable e2) {
            e.c(BLocation.TAG, "getApkVersionName error", e2);
            return str;
        }
    }

    public String a() {
        return this.a.optString("token", "");
    }

    public String a(Context context) {
        byte[] a = b.a(new e(context).a().toCharArray());
        if (a.length == 0) {
            return "";
        }
        try {
            String a2 = a.a(a).a(d());
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("token").append(SimpleComparison.EQUAL_TO_OPERATION).append(a()).append("&").append("cmd").append(SimpleComparison.EQUAL_TO_OPERATION).append(b()).append("&").append("saltHash").append(SimpleComparison.EQUAL_TO_OPERATION).append(c()).append("&").append(Values.MODE).append(SimpleComparison.EQUAL_TO_OPERATION).append(0).append("&").append("appVersion").append(SimpleComparison.EQUAL_TO_OPERATION).append(e()).append("&").append("content").append(SimpleComparison.EQUAL_TO_OPERATION).append(a2);
            return stringBuffer.toString();
        } catch (Throwable e) {
            e.c(BLocation.TAG, "encrypt request content InvalidKeyException:" + e.getMessage(), e);
            return "";
        } catch (Throwable e2) {
            e.c(BLocation.TAG, "encrypt request content NoSuchAlgorithmException:" + e2.getMessage(), e2);
            return "";
        } catch (Throwable e22) {
            e.c(BLocation.TAG, "encrypt request content NoSuchPaddingException:" + e22.getMessage(), e22);
            return "";
        } catch (Throwable e222) {
            e.c(BLocation.TAG, "encrypt request content IllegalBlockSizeException:" + e222.getMessage(), e222);
            return "";
        } catch (Throwable e2222) {
            e.c(BLocation.TAG, "encrypt request content BadPaddingException:" + e2222.getMessage(), e2222);
            return "";
        } catch (Throwable e22222) {
            e.c(BLocation.TAG, "encrypt request content UnsupportedEncodingException:" + e22222.getMessage(), e22222);
            return "";
        } catch (Throwable e222222) {
            e.c(BLocation.TAG, "encrypt request content InvalidAlgorithmParameterException:" + e222222.getMessage(), e222222);
            return "";
        } catch (Throwable e2222222) {
            e.c(BLocation.TAG, "encrypt request content Exception:" + e2222222.getMessage(), e2222222);
            return "";
        }
    }

    public int b() {
        return this.a.optInt("cmd", -1);
    }

    public String c() {
        return this.a.optString("saltHash", "");
    }

    public String d() {
        return this.a.optString("content", "");
    }

    public String e() {
        return this.a.optString("appVersion", "");
    }
}
