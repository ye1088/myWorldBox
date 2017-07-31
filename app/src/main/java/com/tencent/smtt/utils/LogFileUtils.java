package com.tencent.smtt.utils;

import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class LogFileUtils {
    private static OutputStream a = null;

    public static void closeOutputStream(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (Throwable e) {
                Log.e("LOG_FILE", "Couldn't close stream!", e);
            }
        }
    }

    public static byte[] encrypt(String str) {
        String str2 = "%$%&*)f4";
        try {
            byte[] bytes = str.getBytes("UTF-8");
            Cipher instance = Cipher.getInstance("RC4");
            instance.init(1, new SecretKeySpec(str2.getBytes("UTF-8"), "RC4"));
            return instance.update(bytes);
        } catch (Throwable th) {
            Log.e("LOG_FILE", "encrypt exception:" + th.getMessage());
            return null;
        }
    }

    public static synchronized void writeDataToStorage(File file, String str, boolean z) {
        byte[] bArr = null;
        synchronized (LogFileUtils.class) {
            byte[] encrypt = encrypt(str);
            if (encrypt != null) {
                str = null;
                bArr = encrypt;
            }
            try {
                file.getParentFile().mkdirs();
                if (file.isFile() && file.exists() && file.length() > 2097152) {
                    file.delete();
                    file.createNewFile();
                }
                if (a == null) {
                    a = new BufferedOutputStream(new FileOutputStream(file, z));
                }
                if (str != null) {
                    a.write(str.getBytes());
                } else {
                    a.write(bArr);
                    a.write(new byte[]{(byte) 0, (byte) 0});
                }
                if (a != null) {
                    a.flush();
                }
            } catch (Throwable th) {
                Log.e("LOG_FILE", "file.getAbsolutePath=" + file.getAbsolutePath() + " append=" + z, th);
            }
        }
    }
}
