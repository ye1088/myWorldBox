package com.huawei.android.pushagent.a.b;

import android.text.TextUtils;
import com.huawei.android.pushagent.a.b.a.b;
import com.huawei.android.pushagent.c.a;
import com.huawei.android.pushagent.c.a.e;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class h extends b {
    private String b = null;
    private byte c = (byte) -1;
    private int d;
    private long e;
    private long f;
    private long g;
    private int h;
    private g[] i;

    public h() {
        super(c());
    }

    public h(String str, byte b, int i, long j, long j2, long j3, int i2, g[] gVarArr) {
        super(c());
        this.b = str;
        this.c = b;
        this.d = i;
        this.e = j;
        this.f = j2;
        this.g = j3;
        this.h = i2;
        if (gVarArr != null && gVarArr.length > 0) {
            this.i = new g[gVarArr.length];
            System.arraycopy(gVarArr, 0, this.i, 0, gVarArr.length);
        }
    }

    public static byte c() {
        return (byte) -34;
    }

    public b a(InputStream inputStream) throws Exception {
        byte[] bArr = new byte[16];
        b.a(inputStream, bArr);
        this.b = new String(bArr, "UTF-8");
        bArr = new byte[1];
        b.a(inputStream, bArr);
        this.c = bArr[0];
        bArr = new byte[2];
        b.a(inputStream, bArr);
        this.d = a.c(bArr);
        bArr = new byte[8];
        b.a(inputStream, bArr);
        this.e = a.d(bArr);
        bArr = new byte[8];
        b.a(inputStream, bArr);
        this.f = a.d(bArr);
        bArr = new byte[8];
        b.a(inputStream, bArr);
        this.g = a.d(bArr);
        bArr = new byte[1];
        b.a(inputStream, bArr);
        this.h = a.c(bArr);
        int i = this.h & 127;
        if (i > 0) {
            this.i = new g[i];
            for (g gVar : this.i) {
                byte[] bArr2 = new byte[8];
                b.a(inputStream, bArr2);
                gVar.a(a.d(bArr2));
                bArr2 = new byte[1];
                b.a(inputStream, bArr2);
                gVar.a(bArr2[0]);
                bArr2 = new byte[1];
                b.a(inputStream, bArr2);
                gVar.b(bArr2[0]);
            }
        }
        return this;
    }

    public byte[] b() {
        if (TextUtils.isEmpty(this.b)) {
            e.d("PushLogAC2705", "encode error, reason mDeviceId = " + this.b);
            return new byte[0];
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(a());
            byteArrayOutputStream.write(this.b.getBytes("UTF-8"));
            byteArrayOutputStream.write(this.c);
            byteArrayOutputStream.write(a.b(this.d));
            byteArrayOutputStream.write(a.a(this.e));
            byteArrayOutputStream.write(a.a(this.f));
            byteArrayOutputStream.write(a.a(this.g));
            byteArrayOutputStream.write((byte) this.h);
            if (this.i != null && this.i.length > 0) {
                for (g gVar : this.i) {
                    byteArrayOutputStream.write(a.a(gVar.a()));
                    byteArrayOutputStream.write(gVar.b());
                    byteArrayOutputStream.write(gVar.c());
                }
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.d("PushLogAC2705", "encode error " + e.toString());
            return new byte[0];
        }
    }

    public String toString() {
        StringBuffer append = new StringBuffer(getClass().getSimpleName()).append(" cmdId:").append(j()).append(" mDeviceId:").append(this.b).append(" mNetworkType:").append(this.c).append(" mAgentVersion:").append(this.d).append(" mLastDisconnectTime:").append(a.a(this.e, "yyyy-MM-dd HH:mm:ss SSS")).append(" mCurrentConnectTime:").append(a.a(this.f, "yyyy-MM-dd HH:mm:ss SSS")).append(" mCurrentTime:").append(a.a(this.g, "yyyy-MM-dd HH:mm:ss SSS")).append(" mNetEventAccount:").append(this.h);
        if (this.i != null && this.i.length > 0) {
            for (Object append2 : this.i) {
                append.append(append2);
            }
        }
        return append.toString();
    }
}
