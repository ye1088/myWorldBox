package io.netty.handler.codec.spdy;

import io.netty.handler.codec.Headers;
import io.netty.util.AsciiString;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public interface SpdyHeaders extends Headers<CharSequence, CharSequence, SpdyHeaders> {

    public static final class HttpNames {
        public static final AsciiString HOST = new AsciiString((CharSequence) ":host");
        public static final AsciiString METHOD = new AsciiString((CharSequence) ":method");
        public static final AsciiString PATH = new AsciiString((CharSequence) ":path");
        public static final AsciiString SCHEME = new AsciiString((CharSequence) ":scheme");
        public static final AsciiString STATUS = new AsciiString((CharSequence) ":status");
        public static final AsciiString VERSION = new AsciiString((CharSequence) ":version");

        private HttpNames() {
        }
    }

    boolean contains(CharSequence charSequence, CharSequence charSequence2, boolean z);

    List<String> getAllAsString(CharSequence charSequence);

    String getAsString(CharSequence charSequence);

    Iterator<Entry<String, String>> iteratorAsString();
}
