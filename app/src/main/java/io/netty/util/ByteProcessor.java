package io.netty.util;

import io.netty.handler.codec.http.HttpConstants;

public interface ByteProcessor {
    public static final ByteProcessor FIND_CR = new IndexOfProcessor((byte) 13);
    public static final ByteProcessor FIND_CRLF = new ByteProcessor() {
        public boolean process(byte value) {
            return (value == (byte) 13 || value == (byte) 10) ? false : true;
        }
    };
    public static final ByteProcessor FIND_LF = new IndexOfProcessor((byte) 10);
    public static final ByteProcessor FIND_LINEAR_WHITESPACE = new ByteProcessor() {
        public boolean process(byte value) {
            return (value == (byte) 32 || value == (byte) 9) ? false : true;
        }
    };
    public static final ByteProcessor FIND_NON_CR = new IndexNotOfProcessor((byte) 13);
    public static final ByteProcessor FIND_NON_CRLF = new ByteProcessor() {
        public boolean process(byte value) {
            return value == (byte) 13 || value == (byte) 10;
        }
    };
    public static final ByteProcessor FIND_NON_LF = new IndexNotOfProcessor((byte) 10);
    public static final ByteProcessor FIND_NON_LINEAR_WHITESPACE = new ByteProcessor() {
        public boolean process(byte value) {
            return value == (byte) 32 || value == (byte) 9;
        }
    };
    public static final ByteProcessor FIND_NON_NUL = new IndexNotOfProcessor((byte) 0);
    public static final ByteProcessor FIND_NUL = new IndexOfProcessor((byte) 0);
    public static final ByteProcessor FIND_SEMI_COLON = new IndexOfProcessor(HttpConstants.SEMICOLON);

    public static class IndexNotOfProcessor implements ByteProcessor {
        private final byte byteToNotFind;

        public IndexNotOfProcessor(byte byteToNotFind) {
            this.byteToNotFind = byteToNotFind;
        }

        public boolean process(byte value) {
            return value == this.byteToNotFind;
        }
    }

    public static class IndexOfProcessor implements ByteProcessor {
        private final byte byteToFind;

        public IndexOfProcessor(byte byteToFind) {
            this.byteToFind = byteToFind;
        }

        public boolean process(byte value) {
            return value != this.byteToFind;
        }
    }

    boolean process(byte b) throws Exception;
}
