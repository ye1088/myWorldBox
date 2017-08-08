package com.mojang.android.net;

import android.text.TextUtils;
import com.MCWorld.mcsdk.dtlib.h;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.security.InvalidParameterException;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

public class HTTPRequest {
    private static final boolean DEBUG = false;
    String mCookieData = "";
    HttpRequestBase mHTTPRequest = null;
    String mRequestBody = "";
    String mRequestContentType = "text/plain";
    HTTPResponse mResponse = new HTTPResponse();
    String mURL = "";

    public void abort() {
    }

    private static Header[] toApacheHeaders(HttpURLConnection paramHttpURLConnection) {
        Map localMap = paramHttpURLConnection.getHeaderFields();
        Header[] arrayOfHeader = new Header[localMap.size()];
        for (int i = localMap.size() - 1; i > 0; i--) {
            int j = 0;
            for (Entry localEntry : localMap.entrySet()) {
                if (localEntry.getKey() != null) {
                    int k = j + 1;
                    arrayOfHeader[j] = new BasicHeader((String) localEntry.getKey(), TextUtils.join(MiPushClient.ACCEPT_TIME_SEPARATOR, (Iterable) localEntry.getValue()));
                    j = k;
                }
            }
        }
        return arrayOfHeader;
    }

    private void addBodyToRequest(HttpEntityEnclosingRequestBase paramHttpEntityEnclosingRequestBase) {
        if (this.mRequestBody != "") {
            try {
            } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
                localUnsupportedEncodingException.printStackTrace();
                return;
            }
        }
        StringEntity localStringEntity = new StringEntity(this.mRequestBody);
        localStringEntity.setContentType(this.mRequestContentType);
        paramHttpEntityEnclosingRequestBase.setEntity(localStringEntity);
    }

    private void addHeaders() {
        this.mHTTPRequest.addHeader("User-Agent", "MCPE/Android");
        BasicHttpParams localBasicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 3000);
        this.mHTTPRequest.setParams(localBasicHttpParams);
        if (this.mCookieData != null && this.mCookieData.length() > 0) {
            this.mHTTPRequest.addHeader("Cookie", this.mCookieData);
        }
        this.mHTTPRequest.addHeader("Content-Type", this.mRequestContentType);
        this.mHTTPRequest.addHeader("Charset", "utf-8");
    }

    private void createHTTPRequest(String paramString) {
        while (true) {
            try {
                h.iJ(20);
                if (paramString.equals("DELETE")) {
                    this.mHTTPRequest = new HttpDelete(this.mURL);
                } else if (paramString.equals("PUT")) {
                    HttpPut localHttpPut = new HttpPut(this.mURL);
                    addBodyToRequest(localHttpPut);
                    this.mHTTPRequest = localHttpPut;
                }
                if (paramString.equals("GET")) {
                    this.mHTTPRequest = new HttpGet(this.mURL);
                } else if (paramString.equals("POST")) {
                    HttpPost localHttpPost = new HttpPost(this.mURL);
                    addBodyToRequest(localHttpPost);
                    this.mHTTPRequest = localHttpPost;
                } else {
                    return;
                }
            } catch (Exception e) {
                throw new InvalidParameterException("Unknown request method " + paramString);
            }
        }
    }

    public HTTPResponse send(String paramString) {
        return new HTTPResponse();
    }

    public void setContentType(String paramString) {
        this.mRequestContentType = paramString;
    }

    public void setCookieData(String paramString) {
        this.mCookieData = paramString;
    }

    public void setRequestBody(String paramString) {
        this.mRequestBody = paramString;
    }

    public void setURL(String paramString) {
        this.mURL = paramString;
    }
}
