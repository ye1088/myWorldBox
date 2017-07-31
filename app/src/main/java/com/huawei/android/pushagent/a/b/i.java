package com.huawei.android.pushagent.a.b;

import com.huawei.android.pushagent.a.b.a.b;
import java.io.IOException;
import java.io.InputStream;

public class i extends b {
    private byte b = (byte) 1;

    public i() {
        super(c());
    }

    public static byte c() {
        return (byte) -33;
    }

    public b a(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[1];
        b.a(inputStream, bArr);
        this.b = bArr[0];
        return this;
    }

    public byte[] b() {
        return new byte[]{a(), this.b};
    }

    public byte d() {
        return this.b;
    }

    public String toString() {
        return new StringBuffer(getClass().getSimpleName()).append(" cmdId:").append(j()).append(" result:").append(this.b).toString();
    }
}
