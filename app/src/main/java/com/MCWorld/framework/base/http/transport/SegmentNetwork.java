package com.MCWorld.framework.base.http.transport;

import android.os.SystemClock;
import com.MCWorld.framework.base.http.datasource.cache.Cache.Entry;
import com.MCWorld.framework.base.http.io.impl.request.SegmentRequest;
import com.MCWorld.framework.base.http.toolbox.HttpLog;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import com.MCWorld.framework.base.http.toolbox.error.CancelError;
import com.MCWorld.framework.base.http.toolbox.error.CannotResumeError;
import com.MCWorld.framework.base.http.toolbox.error.CloseSocketBeforeEndError;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.http.toolbox.retrypolicy.RetryPolicy;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.ByteArrayPool;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.impl.cookie.DateUtils;

public class SegmentNetwork implements Network<SegmentRequest> {
    private static int DEFAULT_POOL_SIZE = 8192;
    private static int SLOW_REQUEST_THRESHOLD_MS = 3000;
    private static final String TAG = "SegmentNetwork";
    private final ByteArrayPool mByteArrayPool;
    private final HttpStack mHttpStack;

    public SegmentNetwork(HttpStack httpStack) {
        this(httpStack, null);
    }

    public SegmentNetwork(HttpStack httpStack, ByteArrayPool byteArrayPool) {
        this.mHttpStack = httpStack;
        if (byteArrayPool == null) {
            byteArrayPool = new ByteArrayPool(DEFAULT_POOL_SIZE);
        }
        this.mByteArrayPool = byteArrayPool;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.MCWorld.framework.base.http.io.NetworkResponse performRequest(com.MCWorld.framework.base.http.io.impl.request.SegmentRequest r29) throws com.MCWorld.framework.base.http.toolbox.error.VolleyError {
        /*
        r28 = this;
        r5 = 10;
        android.os.Process.setThreadPriority(r5);
        r20 = android.os.SystemClock.elapsedRealtime();
    L_0x0009:
        r16 = 0;
        r9 = 0;
        r19 = new java.util.HashMap;
        r19.<init>();
        r4 = 0;
        r29.prepare();	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r15 = new java.util.HashMap;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r15.<init>();	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r5 = r29.getCacheEntry();	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r0 = r28;
        r0.addCacheHeaders(r15, r5);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r5 = "begin-request-%s";
        r8 = 1;
        r8 = new java.lang.Object[r8];	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r25 = 0;
        r26 = r29.toString();	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r8[r25] = r26;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r5 = java.lang.String.format(r5, r8);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r0 = r28;
        r5 = r0.mHttpStack;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r8 = 1;
        r0 = r29;
        r17 = r5.performRequest(r0, r15, r8);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r0 = r17;
        r5 = r0.first;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r0 = r5;
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r4 = r0;
        r0 = r17;
        r5 = r0.second;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r0 = r5;
        r0 = (org.apache.http.HttpResponse) r0;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r16 = r0;
        r10 = r16.getStatusLine();	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r24 = r10.getStatusCode();	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r5 = "after-request-statuscode-%d";
        r8 = 1;
        r8 = new java.lang.Object[r8];	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r25 = 0;
        r26 = java.lang.Integer.valueOf(r24);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r8[r25] = r26;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r5 = java.lang.String.format(r5, r8);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r0 = r29;
        r1 = r24;
        r0.httpStatusCode(r1);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r5 = r16.getAllHeaders();	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r19 = convertHeaders(r5);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r5 = r16.getEntity();	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        if (r5 == 0) goto L_0x0197;
    L_0x008a:
        r5 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r0 = r24;
        if (r0 < r5) goto L_0x013c;
    L_0x0090:
        r5 = 300; // 0x12c float:4.2E-43 double:1.48E-321;
        r0 = r24;
        if (r0 >= r5) goto L_0x013c;
    L_0x0096:
        r5 = r16.getEntity();	 Catch:{ ExceedLimitedCancelError -> 0x00fe, Exception -> 0x0112 }
        r0 = r28;
        r1 = r29;
        r2 = r19;
        r9 = r0.entityToBytes(r5, r1, r2);	 Catch:{ ExceedLimitedCancelError -> 0x00fe, Exception -> 0x0112 }
    L_0x00a4:
        r26 = android.os.SystemClock.elapsedRealtime();	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r6 = r26 - r20;
        r5 = "after-entity-bytes-%d";
        r8 = 1;
        r8 = new java.lang.Object[r8];	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r25 = 0;
        r26 = java.lang.Long.valueOf(r6);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r8[r25] = r26;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r5 = java.lang.String.format(r5, r8);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r5 = r28;
        r8 = r29;
        r5.logSlowRequests(r6, r8, r9, r10);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r5 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r0 = r24;
        if (r0 < r5) goto L_0x00d4;
    L_0x00ce:
        r5 = 299; // 0x12b float:4.19E-43 double:1.477E-321;
        r0 = r24;
        if (r0 <= r5) goto L_0x01a4;
    L_0x00d4:
        r5 = new java.io.IOException;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r5.<init>();	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        throw r5;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
    L_0x00da:
        r11 = move-exception;
        r5 = "create-parent-dir-error";
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ all -> 0x00f7 }
        r5 = "SegmentNetwork";
        r8 = "e %s";
        r25 = 0;
        r0 = r25;
        r0 = new java.lang.Object[r0];	 Catch:{ all -> 0x00f7 }
        r25 = r0;
        r0 = r25;
        com.huluxia.framework.base.log.HLog.error(r5, r8, r11, r0);	 Catch:{ all -> 0x00f7 }
        throw r11;	 Catch:{ all -> 0x00f7 }
    L_0x00f7:
        r5 = move-exception;
        if (r4 == 0) goto L_0x00fd;
    L_0x00fa:
        r4.disconnect();
    L_0x00fd:
        throw r5;
    L_0x00fe:
        r11 = move-exception;
        r5 = "segment-network-catch-exceed-limited-bytes";
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r5 = r29.getRecord();	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r5 = r5.name;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r9 = r5.getBytes();	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        goto L_0x00a4;
    L_0x0112:
        r11 = move-exception;
        throw r11;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
    L_0x0114:
        r11 = move-exception;
        r5 = "cancel download %s";
        r8 = 1;
        r8 = new java.lang.Object[r8];	 Catch:{ all -> 0x00f7 }
        r25 = 0;
        r8[r25] = r11;	 Catch:{ all -> 0x00f7 }
        r0 = r28;
        com.huluxia.framework.base.log.HLog.info(r0, r5, r8);	 Catch:{ all -> 0x00f7 }
        r5 = "catch-cancel-download-%s";
        r8 = 1;
        r8 = new java.lang.Object[r8];	 Catch:{ all -> 0x00f7 }
        r25 = 0;
        r26 = r11.getMessage();	 Catch:{ all -> 0x00f7 }
        r8[r25] = r26;	 Catch:{ all -> 0x00f7 }
        r5 = java.lang.String.format(r5, r8);	 Catch:{ all -> 0x00f7 }
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ all -> 0x00f7 }
        throw r11;	 Catch:{ all -> 0x00f7 }
    L_0x013c:
        r5 = 0;
        r9 = new byte[r5];	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r5 = "entity-not-read-status-%d";
        r8 = 1;
        r8 = new java.lang.Object[r8];	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r25 = 0;
        r26 = java.lang.Integer.valueOf(r24);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r8[r25] = r26;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r5 = java.lang.String.format(r5, r8);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        goto L_0x00a4;
    L_0x0158:
        r11 = move-exception;
        r5 = "socket";
        r8 = new com.huluxia.framework.base.http.toolbox.error.TimeoutError;	 Catch:{ all -> 0x00f7 }
        r25 = "socket";
        r0 = r25;
        r8.<init>(r0);	 Catch:{ all -> 0x00f7 }
        r0 = r29;
        attemptRetryOnException(r5, r0, r8);	 Catch:{ all -> 0x00f7 }
        r5 = r29.getRetryPolicy();	 Catch:{ all -> 0x00f7 }
        if (r5 == 0) goto L_0x0190;
    L_0x0171:
        r5 = "socket-timeout-retry-%d";
        r8 = 1;
        r8 = new java.lang.Object[r8];	 Catch:{ all -> 0x00f7 }
        r25 = 0;
        r26 = r29.getRetryPolicy();	 Catch:{ all -> 0x00f7 }
        r26 = r26.getCurrentRetryCount();	 Catch:{ all -> 0x00f7 }
        r26 = java.lang.Integer.valueOf(r26);	 Catch:{ all -> 0x00f7 }
        r8[r25] = r26;	 Catch:{ all -> 0x00f7 }
        r5 = java.lang.String.format(r5, r8);	 Catch:{ all -> 0x00f7 }
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ all -> 0x00f7 }
    L_0x0190:
        if (r4 == 0) goto L_0x0009;
    L_0x0192:
        r4.disconnect();
        goto L_0x0009;
    L_0x0197:
        r5 = "request-no-entity";
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r5 = 0;
        r9 = new byte[r5];	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        goto L_0x00a4;
    L_0x01a4:
        r5 = new com.huluxia.framework.base.http.io.NetworkResponse;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        r8 = 0;
        r0 = r24;
        r1 = r19;
        r5.<init>(r0, r9, r1, r8);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0114, SocketTimeoutException -> 0x0158, ConnectTimeoutException -> 0x01b4, MalformedURLException -> 0x01f3, InvalidParamError -> 0x021e, CannotResumeError -> 0x023b, CloseSocketBeforeEndError -> 0x0258, LocalFileError -> 0x0275, IOException -> 0x02a7 }
        if (r4 == 0) goto L_0x01b3;
    L_0x01b0:
        r4.disconnect();
    L_0x01b3:
        return r5;
    L_0x01b4:
        r11 = move-exception;
        r5 = "connection";
        r8 = new com.huluxia.framework.base.http.toolbox.error.TimeoutError;	 Catch:{ all -> 0x00f7 }
        r25 = "conection";
        r0 = r25;
        r8.<init>(r0);	 Catch:{ all -> 0x00f7 }
        r0 = r29;
        attemptRetryOnException(r5, r0, r8);	 Catch:{ all -> 0x00f7 }
        r5 = r29.getRetryPolicy();	 Catch:{ all -> 0x00f7 }
        if (r5 == 0) goto L_0x01ec;
    L_0x01cd:
        r5 = "connection-timeout-retry-%d";
        r8 = 1;
        r8 = new java.lang.Object[r8];	 Catch:{ all -> 0x00f7 }
        r25 = 0;
        r26 = r29.getRetryPolicy();	 Catch:{ all -> 0x00f7 }
        r26 = r26.getCurrentRetryCount();	 Catch:{ all -> 0x00f7 }
        r26 = java.lang.Integer.valueOf(r26);	 Catch:{ all -> 0x00f7 }
        r8[r25] = r26;	 Catch:{ all -> 0x00f7 }
        r5 = java.lang.String.format(r5, r8);	 Catch:{ all -> 0x00f7 }
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ all -> 0x00f7 }
    L_0x01ec:
        if (r4 == 0) goto L_0x0009;
    L_0x01ee:
        r4.disconnect();
        goto L_0x0009;
    L_0x01f3:
        r11 = move-exception;
        r5 = "malformed-url-exception";
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ all -> 0x00f7 }
        r5 = new java.lang.RuntimeException;	 Catch:{ all -> 0x00f7 }
        r8 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00f7 }
        r8.<init>();	 Catch:{ all -> 0x00f7 }
        r25 = "Bad URL ";
        r0 = r25;
        r8 = r8.append(r0);	 Catch:{ all -> 0x00f7 }
        r25 = r29.getUrl();	 Catch:{ all -> 0x00f7 }
        r0 = r25;
        r8 = r8.append(r0);	 Catch:{ all -> 0x00f7 }
        r8 = r8.toString();	 Catch:{ all -> 0x00f7 }
        r5.<init>(r8, r11);	 Catch:{ all -> 0x00f7 }
        throw r5;	 Catch:{ all -> 0x00f7 }
    L_0x021e:
        r11 = move-exception;
        r5 = "invalid-param-error";
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ all -> 0x00f7 }
        r5 = "SegmentNetwork";
        r8 = "invalid param e %s";
        r25 = 0;
        r0 = r25;
        r0 = new java.lang.Object[r0];	 Catch:{ all -> 0x00f7 }
        r25 = r0;
        r0 = r25;
        com.huluxia.framework.base.log.HLog.error(r5, r8, r11, r0);	 Catch:{ all -> 0x00f7 }
        throw r11;	 Catch:{ all -> 0x00f7 }
    L_0x023b:
        r11 = move-exception;
        r5 = "cannot-resume-error";
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ all -> 0x00f7 }
        r5 = "SegmentNetwork";
        r8 = "e %s";
        r25 = 0;
        r0 = r25;
        r0 = new java.lang.Object[r0];	 Catch:{ all -> 0x00f7 }
        r25 = r0;
        r0 = r25;
        com.huluxia.framework.base.log.HLog.error(r5, r8, r11, r0);	 Catch:{ all -> 0x00f7 }
        throw r11;	 Catch:{ all -> 0x00f7 }
    L_0x0258:
        r11 = move-exception;
        r5 = "close-socket-before-end-error";
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ all -> 0x00f7 }
        r5 = "SegmentNetwork";
        r8 = "e %s";
        r25 = 0;
        r0 = r25;
        r0 = new java.lang.Object[r0];	 Catch:{ all -> 0x00f7 }
        r25 = r0;
        r0 = r25;
        com.huluxia.framework.base.log.HLog.error(r5, r8, r11, r0);	 Catch:{ all -> 0x00f7 }
        throw r11;	 Catch:{ all -> 0x00f7 }
    L_0x0275:
        r11 = move-exception;
        r5 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00f7 }
        r5.<init>();	 Catch:{ all -> 0x00f7 }
        r8 = "local-file-err-";
        r5 = r5.append(r8);	 Catch:{ all -> 0x00f7 }
        r8 = r11.getMessage();	 Catch:{ all -> 0x00f7 }
        r5 = r5.append(r8);	 Catch:{ all -> 0x00f7 }
        r5 = r5.toString();	 Catch:{ all -> 0x00f7 }
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ all -> 0x00f7 }
        r5 = "SegmentNetwork";
        r8 = "e %s";
        r25 = 0;
        r0 = r25;
        r0 = new java.lang.Object[r0];	 Catch:{ all -> 0x00f7 }
        r25 = r0;
        r0 = r25;
        com.huluxia.framework.base.log.HLog.error(r5, r8, r11, r0);	 Catch:{ all -> 0x00f7 }
        throw r11;	 Catch:{ all -> 0x00f7 }
    L_0x02a7:
        r11 = move-exception;
        r5 = "request-perform-io-error-%s";
        r8 = 1;
        r8 = new java.lang.Object[r8];	 Catch:{ all -> 0x00f7 }
        r25 = 0;
        r8[r25] = r11;	 Catch:{ all -> 0x00f7 }
        r5 = java.lang.String.format(r5, r8);	 Catch:{ all -> 0x00f7 }
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ all -> 0x00f7 }
        r5 = "ETag";
        r0 = r19;
        r5 = r0.get(r5);	 Catch:{ all -> 0x00f7 }
        r5 = (java.lang.String) r5;	 Catch:{ all -> 0x00f7 }
        r0 = r28;
        r1 = r29;
        r5 = r0.cannotResume(r1, r5);	 Catch:{ all -> 0x00f7 }
        if (r5 == 0) goto L_0x0311;
    L_0x02d0:
        r12 = new com.huluxia.framework.base.http.toolbox.error.CannotResumeError;	 Catch:{ all -> 0x00f7 }
        r5 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00f7 }
        r5.<init>();	 Catch:{ all -> 0x00f7 }
        r8 = "Failed reading response: ex ";
        r5 = r5.append(r8);	 Catch:{ all -> 0x00f7 }
        r5 = r5.append(r11);	 Catch:{ all -> 0x00f7 }
        r8 = " unable to resume";
        r5 = r5.append(r8);	 Catch:{ all -> 0x00f7 }
        r5 = r5.toString();	 Catch:{ all -> 0x00f7 }
        r12.<init>(r5);	 Catch:{ all -> 0x00f7 }
        r5 = 30;
        r12.setError(r5);	 Catch:{ all -> 0x00f7 }
        r5 = "SegmentNetwork";
        r8 = "download io ex ,cannot reusme record %s";
        r25 = 1;
        r0 = r25;
        r0 = new java.lang.Object[r0];	 Catch:{ all -> 0x00f7 }
        r25 = r0;
        r26 = 0;
        r27 = r29.getRecord();	 Catch:{ all -> 0x00f7 }
        r25[r26] = r27;	 Catch:{ all -> 0x00f7 }
        r0 = r25;
        com.huluxia.framework.base.log.HLog.error(r5, r8, r0);	 Catch:{ all -> 0x00f7 }
        throw r12;	 Catch:{ all -> 0x00f7 }
    L_0x0311:
        r14 = r28.getFilePath(r29);	 Catch:{ all -> 0x00f7 }
        r13 = new java.io.File;	 Catch:{ all -> 0x00f7 }
        r13.<init>(r14);	 Catch:{ all -> 0x00f7 }
        r5 = r13.getParent();	 Catch:{ all -> 0x00f7 }
        r22 = com.huluxia.framework.base.utils.UtilsFile.availableSpace(r5);	 Catch:{ all -> 0x00f7 }
        r5 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00f7 }
        r5.<init>();	 Catch:{ all -> 0x00f7 }
        r8 = "downloadfile filepath(%s) size(%d) b(%d) error = ";
        r5 = r5.append(r8);	 Catch:{ all -> 0x00f7 }
        r8 = r11.getMessage();	 Catch:{ all -> 0x00f7 }
        r5 = r5.append(r8);	 Catch:{ all -> 0x00f7 }
        r8 = r5.toString();	 Catch:{ all -> 0x00f7 }
        r5 = 3;
        r0 = new java.lang.Object[r5];	 Catch:{ all -> 0x00f7 }
        r25 = r0;
        r5 = 0;
        r25[r5] = r14;	 Catch:{ all -> 0x00f7 }
        r5 = 1;
        r26 = java.lang.Long.valueOf(r22);	 Catch:{ all -> 0x00f7 }
        r25[r5] = r26;	 Catch:{ all -> 0x00f7 }
        r26 = 2;
        r5 = r13.exists();	 Catch:{ all -> 0x00f7 }
        if (r5 == 0) goto L_0x037e;
    L_0x0351:
        r5 = 1;
    L_0x0352:
        r5 = java.lang.Integer.valueOf(r5);	 Catch:{ all -> 0x00f7 }
        r25[r26] = r5;	 Catch:{ all -> 0x00f7 }
        r0 = r28;
        r1 = r25;
        com.huluxia.framework.base.log.HLog.error(r0, r8, r1);	 Catch:{ all -> 0x00f7 }
        r26 = 1048576; // 0x100000 float:1.469368E-39 double:5.180654E-318;
        r5 = (r22 > r26 ? 1 : (r22 == r26 ? 0 : -1));
        if (r5 >= 0) goto L_0x0380;
    L_0x0366:
        r5 = "request-no-avaiable-space";
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ all -> 0x00f7 }
        r5 = new com.huluxia.framework.base.http.toolbox.error.NoAvailbleSpaceError;	 Catch:{ all -> 0x00f7 }
        r8 = new java.lang.Throwable;	 Catch:{ all -> 0x00f7 }
        r25 = "no available left in partition";
        r0 = r25;
        r8.<init>(r0);	 Catch:{ all -> 0x00f7 }
        r5.<init>(r8);	 Catch:{ all -> 0x00f7 }
        throw r5;	 Catch:{ all -> 0x00f7 }
    L_0x037e:
        r5 = 0;
        goto L_0x0352;
    L_0x0380:
        r24 = 0;
        r18 = 0;
        if (r16 == 0) goto L_0x03dd;
    L_0x0386:
        r5 = r16.getStatusLine();	 Catch:{ all -> 0x00f7 }
        r24 = r5.getStatusCode();	 Catch:{ all -> 0x00f7 }
        r5 = "unexpected-response-code-%d-for-%s";
        r8 = 2;
        r8 = new java.lang.Object[r8];	 Catch:{ all -> 0x00f7 }
        r25 = 0;
        r26 = java.lang.Integer.valueOf(r24);	 Catch:{ all -> 0x00f7 }
        r8[r25] = r26;	 Catch:{ all -> 0x00f7 }
        r25 = 1;
        r26 = r29.getUrl();	 Catch:{ all -> 0x00f7 }
        r8[r25] = r26;	 Catch:{ all -> 0x00f7 }
        r5 = java.lang.String.format(r5, r8);	 Catch:{ all -> 0x00f7 }
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ all -> 0x00f7 }
        if (r9 == 0) goto L_0x042b;
    L_0x03af:
        r18 = new com.huluxia.framework.base.http.io.NetworkResponse;	 Catch:{ all -> 0x00f7 }
        r5 = 0;
        r0 = r18;
        r1 = r24;
        r2 = r19;
        r0.<init>(r1, r9, r2, r5);	 Catch:{ all -> 0x00f7 }
        r5 = 401; // 0x191 float:5.62E-43 double:1.98E-321;
        r0 = r24;
        if (r0 == r5) goto L_0x03c7;
    L_0x03c1:
        r5 = 403; // 0x193 float:5.65E-43 double:1.99E-321;
        r0 = r24;
        if (r0 != r5) goto L_0x03e3;
    L_0x03c7:
        r5 = "auth";
        r8 = new com.huluxia.framework.base.http.toolbox.error.AuthFailureError;	 Catch:{ all -> 0x00f7 }
        r0 = r18;
        r8.<init>(r0);	 Catch:{ all -> 0x00f7 }
        r0 = r29;
        attemptRetryOnException(r5, r0, r8);	 Catch:{ all -> 0x00f7 }
        if (r4 == 0) goto L_0x0009;
    L_0x03d8:
        r4.disconnect();
        goto L_0x0009;
    L_0x03dd:
        r5 = new com.huluxia.framework.base.http.toolbox.error.NoConnectionError;	 Catch:{ all -> 0x00f7 }
        r5.<init>(r11);	 Catch:{ all -> 0x00f7 }
        throw r5;	 Catch:{ all -> 0x00f7 }
    L_0x03e3:
        r5 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        r0 = r24;
        if (r0 < r5) goto L_0x03ff;
    L_0x03e9:
        r5 = 599; // 0x257 float:8.4E-43 double:2.96E-321;
        r0 = r24;
        if (r0 > r5) goto L_0x03ff;
    L_0x03ef:
        r5 = "request-server-error";
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ all -> 0x00f7 }
        r5 = new com.huluxia.framework.base.http.toolbox.error.ServerError;	 Catch:{ all -> 0x00f7 }
        r0 = r18;
        r5.<init>(r0);	 Catch:{ all -> 0x00f7 }
        throw r5;	 Catch:{ all -> 0x00f7 }
    L_0x03ff:
        r5 = 400; // 0x190 float:5.6E-43 double:1.976E-321;
        r0 = r24;
        if (r0 < r5) goto L_0x041b;
    L_0x0405:
        r5 = 499; // 0x1f3 float:6.99E-43 double:2.465E-321;
        r0 = r24;
        if (r0 > r5) goto L_0x041b;
    L_0x040b:
        r5 = "request-client-error";
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ all -> 0x00f7 }
        r5 = new com.huluxia.framework.base.http.toolbox.error.ClientError;	 Catch:{ all -> 0x00f7 }
        r0 = r18;
        r5.<init>(r0);	 Catch:{ all -> 0x00f7 }
        throw r5;	 Catch:{ all -> 0x00f7 }
    L_0x041b:
        r5 = "request-unknown-error";
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ all -> 0x00f7 }
        r5 = new com.huluxia.framework.base.http.toolbox.error.UnknownError;	 Catch:{ all -> 0x00f7 }
        r0 = r18;
        r5.<init>(r0);	 Catch:{ all -> 0x00f7 }
        throw r5;	 Catch:{ all -> 0x00f7 }
    L_0x042b:
        r5 = "request-network-error";
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ all -> 0x00f7 }
        r5 = new com.huluxia.framework.base.http.toolbox.error.NetworkError;	 Catch:{ all -> 0x00f7 }
        r0 = r18;
        r5.<init>(r0);	 Catch:{ all -> 0x00f7 }
        throw r5;	 Catch:{ all -> 0x00f7 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.framework.base.http.transport.SegmentNetwork.performRequest(com.huluxia.framework.base.http.io.impl.request.SegmentRequest):com.huluxia.framework.base.http.io.NetworkResponse");
    }

    protected byte[] entityToBytes(HttpEntity entity, SegmentRequest request, Map<String, String> responseHeaders) throws IOException, VolleyError {
        return downloadResume(entity, request, new File(getFilePath(request)), responseHeaders);
    }

    protected void logSlowRequests(long requestLifetime, SegmentRequest request, byte[] responseContents, StatusLine statusLine) {
        if (requestLifetime > ((long) SLOW_REQUEST_THRESHOLD_MS)) {
            String str = "HTTP response for download request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]";
            Object[] objArr = new Object[5];
            objArr[0] = request;
            objArr[1] = Long.valueOf(requestLifetime);
            objArr[2] = responseContents != null ? Integer.valueOf(responseContents.length) : "null";
            objArr[3] = Integer.valueOf(statusLine.getStatusCode());
            objArr[4] = Integer.valueOf(request.getRetryPolicy().getCurrentRetryCount());
            HttpLog.d(str, objArr);
        }
    }

    protected static void attemptRetryOnException(String logPrefix, SegmentRequest request, VolleyError exception) throws VolleyError {
        HLog.error(TAG, "attemptRetryOnException ex %s", exception, new Object[0]);
        RetryPolicy retryPolicy = request.getRetryPolicy();
        int oldTimeout = request.getTimeoutMs();
        if (request.isCanceled()) {
            request.addMarker("cancel-retry");
            throw new CancelError(String.format("cancel retry e %s", new Object[]{exception}));
        }
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

    private boolean cannotResume(SegmentRequest request, String etag) {
        return request.getRecord().progress > 0 && etag == null && !request.getRecord().noIntegrity;
    }

    protected void logError(String what, String url, long start) {
        long now = SystemClock.elapsedRealtime();
        HttpLog.v("HTTP ERROR(%s) %d ms to fetch %s", what, Long.valueOf(now - start), url);
    }

    protected static Map<String, String> convertHeaders(Header[] headers) {
        Map<String, String> result = new HashMap();
        for (int i = 0; i < headers.length; i++) {
            result.put(headers[i].getName(), headers[i].getValue());
        }
        return result;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private byte[] downloadResume(org.apache.http.HttpEntity r26, com.MCWorld.framework.base.http.io.impl.request.SegmentRequest r27, java.io.File r28, java.util.Map<java.lang.String, java.lang.String> r29) throws java.io.IOException, com.MCWorld.framework.base.http.toolbox.error.VolleyError {
        /*
        r25 = this;
        r2 = "Transfer-Encoding";
        r0 = r29;
        r24 = r0.get(r2);
        r24 = (java.lang.String) r24;
        r10 = -1;
        r2 = com.huluxia.framework.base.utils.UtilsFunction.empty(r24);
        if (r2 == 0) goto L_0x0043;
    L_0x0013:
        r2 = "Content-Length";
        r0 = r29;
        r2 = r0.containsKey(r2);
        if (r2 == 0) goto L_0x006b;
    L_0x001e:
        r2 = "Content-Length";
        r0 = r29;
        r2 = r0.get(r2);
        r2 = (java.lang.String) r2;
        r10 = java.lang.Long.parseLong(r2);
    L_0x002d:
        r2 = "download-resume-content-length-%d";
        r3 = 1;
        r3 = new java.lang.Object[r3];
        r4 = 0;
        r5 = java.lang.Long.valueOf(r10);
        r3[r4] = r5;
        r2 = java.lang.String.format(r2, r3);
        r0 = r27;
        r0.addMarker(r2);
    L_0x0043:
        r2 = -1;
        r2 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1));
        if (r2 != 0) goto L_0x006e;
    L_0x0049:
        if (r24 == 0) goto L_0x0056;
    L_0x004b:
        r2 = "chunked";
        r0 = r24;
        r2 = r0.equals(r2);
        if (r2 != 0) goto L_0x006e;
    L_0x0056:
        r16 = 1;
    L_0x0058:
        if (r16 == 0) goto L_0x0071;
    L_0x005a:
        r2 = "no-size-info";
        r0 = r27;
        r0.addMarker(r2);
        r2 = new com.huluxia.framework.base.http.toolbox.error.UnknownSizeError;
        r3 = "can not know size of download, give up";
        r2.<init>(r3);
        throw r2;
    L_0x006b:
        r10 = -1;
        goto L_0x002d;
    L_0x006e:
        r16 = 0;
        goto L_0x0058;
    L_0x0071:
        r6 = r26.getContentLength();
        r2 = r28.exists();	 Catch:{ IOException -> 0x0105 }
        if (r2 != 0) goto L_0x00f8;
    L_0x007b:
        r2 = "SegmentNetwork";
        r3 = "file not exists, redirect to download new file";
        r4 = 0;
        r4 = new java.lang.Object[r4];	 Catch:{ IOException -> 0x0105 }
        com.huluxia.framework.base.log.HLog.warn(r2, r3, r4);	 Catch:{ IOException -> 0x0105 }
        r28.createNewFile();	 Catch:{ IOException -> 0x0105 }
        r17 = new com.huluxia.framework.base.http.toolbox.reader.RandomAccessFileAdapter;	 Catch:{ IOException -> 0x0105 }
        r2 = "rw";
        r0 = r17;
        r1 = r28;
        r0.<init>(r1, r2);	 Catch:{ IOException -> 0x0105 }
        r0 = r17;
        r0.setLength(r6);	 Catch:{ IOException -> 0x0105 }
    L_0x009b:
        r14 = 0;
        r12 = 0;
        r2 = r26.getContentEncoding();	 Catch:{ CancelError -> 0x00e7 }
        if (r2 == 0) goto L_0x010c;
    L_0x00a3:
        r2 = "gzip";
        r3 = r26.getContentEncoding();	 Catch:{ CancelError -> 0x00e7 }
        r3 = r3.getValue();	 Catch:{ CancelError -> 0x00e7 }
        r2 = r2.equals(r3);	 Catch:{ CancelError -> 0x00e7 }
        if (r2 == 0) goto L_0x010c;
    L_0x00b4:
        r2 = "SegmentNetwork";
        r3 = new java.lang.StringBuilder;	 Catch:{ CancelError -> 0x00e7 }
        r3.<init>();	 Catch:{ CancelError -> 0x00e7 }
        r4 = "segment network gzip ";
        r3 = r3.append(r4);	 Catch:{ CancelError -> 0x00e7 }
        r4 = r27.getUrl();	 Catch:{ CancelError -> 0x00e7 }
        r3 = r3.append(r4);	 Catch:{ CancelError -> 0x00e7 }
        r3 = r3.toString();	 Catch:{ CancelError -> 0x00e7 }
        r4 = 0;
        r4 = new java.lang.Object[r4];	 Catch:{ CancelError -> 0x00e7 }
        com.huluxia.framework.base.log.HLog.debug(r2, r3, r4);	 Catch:{ CancelError -> 0x00e7 }
        r15 = new java.util.zip.GZIPInputStream;	 Catch:{ CancelError -> 0x00e7 }
        r2 = r26.getContent();	 Catch:{ CancelError -> 0x00e7 }
        r15.<init>(r2);	 Catch:{ CancelError -> 0x00e7 }
        r14 = r15;
    L_0x00df:
        if (r14 != 0) goto L_0x0111;
    L_0x00e1:
        r2 = new com.huluxia.framework.base.http.toolbox.error.ServerError;	 Catch:{ CancelError -> 0x00e7 }
        r2.<init>();	 Catch:{ CancelError -> 0x00e7 }
        throw r2;	 Catch:{ CancelError -> 0x00e7 }
    L_0x00e7:
        r13 = move-exception;
        r2 = "download-resume-catch";
        r0 = r27;
        r0.addMarker(r2);	 Catch:{ all -> 0x00f1 }
        throw r13;	 Catch:{ all -> 0x00f1 }
    L_0x00f1:
        r2 = move-exception;
        if (r12 == 0) goto L_0x00f7;
    L_0x00f4:
        r12.close();
    L_0x00f7:
        throw r2;
    L_0x00f8:
        r17 = new com.huluxia.framework.base.http.toolbox.reader.RandomAccessFileAdapter;	 Catch:{ IOException -> 0x0105 }
        r2 = "rw";
        r0 = r17;
        r1 = r28;
        r0.<init>(r1, r2);	 Catch:{ IOException -> 0x0105 }
        goto L_0x009b;
    L_0x0105:
        r13 = move-exception;
        r2 = new com.huluxia.framework.base.http.toolbox.error.LocalFileError;
        r2.<init>(r13);
        throw r2;
    L_0x010c:
        r14 = r26.getContent();	 Catch:{ CancelError -> 0x00e7 }
        goto L_0x00df;
    L_0x0111:
        r22 = 0;
        r2 = "Content-Range";
        r0 = r29;
        r20 = r0.get(r2);	 Catch:{ CancelError -> 0x00e7 }
        r20 = (java.lang.String) r20;	 Catch:{ CancelError -> 0x00e7 }
        r2 = "bytes ";
        r8 = r2.length();	 Catch:{ CancelError -> 0x00e7 }
        r2 = r20.length();	 Catch:{ CancelError -> 0x00e7 }
        if (r8 <= r2) goto L_0x0139;
    L_0x012b:
        r2 = "resume-range-invalid";
        r0 = r27;
        r0.addMarker(r2);	 Catch:{ CancelError -> 0x00e7 }
        r2 = new com.huluxia.framework.base.http.toolbox.error.ServerError;	 Catch:{ CancelError -> 0x00e7 }
        r2.<init>();	 Catch:{ CancelError -> 0x00e7 }
        throw r2;	 Catch:{ CancelError -> 0x00e7 }
    L_0x0139:
        r2 = r20.length();	 Catch:{ CancelError -> 0x00e7 }
        r0 = r20;
        r20 = r0.substring(r8, r2);	 Catch:{ CancelError -> 0x00e7 }
        r2 = "-";
        r0 = r20;
        r2 = r0.contains(r2);	 Catch:{ CancelError -> 0x00e7 }
        if (r2 == 0) goto L_0x015e;
    L_0x014e:
        r2 = "-";
        r0 = r20;
        r2 = r0.split(r2);	 Catch:{ CancelError -> 0x00e7 }
        r3 = 0;
        r9 = r2[r3];	 Catch:{ CancelError -> 0x00e7 }
        r22 = java.lang.Long.parseLong(r9);	 Catch:{ NumberFormatException -> 0x01c4 }
    L_0x015e:
        r2 = "download-resume-%d-file-%s";
        r3 = 2;
        r3 = new java.lang.Object[r3];	 Catch:{ CancelError -> 0x00e7 }
        r4 = 0;
        r5 = java.lang.Long.valueOf(r22);	 Catch:{ CancelError -> 0x00e7 }
        r3[r4] = r5;	 Catch:{ CancelError -> 0x00e7 }
        r4 = 1;
        r5 = r28.getAbsolutePath();	 Catch:{ CancelError -> 0x00e7 }
        r3[r4] = r5;	 Catch:{ CancelError -> 0x00e7 }
        r2 = java.lang.String.format(r2, r3);	 Catch:{ CancelError -> 0x00e7 }
        r0 = r27;
        r0.addMarker(r2);	 Catch:{ CancelError -> 0x00e7 }
        r18 = r22;
        r17.seek(r18);	 Catch:{ IOException -> 0x01e4 }
        r21 = r27.getReaderType();	 Catch:{ CancelError -> 0x00e7 }
        r0 = r25;
        r2 = r0.mByteArrayPool;	 Catch:{ CancelError -> 0x00e7 }
        r0 = r21;
        r12 = com.huluxia.framework.base.http.toolbox.reader.ReaderFactory.getReader(r2, r0);	 Catch:{ CancelError -> 0x00e7 }
        r2 = r12 instanceof com.huluxia.framework.base.http.toolbox.reader.XorEnhancedReader;	 Catch:{ CancelError -> 0x00e7 }
        if (r2 == 0) goto L_0x01eb;
    L_0x0192:
        r0 = r12;
        r0 = (com.huluxia.framework.base.http.toolbox.reader.XorEnhancedReader) r0;	 Catch:{ CancelError -> 0x00e7 }
        r2 = r0;
        r0 = r18;
        r2.setStartLength(r0);	 Catch:{ CancelError -> 0x00e7 }
    L_0x019b:
        r2 = android.os.SystemClock.elapsedRealtime();	 Catch:{ CancelError -> 0x00e7 }
        r0 = r27;
        r0.setSpeedSampleStart(r2);	 Catch:{ CancelError -> 0x00e7 }
        r2 = new com.huluxia.framework.base.http.transport.SegmentNetwork$1;	 Catch:{ CancelError -> 0x00e7 }
        r3 = r25;
        r4 = r27;
        r5 = r29;
        r2.<init>(r4, r5, r6);	 Catch:{ CancelError -> 0x00e7 }
        r0 = r17;
        r12.copy(r14, r0, r2);	 Catch:{ CancelError -> 0x00e7 }
        r2 = r27.getRecord();	 Catch:{ CancelError -> 0x00e7 }
        r2 = r2.name;	 Catch:{ CancelError -> 0x00e7 }
        r2 = r2.getBytes();	 Catch:{ CancelError -> 0x00e7 }
        if (r12 == 0) goto L_0x01c3;
    L_0x01c0:
        r12.close();
    L_0x01c3:
        return r2;
    L_0x01c4:
        r13 = move-exception;
        r2 = "SegmentNetwork";
        r3 = new java.lang.StringBuilder;	 Catch:{ CancelError -> 0x00e7 }
        r3.<init>();	 Catch:{ CancelError -> 0x00e7 }
        r4 = "downloadResume exception = ";
        r3 = r3.append(r4);	 Catch:{ CancelError -> 0x00e7 }
        r3 = r3.append(r13);	 Catch:{ CancelError -> 0x00e7 }
        r3 = r3.toString();	 Catch:{ CancelError -> 0x00e7 }
        r4 = 0;
        r4 = new java.lang.Object[r4];	 Catch:{ CancelError -> 0x00e7 }
        com.huluxia.framework.base.log.HLog.error(r2, r3, r4);	 Catch:{ CancelError -> 0x00e7 }
        goto L_0x015e;
    L_0x01e4:
        r13 = move-exception;
        r2 = new com.huluxia.framework.base.http.toolbox.error.LocalFileError;	 Catch:{ CancelError -> 0x00e7 }
        r2.<init>(r13);	 Catch:{ CancelError -> 0x00e7 }
        throw r2;	 Catch:{ CancelError -> 0x00e7 }
    L_0x01eb:
        r2 = r12 instanceof com.huluxia.framework.base.http.toolbox.reader.XorEnhancedLimitBandwidthReader;	 Catch:{ CancelError -> 0x00e7 }
        if (r2 == 0) goto L_0x0202;
    L_0x01ef:
        r0 = r12;
        r0 = (com.huluxia.framework.base.http.toolbox.reader.XorEnhancedLimitBandwidthReader) r0;	 Catch:{ CancelError -> 0x00e7 }
        r2 = r0;
        r0 = r27;
        r2.setRequest(r0);	 Catch:{ CancelError -> 0x00e7 }
        r0 = r12;
        r0 = (com.huluxia.framework.base.http.toolbox.reader.XorEnhancedLimitBandwidthReader) r0;	 Catch:{ CancelError -> 0x00e7 }
        r2 = r0;
        r0 = r18;
        r2.setStartLength(r0);	 Catch:{ CancelError -> 0x00e7 }
        goto L_0x019b;
    L_0x0202:
        r2 = r12 instanceof com.huluxia.framework.base.http.toolbox.reader.BandwidthLimitReader;	 Catch:{ CancelError -> 0x00e7 }
        if (r2 == 0) goto L_0x019b;
    L_0x0206:
        r0 = r12;
        r0 = (com.huluxia.framework.base.http.toolbox.reader.BandwidthLimitReader) r0;	 Catch:{ CancelError -> 0x00e7 }
        r2 = r0;
        r0 = r27;
        r2.setRequest(r0);	 Catch:{ CancelError -> 0x00e7 }
        goto L_0x019b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.framework.base.http.transport.SegmentNetwork.downloadResume(org.apache.http.HttpEntity, com.huluxia.framework.base.http.io.impl.request.SegmentRequest, java.io.File, java.util.Map):byte[]");
    }

    private void handleEndOfStream(SegmentRequest request, Map<String, String> responseHeaders) throws CannotResumeError, CloseSocketBeforeEndError {
        boolean lengthMismatched;
        DownloadRecord record = request.getRecord();
        if (request.getBytesSoFar() != record.total) {
            lengthMismatched = true;
        } else {
            lengthMismatched = false;
        }
        HLog.info(TAG, "handleEndOfStream length currentbyte %d mismatched %b, record %s", new Object[]{Long.valueOf(request.getBytesSoFar()), Boolean.valueOf(lengthMismatched), record});
        if (!lengthMismatched) {
            return;
        }
        if (cannotResume(request, (String) responseHeaders.get("ETag"))) {
            HLog.error(TAG, "handleEndOfStream ,cannot reusme record %s", new Object[]{request.getRecord()});
            request.addMarker("mismatched-content-length");
            throw new CannotResumeError("mismatched content length; unable to resume");
        } else if (request.getBytesSoFar() < record.total) {
            HLog.error(TAG, "handleEndOfStream close socket before end ,cannot reusme record %s", new Object[]{request.getRecord()});
            throw new CloseSocketBeforeEndError("closed socket before end of file");
        } else {
            request.addMarker(String.format("mismatched-content-length-larger-than-total-%d-%d", new Object[]{Long.valueOf(request.getBytesSoFar()), Long.valueOf(record.total)}));
            throw new CannotResumeError("mismatched current bytes larger than total");
        }
    }

    private String getFilePath(SegmentRequest request) {
        return new File(request.getRecord().dir, request.getRecord().name).getAbsolutePath();
    }
}
