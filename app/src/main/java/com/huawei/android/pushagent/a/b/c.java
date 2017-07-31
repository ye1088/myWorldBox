package com.huawei.android.pushagent.a.b;

import android.text.TextUtils;
import com.huawei.android.pushagent.a.b.a.b;
import com.huawei.android.pushagent.c.a.e;
import java.io.InputStream;

public class c extends b {
    private String b = null;
    private byte c = (byte) -1;

    public c() {
        super(c());
    }

    public static byte c() {
        return (byte) -46;
    }

    public b a(InputStream inputStream) throws Exception {
        byte[] bArr = new byte[16];
        b.a(inputStream, bArr);
        this.b = new String(bArr, "UTF-8");
        bArr = new byte[1];
        b.a(inputStream, bArr);
        this.c = bArr[0];
        return this;
    }

    public byte[] b() {
        byte[] bArr = new byte[0];
        if (TextUtils.isEmpty(this.b)) {
            e.d("PushLogAC2705", "encode error, reason mDeviceId = " + this.b);
        } else {
            try {
                Object bytes = this.b.getBytes("UTF-8");
                bArr = new byte[((bytes.length + 1) + 1)];
                bArr[0] = a();
                System.arraycopy(bytes, 0, bArr, 1, bytes.length);
                bArr[bArr.length - 1] = this.c;
            } catch (Throwable e) {
                e.a("PushLogAC2705", e.toString(), e);
            }
        }
        return bArr;
    }

    public String toString() {
        return new StringBuffer(getClass().getSimpleName()).append(" cmdId:").append(j()).append(" mDeviceId:").append(this.b).append(" mNetworkType:").append(this.c).toString();
    }
}
