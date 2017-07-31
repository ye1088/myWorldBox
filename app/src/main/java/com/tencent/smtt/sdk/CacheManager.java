package com.tencent.smtt.sdk;

import com.tencent.smtt.utils.m;
import java.io.File;
import java.io.InputStream;
import java.util.Map;

public final class CacheManager {
    @Deprecated
    public static boolean cacheDisabled() {
        i a = i.a(false);
        if (a != null && a.b()) {
            return ((Boolean) a.a().a()).booleanValue();
        }
        Object a2 = m.a("android.webkit.CacheManager", "cacheDisabled");
        return a2 == null ? false : ((Boolean) a2).booleanValue();
    }

    public static InputStream getCacheFile(String str, boolean z) {
        i a = i.a(false);
        return (a == null || !a.b()) ? null : a.a().a(str, z);
    }

    public static Object getCacheFile(String str, Map<String, String> map) {
        i a = i.a(false);
        if (a != null && a.b()) {
            return a.a().e();
        }
        try {
            return m.a(Class.forName("android.webkit.CacheManager"), "getCacheFile", new Class[]{String.class, Map.class}, new Object[]{str, map});
        } catch (Exception e) {
            return null;
        }
    }

    @Deprecated
    public static File getCacheFileBaseDir() {
        i a = i.a(false);
        return (a == null || !a.b()) ? (File) m.a("android.webkit.CacheManager", "getCacheFileBaseDir") : (File) a.a().e();
    }
}
