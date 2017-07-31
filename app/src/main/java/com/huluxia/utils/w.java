package com.huluxia.utils;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: UtilsDescode */
public class w {
    private static final String bkW = "hlxsystem";
    private Cipher bkU;
    private Cipher bkV;

    public static String z(byte[] arrIn) {
        if (arrIn == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int intTmp : arrIn) {
            int intTmp2;
            while (intTmp2 < 0) {
                intTmp2 += 256;
            }
            if (intTmp2 < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp2, 16));
        }
        return sb.toString();
    }

    public static byte[] fC(String strIn) {
        if (strIn == null) {
            return null;
        }
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        byte[] arrOut = new byte[(iLen / 2)];
        for (int i = 0; i < iLen; i += 2) {
            arrOut[i / 2] = (byte) Integer.parseInt(new String(arrB, i, 2), 16);
        }
        return arrOut;
    }

    public w() {
        this(bkW);
    }

    public w(String strKey) {
        this.bkU = null;
        this.bkV = null;
        try {
            Key key = A(strKey.getBytes());
            this.bkU = Cipher.getInstance("DES");
            this.bkU.init(1, key);
            this.bkV = Cipher.getInstance("DES");
            this.bkV.init(2, key);
        } catch (Exception e) {
        }
    }

    private Key A(byte[] arrKey) throws Exception {
        byte[] arrB = new byte[8];
        int i = 0;
        while (i < arrKey.length && i < arrB.length) {
            arrB[i] = arrKey[i];
            i++;
        }
        return new SecretKeySpec(arrB, "DES");
    }

    public byte[] encrypt(byte[] arrIn) {
        try {
            return this.bkU.doFinal(arrIn);
        } catch (Exception e) {
            return null;
        }
    }

    public String encrypt(String strIn) {
        return z(encrypt(strIn.getBytes()));
    }

    public byte[] decrypt(byte[] arrIn) {
        try {
            return this.bkV.doFinal(arrIn);
        } catch (Exception e) {
            return null;
        }
    }

    public String fD(String strIn) {
        return new String(decrypt(fC(strIn)));
    }
}
