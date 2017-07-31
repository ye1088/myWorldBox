package com.integralblue.httpresponsecache.compat.libcore.net.http;

import com.integralblue.httpresponsecache.compat.URLs;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CacheResponse;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.SecureCacheResponse;
import java.net.URL;
import java.security.Permission;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;

final class HttpsURLConnectionImpl extends HttpsURLConnection {
    private final HttpUrlConnectionDelegate delegate;

    private final class HttpUrlConnectionDelegate extends HttpURLConnectionImpl {
        private HttpUrlConnectionDelegate(URL url, int port) {
            super(url, port);
        }

        private HttpUrlConnectionDelegate(URL url, int port, Proxy proxy) {
            super(url, port, proxy);
        }

        protected HttpEngine newHttpEngine(String method, RawHeaders requestHeaders, HttpConnection connection, RetryableOutputStream requestBody) throws IOException {
            return new HttpsEngine(this, method, requestHeaders, connection, requestBody, HttpsURLConnectionImpl.this);
        }

        public SecureCacheResponse getCacheResponse() {
            HttpsEngine engine = this.httpEngine;
            return engine != null ? (SecureCacheResponse) engine.getCacheResponse() : null;
        }

        public SSLSocket getSSLSocket() {
            HttpsEngine engine = this.httpEngine;
            return engine != null ? engine.sslSocket : null;
        }
    }

    private static class HttpsEngine extends HttpEngine {
        private final HttpsURLConnectionImpl enclosing;
        private SSLSocket sslSocket;

        private HttpsEngine(HttpURLConnectionImpl policy, String method, RawHeaders requestHeaders, HttpConnection connection, RetryableOutputStream requestBody, HttpsURLConnectionImpl enclosing) throws IOException {
            super(policy, method, requestHeaders, connection, requestBody);
            this.sslSocket = connection != null ? connection.getSecureSocketIfConnected() : null;
            this.enclosing = enclosing;
        }

        protected void connect() throws IOException {
            boolean connectionReused;
            try {
                connectionReused = makeSslConnection(true);
            } catch (IOException e) {
                if ((e instanceof SSLHandshakeException) && (e.getCause() instanceof CertificateException)) {
                    throw e;
                }
                release(false);
                connectionReused = makeSslConnection(false);
            }
            if (!connectionReused) {
                this.sslSocket = this.connection.verifySecureSocketHostname(this.enclosing.getHostnameVerifier());
            }
        }

        private boolean makeSslConnection(boolean tlsTolerant) throws IOException {
            if (this.connection == null) {
                this.connection = openSocketConnection();
                if (this.connection.getAddress().getProxy() != null) {
                    makeTunnel(this.policy, this.connection, getRequestHeaders());
                }
            }
            this.sslSocket = this.connection.getSecureSocketIfConnected();
            if (this.sslSocket != null) {
                return true;
            }
            this.connection.setupSecureSocket(this.enclosing.getSSLSocketFactory(), tlsTolerant);
            return false;
        }

        private void makeTunnel(HttpURLConnectionImpl policy, HttpConnection connection, RequestHeaders requestHeaders) throws IOException {
            RawHeaders rawRequestHeaders = requestHeaders.getHeaders();
            while (true) {
                HttpEngine connect = new ProxyConnectEngine(policy, rawRequestHeaders, connection);
                connect.sendRequest();
                connect.readResponse();
                int responseCode = connect.getResponseCode();
                switch (connect.getResponseCode()) {
                    case 200:
                        return;
                    case HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED /*407*/:
                        RawHeaders rawRequestHeaders2 = new RawHeaders(rawRequestHeaders);
                        if (policy.processAuthHeader(HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED, connect.getResponseHeaders(), rawRequestHeaders2)) {
                            rawRequestHeaders = rawRequestHeaders2;
                        } else {
                            throw new IOException("Failed to authenticate with proxy");
                        }
                    default:
                        throw new IOException("Unexpected response code for CONNECT: " + responseCode);
                }
            }
        }

        protected boolean acceptCacheResponseType(CacheResponse cacheResponse) {
            return cacheResponse instanceof SecureCacheResponse;
        }

        protected boolean includeAuthorityInRequestLine() {
            return false;
        }

        protected SSLSocketFactory getSslSocketFactory() {
            return this.enclosing.getSSLSocketFactory();
        }

        protected HttpURLConnection getHttpConnectionToCache() {
            return this.enclosing;
        }
    }

