package com.huawei.android.pushagent.a.b.a;

import com.huawei.android.pushagent.a.b.b;
import com.huawei.android.pushagent.a.b.c;
import com.huawei.android.pushagent.a.b.d;
import com.huawei.android.pushagent.a.b.e;
import com.huawei.android.pushagent.a.b.f;
import com.huawei.android.pushagent.a.b.h;
import com.huawei.android.pushagent.a.b.i;
import com.huawei.android.pushagent.a.b.j;
import com.huawei.android.pushagent.a.b.k;
import com.huawei.android.pushagent.a.b.l;
import com.huawei.android.pushagent.a.b.m;
import com.huawei.android.pushagent.a.b.n;
import com.huawei.android.pushagent.a.b.o;
import com.huawei.android.pushagent.a.b.p;
import com.huawei.android.pushagent.a.b.q;
import java.io.InputStream;
import java.util.HashMap;

public class a {
    private static HashMap a = new HashMap();

    static {
        a.put(Byte.valueOf((byte) -47), f.class);
        a.put(Byte.valueOf((byte) -37), k.class);
        a.put(Byte.valueOf((byte) -45), d.class);
        a.put(Byte.valueOf((byte) -33), i.class);
        a.put(Byte.valueOf((byte) -35), o.class);
        a.put(Byte.valueOf((byte) -41), q.class);
        a.put(Byte.valueOf((byte) -96), l.class);
        a.put(Byte.valueOf((byte) -48), e.class);
        a.put(Byte.valueOf((byte) -38), j.class);
        a.put(Byte.valueOf((byte) -46), c.class);
        a.put(Byte.valueOf((byte) -34), h.class);
        a.put(Byte.valueOf((byte) -36), n.class);
        a.put(Byte.valueOf((byte) -42), p.class);
        a.put(Byte.valueOf((byte) -95), m.class);
        a.put(Byte.valueOf((byte) -92), b.class);
        a.put(Byte.valueOf((byte) -91), b.class);
        a.put(Byte.valueOf((byte) -90), b.class);
        a.put(Byte.valueOf((byte) -89), b.class);
    }

    public static b a(Byte b, InputStream inputStream) throws InstantiationException, IllegalAccessException, Exception {
        if (a.containsKey(b)) {
            b bVar = (b) ((Class) a.get(b)).newInstance();
            if (bVar.a() == (byte) -1) {
                bVar.b(b.byteValue());
            }
            b a = bVar.a(inputStream);
            if (a != null) {
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "after decode msg:" + com.huawei.android.pushagent.c.a.a(a.a()));
            } else {
                com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "call " + bVar.getClass().getSimpleName() + " decode failed!");
            }
            return a;
        }
        com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "cmdId:" + b + " is not exist, all:" + a.keySet());
        throw new InstantiationException("cmdId:" + b + " is not register");
    }
}
