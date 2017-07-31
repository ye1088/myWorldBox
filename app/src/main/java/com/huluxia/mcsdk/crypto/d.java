package com.huluxia.mcsdk.crypto;

import android.content.Context;
import com.huluxia.mcjsmanager.c;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: DTCrypto */
public class d {
    private Key aou;
    private Cipher aov;
    private Cipher aow;
    private Context context;

    public d(String key) throws Exception {
        dJ(key);
        CR();
    }

    public void aX(Context in_context) {
        this.context = in_context;
    }

    public void dJ(String keyRule) {
        byte[] keyByte = keyRule.getBytes();
        byte[] byteTemp = new byte[8];
        int i = 0;
        while (i < byteTemp.length && i < keyByte.length) {
            byteTemp[i] = keyByte[i];
            i++;
        }
        this.aou = new SecretKeySpec(byteTemp, "DES");
    }

    private void CR() throws Exception {
        this.aow = Cipher.getInstance("DES");
        this.aow.init(1, this.aou);
        this.aov = Cipher.getInstance("DES");
        this.aov.init(2, this.aou);
    }

    public void b(InputStream in, String savePath) {
        if (in == null) {
            System.out.println("inputstream is null");
            return;
        }
        try {
            CipherInputStream cin = new CipherInputStream(in, this.aow);
            OutputStream os = new FileOutputStream(savePath);
            byte[] bytes = new byte[1024];
            while (true) {
                int len = cin.read(bytes);
                if (len > 0) {
                    os.write(bytes, 0, len);
                } else {
                    os.flush();
                    os.close();
                    cin.close();
                    in.close();
                    System.out.println("加密成功");
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("加密失败");
            e.printStackTrace();
        }
    }

    public void G(String filePath, String savePath) throws FileNotFoundException {
        b(new FileInputStream(filePath), savePath);
    }

    public void c(InputStream in, String savePath) {
        if (in == null) {
            System.out.println("inputstream is null");
            return;
        }
        try {
            CipherInputStream cin = new CipherInputStream(in, this.aov);
            BufferedInputStream reader = new BufferedInputStream(cin);
            OutputStream os = new FileOutputStream(savePath);
            byte[] b = new byte[1024];
            while (true) {
                int length = reader.read(b);
                if (length > 0) {
                    os.write(b, 0, length);
                } else {
                    os.flush();
                    os.close();
                    reader.close();
                    cin.close();
                    in.close();
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("解密失败");
            e.printStackTrace();
        }
    }

    public void H(String filePath, String savePath) throws Exception {
        c(new FileInputStream(filePath), savePath);
    }

    public String dK(String filePath) throws Exception {
        String out_string = "";
        InputStream in = new FileInputStream(filePath);
        if (in == null) {
            System.out.println("inputstream is null");
            return null;
        }
        try {
            CipherInputStream cin = new CipherInputStream(in, this.aov);
            BufferedReader reader = new BufferedReader(new InputStreamReader(cin));
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                out_string = out_string + line;
            }
            reader.close();
            cin.close();
            in.close();
        } catch (Exception e) {
            System.out.println("解密失败");
            e.printStackTrace();
        }
        return out_string;
    }

    public String dL(String filePath) throws Exception {
        String str = null;
        InputStream in = new FileInputStream(filePath);
        if (in == null) {
            System.out.println("inputstream is null");
        } else {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new CipherInputStream(in, this.aov)));
                char[] cbuf = new char[1024];
                StringBuilder sb = new StringBuilder(1024);
                while (true) {
                    int bytes_read = reader.read(cbuf, 0, 1024);
                    if (bytes_read == -1) {
                        break;
                    }
                    sb.append(cbuf, 0, bytes_read);
                }
                str = sb.toString();
            } catch (Exception e) {
                System.out.println("解密失败");
                e.printStackTrace();
            }
        }
        return str;
    }

    public String J(String DirName, int in_JsFileIndex) throws Exception {
        String str = null;
        InputStream in = this.context.getResources().getAssets().open(DirName + c.hF(in_JsFileIndex));
        if (in == null) {
            System.out.println("inputstream is null");
        } else {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new CipherInputStream(in, this.aov)));
                char[] cbuf = new char[1024];
                StringBuilder sb = new StringBuilder(1024);
                while (true) {
                    int bytes_read = reader.read(cbuf, 0, 1024);
                    if (bytes_read == -1) {
                        break;
                    }
                    sb.append(cbuf, 0, bytes_read);
                }
                str = sb.toString();
            } catch (Exception e) {
                System.out.println("解密失败");
                e.printStackTrace();
            } finally {
                in.close();
            }
        }
        return str;
    }

    public static void dC() throws Exception {
    }
}
