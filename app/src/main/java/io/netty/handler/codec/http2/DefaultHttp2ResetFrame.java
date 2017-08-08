package io.netty.handler.codec.http2;

import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import io.netty.util.internal.ObjectUtil;

public final class DefaultHttp2ResetFrame extends AbstractHttp2StreamFrame implements Http2ResetFrame {
    private final long errorCode;

    public DefaultHttp2ResetFrame(Http2Error error) {
        this.errorCode = ((Http2Error) ObjectUtil.checkNotNull(error, DownloadRecord.COLUMN_ERROR)).code();
    }

    public DefaultHttp2ResetFrame(long errorCode) {
        this.errorCode = errorCode;
    }

    public DefaultHttp2ResetFrame setStreamId(int streamId) {
        super.setStreamId(streamId);
        return this;
    }

    public String name() {
        return "RST_STREAM";
    }

    public long errorCode() {
        return this.errorCode;
    }

    public String toString() {
        return "DefaultHttp2ResetFrame(stream=" + streamId() + "errorCode=" + this.errorCode + ")";
    }

    public boolean equals(Object o) {
        if (!(o instanceof DefaultHttp2ResetFrame)) {
            return false;
        }
        DefaultHttp2ResetFrame other = (DefaultHttp2ResetFrame) o;
        if (super.equals(o) && this.errorCode == other.errorCode) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (super.hashCode() * 31) + ((int) (this.errorCode ^ (this.errorCode >>> 32)));
    }
}
