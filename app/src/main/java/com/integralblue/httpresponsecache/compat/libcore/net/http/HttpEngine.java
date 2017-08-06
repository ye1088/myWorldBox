package com.integralblue.httpresponsecache.compat.libcore.net.http;

import com.integralblue.httpresponsecache.compat.Charsets;
import com.integralblue.httpresponsecache.compat.Strings;
import com.integralblue.httpresponsecache.compat.URLs;
import com.integralblue.httpresponsecache.compat.java.net.ExtendedResponseCache;
import com.integralblue.httpresponsecache.compat.java.net.ResponseSource;
import com.integralblue.httpresponsecache.compat.libcore.io.IoUtils;
import com.integralblue.httpresponsecache.compat.libcore.io.Streams;
import com.integralblue.httpresponsecache.compat.libcore.util.EmptyArray;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CacheRequest;
import java.net.CacheResponse;
import java.net.CookieHandler;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.ResponseCache;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.protocol.HTTP;

public class HttpEngine {
    public static final String CONNECT = "CONNECT";
    public static final int DEFAULT_CHUNK_LENGTH = 1024;
    public static final String DELETE = "DELETE";
    private static final CacheResponse GATEWAY_TIMEOUT_RESPONSE = new CacheResponse() {
        public Map<String, List<String>> getHeaders() throws IOException {
            Map<String, List<String>> result = new HashMap();
            result.put(null, Collections.singletonList("HTTP/1.1 504 Gateway Timeout"));
            return result;
        }

        public InputStream getBody() throws IOException {
            return new ByteArrayInputStream(EmptyArray.BYTE);
        }
    };
    public static final String GET = "GET";
    public static final String HEAD = "HEAD";
    public static final int HTTP_CONTINUE = 100;
    public static final int MAX_REDIRECTS = 5;
    private static final int MAX_REQUEST_BUFFER_LENGTH = 32768;
    public static final String OPTIONS = "OPTIONS";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String TRACE = "TRACE";
    private boolean automaticallyReleaseConnectionToPool;
    private CacheRequest cacheRequest;
    private CacheResponse cacheResponse;
    private InputStream cachedResponseBody;
    private ResponseHeaders cachedResponseHeaders;
    protected HttpConnection connection;
    private boolean connectionReleased;
    private int httpMinorVersion = 1;
    protected final String method;
    protected final HttpURLConnectionImpl policy;
    private AbstractHttpOutputStream requestBodyOut;
    private final RequestHeaders requestHeaders;
    private OutputStream requestOut;
    private InputStream responseBodyIn;
    private final ResponseCache responseCache = ResponseCache.getDefault();
    private ResponseHeaders responseHeaders;
    private ResponseSource responseSource;
    boolean sendChunked;
    private long sentRequestMillis = -1;
    private InputStream socketIn;
    private OutputStream socketOut;
    private boolean transparentGzip;
    private final URI uri;

    public HttpEngine(HttpURLConnectionImpl policy, String method, RawHeaders requestHeaders, HttpConnection connection, RetryableOutputStream requestBodyOut) throws IOException {
        this.policy = policy;
        this.method = method;
        this.connection = connection;
        this.requestBodyOut = requestBodyOut;
        try {
            this.uri = URLs.toURILenient(policy.getURL());
            this.requestHeaders = new RequestHeaders(this.uri, new RawHeaders(requestHeaders));
        } catch (URISyntaxException e) {
            throw new IOException(e.toString());
        }
    }

    public URI getUri() {
        return this.uri;
    }

