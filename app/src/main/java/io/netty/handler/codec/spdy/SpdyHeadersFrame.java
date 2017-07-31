package io.netty.handler.codec.spdy;

public interface SpdyHeadersFrame extends SpdyStreamFrame {
    SpdyHeaders headers();

    boolean isInvalid();

    boolean isTruncated();

    SpdyHeadersFrame setInvalid();

    SpdyHeadersFrame setLast(boolean z);

    SpdyHeadersFrame setStreamId(int i);

    SpdyHeadersFrame setTruncated();
}
