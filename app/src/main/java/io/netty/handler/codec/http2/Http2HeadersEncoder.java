package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;

public interface Http2HeadersEncoder {
    public static final SensitivityDetector ALWAYS_SENSITIVE = new SensitivityDetector() {
        public boolean isSensitive(CharSequence name, CharSequence value) {
            return true;
        }
    };
    public static final SensitivityDetector NEVER_SENSITIVE = new SensitivityDetector() {
        public boolean isSensitive(CharSequence name, CharSequence value) {
            return false;
        }
    };

    public interface Configuration {
        Http2HeaderTable headerTable();
    }

    public interface SensitivityDetector {
        boolean isSensitive(CharSequence charSequence, CharSequence charSequence2);
    }

    Configuration configuration();

    void encodeHeaders(Http2Headers http2Headers, ByteBuf byteBuf) throws Http2Exception;
}
