package com.integralblue.httpresponsecache.compat.libcore.net.http;

import com.integralblue.httpresponsecache.compat.Charsets;
import com.integralblue.httpresponsecache.compat.Strings;
import com.integralblue.httpresponsecache.compat.URLs;
import com.integralblue.httpresponsecache.compat.libcore.io.Base64;
import com.integralblue.httpresponsecache.compat.libcore.io.IoUtils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketPermission;
import java.net.URL;
import java.security.Permission;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpStatus;

class HttpURLConnectionImpl extends HttpURLConnection {
    private final int defaultPort;
    protected HttpEngine httpEngine;
    protected IOException httpEngineFailure;
    private Proxy proxy;
    private final RawHeaders rawRequestHeaders;
    private int redirectionCount;

    enum Retry {
        NONE,
        SAME_CONNECTION,
        DIFFERENT_CONNECTION
    }

    protected HttpURLConnectionImpl(URL url, int port) {
        super(url);
        this.rawRequestHeaders = new RawHeaders();
        this.defaultPort = port;
    }

    protected HttpURLConnectionImpl(URL url, int port, Proxy proxy) {
        this(url, port);
        this.proxy = proxy;
    }

    public final void connect() throws IOException {
        initHttpEngine();
        try {
            this.httpEngine.sendRequest();
        } catch (IOException e) {
            this.httpEngineFailure = e;
            throw e;
        }
    }

    public final void disconnect() {
        if (this.httpEngine != null) {
            if (this.httpEngine.hasResponse()) {
                IoUtils.closeQuietly(this.httpEngine.getResponseBody());
            }
            this.httpEngine.release(false);
        }
    }

    public final InputStream getErrorStream() {
        InputStream inputStream = null;
        try {
            HttpEngine response = getResponse();
            if (response.hasResponseBody() && response.getResponseCode() >= 400) {
                inputStream = response.getResponseBody();
            }
        } catch (IOException e) {
        }
        return inputStream;
    }

    public final String getHeaderField(int position) {
        try {
            return getResponse().getResponseHeaders().getHeaders().getValue(position);
        } catch (IOException e) {
            return null;
        }
    }

    public final String getHeaderField(String fieldName) {
        try {
            RawHeaders rawHeaders = getResponse().getResponseHeaders().getHeaders();
            return fieldName == null ? rawHeaders.getStatusLine() : rawHeaders.get(fieldName);
        } catch (IOException e) {
            return null;
        }
    }

    public final String getHeaderFieldKey(int position) {
        try {
            return getResponse().getResponseHeaders().getHeaders().getFieldName(position);
        } catch (IOException e) {
            return null;
        }
    }

    public final Map<String, List<String>> getHeaderFields() {
        try {
            return getResponse().getResponseHeaders().getHeaders().toMultimap();
        } catch (IOException e) {
            return null;
        }
    }

    public final Map<String, List<String>> getRequestProperties() {
        if (!this.connected) {
            return this.rawRequestHeaders.toMultimap();
        }
        throw new IllegalStateException("Cannot access request header fields after connection is set");
    }

    public final InputStream getInputStream() throws IOException {
        if (this.doInput) {
            HttpEngine response = getResponse();
            if (getResponseCode() >= 400) {
                throw new FileNotFoundException(this.url.toString());
            }
            InputStream result = response.getResponseBody();
            if (result != null) {
                return result;
            }
            throw new IOException("No response body exists; responseCode=" + getResponseCode());
        }
        throw new ProtocolException("This protocol does not support input");
    }

    public final OutputStream getOutputStream() throws IOException {
        connect();
        OutputStream result = this.httpEngine.getRequestBody();
        if (result == null) {
            throw new ProtocolException("method does not support a_isRightVersion request body: " + this.method);
        } else if (!this.httpEngine.hasResponse()) {
            return result;
        } else {
            throw new ProtocolException("cannot write request body after response has been read");
        }
    }