    public final void sendRequest() throws IOException {
        if (this.responseSource == null) {
            prepareRawRequestHeaders();
            initResponseSource();
            if (this.responseCache instanceof ExtendedResponseCache) {
                ((ExtendedResponseCache) this.responseCache).trackResponse(this.responseSource);
            }
            if (this.requestHeaders.isOnlyIfCached() && this.responseSource.requiresConnection()) {
                if (this.responseSource == ResponseSource.CONDITIONAL_CACHE) {
                    IoUtils.closeQuietly(this.cachedResponseBody);
                }
                this.responseSource = ResponseSource.CACHE;
                this.cacheResponse = GATEWAY_TIMEOUT_RESPONSE;
                setResponse(new ResponseHeaders(this.uri, RawHeaders.fromMultimap(this.cacheResponse.getHeaders())), this.cacheResponse.getBody());
            }
            if (this.responseSource.requiresConnection()) {
                sendSocketRequest();
            } else if (this.connection != null) {
                HttpConnectionPool.INSTANCE.recycle(this.connection);
                this.connection = null;
            }
        }
    }

    private void initResponseSource() throws IOException {
        this.responseSource = ResponseSource.NETWORK;
        if (this.policy.getUseCaches() && this.responseCache != null) {
            CacheResponse candidate = this.responseCache.get(this.uri, this.method, this.requestHeaders.getHeaders().toMultimap());
            if (candidate != null) {
                Map<String, List<String>> responseHeadersMap = candidate.getHeaders();
                this.cachedResponseBody = candidate.getBody();
                if (!acceptCacheResponseType(candidate) || responseHeadersMap == null || this.cachedResponseBody == null) {
                    IoUtils.closeQuietly(this.cachedResponseBody);
                    return;
                }
                this.cachedResponseHeaders = new ResponseHeaders(this.uri, RawHeaders.fromMultimap(responseHeadersMap));
                this.responseSource = this.cachedResponseHeaders.chooseResponseSource(System.currentTimeMillis(), this.requestHeaders);
                if (this.responseSource == ResponseSource.CACHE) {
                    this.cacheResponse = candidate;
                    setResponse(this.cachedResponseHeaders, this.cachedResponseBody);
                } else if (this.responseSource == ResponseSource.CONDITIONAL_CACHE) {
                    this.cacheResponse = candidate;
                } else if (this.responseSource == ResponseSource.NETWORK) {
                    IoUtils.closeQuietly(this.cachedResponseBody);
                } else {
                    throw new AssertionError();
                }
            }
        }
    }

    private void sendSocketRequest() throws IOException {
        if (this.connection == null) {
            connect();
        }
        if (this.socketOut == null && this.requestOut == null && this.socketIn == null) {
            this.socketOut = this.connection.getOutputStream();
            this.requestOut = this.socketOut;
            this.socketIn = this.connection.getInputStream();
            if (hasRequestBody()) {
                initRequestBodyOut();
                return;
            }
            return;
        }
        throw new IllegalStateException();
    }

    protected void connect() throws IOException {
        if (this.connection == null) {
            this.connection = openSocketConnection();
        }
    }

    protected final HttpConnection openSocketConnection() throws IOException {
        HttpConnection result = HttpConnection.connect(this.uri, getSslSocketFactory(), this.policy.getProxy(), requiresTunnel(), this.policy.getConnectTimeout());
        Proxy proxy = result.getAddress().getProxy();
        if (proxy != null) {
            this.policy.setProxy(proxy);
        }
        result.setSoTimeout(this.policy.getReadTimeout());
        return result;
    }

    protected void initRequestBodyOut() throws IOException {
        int chunkLength = this.policy.getChunkLength();
        if (chunkLength > 0 || this.requestHeaders.isChunked()) {
            this.sendChunked = true;
            if (chunkLength == -1) {
                chunkLength = 1024;
            }
        }
        if (this.socketOut == null) {
            throw new IllegalStateException("No socket to write to; was a_isRightVersion POST cached?");
        }
        if (this.httpMinorVersion == 0) {
            this.sendChunked = false;
        }
        int fixedContentLength = this.policy.getFixedContentLength();
        if (this.requestBodyOut == null) {
            if (fixedContentLength != -1) {
                writeRequestHeaders(fixedContentLength);
                this.requestBodyOut = new FixedLengthOutputStream(this.requestOut, fixedContentLength);
            } else if (this.sendChunked) {
                writeRequestHeaders(-1);
                this.requestBodyOut = new ChunkedOutputStream(this.requestOut, chunkLength);
            } else if (this.requestHeaders.getContentLength() != -1) {
                writeRequestHeaders(this.requestHeaders.getContentLength());
                this.requestBodyOut = new RetryableOutputStream(this.requestHeaders.getContentLength());
            } else {
                this.requestBodyOut = new RetryableOutputStream();
            }
        }
    }

