package com.huluxia.framework.base.http.transport;

import android.os.SystemClock;
import com.huluxia.framework.base.http.datasource.cache.Cache.Entry;
import com.huluxia.framework.base.http.io.impl.request.DownloadRequest;
import com.huluxia.framework.base.http.toolbox.HttpLog;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord;
import com.huluxia.framework.base.http.toolbox.error.CancelError;
import com.huluxia.framework.base.http.toolbox.error.CannotResumeError;
import com.huluxia.framework.base.http.toolbox.error.CloseSocketBeforeEndError;
import com.huluxia.framework.base.http.toolbox.error.LocalFileError;
import com.huluxia.framework.base.http.toolbox.error.ServerError;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.http.toolbox.reader.BandwidthLimitReader;
import com.huluxia.framework.base.http.toolbox.reader.BufferOutputStreamAdapter;
import com.huluxia.framework.base.http.toolbox.reader.DownloadReader;
import com.huluxia.framework.base.http.toolbox.reader.IReaderCallback;
import com.huluxia.framework.base.http.toolbox.reader.ReaderFactory;
import com.huluxia.framework.base.http.toolbox.reader.XorEnhancedReader;
import com.huluxia.framework.base.http.toolbox.retrypolicy.RetryPolicy;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.ByteArrayPool;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.impl.cookie.DateUtils;

public class DownloadNetwork implements Network<DownloadRequest> {
    private static int DEFAULT_POOL_SIZE = 8192;
    private static int SLOW_REQUEST_THRESHOLD_MS = 3000;
    private static final String TAG = "DownloadNetwork";
    private final ByteArrayPool mByteArrayPool;
    private final HttpStack mHttpStack;

    public DownloadNetwork(HttpStack httpStack) {
        this(httpStack, null);
    }

