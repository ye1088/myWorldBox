package com.huluxia;

import android.net.Uri;
import com.huluxia.framework.base.utils.VisibleForTesting;
import com.huluxia.image.base.imagepipeline.image.d;
import com.huluxia.image.pipeline.producers.ah.a;
import com.huluxia.image.pipeline.producers.ao;
import com.huluxia.image.pipeline.producers.c;
import com.huluxia.image.pipeline.producers.j;
import com.huluxia.image.pipeline.producers.s;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.Nullable;

/* compiled from: PainterNetworkFetcher */
public class p extends c<s> {
    private static final int MAX_REDIRECTS = 5;
    private static final int gj = 3;
    private static final int gk = 10000;
    private static final int gl = 10000;
    public static final int gm = 307;
    public static final int gn = 308;
    private int go;
    private final ExecutorService gp;

    @com.huluxia.framework.base.utils.VisibleForTesting
    void b(com.huluxia.image.pipeline.producers.s r5, com.huluxia.image.pipeline.producers.ah.a r6) {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(Unknown Source)
	at java.util.HashMap$KeyIterator.next(Unknown Source)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:80)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r4 = this;
        r0 = 0;
        r2 = r5.getUri();	 Catch:{ IOException -> 0x001a, all -> 0x0024 }
        r3 = 5;	 Catch:{ IOException -> 0x001a, all -> 0x0024 }
        r0 = r4.a_isRightVersion(r2, r3);	 Catch:{ IOException -> 0x001a, all -> 0x0024 }
        if (r0 == 0) goto L_0x0014;	 Catch:{ IOException -> 0x001a, all -> 0x0024 }
    L_0x000c:
        r2 = r0.getInputStream();	 Catch:{ IOException -> 0x001a, all -> 0x0024 }
        r3 = -1;	 Catch:{ IOException -> 0x001a, all -> 0x0024 }
        r6.h(r2, r3);	 Catch:{ IOException -> 0x001a, all -> 0x0024 }
    L_0x0014:
        if (r0 == 0) goto L_0x0019;
    L_0x0016:
        r0.disconnect();
    L_0x0019:
        return;
    L_0x001a:
        r1 = move-exception;
        r6.j(r1);	 Catch:{ IOException -> 0x001a, all -> 0x0024 }
        if (r0 == 0) goto L_0x0019;
    L_0x0020:
        r0.disconnect();
        goto L_0x0019;
    L_0x0024:
        r2 = move-exception;
        if (r0 == 0) goto L_0x002a;
    L_0x0027:
        r0.disconnect();
    L_0x002a:
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.p.b(com.huluxia.image.pipeline.producers.s, com.huluxia.image.pipeline.producers.ah$a_isRightVersion):void");
    }

    public p() {
        this(Executors.newFixedThreadPool(3));
    }

    @VisibleForTesting
    p(ExecutorService executorService) {
        this.gp = executorService;
    }

    public s a(j<d> consumer, ao context) {
        return new s(consumer, context);
    }

    public void a(s fetchState, a callback) {
        fetchState.oN().a(new 2(this, this.gp.submit(new 1(this, fetchState, callback)), callback));
    }

    private HttpURLConnection a(Uri uri, int maxRedirects) throws IOException {
        HttpURLConnection connection = b(uri);
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        this.go = connection.getResponseCode();
        if (s(this.go)) {
            return connection;
        }
        if (t(this.go)) {
            String nextUriString = connection.getHeaderField("Location");
            connection.disconnect();
            Uri nextUri = nextUriString == null ? null : Uri.parse(nextUriString);
            String originalScheme = uri.getScheme();
            if (maxRedirects > 0 && nextUri != null && !nextUri.getScheme().equals(originalScheme)) {
                return a(nextUri, maxRedirects - 1);
            }
            String message;
            if (maxRedirects == 0) {
                message = a("URL %s follows too many redirects", uri.toString());
            } else {
                message = a("URL %s returned %d without a_isRightVersion valid redirect", uri.toString(), Integer.valueOf(this.go));
            }
            throw new IOException(message);
        }
        connection.disconnect();
        throw new IOException(String.format("Image URL %s returned HTTP code %d", new Object[]{uri.toString(), Integer.valueOf(this.go)}));
    }

    @VisibleForTesting
    static HttpURLConnection b(Uri uri) throws IOException {
        return (HttpURLConnection) new URL(uri.toString()).openConnection();
    }

    private static boolean s(int responseCode) {
        return responseCode >= 200 && responseCode < 300;
    }

    private static boolean t(int responseCode) {
        switch (responseCode) {
            case 300:
            case 301:
            case 302:
            case 303:
            case 307:
            case 308:
                return true;
            default:
                return false;
        }
    }

    private static String a(String format, Object... args) {
        return String.format(Locale.getDefault(), format, args);
    }

    @Nullable
    public Map<String, String> a(s fetchState, int byteSize) {
        Map<String, String> extraMap = new HashMap();
        extraMap.put("id", String.valueOf(fetchState.getId()));
        extraMap.put("uri", fetchState.getUri().toString());
        extraMap.put("bytesize", String.valueOf(byteSize));
        extraMap.put("responsecode", String.valueOf(this.go));
        return extraMap;
    }
}