    private void setResponse(ResponseHeaders headers, InputStream body) throws IOException {
        if (this.responseBodyIn != null) {
            throw new IllegalStateException();
        }
        this.responseHeaders = headers;
        this.httpMinorVersion = this.responseHeaders.getHeaders().getHttpMinorVersion();
        if (body != null) {
            initContentStream(body);
        }
    }

    private boolean hasRequestBody() {
        return this.method == "POST" || this.method == "PUT";
    }

    public final OutputStream getRequestBody() {
        if (this.responseSource != null) {
            return this.requestBodyOut;
        }
        throw new IllegalStateException();
    }

    public final boolean hasResponse() {
        return this.responseHeaders != null;
    }

    public final RequestHeaders getRequestHeaders() {
        return this.requestHeaders;
    }

    public final ResponseHeaders getResponseHeaders() {
        if (this.responseHeaders != null) {
            return this.responseHeaders;
        }
        throw new IllegalStateException();
    }

    public final int getResponseCode() {
        if (this.responseHeaders != null) {
            return this.responseHeaders.getHeaders().getResponseCode();
        }
        throw new IllegalStateException();
    }

    public final InputStream getResponseBody() {
        if (this.responseHeaders != null) {
            return this.responseBodyIn;
        }
        throw new IllegalStateException();
    }

    public final CacheResponse getCacheResponse() {
        return this.cacheResponse;
    }

    public final HttpConnection getConnection() {
        return this.connection;
    }

    public final boolean hasRecycledConnection() {
        return this.connection != null && this.connection.isRecycled();
    }

    protected boolean acceptCacheResponseType(CacheResponse cacheResponse) {
        return true;
    }

    private void maybeCache() throws IOException {
        if (this.method != CONNECT && this.policy.getUseCaches() && this.responseCache != null && this.responseHeaders.isCacheable(this.requestHeaders)) {
            this.cacheRequest = this.responseCache.put(this.uri, getHttpConnectionToCache());
        }
    }

    protected HttpURLConnection getHttpConnectionToCache() {
        return this.policy;
    }

    public final void automaticallyReleaseConnectionToPool() {
        this.automaticallyReleaseConnectionToPool = true;
        if (this.connection != null && this.connectionReleased) {
            HttpConnectionPool.INSTANCE.recycle(this.connection);
            this.connection = null;
        }
    }

    public final void release(boolean reusable) {
        if (this.responseBodyIn == this.cachedResponseBody) {
            IoUtils.closeQuietly(this.responseBodyIn);
        }
        if (!this.connectionReleased && this.connection != null) {
            this.connectionReleased = true;
            if (!(this.requestBodyOut == null || this.requestBodyOut.closed)) {
                reusable = false;
            }
            if (hasConnectionCloseHeader()) {
                reusable = false;
            }
            if (this.responseBodyIn instanceof UnknownLengthHttpInputStream) {
                reusable = false;
            }
            if (reusable && this.responseBodyIn != null) {
                try {
                    Streams.skipAll(this.responseBodyIn);
                } catch (IOException e) {
                    reusable = false;
                }
            }
            if (!reusable) {
                this.connection.closeSocketAndStreams();
                this.connection = null;
            } else if (this.automaticallyReleaseConnectionToPool) {
                HttpConnectionPool.INSTANCE.recycle(this.connection);
                this.connection = null;
            }
        }
    }

    private void initContentStream(InputStream transferStream) throws IOException {
        if (this.transparentGzip && this.responseHeaders.isContentEncodingGzip()) {
            this.responseHeaders.stripContentEncoding();
            this.responseBodyIn = new GZIPInputStream(transferStream);
            return;
        }
        this.responseBodyIn = transferStream;
    }

