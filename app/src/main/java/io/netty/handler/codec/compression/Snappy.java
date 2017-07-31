package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import org.bytedeco.javacpp.avutil;

public final class Snappy {
    private static final int COPY_1_BYTE_OFFSET = 1;
    private static final int COPY_2_BYTE_OFFSET = 2;
    private static final int COPY_4_BYTE_OFFSET = 3;
    private static final int LITERAL = 0;
    private static final int MAX_HT_SIZE = 16384;
    private static final int MIN_COMPRESSIBLE_BYTES = 15;
    private static final int NOT_ENOUGH_INPUT = -1;
    private static final int PREAMBLE_NOT_FULL = -1;
    private State state = State.READY;
    private byte tag;
    private int written;

    private enum State {
        READY,
        READING_PREAMBLE,
        READING_TAG,
        READING_LITERAL,
        READING_COPY
    }

    public void reset() {
        this.state = State.READY;
        this.tag = (byte) 0;
        this.written = 0;
    }

    public void encode(ByteBuf in, ByteBuf out, int length) {
        int b;
        int i = 0;
        while (true) {
            b = length >>> (i * 7);
            if ((b & -128) == 0) {
                break;
            }
            out.writeByte((b & 127) | 128);
            i++;
        }
        out.writeByte(b);
        int inIndex = in.readerIndex();
        int baseIndex = inIndex;
        short[] table = getHashTable(length);
        int shift = 32 - ((int) Math.floor(Math.log((double) table.length) / Math.log(2.0d)));
        int nextEmit = inIndex;
        if (length - inIndex >= 15) {
            inIndex++;
            int nextHash = hash(in, inIndex, shift);
            loop1:
            while (true) {
                int candidate;
                int insertTail;
                int nextIndex = inIndex;
                int skip = 32;
                while (true) {
                    inIndex = nextIndex;
                    int hash = nextHash;
                    int skip2 = skip + 1;
                    nextIndex = inIndex + (skip >> 5);
                    if (nextIndex > length - 4) {
                        break loop1;
                    }
                    nextHash = hash(in, nextIndex, shift);
                    candidate = baseIndex + table[hash];
                    table[hash] = (short) (inIndex - baseIndex);
                    if (in.getInt(inIndex) == in.getInt(candidate)) {
                        break;
                    }
                    skip = skip2;
                }
                encodeLiteral(in, out, inIndex - nextEmit);
                do {
                    int base = inIndex;
                    int matched = findMatchingLength(in, candidate + 4, inIndex + 4, length) + 4;
                    inIndex += matched;
                    encodeCopy(out, base - candidate, matched);
                    in.readerIndex(in.readerIndex() + matched);
                    insertTail = inIndex - 1;
                    nextEmit = inIndex;
                    if (inIndex >= length - 4) {
                        break loop1;
                    }
                    table[hash(in, insertTail, shift)] = (short) ((inIndex - baseIndex) - 1);
                    int currentHash = hash(in, insertTail + 1, shift);
                    candidate = baseIndex + table[currentHash];
                    table[currentHash] = (short) (inIndex - baseIndex);
                } while (in.getInt(insertTail + 1) == in.getInt(candidate));
                nextHash = hash(in, insertTail + 2, shift);
                inIndex++;
            }
        }
        if (nextEmit < length) {
            encodeLiteral(in, out, length - nextEmit);
        }
    }

    private static int hash(ByteBuf in, int index, int shift) {
        return (in.getInt(index) + 506832829) >>> shift;
    }

    private static short[] getHashTable(int inputSize) {
        int htSize = 256;
        while (htSize < 16384 && htSize < inputSize) {
            htSize <<= 1;
        }
        if (htSize <= 256) {
            return new short[256];
        }
        return new short[16384];
    }

    private static int findMatchingLength(ByteBuf in, int minIndex, int inIndex, int maxIndex) {
        int matched = 0;
        while (inIndex <= maxIndex - 4 && in.getInt(inIndex) == in.getInt(minIndex + matched)) {
            inIndex += 4;
            matched += 4;
        }
        while (inIndex < maxIndex && in.getByte(minIndex + matched) == in.getByte(inIndex)) {
            inIndex++;
            matched++;
        }
        return matched;
    }

    private static int bitsToEncode(int value) {
        int highestOneBit = Integer.highestOneBit(value);
        int bitLength = 0;
        while (true) {
            highestOneBit >>= 1;
            if (highestOneBit == 0) {
                return bitLength;
            }
            bitLength++;
        }
    }

