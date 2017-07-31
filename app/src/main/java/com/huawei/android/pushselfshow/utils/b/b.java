package com.huawei.android.pushselfshow.utils.b;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushselfshow.utils.a;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

public class b {
    public Handler a;
    public Context b;
    public String c;
    public String d;
    public boolean e;
    private boolean f;
    private Runnable g;

    public b() {
        this.a = null;
        this.e = false;
        this.g = new c(this);
        this.f = false;
    }

    public b(Handler handler, Context context, String str, String str2) {
        this.a = null;
        this.e = false;
        this.g = new c(this);
        this.a = handler;
        this.b = context;
        this.c = str;
        this.d = str2;
        this.f = false;
    }

    public static String a(Context context) {
        return b(context) + File.separator + "richpush";
    }

    public static void a(HttpClient httpClient) {
        if (httpClient != null) {
            try {
                httpClient.getConnectionManager().shutdown();
            } catch (Throwable e) {
                e.c("PushSelfShowLog", "close input stream failed." + e.getMessage(), e);
            }
        }
    }

    public static void a(HttpRequestBase httpRequestBase) {
        if (httpRequestBase != null) {
            try {
                httpRequestBase.abort();
            } catch (Throwable e) {
                e.c("PushSelfShowLog", "close input stream failed." + e.getMessage(), e);
            }
        }
    }

    public static String b(Context context) {
        String str = "";
        try {
            str = !Environment.getExternalStorageState().equals("mounted") ? context.getFilesDir().getPath() : Environment.getExternalStorageDirectory().getPath();
        } catch (Throwable e) {
            e.c("PushSelfShowLog", "getLocalFileHeader failed ", e);
        }
        str = str + File.separator + "PushService";
        e.a("PushSelfShowLog", "getFileHeader:" + str);
        return str;
    }

    public static String c(Context context) {
        String str = "";
        try {
            if (!Environment.getExternalStorageState().equals("mounted")) {
                return null;
            }
            str = Environment.getExternalStorageDirectory().getPath();
            return str + File.separator + "PushService";
        } catch (Throwable e) {
            e.c("PushSelfShowLog", "getLocalFileHeader failed ", e);
        }
    }

    public String a(Context context, String str, String str2) {
        try {
            String str3 = "" + System.currentTimeMillis();
            String str4 = str3 + str2;
            String str5 = a(context) + File.separator + str3;
            str3 = str5 + File.separator + str4;
            File file = new File(str5);
            if (file.exists()) {
                a.a(file);
            } else {
                e.a("PushSelfShowLog", "dir is not exists()," + file.getAbsolutePath());
            }
            if (file.mkdirs()) {
                e.a("PushSelfShowLog", "dir.mkdirs() success  ");
            }
            e.a("PushSelfShowLog", "begin to download zip file, path is " + str3 + ",dir is " + file.getAbsolutePath());
            if (b(context, str, str3)) {
                return str3;
            }
            e.a("PushSelfShowLog", "download richpush file failed，clear temp file");
            if (file.exists()) {
                a.a(file);
            }
            return null;
        } catch (Exception e) {
            e.a("PushSelfShowLog", "download err" + e.toString());
        }
    }

    public void a() {
        this.f = true;
    }

    public void a(String str) {
        Message message = new Message();
        message.what = 1;
        message.obj = str;
        e.a("PushSelfShowLog", "mDownloadHandler = " + this.a);
        if (this.a != null) {
            this.a.sendMessageDelayed(message, 1);
        }
    }

    public void b() {
        new Thread(this.g).start();
    }

