package io.netty.handler.codec.http2;

public interface Http2FrameSizePolicy {
    int maxFrameSize();

    void maxFrameSize(int i) throws Http2Exception;
}
