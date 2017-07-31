package com.tencent.smtt.utils;

import com.tencent.smtt.utils.b.a;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

final class g extends Thread {
    final /* synthetic */ String a;
    final /* synthetic */ a b;

    g(String str, a aVar) {
        this.a = str;
        this.b = aVar;
    }

    public void run() {
        InputStream inputStream;
        Throwable e;
        OutputStream outputStream = null;
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("http://soft.tbs.imtt.qq.com/17421/tbs_res_imtt_tbs_DebugPlugin_DebugPlugin.tbs").openConnection();
            int contentLength = httpURLConnection.getContentLength();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            try {
                outputStream = h.b(new File(this.a));
                byte[] bArr = new byte[1024];
                int i = 0;
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read <= 0) {
                        break;
                    }
                    i += read;
                    outputStream.write(bArr, 0, read);
                    this.b.a((i * 100) / contentLength);
                }
                this.b.a();
                try {
                    inputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                try {
                    outputStream.close();
                } catch (Exception e22) {
                    e22.printStackTrace();
                }
            } catch (Exception e3) {
                e = e3;
                try {
                    e.printStackTrace();
                    this.b.a(e);
                    try {
                        inputStream.close();
                    } catch (Exception e222) {
                        e222.printStackTrace();
                    }
                    try {
                        outputStream.close();
                    } catch (Exception e2222) {
                        e2222.printStackTrace();
                    }
                } catch (Throwable th) {
                    e = th;
                    try {
                        inputStream.close();
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                    try {
                        outputStream.close();
                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                    throw e;
                }
            }
        } catch (Exception e6) {
            e = e6;
            inputStream = null;
            e.printStackTrace();
            this.b.a(e);
            inputStream.close();
            outputStream.close();
        } catch (Throwable th2) {
            e = th2;
            inputStream = null;
            inputStream.close();
            outputStream.close();
            throw e;
        }
    }
}
