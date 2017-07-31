package io.netty.handler.codec.spdy;

public interface SpdyGoAwayFrame extends SpdyFrame {
    int lastGoodStreamId();

    SpdyGoAwayFrame setLastGoodStreamId(int i);

    SpdyGoAwayFrame setStatus(SpdySessionStatus spdySessionStatus);

    SpdySessionStatus status();
}
