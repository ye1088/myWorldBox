package com.huluxia.framework.base.http.transport;

import android.os.SystemClock;
import com.huluxia.framework.base.http.datasource.cache.Cache.Entry;
import com.huluxia.framework.base.http.io.Request;
import com.huluxia.framework.base.http.io.Response.ProgressListener;
import com.huluxia.framework.base.http.toolbox.HttpLog;
import com.huluxia.framework.base.http.toolbox.PoolingByteArrayOutputStream;
import com.huluxia.framework.base.http.toolbox.error.CancelError;
import com.huluxia.framework.base.http.toolbox.error.ServerError;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.http.toolbox.retrypolicy.RetryPolicy;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.ByteArrayPool;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.impl.cookie.DateUtils;

public class ImageNetwork implements Network<Request<?>> {
    private static int DEFAULT_POOL_SIZE = 4096;
    private static int SLOW_REQUEST_THRESHOLD_MS = 3000;
    private static final String TAG = "BasicNetwork";
    private final HttpStack mHttpStack;
    private final ByteArrayPool mPool;

    public ImageNetwork(HttpStack httpStack) {
        this(httpStack, new ByteArrayPool(DEFAULT_POOL_SIZE));
    }

    public ImageNetwork(HttpStack httpStack, ByteArrayPool pool) {
        this.mHttpStack = httpStack;
        this.mPool = pool;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.huluxia.framework.base.http.io.NetworkResponse performRequest(com.huluxia.framework.base.http.io.Request<?> r23) throws com.huluxia.framework.base.http.toolbox.error.VolleyError {
        /*
        r22 = this;
        r16 = android.os.SystemClock.elapsedRealtime();
    L_0x0004:
        r13 = 0;
        r9 = 0;
        r18 = new java.util.HashMap;
        r18.<init>();
        r4 = 0;
        r23.prepare();	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        r12 = new java.util.HashMap;	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        r12.<init>();	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        r5 = r23.getCacheEntry();	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        r0 = r22;
        r0.addCacheHeaders(r12, r5);	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        r0 = r22;
        r5 = r0.mHttpStack;	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        r8 = 0;
        r0 = r23;
        r14 = r5.performRequest(r0, r12, r8);	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        r5 = r14.first;	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        r0 = r5;
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        r4 = r0;
        r5 = r14.second;	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        r0 = r5;
        r0 = (org.apache.http.HttpResponse) r0;	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        r13 = r0;
        r10 = r13.getStatusLine();	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        r19 = r10.getStatusCode();	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        r5 = r13.getAllHeaders();	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        r18 = convertHeaders(r5);	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        r5 = 304; // 0x130 float:4.26E-43 double:1.5E-321;
        r0 = r19;
        if (r0 != r5) goto L_0x006d;
    L_0x004a:
        r5 = new com.huluxia.framework.base.http.io.NetworkResponse;	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        r20 = 304; // 0x130 float:4.26E-43 double:1.5E-321;
        r8 = r23.getCacheEntry();	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        if (r8 != 0) goto L_0x0066;
    L_0x0054:
        r8 = 0;
    L_0x0055:
        r21 = 1;
        r0 = r20;
        r1 = r18;
        r2 = r21;
        r5.<init>(r0, r8, r1, r2);	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        if (r4 == 0) goto L_0x0065;
    L_0x0062:
        r4.disconnect();
    L_0x0065:
        return r5;
    L_0x0066:
        r8 = r23.getCacheEntry();	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        r8 = r8.data;	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        goto L_0x0055;
    L_0x006d:
        r5 = r13.getEntity();	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        if (r5 == 0) goto L_0x00cd;
    L_0x0073:
        r5 = r13.getEntity();	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        r0 = r22;
        r1 = r23;
        r9 = r0.entityToBytes(r5, r1);	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
    L_0x007f:
        r20 = android.os.SystemClock.elapsedRealtime();	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        r6 = r20 - r16;
        r5 = r22;
        r8 = r23;
        r5.logSlowRequests(r6, r8, r9, r10);	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        r5 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r0 = r19;
        if (r0 < r5) goto L_0x0098;
    L_0x0092:
        r5 = 299; // 0x12b float:4.19E-43 double:1.477E-321;
        r0 = r19;
        if (r0 <= r5) goto L_0x00d1;
    L_0x0098:
        r5 = new java.io.IOException;	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        r5.<init>();	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        throw r5;	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
    L_0x009e:
        r11 = move-exception;
        r5 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0126 }
        r5.<init>();	 Catch:{ all -> 0x0126 }
        r8 = "basic network socket time out ex = ";
        r5 = r5.append(r8);	 Catch:{ all -> 0x0126 }
        r5 = r5.append(r11);	 Catch:{ all -> 0x0126 }
        r5 = r5.toString();	 Catch:{ all -> 0x0126 }
        r8 = 0;
        r8 = new java.lang.Object[r8];	 Catch:{ all -> 0x0126 }
        com.huluxia.framework.base.http.toolbox.HttpLog.e(r5, r8);	 Catch:{ all -> 0x0126 }
        r5 = "socket";
        r8 = new com.huluxia.framework.base.http.toolbox.error.TimeoutError;	 Catch:{ all -> 0x0126 }
        r8.<init>();	 Catch:{ all -> 0x0126 }
        r0 = r23;
        attemptRetryOnException(r5, r0, r8);	 Catch:{ all -> 0x0126 }
        if (r4 == 0) goto L_0x0004;
    L_0x00c8:
        r4.disconnect();
        goto L_0x0004;
    L_0x00cd:
        r5 = 0;
        r9 = new byte[r5];	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        goto L_0x007f;
    L_0x00d1:
        r5 = new com.huluxia.framework.base.http.io.NetworkResponse;	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        r8 = 0;
        r0 = r19;
        r1 = r18;
        r5.<init>(r0, r9, r1, r8);	 Catch:{ SocketTimeoutException -> 0x009e, ConnectTimeoutException -> 0x00e1, MalformedURLException -> 0x0103, InvalidParamError -> 0x012d, IOException -> 0x0137 }
        if (r4 == 0) goto L_0x0065;
    L_0x00dd:
        r4.disconnect();
        goto L_0x0065;
    L_0x00e1:
        r11 = move-exception;
        r5 = "basic network connect time out ex";
        r8 = 1;
        r8 = new java.lang.Object[r8];	 Catch:{ all -> 0x0126 }
        r20 = 0;
        r8[r20] = r11;	 Catch:{ all -> 0x0126 }
        com.huluxia.framework.base.http.toolbox.HttpLog.d(r5, r8);	 Catch:{ all -> 0x0126 }
        r5 = "connection";
        r8 = new com.huluxia.framework.base.http.toolbox.error.TimeoutError;	 Catch:{ all -> 0x0126 }
        r8.<init>();	 Catch:{ all -> 0x0126 }
        r0 = r23;
        attemptRetryOnException(r5, r0, r8);	 Catch:{ all -> 0x0126 }
        if (r4 == 0) goto L_0x0004;
    L_0x00fe:
        r4.disconnect();
        goto L_0x0004;
    L_0x0103:
        r11 = move-exception;
        r5 = new java.lang.RuntimeException;	 Catch:{ all -> 0x0126 }
        r8 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0126 }
        r8.<init>();	 Catch:{ all -> 0x0126 }
        r20 = "Bad URL ";
        r0 = r20;
        r8 = r8.append(r0);	 Catch:{ all -> 0x0126 }
        r20 = r23.getUrl();	 Catch:{ all -> 0x0126 }
        r0 = r20;
        r8 = r8.append(r0);	 Catch:{ all -> 0x0126 }
        r8 = r8.toString();	 Catch:{ all -> 0x0126 }
        r5.<init>(r8, r11);	 Catch:{ all -> 0x0126 }
        throw r5;	 Catch:{ all -> 0x0126 }
    L_0x0126:
        r5 = move-exception;
        if (r4 == 0) goto L_0x012c;
    L_0x0129:
        r4.disconnect();
    L_0x012c:
        throw r5;
    L_0x012d:
        r11 = move-exception;
        r5 = new com.huluxia.framework.base.http.toolbox.error.InvalidParamError;	 Catch:{ all -> 0x0126 }
        r8 = "invalid request param";
        r5.<init>(r8);	 Catch:{ all -> 0x0126 }
        throw r5;	 Catch:{ all -> 0x0126 }
    L_0x0137:
        r11 = move-exception;
        r19 = 0;
        r15 = 0;
        if (r13 == 0) goto L_0x018a;
    L_0x013d:
        r5 = r13.getStatusLine();	 Catch:{ all -> 0x0126 }
        r19 = r5.getStatusCode();	 Catch:{ all -> 0x0126 }
        r5 = "Unexpected response code %d for %s";
        r8 = 2;
        r8 = new java.lang.Object[r8];	 Catch:{ all -> 0x0126 }
        r20 = 0;
        r21 = java.lang.Integer.valueOf(r19);	 Catch:{ all -> 0x0126 }
        r8[r20] = r21;	 Catch:{ all -> 0x0126 }
        r20 = 1;
        r21 = r23.getUrl();	 Catch:{ all -> 0x0126 }
        r8[r20] = r21;	 Catch:{ all -> 0x0126 }
        com.huluxia.framework.base.http.toolbox.HttpLog.e(r5, r8);	 Catch:{ all -> 0x0126 }
        if (r9 == 0) goto L_0x0196;
    L_0x0160:
        r15 = new com.huluxia.framework.base.http.io.NetworkResponse;	 Catch:{ all -> 0x0126 }
        r5 = 0;
        r0 = r19;
        r1 = r18;
        r15.<init>(r0, r9, r1, r5);	 Catch:{ all -> 0x0126 }
        r5 = 401; // 0x191 float:5.62E-43 double:1.98E-321;
        r0 = r19;
        if (r0 == r5) goto L_0x0176;
    L_0x0170:
        r5 = 403; // 0x193 float:5.65E-43 double:1.99E-321;
        r0 = r19;
        if (r0 != r5) goto L_0x0190;
    L_0x0176:
        r5 = "auth";
        r8 = new com.huluxia.framework.base.http.toolbox.error.AuthFailureError;	 Catch:{ all -> 0x0126 }
        r8.<init>(r15);	 Catch:{ all -> 0x0126 }
        r0 = r23;
        attemptRetryOnException(r5, r0, r8);	 Catch:{ all -> 0x0126 }
        if (r4 == 0) goto L_0x0004;
    L_0x0185:
        r4.disconnect();
        goto L_0x0004;
    L_0x018a:
        r5 = new com.huluxia.framework.base.http.toolbox.error.NoConnectionError;	 Catch:{ all -> 0x0126 }
        r5.<init>(r11);	 Catch:{ all -> 0x0126 }
        throw r5;	 Catch:{ all -> 0x0126 }
    L_0x0190:
        r5 = new com.huluxia.framework.base.http.toolbox.error.ServerError;	 Catch:{ all -> 0x0126 }
        r5.<init>(r15);	 Catch:{ all -> 0x0126 }
        throw r5;	 Catch:{ all -> 0x0126 }
    L_0x0196:
        r5 = new com.huluxia.framework.base.http.toolbox.error.NetworkError;	 Catch:{ all -> 0x0126 }
        r5.<init>(r15);	 Catch:{ all -> 0x0126 }
        throw r5;	 Catch:{ all -> 0x0126 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.framework.base.http.transport.ImageNetwork.performRequest(com.huluxia.framework.base.http.io.Request):com.huluxia.framework.base.http.io.NetworkResponse");
    }

    protected void logSlowRequests(long requestLifetime, Request<?> request, byte[] responseContents, StatusLine statusLine) {
        if (requestLifetime > ((long) SLOW_REQUEST_THRESHOLD_MS)) {
            String str = "HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]";
            Object[] objArr = new Object[5];
            objArr[0] = request;
            objArr[1] = Long.valueOf(requestLifetime);
            objArr[2] = responseContents != null ? Integer.valueOf(responseContents.length) : "null";
            objArr[3] = Integer.valueOf(statusLine.getStatusCode());
            objArr[4] = Integer.valueOf(request.getRetryPolicy().getCurrentRetryCount());
            HttpLog.d(str, objArr);
        }
    }

    protected static void attemptRetryOnException(String logPrefix, Request<?> request, VolleyError exception) throws VolleyError {
        HttpLog.wtf("attemptRetryOnException logPrefix %s ex %s", logPrefix, exception.toString());
        if (request.isCanceled()) {
            request.addMarker("cancel-retry");
            throw new CancelError(String.format("cancel retry e %s", new Object[]{exception}));
        }
        RetryPolicy retryPolicy = request.getRetryPolicy();
        int oldTimeout = request.getTimeoutMs();
        try {
            retryPolicy.retry(exception);
            request.addMarker(String.format("%s-retry [timeout=%s]", new Object[]{logPrefix, Integer.valueOf(oldTimeout)}));
        } catch (VolleyError e) {
            request.addMarker(String.format("%s-timeout-giveup [timeout=%s]", new Object[]{logPrefix, Integer.valueOf(oldTimeout)}));
            throw e;
        }
    }

    protected void addCacheHeaders(Map<String, String> headers, Entry entry) {
        if (entry != null) {
            if (entry.etag != null) {
                headers.put("If-None-Match", entry.etag);
            }
            if (entry.serverDate > 0) {
                headers.put("If-Modified-Since", DateUtils.formatDate(new Date(entry.serverDate)));
            }
        }
    }

    protected void logError(String what, String url, long start) {
        long now = SystemClock.elapsedRealtime();
        HttpLog.v("HTTP ERROR(%s) %d ms to fetch %s", what, Long.valueOf(now - start), url);
    }

    protected byte[] entityToBytes(HttpEntity entity, Request<?> request) throws IOException, ServerError {
        if (entity.getContentLength() > 10485760) {
            HLog.error(TAG, "entity too large " + entity.getContentLength() + ", url " + request.getUrl(), new Object[0]);
            throw new IOException("HTTP entity too large to be buffered in memory");
        }
        try {
            PoolingByteArrayOutputStream bytes = new PoolingByteArrayOutputStream(this.mPool, (int) entity.getContentLength());
            byte[] bArr = null;
            try {
                InputStream in = entity.getContent();
                long total = entity.getContentLength();
                if (in == null) {
                    throw new ServerError();
                }
                bArr = this.mPool.getBuf(1024);
                int progress = 0;
                ProgressListener progressListener = request.getProgressListener();
                while (true) {
                    int count = in.read(bArr);
                    if (count == -1) {
                        break;
                    }
                    bytes.write(bArr, 0, count);
                    progress += count;
                    if (progressListener != null) {
                        progressListener.onProgress(request.getUrl(), total, (long) progress, 0.0f);
                    }
                }
                byte[] toByteArray = bytes.toByteArray();
                return toByteArray;
            } finally {
                try {
                    entity.consumeContent();
                } catch (IOException e) {
                    HttpLog.v("Error occured when calling consumingContent", new Object[0]);
                }
                this.mPool.returnBuf(bArr);
                bytes.close();
            }
        } catch (OutOfMemoryError oom) {
            HLog.error(TAG, "entity content too large, oom " + oom, new Object[0]);
            throw new IOException("entity content too large");
        }
    }

    protected static Map<String, String> convertHeaders(Header[] headers) {
        Map<String, String> result = new HashMap();
        for (int i = 0; i < headers.length; i++) {
            result.put(headers[i].getName(), headers[i].getValue());
        }
        return result;
    }
}