    static void encodeLiteral(ByteBuf in, ByteBuf out, int length) {
        if (length < 61) {
            out.writeByte((length - 1) << 2);
        } else {
            int bytesToEncode = (bitsToEncode(length - 1) / 8) + 1;
            out.writeByte((bytesToEncode + 59) << 2);
            for (int i = 0; i < bytesToEncode; i++) {
                out.writeByte(((length - 1) >> (i * 8)) & 255);
            }
        }
        out.writeBytes(in, length);
    }

    private static void encodeCopyWithOffset(ByteBuf out, int offset, int length) {
        if (length >= 12 || offset >= 2048) {
            out.writeByte(((length - 1) << 2) | 2);
            out.writeByte(offset & 255);
            out.writeByte((offset >> 8) & 255);
            return;
        }
        out.writeByte((((length - 4) << 2) | 1) | ((offset >> 8) << 5));
        out.writeByte(offset & 255);
    }

    private static void encodeCopy(ByteBuf out, int offset, int length) {
        while (length >= 68) {
            encodeCopyWithOffset(out, offset, 64);
            length -= 64;
        }
        if (length > 64) {
            encodeCopyWithOffset(out, offset, 60);
            length -= 60;
        }
        encodeCopyWithOffset(out, offset, length);
    }

    public void decode(ByteBuf in, ByteBuf out) {
        while (in.isReadable()) {
            switch (this.state) {
                case READY:
                    this.state = State.READING_PREAMBLE;
                    break;
                case READING_PREAMBLE:
                    break;
                case READING_TAG:
                    break;
                case READING_LITERAL:
                    int literalWritten = decodeLiteral(this.tag, in, out);
                    if (literalWritten != -1) {
                        this.state = State.READING_TAG;
                        this.written += literalWritten;
                        continue;
                    } else {
                        return;
                    }
                case READING_COPY:
                    int decodeWritten;
                    switch (this.tag & 3) {
                        case 1:
                            decodeWritten = decodeCopyWith1ByteOffset(this.tag, in, out, this.written);
                            if (decodeWritten != -1) {
                                this.state = State.READING_TAG;
                                this.written += decodeWritten;
                                break;
                            }
                            return;
                        case 2:
                            decodeWritten = decodeCopyWith2ByteOffset(this.tag, in, out, this.written);
                            if (decodeWritten != -1) {
                                this.state = State.READING_TAG;
                                this.written += decodeWritten;
                                break;
                            }
                            return;
                        case 3:
                            decodeWritten = decodeCopyWith4ByteOffset(this.tag, in, out, this.written);
                            if (decodeWritten != -1) {
                                this.state = State.READING_TAG;
                                this.written += decodeWritten;
                                break;
                            }
                            return;
                        default:
                            continue;
                    }
                default:
                    continue;
            }
            int uncompressedLength = readPreamble(in);
            if (uncompressedLength != -1) {
                if (uncompressedLength == 0) {
                    this.state = State.READY;
                    return;
                }
                out.ensureWritable(uncompressedLength);
                this.state = State.READING_TAG;
                if (in.isReadable()) {
                    this.tag = in.readByte();
                    switch (this.tag & 3) {
                        case 0:
                            this.state = State.READING_LITERAL;
                            break;
                        case 1:
                        case 2:
                        case 3:
                            this.state = State.READING_COPY;
                            break;
                        default:
                            continue;
                    }
                } else {
                    return;
                }
            }
            return;
        }
    }

    private static int readPreamble(ByteBuf in) {
        int length = 0;
        int byteIndex = 0;
        while (in.isReadable()) {
            int current = in.readUnsignedByte();
            int byteIndex2 = byteIndex + 1;
            length |= (current & 127) << (byteIndex * 7);
            if ((current & 128) == 0) {
                byteIndex = byteIndex2;
                return length;
            } else if (byteIndex2 >= 4) {
                throw new DecompressionException("Preamble is greater than 4 bytes");
            } else {
                byteIndex = byteIndex2;
            }
        }
        return 0;
    }

