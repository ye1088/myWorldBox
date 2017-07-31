package com.baidu.frontia;

import android.content.Context;
import com.baidu.frontia.framework.IModule;
import java.lang.reflect.Method;

class a {
    a() {
    }

    public static void a(Context context, String str) {
        try {
            String[] a = a();
            if (a != null) {
                for (String str2 : a) {
                    if (str2 != null && str2.length() > 0) {
                        Class cls = Class.forName(str2);
                        if (cls != null) {
                            Method method = cls.getMethod("newInstance", new Class[]{Context.class});
                            if (method != null) {
                                IModule iModule = (IModule) method.invoke(null, new Object[]{context});
                                if (iModule != null) {
                                    iModule.init(str);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public static String[] a() {
        return new String[]{"com.baidu.frontia.api.FrontiaPush"};
    }
}
