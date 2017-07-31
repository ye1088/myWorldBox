package com.huawei.android.pushagent.a.b;

import android.text.TextUtils;
import com.huawei.android.pushagent.a.b.a.b;
import com.huawei.android.pushagent.c.a;
import com.huawei.android.pushagent.c.a.e;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class n extends b {
    private String b = null;
    private String c = null;

    public n() {
        super(c());
    }

    public n(String str, String str2) {
        super(c());
        this.b = str;
        this.c = str2;
    }

    public static byte c() {
        return (byte) -36;
    }

    public b a(InputStream inputStream) throws Exception {
        byte[] bArr = new byte[16];
        b.a(inputStream, bArr);
        this.b = new String(bArr, "UTF-8");
        bArr = new byte[2];
        b.a(inputStream, bArr);
        bArr = new byte[a.c(bArr)];
        b.a(inputStream, bArr);
        this.c = new String(bArr, "UTF-8");
        return this;
    }

    public byte[] b() {
        byte[] bArr = new byte[0];
        try {
            if (TextUtils.isEmpty(this.b)) {
                e.d("PushLogAC2705", "encode error mDeviceId = " + this.b);
            } else if (TextUtils.isEmpty(this.c)) {
                e.d("PushLogAC2705", "encode error mPackageName = " + this.c);
            } else {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byteArrayOutputStream.write(a());
                byteArrayOutputStream.write(this.b.getBytes("UTF-8"));
                byteArrayOutputStream.write(a.b(this.c.length()));
                byteArrayOutputStream.write(this.c.getBytes("UTF-8"));
                bArr = byteArrayOutputStream.toByteArray();
            }
        } catch (IOException e) {
            e.d("PushLogAC2705", "encode error " + e.toString());
        }
        return bArr;
    }

    public String toString() {
        return new StringBuffer().append("RegisterTokenReqMessage[").append("deviceId:").append(this.b).append(",packageName:").append(this.c).append("]").toString();
    }
}
