package io.netty.handler.codec.rtsp;

import io.netty.handler.codec.http.HttpVersion;
import org.apache.tools.ant.types.selectors.ContainsSelector;

public final class RtspVersions {
    public static final HttpVersion RTSP_1_0 = new HttpVersion("RTSP", 1, 0, true);

    public static HttpVersion valueOf(String text) {
        if (text == null) {
            throw new NullPointerException(ContainsSelector.CONTAINS_KEY);
        }
        text = text.trim().toUpperCase();
        if ("RTSP/1.0".equals(text)) {
            return RTSP_1_0;
        }
        return new HttpVersion(text, true);
    }

    private RtspVersions() {
    }
}
