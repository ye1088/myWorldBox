package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;

final class HttpPostBodyUtil {
    public static final String DEFAULT_BINARY_CONTENT_TYPE = "application/octet-stream";
    public static final String DEFAULT_TEXT_CONTENT_TYPE = "text/plain";
    public static final int chunkSize = 8096;

    static class SeekAheadNoBackArrayException extends Exception {
        private static final long serialVersionUID = -630418804938699495L;

        SeekAheadNoBackArrayException() {
        }
    }

    static class SeekAheadOptimize {
        ByteBuf buffer;
        byte[] bytes;
        int limit;
        int origPos;
        int pos;
        int readerIndex;

        SeekAheadOptimize(ByteBuf buffer) throws SeekAheadNoBackArrayException {
            if (buffer.hasArray()) {
                this.buffer = buffer;
                this.bytes = buffer.array();
                this.readerIndex = buffer.readerIndex();
                int arrayOffset = buffer.arrayOffset() + this.readerIndex;
                this.pos = arrayOffset;
                this.origPos = arrayOffset;
                this.limit = buffer.arrayOffset() + buffer.writerIndex();
                return;
            }
            throw new SeekAheadNoBackArrayException();
        }

        void setReadPosition(int minus) {
            this.pos -= minus;
            this.readerIndex = getReadPosition(this.pos);
            this.buffer.readerIndex(this.readerIndex);
        }

        int getReadPosition(int index) {
            return (index - this.origPos) + this.readerIndex;
        }

        void clear() {
            this.buffer = null;
            this.bytes = null;
            this.limit = 0;
            this.pos = 0;
            this.readerIndex = 0;
        }
    }

    public enum TransferEncodingMechanism {
        BIT7("7bit"),
        BIT8("8bit"),
        BINARY("binary");
        
        private final String value;

        private TransferEncodingMechanism(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }

        public String toString() {
            return this.value;
        }
    }

    private HttpPostBodyUtil() {
    }

    static int findNonWhitespace(String sb, int offset) {
        int result = offset;
        while (result < sb.length() && Character.isWhitespace(sb.charAt(result))) {
            result++;
        }
        return result;
    }

    static int findWhitespace(String sb, int offset) {
        int result = offset;
        while (result < sb.length() && !Character.isWhitespace(sb.charAt(result))) {
            result++;
        }
        return result;
    }

    static int findEndOfString(String sb) {
        int result = sb.length();
        while (result > 0 && Character.isWhitespace(sb.charAt(result - 1))) {
            result--;
        }
        return result;
    }
}
