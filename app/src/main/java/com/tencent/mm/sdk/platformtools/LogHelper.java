package com.tencent.mm.sdk.platformtools;

import android.os.Build;
import android.os.Build.VERSION;
import com.tencent.mm.algorithm.TypeTransform;
import java.io.PrintStream;
import java.security.Key;
import java.text.SimpleDateFormat;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

final class LogHelper {
    private static final SimpleDateFormat aa = new SimpleDateFormat("MM-dd HH:mm:ss SSS");
    private static final byte[] ab = new byte[]{(byte) 4, (byte) 0, (byte) 0, (byte) 0, (byte) -1, (byte) -1, (byte) -1, (byte) 0};

    private LogHelper() {
    }

    public static void initLogHeader(PrintStream printStream, String str, String str2, long j, int i) {
        if (printStream != null && !Util.isNullOrNil(str2) && j != 0) {
            printStream.println("1 " + str);
            printStream.println("2 " + str2);
            printStream.println("3 " + j);
            printStream.println("4 " + Integer.toHexString(i));
            printStream.println("5 " + VERSION.RELEASE);
            printStream.println("6 " + VERSION.CODENAME);
            printStream.println("7 " + VERSION.INCREMENTAL);
            printStream.println("8 " + Build.BOARD);
            printStream.println("9 " + Build.DEVICE);
            printStream.println("10 " + Build.DISPLAY);
            printStream.println("11 " + Build.FINGERPRINT);
            printStream.println("12 " + Build.HOST);
            printStream.println("13 " + Build.MANUFACTURER);
            printStream.println("14 " + Build.MODEL);
            printStream.println("15 " + Build.PRODUCT);
            printStream.println("16 " + Build.TAGS);
            printStream.println("17 " + Build.TYPE);
            printStream.println("18 " + Build.USER);
            printStream.println();
            printStream.flush();
        }
    }

    public static void writeToStream(PrintStream printStream, byte[] bArr, String str, String str2) {
        if (printStream != null && !Util.isNullOrNil(bArr) && !Util.isNullOrNil(str) && !Util.isNullOrNil(str2)) {
            synchronized (printStream) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(aa.format(Long.valueOf(System.currentTimeMillis())));
                stringBuffer.append(" ").append(str).append(" ").append(str2);
                String stringBuffer2 = stringBuffer.toString();
                try {
                    Key generateSecret = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(bArr));
                    Cipher instance = Cipher.getInstance("DES");
                    instance.init(1, generateSecret);
                    byte[] doFinal = instance.doFinal(stringBuffer2.getBytes());
                    printStream.write(TypeTransform.intToByteArrayLH(doFinal.length));
                    printStream.write(doFinal);
                    printStream.write(ab);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                printStream.flush();
            }
        }
    }
}
