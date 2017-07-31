package com.tencent.smtt.sdk;

import android.content.ContentResolver;
import android.graphics.Bitmap;

@Deprecated
public class WebIconDatabase {
    private static WebIconDatabase a;
    private android.webkit.WebIconDatabase b = android.webkit.WebIconDatabase.getInstance();

    @Deprecated
    public interface a {
        void a(String str, Bitmap bitmap);
    }

    private WebIconDatabase() {
    }

    private static synchronized WebIconDatabase a() {
        WebIconDatabase webIconDatabase;
        synchronized (WebIconDatabase.class) {
            if (a == null) {
                a = new WebIconDatabase();
            }
            webIconDatabase = a;
        }
        return webIconDatabase;
    }

    public static WebIconDatabase getInstance() {
        return a();
    }

    public void bulkRequestIconForPageUrl(ContentResolver contentResolver, String str, a aVar) {
    }

    public void close() {
        i a = i.a(false);
        if (a == null || !a.b()) {
            this.b.close();
        } else {
            a.a().k();
        }
    }

    public void open(String str) {
        i a = i.a(false);
        if (a == null || !a.b()) {
            this.b.open(str);
        } else {
            a.a().b(str);
        }
    }

    public void releaseIconForPageUrl(String str) {
        i a = i.a(false);
        if (a == null || !a.b()) {
            this.b.releaseIconForPageUrl(str);
        } else {
            a.a().d(str);
        }
    }

    public void removeAllIcons() {
        i a = i.a(false);
        if (a == null || !a.b()) {
            this.b.removeAllIcons();
        } else {
            a.a().j();
        }
    }

    public void requestIconForPageUrl(String str, a aVar) {
        i a = i.a(false);
        if (a == null || !a.b()) {
            this.b.requestIconForPageUrl(str, new ai(this, aVar));
        } else {
            a.a().a(str, new ah(this, aVar));
        }
    }

    public void retainIconForPageUrl(String str) {
        i a = i.a(false);
        if (a == null || !a.b()) {
            this.b.retainIconForPageUrl(str);
        } else {
            a.a().c(str);
        }
    }
}
