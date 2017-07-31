package io.netty.handler.codec.http2;

public class Http2StreamActiveEvent extends AbstractHttp2StreamStateEvent {
    public Http2StreamActiveEvent(int streamId) {
        super(streamId);
    }
}
