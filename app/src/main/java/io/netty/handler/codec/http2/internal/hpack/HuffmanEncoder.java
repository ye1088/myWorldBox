package io.netty.handler.codec.http2.internal.hpack;

import io.netty.buffer.ByteBuf;
import io.netty.util.AsciiString;
import io.netty.util.ByteProcessor;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;

final class HuffmanEncoder {
    private final int[] codes;
    private final EncodeProcessor encodeProcessor;
    private final EncodedLengthProcessor encodedLengthProcessor;
    private final byte[] lengths;

    private final class EncodeProcessor implements ByteProcessor {
        private long current;
        private int n;
        ByteBuf out;

        private EncodeProcessor() {
        }

        public boolean process(byte value) {
            int b = value & 255;
            int nbits = HuffmanEncoder.this.lengths[b];
            this.current <<= nbits;
            this.current |= (long) HuffmanEncoder.this.codes[b];
            this.n += nbits;
            while (this.n >= 8) {
                this.n -= 8;
                this.out.writeByte((int) (this.current >> this.n));
            }
            return true;
        }

        void end() {
            try {
                if (this.n > 0) {
                    this.current <<= 8 - this.n;
                    this.current |= (long) (255 >>> this.n);
                    this.out.writeByte((int) this.current);
                }
                this.out = null;
                this.current = 0;
                this.n = 0;
            } catch (Throwable th) {
                this.out = null;
                this.current = 0;
                this.n = 0;
            }
        }
    }

    private final class EncodedLengthProcessor implements ByteProcessor {
        private long len;

        private EncodedLengthProcessor() {
        }

        public boolean process(byte value) {
            this.len += (long) HuffmanEncoder.this.lengths[value & 255];
            return true;
        }

        void reset() {
            this.len = 0;
        }

        int length() {
            return (int) ((this.len + 7) >> 3);
        }
    }

    HuffmanEncoder() {
        this(HpackUtil.HUFFMAN_CODES, HpackUtil.HUFFMAN_CODE_LENGTHS);
    }

    private HuffmanEncoder(int[] codes, byte[] lengths) {
        this.encodedLengthProcessor = new EncodedLengthProcessor();
        this.encodeProcessor = new EncodeProcessor();
        this.codes = codes;
        this.lengths = lengths;
    }

    public void encode(ByteBuf out, CharSequence data) {
        ObjectUtil.checkNotNull(out, "out");
        if (data instanceof AsciiString) {
            AsciiString string = (AsciiString) data;
            try {
                this.encodeProcessor.out = out;
                string.forEachByte(this.encodeProcessor);
            } catch (Exception e) {
                PlatformDependent.throwException(e);
            } finally {
                this.encodeProcessor.end();
            }
            return;
        }
        encodeSlowPath(out, data);
    }

    private void encodeSlowPath(ByteBuf out, CharSequence data) {
        long current = 0;
        int n = 0;
        for (int i = 0; i < data.length(); i++) {
            int b = data.charAt(i) & 255;
            int code = this.codes[b];
            int nbits = this.lengths[b];
            current = (current << nbits) | ((long) code);
            n += nbits;
            while (n >= 8) {
                n -= 8;
                out.writeByte((int) (current >> n));
            }
        }
        if (n > 0) {
            out.writeByte((int) ((current << (8 - n)) | ((long) (255 >>> n))));
        }
    }

    public int getEncodedLength(CharSequence data) {
        if (!(data instanceof AsciiString)) {
            return getEncodedLengthSlowPath(data);
        }
        AsciiString string = (AsciiString) data;
        try {
            this.encodedLengthProcessor.reset();
            string.forEachByte(this.encodedLengthProcessor);
            return this.encodedLengthProcessor.length();
        } catch (Exception e) {
            PlatformDependent.throwException(e);
            return -1;
        }
    }

    private int getEncodedLengthSlowPath(CharSequence data) {
        long len = 0;
        for (int i = 0; i < data.length(); i++) {
            len += (long) this.lengths[data.charAt(i) & 255];
        }
        return (int) ((7 + len) >> 3);
    }
}
