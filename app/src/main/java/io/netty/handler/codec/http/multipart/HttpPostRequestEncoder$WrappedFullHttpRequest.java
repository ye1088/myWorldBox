package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.EmptyHttpHeaders;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder.WrappedHttpRequest;

final class HttpPostRequestEncoder$WrappedFullHttpRequest extends WrappedHttpRequest implements FullHttpRequest {
    private final HttpContent content;

    private HttpPostRequestEncoder$WrappedFullHttpRequest(HttpRequest request, HttpContent content) {
        super(request);
        this.content = content;
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
        DefaultFullHttpRequest duplicate = new DefaultFullHttpRequest(protocolVersion(), method(), uri(), content);
        duplicate.headers().set(headers());
        duplicate.trailingHeaders().set(trailingHeaders());
        return duplicate;
    }

    public FullHttpRequest retain(int increment) {
        this.content.retain(increment);
        return this;
    }

    public FullHttpRequest retain() {
        this.content.retain();
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

    public ByteBuf content() {
        return this.content.content();
    }

    public HttpHeaders trailingHeaders() {
        if (this.content instanceof LastHttpContent) {
            return ((LastHttpContent) this.content).trailingHeaders();
        }
        return EmptyHttpHeaders.INSTANCE;
    }

    public int refCnt() {
        return this.content.refCnt();
    }

    public boolean release() {
        return this.content.release();
    }

    public boolean release(int decrement) {
        return this.content.release(decrement);
    }
}
