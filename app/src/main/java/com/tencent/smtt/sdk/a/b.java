package com.tencent.smtt.sdk.a;

import MTT.a;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.l;
import com.tencent.smtt.utils.n;
import com.tencent.smtt.utils.o;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import org.json.JSONObject;

public class b {
    public static byte[] a;

    static {
        a = null;
        try {
            a = "65dRa93L".getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
        }
    }

    private static String a(Context context) {
        String str = null;
        try {
            byte[] toByteArray = context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures[0].toByteArray();
            if (toByteArray != null) {
                MessageDigest instance = MessageDigest.getInstance("SHA-1");
                instance.update(toByteArray);
                toByteArray = instance.digest();
                if (toByteArray != null) {
                    StringBuilder stringBuilder = new StringBuilder("");
                    if (toByteArray != null && toByteArray.length > 0) {
                        for (int i = 0; i < toByteArray.length; i++) {
                            String toUpperCase = Integer.toHexString(toByteArray[i] & 255).toUpperCase();
                            if (i > 0) {
                                stringBuilder.append(":");
                            }
                            if (toUpperCase.length() < 2) {
                                stringBuilder.append(0);
                            }
                            stringBuilder.append(toUpperCase);
                        }
                        str = stringBuilder.toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void a(a aVar) {
        new c("HttpUtils", aVar).start();
    }

    public static void a(Context context, String str, String str2, String str3, int i, boolean z) {
        String str4;
        String str5 = "";
        try {
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            if ("com.tencent.mobileqq".equals(applicationInfo.packageName)) {
                str5 = context.getPackageManager().getPackageInfo(applicationInfo.packageName, 0).versionName;
                if (!TextUtils.isEmpty(QbSdk.getQQBuildNumber())) {
                    str5 = str5 + "." + QbSdk.getQQBuildNumber();
                }
            }
            str4 = str5;
        } catch (Exception e) {
            Exception exception = e;
            str4 = str5;
            exception.printStackTrace();
        }
        try {
            a aVar = new a();
            aVar.sAppName = context.getApplicationInfo().packageName;
            o.a(context);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08"));
            aVar.ce = simpleDateFormat.format(Calendar.getInstance().getTime());
            aVar.ci = str;
            if (z) {
                aVar.cf = str2;
            } else {
                aVar.cf = n.a(context);
            }
            aVar.cg = str3;
            str5 = com.tencent.smtt.utils.a.e(context);
            String c = com.tencent.smtt.utils.a.c(context);
            String d = com.tencent.smtt.utils.a.d(context);
            Object f = com.tencent.smtt.utils.a.f(context);
            if (!(c == null || "".equals(c))) {
                aVar.cj = c;
            }
            if (!(d == null || "".equals(d))) {
                aVar.ck = d;
            }
            if (!TextUtils.isEmpty(f)) {
                aVar.ct = f;
            }
            if (!(str5 == null || "".equals(str5))) {
                aVar.cm = str5;
            }
            aVar.cn = (long) i;
            aVar.co = z ? 1 : 0;
            aVar.cq = str4;
            aVar.cr = a(context);
            a(aVar);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private static JSONObject c(a aVar) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("APPNAME", aVar.sAppName);
            jSONObject.put("TIME", aVar.ce);
            jSONObject.put("QUA2", aVar.cf);
            jSONObject.put("LC", aVar.cg);
            jSONObject.put("GUID", aVar.ci);
            jSONObject.put("IMEI", aVar.cj);
            jSONObject.put("IMSI", aVar.ck);
            jSONObject.put("MAC", aVar.cm);
            jSONObject.put("PV", aVar.cn);
            jSONObject.put("CORETYPE", aVar.co);
            jSONObject.put("APPVN", aVar.cq);
            if (aVar.cr == null) {
                jSONObject.put("SIGNATURE", "0");
            } else {
                jSONObject.put("SIGNATURE", aVar.cr);
            }
            jSONObject.put("PROTOCOL_VERSION", 3);
            jSONObject.put("ANDROID_ID", aVar.ct);
            try {
                if (QbSdk.getTID() == null) {
                    return jSONObject;
                }
                if (aVar.sAppName.equals("com.tencent.mobileqq")) {
                    jSONObject.put("TID", l.a().a(QbSdk.getTID()));
                    jSONObject.put("TIDTYPE", 1);
                    return jSONObject;
                } else if (!aVar.sAppName.equals("com.tencent.mm")) {
                    return jSONObject;
                } else {
                    jSONObject.put("TID", QbSdk.getTID());
                    jSONObject.put("TIDTYPE", 0);
                    return jSONObject;
                }
            } catch (Exception e) {
                return jSONObject;
            }
        } catch (Exception e2) {
            TbsLog.e("sdkreport", "getPostData exception!");
            return null;
        }
    }
}
