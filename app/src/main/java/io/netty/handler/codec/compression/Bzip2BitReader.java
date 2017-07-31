package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;

class Bzip2BitReader {
    private static final int MAX_COUNT_OF_READABLE_BYTES = 268435455;
    private long bitBuffer;
    private int bitCount;
    private ByteBuf in;

    Bzip2BitReader() {
    }

    void setByteBuf(ByteBuf in) {
        this.in = in;
    }

    int readBits(int count) {
        if (count < 0 || count > 32) {
            throw new IllegalArgumentException("count: " + count + " (expected: 0-32 )");
        }
        long j;
        int bitCount = this.bitCount;
        long bitBuffer = this.bitBuffer;
        if (bitCount < count) {
            long readData;
            int offset;
            switch (this.in.readableBytes()) {
                case 1:
                    readData = (long) this.in.readUnsignedByte();
                    offset = 8;
                    break;
                case 2:
                    readData = (long) this.in.readUnsignedShort();
                    offset = 16;
                    break;
                case 3:
                    readData = (long) this.in.readUnsignedMedium();
                    offset = 24;
                    break;
                default:
                    readData = this.in.readUnsignedInt();
                    offset = 32;
                    break;
            }
            bitBuffer = (bitBuffer << offset) | readData;
            bitCount += offset;
            this.bitBuffer = bitBuffer;
        }
        bitCount -= count;
        this.bitCount = bitCount;
        long j2 = bitBuffer >>> bitCount;
        if (count != 32) {
            j = (long) ((1 << count) - 1);
        } else {
            j = 4294967295L;
        }
        return (int) (j & j2);
    }

    boolean readBoolean() {
        return readBits(1) != 0;
    }

    int readInt() {
        return readBits(32);
    }

    void refill() {
        this.bitBuffer = (this.bitBuffer << 8) | ((long) this.in.readUnsignedByte());
        this.bitCount += 8;
    }

    boolean isReadable() {
        return this.bitCount > 0 || this.in.isReadable();
    }

    boolean hasReadableBits(int count) {
        if (count >= 0) {
            return this.bitCount >= count || ((this.in.readableBytes() << 3) & Integer.MAX_VALUE) >= count - this.bitCount;
        } else {
            throw new IllegalArgumentException("count: " + count + " (expected value greater than 0)");
        }
    }

    boolean hasReadableBytes(int count) {
        if (count >= 0 && count <= MAX_COUNT_OF_READABLE_BYTES) {
            return hasReadableBits(count << 3);
        }
        throw new IllegalArgumentException("count: " + count + " (expected: 0-" + MAX_COUNT_OF_READABLE_BYTES + ')');
    }
}
