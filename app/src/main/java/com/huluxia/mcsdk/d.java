package com.huluxia.mcsdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import com.huluxia.mcsdk.DTSDKManagerEx.a;
import com.huluxia.mcsdk.dtlib.h;
import io.netty.handler.codec.http2.Http2CodecUtil;
import java.util.ArrayList;
import java.util.List;
import net.zhuoweizhang.mcpelauncher.api.modpe.RendererManager;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/* compiled from: DTRendererManagerEx */
public class d {
    public static int anA = 0;
    public static int anB = 0;
    public static String anC = "";
    public static String anD = "";
    public static String anE = "";
    public static Context anF = null;
    public static SharedPreferences anG = null;
    private static boolean anH = false;
    public static a anI = new a() {
        protected void dv(String strText) {
        }

        protected List<NameValuePair> Ci() {
            String str_eventType = b.a(new short[]{(short) 0, (short) 229, (short) 247, (short) 231, (short) 237, (short) 240, (short) 209, Http2CodecUtil.MAX_UNSIGNED_BYTE, (short) 247, (short) 237});
            String str_time = b.a(new short[]{(short) 0, (short) 244, (short) 232, (short) 239, (short) 230});
            String str_key = b.a(new short[]{(short) 0, (short) 235, (short) 228, (short) 251});
            String str_globalid = b.a(new short[]{(short) 0, (short) 231, (short) 237, (short) 237, (short) 225, (short) 229, (short) 233, (short) 207, (short) 195});
            String imei_text = ((TelephonyManager) d.anF.getSystemService(com.huluxia.data.profile.a.qe)).getDeviceId();
            List<NameValuePair> params = new ArrayList();
            params.add(new BasicNameValuePair(str_eventType, "mcsv"));
            params.add(new BasicNameValuePair(str_time, "1433527374004"));
            params.add(new BasicNameValuePair(str_key, "be87076ab76d7220fe6c2d656716510e"));
            params.add(new BasicNameValuePair(str_globalid, imei_text));
            return params;
        }
    };
    public static final int anp = 0;
    public static final int anq = 1;
    public static final int anr = 2;
    public static final int ans = 4;
    public static final int anu = 5;
    public static String anv = "";
    public static String anw = "";
    public static String anx = "";
    public static String any = "";
    public static String anz = "";

    public static void a(int rendererId, String modelPart, float xOffset, float yOffset, float zOffset, int width, int height, int depth, float scale, int textureX, int textureY, boolean transparent, float textureWidth, float textureHeight) {
        if (h.CW().Da() == 1) {
            DTRendererManager.nativeModelAddBox(rendererId, modelPart, xOffset, yOffset, zOffset, width, height, depth, scale, textureX, textureY, transparent, textureWidth, textureHeight);
        } else {
            RendererManager.nativeModelAddBox(rendererId, modelPart, xOffset, yOffset, zOffset, width, height, depth, scale, textureX, textureY, transparent, textureWidth, textureHeight);
        }
    }

    public static void c(int rendererId, String modelPart) {
        if (h.CW().Da() == 1) {
            DTRendererManager.nativeModelClear(rendererId, modelPart);
        } else {
            RendererManager.nativeModelClear(rendererId, modelPart);
        }
    }

    public static boolean d(int rendererId, String modelPart) {
        if (h.CW().Da() == 1) {
            return DTRendererManager.nativeModelPartExists(rendererId, modelPart);
        }
        return RendererManager.nativeModelPartExists(rendererId, modelPart);
    }

    public static int Cl() {
        if (h.CW().Da() == 1) {
            return DTRendererManager.nativeCreateHumanoidRenderer();
        }
        return RendererManager.nativeCreateHumanoidRenderer();
    }

    public static void a(int rendererId, String modelPart, float x, float y, float z) {
        if (h.CW().Da() == 1) {
            DTRendererManager.nativeModelSetRotationPoint(rendererId, modelPart, x, y, z);
        } else {
            RendererManager.nativeModelSetRotationPoint(rendererId, modelPart, x, y, z);
        }
    }

    public static void aV(Context c) {
        if (anB == 0 && !anH) {
            anH = true;
            anF = c;
            anv = b.a(new short[]{(short) 0, (short) 232, (short) 237, (short) 250, (short) 220, (short) 241});
            anw = b.a(new short[]{(short) 0, (short) 232, (short) 237, (short) 250, (short) 220, (short) 226, (short) 225});
            anx = b.a(new short[]{(short) 0, (short) 232, (short) 237, (short) 250, (short) 220, (short) 231, (short) 236});
            any = b.a(new short[]{(short) 0, (short) 232, (short) 237, (short) 250, (short) 220, (short) 241, (short) 233});
            anz = b.a(new short[]{(short) 0, (short) 232, (short) 237, (short) 250, (short) 220, (short) 233, (short) 225});
            anG = c.getSharedPreferences(anv, 0);
            Time time = new Time();
            time.setToNow();
            int first_date = a.a(anG, anw);
            if (first_date > 365) {
                first_date = 0;
            }
            if (first_date == 0) {
                a.a(anG, anw, time.yearDay);
            } else if (time.yearDay - first_date > 5) {
                anB = 1;
            }
        }
    }
}
