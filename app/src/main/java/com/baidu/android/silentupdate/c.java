package com.baidu.android.silentupdate;

import android.app.Application;
import java.lang.reflect.Field;

class c {
    c() {
    }

    public static void a(Application application, String str) {
        try {
            Field declaredField = Class.forName("android.content.ContextWrapper").getDeclaredField("mBase");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(application);
            Class cls = Class.forName("android.app.ContextImpl");
            cls.getDeclaredField("mMainThread").setAccessible(true);
            Field declaredField2 = cls.getDeclaredField("mPackageInfo");
            declaredField2.setAccessible(true);
            obj = declaredField2.get(obj);
            cls = Class.forName("android.app.LoadedApk");
            Field declaredField3 = cls.getDeclaredField("mResDir");
            declaredField3.setAccessible(true);
            declaredField3.set(obj, str);
            declaredField2 = cls.getDeclaredField("mResources");
            declaredField2.setAccessible(true);
            declaredField2.set(obj, null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e2) {
            e2.printStackTrace();
        } catch (IllegalArgumentException e3) {
            e3.printStackTrace();
        } catch (IllegalAccessException e4) {
            e4.printStackTrace();
        }
    }
}
