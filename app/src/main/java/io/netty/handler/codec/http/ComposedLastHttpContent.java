package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderResult;

final class ComposedLastHttpContent implements LastHttpContent {
    private DecoderResult result;
    private final HttpHeaders trailingHeaders;

    ComposedLastHttpContent(HttpHeaders trailingHeaders) {
        this.trailingHeaders = trailingHeaders;
    }

    public HttpHeaders trailingHeaders() {
        return this.trailingHeaders;
    }

    public LastHttpContent copy() {
        LastHttpContent content = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER);
        content.trailingHeaders().set(trailingHeaders());
        return content;
    }

    public LastHttpContent duplicate() {
        return copy();
    }

    public LastHttpContent retainedDuplicate() {
        return copy();
    }

    public LastHttpContent replace(ByteBuf content) {
        LastHttpContent dup = new DefaultLastHttpContent(content);
        dup.trailingHeaders().setAll(trailingHeaders());
        return dup;
    }

    public LastHttpContent retain(int increment) {
        return this;
    }

    public LastHttpContent retain() {
        return this;
    }

    public LastHttpContent touch() {
        return this;
    }

    public LastHttpContent touch(Object hint) {
        return this;
    }

    public ByteBuf content() {
        return Unpooled.EMPTY_BUFFER;
    }

    public DecoderResult decoderResult() {
        return this.result;
    }

    public DecoderResult getDecoderResult() {
        return decoderResult();
    }

    public void setDecoderResult(DecoderResult result) {
        this.result = result;
    }

    public int refCnt() {
        return 1;
    }

    public boolean release() {
        return false;
    }

    public boolean release(int decrement) {
        return false;
    }
}
