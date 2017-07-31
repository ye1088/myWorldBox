package com.huluxia.mcsdk.crypto;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: DT3DES */
public class a {
    private static String lT = "wefjweofjewofwe";
    private static final String lV = "DESede";

    public static String CL() {
        return lT;
    }

    public static void dF(String pASSWORD_CRYPT_KEY) {
        lT = pASSWORD_CRYPT_KEY;
    }

    public static byte[] x(byte[] src) {
        try {
            SecretKey deskey = new SecretKeySpec(dG(lT), lV);
            Cipher cipher = Cipher.getInstance(lV);
            cipher.init(1, deskey);
            return cipher.doFinal(src);
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
            return null;
        } catch (NoSuchPaddingException e2) {
            e2.printStackTrace();
            return null;
        } catch (Exception e3) {
            e3.printStackTrace();
            return null;
        }
    }

    public static byte[] y(byte[] src) {
        try {
            SecretKey deskey = new SecretKeySpec(dG(lT), lV);
            Cipher c1 = Cipher.getInstance(lV);
            c1.init(2, deskey);
            return c1.doFinal(src);
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
            return null;
        } catch (NoSuchPaddingException e2) {
            e2.printStackTrace();
            return null;
        } catch (Exception e3) {
            e3.printStackTrace();
            return null;
        }
    }

    public static byte[] dG(String keyStr) throws UnsupportedEncodingException {
        byte[] key = new byte[24];
        byte[] temp = keyStr.getBytes("UTF-8");
        if (key.length > temp.length) {
            System.arraycopy(temp, 0, key, 0, temp.length);
        } else {
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        return key;
    }

    public static void dC() {
    }
}
