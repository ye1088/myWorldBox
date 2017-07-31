package com.baidu.android.silentupdate;

import android.content.Context;
import android.util.Log;
import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class b {
    private static final String a = "PushClassloader";

    b() {
    }

    public static ClassLoader a(String str, String str2, String str3, Context context) {
        try {
            return new DexClassLoader(str, str2, str3, context.getClassLoader());
        } catch (Exception e) {
            try {
                DexFile dexFile = new DexFile(context.getPackageResourcePath());
                Class cls = Class.forName("dalvik.system.DexFile");
                Method declaredMethod = cls.getDeclaredMethod("openDexFile", new Class[]{String.class, String.class, Integer.TYPE});
                declaredMethod.setAccessible(true);
                int intValue = ((Integer) declaredMethod.invoke(dexFile, new Object[]{str, str2, Integer.valueOf(0)})).intValue();
                Field declaredField = cls.getDeclaredField("mCookie");
                declaredField.setAccessible(true);
                declaredField.set(dexFile, Integer.valueOf(intValue));
                Field declaredField2 = cls.getDeclaredField("mFileName");
                declaredField2.setAccessible(true);
                declaredField2.set(dexFile, str);
                ClassLoader pathClassLoader = new PathClassLoader(context.getPackageResourcePath(), context.getClassLoader());
                declaredField2 = Class.forName("dalvik.system.BaseDexClassLoader").getDeclaredField("pathList");
                declaredField2.setAccessible(true);
                Object obj = declaredField2.get(pathClassLoader);
                declaredField = Class.forName("dalvik.system.DexPathList").getDeclaredField("dexElements");
                declaredField.setAccessible(true);
                Object[] objArr = (Object[]) declaredField.get(obj);
                if (objArr.length > 0) {
                    declaredField = Class.forName("dalvik.system.DexPathList$Element").getDeclaredField("dexFile");
                    declaredField.setAccessible(true);
                    declaredField.set(objArr[0], dexFile);
                    return pathClassLoader;
                }
            } catch (ClassNotFoundException e2) {
            } catch (NoSuchMethodException e3) {
            } catch (IllegalArgumentException e4) {
            } catch (IllegalAccessException e5) {
            } catch (NoSuchFieldException e6) {
            } catch (IOException e7) {
            } catch (InvocationTargetException e8) {
            }
            return null;
        }
    }

    public static boolean a(ClassLoader classLoader, ClassLoader classLoader2) {
        for (ClassLoader classLoader3 = classLoader2; classLoader3 != null; classLoader3 = classLoader3.getParent()) {
            if (classLoader3 == classLoader) {
                Log.d(a, "the classloader has been inserted");
                return true;
            }
        }
        try {
            Field declaredField = Class.forName("java.lang.ClassLoader").getDeclaredField("parent");
            declaredField.setAccessible(true);
            declaredField.set(classLoader, declaredField.get(classLoader2));
            declaredField.set(classLoader2, classLoader);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        } catch (SecurityException e2) {
            return false;
        } catch (NoSuchFieldException e3) {
            return false;
        } catch (IllegalArgumentException e4) {
            return false;
        } catch (IllegalAccessException e5) {
            return false;
        }
    }
}