    private InputStream getTransferStream() throws IOException {
        if (!hasResponseBody()) {
            return new FixedLengthInputStream(this.socketIn, this.cacheRequest, this, 0);
        }
        if (this.responseHeaders.isChunked()) {
            return new ChunkedInputStream(this.socketIn, this.cacheRequest, this);
        }
        if (this.responseHeaders.getContentLength() != -1) {
            return new FixedLengthInputStream(this.socketIn, this.cacheRequest, this, this.responseHeaders.getContentLength());
        }
        return new UnknownLengthHttpInputStream(this.socketIn, this.cacheRequest, this);
    }

    private void readResponseHeaders() throws IOException {
        RawHeaders headers;
        do {
            headers = new RawHeaders();
            headers.setStatusLine(Streams.readAsciiLine(this.socketIn));
            readHeaders(headers);
        } while (headers.getResponseCode() == 100);
        setResponse(new ResponseHeaders(this.uri, headers), null);
    }

    public final boolean hasResponseBody() {
        int responseCode = this.responseHeaders.getHeaders().getResponseCode();
        if (this.method == "HEAD") {
            return false;
        }
        if (this.method != CONNECT && ((responseCode < 100 || responseCode >= 200) && responseCode != 204 && responseCode != 304)) {
            return true;
        }
        if (this.responseHeaders.getContentLength() != -1 || this.responseHeaders.isChunked()) {
            return true;
        }
        return false;
    }

    final void readTrailers() throws IOException {
        readHeaders(this.responseHeaders.getHeaders());
    }

    private void readHeaders(RawHeaders headers) throws IOException {
        while (true) {
            String line = Streams.readAsciiLine(this.socketIn);
            if (Strings.isEmpty(line)) {
                break;
            }
            headers.addLine(line);
        }
        CookieHandler cookieHandler = CookieHandler.getDefault();
        if (cookieHandler != null) {
            cookieHandler.put(this.uri, headers.toMultimap());
        }
    }

    private void writeRequestHeaders(int contentLength) throws IOException {
        if (this.sentRequestMillis != -1) {
            throw new IllegalStateException();
        }
        byte[] bytes = Strings.getBytes(getNetworkRequestHeaders().toHeaderString(), Charsets.ISO_8859_1);
        if (contentLength != -1 && bytes.length + contentLength <= 32768) {
            this.requestOut = new BufferedOutputStream(this.socketOut, bytes.length + contentLength);
        }
        this.sentRequestMillis = System.currentTimeMillis();
        this.requestOut.write(bytes);
    }

    protected RawHeaders getNetworkRequestHeaders() throws IOException {
        this.requestHeaders.getHeaders().setStatusLine(getRequestLine());
        int fixedContentLength = this.policy.getFixedContentLength();
        if (fixedContentLength != -1) {
            this.requestHeaders.setContentLength(fixedContentLength);
        } else if (this.sendChunked) {
            this.requestHeaders.setChunked();
        } else if (this.requestBodyOut instanceof RetryableOutputStream) {
            this.requestHeaders.setContentLength(((RetryableOutputStream) this.requestBodyOut).contentLength());
        }
        return this.requestHeaders.getHeaders();
    }

