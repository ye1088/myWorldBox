package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

public class FastLzFrameEncoder extends MessageToByteEncoder<ByteBuf> {
    private final Checksum checksum;
    private final int level;

    public FastLzFrameEncoder() {
        this(0, null);
    }

    public FastLzFrameEncoder(int level) {
        this(level, null);
    }

    public FastLzFrameEncoder(boolean validateChecksums) {
        this(0, validateChecksums ? new Adler32() : null);
    }

    public FastLzFrameEncoder(int level, Checksum checksum) {
        super(false);
        if (level == 0 || level == 1 || level == 2) {
            this.level = level;
            this.checksum = checksum;
            return;
        }
        throw new IllegalArgumentException(String.format("level: %d (expected: %d or %d or %d)", new Object[]{Integer.valueOf(level), Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2)}));
    }

    protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
        Checksum checksum = this.checksum;
        while (in.isReadable()) {
            byte blockType;
            int chunkLength;
            int idx = in.readerIndex();
            int length = Math.min(in.readableBytes(), 65535);
            int outputIdx = out.writerIndex();
            out.setMedium(outputIdx, 4607066);
            int outputOffset = (outputIdx + 4) + (checksum != null ? 4 : 0);
            byte[] output;
            int outputPtr;
            byte[] input;
            int inputPtr;
            if (length < 32) {
                blockType = (byte) 0;
                out.ensureWritable((outputOffset + 2) + length);
                output = out.array();
                outputPtr = (out.arrayOffset() + outputOffset) + 2;
                if (checksum != null) {
                    if (in.hasArray()) {
                        input = in.array();
                        inputPtr = in.arrayOffset() + idx;
                    } else {
                        input = new byte[length];
                        in.getBytes(idx, input);
                        inputPtr = 0;
                    }
                    checksum.reset();
                    checksum.update(input, inputPtr, length);
                    out.setInt(outputIdx + 4, (int) checksum.getValue());
                    System.arraycopy(input, inputPtr, output, outputPtr, length);
                } else {
                    in.getBytes(idx, output, outputPtr, length);
                }
                chunkLength = length;
            } else {
                if (in.hasArray()) {
                    input = in.array();
                    inputPtr = in.arrayOffset() + idx;
                } else {
                    input = new byte[length];
                    in.getBytes(idx, input);
                    inputPtr = 0;
                }
                if (checksum != null) {
                    checksum.reset();
                    checksum.update(input, inputPtr, length);
                    out.setInt(outputIdx + 4, (int) checksum.getValue());
                }
                out.ensureWritable((outputOffset + 4) + FastLz.calculateOutputBufferLength(length));
                output = out.array();
                outputPtr = (out.arrayOffset() + outputOffset) + 4;
                int compressedLength = FastLz.compress(input, inputPtr, length, output, outputPtr, this.level);
                if (compressedLength < length) {
                    blockType = (byte) 1;
                    chunkLength = compressedLength;
                    out.setShort(outputOffset, chunkLength);
                    outputOffset += 2;
                } else {
                    blockType = (byte) 0;
                    System.arraycopy(input, inputPtr, output, outputPtr - 2, length);
                    chunkLength = length;
                }
            }
            out.setShort(outputOffset, length);
            out.setByte(outputIdx + 3, (checksum != null ? 16 : 0) | blockType);
            out.writerIndex((outputOffset + 2) + chunkLength);
            in.skipBytes(length);
        }
    }
}
