package io.netty.handler.codec.http2.internal.hpack;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.util.AsciiString;
import io.netty.util.ByteProcessor;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.ThrowableUtil;

final class HuffmanDecoder {
    private static final Http2Exception EOS_DECODED = ((Http2Exception) ThrowableUtil.unknownStackTrace(Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - EOS Decoded", new Object[0]), HuffmanDecoder.class, "decode(...)"));
    private static final Http2Exception INVALID_PADDING = ((Http2Exception) ThrowableUtil.unknownStackTrace(Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - Invalid Padding", new Object[0]), HuffmanDecoder.class, "decode(...)"));
    private static final Node ROOT = buildTree(HpackUtil.HUFFMAN_CODES, HpackUtil.HUFFMAN_CODE_LENGTHS);
    private final DecoderProcessor processor;

    private static final class DecoderProcessor implements ByteProcessor {
        private byte[] bytes;
        private int current;
        private int currentBits;
        private int index;
        private final int initialCapacity;
        private Node node;
        private int symbolBits;

        DecoderProcessor(int initialCapacity) {
            this.initialCapacity = ObjectUtil.checkPositive(initialCapacity, "initialCapacity");
        }

        void reset() {
            this.node = HuffmanDecoder.ROOT;
            this.current = 0;
            this.currentBits = 0;
            this.symbolBits = 0;
            this.bytes = new byte[this.initialCapacity];
            this.index = 0;
        }

        public boolean process(byte value) throws Http2Exception {
            this.current = (this.current << 8) | (value & 255);
            this.currentBits += 8;
            this.symbolBits += 8;
            do {
                this.node = this.node.children[(this.current >>> (this.currentBits - 8)) & 255];
                this.currentBits -= this.node.bits;
                if (this.node.isTerminal()) {
                    if (this.node.symbol == 256) {
                        throw HuffmanDecoder.EOS_DECODED;
                    }
                    append(this.node.symbol);
                    this.node = HuffmanDecoder.ROOT;
                    this.symbolBits = this.currentBits;
                }
            } while (this.currentBits >= 8);
            return true;
        }

        AsciiString end() throws Http2Exception {
            while (this.currentBits > 0) {
                this.node = this.node.children[(this.current << (8 - this.currentBits)) & 255];
                if (!this.node.isTerminal() || this.node.bits > this.currentBits) {
                    break;
                } else if (this.node.symbol == 256) {
                    throw HuffmanDecoder.EOS_DECODED;
                } else {
                    this.currentBits -= this.node.bits;
                    append(this.node.symbol);
                    this.node = HuffmanDecoder.ROOT;
                    this.symbolBits = this.currentBits;
                }
            }
            int mask = (1 << this.symbolBits) - 1;
            if (this.symbolBits <= 7 && (this.current & mask) == mask) {
                return new AsciiString(this.bytes, 0, this.index, false);
            }
            throw HuffmanDecoder.INVALID_PADDING;
        }

        private void append(int i) {
            try {
                this.bytes[this.index] = (byte) i;
            } catch (IndexOutOfBoundsException e) {
                byte[] newBytes = new byte[(this.bytes.length + this.initialCapacity)];
                System.arraycopy(this.bytes, 0, newBytes, 0, this.bytes.length);
                this.bytes = newBytes;
                this.bytes[this.index] = (byte) i;
            }
            this.index++;
        }
    }

    private static final class Node {
        static final /* synthetic */ boolean $assertionsDisabled = (!HuffmanDecoder.class.desiredAssertionStatus());
        private final int bits;
        private final Node[] children;
        private final int symbol;

        Node() {
            this.symbol = 0;
            this.bits = 8;
            this.children = new Node[256];
        }

        Node(int symbol, int bits) {
            if ($assertionsDisabled || (bits > 0 && bits <= 8)) {
                this.symbol = symbol;
                this.bits = bits;
                this.children = null;
                return;
            }
            throw new AssertionError();
        }

        private boolean isTerminal() {
            return this.children == null;
        }
    }

    HuffmanDecoder(int initialCapacity) {
        this.processor = new DecoderProcessor(initialCapacity);
    }

    public AsciiString decode(ByteBuf buf, int length) throws Http2Exception {
        this.processor.reset();
        buf.forEachByte(buf.readerIndex(), length, this.processor);
        buf.skipBytes(length);
        return this.processor.end();
    }

    private static Node buildTree(int[] codes, byte[] lengths) {
        Node root = new Node();
        for (int i = 0; i < codes.length; i++) {
            insert(root, i, codes[i], lengths[i]);
        }
        return root;
    }

    private static void insert(Node root, int symbol, int code, byte length) {
        Node current = root;
        while (length > (byte) 8) {
            if (current.isTerminal()) {
                throw new IllegalStateException("invalid Huffman code: prefix not unique");
            }
            length = (byte) (length - 8);
            int i = (code >>> length) & 255;
            if (current.children[i] == null) {
                current.children[i] = new Node();
            }
            current = current.children[i];
        }
        Node terminal = new Node(symbol, length);
        int shift = 8 - length;
        int start = (code << shift) & 255;
        int end = 1 << shift;
        for (i = start; i < start + end; i++) {
            current.children[i] = terminal;
        }
    }
}
