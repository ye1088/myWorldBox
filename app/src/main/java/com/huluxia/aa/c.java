package com.huluxia.aa;

import android.content.Context;
import android.util.Log;
import com.huluxia.video.recorder.b;
import com.j256.ormlite.stmt.query.SimpleComparison;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheOpcodes;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.tools.tar.TarConstants;

/* compiled from: DTHlxLoginEncode */
public class c {
    private static final boolean DEBUG = true;
    private static c lM = null;
    private static final String lN = "twjx2c2nmgx8";
    private static final String lO = "srvwd23rvgfs";
    private static final String lP = "dfeo54ggdfgdf";
    private static final String lQ = "kcwf2g3rvgfs";
    private static final String lR = "wfhr5y2d35a6";
    private static final String lS = "koiennw34345";
    private static String lT = "wefjweofjewofwe";
    private static int lU = 1345;
    private static final String lV = "DESede";
    private static final char[] lW = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private static byte[] lX = new byte[]{(byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) 62, (byte) -1, (byte) -1, (byte) -1, (byte) 63, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, (byte) 56, (byte) 57, HttpConstants.COLON, HttpConstants.SEMICOLON, (byte) 60, HttpConstants.EQUALS, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10, (byte) 11, (byte) 12, (byte) 13, (byte) 14, (byte) 15, (byte) 16, BinaryMemcacheOpcodes.SETQ, BinaryMemcacheOpcodes.ADDQ, BinaryMemcacheOpcodes.REPLACEQ, BinaryMemcacheOpcodes.DELETEQ, BinaryMemcacheOpcodes.INCREMENTQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.FLUSHQ, BinaryMemcacheOpcodes.APPENDQ, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, BinaryMemcacheOpcodes.PREPENDQ, (byte) 27, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.GAT, BinaryMemcacheOpcodes.GATQ, (byte) 31, (byte) 32, BinaryMemcacheOpcodes.SASL_AUTH, (byte) 34, BinaryMemcacheOpcodes.GATK, BinaryMemcacheOpcodes.GATKQ, (byte) 37, (byte) 38, (byte) 39, (byte) 40, (byte) 41, (byte) 42, (byte) 43, HttpConstants.COMMA, (byte) 45, (byte) 46, (byte) 47, TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1};

    public static synchronized c dB() {
        c cVar;
        synchronized (c.class) {
            if (lM == null) {
                lM = new c();
                System.loadLibrary("gnustl_shared");
                System.loadLibrary("dtspirit");
            }
            cVar = lM;
        }
        return cVar;
    }

    private void g(String pASSWORD_CRYPT_KEY, int inputCryptXorVal) {
        lT = pASSWORD_CRYPT_KEY;
        lU = inputCryptXorVal;
    }

