package com.tencent.mm.sdk.platformtools;

import android.os.Build;
import android.os.Build.VERSION;
import android.widget.Toast;
import com.tencent.mm.algorithm.MD5;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

public class Log {
    public static final int LEVEL_DEBUG = 1;
    public static final int LEVEL_ERROR = 4;
    public static final int LEVEL_FATAL = 5;
    public static final int LEVEL_INFO = 2;
    public static final int LEVEL_NONE = 6;
    public static final int LEVEL_VERBOSE = 0;
    public static final int LEVEL_WARNING = 3;
    private static PrintStream W;
    private static byte[] X = null;
    private static final String Y;
    private static int level = 6;

    final class AnonymousClass1 implements Runnable {
        final /* synthetic */ String Z;

        AnonymousClass1(String str) {
            this.Z = str;
        }

        public final void run() {
            Toast.makeText(MMApplicationContext.getContext(), this.Z, 1).show();
        }
    }

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("VERSION.RELEASE:[" + VERSION.RELEASE);
        stringBuilder.append("] VERSION.CODENAME:[" + VERSION.CODENAME);
        stringBuilder.append("] VERSION.INCREMENTAL:[" + VERSION.INCREMENTAL);
        stringBuilder.append("] BOARD:[" + Build.BOARD);
        stringBuilder.append("] DEVICE:[" + Build.DEVICE);
        stringBuilder.append("] DISPLAY:[" + Build.DISPLAY);
        stringBuilder.append("] FINGERPRINT:[" + Build.FINGERPRINT);
        stringBuilder.append("] HOST:[" + Build.HOST);
        stringBuilder.append("] MANUFACTURER:[" + Build.MANUFACTURER);
        stringBuilder.append("] MODEL:[" + Build.MODEL);
        stringBuilder.append("] PRODUCT:[" + Build.PRODUCT);
        stringBuilder.append("] TAGS:[" + Build.TAGS);
        stringBuilder.append("] TYPE:[" + Build.TYPE);
        stringBuilder.append("] USER:[" + Build.USER + "]");
        Y = stringBuilder.toString();
    }

    protected Log() {
    }

    public static void d(String str, String str2) {
        d(str, str2, null);
    }

    public static void d(String str, String str2, Object... objArr) {
        if (level <= 1) {
            if (objArr != null) {
                str2 = String.format(str2, objArr);
            }
            android.util.Log.d(str, str2);
            LogHelper.writeToStream(W, X, "D/" + str, str2);
        }
    }

    public static void e(String str, String str2) {
        e(str, str2, null);
    }

    public static void e(String str, String str2, Object... objArr) {
        if (level <= 4) {
            if (objArr != null) {
                str2 = String.format(str2, objArr);
            }
            android.util.Log.e(str, str2);
            LogHelper.writeToStream(W, X, "E/" + str, str2);
        }
    }

    public static void f(String str, String str2) {
        f(str, str2, null);
    }

    public static void f(String str, String str2, Object... objArr) {
        if (level <= 5) {
            if (objArr != null) {
                str2 = String.format(str2, objArr);
            }
            android.util.Log.e(str, str2);
            LogHelper.writeToStream(W, X, "F/" + str, str2);
            MMHandlerThread.postToMainThread(new AnonymousClass1(str2));
        }
    }

    public static int getLevel() {
        return level;
    }

    public static String getSysInfo() {
        return Y;
    }

    public static void i(String str, String str2) {
        i(str, str2, null);
    }

    public static void i(String str, String str2, Object... objArr) {
        if (level <= 2) {
            if (objArr != null) {
                str2 = String.format(str2, objArr);
            }
            android.util.Log.i(str, str2);
            LogHelper.writeToStream(W, X, "I/" + str, str2);
        }
    }

    public static void reset() {
        W = null;
        X = null;
    }

    public static void setLevel(int i, boolean z) {
        level = i;
        android.util.Log.w("MicroMsg.SDK.Log", "new log level: " + i);
        if (z) {
            android.util.Log.e("MicroMsg.SDK.Log", "no jni log level support");
        }
    }

    public static void setOutputPath(String str, String str2, String str3, int i) {
        if (str != null && str.length() != 0 && str3 != null && str3.length() != 0) {
            try {
                File file = new File(str);
                if (file.exists()) {
                    InputStream fileInputStream = file.length() > 0 ? new FileInputStream(str) : null;
                    setOutputStream(fileInputStream, new FileOutputStream(str, true), str2, str3, i);
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void setOutputStream(InputStream inputStream, OutputStream outputStream, String str, String str2, int i) {
        try {
            long j;
            W = new PrintStream(new BufferedOutputStream(outputStream));
            if (inputStream != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String trim = Util.nullAsNil(bufferedReader.readLine()).substring(2).trim();
                str2 = Util.nullAsNil(bufferedReader.readLine()).substring(2).trim();
                j = Util.getLong(Util.nullAsNil(bufferedReader.readLine()).trim().substring(2), 0);
                d("MicroMsg.SDK.Log", "using provided info, type=%s, user=%s, createtime=%d", trim, str2, Long.valueOf(j));
            } else {
                j = System.currentTimeMillis();
                LogHelper.initLogHeader(W, str, str2, j, i);
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(str2);
            stringBuffer.append(j);
            stringBuffer.append("dfdhgc");
            X = MD5.getMessageDigest(stringBuffer.toString().getBytes()).substring(7, 21).getBytes();
            android.util.Log.d("MicroMsg.SDK.Log", "set up out put stream");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void v(String str, String str2) {
        v(str, str2, null);
    }

    public static void v(String str, String str2, Object... objArr) {
        if (level <= 0) {
            if (objArr != null) {
                str2 = String.format(str2, objArr);
            }
            android.util.Log.v(str, str2);
            LogHelper.writeToStream(W, X, "V/" + str, str2);
        }
    }

    public static void w(String str, String str2) {
        w(str, str2, null);
    }

    public static void w(String str, String str2, Object... objArr) {
        if (level <= 3) {
            if (objArr != null) {
                str2 = String.format(str2, objArr);
            }
            android.util.Log.w(str, str2);
            LogHelper.writeToStream(W, X, "W/" + str, str2);
        }
    }
}
