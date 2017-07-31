package com.huawei.android.pushagent.a.a;

import com.huawei.android.pushagent.c.a;
import com.huawei.android.pushagent.c.a.e;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class b extends com.huawei.android.pushagent.a.a.a.b {
    private short b;
    private short c;
    private byte d;
    private byte e;
    private short f;

    public b() {
        super(c());
    }

    public static byte c() {
        return (byte) 2;
    }

    public com.huawei.android.pushagent.a.a.a.b a(InputStream inputStream) throws IOException {
        try {
            byte[] bArr = new byte[2];
            com.huawei.android.pushagent.a.a.a.b.a(inputStream, bArr);
            this.c = (short) a.c(bArr);
            bArr = new byte[1];
            com.huawei.android.pushagent.a.a.a.b.a(inputStream, bArr);
            this.d = bArr[0];
            bArr = new byte[1];
            com.huawei.android.pushagent.a.a.a.b.a(inputStream, bArr);
            this.e = bArr[0];
            bArr = new byte[2];
            com.huawei.android.pushagent.a.a.a.b.a(inputStream, bArr);
            this.f = (short) a.c(bArr);
        } catch (Throwable e) {
            e.c("PushLogAC2705", e.toString(), e);
        }
        return this;
    }

    public byte[] b() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(a.b(this.b));
            byteArrayOutputStream.write(a());
            byteArrayOutputStream.write(a.b(this.c));
            byteArrayOutputStream.write(this.d);
            byteArrayOutputStream.write(this.e);
            byteArrayOutputStream.write(a.b(this.f));
            e.a("PushLogAC2705", "PollingDataRspMessage encode : baos " + a.a(byteArrayOutputStream.toByteArray()));
            return byteArrayOutputStream.toByteArray();
        } catch (Throwable e) {
            e.c("PushLogAC2705", e.toString(), e);
            return new byte[0];
        }
    }

    public byte d() {
        return this.d;
    }

    public boolean e() {
        return this.e == (byte) 1;
    }

    public short f() {
        return this.f;
    }

    public String toString() {
        return new StringBuffer(getClass().getSimpleName()).append(",mLength:").append(this.b).append(",cmdId:").append(g()).append(",mRequestId:").append(this.c).append(",mMode:").append(this.d).append(",mHasMsg:").append(this.e).append(",mPollingInterval:").append(this.f).toString();
    }
}
