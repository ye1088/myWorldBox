package com.tencent.smtt.sdk;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import com.tencent.smtt.sdk.TbsDownloadConfig.TbsConfigKey;
import com.tencent.smtt.sdk.TbsListener.ErrorCode;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.a;
import com.tencent.smtt.utils.h;
import com.tencent.smtt.utils.j;
import com.tencent.smtt.utils.o;
import java.io.File;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

public class TbsDownloader {
    public static final boolean DEBUG_DISABLE_DOWNLOAD = false;
    public static boolean DOWNLOAD_OVERSEA_TBS = false;
    public static final String LOGTAG = "TbsDownload";
    static boolean a;
    private static String b;
    private static Context c;
    private static Handler d;
    private static String e;
    private static HandlerThread f;
    private static boolean g = false;
    private static boolean h = false;
    private static boolean i = false;

    static String a(Context context) {
        if (!TextUtils.isEmpty(b)) {
            return b;
        }
        String str;
        Locale locale = Locale.getDefault();
        StringBuffer stringBuffer = new StringBuffer();
        String str2 = VERSION.RELEASE;
        try {
            str = new String(str2.getBytes("UTF-8"), "ISO8859-1");
        } catch (Exception e) {
            str = str2;
        }
        if (str.length() > 0) {
            stringBuffer.append(str);
        } else {
            stringBuffer.append("1.0");
        }
        stringBuffer.append("; ");
        str = locale.getLanguage();
        if (str != null) {
            stringBuffer.append(str.toLowerCase());
            str = locale.getCountry();
            if (str != null) {
                stringBuffer.append("-");
                stringBuffer.append(str.toLowerCase());
            }
        } else {
            stringBuffer.append("en");
        }
        if ("REL".equals(VERSION.CODENAME)) {
            str2 = Build.MODEL;
            try {
                str = new String(str2.getBytes("UTF-8"), "ISO8859-1");
            } catch (Exception e2) {
                str = str2;
            }
            if (str.length() > 0) {
                stringBuffer.append("; ");
                stringBuffer.append(str);
            }
        }
        str = Build.ID.replaceAll("[一-龥]", "");
        if (str.length() > 0) {
            stringBuffer.append(" Build/");
            stringBuffer.append(str);
        }
        str = String.format("Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/533.1 (KHTML, like Gecko)Version/4.0 Mobile Safari/533.1", new Object[]{stringBuffer});
        b = str;
        return str;
    }

    private static String a(String str) {
        return str == null ? "" : str;
    }