    private byte[] W(String keyStr) throws UnsupportedEncodingException {
        byte[] key = new byte[24];
        byte[] temp = keyStr.getBytes("UTF-8");
        if (key.length > temp.length) {
            System.arraycopy(temp, 0, key, 0, temp.length);
        } else {
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        return key;
    }

    public byte[] e(byte[] src) {
        try {
            SecretKey dxkey = new SecretKeySpec(W(lT), lV);
            Cipher cipher = Cipher.getInstance(lV);
            cipher.init(1, dxkey);
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

    public String f(byte[] data) {
        int i;
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i2 = 0;
        while (i2 < len) {
            i = i2 + 1;
            int b1 = data[i2] & 255;
            if (i == len) {
                sb.append(lW[b1 >>> 2]);
                sb.append(lW[(b1 & 3) << 4]);
                sb.append("==");
                break;
            }
            i2 = i + 1;
            int b2 = data[i] & 255;
            if (i2 == len) {
                sb.append(lW[b1 >>> 2]);
                sb.append(lW[((b1 & 3) << 4) | ((b2 & b.bpd) >>> 4)]);
                sb.append(lW[(b2 & 15) << 2]);
                sb.append(SimpleComparison.EQUAL_TO_OPERATION);
                i = i2;
                break;
            }
            i = i2 + 1;
            int b3 = data[i2] & 255;
            sb.append(lW[b1 >>> 2]);
            sb.append(lW[((b1 & 3) << 4) | ((b2 & b.bpd) >>> 4)]);
            sb.append(lW[((b2 & 15) << 2) | ((b3 & 192) >>> 6)]);
            sb.append(lW[b3 & 63]);
            i2 = i;
        }
        i = i2;
        return sb.toString();
    }

    public static String h(String inputString, int inputLen) {
        try {
            return b.zo(inputString, inputLen);
        } catch (Exception e) {
            return null;
        }
    }

    public static String i(String inputString, int inputLen) {
        try {
            return b.zp(inputString, inputLen);
        } catch (Exception e) {
            return null;
        }
    }

    public static String j(String inputString, int inputLen) {
        try {
            return b.zt(inputString, inputLen);
        } catch (Exception e) {
            return null;
        }
    }

    public static String k(String inputString, int inputLen) {
        try {
            return b.zq(inputString, inputLen);
        } catch (Exception e) {
            return null;
        }
    }

    public static String l(String inputString, int inputLen) {
        try {
            return b.zr(inputString, inputLen);
        } catch (Exception e) {
            return null;
        }
    }

    public static String m(String inputString, int inputLen) {
        try {
            return b.zs(inputString, inputLen);
        } catch (Exception e) {
            return null;
        }
    }

    public static String n(String inputString, int inputLen) {
        try {
            return b.zu(inputString, inputLen);
        } catch (Exception e) {
            return null;
        }
    }

    public static String o(String inputString, int inputLen) {
        try {
            return b.zv(inputString, inputLen);
        } catch (Exception e) {
            return null;
        }
    }

    private byte[] g(byte[] src) {
        try {
            SecretKey dxkey = new SecretKeySpec(W(lT), lV);
            Cipher c1 = Cipher.getInstance(lV);
            c1.init(2, dxkey);
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

    private byte[] X(String str) {
        byte[] data = str.getBytes();
        int len = data.length;
        ByteArrayOutputStream buf = new ByteArrayOutputStream(len);
        int i = 0;
        while (i < len) {
            int b2;
            while (true) {
                int b3;
                int b4;
                int i2 = i + 1;
                int b1 = lX[data[i]];
                if (i2 < len && b1 == -1) {
                    i = i2;
                } else if (b1 == -1) {
                    i = i2;
                    break;
                } else {
                    do {
                        i = i2;
                        i2 = i + 1;
                        b2 = lX[data[i]];
                        if (i2 >= len) {
                            break;
                        }
                    } while (b2 == -1);
                    if (b2 == -1) {
                        i = i2;
                        break;
                    }
                    buf.write((b1 << 2) | ((b2 & 48) >>> 4));
                    do {
                        i = i2;
                        i2 = i + 1;
                        b3 = data[i];
                        if (b3 == 61) {
                            b3 = lX[b3];
                            if (i2 >= len) {
                                break;
                            }
                        } else {
                            i = i2;
                            return buf.toByteArray();
                        }
                    } while (b3 == -1);
                    if (b3 == -1) {
                        i = i2;
                        break;
                    }
                    buf.write(((b2 & 15) << 4) | ((b3 & 60) >>> 2));
                    do {
                        i = i2;
                        i2 = i + 1;
                        b4 = data[i];
                        if (b4 == 61) {
                            b4 = lX[b4];
                            if (i2 >= len) {
                                break;
                            }
                        } else {
                            i = i2;
                            return buf.toByteArray();
                        }
                    } while (b4 == -1);
                    if (b4 == -1) {
                        i = i2;
                        break;
                    }
                    buf.write(((b3 & 3) << 6) | b4);
                    i = i2;
                }
            }
            if (b1 == -1) {
                i = i2;
                break;
            }
            do {
                i = i2;
                i2 = i + 1;
                b2 = lX[data[i]];
                if (i2 >= len) {
                    break;
                }
                break;
            } while (b2 == -1);
            if (b2 == -1) {
                i = i2;
                break;
            }
            buf.write((b1 << 2) | ((b2 & 48) >>> 4));
            do {
                i = i2;
                i2 = i + 1;
                b3 = data[i];
                if (b3 == 61) {
                    b3 = lX[b3];
                    if (i2 >= len) {
                        break;
                    }
                    break;
                }
                i = i2;
                return buf.toByteArray();
            } while (b3 == -1);
            if (b3 == -1) {
                i = i2;
                break;
            }
            buf.write(((b2 & 15) << 4) | ((b3 & 60) >>> 2));
            do {
                i = i2;
                i2 = i + 1;
                b4 = data[i];
                if (b4 == 61) {
                    b4 = lX[b4];
                    if (i2 >= len) {
                        break;
                    }
                    break;
                }
                i = i2;
                return buf.toByteArray();
            } while (b4 == -1);
            if (b4 == -1) {
                i = i2;
                break;
            }
            buf.write(((b3 & 3) << 6) | b4);
            i = i2;
        }
        return buf.toByteArray();
    }

    public String Y(String input) {
        String netEncodeString = null;
        try {
            g(lN, 0);
            netEncodeString = f(e(h(input, input.length()).getBytes()));
        } catch (Exception e) {
        }
        return netEncodeString;
    }

    public String Z(String input) {
        String netEncodeString = null;
        try {
            g(lO, 0);
            netEncodeString = f(e(i(input, input.length()).getBytes()));
        } catch (Exception e) {
        }
        return netEncodeString;
    }

    public String aa(String input) {
        String netEncodeString = null;
        try {
            g(lP, 0);
            netEncodeString = f(e(j(input, input.length()).getBytes()));
        } catch (Exception e) {
        }
        return netEncodeString;
    }

    public String ab(String input) {
        String netEncodeString = null;
        try {
            g(lQ, 0);
            netEncodeString = f(e(k(input, input.length()).getBytes()));
        } catch (Exception e) {
        }
        return netEncodeString;
    }

    public String ac(String input) {
        String netEncodeString = null;
        try {
            g(lR, 0);
            netEncodeString = f(e(l(input, input.length()).getBytes()));
        } catch (Exception e) {
        }
        return netEncodeString;
    }

    public String p(String inputCRCString, int inputLightLen) {
        String retString = null;
        if (inputCRCString == null || inputLightLen == 0) {
            return null;
        }
        try {
            g(lR, 0);
            retString = m(new String(g(X(inputCRCString))), inputLightLen);
        } catch (Exception e) {
        }
        return retString;
    }

    public String ad(String input) {
        String netEncodeString = null;
        try {
            g(lS, 0);
            netEncodeString = f(e(n(input, input.length()).getBytes()));
        } catch (Exception e) {
        }
        return netEncodeString;
    }

    public String q(String inputCRCString, int inputLightLen) {
        String retString = null;
        if (inputCRCString == null || inputLightLen == 0) {
            return null;
        }
        try {
            g(lS, 0);
            retString = o(new String(g(X(inputCRCString))), inputLightLen);
        } catch (Exception e) {
        }
        return retString;
    }

    public void ax(Context inputContext) {
        try {
            b.a(inputContext, 0, 0, null);
        } catch (Exception e) {
        }
    }

    public static void dC() {
        String _tmpTestEncodeString = "ypist@qq.com";
        Log.v("TAG", "DTPrint netEncodeString Login is " + dB().Y(_tmpTestEncodeString));
        Log.v("TAG", "DTPrint netEncodeString Register is " + dB().Z(_tmpTestEncodeString));
        Log.v("TAG", "DTPrint netEncodeString OpenID is " + dB().aa(_tmpTestEncodeString));
        Log.v("TAG", "DTPrint netEncodeString Session is " + dB().ab(_tmpTestEncodeString));
        String _tmpTestEncodeStringOut = dB().ac(_tmpTestEncodeString);
        Log.v("TAG", "DTPrint netEncodeString encode local email 000 is " + _tmpTestEncodeStringOut);
        Log.v("TAG", "DTPrint netEncodeString decode local email 001 is " + dB().p(_tmpTestEncodeStringOut, 130));
        _tmpTestEncodeStringOut = dB().ad(_tmpTestEncodeString);
        Log.v("TAG", "DTPrint netEncodeString encode local passwd 000 is " + _tmpTestEncodeStringOut);
        Log.v("TAG", "DTPrint netEncodeString decode local passwd 001 is " + dB().q(_tmpTestEncodeStringOut, 130));
    }
}
