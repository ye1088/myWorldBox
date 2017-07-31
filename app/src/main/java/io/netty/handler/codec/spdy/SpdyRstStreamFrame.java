package io.netty.handler.codec.spdy;

public interface SpdyRstStreamFrame extends SpdyStreamFrame {
    SpdyRstStreamFrame setLast(boolean z);

    SpdyRstStreamFrame setStatus(SpdyStreamStatus spdyStreamStatus);

    SpdyRstStreamFrame setStreamId(int i);

    SpdyStreamStatus status();
}
