package com.tencent.smtt.export.external;

import android.content.Context;
import android.util.Log;
import dalvik.system.DexClassLoader;
import dalvik.system.VMStack;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DexLoader {
    private DexClassLoader mClassLoader;

    public DexLoader(Context context, String str, String str2) {
        this(context, new String[]{str}, str2);
    }

    public DexLoader(Context context, String[] strArr, String str) {
        ClassLoader callingClassLoader = VMStack.getCallingClassLoader();
        if (callingClassLoader == null) {
            callingClassLoader = context.getClassLoader();
        }
        ClassLoader classLoader = callingClassLoader;
        int i = 0;
        while (i < strArr.length) {
            ClassLoader dexClassLoader = new DexClassLoader(strArr[i], str, null, classLoader);
            this.mClassLoader = dexClassLoader;
            i++;
            classLoader = dexClassLoader;
        }
    }

    public DexLoader(Context context, String[] strArr, String str, DexLoader dexLoader) {
        ClassLoader classLoader = dexLoader.getClassLoader();
        int i = 0;
        while (i < strArr.length) {
            ClassLoader dexClassLoader = new DexClassLoader(strArr[i], str, null, classLoader);
            this.mClassLoader = dexClassLoader;
            i++;
            classLoader = dexClassLoader;
        }
    }

    public DexClassLoader getClassLoader() {
        return this.mClassLoader;
    }

    public Object getStaticField(String str, String str2) {
        Object obj = null;
        try {
            Field field = this.mClassLoader.loadClass(str).getField(str2);
            field.setAccessible(true);
            obj = field.get(null);
        } catch (Throwable th) {
            Log.e(getClass().getSimpleName(), "'" + str + "' get field '" + str2 + "' failed", th);
        }
        return obj;
    }

    public Object invokeMethod(Object obj, String str, String str2, Class<?>[] clsArr, Object... objArr) {
        try {
            Method method = this.mClassLoader.loadClass(str).getMethod(str2, clsArr);
            method.setAccessible(true);
            return method.invoke(obj, objArr);
        } catch (Throwable th) {
            Log.e(getClass().getSimpleName(), "'" + str + "' invoke method '" + str2 + "' failed", th);
            return null;
        }
    }

    public Object invokeStaticMethod(String str, String str2, Class<?>[] clsArr, Object... objArr) {
        try {
            Method method = this.mClassLoader.loadClass(str).getMethod(str2, clsArr);
            method.setAccessible(true);
            return method.invoke(null, objArr);
        } catch (Throwable th) {
            if (str2 == null || !str2.equalsIgnoreCase("initTesRuntimeEnvironment")) {
                Log.e(getClass().getSimpleName(), "'" + str + "' invoke static method '" + str2 + "' failed", th);
                return null;
            }
            Log.e(getClass().getSimpleName(), "'" + str + "' invoke static method '" + str2 + "' failed", th);
            return th;
        }
    }

    public Class<?> loadClass(String str) {
        try {
            return this.mClassLoader.loadClass(str);
        } catch (Throwable th) {
            Log.e(getClass().getSimpleName(), "loadClass '" + str + "' failed", th);
            return null;
        }
    }

    public Object newInstance(String str) {
        try {
            return this.mClassLoader.loadClass(str).newInstance();
        } catch (Throwable th) {
            Log.e(getClass().getSimpleName(), "create " + str + " instance failed", th);
            return null;
        }
    }

    public Object newInstance(String str, Class<?>[] clsArr, Object... objArr) {
        try {
            return this.mClassLoader.loadClass(str).getConstructor(clsArr).newInstance(objArr);
        } catch (Throwable th) {
            if ("com.tencent.smtt.webkit.adapter.X5WebViewAdapter".equalsIgnoreCase(str)) {
                Log.e(getClass().getSimpleName(), "'newInstance " + str + " failed", th);
                return th;
            }
            Log.e(getClass().getSimpleName(), "create '" + str + "' instance failed", th);
            return null;
        }
    }

    public void setStaticField(String str, String str2, Object obj) {
        try {
            Field field = this.mClassLoader.loadClass(str).getField(str2);
            field.setAccessible(true);
            field.set(null, obj);
        } catch (Throwable th) {
            Log.e(getClass().getSimpleName(), "'" + str + "' set field '" + str2 + "' failed", th);
        }
    }
}