    public DownloadNetwork(HttpStack httpStack, ByteArrayPool byteArrayPool) {
        this.mHttpStack = httpStack;
        if (byteArrayPool == null) {
            byteArrayPool = new ByteArrayPool(DEFAULT_POOL_SIZE);
        }
        this.mByteArrayPool = byteArrayPool;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.huluxia.framework.base.http.io.NetworkResponse performRequest(com.huluxia.framework.base.http.io.impl.request.DownloadRequest r29) throws com.huluxia.framework.base.http.toolbox.error.VolleyError {
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
        r29.prepare();	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r15 = new java.util.HashMap;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r15.<init>();	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r5 = r29.getCacheEntry();	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r0 = r28;
        r0.addCacheHeaders(r15, r5);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r5 = "begin-request-%s";
        r8 = 1;
        r8 = new java.lang.Object[r8];	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r25 = 0;
        r26 = r29.toString();	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r8[r25] = r26;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r5 = java.lang.String.format(r5, r8);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r0 = r28;
        r5 = r0.mHttpStack;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r8 = 1;
        r0 = r29;
        r17 = r5.performRequest(r0, r15, r8);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r0 = r17;
        r5 = r0.first;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r0 = r5;
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r4 = r0;
        r0 = r17;
        r5 = r0.second;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r0 = r5;
        r0 = (org.apache.http.HttpResponse) r0;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r16 = r0;
        r10 = r16.getStatusLine();	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r24 = r10.getStatusCode();	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r5 = "after-request-statuscode-%d";
        r8 = 1;
        r8 = new java.lang.Object[r8];	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r25 = 0;
        r26 = java.lang.Integer.valueOf(r24);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r8[r25] = r26;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r5 = java.lang.String.format(r5, r8);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r0 = r29;
        r1 = r24;
        r0.httpStatusCode(r1);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r5 = r16.getAllHeaders();	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r19 = convertHeaders(r5);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r5 = r16.getEntity();	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        if (r5 == 0) goto L_0x0141;
    L_0x008a:
        r5 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r0 = r24;
        if (r0 < r5) goto L_0x00fe;
    L_0x0090:
        r5 = 300; // 0x12c float:4.2E-43 double:1.48E-321;
        r0 = r24;
        if (r0 >= r5) goto L_0x00fe;
    L_0x0096:
        r5 = r16.getEntity();	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r0 = r28;
        r1 = r29;
        r2 = r19;
        r9 = r0.entityToBytes(r5, r1, r2);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
    L_0x00a4:
        r26 = android.os.SystemClock.elapsedRealtime();	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r6 = r26 - r20;
        r5 = "after-entity-bytes-%d";
        r8 = 1;
        r8 = new java.lang.Object[r8];	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r25 = 0;
        r26 = java.lang.Long.valueOf(r6);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r8[r25] = r26;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r5 = java.lang.String.format(r5, r8);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r5 = r28;
        r8 = r29;
        r5.logSlowRequests(r6, r8, r9, r10);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r5 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r0 = r24;
        if (r0 < r5) goto L_0x00d4;
    L_0x00ce:
        r5 = 299; // 0x12b float:4.19E-43 double:1.477E-321;
        r0 = r24;
        if (r0 <= r5) goto L_0x014e;
    L_0x00d4:
        r5 = new java.io.IOException;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r5.<init>();	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        throw r5;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
    L_0x00da:
        r11 = move-exception;
        r5 = "create-parent-dir-error";
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ all -> 0x00f7 }
        r5 = "DownloadNetwork";
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
        r5 = 0;
        r9 = new byte[r5];	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r5 = "entity-not-read-status-%d";
        r8 = 1;
        r8 = new java.lang.Object[r8];	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r25 = 0;
        r26 = java.lang.Integer.valueOf(r24);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r8[r25] = r26;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r5 = java.lang.String.format(r5, r8);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        goto L_0x00a4;
    L_0x0119:
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
    L_0x0141:
        r5 = "request-no-entity";
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r5 = 0;
        r9 = new byte[r5];	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        goto L_0x00a4;
    L_0x014e:
        r5 = new com.huluxia.framework.base.http.io.NetworkResponse;	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        r8 = 0;
        r0 = r24;
        r1 = r19;
        r5.<init>(r0, r9, r1, r8);	 Catch:{ CreateDirectoryError -> 0x00da, CancelError -> 0x0119, SocketTimeoutException -> 0x015e, ConnectTimeoutException -> 0x019d, MalformedURLException -> 0x01dc, InvalidParamError -> 0x0207, CannotResumeError -> 0x0224, CloseSocketBeforeEndError -> 0x0241, LocalFileError -> 0x025e, IOException -> 0x0290 }
        if (r4 == 0) goto L_0x015d;
    L_0x015a:
        r4.disconnect();
    L_0x015d:
        return r5;
    L_0x015e:
        r11 = move-exception;
        r5 = "socket";
        r8 = new com.huluxia.framework.base.http.toolbox.error.TimeoutError;	 Catch:{ all -> 0x00f7 }
        r25 = "socket";
        r0 = r25;
        r8.<init>(r0);	 Catch:{ all -> 0x00f7 }
        r0 = r29;
        attemptRetryOnException(r5, r0, r8);	 Catch:{ all -> 0x00f7 }
        r5 = r29.getRetryPolicy();	 Catch:{ all -> 0x00f7 }
        if (r5 == 0) goto L_0x0196;
    L_0x0177:
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
    L_0x0196:
        if (r4 == 0) goto L_0x0009;
    L_0x0198:
        r4.disconnect();
        goto L_0x0009;
    L_0x019d:
        r11 = move-exception;
        r5 = "connection";
        r8 = new com.huluxia.framework.base.http.toolbox.error.TimeoutError;	 Catch:{ all -> 0x00f7 }
        r25 = "conection";
        r0 = r25;
        r8.<init>(r0);	 Catch:{ all -> 0x00f7 }
        r0 = r29;
        attemptRetryOnException(r5, r0, r8);	 Catch:{ all -> 0x00f7 }
        r5 = r29.getRetryPolicy();	 Catch:{ all -> 0x00f7 }
        if (r5 == 0) goto L_0x01d5;
    L_0x01b6:
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
    L_0x01d5:
        if (r4 == 0) goto L_0x0009;
    L_0x01d7:
        r4.disconnect();
        goto L_0x0009;
    L_0x01dc:
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
    L_0x0207:
        r11 = move-exception;
        r5 = "invalid-param-error";
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ all -> 0x00f7 }
        r5 = "DownloadNetwork";
        r8 = "invalid param e %s";
        r25 = 0;
        r0 = r25;
        r0 = new java.lang.Object[r0];	 Catch:{ all -> 0x00f7 }
        r25 = r0;
        r0 = r25;
        com.huluxia.framework.base.log.HLog.error(r5, r8, r11, r0);	 Catch:{ all -> 0x00f7 }
        throw r11;	 Catch:{ all -> 0x00f7 }
    L_0x0224:
        r11 = move-exception;
        r5 = "cannot-resume-error";
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ all -> 0x00f7 }
        r5 = "DownloadNetwork";
        r8 = "e %s";
        r25 = 0;
        r0 = r25;
        r0 = new java.lang.Object[r0];	 Catch:{ all -> 0x00f7 }
        r25 = r0;
        r0 = r25;
        com.huluxia.framework.base.log.HLog.error(r5, r8, r11, r0);	 Catch:{ all -> 0x00f7 }
        throw r11;	 Catch:{ all -> 0x00f7 }
    L_0x0241:
        r11 = move-exception;
        r5 = "close-socket-before-end-error";
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ all -> 0x00f7 }
        r5 = "DownloadNetwork";
        r8 = "e %s";
        r25 = 0;
        r0 = r25;
        r0 = new java.lang.Object[r0];	 Catch:{ all -> 0x00f7 }
        r25 = r0;
        r0 = r25;
        com.huluxia.framework.base.log.HLog.error(r5, r8, r11, r0);	 Catch:{ all -> 0x00f7 }
        throw r11;	 Catch:{ all -> 0x00f7 }
    L_0x025e:
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
        r5 = "DownloadNetwork";
        r8 = "e %s";
        r25 = 0;
        r0 = r25;
        r0 = new java.lang.Object[r0];	 Catch:{ all -> 0x00f7 }
        r25 = r0;
        r0 = r25;
        com.huluxia.framework.base.log.HLog.error(r5, r8, r11, r0);	 Catch:{ all -> 0x00f7 }
        throw r11;	 Catch:{ all -> 0x00f7 }
    L_0x0290:
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
        if (r5 == 0) goto L_0x02fa;
    L_0x02b9:
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
        r5 = "DownloadNetwork";
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
    L_0x02fa:
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
        if (r5 == 0) goto L_0x0367;
    L_0x033a:
        r5 = 1;
    L_0x033b:
        r5 = java.lang.Integer.valueOf(r5);	 Catch:{ all -> 0x00f7 }
        r25[r26] = r5;	 Catch:{ all -> 0x00f7 }
        r0 = r28;
        r1 = r25;
        com.huluxia.framework.base.log.HLog.error(r0, r8, r1);	 Catch:{ all -> 0x00f7 }
        r26 = 1048576; // 0x100000 float:1.469368E-39 double:5.180654E-318;
        r5 = (r22 > r26 ? 1 : (r22 == r26 ? 0 : -1));
        if (r5 >= 0) goto L_0x0369;
    L_0x034f:
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
    L_0x0367:
        r5 = 0;
        goto L_0x033b;
    L_0x0369:
        r24 = 0;
        r18 = 0;
        if (r16 == 0) goto L_0x03c6;
    L_0x036f:
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
        if (r9 == 0) goto L_0x0414;
    L_0x0398:
        r18 = new com.huluxia.framework.base.http.io.NetworkResponse;	 Catch:{ all -> 0x00f7 }
        r5 = 0;
        r0 = r18;
        r1 = r24;
        r2 = r19;
        r0.<init>(r1, r9, r2, r5);	 Catch:{ all -> 0x00f7 }
        r5 = 401; // 0x191 float:5.62E-43 double:1.98E-321;
        r0 = r24;
        if (r0 == r5) goto L_0x03b0;
    L_0x03aa:
        r5 = 403; // 0x193 float:5.65E-43 double:1.99E-321;
        r0 = r24;
        if (r0 != r5) goto L_0x03cc;
    L_0x03b0:
        r5 = "auth";
        r8 = new com.huluxia.framework.base.http.toolbox.error.AuthFailureError;	 Catch:{ all -> 0x00f7 }
        r0 = r18;
        r8.<init>(r0);	 Catch:{ all -> 0x00f7 }
        r0 = r29;
        attemptRetryOnException(r5, r0, r8);	 Catch:{ all -> 0x00f7 }
        if (r4 == 0) goto L_0x0009;
    L_0x03c1:
        r4.disconnect();
        goto L_0x0009;
    L_0x03c6:
        r5 = new com.huluxia.framework.base.http.toolbox.error.NoConnectionError;	 Catch:{ all -> 0x00f7 }
        r5.<init>(r11);	 Catch:{ all -> 0x00f7 }
        throw r5;	 Catch:{ all -> 0x00f7 }
    L_0x03cc:
        r5 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        r0 = r24;
        if (r0 < r5) goto L_0x03e8;
    L_0x03d2:
        r5 = 599; // 0x257 float:8.4E-43 double:2.96E-321;
        r0 = r24;
        if (r0 > r5) goto L_0x03e8;
    L_0x03d8:
        r5 = "request-server-error";
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ all -> 0x00f7 }
        r5 = new com.huluxia.framework.base.http.toolbox.error.ServerError;	 Catch:{ all -> 0x00f7 }
        r0 = r18;
        r5.<init>(r0);	 Catch:{ all -> 0x00f7 }
        throw r5;	 Catch:{ all -> 0x00f7 }
    L_0x03e8:
        r5 = 400; // 0x190 float:5.6E-43 double:1.976E-321;
        r0 = r24;
        if (r0 < r5) goto L_0x0404;
    L_0x03ee:
        r5 = 499; // 0x1f3 float:6.99E-43 double:2.465E-321;
        r0 = r24;
        if (r0 > r5) goto L_0x0404;
    L_0x03f4:
        r5 = "request-client-error";
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ all -> 0x00f7 }
        r5 = new com.huluxia.framework.base.http.toolbox.error.ClientError;	 Catch:{ all -> 0x00f7 }
        r0 = r18;
        r5.<init>(r0);	 Catch:{ all -> 0x00f7 }
        throw r5;	 Catch:{ all -> 0x00f7 }
    L_0x0404:
        r5 = "request-unknown-error";
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ all -> 0x00f7 }
        r5 = new com.huluxia.framework.base.http.toolbox.error.UnknownError;	 Catch:{ all -> 0x00f7 }
        r0 = r18;
        r5.<init>(r0);	 Catch:{ all -> 0x00f7 }
        throw r5;	 Catch:{ all -> 0x00f7 }
    L_0x0414:
        r5 = "request-network-error";
        r0 = r29;
        r0.addMarker(r5);	 Catch:{ all -> 0x00f7 }
        r5 = new com.huluxia.framework.base.http.toolbox.error.NetworkError;	 Catch:{ all -> 0x00f7 }
        r0 = r18;
        r5.<init>(r0);	 Catch:{ all -> 0x00f7 }
        throw r5;	 Catch:{ all -> 0x00f7 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.framework.base.http.transport.DownloadNetwork.performRequest(com.huluxia.framework.base.http.io.impl.request.DownloadRequest):com.huluxia.framework.base.http.io.NetworkResponse");
    }

    protected byte[] entityToBytes(HttpEntity entity, DownloadRequest request, Map<String, String> responseHeaders) throws IOException, VolleyError {
        return downloadResume(entity, request, new File(getFilePath(request)), responseHeaders);
    }

    protected void logSlowRequests(long requestLifetime, DownloadRequest request, byte[] responseContents, StatusLine statusLine) {
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

    protected static void attemptRetryOnException(String logPrefix, DownloadRequest request, VolleyError exception) throws VolleyError {
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

    private boolean cannotResume(DownloadRequest request, String etag) {
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
    private byte[] downloadResume(org.apache.http.HttpEntity r28, com.huluxia.framework.base.http.io.impl.request.DownloadRequest r29, java.io.File r30, java.util.Map<java.lang.String, java.lang.String> r31) throws java.io.IOException, com.huluxia.framework.base.http.toolbox.error.VolleyError {
        /*
        r27 = this;
        r4 = "Transfer-Encoding";
        r0 = r31;
        r26 = r0.get(r4);
        r26 = (java.lang.String) r26;
        r12 = -1;
        r4 = com.huluxia.framework.base.utils.UtilsFunction.empty(r26);
        if (r4 == 0) goto L_0x0043;
    L_0x0013:
        r4 = "Content-Length";
        r0 = r31;
        r4 = r0.containsKey(r4);
        if (r4 == 0) goto L_0x006b;
    L_0x001e:
        r4 = "Content-Length";
        r0 = r31;
        r4 = r0.get(r4);
        r4 = (java.lang.String) r4;
        r12 = java.lang.Long.parseLong(r4);
    L_0x002d:
        r4 = "download-resume-content-length-%d";
        r5 = 1;
        r5 = new java.lang.Object[r5];
        r6 = 0;
        r7 = java.lang.Long.valueOf(r12);
        r5[r6] = r7;
        r4 = java.lang.String.format(r4, r5);
        r0 = r29;
        r0.addMarker(r4);
    L_0x0043:
        r4 = -1;
        r4 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1));
        if (r4 != 0) goto L_0x006e;
    L_0x0049:
        if (r26 == 0) goto L_0x0056;
    L_0x004b:
        r4 = "chunked";
        r0 = r26;
        r4 = r0.equals(r4);
        if (r4 != 0) goto L_0x006e;
    L_0x0056:
        r18 = 1;
    L_0x0058:
        if (r18 == 0) goto L_0x0071;
    L_0x005a:
        r4 = "no-size-info";
        r0 = r29;
        r0.addMarker(r4);
        r4 = new com.huluxia.framework.base.http.toolbox.error.UnknownSizeError;
        r5 = "can not know size of download, give up";
        r4.<init>(r5);
        throw r4;
    L_0x006b:
        r12 = -1;
        goto L_0x002d;
    L_0x006e:
        r18 = 0;
        goto L_0x0058;
    L_0x0071:
        r4 = "Content-Range";
        r0 = r31;
        r4 = r0.containsKey(r4);
        if (r4 != 0) goto L_0x009c;
    L_0x007c:
        r4 = "DownloadNetwork";
        r5 = "response header not contain range, redirect to download new file";
        r6 = 0;
        r6 = new java.lang.Object[r6];
        com.huluxia.framework.base.log.HLog.warn(r4, r5, r6);
        r4 = "resume-redirect-no-range";
        r0 = r29;
        r0.addMarker(r4);
        r29.resetRecordProgress();
        r4 = r27.downloadNewFile(r28, r29, r30, r31);	 Catch:{ VolleyError -> 0x0098, IOException -> 0x009a }
    L_0x0097:
        return r4;
    L_0x0098:
        r15 = move-exception;
        throw r15;
    L_0x009a:
        r15 = move-exception;
        throw r15;
    L_0x009c:
        r4 = r30.exists();
        if (r4 != 0) goto L_0x00c2;
    L_0x00a2:
        r4 = "DownloadNetwork";
        r5 = "file not exists, redirect to download new file";
        r6 = 0;
        r6 = new java.lang.Object[r6];
        com.huluxia.framework.base.log.HLog.warn(r4, r5, r6);
        r4 = "resume-redirect-no-file";
        r0 = r29;
        r0.addMarker(r4);
        r29.resetRecordProgress();
        r4 = r27.downloadNewFile(r28, r29, r30, r31);	 Catch:{ VolleyError -> 0x00be, IOException -> 0x00c0 }
        goto L_0x0097;
    L_0x00be:
        r15 = move-exception;
        throw r15;
    L_0x00c0:
        r15 = move-exception;
        throw r15;
    L_0x00c2:
        r19 = 0;
        r19 = new com.huluxia.framework.base.http.toolbox.reader.RandomAccessFileAdapter;	 Catch:{ FileNotFoundException -> 0x0131 }
        r4 = "rw";
        r0 = r19;
        r1 = r30;
        r0.<init>(r1, r4);	 Catch:{ FileNotFoundException -> 0x0131 }
        r16 = 0;
        r14 = 0;
        r4 = r28.getContentEncoding();	 Catch:{ CancelError -> 0x0120 }
        if (r4 == 0) goto L_0x0138;
    L_0x00d9:
        r4 = "gzip";
        r5 = r28.getContentEncoding();	 Catch:{ CancelError -> 0x0120 }
        r5 = r5.getValue();	 Catch:{ CancelError -> 0x0120 }
        r4 = r4.equals(r5);	 Catch:{ CancelError -> 0x0120 }
        if (r4 == 0) goto L_0x0138;
    L_0x00ea:
        r4 = "DownloadNetwork";
        r5 = new java.lang.StringBuilder;	 Catch:{ CancelError -> 0x0120 }
        r5.<init>();	 Catch:{ CancelError -> 0x0120 }
        r6 = "download network gzip ";
        r5 = r5.append(r6);	 Catch:{ CancelError -> 0x0120 }
        r6 = r29.getUrl();	 Catch:{ CancelError -> 0x0120 }
        r5 = r5.append(r6);	 Catch:{ CancelError -> 0x0120 }
        r5 = r5.toString();	 Catch:{ CancelError -> 0x0120 }
        r6 = 0;
        r6 = new java.lang.Object[r6];	 Catch:{ CancelError -> 0x0120 }
        com.huluxia.framework.base.log.HLog.debug(r4, r5, r6);	 Catch:{ CancelError -> 0x0120 }
        r17 = new java.util.zip.GZIPInputStream;	 Catch:{ CancelError -> 0x0120 }
        r4 = r28.getContent();	 Catch:{ CancelError -> 0x0120 }
        r0 = r17;
        r0.<init>(r4);	 Catch:{ CancelError -> 0x0120 }
        r16 = r17;
    L_0x0118:
        if (r16 != 0) goto L_0x013d;
    L_0x011a:
        r4 = new com.huluxia.framework.base.http.toolbox.error.ServerError;	 Catch:{ CancelError -> 0x0120 }
        r4.<init>();	 Catch:{ CancelError -> 0x0120 }
        throw r4;	 Catch:{ CancelError -> 0x0120 }
    L_0x0120:
        r15 = move-exception;
        r4 = "download-resume-catch";
        r0 = r29;
        r0.addMarker(r4);	 Catch:{ all -> 0x012a }
        throw r15;	 Catch:{ all -> 0x012a }
    L_0x012a:
        r4 = move-exception;
        if (r14 == 0) goto L_0x0130;
    L_0x012d:
        r14.close();
    L_0x0130:
        throw r4;
    L_0x0131:
        r15 = move-exception;
        r4 = new com.huluxia.framework.base.http.toolbox.error.LocalFileError;
        r4.<init>(r15);
        throw r4;
    L_0x0138:
        r16 = r28.getContent();	 Catch:{ CancelError -> 0x0120 }
        goto L_0x0118;
    L_0x013d:
        r24 = 0;
        r4 = "Content-Range";
        r0 = r31;
        r22 = r0.get(r4);	 Catch:{ CancelError -> 0x0120 }
        r22 = (java.lang.String) r22;	 Catch:{ CancelError -> 0x0120 }
        r4 = "bytes ";
        r10 = r4.length();	 Catch:{ CancelError -> 0x0120 }
        r4 = r22.length();	 Catch:{ CancelError -> 0x0120 }
        if (r10 <= r4) goto L_0x0165;
    L_0x0157:
        r4 = "resume-range-invalid";
        r0 = r29;
        r0.addMarker(r4);	 Catch:{ CancelError -> 0x0120 }
        r4 = new com.huluxia.framework.base.http.toolbox.error.ServerError;	 Catch:{ CancelError -> 0x0120 }
        r4.<init>();	 Catch:{ CancelError -> 0x0120 }
        throw r4;	 Catch:{ CancelError -> 0x0120 }
    L_0x0165:
        r4 = r22.length();	 Catch:{ CancelError -> 0x0120 }
        r0 = r22;
        r22 = r0.substring(r10, r4);	 Catch:{ CancelError -> 0x0120 }
        r4 = "-";
        r0 = r22;
        r4 = r0.contains(r4);	 Catch:{ CancelError -> 0x0120 }
        if (r4 == 0) goto L_0x0191;
    L_0x017a:
        r4 = "-";
        r0 = r22;
        r4 = r0.split(r4);	 Catch:{ CancelError -> 0x0120 }
        r5 = 0;
        r11 = r4[r5];	 Catch:{ CancelError -> 0x0120 }
        r24 = java.lang.Long.parseLong(r11);	 Catch:{ NumberFormatException -> 0x0200 }
    L_0x018a:
        r0 = r19;
        r1 = r24;
        r0.seek(r1);	 Catch:{ IOException -> 0x0220 }
    L_0x0191:
        r4 = "download-resume-%d-file-%s";
        r5 = 2;
        r5 = new java.lang.Object[r5];	 Catch:{ CancelError -> 0x0120 }
        r6 = 0;
        r7 = java.lang.Long.valueOf(r24);	 Catch:{ CancelError -> 0x0120 }
        r5[r6] = r7;	 Catch:{ CancelError -> 0x0120 }
        r6 = 1;
        r7 = r30.getAbsolutePath();	 Catch:{ CancelError -> 0x0120 }
        r5[r6] = r7;	 Catch:{ CancelError -> 0x0120 }
        r4 = java.lang.String.format(r4, r5);	 Catch:{ CancelError -> 0x0120 }
        r0 = r29;
        r0.addMarker(r4);	 Catch:{ CancelError -> 0x0120 }
        r20 = r24;
        r19.seek(r20);	 Catch:{ IOException -> 0x0227 }
        r4 = r28.getContentLength();	 Catch:{ CancelError -> 0x0120 }
        r8 = r4 + r20;
        r23 = r29.getReaderType();	 Catch:{ CancelError -> 0x0120 }
        r0 = r27;
        r4 = r0.mByteArrayPool;	 Catch:{ CancelError -> 0x0120 }
        r0 = r23;
        r14 = com.huluxia.framework.base.http.toolbox.reader.ReaderFactory.getReader(r4, r0);	 Catch:{ CancelError -> 0x0120 }
        r4 = r14 instanceof com.huluxia.framework.base.http.toolbox.reader.XorEnhancedReader;	 Catch:{ CancelError -> 0x0120 }
        if (r4 == 0) goto L_0x022e;
    L_0x01cb:
        r0 = r14;
        r0 = (com.huluxia.framework.base.http.toolbox.reader.XorEnhancedReader) r0;	 Catch:{ CancelError -> 0x0120 }
        r4 = r0;
        r0 = r20;
        r4.setStartLength(r0);	 Catch:{ CancelError -> 0x0120 }
    L_0x01d4:
        r4 = android.os.SystemClock.elapsedRealtime();	 Catch:{ CancelError -> 0x0120 }
        r0 = r29;
        r0.setSpeedSampleStart(r4);	 Catch:{ CancelError -> 0x0120 }
        r4 = new com.huluxia.framework.base.http.transport.DownloadNetwork$1;	 Catch:{ CancelError -> 0x0120 }
        r5 = r27;
        r6 = r29;
        r7 = r31;
        r4.<init>(r6, r7, r8);	 Catch:{ CancelError -> 0x0120 }
        r0 = r16;
        r1 = r19;
        r14.copy(r0, r1, r4);	 Catch:{ CancelError -> 0x0120 }
        r4 = r29.getRecord();	 Catch:{ CancelError -> 0x0120 }
        r4 = r4.name;	 Catch:{ CancelError -> 0x0120 }
        r4 = r4.getBytes();	 Catch:{ CancelError -> 0x0120 }
        if (r14 == 0) goto L_0x0097;
    L_0x01fb:
        r14.close();
        goto L_0x0097;
    L_0x0200:
        r15 = move-exception;
        r4 = "DownloadNetwork";
        r5 = new java.lang.StringBuilder;	 Catch:{ CancelError -> 0x0120 }
        r5.<init>();	 Catch:{ CancelError -> 0x0120 }
        r6 = "downloadResume exception = ";
        r5 = r5.append(r6);	 Catch:{ CancelError -> 0x0120 }
        r5 = r5.append(r15);	 Catch:{ CancelError -> 0x0120 }
        r5 = r5.toString();	 Catch:{ CancelError -> 0x0120 }
        r6 = 0;
        r6 = new java.lang.Object[r6];	 Catch:{ CancelError -> 0x0120 }
        com.huluxia.framework.base.log.HLog.error(r4, r5, r6);	 Catch:{ CancelError -> 0x0120 }
        goto L_0x018a;
    L_0x0220:
        r15 = move-exception;
        r4 = new com.huluxia.framework.base.http.toolbox.error.LocalFileError;	 Catch:{ CancelError -> 0x0120 }
        r4.<init>(r15);	 Catch:{ CancelError -> 0x0120 }
        throw r4;	 Catch:{ CancelError -> 0x0120 }
    L_0x0227:
        r15 = move-exception;
        r4 = new com.huluxia.framework.base.http.toolbox.error.LocalFileError;	 Catch:{ CancelError -> 0x0120 }
        r4.<init>(r15);	 Catch:{ CancelError -> 0x0120 }
        throw r4;	 Catch:{ CancelError -> 0x0120 }
    L_0x022e:
        r4 = r14 instanceof com.huluxia.framework.base.http.toolbox.reader.BandwidthLimitReader;	 Catch:{ CancelError -> 0x0120 }
        if (r4 == 0) goto L_0x01d4;
    L_0x0232:
        r0 = r14;
        r0 = (com.huluxia.framework.base.http.toolbox.reader.BandwidthLimitReader) r0;	 Catch:{ CancelError -> 0x0120 }
        r4 = r0;
        r0 = r29;
        r4.setRequest(r0);	 Catch:{ CancelError -> 0x0120 }
        goto L_0x01d4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.framework.base.http.transport.DownloadNetwork.downloadResume(org.apache.http.HttpEntity, com.huluxia.framework.base.http.io.impl.request.DownloadRequest, java.io.File, java.util.Map):byte[]");
    }

    private byte[] downloadNewFile(HttpEntity entity, DownloadRequest request, File file, Map<String, String> responseHeaders) throws IOException, VolleyError {
        request.addMarker("begin-download-refresh");
        try {
            BufferOutputStreamAdapter outputStream = new BufferOutputStreamAdapter(new FileOutputStream(file));
            DownloadReader downloadReader = null;
            try {
                InputStream in;
                if (entity.getContentEncoding() == null || !"gzip".equals(entity.getContentEncoding().getValue())) {
                    in = entity.getContent();
                } else {
                    HLog.debug(TAG, "download network gzip " + request.getUrl(), new Object[0]);
                    in = new GZIPInputStream(entity.getContent());
                }
                if (in == null) {
                    throw new ServerError();
                }
                final long total = entity.getContentLength();
                downloadReader = ReaderFactory.getReader(this.mByteArrayPool, request.getReaderType());
                if (downloadReader instanceof XorEnhancedReader) {
                    ((XorEnhancedReader) downloadReader).setStartLength(file.length());
                } else if (downloadReader instanceof BandwidthLimitReader) {
                    ((BandwidthLimitReader) downloadReader).setRequest(request);
                }
                request.setSpeedSampleStart(SystemClock.elapsedRealtime());
                final DownloadRequest downloadRequest = request;
                final Map<String, String> map = responseHeaders;
                downloadReader.copy(in, outputStream, new IReaderCallback<CancelError, VolleyError>() {
                    public void end() throws VolleyError {
                        downloadRequest.networkComplete();
                        DownloadNetwork.this.handleEndOfStream(downloadRequest, map);
                        HLog.error(DownloadNetwork.TAG, "downloadNewFile read buffer result is -1", new Object[0]);
                    }

                    public void readLoop(int count) throws CancelError {
                        downloadRequest.progress((long) count, total);
                        float speed = downloadRequest.getSpeed();
                        if (downloadRequest.isCanceled()) {
                            downloadRequest.addMarker("run-loop-cancel-new");
                            throw new CancelError("download new cancel when read network data");
                        } else if (downloadRequest.getShouldNotify() && downloadRequest.getProgressListener() != null) {
                            downloadRequest.getProgressListener().onProgress(downloadRequest.getUrl(), total, downloadRequest.getRecord().progress, speed);
                        }
                    }
                });
                byte[] bytes = request.getRecord().name.getBytes();
                HLog.debug(TAG, "downloadNewFile completed, file %s, url %s ", new Object[]{file.getAbsolutePath(), request.getUrl()});
                if (downloadReader != null) {
                    downloadReader.close();
                }
                return bytes;
            } catch (CancelError e) {
                request.addMarker("download-new-catch");
                throw e;
            } catch (Throwable th) {
                HLog.debug(TAG, "downloadNewFile completed, file %s, url %s ", new Object[]{file.getAbsolutePath(), request.getUrl()});
                if (downloadReader != null) {
                    downloadReader.close();
                }
            }
        } catch (FileNotFoundException e2) {
            throw new LocalFileError(e2);
        }
    }

    private void handleEndOfStream(DownloadRequest request, Map<String, String> responseHeaders) throws CannotResumeError, CloseSocketBeforeEndError {
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

    private String getFilePath(DownloadRequest request) {
        return new File(request.getRecord().dir, request.getRecord().name).getAbsolutePath();
    }
}