    public final Permission getPermission() throws IOException {
        return new SocketPermission(getConnectToHost() + ":" + getConnectToPort(), "connect, resolve");
    }

    private String getConnectToHost() {
        return usingProxy() ? ((InetSocketAddress) this.proxy.address()).getHostName() : getURL().getHost();
    }

    private int getConnectToPort() {
        int hostPort = usingProxy() ? ((InetSocketAddress) this.proxy.address()).getPort() : getURL().getPort();
        return hostPort < 0 ? getDefaultPort() : hostPort;
    }

    public final String getRequestProperty(String field) {
        if (field == null) {
            return null;
        }
        return this.rawRequestHeaders.get(field);
    }

    private void initHttpEngine() throws IOException {
        if (this.httpEngineFailure != null) {
            throw this.httpEngineFailure;
        } else if (this.httpEngine == null) {
            this.connected = true;
            try {
                if (this.doOutput) {
                    if (this.method == "GET") {
                        this.method = "POST";
                    } else if (!(this.method == "POST" || this.method == "PUT")) {
                        throw new ProtocolException(this.method + " does not support writing");
                    }
                }
                this.httpEngine = newHttpEngine(this.method, this.rawRequestHeaders, null, null);
            } catch (IOException e) {
                this.httpEngineFailure = e;
                throw e;
            }
        }
    }

    protected HttpEngine newHttpEngine(String method, RawHeaders requestHeaders, HttpConnection connection, RetryableOutputStream requestBody) throws IOException {
        return new HttpEngine(this, method, requestHeaders, connection, requestBody);
    }

    private HttpEngine getResponse() throws IOException {
        initHttpEngine();
        if (this.httpEngine.hasResponse()) {
            return this.httpEngine;
        }
        while (true) {
            OutputStream requestBody;
            try {
                this.httpEngine.sendRequest();
                this.httpEngine.readResponse();
                Retry retry = processResponseHeaders();
                if (retry == Retry.NONE) {
                    this.httpEngine.automaticallyReleaseConnectionToPool();
                    return this.httpEngine;
                }
                String retryMethod = this.method;
                requestBody = this.httpEngine.getRequestBody();
                int responseCode = getResponseCode();
                if (responseCode == 300 || responseCode == 301 || responseCode == 302 || responseCode == 303) {
                    retryMethod = "GET";
                    requestBody = null;
                }
                if (requestBody == null || (requestBody instanceof RetryableOutputStream)) {
                    if (retry == Retry.DIFFERENT_CONNECTION) {
                        this.httpEngine.automaticallyReleaseConnectionToPool();
                    }
                    this.httpEngine.release(true);
                    this.httpEngine = newHttpEngine(retryMethod, this.rawRequestHeaders, this.httpEngine.getConnection(), (RetryableOutputStream) requestBody);
                } else {
                    throw new HttpRetryException("Cannot retry streamed HTTP body", this.httpEngine.getResponseCode());
                }
            } catch (IOException e) {
                requestBody = this.httpEngine.getRequestBody();
                if (this.httpEngine.hasRecycledConnection() && (requestBody == null || (requestBody instanceof RetryableOutputStream))) {
                    this.httpEngine.release(false);
                    this.httpEngine = newHttpEngine(this.method, this.rawRequestHeaders, null, (RetryableOutputStream) requestBody);
                } else {
                    this.httpEngineFailure = e;
                    throw e;
                }
            }
        }
        this.httpEngineFailure = e;
        throw e;
    }

    HttpEngine getHttpEngine() {
        return this.httpEngine;
    }

