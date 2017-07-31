package com.huawei.android.pushagent.c.a.a;

import android.util.Base64;
import com.huluxia.framework.base.utils.UtilsRSA;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

public class g {
    private static PublicKey a(String str) throws Exception {
        return KeyFactory.getInstance(UtilsRSA.KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(Base64.decode(str, 0)));
    }

    public static byte[] a(byte[] bArr, String str) throws Exception {
        Cipher instance = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
        instance.init(1, a(str));
        return instance.doFinal(bArr);
    }
}
