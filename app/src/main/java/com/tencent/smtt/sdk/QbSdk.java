package com.tencent.smtt.sdk;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebIconDatabase;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewDatabase;
import android.widget.Toast;
import com.tencent.smtt.export.external.DexLoader;
import com.tencent.smtt.sdk.TbsDownloadConfig.TbsConfigKey;
import com.tencent.smtt.sdk.TbsListener.ErrorCode;
import com.tencent.smtt.sdk.a.d;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.TbsLogClient;
import com.tencent.smtt.utils.a;
import com.tencent.smtt.utils.h;
import com.tencent.smtt.utils.k;
import com.tencent.smtt.utils.m;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;

@SuppressLint({"NewApi"})
public class QbSdk {
    public static final String LOGIN_TYPE_KEY_PARTNER_CALL_POS = "PosID";
    public static final String LOGIN_TYPE_KEY_PARTNER_ID = "ChannelID";
    public static final String PARAM_KEY_FEATUREID = "param_key_featureid";
    public static final String PARAM_KEY_FUNCTIONID = "param_key_functionid";
    public static final String PARAM_KEY_POSITIONID = "param_key_positionid";
    public static final int SVNVERSION = 382481;
    public static final String TID_QQNumber_Prefix = "QQ:";
    public static final int VERSION = 1;
    static boolean a = false;
    static boolean b = false;
    static boolean c = false;
    static volatile boolean d = a;
    static TbsListener e = new g();
    private static int f;
    private static String g = "";
    private static Class<?> h;
    private static Object i;
    private static boolean j = false;
    private static String[] k;
    private static String l = null;
    private static String m = null;
    public static boolean mTbsDebugInstallOnline = false;
    public static TbsListener mTbsListenerDebug = null;
    private static boolean n = true;
    private static TbsListener o = null;
    private static boolean p = false;
    private static boolean q = false;
    public static boolean sIsVersionPrinted = false;

    public interface PreInitCallback {
        void onCoreInitFinished();

        void onViewInitFinished();
    }

    static void a() {
        a = true;
        TbsLog.e("QbSdk", "sys WebView: SysWebViewForcedInner");
    }

