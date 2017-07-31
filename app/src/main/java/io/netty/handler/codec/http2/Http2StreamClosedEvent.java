package io.netty.handler.codec.http2;

public class Http2StreamClosedEvent extends AbstractHttp2StreamStateEvent {
    public Http2StreamClosedEvent(int streamId) {
        super(streamId);
    }
}
