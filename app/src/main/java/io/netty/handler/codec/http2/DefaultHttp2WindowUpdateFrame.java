package io.netty.handler.codec.http2;

import io.netty.util.internal.ObjectUtil;

public class DefaultHttp2WindowUpdateFrame extends AbstractHttp2StreamFrame implements Http2WindowUpdateFrame {
    private final int windowUpdateIncrement;

    public DefaultHttp2WindowUpdateFrame(int windowUpdateIncrement) {
        this.windowUpdateIncrement = ObjectUtil.checkPositive(windowUpdateIncrement, "windowUpdateIncrement");
    }

    public DefaultHttp2WindowUpdateFrame setStreamId(int streamId) {
        super.setStreamId(streamId);
        return this;
    }

    public String name() {
        return "WINDOW_UPDATE";
    }

    public int windowSizeIncrement() {
        return this.windowUpdateIncrement;
    }
}
