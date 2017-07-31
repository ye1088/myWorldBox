package com.huluxia.utils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.huluxia.framework.base.log.HLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/* compiled from: UtilsRequest */
public class ap {

    /* compiled from: UtilsRequest */
    public static abstract class a {
        private String anR = null;
        private List<NameValuePair> anS = null;
        private Runnable anU = new Runnable(this) {
            final /* synthetic */ a bmP;

            {
                this.bmP = this$0;
            }

            public void run() {
                String strResult;
                if (this.bmP.anS == null) {
                    strResult = ap.dE(this.bmP.anR);
                } else if (this.bmP.bmO != null && this.bmP.mType == 0) {
                    strResult = ap.as(this.bmP.anR, this.bmP.bmO);
                } else if (this.bmP.bmO == null || this.bmP.mType != 1) {
                    strResult = ap.a(this.bmP.anR, this.bmP.anS, this.bmP.bmO);
                } else {
                    strResult = ap.b(this.bmP.anR, this.bmP.anS, this.bmP.bmO);
                }
                Bundle bundle = new Bundle();
                bundle.putString("ret", strResult);
                Message msg = this.bmP.mHandler.obtainMessage(1);
                msg.setData(bundle);
                this.bmP.mHandler.sendMessage(msg);
            }
        };
        private String bmO = null;
        @SuppressLint({"HandlerLeak"})
        private Handler mHandler = new Handler(this) {
            final /* synthetic */ a bmP;

            {
                this.bmP = this$0;
            }

            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    this.bmP.dv(aw.gm(msg.getData().getString("ret")));
                }
            }
        };
        private int mType = 0;

        protected abstract List<NameValuePair> Ci();

        protected abstract void dv(String str);

        public void dD(String url) {
            this.anR = url;
            this.anS = Ci();
            new Thread(this.anU).start();
        }

        public void c(String url, String refer, int type) {
            this.anR = url;
            this.anS = Ci();
            this.bmO = refer;
            this.mType = type;
            new Thread(this.anU).start();
        }
    }

    public static String dE(String strUrl) {
        try {
            HttpResponse httpResponse = new DefaultHttpClient().execute(new HttpGet(strUrl));
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            HLog.error("UtilsRequest", "sendHttpGetRequest " + e.getMessage(), new Object[0]);
        }
        return "";
    }

    public static String as(String strUrl, String refer) {
        try {
            HttpGet httpRequest = new HttpGet(strUrl);
            if (refer != null) {
                httpRequest.addHeader("Referer", refer);
            }
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            HLog.error("UtilsRequest", "sendHttpGetRequest " + e.getMessage(), new Object[0]);
        }
        return "";
    }

    public static String c(String strUrl, List<NameValuePair> params) {
        return a(strUrl, params, null);
    }

    public static String a(String strUrl, List<NameValuePair> params, String refer) {
        if (params == null) {
            params = new ArrayList();
        }
        try {
            HttpPost httpPost = new HttpPost(strUrl);
            if (refer != null) {
                httpPost.addHeader("Referer", refer);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (IOException e) {
        }
        return "";
    }

    public static String b(String strUrl, List<NameValuePair> params, String refer) {
        if (params == null) {
            params = new ArrayList();
        }
        try {
            HttpPost httpPost = new HttpPost(strUrl);
            if (refer != null) {
                httpPost.setHeader("Referer", refer);
            }
            httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml; q=0.9,image/webp,*/*;q=0.8");
            httpPost.setHeader("Accept-Encoding", "text/html");
            httpPost.setHeader("Accept-Language", "en-US,en;q=0.8,zh-CN;q=0.6,zh;q=0.4,zh-TW;q=0.2");
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36");
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (IOException e) {
        }
        return "";
    }
}
