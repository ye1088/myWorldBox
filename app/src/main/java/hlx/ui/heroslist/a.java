package hlx.ui.heroslist;

import com.MCWorld.module.h;
import com.MCWorld.video.recorder.b;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.tencent.smtt.sdk.TbsListener.ErrorCode;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheOpcodes;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.tools.tar.TarConstants;
import org.mozilla.classfile.ByteCode;

/* compiled from: HeroCrypto */
public class a {
    private static final boolean DEBUG = false;
    private static a bXO = null;
    private static String lT = "wefjweofjewofwe";
    private static final String lV = "DESede";
    private final char[] bXP = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private byte[] bXQ = new byte[]{(byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) 62, (byte) -1, (byte) -1, (byte) -1, (byte) 63, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, (byte) 56, (byte) 57, HttpConstants.COLON, HttpConstants.SEMICOLON, (byte) 60, HttpConstants.EQUALS, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10, (byte) 11, (byte) 12, (byte) 13, (byte) 14, (byte) 15, (byte) 16, BinaryMemcacheOpcodes.SETQ, BinaryMemcacheOpcodes.ADDQ, BinaryMemcacheOpcodes.REPLACEQ, BinaryMemcacheOpcodes.DELETEQ, BinaryMemcacheOpcodes.INCREMENTQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.FLUSHQ, BinaryMemcacheOpcodes.APPENDQ, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, BinaryMemcacheOpcodes.PREPENDQ, (byte) 27, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.GAT, BinaryMemcacheOpcodes.GATQ, (byte) 31, (byte) 32, BinaryMemcacheOpcodes.SASL_AUTH, (byte) 34, BinaryMemcacheOpcodes.GATK, BinaryMemcacheOpcodes.GATKQ, (byte) 37, (byte) 38, (byte) 39, (byte) 40, (byte) 41, (byte) 42, (byte) 43, HttpConstants.COMMA, (byte) 45, (byte) 46, (byte) 47, TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1};
    private final String bXR = "mcxxoo";
    private final String bXS = "2016";
    private final int bXT = 16;
    private final int bXU = 32;

    public static synchronized a TY() {
        a aVar;
        synchronized (a.class) {
            if (bXO == null) {
                bXO = new a();
                bXO.dF("tcjx2c2nmgx8");
            }
            aVar = bXO;
        }
        return aVar;
    }

    public void dF(String pASSWORD_CRYPT_KEY) {
        lT = pASSWORD_CRYPT_KEY;
    }

    public byte[] x(byte[] src) {
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

    public byte[] dG(String keyStr) throws UnsupportedEncodingException {
        byte[] key = new byte[24];
        byte[] temp = keyStr.getBytes("UTF-8");
        if (key.length > temp.length) {
            System.arraycopy(temp, 0, key, 0, temp.length);
        } else {
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        return key;
    }

    public String F(byte[] data) {
        int i;
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i2 = 0;
        while (i2 < len) {
            i = i2 + 1;
            int b1 = data[i2] & 255;
            if (i == len) {
                sb.append(this.bXP[b1 >>> 2]);
                sb.append(this.bXP[(b1 & 3) << 4]);
                sb.append("==");
                break;
            }
            i2 = i + 1;
            int b2 = data[i] & 255;
            if (i2 == len) {
                sb.append(this.bXP[b1 >>> 2]);
                sb.append(this.bXP[((b1 & 3) << 4) | ((b2 & b.bpd) >>> 4)]);
                sb.append(this.bXP[(b2 & 15) << 2]);
                sb.append(SimpleComparison.EQUAL_TO_OPERATION);
                i = i2;
                break;
            }
            i = i2 + 1;
            int b3 = data[i2] & 255;
            sb.append(this.bXP[b1 >>> 2]);
            sb.append(this.bXP[((b1 & 3) << 4) | ((b2 & b.bpd) >>> 4)]);
            sb.append(this.bXP[((b2 & 15) << 2) | ((b3 & 192) >>> 6)]);
            sb.append(this.bXP[b3 & 63]);
            i2 = i;
        }
        i = i2;
        return sb.toString();
    }

    private byte i(byte inputByteChar) {
        int tmpParamData = inputByteChar;
        if (tmpParamData < (byte) 0) {
            tmpParamData += 256;
        }
        int a = tmpParamData;
        int b = tmpParamData;
        int j = 0;
        while (tmpParamData < ErrorCode.DEXOPT_EXCEPTION && j < ByteCode.IFNULL) {
            a = (a * b) % ErrorCode.DEXOPT_EXCEPTION;
            j++;
        }
        return (byte) a;
    }

    private static String fillLeftZero(String input, int size) {
        String _tmpSymbol = "0";
        while (input.length() < size) {
            input = "0" + input;
        }
        return input;
    }

    private String ai(int inputCateId, int inputUserId, int inputScore) {
        String _tmpLightCRC = null;
        try {
            String _tmpCatId = fillLeftZero(Integer.toHexString(inputCateId).toUpperCase(), 8);
            String _tmpUserId = fillLeftZero(Integer.toHexString(inputUserId).toUpperCase(), 8);
            String _tmpUserId01 = fillLeftZero(Integer.toHexString(inputUserId + 16).toUpperCase(), 8);
            String _tmpUserId02 = fillLeftZero(Integer.toHexString(inputUserId + 32).toUpperCase(), 8);
            _tmpLightCRC = _tmpUserId + _tmpCatId + "mcxxoo" + _tmpUserId01 + "2016" + _tmpUserId02 + fillLeftZero(Integer.toHexString(inputScore).toUpperCase(), 8);
        } catch (Exception e) {
        }
        return _tmpLightCRC;
    }

    public String aj(int inputCateId, int inputUserId, int inputScore) {
        String _tmpRetString = null;
        try {
            String _tmpCRCCode = ai(inputCateId, inputUserId, inputScore);
            if (_tmpCRCCode == null) {
                return null;
            }
            byte[] _tmpCRCCodeArray = _tmpCRCCode.getBytes();
            if (true) {
                for (int k = 0; k < 50; k++) {
                    if (k % 2 == 0) {
                        _tmpCRCCodeArray[k] = i(_tmpCRCCodeArray[k]);
                    }
                    _tmpCRCCodeArray[k] = (byte) (_tmpCRCCodeArray[k] ^ (k + h.arj));
                }
            }
            _tmpRetString = F(x(_tmpCRCCodeArray));
            return _tmpRetString;
        } catch (Exception e) {
        }
    }
}