    private static boolean a() {
        try {
            for (String sharedTbsCoreVersion : TbsShareManager.getCoreProviderAppList()) {
                if (TbsShareManager.getSharedTbsCoreVersion(c, sharedTbsCoreVersion) > 0) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @TargetApi(11)
    private static boolean a(String str, int i) {
        TbsLog.i(LOGTAG, "[TbsDownloader.readResponse] response=" + str);
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        JSONObject jSONObject = new JSONObject(str);
        if (jSONObject.getInt("RET") != 0) {
            return false;
        }
        int i2;
        TbsDownloadConfig a = TbsDownloadConfig.a(c);
        int i3 = jSONObject.getInt("RESPONSECODE");
        CharSequence string = jSONObject.getString("DOWNLOADURL");
        int i4 = jSONObject.getInt("TBSAPKSERVERVERSION");
        int i5 = jSONObject.getInt("DOWNLOADMAXFLOW");
        int i6 = jSONObject.getInt("DOWNLOAD_MIN_FREE_SPACE");
        int i7 = jSONObject.getInt("DOWNLOAD_SUCCESS_MAX_RETRYTIMES");
        int i8 = jSONObject.getInt("DOWNLOAD_FAILED_MAX_RETRYTIMES");
        long j = jSONObject.getLong("DOWNLOAD_SINGLE_TIMEOUT");
        long j2 = jSONObject.getLong("TBSAPKFILESIZE");
        long optLong = jSONObject.optLong("RETRY_INTERVAL");
        String str2 = null;
        int i9 = 0;
        int i10 = 0;
        try {
            str2 = jSONObject.getString("PKGMD5");
            i9 = jSONObject.getInt("RESETX5");
            Object obj = str2;
            i2 = i9;
            i9 = jSONObject.getInt("UPLOADLOG");
        } catch (Exception e) {
            int i11 = i10;
            String str3 = str2;
            i2 = i9;
            i9 = i11;
        }
        if (i2 == 1) {
            QbSdk.reset(c);
            return false;
        }
        if (i9 == 1) {
            d.removeMessages(104);
            Message.obtain(d, 104).sendToTarget();
        }
        if (TbsShareManager.isThirdPartyApp(c)) {
            a.a.put(TbsConfigKey.KEY_NEEDDOWNLOAD, Boolean.valueOf(false));
            a.b();
            TbsShareManager.writeCoreInfoForThirdPartyApp(c, i4, false);
            return false;
        } else if (i3 == 0) {
            a.a.put(TbsConfigKey.KEY_NEEDDOWNLOAD, Boolean.valueOf(false));
            a.b();
            return false;
        } else {
            i2 = TbsDownloadConfig.a(c).b.getInt(TbsConfigKey.KEY_TBS_DOWNLOAD_V, 0);
            i9 = (VERSION.SDK_INT >= 11 ? c.getSharedPreferences("tbs_preloadx5_check_cfg_file", 4) : c.getSharedPreferences("tbs_preloadx5_check_cfg_file", 0)).getInt("tbs_precheck_disable_version", -1);
            if (i9 == i4 || i >= i4 || i2 > i4 || TextUtils.isEmpty(string)) {
                if (i9 == i4) {
                    TbsLog.e(LOGTAG, "Download is disabled by preload_x5_check; tbs_version:" + i4);
                }
                a.a.put(TbsConfigKey.KEY_NEEDDOWNLOAD, Boolean.valueOf(false));
                a.b();
                return false;
            }
            a.a.put(TbsConfigKey.KEY_TBS_DOWNLOAD_V, Integer.valueOf(i4));
            a.a.put(TbsConfigKey.KEY_TBSDOWNLOADURL, string);
            a.a.put(TbsConfigKey.KEY_RESPONSECODE, Integer.valueOf(i3));
            a.a.put(TbsConfigKey.KEY_DOWNLOAD_MAXFLOW, Integer.valueOf(i5));
            a.a.put(TbsConfigKey.KEY_DOWNLOAD_MIN_FREE_SPACE, Integer.valueOf(i6));
            a.a.put(TbsConfigKey.KEY_DOWNLOAD_SUCCESS_MAX_RETRYTIMES, Integer.valueOf(i7));
            a.a.put(TbsConfigKey.KEY_DOWNLOAD_FAILED_MAX_RETRYTIMES, Integer.valueOf(i8));
            a.a.put(TbsConfigKey.KEY_DOWNLOAD_SINGLE_TIMEOUT, Long.valueOf(j));
            a.a.put(TbsConfigKey.KEY_TBSAPKFILESIZE, Long.valueOf(j2));
            a.a.put(TbsConfigKey.KEY_RETRY_INTERVAL, Long.valueOf(optLong));
            if (obj != null) {
                a.a.put(TbsConfigKey.KEY_TBSAPK_MD5, obj);
            }
            if (aa.a().a(c, i4)) {
                a.a.put(TbsConfigKey.KEY_NEEDDOWNLOAD, Boolean.valueOf(false));
            } else {
                a.a.put(TbsConfigKey.KEY_NEEDDOWNLOAD, Boolean.valueOf(true));
            }
            a.b();
            return true;
        }
    }

    private static JSONObject b(boolean z) {
        TbsDownloadConfig a = TbsDownloadConfig.a(c);
        String a2 = a(c);
        String d = a.d(c);
        String c = a.c(c);
        String f = a.f(c);
        JSONObject jSONObject = new JSONObject();
        try {
            int i;
            jSONObject.put("PROTOCOLVERSION", 1);
            if (TbsShareManager.isThirdPartyApp(c)) {
                i = TbsDownloadConfig.a(c).b.getInt(TbsConfigKey.KEY_TBS_DOWNLOAD_V, 0);
            } else {
                int d2 = aa.a().d(c);
                i = (!z && d2 == 0 && aa.a().c(c)) ? -1 : d2;
            }
            if (z) {
                jSONObject.put("FUNCTION", 2);
            } else {
                jSONObject.put("FUNCTION", i == 0 ? 0 : 1);
            }
            if (TbsShareManager.isThirdPartyApp(c)) {
                Object obj;
                JSONArray jSONArray = new JSONArray();
                Object coreProviderAppList = TbsShareManager.getCoreProviderAppList();
                String packageName = c.getApplicationContext().getPackageName();
                Object obj2;
                if (packageName.equals(TbsShareManager.d(c))) {
                    int length = coreProviderAppList.length;
                    obj = new String[(length + 1)];
                    System.arraycopy(coreProviderAppList, 0, obj, 0, length);
                    obj[length] = packageName;
                    obj2 = obj;
                } else {
                    obj2 = coreProviderAppList;
                }
                for (String sharedTbsCoreVersion : r3) {
                    int sharedTbsCoreVersion2 = TbsShareManager.getSharedTbsCoreVersion(c, sharedTbsCoreVersion);
                    if (sharedTbsCoreVersion2 > 0) {
                        obj = null;
                        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                            if (jSONArray.optInt(i2) == sharedTbsCoreVersion2) {
                                obj = 1;
                                break;
                            }
                        }
                        if (obj == null) {
                            jSONArray.put(sharedTbsCoreVersion2);
                        }
                    }
                }
                jSONObject.put("TBSVLARR", jSONArray);
                if (QbSdk.c) {
                    jSONObject.put("THIRDREQ", 1);
                }
            }
            jSONObject.put("APPN", c.getPackageName());
            jSONObject.put("APPVN", a(a.b.getString(TbsConfigKey.KEY_APP_VERSIONNAME, null)));
            jSONObject.put("APPVC", a.b.getInt(TbsConfigKey.KEY_APP_VERSIONCODE, 0));
            jSONObject.put("APPMETA", a(a.b.getString(TbsConfigKey.KEY_APP_METADATA, null)));
            jSONObject.put("TBSSDKV", 36510);
            jSONObject.put("TBSV", i);
            jSONObject.put("CPU", e);
            jSONObject.put("UA", a2);
            jSONObject.put("IMSI", a(d));
            jSONObject.put("IMEI", a(c));
            jSONObject.put("ANDROID_ID", a(f));
            if (!TbsShareManager.isThirdPartyApp(c)) {
                jSONObject.put("STATUS", QbSdk.a(c, i) ? 0 : 1);
            }
            if (getOverSea(c)) {
                jSONObject.put("OVERSEA", 1);
            }
            if (QbSdk.mTbsDebugInstallOnline) {
                jSONObject.put("TBSDEBUG", 1);
            }
        } catch (Exception e) {
        }
        TbsLog.i(LOGTAG, "[TbsDownloader.postJsonData] jsonData=" + jSONObject.toString());
        return jSONObject;
    }

    private static synchronized void b() {
        synchronized (TbsDownloader.class) {
            if (f == null) {
                f = z.a();
                d = new x(f.getLooper());
            }
        }
    }

    private static void c() {
        TbsLog.i(LOGTAG, "[TbsDownloader.queryConfig]");
        d.removeMessages(100);
        Message.obtain(d, 100).sendToTarget();
    }

    private static boolean c(boolean z) {
        TbsLog.i(LOGTAG, "[TbsDownloader.sendRequest]");
        if (aa.a().a(c)) {
            TbsLog.i(LOGTAG, "[TbsDownloader.sendRequest] -- isTbsLocalInstalled!");
            return false;
        }
        int i;
        boolean a;
        TbsDownloadConfig a2 = TbsDownloadConfig.a(c);
        File file = new File(h.a(c, 1), getOverSea(c) ? "x5.oversea.tbs.org" : "x5.tbs.org");
        File file2 = new File(h.a(c, 2), getOverSea(c) ? "x5.oversea.tbs.org" : "x5.tbs.org");
        File file3 = new File(h.a(c, 3), getOverSea(c) ? "x5.oversea.tbs.org" : "x5.tbs.org");
        if (!file3.exists()) {
            if (file2.exists()) {
                file2.renameTo(file3);
            } else if (file.exists()) {
                file.renameTo(file3);
            }
        }
        a2.a.put(TbsConfigKey.KEY_LAST_CHECK, Long.valueOf(System.currentTimeMillis()));
        a2.a.put(TbsConfigKey.KEY_APP_VERSIONNAME, a.a(c));
        a2.a.put(TbsConfigKey.KEY_APP_VERSIONCODE, Integer.valueOf(a.b(c)));
        a2.a.put(TbsConfigKey.KEY_APP_METADATA, a.a(c, "com.tencent.mm.BuildInfo.CLIENT_VERSION"));
        a2.b();
        if (e == null) {
            e = a.a();
            a2.a.put(TbsConfigKey.KEY_DEVICE_CPUABI, e);
            a2.b();
        }
        if (!TextUtils.isEmpty(e)) {
            Matcher matcher = null;
            try {
                matcher = Pattern.compile("i686|mips|x86_64").matcher(e);
            } catch (Exception e) {
            }
            if (matcher != null && matcher.find()) {
                return false;
            }
        }
        JSONObject b = b(z);
        try {
            i = b.getInt("TBSV");
        } catch (Exception e2) {
            i = -1;
        }
        if (i != -1) {
            try {
                a = a(j.a(o.a(c).c(), b.toString().getBytes("utf-8"), new y(), false), i);
            } catch (Throwable th) {
                th.printStackTrace();
            }
            return a;
        }
        a = false;
        return a;
    }

    private static boolean d() {
        return true;
    }

    public static synchronized boolean getOverSea(Context context) {
        boolean z;
        synchronized (TbsDownloader.class) {
            if (!i) {
                i = true;
                TbsDownloadConfig a = TbsDownloadConfig.a(context);
                if (a.b.contains(TbsConfigKey.KEY_IS_OVERSEA)) {
                    h = a.b.getBoolean(TbsConfigKey.KEY_IS_OVERSEA, false);
                    TbsLog.i(LOGTAG, "[TbsDownloader.getOverSea]  first called. sOverSea = " + h);
                }
                TbsLog.i(LOGTAG, "[TbsDownloader.getOverSea]  sOverSea = " + h);
            }
            z = h;
        }
        return z;
    }

    public static synchronized boolean isDownloading() {
        boolean z;
        synchronized (TbsDownloader.class) {
            TbsLog.i(LOGTAG, "[TbsDownloader.isDownloading]");
            z = a;
        }
        return z;
    }

    public static boolean needDownload(Context context, boolean z) {
        boolean z2 = false;
        TbsLog.app_extra(LOGTAG, context);
        if (VERSION.SDK_INT >= 8) {
            c = context.getApplicationContext();
            if (TbsShareManager.isThirdPartyApp(c) && !a()) {
                return false;
            }
            TbsDownloadConfig a = TbsDownloadConfig.a(c);
            if (!a.b.contains(TbsConfigKey.KEY_IS_OVERSEA)) {
                if (z && !"com.tencent.mm".equals(context.getApplicationInfo().packageName)) {
                    z = false;
                    TbsLog.i(LOGTAG, "needDownload-oversea is true, but not WX");
                }
                a.a.put(TbsConfigKey.KEY_IS_OVERSEA, Boolean.valueOf(z));
                a.b();
                h = z;
                TbsLog.i(LOGTAG, "needDownload-first-called--isoversea = " + z);
            }
            if (!getOverSea(context) || VERSION.SDK_INT == 16 || VERSION.SDK_INT == 17 || VERSION.SDK_INT == 18) {
                e = a.b.getString(TbsConfigKey.KEY_DEVICE_CPUABI, null);
                if (!TextUtils.isEmpty(e)) {
                    Matcher matcher = null;
                    try {
                        matcher = Pattern.compile("i686|mips|x86_64").matcher(e);
                    } catch (Exception e) {
                    }
                    if (matcher != null && matcher.find()) {
                        QbSdk.e.onCallBackErrorCode(1000, ErrorCode.INFO_ERROR_NOMATCH_CPU);
                        return false;
                    }
                }
                b();
                if (g) {
                    return false;
                }
                String string = a.b.getString(TbsConfigKey.KEY_APP_VERSIONNAME, null);
                int i = a.b.getInt(TbsConfigKey.KEY_APP_VERSIONCODE, 0);
                String string2 = a.b.getString(TbsConfigKey.KEY_APP_METADATA, null);
                String a2 = a.a(c);
                int b = a.b(c);
                String a3 = a.a(c, "com.tencent.mm.BuildInfo.CLIENT_VERSION");
                Object obj = null;
                if (System.currentTimeMillis() - a.b.getLong(TbsConfigKey.KEY_LAST_CHECK, 0) > 1000 * a.a()) {
                    obj = 1;
                } else if (!(a2 == null || b == 0 || a3 == null || (a2.equals(string) && b == i && a3.equals(string2)))) {
                    obj = 1;
                }
                if (obj != null) {
                    c();
                }
                d.removeMessages(102);
                Message.obtain(d, 102).sendToTarget();
                if (!TbsShareManager.isThirdPartyApp(context)) {
                    z2 = a.b.contains(TbsConfigKey.KEY_NEEDDOWNLOAD);
                    TbsLog.i(LOGTAG, "[TbsDownloader.needDownload] hasNeedDownloadKey=" + z2);
                    z2 = (z2 || TbsShareManager.isThirdPartyApp(context)) ? a.b.getBoolean(TbsConfigKey.KEY_NEEDDOWNLOAD, false) : true;
                }
                if (z2) {
                    if (!d()) {
                        z2 = false;
                    }
                } else if (obj != null) {
                    d.removeMessages(103);
                    Message.obtain(d, 103, c).sendToTarget();
                }
            } else {
                TbsLog.i(LOGTAG, "needDownload- return false,  because of  version is " + VERSION.SDK_INT + ", and overea");
                return false;
            }
        }
        TbsLog.i(LOGTAG, "[TbsDownloader.needDownload] needDownload=" + z2);
        return z2;
    }

    public static synchronized void startDownload(Context context) {
        synchronized (TbsDownloader.class) {
        }
    }

    public static void stopDownload() {
    }
}
