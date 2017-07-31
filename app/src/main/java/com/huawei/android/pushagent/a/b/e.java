package com.huawei.android.pushagent.a.b;

import com.huawei.android.pushagent.a.b.a.b;
import java.io.IOException;
import java.io.InputStream;

public class e extends b {
    public e() {
        super(c());
    }

    public static byte c() {
        return (byte) -48;
    }

    public b a(InputStream inputStream) throws IOException {
        b.a(inputStream, new byte[1]);
        return this;
    }

    public byte[] b() {
        return new byte[]{a()};
    }

    public String toString() {
        return new StringBuffer(getClass().getSimpleName()).append(" cmdId:").append(j()).toString();
    }
}
