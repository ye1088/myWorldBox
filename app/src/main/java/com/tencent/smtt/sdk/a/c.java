package com.tencent.smtt.sdk.a;

import MTT.a;
import android.os.Build.VERSION;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.k;
import com.tencent.smtt.utils.o;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

final class c extends Thread {
    final /* synthetic */ a a;

    c(String str, a aVar) {
        this.a = aVar;
        super(str);
    }

    public void run() {
        if (VERSION.SDK_INT >= 8) {
            if (b.a == null) {
                try {
                    b.a = "65dRa93L".getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    b.a = null;
                    TbsLog.e("sdkreport", "Post failed -- get POST_DATA_KEY failed!");
                }
            }
            if (b.a == null) {
                TbsLog.e("sdkreport", "Post failed -- POST_DATA_KEY is null!");
                return;
            }
            try {
                JSONObject b;
                String b2 = o.a().b();
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(b2 + k.a().b()).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setConnectTimeout(20000);
                if (VERSION.SDK_INT > 13) {
                    httpURLConnection.setRequestProperty("Connection", "close");
                }
                try {
                    b = b.c(this.a);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    b = null;
                }
                if (b == null) {
                    TbsLog.e("sdkreport", "post -- jsonData is null!");
                    return;
                }
                try {
                    byte[] a = k.a().a(b.toString().getBytes("utf-8"));
                    httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    httpURLConnection.setRequestProperty("Content-Length", String.valueOf(a.length));
                    try {
                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        outputStream.write(a);
                        outputStream.flush();
                        if (httpURLConnection.getResponseCode() != 200) {
                            TbsLog.e("sdkreport", "Post failed -- not 200");
                        }
                    } catch (Throwable th) {
                        TbsLog.e("sdkreport", "Post failed -- exceptions:" + th.getMessage());
                    }
                } catch (Throwable th2) {
                }
            } catch (IOException e3) {
                TbsLog.e("sdkreport", "Post failed -- IOException:" + e3);
            } catch (AssertionError e4) {
                TbsLog.e("sdkreport", "Post failed -- AssertionError:" + e4);
            } catch (NoClassDefFoundError e5) {
                TbsLog.e("sdkreport", "Post failed -- NoClassDefFoundError:" + e5);
            }
        }
    }
}
