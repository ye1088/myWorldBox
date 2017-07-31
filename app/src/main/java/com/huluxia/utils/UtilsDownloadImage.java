package com.huluxia.utils;

import android.os.AsyncTask;
import android.os.SystemClock;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.image.core.common.util.e;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UtilsDownloadImage extends AsyncTask<String, Void, Integer> {
    private static final String TAG = "UtilsDownloadImage";
    private static final String blm = ".jpeg";
    private static final String bln = ".jpg";
    private static final String blo = ".gif";
    public static final int blp = 0;
    public static final int blq = 1;
    private a blr;

    public interface a {
        void onDownloadFinish(int i);
    }

    protected /* synthetic */ Object doInBackground(Object[] objArr) {
        return e((String[]) objArr);
    }

    protected /* synthetic */ void onPostExecute(Object obj) {
        b((Integer) obj);
    }

    public UtilsDownloadImage(a callback) {
        this.blr = callback;
    }

    protected Integer e(String... params) {
        try {
            String netPath = params[0];
            HLog.info(TAG, "netImage path = " + netPath, new Object[0]);
            HttpURLConnection conn = (HttpURLConnection) new URL(netPath).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(e.yQ);
            conn.setUseCaches(true);
            conn.connect();
            if (conn.getResponseCode() != 200) {
                return Integer.valueOf(0);
            }
            String savePath = UtilsFile.KP() + File.separator + SystemClock.elapsedRealtime() + blo;
            HLog.info(TAG, "save image path = " + savePath, new Object[0]);
            FileOutputStream bos = new FileOutputStream(new File(savePath));
            InputStream bis = conn.getInputStream();
            byte[] buffer = new byte[1024];
            while (true) {
                int len = bis.read(buffer);
                if (len == -1) {
                    break;
                }
                bos.write(buffer, 0, len);
            }
            bis.close();
            bos.close();
            conn.disconnect();
            return Integer.valueOf(1);
        } catch (Exception e) {
            HLog.error(TAG, "download image error, error = " + e, new Object[0]);
        }
    }

    protected void b(Integer integer) {
        super.onPostExecute(integer);
        this.blr.onDownloadFinish(integer.intValue());
    }
}
