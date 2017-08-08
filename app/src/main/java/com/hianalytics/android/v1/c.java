package com.hianalytics.android.v1;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.util.Base64;

import com.MCWorld.framework.base.utils.UtilsRSA;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.SecureRandom;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class c implements Runnable {
    boolean a;
    private Context b;
    private JSONObject c;

    public c(Context context, JSONObject jSONObject, boolean z) {
        this.b = context;
        this.c = jSONObject;
        this.a = z;
    }

    private String a(byte[] bArr) {
        SecureRandom secureRandom = new SecureRandom();
        Object valueOf = String.valueOf(System.currentTimeMillis());
        int length = valueOf.length();
        if (length < 13) {
            StringBuffer stringBuffer = new StringBuffer(valueOf);
            for (int i = 0; i < 13 - length; i++) {
                stringBuffer.append("0");
            }
            valueOf = stringBuffer.toString();
        } else if (length > 13) {
            valueOf = valueOf.substring(0, 13);
        }
        String stringBuilder = new StringBuilder(String.valueOf(valueOf)).append(String.format("%03d", new Object[]{Integer.valueOf(secureRandom.nextInt(999))})).toString();
        try {
            byte[] c = b.c(stringBuilder, bArr);
            byte[] bytes = stringBuilder.getBytes("UTF-8");
            RSAPublicKey rSAPublicKey = (RSAPublicKey) KeyFactory.getInstance(UtilsRSA.KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(Base64.decode("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDf5raDExuuXbsVNCWl48yuB89W\rfNOuuhPuS2Mptii/0UorpzypBkNTTGt11E7aorCc1lFwlB+4KDMIpFyQsdChSk+A\rt9UfhFKa95uiDpMe5rMfU+DAhoXGER6WQ2qGtrHmBWVv33i3lc76u9IgEfYuLwC6\r1mhQDHzAKPiViY6oeQIDAQAB\r", 0)));
            Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            instance.init(1, rSAPublicKey);
            byte[] doFinal = instance.doFinal(bytes);
            return "{\"vs\":\"" + a.e(this.b) + "\",\"ed\":\"" + a.b(c) + "\",\"ek\":\"" + a.b(doFinal) + "\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean a(JSONObject jSONObject, String str) {
        String toLowerCase = str.toLowerCase();
        try {
            byte[] a = a.a(jSONObject.toString().getBytes("UTF-8"));
            if (a == null) {
                return false;
            }
            String a2 = a(a);
            if (a2 == null) {
                return false;
            }
            try {
                a = a2.getBytes("UTF-8");
                if (toLowerCase.indexOf("https") >= 0) {
                    return false;
                }
                a.h();
                return f.d(str, a);
            } catch (UnsupportedEncodingException e) {
                "UnsupportedEncodingException:" + e.getMessage();
                a.h();
                return false;
            }
        } catch (UnsupportedEncodingException e2) {
            "UnsupportedEncodingException:" + e2.getMessage();
            a.h();
            return false;
        }
    }

    public final void run() {
        try {
            if (this.c.getString("type") != null) {
                Object obj;
                Context context = this.b;
                JSONObject jSONObject = this.c;
                boolean z = this.a;
                StringBuffer stringBuffer = new StringBuffer("1.0");
                String a = a.a(context);
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(com.MCWorld.data.profile.a.qe);
                if (telephonyManager == null) {
                    a.h();
                    obj = null;
                } else {
                    Configuration configuration = context.getResources().getConfiguration();
                    String locale = (configuration == null || configuration.locale == null) ? "" : configuration.locale.toString();
                    String b = a.a(context, "android.permission.READ_PHONE_STATE") ? a.b(telephonyManager.getDeviceId()) : "";
                    String e = a.e(context);
                    if (a.f(context)) {
                        stringBuffer.append(MiPushClient.ACCEPT_TIME_SEPARATOR).append("Android" + VERSION.RELEASE).append(MiPushClient.ACCEPT_TIME_SEPARATOR).append(locale).append(MiPushClient.ACCEPT_TIME_SEPARATOR).append(Build.MODEL).append(MiPushClient.ACCEPT_TIME_SEPARATOR).append(Build.DISPLAY).append(MiPushClient.ACCEPT_TIME_SEPARATOR).append(e).append(MiPushClient.ACCEPT_TIME_SEPARATOR).append(b).append(MiPushClient.ACCEPT_TIME_SEPARATOR).append(a).append(MiPushClient.ACCEPT_TIME_SEPARATOR).append(a.b(context));
                        a.h();
                    } else {
                        stringBuffer.append(",,,,,").append(e).append(MiPushClient.ACCEPT_TIME_SEPARATOR).append(b).append(MiPushClient.ACCEPT_TIME_SEPARATOR).append(a).append(MiPushClient.ACCEPT_TIME_SEPARATOR);
                        a.h();
                    }
                    String stringBuffer2 = stringBuffer.toString();
                }
                if (obj == null) {
                    a.h();
                    return;
                }
                JSONObject h = com.hianalytics.android.a.a.c.h(context, "cached");
                JSONObject jSONObject2 = new JSONObject();
                try {
                    String string = jSONObject.getString("type");
                    if (string != null) {
                        JSONArray jSONArray;
                        jSONObject.remove("type");
                        Object obj2 = 1;
                        if (h == null) {
                            h = new JSONObject();
                            jSONArray = new JSONArray();
                        } else if (h.isNull(string)) {
                            jSONArray = new JSONArray();
                        } else {
                            obj2 = null;
                            jSONArray = h.getJSONArray(string);
                        }
                        if (!z || r2 == null) {
                            if (!z) {
                                jSONArray.put(jSONObject);
                            }
                            JSONArray jSONArray2 = new JSONArray();
                            int length = jSONArray.length();
                            for (int i = 0; i <= length - 1; i++) {
                                JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                                JSONArray jSONArray3;
                                if (jSONObject3.has("b")) {
                                    jSONArray3 = jSONObject3.getJSONArray("b");
                                    if (jSONArray3 != null && jSONArray3.length() > 0) {
                                        String[] split = jSONArray3.getString(jSONArray3.length() - 1).split(MiPushClient.ACCEPT_TIME_SEPARATOR);
                                        if (((System.currentTimeMillis() / 1000) - a.a(split[1])) - Long.parseLong(split[2]) < a.bm().longValue()) {
                                            jSONArray2.put(jSONObject3);
                                        } else {
                                            a.h();
                                        }
                                    }
                                } else if (jSONObject3.has("e")) {
                                    jSONArray3 = jSONObject3.getJSONArray("e");
                                    if (jSONArray3 != null && jSONArray3.length() > 0) {
                                        if ((System.currentTimeMillis() / 1000) - a.a(jSONArray3.getString(jSONArray3.length() - 1).split(MiPushClient.ACCEPT_TIME_SEPARATOR)[2]) < a.bm().longValue()) {
                                            jSONArray2.put(jSONObject3);
                                        } else {
                                            a.h();
                                        }
                                    }
                                }
                            }
                            if (jSONArray2.length() <= 0) {
                                a.h();
                                return;
                            }
                            h.remove(string);
                            h.put(string, jSONArray2);
                            jSONObject2.put("g", obj);
                            jSONObject2.put("s", jSONArray2);
                            "message=" + jSONObject2.toString();
                            a.h();
                            if (a(jSONObject2, a.i())) {
                                SharedPreferences a2 = com.hianalytics.android.a.a.c.a(context, "flag");
                                if (a.f(context)) {
                                    Editor edit = a2.edit();
                                    edit.putString("rom_version", Build.DISPLAY);
                                    edit.commit();
                                }
                                com.hianalytics.android.a.a.c.c(context, "cached");
                                a.h();
                                return;
                            }
                            com.hianalytics.android.a.a.c.a(context, h, "cached");
                            a.h();
                            return;
                        }
                        a.h();
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                    com.hianalytics.android.a.a.c.c(context, "cached");
                }
            }
        } catch (Exception e3) {
            "MessageThread.run() throw exception:" + e3.getMessage();
            a.h();
            e3.printStackTrace();
            com.hianalytics.android.a.a.c.c(this.b, "cached");
        }
    }
}
