package io.netty.handler.codec.spdy;

public interface SpdySynReplyFrame extends SpdyHeadersFrame {
    SpdySynReplyFrame setInvalid();

    SpdySynReplyFrame setLast(boolean z);

    SpdySynReplyFrame setStreamId(int i);
}
