package com.baidu.android.silentupdate;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Process;
import android.util.Log;
import com.huluxia.framework.base.utils.UtilsRSA;
import com.tencent.mm.sdk.platformtools.LocaleUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import net.lingala.zip4j.util.e;
import org.json.JSONObject;

public class SilentManager {
    private static final String a = SilentManager.class.getSimpleName();
    private static final String b = "push_lib";
    private static final String c = "push_dex";
    private static final String d = "push_update";
    private static String e = null;
    private static boolean f = false;
    private static boolean g = true;

    private static String a(File file) {
        String str = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String str2 = "";
            while (true) {
                str2 = bufferedReader.readLine();
                if (str2 == null) {
                    break;
                }
                str = str + str2 + "\r\n";
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e2) {
        }
        return str;
    }

    private static String a(String str) {
        int lastIndexOf = str.lastIndexOf(".");
        return (str.lastIndexOf("/") <= lastIndexOf && lastIndexOf > 0 && lastIndexOf < str.length()) ? str.substring(0, lastIndexOf) : str;
    }

    private static String a(byte[] bArr) {
        String str = "";
        str = "";
        for (byte b : bArr) {
            String toHexString = Integer.toHexString(b & 255);
            str = toHexString.length() == 1 ? str + "0" + toHexString : str + toHexString;
        }
        return str;
    }

    private static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    private static void a(InputStream inputStream, OutputStream outputStream) throws IOException {
        Closeable bufferedOutputStream;
        if (!(inputStream instanceof BufferedInputStream)) {
            Closeable bufferedInputStream = new BufferedInputStream(inputStream);
        }
        if (!(outputStream instanceof BufferedOutputStream)) {
            bufferedOutputStream = new BufferedOutputStream(outputStream);
        }
        byte[] bArr = new byte[512];
        while (true) {
            try {
                int read = bufferedInputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                bufferedOutputStream.write(bArr, 0, read);
            } finally {
                a(bufferedInputStream);
                a(bufferedOutputStream);
            }
        }
        bufferedOutputStream.flush();
    }

    private static boolean a(Context context, File file, File file2, String str, String str2, String str3) {
        if (!g) {
            return true;
        }
        try {
            JSONObject jSONObject = new JSONObject(decrypt(str, a(file)));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open(str2 + "/" + a(str3) + ".key")));
            String str4 = "";
            String str5 = "";
            while (true) {
                str5 = bufferedReader.readLine();
                if (str5 == null) {
                    break;
                }
                str4 = str4 + str5 + "\r\n";
            }
            JSONObject jSONObject2 = new JSONObject(decrypt(str, str4));
            if (!jSONObject2.getString("flag").equals(jSONObject.getString("flag"))) {
                if (f) {
                    Log.d(a, "Flag not equal!");
                }
                return false;
            } else if (jSONObject2.getLong("timestamp") > jSONObject.getLong("timestamp")) {
                if (f) {
                    Log.d(a, "APK newer than dex!");
                }
                return false;
            } else {
                str4 = b(file2);
                if (f) {
                    Log.d(a, "Lib MD5 : " + str4);
                }
                if (!jSONObject.getString("md5").equals(str4)) {
                    if (f) {
                        Log.d(a, "RSA check fail");
                    }
                    return false;
                } else if (!f) {
                    return true;
                } else {
                    Log.d(a, "RSA check ok");
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean a(Context context, String str, String str2, String str3, String str4) {
        Context applicationContext = context.getApplicationContext();
        if (e == null) {
            throw new RuntimeException("public key must init first!");
        } else if (str2 == null) {
            throw new RuntimeException("Lib Name Must Not Null!");
        } else {
            Date date = new Date();
            String str5 = str == null ? "" : str;
            File file = new File(applicationContext.getDir(b, 0).getAbsolutePath() + "/" + str2);
            String str6 = a(str2) + ".key";
            File file2 = new File(applicationContext.getDir(b, 0).getAbsolutePath() + "/" + str6);
            if (str4 != null) {
                File file3 = new File(str4 + "/" + str2);
                File file4 = new File(str4 + "/" + str6);
                if (file3.exists() && file4.exists()) {
                    int myPid = Process.myPid();
                    for (RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) applicationContext.getSystemService("activity")).getRunningAppProcesses()) {
                        if (runningAppProcessInfo.pid == myPid && runningAppProcessInfo.processName.endsWith(applicationContext.getPackageName())) {
                            if (f) {
                                Log.d(a, "Found update");
                            }
                            file3.renameTo(file);
                            file4.renameTo(file2);
                        }
                    }
                }
            }
            if (!file.exists() || !file2.exists() || !a(applicationContext, file2, file, e, str5, str2)) {
                if (f) {
                    Log.d(a, "Need copy lib from assert");
                }
                try {
                    a(applicationContext.getAssets().open(str5 + "/" + str2), new FileOutputStream(file));
                    a(applicationContext.getAssets().open(str5 + "/" + str6), new FileOutputStream(file2));
                } catch (IOException e) {
                    if (f) {
                        Log.e(a, "Copy from assert fail!");
                    }
                    return false;
                }
            } else if (f) {
                Log.d(a, "Lib file check ok");
            }
            ClassLoader a = b.a(file.getAbsolutePath(), applicationContext.getDir(c, 0).getAbsolutePath(), str3, applicationContext);
            if (a == null) {
                if (f) {
                    Log.e(a, "Create classloader fail");
                }
                return false;
            }
            boolean a2 = b.a(a, context.getClassLoader());
            if (a2) {
                if (!f) {
                    return a2;
                }
                Log.d(a, "Load lib ok, cost " + (new Date().getTime() - date.getTime()) + LocaleUtil.MALAY);
                return a2;
            } else if (!f) {
                return a2;
            } else {
                Log.e(a, "Insert classloader fail");
                return a2;
            }
        }
    }

