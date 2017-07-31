package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.DefaultByteBufHolder;
import io.netty.buffer.Unpooled;

public final class DefaultHttp2GoAwayFrame extends DefaultByteBufHolder implements Http2GoAwayFrame {
    private final long errorCode;
    private int extraStreamIds;
    private int lastStreamId;

    public DefaultHttp2GoAwayFrame(Http2Error error) {
        this(error.code());
    }

    public DefaultHttp2GoAwayFrame(long errorCode) {
        this(errorCode, Unpooled.EMPTY_BUFFER);
    }

    public DefaultHttp2GoAwayFrame(Http2Error error, ByteBuf content) {
        this(error.code(), content);
    }

    public DefaultHttp2GoAwayFrame(long errorCode, ByteBuf content) {
        this(-1, errorCode, content);
    }

    DefaultHttp2GoAwayFrame(int lastStreamId, long errorCode, ByteBuf content) {
        super(content);
        this.errorCode = errorCode;
        this.lastStreamId = lastStreamId;
    }

    public String name() {
        return "GOAWAY";
    }

    public long errorCode() {
        return this.errorCode;
    }

    public int extraStreamIds() {
        return this.extraStreamIds;
    }

    public Http2GoAwayFrame setExtraStreamIds(int extraStreamIds) {
        if (extraStreamIds < 0) {
            throw new IllegalArgumentException("extraStreamIds must be non-negative");
        }
        this.extraStreamIds = extraStreamIds;
        return this;
    }

    public int lastStreamId() {
        return this.lastStreamId;
    }

    public Http2GoAwayFrame copy() {
        return new DefaultHttp2GoAwayFrame(this.lastStreamId, this.errorCode, content().copy());
    }

    public Http2GoAwayFrame duplicate() {
        return (Http2GoAwayFrame) super.duplicate();
    }

    public Http2GoAwayFrame retainedDuplicate() {
        return (Http2GoAwayFrame) super.retainedDuplicate();
    }

    public Http2GoAwayFrame replace(ByteBuf content) {
        return new DefaultHttp2GoAwayFrame(this.errorCode, content).setExtraStreamIds(this.extraStreamIds);
    }

    public Http2GoAwayFrame retain() {
        super.retain();
        return this;
    }

    public Http2GoAwayFrame retain(int increment) {
        super.retain(increment);
        return this;
    }

    public Http2GoAwayFrame touch() {
        super.touch();
        return this;
    }

    public Http2GoAwayFrame touch(Object hint) {
        super.touch(hint);
        return this;
    }

    public boolean equals(Object o) {
        if (!(o instanceof DefaultHttp2GoAwayFrame)) {
            return false;
        }
        DefaultHttp2GoAwayFrame other = (DefaultHttp2GoAwayFrame) o;
        if (super.equals(o) && this.errorCode == other.errorCode && content().equals(other.content()) && this.extraStreamIds == other.extraStreamIds) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((-1230679765 + ((int) (this.errorCode ^ (this.errorCode >>> 32)))) * 31) + content().hashCode()) * 31) + this.extraStreamIds;
    }

    public String toString() {
        return "DefaultHttp2GoAwayFrame(errorCode=" + this.errorCode + ", content=" + content() + ", extraStreamIds=" + this.extraStreamIds + ", lastStreamId=" + this.lastStreamId + ")";
    }
}