    private static class ProxyConnectEngine extends HttpEngine {
        public ProxyConnectEngine(HttpURLConnectionImpl policy, RawHeaders requestHeaders, HttpConnection connection) throws IOException {
            super(policy, HttpEngine.CONNECT, requestHeaders, connection, null);
        }

        protected RawHeaders getNetworkRequestHeaders() throws IOException {
            RequestHeaders privateHeaders = getRequestHeaders();
            URL url = this.policy.getURL();
            RawHeaders result = new RawHeaders();
            result.setStatusLine("CONNECT " + url.getHost() + ":" + URLs.getEffectivePort(url) + " HTTP/1.1");
            String host = privateHeaders.getHost();
            if (host == null) {
                host = getOriginAddress(url);
            }
            result.set("Host", host);
            String userAgent = privateHeaders.getUserAgent();
            if (userAgent == null) {
                userAgent = getDefaultUserAgent();
            }
            result.set("User-Agent", userAgent);
            String proxyAuthorization = privateHeaders.getProxyAuthorization();
            if (proxyAuthorization != null) {
                result.set("Proxy-Authorization", proxyAuthorization);
            }
            result.set("Proxy-Connection", HTTP.CONN_KEEP_ALIVE);
            return result;
        }

        protected boolean requiresTunnel() {
            return true;
        }
    }

    protected HttpsURLConnectionImpl(URL url, int port) {
        super(url);
        this.delegate = new HttpUrlConnectionDelegate(url, port);
    }

    protected HttpsURLConnectionImpl(URL url, int port, Proxy proxy) {
        super(url);
        this.delegate = new HttpUrlConnectionDelegate(url, port, proxy);
    }

    private void checkConnected() {
        if (this.delegate.getSSLSocket() == null) {
            throw new IllegalStateException("Connection has not yet been established");
        }
    }

    HttpEngine getHttpEngine() {
        return this.delegate.getHttpEngine();
    }

    public String getCipherSuite() {
        SecureCacheResponse cacheResponse = this.delegate.getCacheResponse();
        if (cacheResponse != null) {
            return cacheResponse.getCipherSuite();
        }
        checkConnected();
        return this.delegate.getSSLSocket().getSession().getCipherSuite();
    }

    public Certificate[] getLocalCertificates() {
        SecureCacheResponse cacheResponse = this.delegate.getCacheResponse();
        if (cacheResponse != null) {
            List<Certificate> result = cacheResponse.getLocalCertificateChain();
            return result != null ? (Certificate[]) result.toArray(new Certificate[result.size()]) : null;
        } else {
            checkConnected();
            return this.delegate.getSSLSocket().getSession().getLocalCertificates();
        }
    }

