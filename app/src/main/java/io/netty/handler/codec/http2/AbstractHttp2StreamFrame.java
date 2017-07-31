package io.netty.handler.codec.http2;

import io.netty.util.internal.ObjectUtil;

public abstract class AbstractHttp2StreamFrame implements Http2StreamFrame {
    private int streamId = -1;

    public AbstractHttp2StreamFrame setStreamId(int streamId) {
        if (this.streamId != -1) {
            throw new IllegalStateException("Stream identifier may only be set once.");
        }
        this.streamId = ObjectUtil.checkPositiveOrZero(streamId, "streamId");
        return this;
    }

    public int streamId() {
        return this.streamId;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Http2StreamFrame)) {
            return false;
        }
        if (this.streamId == ((Http2StreamFrame) o).streamId()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.streamId;
    }
}
