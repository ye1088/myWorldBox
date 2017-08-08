package com.MCWorld.jni;

import android.util.Log;
import com.MCWorld.aa.c;
import com.MCWorld.aa.d;
import com.MCWorld.aa.e;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class UtilsEncrypt {
    private static String TAG = UtilsEncrypt.class.getName();

    public static native String stringFromJNI();

    static {
        if (d.dD().getContext() == null) {
            throw new IllegalArgumentException("UtilsEncrypt context can not be null");
        }
        c.dB().ax(d.dD().getContext());
    }

    public static EncryptItem encrpytLogin(String account, String passwd, String openId, String stamp) {
        String input = getCRC(account, passwd, openId, stamp);
        return new EncryptItem(c.dB().Y(input), input.length());
    }

    public static EncryptItem encrpytRegister(String account, String passwd, String openId, String token) {
        String input = getCRC(account, passwd, openId, token);
        return new EncryptItem(c.dB().Z(input), input.length());
    }

    public static EncryptItem encrpytToken(String token) {
        String input = getEcode(token);
        return new EncryptItem(c.dB().ab(input), input.length());
    }

    public static EncryptItem encrpytEmail(String email) {
        String input = getEcode(email);
        return new EncryptItem(c.dB().ac(input), input.length());
    }

    public static String decrpytEmail(String input, int len) {
        return getDcode(c.dB().p(input, len));
    }

    public static String encrpytEmailForLastLogin(String email) {
        return getEcode(email);
    }

    public static EncryptItem encrpytPasswd(String passwd) {
        String input = getEcode(passwd);
        return new EncryptItem(c.dB().ad(input), input.length());
    }

    public static String decrpytPasswd(String input, int len) {
        return getDcode(c.dB().q(input, len));
    }

    private static String buildCRC(String input) {
        StringBuilder sb = new StringBuilder();
        sb.append(fillLeftZero(Integer.toHexString(input.length()).toUpperCase(), 8)).append(input);
        return sb.toString();
    }

    private static String fillLeftZero(String input, int size) {
        String _tmpSymbol = "0";
        while (input.length() < size) {
            input = "0" + input;
        }
        return input;
    }

    public static int radomInt() {
        return new Random().nextInt(1000000);
    }

    private static String getCRC(String para1, String para2, String para3, String para4) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(buildCRC(para1)).append(buildCRC(para2)).append(buildCRC(para3)).append(buildCRC(para4));
        } catch (Exception e) {
        }
        Log.i(TAG, "before:" + sb.toString());
        String code = getEcode(sb.toString());
        Log.i(TAG, "ecode: ecodelen:" + code.length() + " ecode:" + code);
        return code;
    }

    public static String decodeLoginData(String input) {
        if (input == null || input.length() < 8) {
            return null;
        }
        int len1 = Integer.parseInt(input.substring(0, 8), 16);
        int start = 0 + 8;
        if (input.length() < len1 + 8) {
            return null;
        }
        Log.d("LKY", "email " + input.substring(start, len1 + 8));
        start = len1 + 8;
        if (input.length() < start + 8) {
            return null;
        }
        int len2 = Integer.parseInt(input.substring(start, start + 8), 16);
        start += 8;
        if (input.length() < start + len2) {
            return null;
        }
        Log.d("LKY", "passwd " + input.substring(start, start + len2));
        start += len2;
        if (input.length() < start + 8) {
            return null;
        }
        int len3 = Integer.parseInt(input.substring(start, start + 8), 16);
        start += 8;
        if (input.length() < start + len3) {
            return null;
        }
        Log.d("LKY", "openId " + input.substring(start, start + len3));
        start += len3;
        if (input.length() < start + 8) {
            return null;
        }
        int len4 = Integer.parseInt(input.substring(start, start + 8), 16);
        start += 8;
        if (input.length() < start + len4) {
            return null;
        }
        Log.d(TAG, "lastLogin " + input.substring(start, start + len4));
        if (input.length() != start + len4) {
            return null;
        }
        Log.d(TAG, "successs ");
        return "successs";
    }

    private static String getEcode(String str) {
        if (str == null) {
            return str;
        }
        char[] array = str.toCharArray();
        for (int i = 0; i < array.length; i++) {
            array[i] = (char) (array[i] ^ 1984);
        }
        byte[] b = null;
        String s = null;
        try {
            b = new String(array).getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            try {
                e eVar = new e();
                s = e.encode(b);
            } catch (Exception e2) {
                Log.e(TAG, e2.toString());
            }
        }
        return s;
    }

    private static String getDcode(String str) {
        if (str == null) {
            return str;
        }
        String s = null;
        if (str != null) {
            try {
                s = new String(e.decode(str), "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (s == null) {
            return null;
        }
        char[] array = s.toCharArray();
        for (int i = 0; i < array.length; i++) {
            array[i] = (char) (array[i] ^ 1984);
        }
        return new String(array);
    }
}
