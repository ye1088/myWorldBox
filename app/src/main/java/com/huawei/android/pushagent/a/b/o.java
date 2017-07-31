package com.huawei.android.pushagent.a.b;

import com.huawei.android.pushagent.a.b.a.b;
import com.huawei.android.pushagent.c.a;
import com.huawei.android.pushagent.c.a.e;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class o extends b {
    private byte b = (byte) 1;
    private String c = null;
    private String d = null;

    public o() {
        super(c());
    }

    public static byte c() {
        return (byte) -35;
    }

    public b a(InputStream inputStream) throws Exception {
        byte[] bArr = new byte[1];
        b.a(inputStream, bArr);
        this.b = bArr[0];
        if (bArr[0] != (byte) 0) {
            this.d = null;
            this.c = null;
        }
        bArr = new byte[32];
        b.a(inputStream, bArr);
        this.c = new String(bArr, "UTF-8");
        bArr = new byte[2];
        b.a(inputStream, bArr);
        bArr = new byte[a.c(bArr)];
        b.a(inputStream, bArr);
        this.d = new String(bArr, "UTF-8");
        return this;
    }

    public byte[] b() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(a());
            byteArrayOutputStream.write(0);
            byteArrayOutputStream.write(this.c.getBytes("UTF-8"));
            byteArrayOutputStream.write(a.b(this.d.length()));
            byteArrayOutputStream.write(this.d.getBytes("UTF-8"));
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.d("PushLogAC2705", "encode error,e " + e.toString());
            return new byte[0];
        }
    }

    public String d() {
        return this.c;
    }

    public String e() {
        return this.d;
    }

    public byte f() {
        return this.b;
    }

    public String toString() {
        return new StringBuffer().append("RegisterTokenRspMessage[").append("result:").append(a.a(this.b)).append(",token:").append(this.c).append(",packageName:").append(this.d).append("]").toString();
    }
}