    private void prepareRawRequestHeaders() throws IOException {
        this.requestHeaders.getHeaders().setStatusLine(getRequestLine());
        if (this.requestHeaders.getUserAgent() == null) {
            this.requestHeaders.setUserAgent(getDefaultUserAgent());
        }
        if (this.requestHeaders.getHost() == null) {
            this.requestHeaders.setHost(getOriginAddress(this.policy.getURL()));
        }
        String keepAlive = System.getProperty("http.keepAlive");
        if (this.httpMinorVersion > 0 && this.requestHeaders.getConnection() == null && (keepAlive == null || Boolean.parseBoolean(keepAlive))) {
            this.requestHeaders.setConnection(HTTP.CONN_KEEP_ALIVE);
        }
        if (this.requestHeaders.getAcceptEncoding() == null) {
            this.transparentGzip = true;
            this.requestHeaders.setAcceptEncoding("gzip");
        }
        if (hasRequestBody() && this.requestHeaders.getContentType() == null) {
            this.requestHeaders.setContentType("application/x-www-form-urlencoded");
        }
        long ifModifiedSince = this.policy.getIfModifiedSince();
        if (ifModifiedSince != 0) {
            this.requestHeaders.setIfModifiedSince(new Date(ifModifiedSince));
        }
        CookieHandler cookieHandler = CookieHandler.getDefault();
        if (cookieHandler != null) {
            this.requestHeaders.addCookies(cookieHandler.get(this.uri, this.requestHeaders.getHeaders().toMultimap()));
        }
    }

    private String getRequestLine() {
        return this.method + " " + requestString() + " " + (this.httpMinorVersion == 0 ? "HTTP/1.0" : "HTTP/1.1");
    }

    private String requestString() {
        URL url = this.policy.getURL();
        if (includeAuthorityInRequestLine()) {
            return url.toString();
        }
        String fileOnly = url.getFile();
        if (fileOnly == null) {
            return "/";
        }
        if (fileOnly.startsWith("/")) {
            return fileOnly;
        }
        return "/" + fileOnly;
    }

    protected boolean includeAuthorityInRequestLine() {
        return this.policy.usingProxy();
    }

    protected SSLSocketFactory getSslSocketFactory() {
        return null;
    }

    protected final String getDefaultUserAgent() {
        String agent = System.getProperty("http.agent");
        return agent != null ? agent : "Java" + System.getProperty("java.version");
    }

    private boolean hasConnectionCloseHeader() {
        return (this.responseHeaders != null && this.responseHeaders.hasConnectionClose()) || this.requestHeaders.hasConnectionClose();
    }

    protected final String getOriginAddress(URL url) {
        int port = url.getPort();
        String result = url.getHost();
        if (port <= 0 || port == this.policy.getDefaultPort()) {
            return result;
        }
        return result + ":" + port;
    }

    protected boolean requiresTunnel() {
        return false;
    }

    public final void readResponse() throws IOException {
        if (!hasResponse()) {
            if (this.responseSource == null) {
                throw new IllegalStateException("readResponse() without sendRequest()");
            } else if (this.responseSource.requiresConnection()) {
                if (this.sentRequestMillis == -1) {
                    writeRequestHeaders(this.requestBodyOut instanceof RetryableOutputStream ? ((RetryableOutputStream) this.requestBodyOut).contentLength() : -1);
                }
                if (this.requestBodyOut != null) {
                    this.requestBodyOut.close();
                    if (this.requestBodyOut instanceof RetryableOutputStream) {
                        ((RetryableOutputStream) this.requestBodyOut).writeToSocket(this.requestOut);
                    }
                }
                this.requestOut.flush();
                this.requestOut = this.socketOut;
                readResponseHeaders();
                this.responseHeaders.setLocalTimestamps(this.sentRequestMillis, System.currentTimeMillis());
                if (this.responseSource == ResponseSource.CONDITIONAL_CACHE) {
                    if (this.cachedResponseHeaders.validate(this.responseHeaders)) {
                        release(true);
                        setResponse(this.cachedResponseHeaders.combine(this.responseHeaders), this.cachedResponseBody);
                        if (this.responseCache instanceof ExtendedResponseCache) {
                            ExtendedResponseCache httpResponseCache = this.responseCache;
                            httpResponseCache.trackConditionalCacheHit();
                            httpResponseCache.update(this.cacheResponse, getHttpConnectionToCache());
                            return;
                        }
                        return;
                    }
                    IoUtils.closeQuietly(this.cachedResponseBody);
                }
                if (hasResponseBody()) {
                    maybeCache();
                }
                initContentStream(getTransferStream());
            }
        }
    }
}
