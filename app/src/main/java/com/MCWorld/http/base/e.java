package com.MCWorld.http.base;

import android.os.Handler;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* compiled from: HttpFile */
public class e {
    public static byte[] a(String imageUrl, Handler handler) {
        byte[] data = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(imageUrl).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(com.MCWorld.image.core.common.util.e.yQ);
            conn.setUseCaches(false);
            conn.connect();
            if (conn.getResponseCode() == -1) {
                return null;
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            long total = (long) conn.getContentLength();
            InputStream bis = conn.getInputStream();
            byte[] buffer = new byte[1024];
            int read = 0;
            while (true) {
                int len = bis.read(buffer);
                if (len == -1) {
                    break;
                }
                bos.write(buffer, 0, len);
                read += len;
                if (handler != null) {
                    handler.obtainMessage((int) (((((double) read) * 1.0d) / ((double) total)) * 100.0d)).sendToTarget();
                }
            }
            bis.close();
            if (bos.size() > 0) {
                data = bos.toByteArray();
            }
            bos.close();
            conn.disconnect();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
