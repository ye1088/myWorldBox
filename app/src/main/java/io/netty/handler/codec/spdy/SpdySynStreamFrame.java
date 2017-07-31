package io.netty.handler.codec.spdy;

public interface SpdySynStreamFrame extends SpdyHeadersFrame {
    int associatedStreamId();

    boolean isUnidirectional();

    byte priority();

    SpdySynStreamFrame setAssociatedStreamId(int i);

    SpdySynStreamFrame setInvalid();

    SpdySynStreamFrame setLast(boolean z);

    SpdySynStreamFrame setPriority(byte b);

    SpdySynStreamFrame setStreamId(int i);

    SpdySynStreamFrame setUnidirectional(boolean z);
}
