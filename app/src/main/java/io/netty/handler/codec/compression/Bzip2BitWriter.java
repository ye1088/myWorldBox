package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;

final class Bzip2BitWriter {
    private long bitBuffer;
    private int bitCount;

    Bzip2BitWriter() {
    }

    void writeBits(ByteBuf out, int count, long value) {
        if (count < 0 || count > 32) {
            throw new IllegalArgumentException("count: " + count + " (expected: 0-32)");
        }
        int bitCount = this.bitCount;
        long bitBuffer = this.bitBuffer | ((value << (64 - count)) >>> bitCount);
        bitCount += count;
        if (bitCount >= 32) {
            out.writeInt((int) (bitBuffer >>> 32));
            bitBuffer <<= 32;
            bitCount -= 32;
        }
        this.bitBuffer = bitBuffer;
        this.bitCount = bitCount;
    }

    void writeBoolean(ByteBuf out, boolean value) {
        int bitCount = this.bitCount + 1;
        long bitBuffer = this.bitBuffer | (value ? 1 << (64 - bitCount) : 0);
        if (bitCount == 32) {
            out.writeInt((int) (bitBuffer >>> 32));
            bitBuffer = 0;
            bitCount = 0;
        }
        this.bitBuffer = bitBuffer;
        this.bitCount = bitCount;
    }

    void writeUnary(ByteBuf out, int value) {
        if (value < 0) {
            throw new IllegalArgumentException("value: " + value + " (expected 0 or more)");
        }
        int value2 = value;
        while (true) {
            value = value2 - 1;
            if (value2 > 0) {
                writeBoolean(out, true);
                value2 = value;
            } else {
                writeBoolean(out, false);
                return;
            }
        }
    }

    void writeInt(ByteBuf out, int value) {
        writeBits(out, 32, (long) value);
    }

    void flush(ByteBuf out) {
        int bitCount = this.bitCount;
        if (bitCount > 0) {
            long bitBuffer = this.bitBuffer;
            int shiftToRight = 64 - bitCount;
            if (bitCount <= 8) {
                out.writeByte((int) ((bitBuffer >>> shiftToRight) << (8 - bitCount)));
            } else if (bitCount <= 16) {
                out.writeShort((int) ((bitBuffer >>> shiftToRight) << (16 - bitCount)));
            } else if (bitCount <= 24) {
                out.writeMedium((int) ((bitBuffer >>> shiftToRight) << (24 - bitCount)));
            } else {
                out.writeInt((int) ((bitBuffer >>> shiftToRight) << (32 - bitCount)));
            }
        }
    }
}
