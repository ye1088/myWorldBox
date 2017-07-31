package com.integralblue.httpresponsecache.compat.libcore.net.http;

import com.integralblue.httpresponsecache.compat.Charsets;
import com.integralblue.httpresponsecache.compat.MD5;
import com.integralblue.httpresponsecache.compat.Strings;
import com.integralblue.httpresponsecache.compat.java.net.ExtendedResponseCache;
import com.integralblue.httpresponsecache.compat.java.net.ResponseSource;
import com.integralblue.httpresponsecache.compat.libcore.io.Base64;
import com.integralblue.httpresponsecache.compat.libcore.io.IoUtils;
import com.integralblue.httpresponsecache.compat.libcore.io.Streams;
import com.jakewharton.DiskLruCache;
import com.jakewharton.DiskLruCache.Editor;
import com.jakewharton.DiskLruCache.Snapshot;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.CacheRequest;
import java.net.CacheResponse;
import java.net.HttpURLConnection;
import java.net.ResponseCache;
import java.net.SecureCacheResponse;
import java.net.URI;
import java.net.URLConnection;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public final class HttpResponseCache extends ResponseCache implements ExtendedResponseCache {
    private static final int ENTRY_BODY = 1;
    private static final int ENTRY_COUNT = 2;
    private static final int ENTRY_METADATA = 0;
    private static final int VERSION = 201105;
    private final DiskLruCache cache;
    private int hitCount;
    private int networkCount;
    private int requestCount;
    private int writeAbortCount;
    private int writeSuccessCount;

    private final class CacheRequestImpl extends CacheRequest {
        private OutputStream body;
        private OutputStream cacheOut;
        private boolean done;
        private final Editor editor;

        public CacheRequestImpl(final Editor editor) throws IOException {
            this.editor = editor;
            this.cacheOut = editor.newOutputStream(1);
            this.body = new FilterOutputStream(this.cacheOut, HttpResponseCache.this) {
                public void close() throws IOException {
                    synchronized (HttpResponseCache.this) {
                        if (CacheRequestImpl.this.done) {
                            return;
                        }
                        CacheRequestImpl.this.done = true;
                        HttpResponseCache.this.writeSuccessCount = HttpResponseCache.this.writeSuccessCount + 1;
                        super.close();
                        editor.commit();
                    }
                }

                public void write(byte[] buffer, int offset, int length) throws IOException {
                    this.out.write(buffer, offset, length);
                }
            };
        }

        public void abort() {
            synchronized (HttpResponseCache.this) {
                if (this.done) {
                    return;
                }
                this.done = true;
                HttpResponseCache.this.writeAbortCount = HttpResponseCache.this.writeAbortCount + 1;
                IoUtils.closeQuietly(this.cacheOut);
                try {
                    this.editor.abort();
                } catch (IOException e) {
                }
            }
        }

        public OutputStream getBody() throws IOException {
            return this.body;
        }
    }

    private static final class Entry {
        private final String cipherSuite;
        private final Certificate[] localCertificates;
        private final Certificate[] peerCertificates;
        private final String requestMethod;
        private final RawHeaders responseHeaders;
        private final String uri;
        private final RawHeaders varyHeaders;

        public Entry(InputStream in) throws IOException {
            try {
                int i;
                this.uri = Streams.readAsciiLine(in);
                this.requestMethod = Streams.readAsciiLine(in);
                this.varyHeaders = new RawHeaders();
                int varyRequestHeaderLineCount = readInt(in);
                for (i = 0; i < varyRequestHeaderLineCount; i++) {
                    this.varyHeaders.addLine(Streams.readAsciiLine(in));
                }
                this.responseHeaders = new RawHeaders();
                this.responseHeaders.setStatusLine(Streams.readAsciiLine(in));
                int responseHeaderLineCount = readInt(in);
                for (i = 0; i < responseHeaderLineCount; i++) {
                    this.responseHeaders.addLine(Streams.readAsciiLine(in));
                }
                if (isHttps()) {
                    String blank = Streams.readAsciiLine(in);
                    if (Strings.isEmpty(blank)) {
                        this.cipherSuite = Streams.readAsciiLine(in);
                        this.peerCertificates = readCertArray(in);
                        this.localCertificates = readCertArray(in);
                    } else {
                        throw new IOException("expected \"\" but was \"" + blank + "\"");
                    }
                }
                this.cipherSuite = null;
                this.peerCertificates = null;
                this.localCertificates = null;
                in.close();
            } catch (Throwable th) {
                in.close();
            }
        }

        public Entry(URI uri, RawHeaders varyHeaders, HttpURLConnection httpConnection) {
            this.uri = uri.toString();
            this.varyHeaders = varyHeaders;
            this.requestMethod = httpConnection.getRequestMethod();
            this.responseHeaders = RawHeaders.fromMultimap(httpConnection.getHeaderFields());
            if (isHttps()) {
                HttpsURLConnection httpsConnection = (HttpsURLConnection) httpConnection;
                this.cipherSuite = httpsConnection.getCipherSuite();
                Certificate[] peerCertificatesNonFinal = null;
                try {
                    peerCertificatesNonFinal = httpsConnection.getServerCertificates();
                } catch (SSLPeerUnverifiedException e) {
                }
                this.peerCertificates = peerCertificatesNonFinal;
                this.localCertificates = httpsConnection.getLocalCertificates();
                return;
            }
            this.cipherSuite = null;
            this.peerCertificates = null;
            this.localCertificates = null;
        }

        public void writeTo(Editor editor) throws IOException {
            int i;
            Writer writer = new BufferedWriter(new OutputStreamWriter(editor.newOutputStream(0), Charsets.UTF_8));
            writer.write(this.uri + '\n');
            writer.write(this.requestMethod + '\n');
            writer.write(Integer.toString(this.varyHeaders.length()) + '\n');
            for (i = 0; i < this.varyHeaders.length(); i++) {
                writer.write(this.varyHeaders.getFieldName(i) + ": " + this.varyHeaders.getValue(i) + '\n');
            }
            writer.write(this.responseHeaders.getStatusLine() + '\n');
            writer.write(Integer.toString(this.responseHeaders.length()) + '\n');
            for (i = 0; i < this.responseHeaders.length(); i++) {
                writer.write(this.responseHeaders.getFieldName(i) + ": " + this.responseHeaders.getValue(i) + '\n');
            }
            if (isHttps()) {
                writer.write(10);
                writer.write(this.cipherSuite + '\n');
                writeCertArray(writer, this.peerCertificates);
                writeCertArray(writer, this.localCertificates);
            }
            writer.close();
        }

        private boolean isHttps() {
            return this.uri.startsWith("https://");
        }

        private int readInt(InputStream in) throws IOException {
            String intString = Streams.readAsciiLine(in);
            try {
                return Integer.parseInt(intString);
            } catch (NumberFormatException e) {
                throw new IOException("expected an int but was \"" + intString + "\"");
            }
        }

        private Certificate[] readCertArray(InputStream in) throws IOException {
            int length = readInt(in);
            if (length == -1) {
                return null;
            }
            try {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                Certificate[] result = new Certificate[length];
                for (int i = 0; i < result.length; i++) {
                    result[i] = certificateFactory.generateCertificate(new ByteArrayInputStream(Base64.decode(Strings.getBytes(Streams.readAsciiLine(in), Charsets.US_ASCII))));
                }
                return result;
            } catch (CertificateException e) {
                throw new IOException(e.toString());
            }
        }

        private void writeCertArray(Writer writer, Certificate[] certificates) throws IOException {
            if (certificates == null) {
                writer.write("-1\n");
                return;
            }
            try {
                writer.write(Integer.toString(certificates.length) + '\n');
                for (Certificate certificate : certificates) {
                    writer.write(Base64.encode(certificate.getEncoded()) + '\n');
                }
            } catch (CertificateEncodingException e) {
                throw new IOException(e.toString());
            }
        }

        public boolean matches(URI uri, String requestMethod, Map<String, List<String>> requestHeaders) {
            return this.uri.equals(uri.toString()) && this.requestMethod.equals(requestMethod) && new ResponseHeaders(uri, this.responseHeaders).varyMatches(this.varyHeaders.toMultimap(), requestHeaders);
        }
    }

    static class EntryCacheResponse extends CacheResponse {
        private final Entry entry;
        private final InputStream in;
        private final Snapshot snapshot;

        public EntryCacheResponse(Entry entry, Snapshot snapshot) {
            this.entry = entry;
            this.snapshot = snapshot;
            this.in = HttpResponseCache.newBodyInputStream(snapshot);
        }

        public Map<String, List<String>> getHeaders() {
            return this.entry.responseHeaders.toMultimap();
        }

        public InputStream getBody() {
            return this.in;
        }
    }

    static class EntrySecureCacheResponse extends SecureCacheResponse {
        private final Entry entry;
        private final InputStream in;
        private final Snapshot snapshot;

        public EntrySecureCacheResponse(Entry entry, Snapshot snapshot) {
            this.entry = entry;
            this.snapshot = snapshot;
            this.in = HttpResponseCache.newBodyInputStream(snapshot);
        }

        public Map<String, List<String>> getHeaders() {
            return this.entry.responseHeaders.toMultimap();
        }

        public InputStream getBody() {
            return this.in;
        }

        public String getCipherSuite() {
            return this.entry.cipherSuite;
        }

        public List<Certificate> getServerCertificateChain() throws SSLPeerUnverifiedException {
            if (this.entry.peerCertificates != null && this.entry.peerCertificates.length != 0) {
                return Arrays.asList((Object[]) this.entry.peerCertificates.clone());
            }
            throw new SSLPeerUnverifiedException(null);
        }

        public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
            if (this.entry.peerCertificates != null && this.entry.peerCertificates.length != 0) {
                return ((X509Certificate) this.entry.peerCertificates[0]).getSubjectX500Principal();
            }
            throw new SSLPeerUnverifiedException(null);
        }

        public List<Certificate> getLocalCertificateChain() {
            if (this.entry.localCertificates == null || this.entry.localCertificates.length == 0) {
                return null;
            }
            return Arrays.asList((Object[]) this.entry.localCertificates.clone());
        }

        public Principal getLocalPrincipal() {
            if (this.entry.localCertificates == null || this.entry.localCertificates.length == 0) {
                return null;
            }
            return ((X509Certificate) this.entry.localCertificates[0]).getSubjectX500Principal();
        }
    }

    public HttpResponseCache(File directory, long maxSize) throws IOException {
        this.cache = DiskLruCache.open(directory, VERSION, 2, maxSize);
    }

    private String uriToKey(URI uri) {
        return Strings.bytesToHexString(new MD5().digest(Strings.getBytes(uri.toString(), Charsets.UTF_8)), false);
    }

    public CacheResponse get(URI uri, String requestMethod, Map<String, List<String>> requestHeaders) {
        try {
            Snapshot snapshot = this.cache.get(uriToKey(uri));
            if (snapshot == null) {
                return null;
            }
            Entry entry = new Entry(new BufferedInputStream(snapshot.getInputStream(0)));
            if (entry.matches(uri, requestMethod, requestHeaders)) {
                return entry.isHttps() ? new EntrySecureCacheResponse(entry, snapshot) : new EntryCacheResponse(entry, snapshot);
            } else {
                snapshot.close();
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }

    public CacheRequest put(URI uri, URLConnection urlConnection) throws IOException {
        if (!(urlConnection instanceof HttpURLConnection)) {
            return null;
        }
        HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
        String requestMethod = httpConnection.getRequestMethod();
        String key = uriToKey(uri);
        if (requestMethod.equals("POST") || requestMethod.equals("PUT") || requestMethod.equals("DELETE")) {
            try {
                this.cache.remove(key);
                return null;
            } catch (IOException e) {
                return null;
            }
        } else if (!requestMethod.equals("GET")) {
            return null;
        } else {
            HttpEngine httpEngine = getHttpEngine(httpConnection);
            if (httpEngine == null) {
                return null;
            }
            ResponseHeaders response = httpEngine.getResponseHeaders();
            if (response.hasVaryAll()) {
                return null;
            }
            Entry entry = new Entry(uri, httpEngine.getRequestHeaders().getHeaders().getAll(response.getVaryFields()), httpConnection);
            try {
                Editor editor = this.cache.edit(key);
                if (editor == null) {
                    return null;
                }
                entry.writeTo(editor);
                return new CacheRequestImpl(editor);
            } catch (IOException e2) {
                abortQuietly(null);
                return null;
            }
        }
    }

    public void update(CacheResponse conditionalCacheHit, HttpURLConnection httpConnection) {
        HttpEngine httpEngine = getHttpEngine(httpConnection);
        Entry entry = new Entry(httpEngine.getUri(), httpEngine.getRequestHeaders().getHeaders().getAll(httpEngine.getResponseHeaders().getVaryFields()), httpConnection);
        try {
            Editor editor = (conditionalCacheHit instanceof EntryCacheResponse ? ((EntryCacheResponse) conditionalCacheHit).snapshot : ((EntrySecureCacheResponse) conditionalCacheHit).snapshot).edit();
            if (editor != null) {
                entry.writeTo(editor);
                editor.commit();
            }
        } catch (IOException e) {
            abortQuietly(null);
        }
    }

    private void abortQuietly(Editor editor) {
        if (editor != null) {
            try {
                editor.abort();
            } catch (IOException e) {
            }
        }
    }

    private HttpEngine getHttpEngine(HttpURLConnection httpConnection) {
        if (httpConnection instanceof HttpURLConnectionImpl) {
            return ((HttpURLConnectionImpl) httpConnection).getHttpEngine();
        }
        if (httpConnection instanceof HttpsURLConnectionImpl) {
            return ((HttpsURLConnectionImpl) httpConnection).getHttpEngine();
        }
        return null;
    }

    public DiskLruCache getCache() {
        return this.cache;
    }

    public synchronized int getWriteAbortCount() {
        return this.writeAbortCount;
    }

    public synchronized int getWriteSuccessCount() {
        return this.writeSuccessCount;
    }

    public synchronized void trackResponse(ResponseSource source) {
        this.requestCount++;
        switch (source) {
            case CACHE:
                this.hitCount++;
                break;
            case CONDITIONAL_CACHE:
            case NETWORK:
                this.networkCount++;
                break;
        }
    }

    public synchronized void trackConditionalCacheHit() {
        this.hitCount++;
    }

    public synchronized int getNetworkCount() {
        return this.networkCount;
    }

    public synchronized int getHitCount() {
        return this.hitCount;
    }

    public synchronized int getRequestCount() {
        return this.requestCount;
    }

    private static InputStream newBodyInputStream(final Snapshot snapshot) {
        return new FilterInputStream(snapshot.getInputStream(1)) {
            public void close() throws IOException {
                snapshot.close();
                super.close();
            }
        };
    }
}
