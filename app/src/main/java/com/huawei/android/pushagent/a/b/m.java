package com.huawei.android.pushagent.a.b;

import com.huawei.android.pushagent.a.b.a.b;
import com.huawei.android.pushagent.c.a;
import com.huawei.android.pushagent.c.a.e;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class m extends b {
    private byte[] b;
    private byte c;

    public m() {
        super(c());
    }

    public m(byte[] bArr, byte b) {
        this();
        this.b = new byte[bArr.length];
        System.arraycopy(bArr, 0, this.b, 0, bArr.length);
        this.c = b;
    }

    public static byte c() {
        return (byte) -95;
    }

    public b a(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[8];
        b.a(inputStream, bArr);
        this.b = bArr;
        bArr = new byte[1];
        b.a(inputStream, bArr);
        this.c = bArr[0];
        return this;
    }

    public byte[] b() {
        byte[] bArr = new byte[0];
        if (this.b == null) {
            e.d("PushLogAC2705", "encode error, mMsgId is null ");
        } else {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byteArrayOutputStream.write(a());
                byteArrayOutputStream.write(this.b);
                byteArrayOutputStream.write(this.c);
                bArr = byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                e.d("PushLogAC2705", "encode error " + e.toString());
            }
        }
        return bArr;
    }

    public String toString() {
        return new StringBuffer(getClass().getSimpleName()).append(",cmdId:").append(j()).append(",msgId:").append(a.a(this.b)).append(",flag:").append(this.c).toString();
    }
}