    private static String b(File file) {
        if (!file.isFile() || !file.exists()) {
            return null;
        }
        byte[] bArr = new byte[1024];
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            FileInputStream fileInputStream = new FileInputStream(file);
            while (true) {
                int read = fileInputStream.read(bArr, 0, 1024);
                if (read != -1) {
                    instance.update(bArr, 0, read);
                } else {
                    fileInputStream.close();
                    return a(instance.digest()).toLowerCase();
                }
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static String decrypt(String str, String str2) {
        try {
            Key generatePublic = KeyFactory.getInstance(UtilsRSA.KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(a.a(str.getBytes())));
            Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            instance.init(2, generatePublic);
            byte[] doFinal = instance.doFinal(a.a(str2.getBytes()));
            String str3 = new String(doFinal, e.clA);
            if (f) {
                Log.d(a, "RSA decrypt:" + str3);
            }
            return new String(doFinal, e.clA);
        } catch (NoSuchAlgorithmException e) {
            if (f) {
                Log.d(a, "RSA decrypt fail");
            }
            return null;
        } catch (InvalidKeySpecException e2) {
            if (f) {
                Log.d(a, "RSA decrypt fail");
            }
            return null;
        } catch (NoSuchPaddingException e3) {
            if (f) {
                Log.d(a, "RSA decrypt fail");
            }
            return null;
        } catch (InvalidKeyException e4) {
            if (f) {
                Log.d(a, "RSA decrypt fail");
            }
            return null;
        } catch (IllegalBlockSizeException e5) {
            if (f) {
                Log.d(a, "RSA decrypt fail");
            }
            return null;
        } catch (BadPaddingException e6) {
            if (f) {
                Log.d(a, "RSA decrypt fail");
            }
            return null;
        } catch (UnsupportedEncodingException e7) {
            if (f) {
                Log.d(a, "RSA decrypt fail");
            }
            return null;
        } catch (Exception e8) {
            if (f) {
                Log.d(a, "RSA decrypt fail");
            }
            return null;
        }
    }

    public static void enableDebugMode(boolean z) {
        f = z;
    }

    public static void enableRSA(boolean z) {
        g = z;
    }

    public static String getKey() {
        return e;
    }

    public static boolean loadLib(Context context, String str, String str2) {
        return loadLib(context, str, str2, "/data/data/" + context.getPackageName() + "/lib");
    }

    public static boolean loadLib(Context context, String str, String str2, String str3) {
        return a(context, str, str2, str3, context.getDir(d, 0).getAbsolutePath());
    }

    public static void setKey(String str) {
        e = str;
    }
}
