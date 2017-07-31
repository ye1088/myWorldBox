package io.netty.handler.codec.http2;

public interface Http2StreamFrame extends Http2Frame {
    Http2StreamFrame setStreamId(int i);

    int streamId();
}
