package io.netty.handler.codec.http2;

import io.netty.handler.codec.Headers;
import io.netty.util.AsciiString;
import java.util.Iterator;
import java.util.Map.Entry;

public interface Http2Headers extends Headers<CharSequence, CharSequence, Http2Headers> {

    public enum PseudoHeaderName {
        METHOD(":method"),
        SCHEME(":scheme"),
        AUTHORITY(":authority"),
        PATH(":path"),
        STATUS(":status");
        
        private static final CharSequenceMap<AsciiString> PSEUDO_HEADERS = null;
        private final AsciiString value;

        static {
            PSEUDO_HEADERS = new CharSequenceMap();
            for (PseudoHeaderName pseudoHeader : values()) {
                PSEUDO_HEADERS.add(pseudoHeader.value(), AsciiString.EMPTY_STRING);
            }
        }

        private PseudoHeaderName(String value) {
            this.value = new AsciiString((CharSequence) value);
        }

        public AsciiString value() {
            return this.value;
        }

        public static boolean isPseudoHeader(CharSequence header) {
            return PSEUDO_HEADERS.contains(header);
        }
    }

    Http2Headers authority(CharSequence charSequence);

    CharSequence authority();

    Iterator<Entry<CharSequence, CharSequence>> iterator();

    Http2Headers method(CharSequence charSequence);

    CharSequence method();

    Http2Headers path(CharSequence charSequence);

    CharSequence path();

    Http2Headers scheme(CharSequence charSequence);

    CharSequence scheme();

    Http2Headers status(CharSequence charSequence);

    CharSequence status();
}
