package com.huawei.android.pushagent.a.a;

import com.huawei.android.pushagent.a.a.a.b;
import com.huawei.android.pushagent.c.a.e;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class a extends b {
    private short b;
    private short c = ((short) (com.huawei.android.pushagent.c.a.a().hashCode() & 255));
    private int d;

    public a(int i) {
        super(c());
        this.d = i;
        this.b = (short) 7;
    }

    private static byte c() {
        return (byte) 1;
    }

    public b a(InputStream inputStream) throws IOException {
        try {
            byte[] bArr = new byte[2];
            b.a(inputStream, bArr);
            this.c = (short) com.huawei.android.pushagent.c.a.c(bArr);
            bArr = new byte[4];
            b.a(inputStream, bArr);
            this.d = com.huawei.android.pushagent.c.a.b(bArr);
        } catch (Throwable e) {
            e.c("PushLogAC2705", e.toString(), e);
        }
        return this;
    }

    public byte[] b() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(com.huawei.android.pushagent.c.a.b(this.b));
            byteArrayOutputStream.write(a());
            byteArrayOutputStream.write(com.huawei.android.pushagent.c.a.b(this.c));
            byteArrayOutputStream.write(com.huawei.android.pushagent.c.a.a(this.d));
            e.a("PushLogAC2705", "PollingDataReqMessage encode : baos " + com.huawei.android.pushagent.c.a.a(byteArrayOutputStream.toByteArray()));
            return byteArrayOutputStream.toByteArray();
        } catch (Throwable e) {
            e.c("PushLogAC2705", e.toString(), e);
            return new byte[0];
        }
    }

    public String toString() {
        return new StringBuffer(getClass().getSimpleName()).append(" mLength:").append(this.b).append(" cmdId:").append(g()).append(" mRequestId:").append(this.c).append(" mPollingId:").append(this.d).toString();
    }
}
