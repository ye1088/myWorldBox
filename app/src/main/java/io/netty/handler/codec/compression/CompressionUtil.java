package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import java.nio.ByteBuffer;

final class CompressionUtil {
    private CompressionUtil() {
    }

    static void checkChecksum(ByteBufChecksum checksum, ByteBuf uncompressed, int currentChecksum) {
        checksum.reset();
        checksum.update(uncompressed, uncompressed.readerIndex(), uncompressed.readableBytes());
        if (((int) checksum.getValue()) != currentChecksum) {
            throw new DecompressionException(String.format("stream corrupted: mismatching checksum: %d (expected: %d)", new Object[]{Integer.valueOf((int) checksum.getValue()), Integer.valueOf(currentChecksum)}));
        }
    }

    static ByteBuffer safeNioBuffer(ByteBuf buffer) {
        return buffer.nioBufferCount() == 1 ? buffer.internalNioBuffer(buffer.readerIndex(), buffer.readableBytes()) : buffer.nioBuffer();
    }
}
