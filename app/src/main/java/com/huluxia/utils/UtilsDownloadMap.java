package com.huluxia.utils;

import android.os.AsyncTask;
import com.huluxia.http.base.b;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

public class UtilsDownloadMap extends AsyncTask<String, String, String> {
    public static final int blg = 1;
    public static final int blh = 2;
    public static final int bli = 3;
    private String bll;
    private a bls;
    private long blt;

    public interface a {
        void a(int i, String str, long j);

        void b(int i, int i2, long j);
    }

    protected /* synthetic */ Object doInBackground(Object[] objArr) {
        return c((String[]) objArr);
    }

    protected /* synthetic */ void onPostExecute(Object obj) {
        ce((String) obj);
    }

    protected /* synthetic */ void onProgressUpdate(Object[] objArr) {
        d((String[]) objArr);
    }

    public UtilsDownloadMap(String appPath, a callback, long downID) {
        this.bll = appPath;
        this.blt = downID;
        this.bls = callback;
    }

    protected void onPreExecute() {
        if (this.bls != null) {
            this.bls.a(1, this.bll, this.blt);
        }
    }

    protected String c(String... arg0) {
        Exception e;
        Throwable th;
        HttpGet httpGet = null;
        try {
            HttpClient httpClient = b.eS();
            HttpGet httpGet2 = new HttpGet(arg0[0]);
            try {
                HttpResponse response = httpClient.execute(httpGet2);
                if (response.getStatusLine().getStatusCode() != 200) {
                    if (httpGet2 != null) {
                        httpGet2.abort();
                    }
                    httpGet = httpGet2;
                    return null;
                }
                HttpEntity entity = response.getEntity();
                FileOutputStream bos = new FileOutputStream(new File(this.bll));
                long lenghtOfFile = entity.getContentLength();
                InputStream bis = entity.getContent();
                byte[] buffer = new byte[1024];
                int total = 0;
                while (true) {
                    int len = bis.read(buffer);
                    if (len == -1) {
                        break;
                    }
                    bos.write(buffer, 0, len);
                    total += len;
                    publishProgress(new String[]{"" + ((int) (((long) (total * 100)) / lenghtOfFile))});
                }
                entity.consumeContent();
                bis.close();
                bos.close();
                if (httpGet2 != null) {
                    httpGet2.abort();
                }
                httpGet = httpGet2;
                return null;
            } catch (Exception e2) {
                e = e2;
                httpGet = httpGet2;
                try {
                    e.printStackTrace();
                    if (httpGet != null) {
                        httpGet.abort();
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    if (httpGet != null) {
                        httpGet.abort();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                httpGet = httpGet2;
                if (httpGet != null) {
                    httpGet.abort();
                }
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            if (httpGet != null) {
                httpGet.abort();
            }
            return null;
        }
    }

    protected void d(String... progress) {
        if (this.bls != null) {
            this.bls.a(2, this.bll, this.blt);
            this.bls.b(Integer.parseInt(progress[0]), 100, this.blt);
        }
    }

    protected void ce(String result) {
        if (this.bls != null) {
            this.bls.a(3, this.bll, this.blt);
        }
    }
}
