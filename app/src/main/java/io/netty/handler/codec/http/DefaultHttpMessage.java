package io.netty.handler.codec.http;

import io.netty.util.internal.ObjectUtil;

public abstract class DefaultHttpMessage extends DefaultHttpObject implements HttpMessage {
    private static final int HASH_CODE_PRIME = 31;
    private final HttpHeaders headers;
    private HttpVersion version;

    protected DefaultHttpMessage(HttpVersion version) {
        this(version, true, false);
    }

    protected DefaultHttpMessage(HttpVersion version, boolean validateHeaders, boolean singleFieldHeaders) {
        this(version, singleFieldHeaders ? new CombinedHttpHeaders(validateHeaders) : new DefaultHttpHeaders(validateHeaders));
    }

    protected DefaultHttpMessage(HttpVersion version, HttpHeaders headers) {
        this.version = (HttpVersion) ObjectUtil.checkNotNull(version, "version");
        this.headers = (HttpHeaders) ObjectUtil.checkNotNull(headers, "headers");
    }

    public HttpHeaders headers() {
        return this.headers;
    }

    @Deprecated
    public HttpVersion getProtocolVersion() {
        return protocolVersion();
    }

    public HttpVersion protocolVersion() {
        return this.version;
    }

    public int hashCode() {
        return ((((this.headers.hashCode() + 31) * 31) + this.version.hashCode()) * 31) + super.hashCode();
    }

    public boolean equals(Object o) {
        if (!(o instanceof DefaultHttpMessage)) {
            return false;
        }
        DefaultHttpMessage other = (DefaultHttpMessage) o;
        if (headers().equals(other.headers()) && protocolVersion().equals(other.protocolVersion()) && super.equals(o)) {
            return true;
        }
        return false;
    }

    public HttpMessage setProtocolVersion(HttpVersion version) {
        if (version == null) {
            throw new NullPointerException("version");
        }
        this.version = version;
        return this;
    }
}
