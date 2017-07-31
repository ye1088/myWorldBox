package com.huawei.android.pushagent.c.a.a;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushagent.plugin.tools.BLocation;

public class f {
    public static String a(Context context, String str) {
        return TextUtils.isEmpty(str) ? "" : a.a(str);
    }

    public static byte[] a(byte[] bArr, String str) {
        byte[] bArr2 = null;
        try {
            bArr2 = g.a(bArr, str);
        } catch (Throwable e) {
            e.c(BLocation.TAG, "rsa encrypt data error ", e);
        }
        return bArr2;
    }

    public static byte[] a(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = null;
        try {
            bArr3 = c.a(bArr, 0, bArr2, 0);
        } catch (Throwable e) {
            e.c(BLocation.TAG, "InvalidKeyException:" + e.getMessage(), e);
        } catch (Throwable e2) {
            e.c(BLocation.TAG, "BadPaddingException:" + e2.getMessage(), e2);
        } catch (Throwable e22) {
            e.c(BLocation.TAG, "IllegalBlockSizeException:" + e22.getMessage(), e22);
        } catch (Throwable e222) {
            e.c(BLocation.TAG, "NoSuchAlgorithmException:" + e222.getMessage(), e222);
        } catch (Throwable e2222) {
            e.c(BLocation.TAG, "NoSuchPaddingException:" + e2222.getMessage(), e2222);
        } catch (Throwable e22222) {
            e.c(BLocation.TAG, "Exception:" + e22222.getMessage(), e22222);
        }
        return bArr3;
    }

    public static String b(Context context, String str) {
        return TextUtils.isEmpty(str) ? "" : a.b(str);
    }

    public static byte[] b(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = null;
        try {
            bArr3 = c.a(bArr, bArr2, 0);
        } catch (Throwable e) {
            e.c(BLocation.TAG, "InvalidKeyException:" + e.getMessage(), e);
        } catch (Throwable e2) {
            e.c(BLocation.TAG, "BadPaddingException:" + e2.getMessage(), e2);
        } catch (Throwable e22) {
            e.c(BLocation.TAG, "IllegalBlockSizeException:" + e22.getMessage(), e22);
        } catch (Throwable e222) {
            e.c(BLocation.TAG, "NoSuchAlgorithmException:" + e222.getMessage(), e222);
        } catch (Throwable e2222) {
            e.c(BLocation.TAG, "NoSuchPaddingException:" + e2222.getMessage(), e2222);
        } catch (Throwable e22222) {
            e.c(BLocation.TAG, "Exception:" + e22222.getMessage(), e22222);
        }
        return bArr3;
    }
}
