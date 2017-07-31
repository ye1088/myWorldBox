package com.tencent.smtt.sdk;

import android.content.Context;
import android.os.Build.VERSION;
import com.tencent.smtt.sdk.TbsListener.ErrorCode;
import com.tencent.smtt.utils.TbsLog;
import java.io.File;

class i {
    static boolean a = false;
    private static i c = null;
    private static int f = 0;
    private static int g = 0;
    private static int h = 3;
    private static String j = null;
    private af b = null;
    private boolean d = false;
    private boolean e = false;
    private File i = null;

    private i() {
    }

    public static i a(boolean z) {
        if (c == null && z) {
            c = new i();
        }
        return c;
    }

    static void a(int i) {
        f = i;
    }

    private void b(Context context, boolean z) {
        if (!QbSdk.a || z || !this.d) {
            this.d = false;
            if (z) {
                TbsLog.w("SDKEngine", "useSystemWebView -> QbSdk.forceSysWebViewInner");
                QbSdk.a();
            }
            if (f != 0 && !this.d) {
                TbsCoreLoadStat.getInstance().a(context, 405);
            }
        }
    }

    public static int d() {
        return f;
    }

    private static boolean h() {
        return VERSION.SDK_INT >= 7;
    }

    public af a() {
        return QbSdk.a ? null : this.b;
    }

    public synchronized void a(Context context) {
        a(context, false, false);
    }

    public synchronized void a(Context context, boolean z) {
        a(context, z, false);
    }

    public synchronized void a(Context context, boolean z, boolean z2) {
        Context context2 = null;
        int i = 0;
        boolean z3 = true;
        synchronized (this) {
            g++;
            TbsCoreLoadStat.getInstance().a();
            boolean a = QbSdk.a(context, z, z2);
            boolean h = h();
            if (!h) {
                TbsLog.e(TbsListener.tag_load_error, "320");
                QbSdk.e.onCallBackErrorCode(320, ErrorCode.INFO_ERROR_SDKENGINE_ISCOMPATIBLE);
            }
            if (!(a && h)) {
                z3 = false;
            }
            TbsLog.i("SDKEngine", "init canLoadTbs=" + z3);
            if (!z3) {
                TbsLog.e("SDKEngine", "init failure: can_load_x5=" + a + "; is_compatible=" + h + "; isTbsCoreLegaL=" + true);
            }
            if (!z3) {
                TbsLog.e("SDKEngine", "useSystemWebView by !canLoadTbs");
                TbsLog.e(TbsListener.tag_load_error, "324");
                QbSdk.e.onCallBackErrorCode(324, ErrorCode.INFO_SDKENGINE_CANLOADTBS);
                b(context, false);
                aa.a();
                this.i = aa.g(context);
                this.e = true;
            } else if (!this.d) {
                try {
                    File file;
                    File file2;
                    if (!TbsShareManager.isThirdPartyApp(context)) {
                        file = null;
                        file2 = null;
                    } else if (TbsShareManager.g(context)) {
                        file2 = new File(TbsShareManager.a(context));
                        file = aa.a().e(context);
                        context2 = TbsShareManager.c(context);
                        if (file == null) {
                            TbsLog.e("SDKEngine", "useSystemWebView by ERROR_TBSCORE_DEXOPT_DIR");
                            TbsCoreLoadStat.getInstance().a(context, 311);
                            QbSdk.e.onCallBackErrorCode(311, ErrorCode.INFO_ERROR_TBSCORE_DEXOPT_DIR);
                            b(context, true);
                        }
                    } else {
                        TbsLog.e("SDKEngine", "useSystemWebView by ERROR_HOST_UNAVAILABLE");
                        TbsCoreLoadStat.getInstance().a(context, 304);
                        QbSdk.e.onCallBackErrorCode(304, ErrorCode.INFO_ERROR_HOST_UNAVAILABLE);
                        b(context, true);
                    }
                    String[] dexLoaderFileList = QbSdk.getDexLoaderFileList(context, context2, file2.getAbsolutePath());
                    while (i < dexLoaderFileList.length) {
                        i++;
                    }
                    this.b = new af(context, context2, file2.getAbsolutePath(), file.getAbsolutePath(), dexLoaderFileList);
                    this.d = true;
                } catch (Throwable th) {
                    TbsLog.e("SDKEngine", "useSystemWebView by exception: " + th);
                    if (th == null) {
                        TbsCoreLoadStat.getInstance().a(context, 326);
                    } else {
                        TbsCoreLoadStat.getInstance().a(context, 327, th);
                    }
                    QbSdk.e.onCallBackErrorCode(310, ErrorCode.INFO_THROWABLE_LOAD_TBS);
                    b(context, true);
                }
                aa.a();
                this.i = aa.g(context);
                this.e = true;
            }
        }
    }

    public af b(boolean z) {
        return z ? this.b : QbSdk.a ? null : this.b;
    }

    public boolean b() {
        return QbSdk.a ? false : this.d;
    }

    af c() {
        return this.b;
    }

    public String e() {
        return (this.b == null || QbSdk.a) ? "system webview get nothing..." : this.b.m();
    }

    boolean f() {
        return this.e;
    }

    public boolean g() {
        return QbSdk.useSoftWare();
    }
}
