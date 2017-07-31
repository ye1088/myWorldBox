package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.internal.ObjectUtil;

public final class DefaultHttp2DataFrame extends AbstractHttp2StreamFrame implements Http2DataFrame {
    private final ByteBuf content;
    private final boolean endStream;
    private final int padding;

    public DefaultHttp2DataFrame(ByteBuf content) {
        this(content, false);
    }

    public DefaultHttp2DataFrame(boolean endStream) {
        this(Unpooled.EMPTY_BUFFER, endStream);
    }

    public DefaultHttp2DataFrame(ByteBuf content, boolean endStream) {
        this(content, endStream, 0);
    }

    public DefaultHttp2DataFrame(ByteBuf content, boolean endStream, int padding) {
        this.content = (ByteBuf) ObjectUtil.checkNotNull(content, "content");
        this.endStream = endStream;
        Http2CodecUtil.verifyPadding(padding);
        this.padding = padding;
    }

    public DefaultHttp2DataFrame setStreamId(int streamId) {
        super.setStreamId(streamId);
        return this;
    }

    public String name() {
        return "DATA";
    }

    public boolean isEndStream() {
        return this.endStream;
    }

    public int padding() {
        return this.padding;
    }

    public ByteBuf content() {
        if (this.content.refCnt() > 0) {
            return this.content;
        }
        throw new IllegalReferenceCountException(this.content.refCnt());
    }

    public DefaultHttp2DataFrame copy() {
        return replace(content().copy());
    }

    public DefaultHttp2DataFrame duplicate() {
        return replace(content().duplicate());
    }

    public DefaultHttp2DataFrame retainedDuplicate() {
        return replace(content().retainedDuplicate());
    }

    public DefaultHttp2DataFrame replace(ByteBuf content) {
        return new DefaultHttp2DataFrame(content, this.endStream, this.padding);
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

    public DefaultHttp2DataFrame retain() {
        this.content.retain();
        return this;
    }

    public DefaultHttp2DataFrame retain(int increment) {
        this.content.retain(increment);
        return this;
    }

    public String toString() {
        return "DefaultHttp2DataFrame(streamId=" + streamId() + ", content=" + this.content + ", endStream=" + this.endStream + ", padding=" + this.padding + ")";
    }

    public DefaultHttp2DataFrame touch() {
        this.content.touch();
        return this;
    }

    public DefaultHttp2DataFrame touch(Object hint) {
        this.content.touch(hint);
        return this;
    }

    public boolean equals(Object o) {
        if (!(o instanceof DefaultHttp2DataFrame)) {
            return false;
        }
        DefaultHttp2DataFrame other = (DefaultHttp2DataFrame) o;
        if (super.equals(other) && this.content.equals(other.content()) && this.endStream == other.endStream && this.padding == other.padding) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((super.hashCode() * 31) + this.content.hashCode()) * 31) + (this.endStream ? 0 : 1)) * 31) + this.padding;
    }
}
