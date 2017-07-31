package com.huawei.android.pushagent.a.a.a;

import com.huawei.android.pushagent.a.a.b;
import com.huawei.android.pushagent.c.a.e;
import java.io.InputStream;
import java.util.HashMap;

public class a {
    private static HashMap a = new HashMap();

    static {
        a.put(Byte.valueOf((byte) 1), com.huawei.android.pushagent.a.a.a.class);
        a.put(Byte.valueOf((byte) 2), b.class);
    }

    public static b a(Byte b, InputStream inputStream) throws InstantiationException, IllegalAccessException, Exception {
        if (a.containsKey(b)) {
            b bVar = (b) ((Class) a.get(b)).newInstance();
            b a = bVar.a(inputStream);
            if (a != null) {
                e.b("PushLogAC2705", "after decode msg:" + a);
            } else {
                e.d("PushLogAC2705", "call " + bVar.getClass().getSimpleName() + " decode failed!");
            }
            return a;
        }
        e.d("PushLogAC2705", "cmdId:" + b + " is not exist, all:" + a.keySet());
        throw new InstantiationException("cmdId:" + b + " is not register");
    }
}
