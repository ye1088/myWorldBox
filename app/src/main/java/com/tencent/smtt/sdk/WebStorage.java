package com.tencent.smtt.sdk;

import java.util.Map;

public class WebStorage {
    private static WebStorage a;
    private android.webkit.WebStorage b = android.webkit.WebStorage.getInstance();

    @Deprecated
    public interface QuotaUpdater {
        void updateQuota(long j);
    }

    private static synchronized WebStorage a() {
        WebStorage webStorage;
        synchronized (WebStorage.class) {
            if (a == null) {
                a = new WebStorage();
            }
            webStorage = a;
        }
        return webStorage;
    }

    public static WebStorage getInstance() {
        return a();
    }

    public void deleteAllData() {
        i a = i.a(false);
        if (a == null || !a.b()) {
            this.b.deleteAllData();
        } else {
            a.a().l();
        }
    }

    public void deleteOrigin(String str) {
        i a = i.a(false);
        if (a == null || !a.b()) {
            this.b.deleteOrigin(str);
        } else {
            a.a().e(str);
        }
    }

    public void getOrigins(ValueCallback<Map> valueCallback) {
        i a = i.a(false);
        if (a == null || !a.b()) {
            this.b.getOrigins(valueCallback);
        } else {
            a.a().a(valueCallback);
        }
    }

    public void getQuotaForOrigin(String str, ValueCallback<Long> valueCallback) {
        i a = i.a(false);
        if (a == null || !a.b()) {
            this.b.getQuotaForOrigin(str, valueCallback);
        } else {
            a.a().b(str, valueCallback);
        }
    }

    public void getUsageForOrigin(String str, ValueCallback<Long> valueCallback) {
        i a = i.a(false);
        if (a == null || !a.b()) {
            this.b.getUsageForOrigin(str, valueCallback);
        } else {
            a.a().a(str, valueCallback);
        }
    }

    @Deprecated
    public void setQuotaForOrigin(String str, long j) {
        i a = i.a(false);
        if (a == null || !a.b()) {
            this.b.setQuotaForOrigin(str, j);
        } else {
            a.a().a(str, j);
        }
    }
}