    public boolean b(Context context, String str, String str2) {
        Throwable e;
        BufferedOutputStream bufferedOutputStream;
        Throwable e2;
        HttpClient httpClient;
        BufferedInputStream bufferedInputStream;
        BufferedOutputStream bufferedOutputStream2;
        OutputStream outputStream = null;
        BufferedOutputStream bufferedOutputStream3 = null;
        BufferedInputStream bufferedInputStream2 = null;
        OutputStream outputStream2 = null;
        HttpClient defaultHttpClient;
        HttpRequestBase httpGet;
        try {
            defaultHttpClient = new DefaultHttpClient();
            try {
                httpGet = new HttpGet(str);
                try {
                    HttpResponse a = new d(context).a(str, defaultHttpClient, (HttpGet) httpGet);
                    if (a == null) {
                        e.a("PushSelfShowLog", "fail, httprespone  is null");
                        a(httpGet);
                        a(defaultHttpClient);
                        if (null != null) {
                            try {
                                bufferedOutputStream3.close();
                            } catch (Throwable e3) {
                                e.c("PushSelfShowLog", " bos download  error" + e3.toString(), e3);
                            }
                        }
                        if (null != null) {
                            try {
                                bufferedInputStream2.close();
                            } catch (Throwable e32) {
                                e.c("PushSelfShowLog", " bis download  error" + e32.toString(), e32);
                            }
                        }
                        if (null == null) {
                            return false;
                        }
                        try {
                            outputStream2.close();
                            return false;
                        } catch (Throwable e322) {
                            e.c("PushSelfShowLog", "out download  error" + e322.toString(), e322);
                            return false;
                        }
                    } else if (a.getStatusLine() == null || a.getStatusLine().getStatusCode() == 200) {
                        bufferedInputStream2 = new BufferedInputStream(a.getEntity().getContent());
                        try {
                            e.a("PushSelfShowLog", "begin to write content to " + str2);
                            if (new File(str2).exists()) {
                                e.a("PushSelfShowLog", "file delete " + new File(str2).delete());
                            }
                            OutputStream fileOutputStream = new FileOutputStream(str2);
                            try {
                                bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                                try {
                                    byte[] bArr = new byte[32768];
                                    do {
                                        int read = bufferedInputStream2.read(bArr);
                                        if (read < 0) {
                                            e.a("PushSelfShowLog", "downLoad success ");
                                            this.e = false;
                                            a(httpGet);
                                            a(defaultHttpClient);
                                            if (bufferedOutputStream != null) {
                                                try {
                                                    bufferedOutputStream.close();
                                                } catch (Throwable e22) {
                                                    e.c("PushSelfShowLog", " bos download  error" + e22.toString(), e22);
                                                }
                                            }
                                            if (bufferedInputStream2 != null) {
                                                try {
                                                    bufferedInputStream2.close();
                                                } catch (Throwable e222) {
                                                    e.c("PushSelfShowLog", " bis download  error" + e222.toString(), e222);
                                                }
                                            }
                                            if (fileOutputStream != null) {
                                                try {
                                                    fileOutputStream.close();
                                                } catch (Throwable e2222) {
                                                    e.c("PushSelfShowLog", "out download  error" + e2222.toString(), e2222);
                                                }
                                            }
                                            return true;
                                        }
                                        this.e = true;
                                        bufferedOutputStream.write(bArr, 0, read);
                                    } while (!this.f);
                                    a(httpGet);
                                    a(defaultHttpClient);
                                    if (bufferedOutputStream != null) {
                                        try {
                                            bufferedOutputStream.close();
                                        } catch (Throwable e3222) {
                                            e.c("PushSelfShowLog", " bos download  error" + e3222.toString(), e3222);
                                        }
                                    }
                                    if (bufferedInputStream2 != null) {
                                        try {
                                            bufferedInputStream2.close();
                                        } catch (Throwable e32222) {
                                            e.c("PushSelfShowLog", " bis download  error" + e32222.toString(), e32222);
                                        }
                                    }
                                    if (fileOutputStream != null) {
                                        try {
                                            fileOutputStream.close();
                                        } catch (Throwable e322222) {
                                            e.c("PushSelfShowLog", "out download  error" + e322222.toString(), e322222);
                                        }
                                    }
                                } catch (IOException e4) {
                                    e322222 = e4;
                                    outputStream = fileOutputStream;
                                    httpClient = defaultHttpClient;
                                    bufferedInputStream = bufferedInputStream2;
                                    bufferedOutputStream2 = bufferedOutputStream;
                                } catch (Throwable th) {
                                    e2222 = th;
                                    outputStream = fileOutputStream;
                                }
                            } catch (IOException e5) {
                                e322222 = e5;
                                OutputStream outputStream3 = fileOutputStream;
                                httpClient = defaultHttpClient;
                                bufferedInputStream = bufferedInputStream2;
                                bufferedOutputStream2 = null;
                                outputStream = outputStream3;
                                try {
                                    e.c("PushSelfShowLog", "downLoadSgThread download  error" + e322222.toString(), e322222);
                                    a(httpGet);
                                    a(httpClient);
                                    if (bufferedOutputStream2 != null) {
                                        try {
                                            bufferedOutputStream2.close();
                                        } catch (Throwable e3222222) {
                                            e.c("PushSelfShowLog", " bos download  error" + e3222222.toString(), e3222222);
                                        }
                                    }
                                    if (bufferedInputStream != null) {
                                        try {
                                            bufferedInputStream.close();
                                        } catch (Throwable e32222222) {
                                            e.c("PushSelfShowLog", " bis download  error" + e32222222.toString(), e32222222);
                                        }
                                    }
                                    if (outputStream != null) {
                                        try {
                                            outputStream.close();
                                        } catch (Throwable e322222222) {
                                            e.c("PushSelfShowLog", "out download  error" + e322222222.toString(), e322222222);
                                        }
                                    }
                                    this.e = false;
                                    return false;
                                } catch (Throwable th2) {
                                    e2222 = th2;
                                    bufferedOutputStream = bufferedOutputStream2;
                                    bufferedInputStream2 = bufferedInputStream;
                                    defaultHttpClient = httpClient;
                                    a(httpGet);
                                    a(defaultHttpClient);
                                    if (bufferedOutputStream != null) {
                                        try {
                                            bufferedOutputStream.close();
                                        } catch (Throwable e3222222222) {
                                            e.c("PushSelfShowLog", " bos download  error" + e3222222222.toString(), e3222222222);
                                        }
                                    }
                                    if (bufferedInputStream2 != null) {
                                        try {
                                            bufferedInputStream2.close();
                                        } catch (Throwable e32222222222) {
                                            e.c("PushSelfShowLog", " bis download  error" + e32222222222.toString(), e32222222222);
                                        }
                                    }
                                    if (outputStream != null) {
                                        try {
                                            outputStream.close();
                                        } catch (Throwable e322222222222) {
                                            e.c("PushSelfShowLog", "out download  error" + e322222222222.toString(), e322222222222);
                                        }
                                    }
                                    throw e2222;
                                }
                            } catch (Throwable th3) {
                                e2222 = th3;
                                bufferedOutputStream = null;
                                outputStream = fileOutputStream;
                                a(httpGet);
                                a(defaultHttpClient);
                                if (bufferedOutputStream != null) {
                                    bufferedOutputStream.close();
                                }
                                if (bufferedInputStream2 != null) {
                                    bufferedInputStream2.close();
                                }
                                if (outputStream != null) {
                                    outputStream.close();
                                }
                                throw e2222;
                            }
                        } catch (IOException e6) {
                            e322222222222 = e6;
                            httpClient = defaultHttpClient;
                            bufferedInputStream = bufferedInputStream2;
                            bufferedOutputStream2 = null;
                            e.c("PushSelfShowLog", "downLoadSgThread download  error" + e322222222222.toString(), e322222222222);
                            a(httpGet);
                            a(httpClient);
                            if (bufferedOutputStream2 != null) {
                                bufferedOutputStream2.close();
                            }
                            if (bufferedInputStream != null) {
                                bufferedInputStream.close();
                            }
                            if (outputStream != null) {
                                outputStream.close();
                            }
                            this.e = false;
                            return false;
                        } catch (Throwable th4) {
                            e2222 = th4;
                            bufferedOutputStream = null;
                            a(httpGet);
                            a(defaultHttpClient);
                            if (bufferedOutputStream != null) {
                                bufferedOutputStream.close();
                            }
                            if (bufferedInputStream2 != null) {
                                bufferedInputStream2.close();
                            }
                            if (outputStream != null) {
                                outputStream.close();
                            }
                            throw e2222;
                        }
                        this.e = false;
                        return false;
                    } else {
                        e.a("PushSelfShowLog", "fail, httprespone status is " + a.getStatusLine().getStatusCode());
                        a(httpGet);
                        a(defaultHttpClient);
                        if (null != null) {
                            try {
                                bufferedOutputStream3.close();
                            } catch (Throwable e3222222222222) {
                                e.c("PushSelfShowLog", " bos download  error" + e3222222222222.toString(), e3222222222222);
                            }
                        }
                        if (null != null) {
                            try {
                                bufferedInputStream2.close();
                            } catch (Throwable e32222222222222) {
                                e.c("PushSelfShowLog", " bis download  error" + e32222222222222.toString(), e32222222222222);
                            }
                        }
                        if (null == null) {
                            return false;
                        }
                        try {
                            outputStream2.close();
                            return false;
                        } catch (Throwable e322222222222222) {
                            e.c("PushSelfShowLog", "out download  error" + e322222222222222.toString(), e322222222222222);
                            return false;
                        }
                    }
                } catch (IOException e7) {
                    e322222222222222 = e7;
                    httpClient = defaultHttpClient;
                    bufferedOutputStream2 = null;
                    bufferedInputStream = null;
                    e.c("PushSelfShowLog", "downLoadSgThread download  error" + e322222222222222.toString(), e322222222222222);
                    a(httpGet);
                    a(httpClient);
                    if (bufferedOutputStream2 != null) {
                        bufferedOutputStream2.close();
                    }
                    if (bufferedInputStream != null) {
                        bufferedInputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    this.e = false;
                    return false;
                } catch (Throwable th5) {
                    e2222 = th5;
                    bufferedInputStream2 = null;
                    bufferedOutputStream = null;
                    a(httpGet);
                    a(defaultHttpClient);
                    if (bufferedOutputStream != null) {
                        bufferedOutputStream.close();
                    }
                    if (bufferedInputStream2 != null) {
                        bufferedInputStream2.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    throw e2222;
                }
            } catch (IOException e8) {
                e322222222222222 = e8;
                httpGet = null;
                httpClient = defaultHttpClient;
                bufferedOutputStream2 = null;
                bufferedInputStream = null;
                e.c("PushSelfShowLog", "downLoadSgThread download  error" + e322222222222222.toString(), e322222222222222);
                a(httpGet);
                a(httpClient);
                if (bufferedOutputStream2 != null) {
                    bufferedOutputStream2.close();
                }
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                this.e = false;
                return false;
            } catch (Throwable th6) {
                e2222 = th6;
                httpGet = null;
                bufferedInputStream2 = null;
                bufferedOutputStream = null;
                a(httpGet);
                a(defaultHttpClient);
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
                if (bufferedInputStream2 != null) {
                    bufferedInputStream2.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                throw e2222;
            }
        } catch (IOException e9) {
            e322222222222222 = e9;
            httpGet = null;
            httpClient = null;
            bufferedInputStream = null;
            bufferedOutputStream2 = null;
            e.c("PushSelfShowLog", "downLoadSgThread download  error" + e322222222222222.toString(), e322222222222222);
            a(httpGet);
            a(httpClient);
            if (bufferedOutputStream2 != null) {
                bufferedOutputStream2.close();
            }
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            this.e = false;
            return false;
        } catch (Throwable th7) {
            e2222 = th7;
            httpGet = null;
            defaultHttpClient = null;
            bufferedInputStream2 = null;
            bufferedOutputStream = null;
            a(httpGet);
            a(defaultHttpClient);
            if (bufferedOutputStream != null) {
                bufferedOutputStream.close();
            }
            if (bufferedInputStream2 != null) {
                bufferedInputStream2.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            throw e2222;
        }
    }

    public void c() {
        Message message = new Message();
        message.what = 2;
        e.a("PushSelfShowLog", "mDownloadHandler = " + this.a);
        if (this.a != null) {
            this.a.sendMessageDelayed(message, 1);
        }
    }
}