    static int decodeLiteral(byte tag, ByteBuf in, ByteBuf out) {
        int length;
        in.markReaderIndex();
        switch ((tag >> 2) & 63) {
            case 60:
                if (in.isReadable()) {
                    length = in.readUnsignedByte();
                    break;
                }
                return -1;
            case 61:
                if (in.readableBytes() >= 2) {
                    length = in.readShortLE();
                    break;
                }
                return -1;
            case 62:
                if (in.readableBytes() >= 3) {
                    length = in.readUnsignedMediumLE();
                    break;
                }
                return -1;
            case 63:
                if (in.readableBytes() >= 4) {
                    length = in.readIntLE();
                    break;
                }
                return -1;
            default:
                length = (tag >> 2) & 63;
                break;
        }
        length++;
        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return -1;
        }
        out.writeBytes(in, length);
        return length;
    }

    private static int decodeCopyWith1ByteOffset(byte tag, ByteBuf in, ByteBuf out, int writtenSoFar) {
        if (!in.isReadable()) {
            return -1;
        }
        int initialIndex = out.writerIndex();
        int length = ((tag & 28) >> 2) + 4;
        int offset = (((tag & 224) << 8) >> 5) | in.readUnsignedByte();
        validateOffset(offset, writtenSoFar);
        out.markReaderIndex();
        if (offset < length) {
            for (int copies = length / offset; copies > 0; copies--) {
                out.readerIndex(initialIndex - offset);
                out.readBytes(out, offset);
            }
            if (length % offset != 0) {
                out.readerIndex(initialIndex - offset);
                out.readBytes(out, length % offset);
            }
        } else {
            out.readerIndex(initialIndex - offset);
            out.readBytes(out, length);
        }
        out.resetReaderIndex();
        return length;
    }

    private static int decodeCopyWith2ByteOffset(byte tag, ByteBuf in, ByteBuf out, int writtenSoFar) {
        if (in.readableBytes() < 2) {
            return -1;
        }
        int initialIndex = out.writerIndex();
        int length = ((tag >> 2) & 63) + 1;
        int offset = in.readShortLE();
        validateOffset(offset, writtenSoFar);
        out.markReaderIndex();
        if (offset < length) {
            for (int copies = length / offset; copies > 0; copies--) {
                out.readerIndex(initialIndex - offset);
                out.readBytes(out, offset);
            }
            if (length % offset != 0) {
                out.readerIndex(initialIndex - offset);
                out.readBytes(out, length % offset);
            }
        } else {
            out.readerIndex(initialIndex - offset);
            out.readBytes(out, length);
        }
        out.resetReaderIndex();
        return length;
    }

    private static int decodeCopyWith4ByteOffset(byte tag, ByteBuf in, ByteBuf out, int writtenSoFar) {
        if (in.readableBytes() < 4) {
            return -1;
        }
        int initialIndex = out.writerIndex();
        int length = ((tag >> 2) & 63) + 1;
        int offset = in.readIntLE();
        validateOffset(offset, writtenSoFar);
        out.markReaderIndex();
        if (offset < length) {
            for (int copies = length / offset; copies > 0; copies--) {
                out.readerIndex(initialIndex - offset);
                out.readBytes(out, offset);
            }
            if (length % offset != 0) {
                out.readerIndex(initialIndex - offset);
                out.readBytes(out, length % offset);
            }
        } else {
            out.readerIndex(initialIndex - offset);
            out.readBytes(out, length);
        }
        out.resetReaderIndex();
        return length;
    }

    private static void validateOffset(int offset, int chunkSizeSoFar) {
        if (offset > avutil.FF_LAMBDA_MAX) {
            throw new DecompressionException("Offset exceeds maximum permissible value");
        } else if (offset <= 0) {
            throw new DecompressionException("Offset is less than minimum permissible value");
        } else if (offset > chunkSizeSoFar) {
            throw new DecompressionException("Offset exceeds size of chunk");
        }
    }

    static int calculateChecksum(ByteBuf data) {
        return calculateChecksum(data, data.readerIndex(), data.readableBytes());
    }

    static int calculateChecksum(ByteBuf data, int offset, int length) {
        Crc32c crc32 = new Crc32c();
        try {
            crc32.update(data, offset, length);
            int maskChecksum = maskChecksum((int) crc32.getValue());
            return maskChecksum;
        } finally {
            crc32.reset();
        }
    }

    static void validateChecksum(int expectedChecksum, ByteBuf data) {
        validateChecksum(expectedChecksum, data, data.readerIndex(), data.readableBytes());
    }

    static void validateChecksum(int expectedChecksum, ByteBuf data, int offset, int length) {
        int actualChecksum = calculateChecksum(data, offset, length);
        if (actualChecksum != expectedChecksum) {
            throw new DecompressionException("mismatching checksum: " + Integer.toHexString(actualChecksum) + " (expected: " + Integer.toHexString(expectedChecksum) + ')');
        }
    }

    static int maskChecksum(int checksum) {
        return ((checksum >> 15) | (checksum << 17)) - 1568478504;
    }
}
