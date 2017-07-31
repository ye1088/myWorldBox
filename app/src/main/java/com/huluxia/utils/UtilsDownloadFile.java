package com.huluxia.utils;

import android.content.Context;
import android.os.AsyncTask;
import com.huluxia.image.core.common.util.e;
import com.huluxia.widget.dialog.l;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UtilsDownloadFile extends AsyncTask<String, String, String> {
    public static final int blg = 1;
    public static final int blh = 2;
    public static final int bli = 3;
    private a blj;
    private l blk = null;
    private String bll;
    private Context context;

    protected /* synthetic */ Object doInBackground(Object[] objArr) {
        return c((String[]) objArr);
    }

    protected /* synthetic */ void onPostExecute(Object obj) {
        ce((String) obj);
    }

    protected /* synthetic */ void onProgressUpdate(Object[] objArr) {
        d((String[]) objArr);
    }

    public UtilsDownloadFile(String appPath, a callback) {
        this.bll = appPath;
        this.blj = callback;
    }

    public UtilsDownloadFile(Context context, String appPath, boolean showProgress, a callback) {
        this.context = context;
        this.bll = appPath;
        this.blj = callback;
        if (showProgress) {
            this.blk = new l(context);
            this.blk.setTitle("等待下载:");
            this.blk.setCancelable(false);
        }
    }

    protected void onPreExecute() {
        if (this.blk != null) {
            this.blk.show();
        }
        if (this.blj != null) {
            this.blj.h(1, this.bll);
        }
    }

    protected String c(String... arg0) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(arg0[0]).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(e.yQ);
            conn.setUseCaches(false);
            conn.connect();
            if (conn.getResponseCode() == -1) {
                return null;
            }
            FileOutputStream bos = new FileOutputStream(new File(this.bll));
            long lenghtOfFile = (long) conn.getContentLength();
            InputStream bis = conn.getInputStream();
            byte[] buffer = new byte[1024];
            long total = 0;
            while (true) {
                int len = bis.read(buffer);
                if (len == -1) {
                    break;
                }
                bos.write(buffer, 0, len);
                total += (long) len;
                publishProgress(new String[]{"" + ((int) ((100 * total) / lenghtOfFile))});
            }
            bis.close();
            bos.close();
            conn.disconnect();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void d(String... progress) {
        if (this.blk != null) {
            this.blk.setTitle("正在下载:");
            this.blk.setProgress(Integer.parseInt(progress[0]));
        }
        if (this.blj != null) {
            this.blj.h(2, this.bll);
            this.blj.au(Integer.parseInt(progress[0]), 100);
        }
    }

    protected void ce(String result) {
        if (this.blk != null) {
            this.blk.dismiss();
        }
        if (this.blj != null) {
            this.blj.h(3, this.bll);
        }
    }
}
