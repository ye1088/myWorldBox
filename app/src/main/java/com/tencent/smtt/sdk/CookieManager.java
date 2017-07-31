package com.tencent.smtt.sdk;

import android.os.Build.VERSION;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import com.tencent.smtt.utils.m;
import java.util.Map;

public class CookieManager {
    private static CookieManager b;
    private android.webkit.CookieManager a = android.webkit.CookieManager.getInstance();

    private CookieManager() {
    }

    public static synchronized CookieManager getInstance() {
        CookieManager cookieManager;
        synchronized (CookieManager.class) {
            if (b == null) {
                b = new CookieManager();
            }
            cookieManager = b;
        }
        return cookieManager;
    }

    public boolean acceptCookie() {
        i a = i.a(false);
        return (a == null || !a.b()) ? this.a.acceptCookie() : a.a().b();
    }

    public synchronized boolean acceptThirdPartyCookies(WebView webView) {
        boolean booleanValue;
        i a = i.a(false);
        Object invokeStaticMethod;
        if (a != null && a.b()) {
            invokeStaticMethod = a.a().o().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_acceptThirdPartyCookies", new Class[]{Object.class}, webView.getView());
            booleanValue = invokeStaticMethod != null ? ((Boolean) invokeStaticMethod).booleanValue() : true;
        } else if (VERSION.SDK_INT < 21) {
            booleanValue = true;
        } else {
            invokeStaticMethod = m.a(this.a, "acceptThirdPartyCookies", new Class[]{WebView.class}, webView.getView());
            booleanValue = invokeStaticMethod != null ? ((Boolean) invokeStaticMethod).booleanValue() : false;
        }
        return booleanValue;
    }

    public void flush() {
        i a = i.a(false);
        if (a != null && a.b()) {
            a.a().o().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_flush", new Class[0], new Object[0]);
        } else if (VERSION.SDK_INT >= 21) {
            m.a(this.a, "flush", new Class[0], new Object[0]);
        }
    }

    public String getCookie(String str) {
        i a = i.a(false);
        return (a == null || !a.b()) ? this.a.getCookie(str) : a.a().a(str);
    }

    public boolean hasCookies() {
        i a = i.a(false);
        return (a == null || !a.b()) ? this.a.hasCookies() : a.a().f();
    }

    public void removeAllCookie() {
        i a = i.a(false);
        if (a == null || !a.b()) {
            this.a.removeAllCookie();
        } else {
            a.a().c();
        }
    }

    public void removeAllCookies(ValueCallback<Boolean> valueCallback) {
        i a = i.a(false);
        if (a != null && a.b()) {
            a.a().o().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_removeAllCookies", new Class[]{ValueCallback.class}, valueCallback);
        } else if (VERSION.SDK_INT >= 21) {
            m.a(this.a, "removeAllCookies", new Class[]{ValueCallback.class}, valueCallback);
        }
    }

    public void removeExpiredCookie() {
        i a = i.a(false);
        if (a == null || !a.b()) {
            this.a.removeExpiredCookie();
        } else {
            a.a().o().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_removeExpiredCookie", new Class[0], new Object[0]);
        }
    }

    public void removeSessionCookie() {
        i a = i.a(false);
        if (a == null || !a.b()) {
            this.a.removeSessionCookie();
        } else {
            a.a().o().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_removeSessionCookie", new Class[0], new Object[0]);
        }
    }

    public void removeSessionCookies(ValueCallback<Boolean> valueCallback) {
        i a = i.a(false);
        if (a != null && a.b()) {
            a.a().o().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_removeSessionCookies", new Class[]{ValueCallback.class}, valueCallback);
        } else if (VERSION.SDK_INT >= 21) {
            m.a(this.a, "removeSessionCookies", new Class[]{ValueCallback.class}, valueCallback);
        }
    }

    public synchronized void setAcceptCookie(boolean z) {
        i a = i.a(false);
        if (a == null || !a.b()) {
            this.a.setAcceptCookie(z);
        } else {
            a.a().o().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_setAcceptCookie", new Class[]{Boolean.TYPE}, Boolean.valueOf(z));
        }
    }

    public synchronized void setAcceptThirdPartyCookies(WebView webView, boolean z) {
        i a = i.a(false);
        if (a != null && a.b()) {
            a.a().o().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_setAcceptThirdPartyCookies", new Class[]{Object.class, Boolean.TYPE}, webView.getView(), Boolean.valueOf(z));
        } else if (VERSION.SDK_INT >= 21) {
            m.a(this.a, "setAcceptThirdPartyCookies", new Class[]{WebView.class, Boolean.TYPE}, webView.getView(), Boolean.valueOf(z));
        }
    }

    public void setCookie(String str, String str2) {
        i a = i.a(false);
        if (a == null || !a.b()) {
            this.a.setCookie(str, str2);
            return;
        }
        a.a().o().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_setCookie", new Class[]{String.class, String.class}, str, str2);
    }

    public void setCookie(String str, String str2, ValueCallback<Boolean> valueCallback) {
        i a = i.a(false);
        if (a != null && a.b()) {
            a.a().o().invokeStaticMethod("com.tencent.tbs.tbsshell.WebCoreProxy", "cookieManager_setCookie", new Class[]{String.class, String.class, ValueCallback.class}, str, str2, valueCallback);
        } else if (VERSION.SDK_INT >= 21) {
            m.a(this.a, "setCookie", new Class[]{String.class, String.class, ValueCallback.class}, str, str2, valueCallback);
        }
    }

    public void setCookies(Map<String, String[]> map) {
        i a = i.a(false);
        boolean a2 = (a == null || !a.b()) ? false : a.a().a((Map) map);
        if (!a2) {
            for (String str : map.keySet()) {
                for (String cookie : (String[]) map.get(str)) {
                    setCookie(str, cookie);
                }
            }
        }
    }
}
