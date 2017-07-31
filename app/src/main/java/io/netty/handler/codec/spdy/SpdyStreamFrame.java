package io.netty.handler.codec.spdy;

public interface SpdyStreamFrame extends SpdyFrame {
    boolean isLast();

    SpdyStreamFrame setLast(boolean z);

    SpdyStreamFrame setStreamId(int i);

    int streamId();
}
