package com.tencent.smtt.sdk;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.net.http.SslCertificate;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.tencent.mm.sdk.platformtools.SpecilApiUtil;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebChromeClientExtension;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebSettingsExtension;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.FindListener;
import com.tencent.smtt.export.external.interfaces.IX5WebViewClient;
import com.tencent.smtt.sdk.TbsListener.ErrorCode;
import com.tencent.smtt.sdk.a.b;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.m;
import com.tencent.smtt.utils.p;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.channels.FileLock;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WebView extends FrameLayout implements OnLongClickListener {
    public static final int GETPVERROR = -1;
    public static int NIGHT_MODE_ALPHA = 153;
    public static final int NIGHT_MODE_COLOR = -16777216;
    public static final int NORMAL_MODE_ALPHA = 255;
    public static final String SCHEME_GEO = "geo:0,0?q=";
    public static final String SCHEME_MAILTO = "mailto:";
    public static final String SCHEME_TEL = "tel:";
    public static String TBS_DEBUG_INSTALL_ONLINE = "tbsdebug_install_online_";
    static boolean b = false;
    private static final Lock d = new ReentrantLock();
    private static OutputStream e = null;
    private static Context k = null;
    public static boolean mSysWebviewCreated = false;
    private static p o = null;
    private static Method p = null;
    private static String q = null;
    private static Paint w = null;
    private static boolean x = true;
    int a;
    private final String c;
    private boolean f;
    private IX5WebViewBase g;
    private a h;
    private WebSettings i;
    private Context j;
    private boolean l;
    private boolean m;
    public WebViewCallbackClient mWebViewCallbackClient;
    private WebViewClient n;
    private final int r;
    private final int s;
    private final int t;
    private final String u;
    private final String v;
    private Object y;
    private OnLongClickListener z;

    public static class HitTestResult {
        @Deprecated
        public static final int ANCHOR_TYPE = 1;
        public static final int EDIT_TEXT_TYPE = 9;
        public static final int EMAIL_TYPE = 4;
        public static final int GEO_TYPE = 3;
        @Deprecated
        public static final int IMAGE_ANCHOR_TYPE = 6;
        public static final int IMAGE_TYPE = 5;
        public static final int PHONE_TYPE = 2;
        public static final int SRC_ANCHOR_TYPE = 7;
        public static final int SRC_IMAGE_ANCHOR_TYPE = 8;
        public static final int UNKNOWN_TYPE = 0;
        private com.tencent.smtt.export.external.interfaces.IX5WebViewBase.HitTestResult a;
        private android.webkit.WebView.HitTestResult b;

        public HitTestResult() {
            this.b = null;
            this.a = null;
            this.b = null;
        }

        public HitTestResult(android.webkit.WebView.HitTestResult hitTestResult) {
            this.b = null;
            this.a = null;
            this.b = hitTestResult;
        }

        public HitTestResult(com.tencent.smtt.export.external.interfaces.IX5WebViewBase.HitTestResult hitTestResult) {
            this.b = null;
            this.a = hitTestResult;
            this.b = null;
        }

        public String getExtra() {
            return this.a != null ? this.a.getExtra() : this.b != null ? this.b.getExtra() : "";
        }

        public int getType() {
            return this.a != null ? this.a.getType() : this.b != null ? this.b.getType() : 0;
        }
    }

    public WebView(Context context) {
        this(context, null);
    }

    public WebView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WebView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, false);
    }

    @TargetApi(11)
    public WebView(Context context, AttributeSet attributeSet, int i, Map<String, Object> map, boolean z) {
        super(context, attributeSet, i);
        this.c = "WebView";
        this.f = false;
        this.i = null;
        this.j = null;
        this.a = 0;
        this.l = false;
        this.m = false;
        this.n = null;
        this.r = 1;
        this.s = 2;
        this.t = 3;
        this.u = "javascript:document.getElementsByTagName('HEAD').item(0).removeChild(document.getElementById('QQBrowserSDKNightMode'));";
        this.v = "javascript:var style = document.createElement('style');style.type='text/css';style.id='QQBrowserSDKNightMode';style.innerHTML='html,body{background:none !important;background-color: #1d1e2a !important;}html *{background-color: #1d1e2a !important; color:#888888 !important;border-color:#3e4f61 !important;text-shadow:none !important;box-shadow:none !important;}a_isRightVersion,a_isRightVersion *{border-color:#4c5b99 !important; color:#2d69b3 !important;text-decoration:none !important;}a_isRightVersion:visited,a_isRightVersion:visited *{color:#a600a6 !important;}a_isRightVersion:active,a_isRightVersion:active *{color:#5588AA !important;}input,select,textarea,option,button{background-image:none !important;color:#AAAAAA !important;border-color:#4c5b99 !important;}form,div,button,span{background-color:#1d1e2a !important; border-color:#4c5b99 !important;}img{opacity:0.5}';document.getElementsByTagName('HEAD').item(0).appendChild(style);";
        this.y = null;
        this.z = null;
        if (context == null) {
            b = this.f;
            throw new IllegalArgumentException("Invalid context argument");
        }
        if (o == null) {
            o = p.a(context);
        }
        if (o.a) {
            TbsLog.e("WebView", "sys WebView: debug.conf force syswebview", true);
            QbSdk.e.onCallBackErrorCode(ErrorCode.ERROR_FORCE_SYSWEBVIEW, ErrorCode.INFO_ERROR_FORCE_SYSWEBVIEW);
            QbSdk.a();
        }
        c(context);
        this.j = context;
        if (context != null) {
            k = context.getApplicationContext();
        }
        if (this.f) {
            this.g = i.a(true).a().a(context);
            if (this.g == null || this.g.getView() == null) {
                TbsLog.e("WebView", "sys WebView: failed to createTBSWebview", true);
                this.g = null;
                this.f = false;
                TbsCoreLoadStat.getInstance().a(context, 301);
                TbsCoreLoadStat.getInstance().a(context, 405);
                QbSdk.e.onCallBackErrorCode(301, ErrorCode.INFO_ERROR_FORCE_SYSTEM_WEBVIEW_INNER_FAILED_TO_CREATE);
                QbSdk.a();
                c(context);
                if (TbsShareManager.isThirdPartyApp(this.j)) {
                    this.h = new a(this, context, attributeSet);
                } else {
                    this.h = new a(this, context);
                }
                TbsLog.i("WebView", "SystemWebView Created Success! #1");
                this.h.setFocusableInTouchMode(true);
                addView(this.h, new LayoutParams(-1, -1));
                try {
                    if (VERSION.SDK_INT >= 11) {
                        removeJavascriptInterface("searchBoxJavaBridge_");
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
                b = this.f;
                return;
            }
            TbsLog.i("WebView", "X5 WebView Created Success!!");
            this.g.getView().setFocusableInTouchMode(true);
            a(attributeSet);
            addView(this.g.getView(), new LayoutParams(-1, -1));
            this.g.setDownloadListener(new b(this, null, this.f));
            this.g.getX5WebViewExtension().setWebViewClientExtension(new aj(this, i.a(false).b(true).i()));
        } else {
            this.g = null;
            this.f = false;
            QbSdk.a();
            if (TbsShareManager.isThirdPartyApp(this.j)) {
                this.h = new a(this, context, attributeSet);
            } else {
                this.h = new a(this, context);
            }
            TbsLog.i("WebView", "SystemWebView Created Success! #2");
            this.h.setFocusableInTouchMode(true);
            addView(this.h, new LayoutParams(-1, -1));
            setDownloadListener(null);
        }
        try {
            if (VERSION.SDK_INT >= 11) {
                removeJavascriptInterface("searchBoxJavaBridge_");
            }
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
        if (("com.tencent.mobileqq".equals(this.j.getApplicationInfo().packageName) || "com.tencent.mm".equals(this.j.getApplicationInfo().packageName)) && i.a(true).g() && VERSION.SDK_INT >= 11) {
            setLayerType(1, null);
        }
        b = this.f;
    }

    public WebView(Context context, AttributeSet attributeSet, int i, boolean z) {
        this(context, attributeSet, i, null, z);
    }

    private void a(AttributeSet attributeSet) {
        if (attributeSet != null) {
            try {
                int attributeCount = attributeSet.getAttributeCount();
                for (int i = 0; i < attributeCount; i++) {
                    if (attributeSet.getAttributeName(i).equalsIgnoreCase("scrollbars")) {
                        int[] intArray = getResources().getIntArray(16842974);
                        int attributeIntValue = attributeSet.getAttributeIntValue(i, -1);
                        if (attributeIntValue == intArray[1]) {
                            this.g.getView().setVerticalScrollBarEnabled(false);
                            this.g.getView().setHorizontalScrollBarEnabled(false);
                        } else if (attributeIntValue == intArray[2]) {
                            this.g.getView().setVerticalScrollBarEnabled(false);
                        } else if (attributeIntValue == intArray[3]) {
                            this.g.getView().setHorizontalScrollBarEnabled(false);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean a(View view) {
        if (this.j != null && getTbsCoreVersion(this.j) > 36200) {
            return false;
        }
        Object a = m.a(this.y, "onLongClick", new Class[]{View.class}, new Object[]{view});
        return a != null ? ((Boolean) a).booleanValue() : false;
    }

    private boolean b(Context context) {
        try {
            if (context.getPackageName().indexOf("com.tencent.mobileqq") >= 0) {
                return true;
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return false;
    }

    private void c(Context context) {
        i a = i.a(true);
        a.a(context);
        this.f = a.b();
    }

    private int d(Context context) {
        FileInputStream fileInputStream;
        Exception e;
        Throwable th;
        int i = -1;
        FileOutputStream h = aa.h(context);
        if (h != null) {
            FileLock a = aa.a(context, h);
            if (a != null) {
                if (d.tryLock()) {
                    FileInputStream fileInputStream2 = null;
                    try {
                        File file = new File(context.getDir("tbs", 0) + File.separator + "core_private", "pv.db");
                        if (file == null || !file.exists()) {
                            if (fileInputStream2 != null) {
                                try {
                                    fileInputStream2.close();
                                } catch (IOException e2) {
                                    TbsLog.e("getTbsCorePV", "TbsInstaller--getTbsCorePV IOException=" + e2.toString());
                                }
                            }
                            d.unlock();
                            aa.a(a, h);
                        } else {
                            Properties properties = new Properties();
                            fileInputStream = new FileInputStream(file);
                            try {
                                properties.load(fileInputStream);
                                fileInputStream.close();
                                String property = properties.getProperty("PV");
                                if (property == null) {
                                    if (fileInputStream != null) {
                                        try {
                                            fileInputStream.close();
                                        } catch (IOException e22) {
                                            TbsLog.e("getTbsCorePV", "TbsInstaller--getTbsCorePV IOException=" + e22.toString());
                                        }
                                    }
                                    d.unlock();
                                    aa.a(a, h);
                                } else {
                                    i = Integer.parseInt(property);
                                    if (fileInputStream != null) {
                                        try {
                                            fileInputStream.close();
                                        } catch (IOException e222) {
                                            TbsLog.e("getTbsCorePV", "TbsInstaller--getTbsCorePV IOException=" + e222.toString());
                                        }
                                    }
                                    d.unlock();
                                    aa.a(a, h);
                                }
                            } catch (Exception e3) {
                                e = e3;
                                try {
                                    TbsLog.e("getTbsCorePV", "TbsInstaller--getTbsCorePV Exception=" + e.toString());
                                    if (fileInputStream != null) {
                                        try {
                                            fileInputStream.close();
                                        } catch (IOException e2222) {
                                            TbsLog.e("getTbsCorePV", "TbsInstaller--getTbsCorePV IOException=" + e2222.toString());
                                        }
                                    }
                                    d.unlock();
                                    aa.a(a, h);
                                    return i;
                                } catch (Throwable th2) {
                                    th = th2;
                                    fileInputStream2 = fileInputStream;
                                    if (fileInputStream2 != null) {
                                        try {
                                            fileInputStream2.close();
                                        } catch (IOException e22222) {
                                            TbsLog.e("getTbsCorePV", "TbsInstaller--getTbsCorePV IOException=" + e22222.toString());
                                        }
                                    }
                                    d.unlock();
                                    aa.a(a, h);
                                    throw th;
                                }
                            }
                        }
                    } catch (Exception e4) {
                        e = e4;
                        fileInputStream = fileInputStream2;
                        TbsLog.e("getTbsCorePV", "TbsInstaller--getTbsCorePV Exception=" + e.toString());
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        d.unlock();
                        aa.a(a, h);
                        return i;
                    } catch (Throwable th3) {
                        th = th3;
                        if (fileInputStream2 != null) {
                            fileInputStream2.close();
                        }
                        d.unlock();
                        aa.a(a, h);
                        throw th;
                    }
                }
                aa.a(a, h);
            }
        }
        return i;
    }

    @Deprecated
    public static void disablePlatformNotifications() {
        if (i.a(false) == null || !i.a(false).b()) {
            m.a("android.webkit.WebView", "disablePlatformNotifications");
        }
    }

    private void e(Context context) {
        try {
            File file = new File(context.getDir("tbs", 0) + File.separator + "core_private", "pv.db");
            if (file != null && file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            TbsLog.i("getTbsCorePV", "TbsInstaller--getTbsCorePV Exception=" + e.toString());
        }
    }

    @Deprecated
    public static void enablePlatformNotifications() {
        if (i.a(false) == null || !i.a(false).b()) {
            m.a("android.webkit.WebView", "enablePlatformNotifications");
        }
    }

    public static String findAddress(String str) {
        return (i.a(false) == null || i.a(false).b()) ? null : android.webkit.WebView.findAddress(str);
    }

    public static String getCrashExtraMessage(Context context) {
        String str = "tbs_core_version:" + QbSdk.getTbsVersion(context) + ";" + "tbs_sdk_version:" + 36510 + ";";
        if (TbsShareManager.isThirdPartyApp(context)) {
            return "nothing return ^-^." + str;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(i.a(true).e());
        stringBuilder.append(SpecilApiUtil.LINE_SEP);
        stringBuilder.append(str);
        return stringBuilder.length() > 8192 ? stringBuilder.substring(stringBuilder.length() - 8192) : stringBuilder.toString();
    }

    @Deprecated
    public static synchronized Object getPluginList() {
        Object a;
        synchronized (WebView.class) {
            a = (i.a(false) == null || i.a(false).b()) ? null : m.a("android.webkit.WebView", "getPluginList");
        }
        return a;
    }

    public static int getTbsCoreVersion(Context context) {
        return QbSdk.getTbsVersion(context);
    }

    public static int getTbsSDKVersion(Context context) {
        return 36510;
    }

    public static synchronized void setSysDayOrNight(boolean z) {
        synchronized (WebView.class) {
            if (z != x) {
                x = z;
                if (w == null) {
                    w = new Paint();
                    w.setColor(-16777216);
                }
                if (z) {
                    if (w.getAlpha() != 255) {
                        w.setAlpha(255);
                    }
                } else if (w.getAlpha() != NIGHT_MODE_ALPHA) {
                    w.setAlpha(NIGHT_MODE_ALPHA);
                }
            }
        }
    }

    public static void setWebContentsDebuggingEnabled(boolean z) {
        if (VERSION.SDK_INT >= 19) {
            try {
                p = Class.forName("android.webkit.WebView").getDeclaredMethod("setWebContentsDebuggingEnabled", new Class[]{Boolean.TYPE});
                if (p != null) {
                    p.setAccessible(true);
                    p.invoke(null, new Object[]{Boolean.valueOf(z)});
                }
            } catch (Exception e) {
                TbsLog.e("QbSdk", "Exception:" + e.getStackTrace());
                e.printStackTrace();
            }
        }
    }

    android.webkit.WebView a() {
        return !this.f ? this.h : null;
    }

    void a(Context context) {
        String str;
        int d = d(context);
        if (d != -1) {
            str = "PV=" + String.valueOf(d + 1);
        } else {
            str = "PV=1";
        }
        File file = new File(context.getDir("tbs", 0) + File.separator + "core_private", "pv.db");
        if (file != null) {
            try {
                file.getParentFile().mkdirs();
                if (!(file.isFile() && file.exists())) {
                    file.createNewFile();
                }
                e = new FileOutputStream(file, false);
                e.write(str.getBytes());
                if (e != null) {
                    e.flush();
                }
            } catch (Throwable th) {
            }
        }
    }

    void a(android.webkit.WebView webView) {
        if (!this.f) {
        }
    }

    void a(IX5WebViewBase iX5WebViewBase) {
        this.g = iX5WebViewBase;
    }

    public void addJavascriptInterface(Object obj, String str) {
        if (this.f) {
            this.g.addJavascriptInterface(obj, str);
        } else {
            this.h.addJavascriptInterface(obj, str);
        }
    }

    public void addView(View view) {
        if (this.f) {
            View view2 = this.g.getView();
            try {
                Method a = m.a(view2, "addView", new Class[]{View.class});
                a.setAccessible(true);
                a.invoke(view2, new Object[]{view});
                return;
            } catch (Throwable th) {
                return;
            }
        }
        this.h.addView(view);
    }

    IX5WebViewBase b() {
        return this.g;
    }

    public boolean canGoBack() {
        return !this.f ? this.h.canGoBack() : this.g.canGoBack();
    }

    public boolean canGoBackOrForward(int i) {
        return !this.f ? this.h.canGoBackOrForward(i) : this.g.canGoBackOrForward(i);
    }

    public boolean canGoForward() {
        return !this.f ? this.h.canGoForward() : this.g.canGoForward();
    }

    @Deprecated
    public boolean canZoomIn() {
        if (this.f) {
            return this.g.canZoomIn();
        }
        if (VERSION.SDK_INT < 11) {
            return false;
        }
        Object a = m.a(this.h, "canZoomIn");
        return a == null ? false : ((Boolean) a).booleanValue();
    }

    @Deprecated
    public boolean canZoomOut() {
        if (this.f) {
            return this.g.canZoomOut();
        }
        if (VERSION.SDK_INT < 11) {
            return false;
        }
        Object a = m.a(this.h, "canZoomOut");
        return a == null ? false : ((Boolean) a).booleanValue();
    }

    @Deprecated
    public Picture capturePicture() {
        if (this.f) {
            return this.g.capturePicture();
        }
        Object a = m.a(this.h, "capturePicture");
        return a == null ? null : (Picture) a;
    }

    public void clearCache(boolean z) {
        if (this.f) {
            this.g.clearCache(z);
        } else {
            this.h.clearCache(z);
        }
    }

    public void clearFormData() {
        if (this.f) {
            this.g.clearFormData();
        } else {
            this.h.clearFormData();
        }
    }

    public void clearHistory() {
        if (this.f) {
            this.g.clearHistory();
        } else {
            this.h.clearHistory();
        }
    }

    @TargetApi(3)
    public void clearMatches() {
        if (this.f) {
            this.g.clearMatches();
        } else {
            this.h.clearMatches();
        }
    }

    public void clearSslPreferences() {
        if (this.f) {
            this.g.clearSslPreferences();
        } else {
            this.h.clearSslPreferences();
        }
    }

    @Deprecated
    public void clearView() {
        if (this.f) {
            this.g.clearView();
        } else {
            m.a(this.h, "clearView");
        }
    }

    public void computeScroll() {
        if (this.f) {
            this.g.computeScroll();
        } else {
            this.h.computeScroll();
        }
    }

    public WebBackForwardList copyBackForwardList() {
        return this.f ? WebBackForwardList.a(this.g.copyBackForwardList()) : WebBackForwardList.a(this.h.copyBackForwardList());
    }

    public Object createPrintDocumentAdapter(String str) {
        Object obj = null;
        if (this.f) {
            try {
                return this.g.createPrintDocumentAdapter(str);
            } catch (Throwable th) {
                th.printStackTrace();
                return obj;
            }
        } else if (VERSION.SDK_INT < 21) {
            return obj;
        } else {
            return m.a(this.h, "createPrintDocumentAdapter", new Class[]{String.class}, new Object[]{str});
        }
    }

    public void destroy() {
        if (!(this.l || this.a == 0)) {
            this.l = true;
            String str = "";
            String str2 = "";
            String str3 = "";
            if (this.f) {
                Bundle sdkQBStatisticsInfo = this.g.getX5WebViewExtension().getSdkQBStatisticsInfo();
                if (sdkQBStatisticsInfo != null) {
                    str = sdkQBStatisticsInfo.getString("guid");
                    str2 = sdkQBStatisticsInfo.getString("qua2");
                    str3 = sdkQBStatisticsInfo.getString("lc");
                }
            }
            if ("com.qzone".equals(this.j.getApplicationInfo().packageName)) {
                int d = d(this.j);
                if (d == -1) {
                    d = this.a;
                }
                this.a = d;
                e(this.j);
            }
            b.a(this.j, str, str2, str3, this.a, this.f);
            this.a = 0;
            this.l = false;
        }
        if (this.f) {
            this.g.destroy();
            return;
        }
        Object invoke;
        try {
            Class cls = Class.forName("android.webkit.WebViewClassic");
            Method method = cls.getMethod("fromWebView", new Class[]{android.webkit.WebView.class});
            method.setAccessible(true);
            invoke = method.invoke(null, new Object[]{this.h});
            if (invoke != null) {
                Field declaredField = cls.getDeclaredField("mListBoxDialog");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(invoke);
                if (obj != null) {
                    Dialog dialog = (Dialog) obj;
                    dialog.setOnCancelListener(null);
                    Class cls2 = Class.forName("android.app.Dialog");
                    Field declaredField2 = cls2.getDeclaredField("CANCEL");
                    declaredField2.setAccessible(true);
                    int intValue = ((Integer) declaredField2.get(dialog)).intValue();
                    Field declaredField3 = cls2.getDeclaredField("mListenersHandler");
                    declaredField3.setAccessible(true);
                    ((Handler) declaredField3.get(dialog)).removeMessages(intValue);
                }
            }
        } catch (Exception e) {
        }
        this.h.destroy();
        try {
            declaredField2 = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
            declaredField2.setAccessible(true);
            ComponentCallbacks componentCallbacks = (ComponentCallbacks) declaredField2.get(null);
            if (componentCallbacks != null) {
                declaredField2.set(null, null);
                declaredField2 = Class.forName("android.view.ViewRoot").getDeclaredField("sConfigCallbacks");
                declaredField2.setAccessible(true);
                invoke = declaredField2.get(null);
                if (invoke != null) {
                    List list = (List) invoke;
                    synchronized (list) {
                        list.remove(componentCallbacks);
                    }
                }
            }
        } catch (Exception e2) {
        }
    }

    public void documentHasImages(Message message) {
        if (this.f) {
            this.g.documentHasImages(message);
        } else {
            this.h.documentHasImages(message);
        }
    }

    public void dumpViewHierarchyWithProperties(BufferedWriter bufferedWriter, int i) {
        if (this.f) {
            this.g.dumpViewHierarchyWithProperties(bufferedWriter, i);
            return;
        }
        m.a(this.h, "dumpViewHierarchyWithProperties", new Class[]{BufferedWriter.class, Integer.TYPE}, new Object[]{bufferedWriter, Integer.valueOf(i)});
    }

    public void evaluateJavascript(String str, ValueCallback<String> valueCallback) {
        Method a;
        if (this.f) {
            try {
                a = m.a(this.g.getView(), "evaluateJavascript", new Class[]{String.class, ValueCallback.class});
                a.setAccessible(true);
                a.invoke(this.g.getView(), new Object[]{str, valueCallback});
            } catch (Exception e) {
                e.printStackTrace();
                loadUrl(str);
            }
        } else if (VERSION.SDK_INT >= 19) {
            try {
                a = Class.forName("android.webkit.WebView").getDeclaredMethod("evaluateJavascript", new Class[]{String.class, ValueCallback.class});
                a.setAccessible(true);
                a.invoke(this.h, new Object[]{str, valueCallback});
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @Deprecated
    public int findAll(String str) {
        if (this.f) {
            return this.g.findAll(str);
        }
        Object a = m.a(this.h, "findAll", new Class[]{String.class}, new Object[]{str});
        return a == null ? 0 : ((Integer) a).intValue();
    }

    @TargetApi(16)
    public void findAllAsync(String str) {
        if (this.f) {
            this.g.findAllAsync(str);
        } else if (VERSION.SDK_INT >= 16) {
            m.a(this.h, "findAllAsync", new Class[]{String.class}, new Object[]{str});
        }
    }

    public View findHierarchyView(String str, int i) {
        if (this.f) {
            return this.g.findHierarchyView(str, i);
        }
        return (View) m.a(this.h, "findHierarchyView", new Class[]{String.class, Integer.TYPE}, new Object[]{str, Integer.valueOf(i)});
    }

    @TargetApi(3)
    public void findNext(boolean z) {
        if (this.f) {
            this.g.findNext(z);
        } else {
            this.h.findNext(z);
        }
    }

    public void flingScroll(int i, int i2) {
        if (this.f) {
            this.g.flingScroll(i, i2);
        } else {
            this.h.flingScroll(i, i2);
        }
    }

    @Deprecated
    public void freeMemory() {
        if (this.f) {
            this.g.freeMemory();
        } else {
            m.a(this.h, "freeMemory");
        }
    }

    public SslCertificate getCertificate() {
        return !this.f ? this.h.getCertificate() : this.g.getCertificate();
    }

    public int getContentHeight() {
        return !this.f ? this.h.getContentHeight() : this.g.getContentHeight();
    }

    public int getContentWidth() {
        if (this.f) {
            return this.g.getContentWidth();
        }
        Object a = m.a(this.h, "getContentWidth");
        return a == null ? 0 : ((Integer) a).intValue();
    }

    public Bitmap getFavicon() {
        return !this.f ? this.h.getFavicon() : this.g.getFavicon();
    }

    public HitTestResult getHitTestResult() {
        return !this.f ? new HitTestResult(this.h.getHitTestResult()) : new HitTestResult(this.g.getHitTestResult());
    }

    public String[] getHttpAuthUsernamePassword(String str, String str2) {
        return !this.f ? this.h.getHttpAuthUsernamePassword(str, str2) : this.g.getHttpAuthUsernamePassword(str, str2);
    }

    @TargetApi(3)
    public String getOriginalUrl() {
        return !this.f ? this.h.getOriginalUrl() : this.g.getOriginalUrl();
    }

    public int getProgress() {
        return !this.f ? this.h.getProgress() : this.g.getProgress();
    }

    @Deprecated
    public float getScale() {
        if (this.f) {
            return this.g.getScale();
        }
        Object a = m.a(this.h, "getScale");
        return a == null ? 0.0f : ((Float) a).floatValue();
    }

    public int getScrollBarDefaultDelayBeforeFade() {
        return getView().getScrollBarDefaultDelayBeforeFade();
    }

    public int getScrollBarFadeDuration() {
        return getView().getScrollBarFadeDuration();
    }

    public int getScrollBarSize() {
        return getView().getScrollBarSize();
    }

    public int getScrollBarStyle() {
        return getView().getScrollBarStyle();
    }

    public WebSettings getSettings() {
        if (this.i != null) {
            return this.i;
        }
        if (this.f) {
            WebSettings webSettings = new WebSettings(this.g.getSettings());
            this.i = webSettings;
            return webSettings;
        }
        webSettings = new WebSettings(this.h.getSettings());
        this.i = webSettings;
        return webSettings;
    }

    public IX5WebSettingsExtension getSettingsExtension() {
        return !this.f ? null : this.g.getX5WebViewExtension().getSettingsExtension();
    }

    public int getSysNightModeAlpha() {
        return NIGHT_MODE_ALPHA;
    }

    public String getTitle() {
        return !this.f ? this.h.getTitle() : this.g.getTitle();
    }

    public String getUrl() {
        return !this.f ? this.h.getUrl() : this.g.getUrl();
    }

    public View getView() {
        return !this.f ? this.h : this.g.getView();
    }

    public int getVisibleTitleHeight() {
        if (this.f) {
            return this.g.getVisibleTitleHeight();
        }
        Object a = m.a(this.h, "getVisibleTitleHeight");
        return a == null ? 0 : ((Integer) a).intValue();
    }

    public IX5WebChromeClientExtension getWebChromeClientExtension() {
        return !this.f ? null : this.g.getX5WebViewExtension().getWebChromeClientExtension();
    }

    public int getWebScrollX() {
        return this.f ? this.g.getView().getScrollX() : this.h.getScrollX();
    }

    public int getWebScrollY() {
        if (this.f) {
            return this.g.getView().getScrollY();
        }
        Object a = m.a(this.h, "getWebScrollY");
        return a == null ? this.h.getScrollY() : ((Integer) a).intValue();
    }

    public IX5WebViewClientExtension getWebViewClientExtension() {
        return !this.f ? null : this.g.getX5WebViewExtension().getWebViewClientExtension();
    }

    public IX5WebViewExtension getX5WebViewExtension() {
        return !this.f ? null : this.g.getX5WebViewExtension();
    }

    @Deprecated
    public View getZoomControls() {
        return !this.f ? (View) m.a(this.h, "getZoomControls") : this.g.getZoomControls();
    }

    public void goBack() {
        if (this.f) {
            this.g.goBack();
        } else {
            this.h.goBack();
        }
    }

    public void goBackOrForward(int i) {
        if (this.f) {
            this.g.goBackOrForward(i);
        } else {
            this.h.goBackOrForward(i);
        }
    }

    public void goForward() {
        if (this.f) {
            this.g.goForward();
        } else {
            this.h.goForward();
        }
    }

    public void invokeZoomPicker() {
        if (this.f) {
            this.g.invokeZoomPicker();
        } else {
            this.h.invokeZoomPicker();
        }
    }

    public boolean isDayMode() {
        return x;
    }

    public boolean isPrivateBrowsingEnabled() {
        if (this.f) {
            return this.g.isPrivateBrowsingEnable();
        }
        if (VERSION.SDK_INT < 11) {
            return false;
        }
        Object a = m.a(this.h, "isPrivateBrowsingEnabled");
        return a == null ? false : ((Boolean) a).booleanValue();
    }

    public void loadData(String str, String str2, String str3) {
        if (this.f) {
            this.g.loadData(str, str2, str3);
        } else {
            this.h.loadData(str, str2, str3);
        }
    }

    public void loadDataWithBaseURL(String str, String str2, String str3, String str4, String str5) {
        if (this.f) {
            this.g.loadDataWithBaseURL(str, str2, str3, str4, str5);
        } else {
            this.h.loadDataWithBaseURL(str, str2, str3, str4, str5);
        }
    }

    public void loadUrl(String str) {
        if (this.f) {
            this.g.loadUrl(str);
        } else {
            this.h.loadUrl(str);
        }
    }

    @TargetApi(8)
    public void loadUrl(String str, Map<String, String> map) {
        if (this.f) {
            this.g.loadUrl(str, map);
        } else if (VERSION.SDK_INT >= 8) {
            this.h.loadUrl(str, map);
        }
    }

    protected void onDetachedFromWindow() {
        if (!(this.l || this.a == 0)) {
            this.l = true;
            String str = "";
            String str2 = "";
            String str3 = "";
            if (this.f) {
                Bundle sdkQBStatisticsInfo = this.g.getX5WebViewExtension().getSdkQBStatisticsInfo();
                if (sdkQBStatisticsInfo != null) {
                    str = sdkQBStatisticsInfo.getString("guid");
                    str2 = sdkQBStatisticsInfo.getString("qua2");
                    str3 = sdkQBStatisticsInfo.getString("lc");
                }
            }
            if ("com.qzone".equals(this.j.getApplicationInfo().packageName)) {
                int d = d(this.j);
                if (d == -1) {
                    d = this.a;
                }
                this.a = d;
                e(this.j);
            }
            b.a(this.j, str, str2, str3, this.a, this.f);
            this.a = 0;
            this.l = false;
        }
        super.onDetachedFromWindow();
    }

    public boolean onLongClick(View view) {
        return this.z != null ? !this.z.onLongClick(view) ? a(view) : true : a(view);
    }

    public void onPause() {
        if (this.f) {
            this.g.onPause();
        } else {
            m.a(this.h, "onPause");
        }
    }

    public void onResume() {
        if (this.f) {
            this.g.onResume();
        } else {
            m.a(this.h, "onResume");
        }
    }

    @TargetApi(11)
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (VERSION.SDK_INT >= 21 && b(this.j) && isHardwareAccelerated() && i > 0 && i2 > 0 && getLayerType() != 2 && this.g != null && this.g.getView() != null) {
            this.g.getView().setLayerType(2, null);
        }
    }

    protected void onVisibilityChanged(View view, int i) {
        if (this.j == null) {
            super.onVisibilityChanged(view, i);
            return;
        }
        if (q == null) {
            q = this.j.getApplicationInfo().packageName;
        }
        if (q == null || !(q.equals("com.tencent.mm") || q.equals("com.tencent.mobileqq"))) {
            if (!(i == 0 || this.l || this.a == 0)) {
                this.l = true;
                String str = "";
                String str2 = "";
                String str3 = "";
                if (this.f) {
                    Bundle sdkQBStatisticsInfo = this.g.getX5WebViewExtension().getSdkQBStatisticsInfo();
                    if (sdkQBStatisticsInfo != null) {
                        str = sdkQBStatisticsInfo.getString("guid");
                        str2 = sdkQBStatisticsInfo.getString("qua2");
                        str3 = sdkQBStatisticsInfo.getString("lc");
                    }
                }
                if ("com.qzone".equals(this.j.getApplicationInfo().packageName)) {
                    int d = d(this.j);
                    if (d == -1) {
                        d = this.a;
                    }
                    this.a = d;
                    e(this.j);
                }
                b.a(this.j, str, str2, str3, this.a, this.f);
                this.a = 0;
                this.l = false;
            }
            super.onVisibilityChanged(view, i);
            return;
        }
        super.onVisibilityChanged(view, i);
    }

    public boolean overlayHorizontalScrollbar() {
        return !this.f ? this.h.overlayHorizontalScrollbar() : this.g.overlayHorizontalScrollbar();
    }

    public boolean overlayVerticalScrollbar() {
        return this.f ? this.g.overlayVerticalScrollbar() : this.h.overlayVerticalScrollbar();
    }

    public boolean pageDown(boolean z) {
        return !this.f ? this.h.pageDown(z) : this.g.pageDown(z, -1);
    }

    public boolean pageUp(boolean z) {
        return !this.f ? this.h.pageUp(z) : this.g.pageUp(z, -1);
    }

    public void pauseTimers() {
        if (this.f) {
            this.g.pauseTimers();
        } else {
            this.h.pauseTimers();
        }
    }

    @TargetApi(5)
    public void postUrl(String str, byte[] bArr) {
        if (this.f) {
            this.g.postUrl(str, bArr);
        } else {
            this.h.postUrl(str, bArr);
        }
    }

    @Deprecated
    public void refreshPlugins(boolean z) {
        if (this.f) {
            this.g.refreshPlugins(z);
            return;
        }
        m.a(this.h, "refreshPlugins", new Class[]{Boolean.TYPE}, new Object[]{Boolean.valueOf(z)});
    }

    public void reload() {
        if (this.f) {
            this.g.reload();
        } else {
            this.h.reload();
        }
    }

    @TargetApi(11)
    public void removeJavascriptInterface(String str) {
        if (!this.f) {
            if (VERSION.SDK_INT >= 11) {
                m.a(this.h, "removeJavascriptInterface", new Class[]{String.class}, new Object[]{str});
                return;
            }
            this.g.removeJavascriptInterface(str);
        }
    }

    public void removeView(View view) {
        if (this.f) {
            View view2 = this.g.getView();
            try {
                Method a = m.a(view2, "removeView", new Class[]{View.class});
                a.setAccessible(true);
                a.invoke(view2, new Object[]{view});
                return;
            } catch (Throwable th) {
                return;
            }
        }
        this.h.removeView(view);
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        if (this.f) {
            View view2 = this.g.getView();
            if (!(view2 instanceof ViewGroup)) {
                return false;
            }
            ViewGroup viewGroup = (ViewGroup) view2;
            if (view != this) {
                view2 = view;
            }
            return viewGroup.requestChildRectangleOnScreen(view2, rect, z);
        }
        android.webkit.WebView webView = this.h;
        if (view == this) {
            view = this.h;
        }
        return webView.requestChildRectangleOnScreen(view, rect, z);
    }

    public void requestFocusNodeHref(Message message) {
        if (this.f) {
            this.g.requestFocusNodeHref(message);
        } else {
            this.h.requestFocusNodeHref(message);
        }
    }

    public void requestImageRef(Message message) {
        if (this.f) {
            this.g.requestImageRef(message);
        } else {
            this.h.requestImageRef(message);
        }
    }

    @Deprecated
    public boolean restorePicture(Bundle bundle, File file) {
        if (this.f) {
            return this.g.restorePicture(bundle, file);
        }
        Object a = m.a(this.h, "restorePicture", new Class[]{Bundle.class, File.class}, new Object[]{bundle, file});
        return a == null ? false : ((Boolean) a).booleanValue();
    }

    public WebBackForwardList restoreState(Bundle bundle) {
        return !this.f ? WebBackForwardList.a(this.h.restoreState(bundle)) : WebBackForwardList.a(this.g.restoreState(bundle));
    }

    public void resumeTimers() {
        if (this.f) {
            this.g.resumeTimers();
        } else {
            this.h.resumeTimers();
        }
    }

    @Deprecated
    public void savePassword(String str, String str2, String str3) {
        if (this.f) {
            this.g.savePassword(str, str2, str3);
            return;
        }
        m.a(this.h, "savePassword", new Class[]{String.class, String.class, String.class}, new Object[]{str, str2, str3});
    }

    @Deprecated
    public boolean savePicture(Bundle bundle, File file) {
        if (this.f) {
            return this.g.savePicture(bundle, file);
        }
        Object a = m.a(this.h, "savePicture", new Class[]{Bundle.class, File.class}, new Object[]{bundle, file});
        return a == null ? false : ((Boolean) a).booleanValue();
    }

    public WebBackForwardList saveState(Bundle bundle) {
        return !this.f ? WebBackForwardList.a(this.h.saveState(bundle)) : WebBackForwardList.a(this.g.saveState(bundle));
    }

    @TargetApi(11)
    public void saveWebArchive(String str) {
        if (this.f) {
            this.g.saveWebArchive(str);
        } else if (VERSION.SDK_INT >= 11) {
            m.a(this.h, "saveWebArchive", new Class[]{String.class}, new Object[]{str});
        }
    }

    @TargetApi(11)
    public void saveWebArchive(String str, boolean z, ValueCallback<String> valueCallback) {
        if (this.f) {
            this.g.saveWebArchive(str, z, valueCallback);
        } else if (VERSION.SDK_INT >= 11) {
            m.a(this.h, "saveWebArchive", new Class[]{String.class, Boolean.TYPE, ValueCallback.class}, new Object[]{str, Boolean.valueOf(z), valueCallback});
        }
    }

    public void setBackgroundColor(int i) {
        if (this.f) {
            this.g.setBackgroundColor(i);
        } else {
            this.h.setBackgroundColor(i);
        }
        super.setBackgroundColor(i);
    }

    @Deprecated
    public void setCertificate(SslCertificate sslCertificate) {
        if (this.f) {
            this.g.setCertificate(sslCertificate);
        } else {
            this.h.setCertificate(sslCertificate);
        }
    }

    public void setDayOrNight(boolean z) {
        try {
            if (this.f) {
                getSettingsExtension().setDayOrNight(z);
            }
            setSysDayOrNight(z);
            getView().postInvalidate();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void setDownloadListener(DownloadListener downloadListener) {
        if (this.f) {
            this.g.setDownloadListener(new b(this, downloadListener, this.f));
        } else {
            this.h.setDownloadListener(new al(this, downloadListener));
        }
    }

    @TargetApi(16)
    public void setFindListener(FindListener findListener) {
        if (this.f) {
            this.g.setFindListener(findListener);
        } else if (VERSION.SDK_INT >= 16) {
            this.h.setFindListener(new ak(this, findListener));
        }
    }

    public void setHorizontalScrollbarOverlay(boolean z) {
        if (this.f) {
            this.g.setHorizontalScrollbarOverlay(z);
        } else {
            this.h.setHorizontalScrollbarOverlay(z);
        }
    }

    public void setHttpAuthUsernamePassword(String str, String str2, String str3, String str4) {
        if (this.f) {
            this.g.setHttpAuthUsernamePassword(str, str2, str3, str4);
        } else {
            this.h.setHttpAuthUsernamePassword(str, str2, str3, str4);
        }
    }

    public void setInitialScale(int i) {
        if (this.f) {
            this.g.setInitialScale(i);
        } else {
            this.h.setInitialScale(i);
        }
    }

    public void setLongPressTextExtensionMenu(int i) {
        if (this.f) {
            m.a(this.g, "setLongPressTextExtensionMenu", new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(i)});
        }
    }

    @Deprecated
    public void setMapTrackballToArrowKeys(boolean z) {
        if (this.f) {
            this.g.setMapTrackballToArrowKeys(z);
            return;
        }
        m.a(this.h, "setMapTrackballToArrowKeys", new Class[]{Boolean.TYPE}, new Object[]{Boolean.valueOf(z)});
    }

    public void setNetworkAvailable(boolean z) {
        if (this.f) {
            this.g.setNetworkAvailable(z);
        } else if (VERSION.SDK_INT >= 3) {
            this.h.setNetworkAvailable(z);
        }
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        if (this.f) {
            View view = this.g.getView();
            try {
                if (this.y == null) {
                    Method a = m.a(view, "getListenerInfo", new Class[0]);
                    a.setAccessible(true);
                    Object invoke = a.invoke(view, (Object[]) null);
                    Field declaredField = invoke.getClass().getDeclaredField("mOnLongClickListener");
                    declaredField.setAccessible(true);
                    this.y = declaredField.get(invoke);
                }
                this.z = onLongClickListener;
                getView().setOnLongClickListener(this);
                return;
            } catch (Throwable th) {
                return;
            }
        }
        this.h.setOnLongClickListener(onLongClickListener);
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        getView().setOnTouchListener(onTouchListener);
    }

    public void setPictureListener(PictureListener pictureListener) {
        if (this.f) {
            if (pictureListener == null) {
                this.g.setPictureListener(null);
            } else {
                this.g.setPictureListener(new an(this, pictureListener));
            }
        } else if (pictureListener == null) {
            this.h.setPictureListener(null);
        } else {
            this.h.setPictureListener(new am(this, pictureListener));
        }
    }

    public void setScrollBarStyle(int i) {
        if (this.f) {
            this.g.getView().setScrollBarStyle(i);
        } else {
            this.h.setScrollBarStyle(i);
        }
    }

    public void setSysNightModeAlpha(int i) {
        NIGHT_MODE_ALPHA = i;
    }

    public void setVerticalScrollbarOverlay(boolean z) {
        if (this.f) {
            this.g.setVerticalScrollbarOverlay(z);
        } else {
            this.h.setVerticalScrollbarOverlay(z);
        }
    }

    public boolean setVideoFullScreen(Context context, boolean z) {
        if (!context.getApplicationInfo().processName.contains("com.tencent.android.qqdownloader") || this.g == null) {
            return false;
        }
        Bundle bundle = new Bundle();
        if (z) {
            bundle.putInt("DefaultVideoScreen", 2);
        } else {
            bundle.putInt("DefaultVideoScreen", 1);
        }
        this.g.getX5WebViewExtension().invokeMiscMethod("setVideoParams", bundle);
        return true;
    }

    public void setWebChromeClient(WebChromeClient webChromeClient) {
        WebChromeClient webChromeClient2 = null;
        if (this.f) {
            IX5WebChromeClient kVar;
            IX5WebViewBase iX5WebViewBase = this.g;
            if (webChromeClient != null) {
                kVar = new k(i.a(true).b(true).g(), this, webChromeClient);
            }
            iX5WebViewBase.setWebChromeClient(kVar);
            return;
        }
        android.webkit.WebView webView = this.h;
        if (webChromeClient != null) {
            webChromeClient2 = new SystemWebChromeClient(this, webChromeClient);
        }
        webView.setWebChromeClient(webChromeClient2);
    }

    public void setWebChromeClientExtension(IX5WebChromeClientExtension iX5WebChromeClientExtension) {
        if (this.f) {
            this.g.getX5WebViewExtension().setWebChromeClientExtension(iX5WebChromeClientExtension);
        }
    }

    public void setWebViewCallbackClient(WebViewCallbackClient webViewCallbackClient) {
        this.mWebViewCallbackClient = webViewCallbackClient;
        if (this.f && getX5WebViewExtension() != null) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("flag", true);
            getX5WebViewExtension().invokeMiscMethod("setWebViewCallbackClientFlag", bundle);
        }
    }

    public void setWebViewClient(WebViewClient webViewClient) {
        WebViewClient webViewClient2 = null;
        this.n = webViewClient;
        if (this.f) {
            IX5WebViewClient pVar;
            IX5WebViewBase iX5WebViewBase = this.g;
            if (webViewClient != null) {
                pVar = new p(i.a(true).b(true).h(), this, webViewClient);
            }
            iX5WebViewBase.setWebViewClient(pVar);
            return;
        }
        android.webkit.WebView webView = this.h;
        if (webViewClient != null) {
            webViewClient2 = new v(this, webViewClient);
        }
        webView.setWebViewClient(webViewClient2);
    }

    public void setWebViewClientExtension(IX5WebViewClientExtension iX5WebViewClientExtension) {
        if (this.f) {
            this.g.getX5WebViewExtension().setWebViewClientExtension(iX5WebViewClientExtension);
        }
    }

    @SuppressLint({"NewApi"})
    public boolean showDebugView(String str) {
        if (!str.startsWith("http://debugtbs.qq.com")) {
            return false;
        }
        getView().setVisibility(4);
        com.tencent.smtt.utils.b.a(this.j).b(str, this, this.j);
        return true;
    }

    public boolean showFindDialog(String str, boolean z) {
        return false;
    }

    public void stopLoading() {
        if (this.f) {
            this.g.stopLoading();
        } else {
            this.h.stopLoading();
        }
    }

    public void super_computeScroll() {
        if (this.f) {
            try {
                m.a(this.g.getView(), "super_computeScroll");
                return;
            } catch (Throwable th) {
                th.printStackTrace();
                return;
            }
        }
        this.h.a();
    }

    public boolean super_dispatchTouchEvent(MotionEvent motionEvent) {
        if (!this.f) {
            return this.h.b(motionEvent);
        }
        try {
            Object a = m.a(this.g.getView(), "super_dispatchTouchEvent", new Class[]{MotionEvent.class}, new Object[]{motionEvent});
            return a == null ? false : ((Boolean) a).booleanValue();
        } catch (Throwable th) {
            return false;
        }
    }

    public boolean super_onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!this.f) {
            return this.h.c(motionEvent);
        }
        try {
            Object a = m.a(this.g.getView(), "super_onInterceptTouchEvent", new Class[]{MotionEvent.class}, new Object[]{motionEvent});
            return a == null ? false : ((Boolean) a).booleanValue();
        } catch (Throwable th) {
            return false;
        }
    }

    public void super_onOverScrolled(int i, int i2, boolean z, boolean z2) {
        if (this.f) {
            try {
                m.a(this.g.getView(), "super_onOverScrolled", new Class[]{Integer.TYPE, Integer.TYPE, Boolean.TYPE, Boolean.TYPE}, new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Boolean.valueOf(z), Boolean.valueOf(z2)});
                return;
            } catch (Throwable th) {
                th.printStackTrace();
                return;
            }
        }
        this.h.a(i, i2, z, z2);
    }

    public void super_onScrollChanged(int i, int i2, int i3, int i4) {
        if (this.f) {
            try {
                m.a(this.g.getView(), "super_onScrollChanged", new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE}, new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4)});
                return;
            } catch (Throwable th) {
                th.printStackTrace();
                return;
            }
        }
        this.h.a(i, i2, i3, i4);
    }

    public boolean super_onTouchEvent(MotionEvent motionEvent) {
        if (!this.f) {
            return this.h.a(motionEvent);
        }
        try {
            Object a = m.a(this.g.getView(), "super_onTouchEvent", new Class[]{MotionEvent.class}, new Object[]{motionEvent});
            return a == null ? false : ((Boolean) a).booleanValue();
        } catch (Throwable th) {
            return false;
        }
    }

    public boolean super_overScrollBy(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
        if (!this.f) {
            return this.h.a(i, i2, i3, i4, i5, i6, i7, i8, z);
        }
        try {
            Object a = m.a(this.g.getView(), "super_overScrollBy", new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE}, new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6), Integer.valueOf(i7), Integer.valueOf(i8), Boolean.valueOf(z)});
            return a == null ? false : ((Boolean) a).booleanValue();
        } catch (Throwable th) {
            return false;
        }
    }

    public void switchNightMode(boolean z) {
        if (z != x) {
            x = z;
            if (x) {
                TbsLog.e("QB_SDK", "deleteNightMode");
                loadUrl("javascript:document.getElementsByTagName('HEAD').item(0).removeChild(document.getElementById('QQBrowserSDKNightMode'));");
                return;
            }
            TbsLog.e("QB_SDK", "nightMode");
            loadUrl("javascript:var style = document.createElement('style');style.type='text/css';style.id='QQBrowserSDKNightMode';style.innerHTML='html,body{background:none !important;background-color: #1d1e2a !important;}html *{background-color: #1d1e2a !important; color:#888888 !important;border-color:#3e4f61 !important;text-shadow:none !important;box-shadow:none !important;}a_isRightVersion,a_isRightVersion *{border-color:#4c5b99 !important; color:#2d69b3 !important;text-decoration:none !important;}a_isRightVersion:visited,a_isRightVersion:visited *{color:#a600a6 !important;}a_isRightVersion:active,a_isRightVersion:active *{color:#5588AA !important;}input,select,textarea,option,button{background-image:none !important;color:#AAAAAA !important;border-color:#4c5b99 !important;}form,div,button,span{background-color:#1d1e2a !important; border-color:#4c5b99 !important;}img{opacity:0.5}';document.getElementsByTagName('HEAD').item(0).appendChild(style);");
        }
    }

    public void switchToNightMode() {
        TbsLog.e("QB_SDK", "switchToNightMode 01");
        if (!x) {
            TbsLog.e("QB_SDK", "switchToNightMode");
            loadUrl("javascript:var style = document.createElement('style');style.type='text/css';style.id='QQBrowserSDKNightMode';style.innerHTML='html,body{background:none !important;background-color: #1d1e2a !important;}html *{background-color: #1d1e2a !important; color:#888888 !important;border-color:#3e4f61 !important;text-shadow:none !important;box-shadow:none !important;}a_isRightVersion,a_isRightVersion *{border-color:#4c5b99 !important; color:#2d69b3 !important;text-decoration:none !important;}a_isRightVersion:visited,a_isRightVersion:visited *{color:#a600a6 !important;}a_isRightVersion:active,a_isRightVersion:active *{color:#5588AA !important;}input,select,textarea,option,button{background-image:none !important;color:#AAAAAA !important;border-color:#4c5b99 !important;}form,div,button,span{background-color:#1d1e2a !important; border-color:#4c5b99 !important;}img{opacity:0.5}';document.getElementsByTagName('HEAD').item(0).appendChild(style);");
        }
    }

    public boolean zoomIn() {
        return !this.f ? this.h.zoomIn() : this.g.zoomIn();
    }

    public boolean zoomOut() {
        return !this.f ? this.h.zoomOut() : this.g.zoomOut();
    }
}