    private static boolean a(Context context) {
        try {
            if (h != null) {
                return true;
            }
            File e = aa.a().e(context);
            if (e == null) {
                e.onCallBackErrorCode(311, ErrorCode.INFO_ERROR_TBSCORE_DEXOPT_DIR);
                TbsLog.e("QbSdk", "QbSdk initForX5DisableConfig (false) optDir == null");
                return false;
            }
            File file = null;
            if (TbsShareManager.isThirdPartyApp(context)) {
                if (TbsShareManager.g(context)) {
                    file = new File(TbsShareManager.a(context), "tbs_sdk_extension_dex.jar");
                } else {
                    TbsCoreLoadStat.getInstance().a(context, ErrorCode.ERROR_HOSTAPP_UNAVAILABLE);
                    e.onCallBackErrorCode(ErrorCode.ERROR_HOSTAPP_UNAVAILABLE, ErrorCode.INFO_ERROR_HOST_UNAVAILABLE);
                    return false;
                }
            }
            if (file.exists()) {
                h = new DexClassLoader(file.getAbsolutePath(), e.getAbsolutePath(), null, QbSdk.class.getClassLoader()).loadClass("com.tencent.tbs.sdk.extension.TbsSDKExtension");
                Constructor constructor = h.getConstructor(new Class[]{Context.class, Context.class});
                if (TbsShareManager.isThirdPartyApp(context)) {
                    i = constructor.newInstance(new Object[]{context, TbsShareManager.c(context)});
                }
                m.a(i, "putInfo", new Class[]{String.class, String.class, String.class, String.class}, new Object[]{a.a, a.b, a.c, a.d});
                m.a(i, "setClientVersion", new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(1)});
                return true;
            }
            e.onCallBackErrorCode(311, ErrorCode.INFO_ERROR_TBSCORE_DEXOPT_DIR);
            return false;
        } catch (Throwable th) {
            TbsLog.e("QbSdk", "initForX5DisableConfig sys WebView: " + Log.getStackTraceString(th));
            return false;
        }
    }

    static boolean a(Context context, int i) {
        return a(context, i, 20000);
    }

    static boolean a(Context context, int i, int i2) {
        if (!a(context)) {
            return true;
        }
        Object a = m.a(i, "isX5Disabled", new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE}, new Object[]{Integer.valueOf(i), Integer.valueOf(36510), Integer.valueOf(i2)});
        if (a != null) {
            return ((Boolean) a).booleanValue();
        }
        a = m.a(i, "isX5Disabled", new Class[]{Integer.TYPE, Integer.TYPE}, new Object[]{Integer.valueOf(i), Integer.valueOf(36510)});
        return a != null ? ((Boolean) a).booleanValue() : true;
    }

    @SuppressLint({"NewApi"})
    private static boolean a(Context context, boolean z) {
        File file = null;
        if (!sIsVersionPrinted) {
            TbsLog.i("QbSdk", "svn revision: 382481; SDK_VERSION_CODE: 36510; SDK_VERSION_NAME: 2.1.2.1084");
            sIsVersionPrinted = true;
        }
        if (a && !z) {
            TbsLog.e("QbSdk", "QbSdk init mIsSysWebViewForced = true", false);
            TbsCoreLoadStat.getInstance().a(context, 401);
            e.onCallBackErrorCode(401, ErrorCode.FORCE_SYSTEM_WEBVIEW_INNER);
            return false;
        } else if (b) {
            TbsLog.e("QbSdk", "QbSdk init mIsSysWebViewForcedByOuter = true", true);
            TbsCoreLoadStat.getInstance().a(context, 402);
            e.onCallBackErrorCode(402, ErrorCode.FORCE_SYSTEM_WEBVIEW_OUTER);
            return false;
        } else {
            if (!n) {
                c(context);
            }
            try {
                File e = aa.a().e(context);
                if (e == null) {
                    TbsLog.e("QbSdk", "QbSdk init (false) optDir == null");
                    TbsCoreLoadStat.getInstance().a(context, 312);
                    return false;
                }
                if (TbsShareManager.isThirdPartyApp(context)) {
                    if (f == 0 || f == TbsShareManager.b(context)) {
                        f = TbsShareManager.b(context);
                    } else {
                        h = null;
                        i = null;
                        TbsLog.e("QbSdk", "QbSdk init (false) isThirdPartyApp");
                        TbsCoreLoadStat.getInstance().a(context, 302);
                        return false;
                    }
                }
                if (h != null) {
                    return true;
                }
                if (TbsShareManager.isThirdPartyApp(context)) {
                    if (TbsShareManager.g(context)) {
                        file = new File(TbsShareManager.a(context), "tbs_sdk_extension_dex.jar");
                    } else {
                        TbsCoreLoadStat.getInstance().a(context, 304);
                        e.onCallBackErrorCode(304, ErrorCode.INFO_ERROR_HOST_UNAVAILABLE);
                        return false;
                    }
                }
                if (file.exists()) {
                    h = new DexClassLoader(file.getAbsolutePath(), e.getAbsolutePath(), null, QbSdk.class.getClassLoader()).loadClass("com.tencent.tbs.sdk.extension.TbsSDKExtension");
                    Constructor constructor = h.getConstructor(new Class[]{Context.class, Context.class});
                    if (TbsShareManager.isThirdPartyApp(context)) {
                        i = constructor.newInstance(new Object[]{context, TbsShareManager.c(context)});
                    }
                    m.a(i, "putInfo", new Class[]{String.class, String.class, String.class, String.class}, new Object[]{a.a, a.b, a.c, a.d});
                    m.a(i, "setClientVersion", new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(1)});
                    return true;
                }
                TbsCoreLoadStat.getInstance().a(context, 403);
                e.onCallBackErrorCode(403, ErrorCode.METHOD_MISS_SDKEXTENSION_JAR);
                return false;
            } catch (Throwable e2) {
                TbsLog.e("QbSdk", "QbSdk init Exception: " + Log.getStackTraceString(e2));
                TbsCoreLoadStat.getInstance().a(context, 305, e2);
                e.onCallBackErrorCode(305, ErrorCode.INFO_EXCEPTION_QBSDK_INIT);
                TbsLog.e(TbsListener.tag_load_error, "316");
                return false;
            } catch (Throwable e22) {
                TbsLog.e("QbSdk", "QbSdk init Throwable: " + Log.getStackTraceString(e22));
                TbsCoreLoadStat.getInstance().a(context, 306, e22);
                e.onCallBackErrorCode(306, ErrorCode.INFO_EXCEPTION_QBSDK_INIT);
                TbsLog.e(TbsListener.tag_load_error, "316");
                return false;
            }
        }
    }

    static boolean a(Context context, boolean z, boolean z2) {
        int i = 1;
        boolean z3 = false;
        if (TbsShareManager.isThirdPartyApp(context) && !TbsShareManager.f(context)) {
            TbsCoreLoadStat.getInstance().a(context, 302);
        } else if (a(context, z)) {
            Object a = m.a(i, "canLoadX5Core", new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(36510)});
            boolean a2;
            if (a == null) {
                a = m.a(i, "canLoadX5", new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(a.a())});
                if (a == null) {
                    TbsCoreLoadStat.getInstance().a(context, 308);
                    e.onCallBackErrorCode(308, ErrorCode.INFO_ERROR_CANLOADX5_RETURN_NULL);
                } else if ((a instanceof String) && ((String) a).equalsIgnoreCase("AuthenticationFail")) {
                    if (!z2) {
                        Toast.makeText(context, "Authentication fail, use sys webview, please contact tencent ", 1).show();
                    }
                    TbsLog.e(TbsListener.tag_load_error, "317");
                    e.onCallBackErrorCode(317, ErrorCode.INFO_ERROR_AUTHENTICATION);
                } else if (a instanceof Boolean) {
                    a2 = a(context, i.d());
                    f = i.d();
                    if (((Boolean) a).booleanValue() && !a2) {
                        z3 = true;
                    }
                    if (!z3) {
                        TbsLog.e(TbsListener.tag_load_error, "318");
                        TbsLog.w(TbsListener.tag_load_error, "isX5Disable:" + a2);
                        TbsLog.w(TbsListener.tag_load_error, "(Boolean) ret:" + ((Boolean) a));
                    }
                }
            } else if ((a instanceof String) && ((String) a).equalsIgnoreCase("AuthenticationFail")) {
                if (!z2) {
                    Toast.makeText(context, "Authentication fail, use sys webview, please contact tencent ", 1).show();
                }
                e.onCallBackErrorCode(317, ErrorCode.INFO_ERROR_AUTHENTICATION);
                TbsLog.e(TbsListener.tag_load_error, "317");
            } else if (a instanceof Bundle) {
                Bundle bundle = (Bundle) a;
                int i2 = bundle.getInt("result_code", -1);
                a2 = i2 == 0;
                if (TbsShareManager.isThirdPartyApp(context)) {
                    i.a(TbsShareManager.b(context));
                    g = String.valueOf(TbsShareManager.b(context));
                    if (g.length() == 5) {
                        g = "0" + g;
                    }
                    if (g.length() != 6) {
                        g = "";
                    }
                } else {
                    if (VERSION.SDK_INT >= 12) {
                        g = bundle.getString("tbs_core_version", "0");
                    } else {
                        g = bundle.getString("tbs_core_version");
                        if (g == null) {
                            g = "0";
                        }
                    }
                    try {
                        f = Integer.parseInt(g);
                    } catch (NumberFormatException e) {
                        f = 0;
                    }
                    i.a(f);
                    if ((f <= 0 || f > 25438) && f != 25472) {
                        i = 0;
                    }
                    if (i != 0) {
                        h.a(aa.a().e(context));
                        TbsCoreLoadStat.getInstance().a(context, 307);
                        TbsLog.e(TbsListener.tag_load_error, "is_obsolete -- delete 319");
                        e.onCallBackErrorCode(ErrorCode.ERROR_COREVERSION_TOOLOW, ErrorCode.INFO_ERROR_COREVERSION_TOOLOW);
                    }
                }
                try {
                    k = bundle.getStringArray("tbs_jarfiles");
                    switch (i2) {
                        case -2:
                            if (TbsShareManager.isThirdPartyApp(context)) {
                                TbsCoreLoadStat.getInstance().a(context, 404);
                            }
                            e.onCallBackErrorCode(404, ErrorCode.DISABLE_X5);
                            break;
                        case -1:
                            a = null;
                            try {
                                a = m.a(i, "getErrorCodeForLogReport", new Class[0], new Object[0]);
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                            if (!(a instanceof Integer) || ((Integer) a).intValue() == -1) {
                                TbsCoreLoadStat.getInstance().a(context, 307);
                            } else {
                                TbsCoreLoadStat.getInstance().a(context, ((Integer) a).intValue());
                            }
                            e.onCallBackErrorCode(307, ErrorCode.INFO_ERROR_CANLOADX5_RETURN_FALSE);
                            break;
                    }
                    z3 = a2;
                } catch (Throwable th) {
                    TbsCoreLoadStat.getInstance().a(context, 329, th);
                }
            } else {
                e.onCallBackErrorCode(330, ErrorCode.INFO_ERROR_QBSDK_INIT_ERROR_RET_TYPE_NOT_BUNDLE);
                TbsCoreLoadStat.getInstance().a(context, 330, new Throwable("" + a));
                TbsLog.e(TbsListener.tag_load_error, "ret not instance of bundle");
            }
            if (!z3) {
                TbsLog.e(TbsListener.tag_load_error, "319");
                e.onCallBackErrorCode(319, ErrorCode.INFO_ERROR_QBSDK_INIT_CANLOADX5);
            }
        } else {
            TbsLog.e(TbsListener.tag_load_error, "315");
            e.onCallBackErrorCode(315, ErrorCode.INFO_ERROR_QBSDK_INIT);
        }
        return z3;
    }

    static String b() {
        return g;
    }

    private static boolean b(Context context) {
        return true;
    }

    private static void c(Context context) {
        int i;
        Throwable th;
        File a;
        int i2 = -1;
        n = true;
        SharedPreferences sharedPreferences;
        int i3;
        int b;
        Editor edit;
        try {
            sharedPreferences = VERSION.SDK_INT >= 11 ? context.getSharedPreferences("tbs_preloadx5_check_cfg_file", 4) : context.getSharedPreferences("tbs_preloadx5_check_cfg_file", 0);
            try {
                i3 = sharedPreferences.getInt("tbs_preload_x5_recorder", -1);
                if (i3 >= 0) {
                    i3++;
                    if (i3 <= 4) {
                        i = i3;
                    } else {
                        return;
                    }
                }
                i = i3;
                i3 = -1;
                try {
                    b = aa.a().b(context);
                    if (b > 0) {
                        if (i <= 4) {
                            try {
                                sharedPreferences.edit().putInt("tbs_preload_x5_recorder", i).commit();
                            } catch (Throwable th2) {
                                th = th2;
                                TbsLog.e("QbSdk", "tbs_preload_x5_counter Inc exception:" + Log.getStackTraceString(th));
                                i = -1;
                                if (i <= 3) {
                                    TbsLog.i("QbSdk", "QbSdk - preload_x5_check -- before creation!");
                                    i.a(true).a(context);
                                    TbsLog.i("QbSdk", "QbSdk - preload_x5_check -- after creation!");
                                    i2 = 0;
                                    try {
                                        i = sharedPreferences.getInt("tbs_preload_x5_counter", -1);
                                        if (i > 0) {
                                            sharedPreferences.edit().putInt("tbs_preload_x5_counter", i - 1).commit();
                                        }
                                    } catch (Throwable th3) {
                                        TbsLog.e("QbSdk", "tbs_preload_x5_counter Dec exception:" + Log.getStackTraceString(th3));
                                    }
                                    TbsLog.i("QbSdk", "QbSdk -- preload_x5_check result:" + i2);
                                }
                                try {
                                    i = sharedPreferences.getInt("tbs_preload_x5_version", -1);
                                    edit = sharedPreferences.edit();
                                    if (i != b) {
                                        TbsLog.e("QbSdk", "QbSdk - preload_x5_check -- reset exception core_ver:" + b + "; value:" + i);
                                    } else {
                                        h.a(aa.a().e(context), false);
                                        a = w.a(context).a();
                                        if (a != null) {
                                            h.a(a, false);
                                        }
                                        TbsLog.e("QbSdk", "QbSdk - preload_x5_check: tbs core " + b + " is deleted!");
                                    }
                                    edit.putInt("tbs_precheck_disable_version", i);
                                    edit.commit();
                                } catch (Throwable th32) {
                                    TbsLog.e("QbSdk", "tbs_preload_x5_counter disable version exception:" + Log.getStackTraceString(th32));
                                    return;
                                }
                            }
                        }
                        i = sharedPreferences.getInt("tbs_preload_x5_counter", -1);
                        if (i >= 0) {
                            i++;
                            sharedPreferences.edit().putInt("tbs_preload_x5_counter", i).commit();
                            if (i <= 3) {
                                i = sharedPreferences.getInt("tbs_preload_x5_version", -1);
                                edit = sharedPreferences.edit();
                                if (i != b) {
                                    h.a(aa.a().e(context), false);
                                    a = w.a(context).a();
                                    if (a != null) {
                                        h.a(a, false);
                                    }
                                    TbsLog.e("QbSdk", "QbSdk - preload_x5_check: tbs core " + b + " is deleted!");
                                } else {
                                    TbsLog.e("QbSdk", "QbSdk - preload_x5_check -- reset exception core_ver:" + b + "; value:" + i);
                                }
                                edit.putInt("tbs_precheck_disable_version", i);
                                edit.commit();
                            }
                            if (i3 > 0 && i3 <= 3) {
                                TbsLog.i("QbSdk", "QbSdk - preload_x5_check -- before creation!");
                                i.a(true).a(context);
                                TbsLog.i("QbSdk", "QbSdk - preload_x5_check -- after creation!");
                                i2 = 0;
                            }
                            i = sharedPreferences.getInt("tbs_preload_x5_counter", -1);
                            if (i > 0) {
                                sharedPreferences.edit().putInt("tbs_preload_x5_counter", i - 1).commit();
                            }
                            TbsLog.i("QbSdk", "QbSdk -- preload_x5_check result:" + i2);
                        }
                        i = -1;
                        if (i <= 3) {
                            TbsLog.i("QbSdk", "QbSdk - preload_x5_check -- before creation!");
                            i.a(true).a(context);
                            TbsLog.i("QbSdk", "QbSdk - preload_x5_check -- after creation!");
                            i2 = 0;
                            i = sharedPreferences.getInt("tbs_preload_x5_counter", -1);
                            if (i > 0) {
                                sharedPreferences.edit().putInt("tbs_preload_x5_counter", i - 1).commit();
                            }
                            TbsLog.i("QbSdk", "QbSdk -- preload_x5_check result:" + i2);
                        }
                        i = sharedPreferences.getInt("tbs_preload_x5_version", -1);
                        edit = sharedPreferences.edit();
                        if (i != b) {
                            TbsLog.e("QbSdk", "QbSdk - preload_x5_check -- reset exception core_ver:" + b + "; value:" + i);
                        } else {
                            h.a(aa.a().e(context), false);
                            a = w.a(context).a();
                            if (a != null) {
                                h.a(a, false);
                            }
                            TbsLog.e("QbSdk", "QbSdk - preload_x5_check: tbs core " + b + " is deleted!");
                        }
                        edit.putInt("tbs_precheck_disable_version", i);
                        edit.commit();
                    }
                } catch (Throwable th4) {
                    th32 = th4;
                    b = -1;
                    TbsLog.e("QbSdk", "tbs_preload_x5_counter Inc exception:" + Log.getStackTraceString(th32));
                    i = -1;
                    if (i <= 3) {
                        i = sharedPreferences.getInt("tbs_preload_x5_version", -1);
                        edit = sharedPreferences.edit();
                        if (i != b) {
                            h.a(aa.a().e(context), false);
                            a = w.a(context).a();
                            if (a != null) {
                                h.a(a, false);
                            }
                            TbsLog.e("QbSdk", "QbSdk - preload_x5_check: tbs core " + b + " is deleted!");
                        } else {
                            TbsLog.e("QbSdk", "QbSdk - preload_x5_check -- reset exception core_ver:" + b + "; value:" + i);
                        }
                        edit.putInt("tbs_precheck_disable_version", i);
                        edit.commit();
                    }
                    TbsLog.i("QbSdk", "QbSdk - preload_x5_check -- before creation!");
                    i.a(true).a(context);
                    TbsLog.i("QbSdk", "QbSdk - preload_x5_check -- after creation!");
                    i2 = 0;
                    i = sharedPreferences.getInt("tbs_preload_x5_counter", -1);
                    if (i > 0) {
                        sharedPreferences.edit().putInt("tbs_preload_x5_counter", i - 1).commit();
                    }
                    TbsLog.i("QbSdk", "QbSdk -- preload_x5_check result:" + i2);
                }
            } catch (Throwable th5) {
                th32 = th5;
                i3 = -1;
                b = -1;
                TbsLog.e("QbSdk", "tbs_preload_x5_counter Inc exception:" + Log.getStackTraceString(th32));
                i = -1;
                if (i <= 3) {
                    TbsLog.i("QbSdk", "QbSdk - preload_x5_check -- before creation!");
                    i.a(true).a(context);
                    TbsLog.i("QbSdk", "QbSdk - preload_x5_check -- after creation!");
                    i2 = 0;
                    i = sharedPreferences.getInt("tbs_preload_x5_counter", -1);
                    if (i > 0) {
                        sharedPreferences.edit().putInt("tbs_preload_x5_counter", i - 1).commit();
                    }
                    TbsLog.i("QbSdk", "QbSdk -- preload_x5_check result:" + i2);
                }
                i = sharedPreferences.getInt("tbs_preload_x5_version", -1);
                edit = sharedPreferences.edit();
                if (i != b) {
                    TbsLog.e("QbSdk", "QbSdk - preload_x5_check -- reset exception core_ver:" + b + "; value:" + i);
                } else {
                    h.a(aa.a().e(context), false);
                    a = w.a(context).a();
                    if (a != null) {
                        h.a(a, false);
                    }
                    TbsLog.e("QbSdk", "QbSdk - preload_x5_check: tbs core " + b + " is deleted!");
                }
                edit.putInt("tbs_precheck_disable_version", i);
                edit.commit();
            }
        } catch (Throwable th6) {
            th32 = th6;
            sharedPreferences = null;
            b = -1;
            i3 = -1;
            TbsLog.e("QbSdk", "tbs_preload_x5_counter Inc exception:" + Log.getStackTraceString(th32));
            i = -1;
            if (i <= 3) {
                i = sharedPreferences.getInt("tbs_preload_x5_version", -1);
                edit = sharedPreferences.edit();
                if (i != b) {
                    h.a(aa.a().e(context), false);
                    a = w.a(context).a();
                    if (a != null) {
                        h.a(a, false);
                    }
                    TbsLog.e("QbSdk", "QbSdk - preload_x5_check: tbs core " + b + " is deleted!");
                } else {
                    TbsLog.e("QbSdk", "QbSdk - preload_x5_check -- reset exception core_ver:" + b + "; value:" + i);
                }
                edit.putInt("tbs_precheck_disable_version", i);
                edit.commit();
            }
            TbsLog.i("QbSdk", "QbSdk - preload_x5_check -- before creation!");
            i.a(true).a(context);
            TbsLog.i("QbSdk", "QbSdk - preload_x5_check -- after creation!");
            i2 = 0;
            i = sharedPreferences.getInt("tbs_preload_x5_counter", -1);
            if (i > 0) {
                sharedPreferences.edit().putInt("tbs_preload_x5_counter", i - 1).commit();
            }
            TbsLog.i("QbSdk", "QbSdk -- preload_x5_check result:" + i2);
        }
    }

    public static boolean canLoadVideo(Context context) {
        Object a = m.a(i, "canLoadVideo", new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(0)});
        if (a == null) {
            TbsCoreLoadStat.getInstance().a(context, 314);
        } else if (!((Boolean) a).booleanValue()) {
            TbsCoreLoadStat.getInstance().a(context, 313);
        }
        return a == null ? false : ((Boolean) a).booleanValue();
    }

    public static void canOpenFile(Context context, String str, ValueCallback<Boolean> valueCallback) {
        new c(context, str, valueCallback).start();
    }

    public static boolean canOpenMimeFileType(Context context, String str) {
        return !a(context, false) ? false : false;
    }

    public static boolean canUseVideoFeatrue(Context context, int i) {
        Object a = m.a(i, "canUseVideoFeatrue", new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(i)});
        return (a == null || !(a instanceof Boolean)) ? false : ((Boolean) a).booleanValue();
    }

    public static void clear(Context context) {
    }

    public static void clearAllWebViewCache(Context context, boolean z) {
        try {
            new WebView(context).clearCache(true);
            if (z) {
                CookieSyncManager.createInstance(context);
                CookieManager.getInstance().removeAllCookie();
            }
            WebViewDatabase.getInstance(context).clearUsernamePassword();
            WebViewDatabase.getInstance(context).clearHttpAuthUsernamePassword();
            WebViewDatabase.getInstance(context).clearFormData();
            WebStorage.getInstance().deleteAllData();
            WebIconDatabase.getInstance().removeAllIcons();
        } catch (Throwable th) {
            TbsLog.e("QbSdk", "clearAllWebViewCache exception 1 -- " + Log.getStackTraceString(th));
        }
        try {
            if (new WebView(context).getWebViewClientExtension() != null) {
                i a = i.a(false);
                if (a != null && a.b()) {
                    a.a().a(context, z);
                }
            }
        } catch (Throwable th2) {
        }
    }

    public static void closeFileReader(Context context) {
        i a = i.a(true);
        a.a(context);
        if (a.b()) {
            a.a().n();
        }
    }

    public static boolean createMiniQBShortCut(Context context, String str, String str2, Drawable drawable) {
        if (context == null) {
            return false;
        }
        if (TbsDownloader.getOverSea(context)) {
            return false;
        }
        if (isMiniQBShortCutExist(context, str, str2)) {
            return false;
        }
        i a = i.a(false);
        if (a == null || !a.b()) {
            return false;
        }
        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        }
        DexLoader o = a.a().o();
        TbsLog.e("QbSdk", "qbsdk createMiniQBShortCut");
        Object invokeStaticMethod = o.invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "createMiniQBShortCut", new Class[]{Context.class, String.class, String.class, Bitmap.class}, new Object[]{context, str, str2, bitmap});
        TbsLog.e("QbSdk", "qbsdk after createMiniQBShortCut ret: " + invokeStaticMethod);
        return invokeStaticMethod != null;
    }

    public static boolean deleteMiniQBShortCut(Context context, String str, String str2) {
        if (context == null || TbsDownloader.getOverSea(context)) {
            return false;
        }
        i a = i.a(false);
        if (a == null || !a.b()) {
            return false;
        }
        return a.a().o().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "deleteMiniQBShortCut", new Class[]{Context.class, String.class, String.class}, new Object[]{context, str, str2}) != null;
    }

    public static void forceSysWebView() {
        b = true;
        TbsLog.e("QbSdk", "sys WebView: SysWebViewForcedByOuter");
    }

    public static long getApkFileSize(Context context) {
        return context != null ? TbsDownloadConfig.a(context.getApplicationContext()).b.getLong(TbsConfigKey.KEY_TBSAPKFILESIZE, 0) : 0;
    }

    public static String[] getDexLoaderFileList(Context context, Context context2, String str) {
        int i = 0;
        if (k != null) {
            int length = k.length;
            String[] strArr = new String[length];
            while (i < length) {
                strArr[i] = str + k[i];
                i++;
            }
            return strArr;
        }
        Object a = m.a(i, "getJarFiles", new Class[]{Context.class, Context.class, String.class}, new Object[]{context, context2, str});
        if (a == null) {
            a = "";
        }
        return (String[]) a;
    }

    public static boolean getDownloadWithoutWifi() {
        return p;
    }

    public static String getMiniQBVersion() {
        i a = i.a(false);
        return (a == null || !a.b()) ? null : a.a().d();
    }

    public static String getQQBuildNumber() {
        return m;
    }

    public static boolean getTBSInstalling() {
        return q;
    }

    public static String getTID() {
        return l;
    }

    public static int getTbsVersion(Context context) {
        b(context);
        return TbsShareManager.isThirdPartyApp(context) ? TbsShareManager.b(context) : aa.a().b(context);
    }

    public static String getX5CoreTimestamp() {
        Object a = m.a(h, "getX5CoreTimestamp", null, new Object[0]);
        return a == null ? "" : (String) a;
    }

    public static boolean installLocalQbApk(Context context, String str, String str2, Bundle bundle) {
        i a = i.a(false);
        return (a == null || !a.b()) ? false : a.a().a(context, str, str2, bundle);
    }

    public static boolean intentDispatch(WebView webView, Intent intent, String str, String str2) {
        if (webView == null) {
            return false;
        }
        if (str.startsWith("mttbrowser://miniqb/ch=icon?")) {
            Context context = webView.getContext();
            int indexOf = str.indexOf("url=");
            String substring = indexOf > 0 ? str.substring(indexOf + 4) : null;
            HashMap hashMap = new HashMap();
            Object obj = "unknown";
            try {
                obj = context.getApplicationInfo().packageName;
            } catch (Exception e) {
                e.printStackTrace();
            }
            hashMap.put(LOGIN_TYPE_KEY_PARTNER_ID, obj);
            hashMap.put(LOGIN_TYPE_KEY_PARTNER_CALL_POS, "14004");
            if (d.a(context, "miniqb://home".equals(substring) ? "qb://navicard/addCard?cardId=168&cardName=168" : substring, hashMap, "QbSdk.startMiniQBToLoadUrl") != 0) {
                i a = i.a(false);
                if (a != null && a.b() && a.a().a(context, substring, null, str2) == 0) {
                    return true;
                }
                webView.loadUrl(substring);
            }
        } else {
            webView.loadUrl(str);
        }
        return false;
    }

    public static boolean isMiniQBShortCutExist(Context context, String str, String str2) {
        if (context == null) {
            return false;
        }
        if (TbsDownloader.getOverSea(context)) {
            return false;
        }
        i a = i.a(false);
        if (a == null || !a.b()) {
            return false;
        }
        Object invokeStaticMethod = a.a().o().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "isMiniQBShortCutExist", new Class[]{Context.class, String.class}, new Object[]{context, str});
        if (invokeStaticMethod == null) {
            return false;
        }
        return (invokeStaticMethod instanceof Boolean ? (Boolean) invokeStaticMethod : Boolean.valueOf(false)).booleanValue();
    }

    public static boolean isSdkVideoServiceFg(Context context) {
        try {
            List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getApplicationContext().getSystemService("activity")).getRunningAppProcesses();
            if (runningAppProcesses == null || runningAppProcesses.size() == 0) {
                return false;
            }
            for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.importance == 100 && runningAppProcessInfo.processName != null && runningAppProcessInfo.processName.contains("com.tencent.mtt:VideoService")) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            TbsLog.e("QbSdk", "isSdkVideoServiceFg Exception: " + Log.getStackTraceString(th));
        }
    }

    public static boolean isTbsCoreInited() {
        i a = i.a(false);
        return a != null && a.f();
    }

    public static boolean isX5DisabledSync(Context context) {
        if (w.a(context).b() == 2) {
            int i = 1;
        } else {
            boolean z = false;
        }
        if (i != 0) {
            return false;
        }
        if (!a(context)) {
            return true;
        }
        i = aa.a().b(context);
        Object a = m.a(i, "isX5DisabledSync", new Class[]{Integer.TYPE, Integer.TYPE}, new Object[]{Integer.valueOf(i), Integer.valueOf(36510)});
        return a != null ? ((Boolean) a).booleanValue() : true;
    }

    public static synchronized void preInit(Context context) {
        synchronized (QbSdk.class) {
            preInit(context, null);
        }
    }

    public static synchronized void preInit(Context context, PreInitCallback preInitCallback) {
        synchronized (QbSdk.class) {
            d = a;
            if (!j) {
                new f(context, new e(Looper.getMainLooper(), context, preInitCallback)).start();
                j = true;
            }
        }
    }

    public static void reset(Context context) {
    }

    public static void setCurrentID(String str) {
        if (str != null && str.startsWith(TID_QQNumber_Prefix)) {
            String substring = str.substring(TID_QQNumber_Prefix.length());
            l = "0000000000000000".substring(substring.length()) + substring;
        }
    }

    public static void setDeviceInfo(String str, String str2, String str3, String str4) {
        a.a = str;
        a.b = str2;
        a.c = str3;
        a.d = str4;
    }

    public static void setDownloadWithoutWifi(boolean z) {
        p = z;
    }

    public static void setQQBuildNumber(String str) {
        m = str;
    }

    public static void setTBSInstallingStatus(boolean z) {
        q = z;
    }

    public static void setTbsListener(TbsListener tbsListener) {
        o = tbsListener;
    }

    public static void setTbsLogClient(TbsLogClient tbsLogClient) {
        TbsLog.setTbsLogClient(tbsLogClient);
    }

    public static int startMiniQBToLoadUrl(Context context, String str, HashMap<String, String> hashMap) {
        if (context == null) {
            return -100;
        }
        i a = i.a(true);
        a.a(context);
        return a.b() ? (context == null || !context.getApplicationInfo().packageName.equals("com.nd.android.pandahome2") || getTbsVersion(context) >= 25487) ? a.a().a(context, str, hashMap, null) : -101 : -102;
    }

    public static boolean startQBForDoc(Context context, String str, int i, int i2, String str2) {
        return startQBForDoc(context, str, i, i2, str2, null);
    }

    public static boolean startQBForDoc(Context context, String str, int i, int i2, String str2, Bundle bundle) {
        HashMap hashMap = new HashMap();
        hashMap.put(LOGIN_TYPE_KEY_PARTNER_ID, context.getApplicationContext().getApplicationInfo().processName);
        hashMap.put(LOGIN_TYPE_KEY_PARTNER_CALL_POS, Integer.toString(i));
        return d.a(context, str, i2, str2, hashMap, bundle);
    }

    public static boolean startQBForVideo(Context context, String str, int i) {
        HashMap hashMap = new HashMap();
        hashMap.put(LOGIN_TYPE_KEY_PARTNER_ID, context.getApplicationInfo().processName);
        hashMap.put(LOGIN_TYPE_KEY_PARTNER_CALL_POS, Integer.toString(i));
        return d.a(context, str, hashMap);
    }

    public static boolean startQBToLoadurl(Context context, String str, int i) {
        HashMap hashMap = new HashMap();
        hashMap.put(LOGIN_TYPE_KEY_PARTNER_ID, context.getApplicationInfo().processName);
        hashMap.put(LOGIN_TYPE_KEY_PARTNER_CALL_POS, Integer.toString(i));
        return d.a(context, str, hashMap, "QbSdk.startQBToLoadurl") == 0;
    }

    public static int startQBWeb(Context context, String str) {
        if (context == null) {
            return -1;
        }
        i a = i.a(false);
        if (a == null || !a.b()) {
            return -1;
        }
        Object invokeStaticMethod = a.a().o().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "startQBWeb", new Class[]{Context.class, String.class, String.class}, new Object[]{context, str, null});
        return invokeStaticMethod == null ? -3 : ((Integer) invokeStaticMethod).intValue();
    }

    public static boolean startQbOrMiniQBToLoadUrl(Context context, String str, HashMap<String, String> hashMap) {
        if (context == null) {
            return false;
        }
        String string;
        String string2;
        i a = i.a(true);
        a.a(context);
        String str2 = "QbSdk.startMiniQBToLoadUrl";
        if ("miniqb://home".equals(str)) {
            String str3 = "qb://navicard/addCard?cardId=168&cardName=168";
            if (a.b()) {
                Bundle bundle = (Bundle) a.a().o().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "getAdWebViewInfoFromX5Core", new Class[0], new Object[0]);
                if (bundle != null) {
                    string = bundle.getString("ad_webview_detail_url");
                    string2 = bundle.getString("ad_webview_click_stat_posid_for_qb");
                    if (!TextUtils.isEmpty(string)) {
                        if (hashMap != null) {
                            hashMap.put(LOGIN_TYPE_KEY_PARTNER_CALL_POS, string2);
                        }
                        str = string;
                    }
                }
            }
            string = str3;
        } else {
            string = str;
        }
        if ("miniqb://home".equals(str) && hashMap != null) {
            string2 = (String) hashMap.get("curUrlQQ");
            if (!TextUtils.isEmpty(string2)) {
                str = "http://adsolution.imtt.qq.com/index?p=tad&u=" + k.b(string2.getBytes());
                string = str;
            }
        }
        if (d.a(context, string, hashMap, str2) == 0) {
            return true;
        }
        if (a.b()) {
            if (context != null && context.getApplicationInfo().packageName.equals("com.nd.android.pandahome2") && getTbsVersion(context) < 25487) {
                return false;
            }
            if (a.a().a(context, str, hashMap, null) == 0) {
                return true;
            }
        }
        return false;
    }

    public static void unForceSysWebView() {
        b = false;
        TbsLog.e("QbSdk", "sys WebView: unForceSysWebView called");
    }

    public static boolean useSoftWare() {
        if (i == null) {
            return false;
        }
        Object a = m.a(i, "useSoftWare", new Class[0], new Object[0]);
        if (a == null) {
            a = m.a(i, "useSoftWare", new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(a.a())});
        }
        return a == null ? false : ((Boolean) a).booleanValue();
    }
}
