package com.mojang.android.net;

import org.apache.http.Header;

public class HTTPResponse {
    public static final int ABORTED = 2;
    private static final boolean DEBUG = false;
    public static final int DONE = 1;
    public static final int IN_PROGRESS = 0;
    public static final int TIME_OUT = 3;
    String body;
    Header[] headers;
    int responseCode;
    int status;

    public HTTPResponse() {
        this.body = "";
        this.responseCode = -100;
        this.status = 0;
        this.status = 0;
        this.responseCode = 0;
        this.body = "";
        this.headers = null;
    }

    public HTTPResponse(int paramInt1, int paramInt2, String paramString, Header[] paramArrayOfHeader) {
        this.body = "";
        this.responseCode = -100;
        this.status = 0;
        this.status = paramInt1;
        this.responseCode = paramInt2;
        this.body = paramString;
        this.headers = paramArrayOfHeader;
    }

    public Header[] getHeaders() {
        return this.headers;
    }

    public void setHeaders(Header[] paramArrayOfHeader) {
        this.headers = paramArrayOfHeader;
    }

    public String getBody() {
        return this.body;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public int getStatus() {
        return this.status;
    }

    public void setBody(String paramString) {
        this.body = paramString;
    }

    public void setResponseCode(int paramInt) {
        this.responseCode = paramInt;
    }

    public void setStatus(int paramInt) {
        this.status = paramInt;
    }
}
