package com.xiaomi.xmpush.thrift;

import android.content.Context;
import com.xiaomi.channel.commonutils.android.b;
import com.xiaomi.channel.commonutils.misc.c;
import org.apache.thrift.e;
import org.apache.thrift.f;
import org.apache.thrift.g;
import org.apache.thrift.protocol.l.a;

public class ad {
    public static short a(Context context, String str) {
        int i = 0;
        int a = (c.b(context) ? 4 : 0) + (0 + b.d(context, str).a());
        if (c.a(context)) {
            i = 8;
        }
        return (short) (a + i);
    }

    public static <T extends org.apache.thrift.b<T, ?>> void a(T t, byte[] bArr) {
        if (bArr == null) {
            throw new f("the message byte is empty.");
        }
        new e(new a(true, true, bArr.length)).a(t, bArr);
    }

    public static <T extends org.apache.thrift.b<T, ?>> byte[] a(T t) {
        byte[] bArr = null;
        if (t != null) {
            try {
                bArr = new g(new org.apache.thrift.protocol.a.a()).a(t);
            } catch (Throwable e) {
                com.xiaomi.channel.commonutils.logger.b.a("convertThriftObjectToBytes catch TException.", e);
            }
        }
        return bArr;
    }
}