    public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
        SecureCacheResponse cacheResponse = this.delegate.getCacheResponse();
        if (cacheResponse != null) {
            List<Certificate> result = cacheResponse.getServerCertificateChain();
            return result != null ? (Certificate[]) result.toArray(new Certificate[result.size()]) : null;
        } else {
            checkConnected();
            return this.delegate.getSSLSocket().getSession().getPeerCertificates();
        }
    }

    public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
        SecureCacheResponse cacheResponse = this.delegate.getCacheResponse();
        if (cacheResponse != null) {
            return cacheResponse.getPeerPrincipal();
        }
        checkConnected();
        return this.delegate.getSSLSocket().getSession().getPeerPrincipal();
    }

    public Principal getLocalPrincipal() {
        SecureCacheResponse cacheResponse = this.delegate.getCacheResponse();
        if (cacheResponse != null) {
            return cacheResponse.getLocalPrincipal();
        }
        checkConnected();
        return this.delegate.getSSLSocket().getSession().getLocalPrincipal();
    }

    public void disconnect() {
        this.delegate.disconnect();
    }

    public InputStream getErrorStream() {
        return this.delegate.getErrorStream();
    }

    public String getRequestMethod() {
        return this.delegate.getRequestMethod();
    }

    public int getResponseCode() throws IOException {
        return this.delegate.getResponseCode();
    }

    public String getResponseMessage() throws IOException {
        return this.delegate.getResponseMessage();
    }

    public void setRequestMethod(String method) throws ProtocolException {
        this.delegate.setRequestMethod(method);
    }

    public boolean usingProxy() {
        return this.delegate.usingProxy();
    }

    public boolean getInstanceFollowRedirects() {
        return this.delegate.getInstanceFollowRedirects();
    }

    public void setInstanceFollowRedirects(boolean followRedirects) {
        this.delegate.setInstanceFollowRedirects(followRedirects);
    }

    public void connect() throws IOException {
        this.connected = true;
        this.delegate.connect();
    }

    public boolean getAllowUserInteraction() {
        return this.delegate.getAllowUserInteraction();
    }

    public Object getContent() throws IOException {
        return this.delegate.getContent();
    }

    public Object getContent(Class[] types) throws IOException {
        return this.delegate.getContent(types);
    }

    public String getContentEncoding() {
        return this.delegate.getContentEncoding();
    }

    public int getContentLength() {
        return this.delegate.getContentLength();
    }

    public String getContentType() {
        return this.delegate.getContentType();
    }

    public long getDate() {
        return this.delegate.getDate();
    }

    public boolean getDefaultUseCaches() {
        return this.delegate.getDefaultUseCaches();
    }

    public boolean getDoInput() {
        return this.delegate.getDoInput();
    }

    public boolean getDoOutput() {
        return this.delegate.getDoOutput();
    }

    public long getExpiration() {
        return this.delegate.getExpiration();
    }

    public String getHeaderField(int pos) {
        return this.delegate.getHeaderField(pos);
    }

    public Map<String, List<String>> getHeaderFields() {
        return this.delegate.getHeaderFields();
    }

    public Map<String, List<String>> getRequestProperties() {
        return this.delegate.getRequestProperties();
    }

    public void addRequestProperty(String field, String newValue) {
        this.delegate.addRequestProperty(field, newValue);
    }

    public String getHeaderField(String key) {
        return this.delegate.getHeaderField(key);
    }

    public long getHeaderFieldDate(String field, long defaultValue) {
        return this.delegate.getHeaderFieldDate(field, defaultValue);
    }

    public int getHeaderFieldInt(String field, int defaultValue) {
        return this.delegate.getHeaderFieldInt(field, defaultValue);
    }

    public String getHeaderFieldKey(int posn) {
        return this.delegate.getHeaderFieldKey(posn);
    }

    public long getIfModifiedSince() {
        return this.delegate.getIfModifiedSince();
    }

    public InputStream getInputStream() throws IOException {
        return this.delegate.getInputStream();
    }

    public long getLastModified() {
        return this.delegate.getLastModified();
    }

    public OutputStream getOutputStream() throws IOException {
        return this.delegate.getOutputStream();
    }

    public Permission getPermission() throws IOException {
        return this.delegate.getPermission();
    }

    public String getRequestProperty(String field) {
        return this.delegate.getRequestProperty(field);
    }

    public URL getURL() {
        return this.delegate.getURL();
    }

    public boolean getUseCaches() {
        return this.delegate.getUseCaches();
    }

    public void setAllowUserInteraction(boolean newValue) {
        this.delegate.setAllowUserInteraction(newValue);
    }

    public void setDefaultUseCaches(boolean newValue) {
        this.delegate.setDefaultUseCaches(newValue);
    }

    public void setDoInput(boolean newValue) {
        this.delegate.setDoInput(newValue);
    }

    public void setDoOutput(boolean newValue) {
        this.delegate.setDoOutput(newValue);
    }

    public void setIfModifiedSince(long newValue) {
        this.delegate.setIfModifiedSince(newValue);
    }

    public void setRequestProperty(String field, String newValue) {
        this.delegate.setRequestProperty(field, newValue);
    }

    public void setUseCaches(boolean newValue) {
        this.delegate.setUseCaches(newValue);
    }

    public void setConnectTimeout(int setConnectTimeout) {
        this.delegate.setConnectTimeout(setConnectTimeout);
    }

    public int getConnectTimeout() {
        return this.delegate.getConnectTimeout();
    }

    public void setReadTimeout(int timeoutMillis) {
        this.delegate.setReadTimeout(timeoutMillis);
    }

    public int getReadTimeout() {
        return this.delegate.getReadTimeout();
    }

    public String toString() {
        return this.delegate.toString();
    }

    public void setFixedLengthStreamingMode(int contentLength) {
        this.delegate.setFixedLengthStreamingMode(contentLength);
    }

    public void setChunkedStreamingMode(int chunkLength) {
        this.delegate.setChunkedStreamingMode(chunkLength);
    }
}