    private Retry processResponseHeaders() throws IOException {
        switch (getResponseCode()) {
            case 300:
            case 301:
            case 302:
            case 303:
                if (!getInstanceFollowRedirects()) {
                    return Retry.NONE;
                }
                int i = this.redirectionCount + 1;
                this.redirectionCount = i;
                if (i > 5) {
                    throw new ProtocolException("Too many redirects");
                }
                String location = getHeaderField("Location");
                if (location == null) {
                    return Retry.NONE;
                }
                URL previousUrl = this.url;
                this.url = new URL(previousUrl, location);
                if (!previousUrl.getProtocol().equals(this.url.getProtocol())) {
                    return Retry.NONE;
                }
                if (previousUrl.getHost().equals(this.url.getHost()) && URLs.getEffectivePort(previousUrl) == URLs.getEffectivePort(this.url)) {
                    return Retry.SAME_CONNECTION;
                }
                return Retry.DIFFERENT_CONNECTION;
            case 401:
                break;
            case HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED /*407*/:
                if (!usingProxy()) {
                    throw new IOException("Received HTTP_PROXY_AUTH (407) code while not using proxy");
                }
                break;
            default:
                return Retry.NONE;
        }
        return processAuthHeader(getResponseCode(), this.httpEngine.getResponseHeaders(), this.rawRequestHeaders) ? Retry.SAME_CONNECTION : Retry.NONE;
    }

    final boolean processAuthHeader(int responseCode, ResponseHeaders response, RawHeaders successorRequestHeaders) throws IOException {
        if (responseCode == HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED || responseCode == 401) {
            String credentials = getAuthorizationCredentials(response.getHeaders(), responseCode == HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED ? "Proxy-Authenticate" : "WWW-Authenticate");
            if (credentials == null) {
                return false;
            }
            successorRequestHeaders.set(responseCode == HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED ? "Proxy-Authorization" : "Authorization", credentials);
            return true;
        }
        throw new IllegalArgumentException();
    }

    private String getAuthorizationCredentials(RawHeaders responseHeaders, String challengeHeader) throws IOException {
        List<Challenge> challenges = HeaderParser.parseChallenges(responseHeaders, challengeHeader);
        if (challenges.isEmpty()) {
            throw new IOException("No authentication challenges found");
        }
        for (Challenge challenge : challenges) {
            PasswordAuthentication auth = Authenticator.requestPasswordAuthentication(getConnectToInetAddress(), getConnectToPort(), this.url.getProtocol(), challenge.realm, challenge.scheme);
            if (auth != null) {
                return challenge.scheme + " " + Base64.encode(Strings.getBytes(auth.getUserName() + ":" + new String(auth.getPassword()), Charsets.ISO_8859_1));
            }
        }
        return null;
    }

    private InetAddress getConnectToInetAddress() throws IOException {
        return usingProxy() ? ((InetSocketAddress) this.proxy.address()).getAddress() : InetAddress.getByName(getURL().getHost());
    }

    final int getDefaultPort() {
        return this.defaultPort;
    }

    final int getFixedContentLength() {
        return this.fixedContentLength;
    }

    final int getChunkLength() {
        return this.chunkLength;
    }

    final Proxy getProxy() {
        return this.proxy;
    }

    final void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public final boolean usingProxy() {
        return (this.proxy == null || this.proxy.type() == Type.DIRECT) ? false : true;
    }

    public String getResponseMessage() throws IOException {
        return getResponse().getResponseHeaders().getHeaders().getResponseMessage();
    }

    public final int getResponseCode() throws IOException {
        return getResponse().getResponseCode();
    }

    public final void setRequestProperty(String field, String newValue) {
        if (this.connected) {
            throw new IllegalStateException("Cannot set request property after connection is made");
        } else if (field == null) {
            throw new NullPointerException("field == null");
        } else {
            this.rawRequestHeaders.set(field, newValue);
        }
    }

    public final void addRequestProperty(String field, String value) {
        if (this.connected) {
            throw new IllegalStateException("Cannot add request property after connection is made");
        } else if (field == null) {
            throw new NullPointerException("field == null");
        } else {
            this.rawRequestHeaders.add(field, value);
        }
    }
}
