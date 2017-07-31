package com.google.protobuf;

import com.google.protobuf.MessageLite.Builder;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CodedInputStream {
    private static final int BUFFER_SIZE = 4096;
    private static final int DEFAULT_RECURSION_LIMIT = 64;
    private static final int DEFAULT_SIZE_LIMIT = 67108864;
    private final byte[] buffer;
    private final boolean bufferIsImmutable;
    private int bufferPos;
    private int bufferSize;
    private int bufferSizeAfterLimit;
    private int currentLimit;
    private boolean enableAliasing;
    private final InputStream input;
    private int lastTag;
    private int recursionDepth;
    private int recursionLimit;
    private RefillCallback refillCallback;
    private int sizeLimit;
    private int totalBytesRetired;

    public static CodedInputStream newInstance(InputStream input) {
        return new CodedInputStream(input);
    }

    public static CodedInputStream newInstance(byte[] buf) {
        return newInstance(buf, 0, buf.length);
    }

    public static CodedInputStream newInstance(byte[] buf, int off, int len) {
        CodedInputStream result = new CodedInputStream(buf, off, len);
        try {
            result.pushLimit(len);
            return result;
        } catch (InvalidProtocolBufferException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public static CodedInputStream newInstance(ByteBuffer buf) {
        if (buf.hasArray()) {
            return newInstance(buf.array(), buf.arrayOffset() + buf.position(), buf.remaining());
        }
        ByteBuffer temp = buf.duplicate();
        byte[] buffer = new byte[temp.remaining()];
        temp.get(buffer);
        return newInstance(buffer);
    }

    static CodedInputStream newInstance(LiteralByteString byteString) {
        CodedInputStream result = new CodedInputStream(byteString);
        try {
            result.pushLimit(byteString.size());
            return result;
        } catch (InvalidProtocolBufferException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public int readTag() throws IOException {
        if (isAtEnd()) {
            this.lastTag = 0;
            return 0;
        }
        this.lastTag = readRawVarint32();
        if (WireFormat.getTagFieldNumber(this.lastTag) != 0) {
            return this.lastTag;
        }
        throw InvalidProtocolBufferException.invalidTag();
    }

    public void checkLastTagWas(int value) throws InvalidProtocolBufferException {
        if (this.lastTag != value) {
            throw InvalidProtocolBufferException.invalidEndTag();
        }
    }

    public int getLastTag() {
        return this.lastTag;
    }

    public boolean skipField(int tag) throws IOException {
        switch (WireFormat.getTagWireType(tag)) {
            case 0:
                skipRawVarint();
                return true;
            case 1:
                skipRawBytes(8);
                return true;
            case 2:
                skipRawBytes(readRawVarint32());
                return true;
            case 3:
                skipMessage();
                checkLastTagWas(WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4));
                return true;
            case 4:
                return false;
            case 5:
                skipRawBytes(4);
                return true;
            default:
                throw InvalidProtocolBufferException.invalidWireType();
        }
    }

    public boolean skipField(int tag, CodedOutputStream output) throws IOException {
        long value;
        switch (WireFormat.getTagWireType(tag)) {
            case 0:
                value = readInt64();
                output.writeRawVarint32(tag);
                output.writeUInt64NoTag(value);
                return true;
            case 1:
                value = readRawLittleEndian64();
                output.writeRawVarint32(tag);
                output.writeFixed64NoTag(value);
                return true;
            case 2:
                ByteString value2 = readBytes();
                output.writeRawVarint32(tag);
                output.writeBytesNoTag(value2);
                return true;
            case 3:
                output.writeRawVarint32(tag);
                skipMessage(output);
                int endtag = WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4);
                checkLastTagWas(endtag);
                output.writeRawVarint32(endtag);
                return true;
            case 4:
                return false;
            case 5:
                int value3 = readRawLittleEndian32();
                output.writeRawVarint32(tag);
                output.writeFixed32NoTag(value3);
                return true;
            default:
                throw InvalidProtocolBufferException.invalidWireType();
        }
    }

    public void skipMessage() throws IOException {
        int tag;
        do {
            tag = readTag();
            if (tag == 0) {
                return;
            }
        } while (skipField(tag));
    }

    public void skipMessage(CodedOutputStream output) throws IOException {
        int tag;
        do {
            tag = readTag();
            if (tag == 0) {
                return;
            }
        } while (skipField(tag, output));
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readRawLittleEndian64());
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readRawLittleEndian32());
    }

    public long readUInt64() throws IOException {
        return readRawVarint64();
    }

    public long readInt64() throws IOException {
        return readRawVarint64();
    }

    public int readInt32() throws IOException {
        return readRawVarint32();
    }

    public long readFixed64() throws IOException {
        return readRawLittleEndian64();
    }

    public int readFixed32() throws IOException {
        return readRawLittleEndian32();
    }

    public boolean readBool() throws IOException {
        return readRawVarint64() != 0;
    }

    public String readString() throws IOException {
        int size = readRawVarint32();
        if (size <= this.bufferSize - this.bufferPos && size > 0) {
            String result = new String(this.buffer, this.bufferPos, size, "UTF-8");
            this.bufferPos += size;
            return result;
        } else if (size == 0) {
            return "";
        } else {
            return new String(readRawBytesSlowPath(size), "UTF-8");
        }
    }

    public String readStringRequireUtf8() throws IOException {
        byte[] bytes;
        int size = readRawVarint32();
        int pos = this.bufferPos;
        if (size <= this.bufferSize - pos && size > 0) {
            bytes = this.buffer;
            this.bufferPos = pos + size;
        } else if (size == 0) {
            return "";
        } else {
            bytes = readRawBytesSlowPath(size);
            pos = 0;
        }
        if (Utf8.isValidUtf8(bytes, pos, pos + size)) {
            return new String(bytes, pos, size, "UTF-8");
        }
        throw InvalidProtocolBufferException.invalidUtf8();
    }

    public void readGroup(int fieldNumber, Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
        if (this.recursionDepth >= this.recursionLimit) {
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }
        this.recursionDepth++;
        builder.mergeFrom(this, extensionRegistry);
        checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
        this.recursionDepth--;
    }

    public <T extends MessageLite> T readGroup(int fieldNumber, Parser<T> parser, ExtensionRegistryLite extensionRegistry) throws IOException {
        if (this.recursionDepth >= this.recursionLimit) {
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }
        this.recursionDepth++;
        MessageLite result = (MessageLite) parser.parsePartialFrom(this, extensionRegistry);
        checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
        this.recursionDepth--;
        return result;
    }

    @Deprecated
    public void readUnknownGroup(int fieldNumber, Builder builder) throws IOException {
        readGroup(fieldNumber, builder, null);
    }

    public void readMessage(Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
        int length = readRawVarint32();
        if (this.recursionDepth >= this.recursionLimit) {
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }
        int oldLimit = pushLimit(length);
        this.recursionDepth++;
        builder.mergeFrom(this, extensionRegistry);
        checkLastTagWas(0);
        this.recursionDepth--;
        popLimit(oldLimit);
    }

    public <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistry) throws IOException {
        int length = readRawVarint32();
        if (this.recursionDepth >= this.recursionLimit) {
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }
        int oldLimit = pushLimit(length);
        this.recursionDepth++;
        MessageLite result = (MessageLite) parser.parsePartialFrom(this, extensionRegistry);
        checkLastTagWas(0);
        this.recursionDepth--;
        popLimit(oldLimit);
        return result;
    }

    public ByteString readBytes() throws IOException {
        int size = readRawVarint32();
        if (size <= this.bufferSize - this.bufferPos && size > 0) {
            ByteString result = (this.bufferIsImmutable && this.enableAliasing) ? new BoundedByteString(this.buffer, this.bufferPos, size) : ByteString.copyFrom(this.buffer, this.bufferPos, size);
            this.bufferPos += size;
            return result;
        } else if (size == 0) {
            return ByteString.EMPTY;
        } else {
            return new LiteralByteString(readRawBytesSlowPath(size));
        }
    }

    public byte[] readByteArray() throws IOException {
        int size = readRawVarint32();
        if (size > this.bufferSize - this.bufferPos || size <= 0) {
            return readRawBytesSlowPath(size);
        }
        byte[] result = Arrays.copyOfRange(this.buffer, this.bufferPos, this.bufferPos + size);
        this.bufferPos += size;
        return result;
    }

    public ByteBuffer readByteBuffer() throws IOException {
        int size = readRawVarint32();
        if (size <= this.bufferSize - this.bufferPos && size > 0) {
            ByteBuffer result = (this.input == null && !this.bufferIsImmutable && this.enableAliasing) ? ByteBuffer.wrap(this.buffer, this.bufferPos, size).slice() : ByteBuffer.wrap(Arrays.copyOfRange(this.buffer, this.bufferPos, this.bufferPos + size));
            this.bufferPos += size;
            return result;
        } else if (size == 0) {
            return Internal.EMPTY_BYTE_BUFFER;
        } else {
            return ByteBuffer.wrap(readRawBytesSlowPath(size));
        }
    }

    public int readUInt32() throws IOException {
        return readRawVarint32();
    }

    public int readEnum() throws IOException {
        return readRawVarint32();
    }

    public int readSFixed32() throws IOException {
        return readRawLittleEndian32();
    }

    public long readSFixed64() throws IOException {
        return readRawLittleEndian64();
    }

    public int readSInt32() throws IOException {
        return decodeZigZag32(readRawVarint32());
    }

    public long readSInt64() throws IOException {
        return decodeZigZag64(readRawVarint64());
    }

    public int readRawVarint32() throws IOException {
        int pos = this.bufferPos;
        if (this.bufferSize != pos) {
            byte[] buffer = this.buffer;
            int pos2 = pos + 1;
            int x = buffer[pos];
            if (x >= 0) {
                this.bufferPos = pos2;
                pos = pos2;
                return x;
            } else if (this.bufferSize - pos2 < 9) {
                pos = pos2;
            } else {
                pos = pos2 + 1;
                x ^= buffer[pos2] << 7;
                if (((long) x) < 0) {
                    x = (int) (((long) x) ^ -128);
                } else {
                    pos2 = pos + 1;
                    x ^= buffer[pos] << 14;
                    if (((long) x) >= 0) {
                        x = (int) (((long) x) ^ 16256);
                        pos = pos2;
                    } else {
                        pos = pos2 + 1;
                        x ^= buffer[pos2] << 21;
                        if (((long) x) < 0) {
                            x = (int) (((long) x) ^ -2080896);
                        } else {
                            pos2 = pos + 1;
                            int y = buffer[pos];
                            x = (int) (((long) (x ^ (y << 28))) ^ 266354560);
                            if (y < 0) {
                                pos = pos2 + 1;
                                if (buffer[pos2] < (byte) 0) {
                                    pos2 = pos + 1;
                                    if (buffer[pos] < (byte) 0) {
                                        pos = pos2 + 1;
                                        if (buffer[pos2] < (byte) 0) {
                                            pos2 = pos + 1;
                                            if (buffer[pos] < (byte) 0) {
                                                pos = pos2 + 1;
                                                if (buffer[pos2] < (byte) 0) {
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            pos = pos2;
                        }
                    }
                }
                this.bufferPos = pos;
                return x;
            }
        }
        return (int) readRawVarint64SlowPath();
    }

    private void skipRawVarint() throws IOException {
        if (this.bufferSize - this.bufferPos >= 10) {
            byte[] buffer = this.buffer;
            int i = 0;
            int pos = this.bufferPos;
            while (i < 10) {
                int pos2 = pos + 1;
                if (buffer[pos] >= (byte) 0) {
                    this.bufferPos = pos2;
                    return;
                } else {
                    i++;
                    pos = pos2;
                }
            }
        }
        skipRawVarintSlowPath();
    }

    private void skipRawVarintSlowPath() throws IOException {
        int i = 0;
        while (i < 10) {
            if (readRawByte() < (byte) 0) {
                i++;
            } else {
                return;
            }
        }
        throw InvalidProtocolBufferException.malformedVarint();
    }

    static int readRawVarint32(InputStream input) throws IOException {
        int firstByte = input.read();
        if (firstByte != -1) {
            return readRawVarint32(firstByte, input);
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    public static int readRawVarint32(int firstByte, InputStream input) throws IOException {
        if ((firstByte & 128) == 0) {
            return firstByte;
        }
        int result = firstByte & 127;
        int offset = 7;
        while (offset < 32) {
            int b = input.read();
            if (b == -1) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            result |= (b & 127) << offset;
            if ((b & 128) == 0) {
                return result;
            }
            offset += 7;
        }
        while (offset < 64) {
            b = input.read();
            if (b == -1) {
                throw InvalidProtocolBufferException.truncatedMessage();
            } else if ((b & 128) == 0) {
                return result;
            } else {
                offset += 7;
            }
        }
        throw InvalidProtocolBufferException.malformedVarint();
    }

    public long readRawVarint64() throws IOException {
        int pos = this.bufferPos;
        if (this.bufferSize != pos) {
            byte[] buffer = this.buffer;
            int pos2 = pos + 1;
            int y = buffer[pos];
            if (y >= 0) {
                this.bufferPos = pos2;
                pos = pos2;
                return (long) y;
            } else if (this.bufferSize - pos2 < 9) {
                pos = pos2;
            } else {
                pos = pos2 + 1;
                long x = (long) ((buffer[pos2] << 7) ^ y);
                if (x < 0) {
                    x ^= -128;
                } else {
                    pos2 = pos + 1;
                    x ^= (long) (buffer[pos] << 14);
                    if (x >= 0) {
                        x ^= 16256;
                        pos = pos2;
                    } else {
                        pos = pos2 + 1;
                        x ^= (long) (buffer[pos2] << 21);
                        if (x < 0) {
                            x ^= -2080896;
                        } else {
                            pos2 = pos + 1;
                            x ^= ((long) buffer[pos]) << 28;
                            if (x >= 0) {
                                x ^= 266354560;
                                pos = pos2;
                            } else {
                                pos = pos2 + 1;
                                x ^= ((long) buffer[pos2]) << 35;
                                if (x < 0) {
                                    x ^= -34093383808L;
                                } else {
                                    pos2 = pos + 1;
                                    x ^= ((long) buffer[pos]) << 42;
                                    if (x >= 0) {
                                        x ^= 4363953127296L;
                                        pos = pos2;
                                    } else {
                                        pos = pos2 + 1;
                                        x ^= ((long) buffer[pos2]) << 49;
                                        if (x < 0) {
                                            x ^= -558586000294016L;
                                        } else {
                                            pos2 = pos + 1;
                                            x = (x ^ (((long) buffer[pos]) << 56)) ^ 71499008037633920L;
                                            if (x < 0) {
                                                pos = pos2 + 1;
                                                if (((long) buffer[pos2]) < 0) {
                                                }
                                            } else {
                                                pos = pos2;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                this.bufferPos = pos;
                return x;
            }
        }
        return readRawVarint64SlowPath();
    }

    long readRawVarint64SlowPath() throws IOException {
        long result = 0;
        for (int shift = 0; shift < 64; shift += 7) {
            byte b = readRawByte();
            result |= ((long) (b & 127)) << shift;
            if ((b & 128) == 0) {
                return result;
            }
        }
        throw InvalidProtocolBufferException.malformedVarint();
    }

    public int readRawLittleEndian32() throws IOException {
        int pos = this.bufferPos;
        if (this.bufferSize - pos < 4) {
            refillBuffer(4);
            pos = this.bufferPos;
        }
        byte[] buffer = this.buffer;
        this.bufferPos = pos + 4;
        return (((buffer[pos] & 255) | ((buffer[pos + 1] & 255) << 8)) | ((buffer[pos + 2] & 255) << 16)) | ((buffer[pos + 3] & 255) << 24);
    }

    public long readRawLittleEndian64() throws IOException {
        int pos = this.bufferPos;
        if (this.bufferSize - pos < 8) {
            refillBuffer(8);
            pos = this.bufferPos;
        }
        byte[] buffer = this.buffer;
        this.bufferPos = pos + 8;
        return (((((((((long) buffer[pos]) & 255) | ((((long) buffer[pos + 1]) & 255) << 8)) | ((((long) buffer[pos + 2]) & 255) << 16)) | ((((long) buffer[pos + 3]) & 255) << 24)) | ((((long) buffer[pos + 4]) & 255) << 32)) | ((((long) buffer[pos + 5]) & 255) << 40)) | ((((long) buffer[pos + 6]) & 255) << 48)) | ((((long) buffer[pos + 7]) & 255) << 56);
    }

    public static int decodeZigZag32(int n) {
        return (n >>> 1) ^ (-(n & 1));
    }

    public static long decodeZigZag64(long n) {
        return (n >>> 1) ^ (-(1 & n));
    }

    private CodedInputStream(byte[] buffer, int off, int len) {
        this.enableAliasing = false;
        this.currentLimit = Integer.MAX_VALUE;
        this.recursionLimit = 64;
        this.sizeLimit = 67108864;
        this.refillCallback = null;
        this.buffer = buffer;
        this.bufferSize = off + len;
        this.bufferPos = off;
        this.totalBytesRetired = -off;
        this.input = null;
        this.bufferIsImmutable = false;
    }

    private CodedInputStream(InputStream input) {
        this.enableAliasing = false;
        this.currentLimit = Integer.MAX_VALUE;
        this.recursionLimit = 64;
        this.sizeLimit = 67108864;
        this.refillCallback = null;
        this.buffer = new byte[4096];
        this.bufferSize = 0;
        this.bufferPos = 0;
        this.totalBytesRetired = 0;
        this.input = input;
        this.bufferIsImmutable = false;
    }

    private CodedInputStream(LiteralByteString byteString) {
        this.enableAliasing = false;
        this.currentLimit = Integer.MAX_VALUE;
        this.recursionLimit = 64;
        this.sizeLimit = 67108864;
        this.refillCallback = null;
        this.buffer = byteString.bytes;
        this.bufferPos = byteString.getOffsetIntoBytes();
        this.bufferSize = this.bufferPos + byteString.size();
        this.totalBytesRetired = -this.bufferPos;
        this.input = null;
        this.bufferIsImmutable = true;
    }

    public void enableAliasing(boolean enabled) {
        this.enableAliasing = enabled;
    }

    public int setRecursionLimit(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("Recursion limit cannot be negative: " + limit);
        }
        int oldLimit = this.recursionLimit;
        this.recursionLimit = limit;
        return oldLimit;
    }

    public int setSizeLimit(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("Size limit cannot be negative: " + limit);
        }
        int oldLimit = this.sizeLimit;
        this.sizeLimit = limit;
        return oldLimit;
    }

    public void resetSizeCounter() {
        this.totalBytesRetired = -this.bufferPos;
    }

    public int pushLimit(int byteLimit) throws InvalidProtocolBufferException {
        if (byteLimit < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        byteLimit += this.totalBytesRetired + this.bufferPos;
        int oldLimit = this.currentLimit;
        if (byteLimit > oldLimit) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        this.currentLimit = byteLimit;
        recomputeBufferSizeAfterLimit();
        return oldLimit;
    }

    private void recomputeBufferSizeAfterLimit() {
        this.bufferSize += this.bufferSizeAfterLimit;
        int bufferEnd = this.totalBytesRetired + this.bufferSize;
        if (bufferEnd > this.currentLimit) {
            this.bufferSizeAfterLimit = bufferEnd - this.currentLimit;
            this.bufferSize -= this.bufferSizeAfterLimit;
            return;
        }
        this.bufferSizeAfterLimit = 0;
    }

    public void popLimit(int oldLimit) {
        this.currentLimit = oldLimit;
        recomputeBufferSizeAfterLimit();
    }

    public int getBytesUntilLimit() {
        if (this.currentLimit == Integer.MAX_VALUE) {
            return -1;
        }
        return this.currentLimit - (this.totalBytesRetired + this.bufferPos);
    }

    public boolean isAtEnd() throws IOException {
        return this.bufferPos == this.bufferSize && !tryRefillBuffer(1);
    }

    public int getTotalBytesRead() {
        return this.totalBytesRetired + this.bufferPos;
    }

    private void ensureAvailable(int n) throws IOException {
        if (this.bufferSize - this.bufferPos < n) {
            refillBuffer(n);
        }
    }

    private void refillBuffer(int n) throws IOException {
        if (!tryRefillBuffer(n)) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
    }

    private boolean tryRefillBuffer(int n) throws IOException {
        if (this.bufferPos + n <= this.bufferSize) {
            throw new IllegalStateException("refillBuffer() called when " + n + " bytes were already available in buffer");
        } else if ((this.totalBytesRetired + this.bufferPos) + n > this.currentLimit) {
            return false;
        } else {
            if (this.refillCallback != null) {
                this.refillCallback.onRefill();
            }
            if (this.input == null) {
                return false;
            }
            int pos = this.bufferPos;
            if (pos > 0) {
                if (this.bufferSize > pos) {
                    System.arraycopy(this.buffer, pos, this.buffer, 0, this.bufferSize - pos);
                }
                this.totalBytesRetired += pos;
                this.bufferSize -= pos;
                this.bufferPos = 0;
            }
            int bytesRead = this.input.read(this.buffer, this.bufferSize, this.buffer.length - this.bufferSize);
            if (bytesRead == 0 || bytesRead < -1 || bytesRead > this.buffer.length) {
                throw new IllegalStateException("InputStream#read(byte[]) returned invalid result: " + bytesRead + "\nThe InputStream implementation is buggy.");
            } else if (bytesRead <= 0) {
                return false;
            } else {
                this.bufferSize += bytesRead;
                if ((this.totalBytesRetired + n) - this.sizeLimit > 0) {
                    throw InvalidProtocolBufferException.sizeLimitExceeded();
                }
                recomputeBufferSizeAfterLimit();
                return this.bufferSize >= n ? true : tryRefillBuffer(n);
            }
        }
    }

    public byte readRawByte() throws IOException {
        if (this.bufferPos == this.bufferSize) {
            refillBuffer(1);
        }
        byte[] bArr = this.buffer;
        int i = this.bufferPos;
        this.bufferPos = i + 1;
        return bArr[i];
    }

    public byte[] readRawBytes(int size) throws IOException {
        int pos = this.bufferPos;
        if (size > this.bufferSize - pos || size <= 0) {
            return readRawBytesSlowPath(size);
        }
        this.bufferPos = pos + size;
        return Arrays.copyOfRange(this.buffer, pos, pos + size);
    }

    private byte[] readRawBytesSlowPath(int size) throws IOException {
        if (size <= 0) {
            if (size == 0) {
                return Internal.EMPTY_BYTE_ARRAY;
            }
            throw InvalidProtocolBufferException.negativeSize();
        } else if ((this.totalBytesRetired + this.bufferPos) + size > this.currentLimit) {
            skipRawBytes((this.currentLimit - this.totalBytesRetired) - this.bufferPos);
            throw InvalidProtocolBufferException.truncatedMessage();
        } else if (size < 4096) {
            bytes = new byte[size];
            pos = this.bufferSize - this.bufferPos;
            System.arraycopy(this.buffer, this.bufferPos, bytes, 0, pos);
            this.bufferPos = this.bufferSize;
            ensureAvailable(size - pos);
            System.arraycopy(this.buffer, 0, bytes, pos, size - pos);
            this.bufferPos = size - pos;
            return bytes;
        } else {
            byte[] chunk;
            int originalBufferPos = this.bufferPos;
            int originalBufferSize = this.bufferSize;
            this.totalBytesRetired += this.bufferSize;
            this.bufferPos = 0;
            this.bufferSize = 0;
            int sizeLeft = size - (originalBufferSize - originalBufferPos);
            List<byte[]> chunks = new ArrayList();
            while (sizeLeft > 0) {
                chunk = new byte[Math.min(sizeLeft, 4096)];
                pos = 0;
                while (pos < chunk.length) {
                    int n = this.input == null ? -1 : this.input.read(chunk, pos, chunk.length - pos);
                    if (n == -1) {
                        throw InvalidProtocolBufferException.truncatedMessage();
                    }
                    this.totalBytesRetired += n;
                    pos += n;
                }
                sizeLeft -= chunk.length;
                chunks.add(chunk);
            }
            bytes = new byte[size];
            pos = originalBufferSize - originalBufferPos;
            System.arraycopy(this.buffer, originalBufferPos, bytes, 0, pos);
            for (byte[] chunk2 : chunks) {
                System.arraycopy(chunk2, 0, bytes, pos, chunk2.length);
                pos += chunk2.length;
            }
            return bytes;
        }
    }

    public void skipRawBytes(int size) throws IOException {
        if (size > this.bufferSize - this.bufferPos || size < 0) {
            skipRawBytesSlowPath(size);
        } else {
            this.bufferPos += size;
        }
    }

    private void skipRawBytesSlowPath(int size) throws IOException {
        if (size < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        } else if ((this.totalBytesRetired + this.bufferPos) + size > this.currentLimit) {
            skipRawBytes((this.currentLimit - this.totalBytesRetired) - this.bufferPos);
            throw InvalidProtocolBufferException.truncatedMessage();
        } else {
            int pos = this.bufferSize - this.bufferPos;
            this.bufferPos = this.bufferSize;
            refillBuffer(1);
            while (size - pos > this.bufferSize) {
                pos += this.bufferSize;
                this.bufferPos = this.bufferSize;
                refillBuffer(1);
            }
            this.bufferPos = size - pos;
        }
    }
}
