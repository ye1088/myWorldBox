package com.huawei.android.pushagent.a.b;

import com.huawei.android.pushagent.a.b.a.b;
import com.huawei.android.pushagent.c.a;
import com.huawei.android.pushagent.c.a.e;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class l extends b {
    private byte b = (byte) -1;
    private byte[] c;
    private byte[] d;
    private int e;
    private byte[] f;
    private int g;
    private byte[] h;

    public l() {
        super(c());
    }

    public static final byte c() {
        return (byte) -96;
    }

    public b a(InputStream inputStream) throws Exception {
        this.c = new byte[8];
        b.a(inputStream, this.c);
        this.d = new byte[32];
        b.a(inputStream, this.d);
        byte[] bArr = new byte[2];
        b.a(inputStream, bArr);
        int c = a.c(bArr);
        e.a("PushLogAC2705", "push message len=" + c);
        this.e = c;
        this.f = new byte[c];
        b.a(inputStream, this.f);
        bArr = new byte[2];
        try {
            bArr[0] = (byte) inputStream.read();
            if (bArr[0] < (byte) 0) {
                e.b("PushLogAC2705", "read first Len:" + bArr[0] + ", not valid len, may be next cmdId in Old PushDataReqMessage");
                this.b = bArr[0];
            } else {
                bArr[1] = (byte) inputStream.read();
                c = bArr[1] + bArr[0];
                e.b("PushLogAC2705", "mPackageNameLen=" + c);
                if (c <= 0) {
                    e.b("PushLogAC2705", "the package length:" + c + " is Unavailable ");
                } else {
                    this.g = c;
                    this.h = new byte[c];
                    b.a(inputStream, this.h);
                }
            }
        } catch (Exception e) {
            e.b("PushLogAC2705", "read msg cause:" + e.toString() + " may be old PushDataReqMessage");
        }
        return this;
    }

    public byte[] b() {
        byte[] bArr = new byte[0];
        try {
            if (this.c == null) {
                e.d("PushLogAC2705", "encode error, mMsgId = null");
                return bArr;
            } else if (this.d == null) {
                e.d("PushLogAC2705", "encode error, reason mToken = null");
                return bArr;
            } else if (this.f == null) {
                e.d("PushLogAC2705", "encode error, reason mMsgData = null");
                return bArr;
            } else if (this.h == null) {
                e.d("PushLogAC2705", "encode error, reason mPackage = null");
                return bArr;
            } else {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byteArrayOutputStream.write(a());
                byteArrayOutputStream.write(this.c);
                byteArrayOutputStream.write(this.d);
                byteArrayOutputStream.write(a.b(this.f.length));
                byteArrayOutputStream.write(this.f);
                byte[] bArr2 = new byte[2];
                if (this.g > 127) {
                    bArr2[0] = Byte.MAX_VALUE;
                    bArr2[1] = (byte) (this.g - 127);
                } else {
                    bArr2[0] = (byte) 0;
                    bArr2[1] = (byte) this.g;
                }
                byteArrayOutputStream.write(bArr2);
                byteArrayOutputStream.write(this.h);
                return byteArrayOutputStream.toByteArray();
            }
        } catch (Exception e) {
            e.a("PushLogAC2705", "encode error," + e.toString());
            return bArr;
        }
    }

    public byte[] d() {
        return this.c;
    }

    public byte[] e() {
        return this.d;
    }

    public byte[] f() {
        return this.f;
    }

    public int g() {
        return this.g;
    }

    public byte[] h() {
        return this.h;
    }

    public byte i() {
        return this.b;
    }

    public String toString() {
        String str;
        String str2 = "null";
        if (this.h != null) {
            try {
                str = new String(this.h, "UTF-8");
            } catch (Throwable e) {
                e.a("PushLogAC2705", e.toString(), e);
            }
            return new StringBuffer(getClass().getSimpleName()).append(" cmdId:").append(j()).append(",msgId:").append(a.a(this.c)).append(",deviceToken:").append(a.a(this.d)).append(",msgData:").append(a.a(this.f)).append(", mPackageLen:").append(this.g).append(", pkgName:").append(str).toString();
        }
        str = str2;
        return new StringBuffer(getClass().getSimpleName()).append(" cmdId:").append(j()).append(",msgId:").append(a.a(this.c)).append(",deviceToken:").append(a.a(this.d)).append(",msgData:").append(a.a(this.f)).append(", mPackageLen:").append(this.g).append(", pkgName:").append(str).toString();
    }
}
