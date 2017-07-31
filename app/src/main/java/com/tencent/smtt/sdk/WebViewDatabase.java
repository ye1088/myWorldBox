package com.tencent.smtt.sdk;

import android.content.Context;

public class WebViewDatabase {
    private static WebViewDatabase a;
    private android.webkit.WebViewDatabase b;
    private Context c;

    protected WebViewDatabase(Context context) {
        this.c = context;
    }

    private static synchronized WebViewDatabase a(Context context) {
        WebViewDatabase webViewDatabase;
        synchronized (WebViewDatabase.class) {
            if (a == null) {
                a = new WebViewDatabase(context);
            }
            webViewDatabase = a;
        }
        return webViewDatabase;
    }

    public static WebViewDatabase getInstance(Context context) {
        return a(context);
    }

    public void clearFormData() {
        i a = i.a(false);
        if (a == null || !a.b()) {
            this.b.clearFormData();
        } else {
            a.a().g(this.c);
        }
    }

    public void clearHttpAuthUsernamePassword() {
        i a = i.a(false);
        if (a == null || !a.b()) {
            this.b.clearHttpAuthUsernamePassword();
        } else {
            a.a().e(this.c);
        }
    }

    @Deprecated
    public void clearUsernamePassword() {
        i a = i.a(false);
        if (a == null || !a.b()) {
            this.b.clearUsernamePassword();
        } else {
            a.a().c(this.c);
        }
    }

    public boolean hasFormData() {
        i a = i.a(false);
        return (a == null || !a.b()) ? this.b.hasFormData() : a.a().f(this.c);
    }

    public boolean hasHttpAuthUsernamePassword() {
        i a = i.a(false);
        return (a == null || !a.b()) ? this.b.hasHttpAuthUsernamePassword() : a.a().d(this.c);
    }

    @Deprecated
    public boolean hasUsernamePassword() {
        i a = i.a(false);
        return (a == null || !a.b()) ? this.b.hasUsernamePassword() : a.a().b(this.c);
    }
}
