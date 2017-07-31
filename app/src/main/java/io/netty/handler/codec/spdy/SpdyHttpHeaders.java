package io.netty.handler.codec.spdy;

import io.netty.util.AsciiString;

public final class SpdyHttpHeaders {

    public static final class Names {
        public static final AsciiString ASSOCIATED_TO_STREAM_ID = new AsciiString((CharSequence) "x-spdy-associated-to-stream-id");
        public static final AsciiString PRIORITY = new AsciiString((CharSequence) "x-spdy-priority");
        public static final AsciiString SCHEME = new AsciiString((CharSequence) "x-spdy-scheme");
        public static final AsciiString STREAM_ID = new AsciiString((CharSequence) "x-spdy-stream-id");

        private Names() {
        }
    }

    private SpdyHttpHeaders() {
    }
}
