package com.huawei.android.pushagent.a.b;

import com.huawei.android.pushagent.a.b.a.b;
import java.io.IOException;
import java.io.InputStream;

public class j extends b {
    private byte b = (byte) 10;

    public j() {
        super(c());
    }

    public static byte c() {
        return (byte) -38;
    }

    public b a(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[1];
        b.a(inputStream, bArr);
        this.b = bArr[0];
        return this;
    }

    public void a(byte b) {
        this.b = b;
    }

    public byte[] b() {
        return new byte[]{a(), d()};
    }

    public byte d() {
        return this.b;
    }

    public String toString() {
        return new StringBuffer(getClass().getSimpleName()).append(" cmdId:").append(j()).append(" NextHeartBeatToServer:").append(this.b).toString();
    }
}
