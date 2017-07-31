package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.internal.ObjectUtil;

public class DefaultFullHttpRequest extends DefaultHttpRequest implements FullHttpRequest {
    private final ByteBuf content;
    private int hash;
    private final HttpHeaders trailingHeader;

    public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri) {
        this(httpVersion, method, uri, Unpooled.buffer(0));
    }

    public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri, ByteBuf content) {
        this(httpVersion, method, uri, content, true);
    }

    public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri, boolean validateHeaders) {
        this(httpVersion, method, uri, Unpooled.buffer(0), validateHeaders);
    }

    public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri, ByteBuf content, boolean validateHeaders) {
        super(httpVersion, method, uri, validateHeaders);
        this.content = (ByteBuf) ObjectUtil.checkNotNull(content, "content");
        this.trailingHeader = new DefaultHttpHeaders(validateHeaders);
    }

    public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri, ByteBuf content, HttpHeaders headers, HttpHeaders trailingHeader) {
        super(httpVersion, method, uri, headers);
        this.content = (ByteBuf) ObjectUtil.checkNotNull(content, "content");
        this.trailingHeader = (HttpHeaders) ObjectUtil.checkNotNull(trailingHeader, "trailingHeader");
    }

    public HttpHeaders trailingHeaders() {
        return this.trailingHeader;
    }

    public ByteBuf content() {
        return this.content;
    }

    public int refCnt() {
        return this.content.refCnt();
    }

    public FullHttpRequest retain() {
        this.content.retain();
        return this;
    }

    public FullHttpRequest retain(int increment) {
        this.content.retain(increment);
        return this;
    }

    public FullHttpRequest touch() {
        this.content.touch();
        return this;
    }

    public FullHttpRequest touch(Object hint) {
        this.content.touch(hint);
        return this;
    }

    public boolean release() {
        return this.content.release();
    }

    public boolean release(int decrement) {
        return this.content.release(decrement);
    }

    public FullHttpRequest setProtocolVersion(HttpVersion version) {
        super.setProtocolVersion(version);
        return this;
    }

    public FullHttpRequest setMethod(HttpMethod method) {
        super.setMethod(method);
        return this;
    }

    public FullHttpRequest setUri(String uri) {
        super.setUri(uri);
        return this;
    }

    public FullHttpRequest copy() {
        return replace(content().copy());
    }

    public FullHttpRequest duplicate() {
        return replace(content().duplicate());
    }

    public FullHttpRequest retainedDuplicate() {
        return replace(content().retainedDuplicate());
    }

    public FullHttpRequest replace(ByteBuf content) {
        return new DefaultFullHttpRequest(protocolVersion(), method(), uri(), content, headers(), trailingHeaders());
    }

    public int hashCode() {
        int hash = this.hash;
        if (hash != 0) {
            return hash;
        }
        if (content().refCnt() != 0) {
            try {
                hash = content().hashCode() + 31;
            } catch (IllegalReferenceCountException e) {
                hash = 31;
            }
        } else {
            hash = 31;
        }
        hash = (((hash * 31) + trailingHeaders().hashCode()) * 31) + super.hashCode();
        this.hash = hash;
        return hash;
    }

    public boolean equals(Object o) {
        if (!(o instanceof DefaultFullHttpRequest)) {
            return false;
        }
        DefaultFullHttpRequest other = (DefaultFullHttpRequest) o;
        if (super.equals(other) && content().equals(other.content()) && trailingHeaders().equals(other.trailingHeaders())) {
            return true;
        }
        return false;
    }

    public String toString() {
        return this;
    }
}
