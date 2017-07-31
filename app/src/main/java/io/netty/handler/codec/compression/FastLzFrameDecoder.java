package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.internal.EmptyArrays;
import java.util.List;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

public class FastLzFrameDecoder extends ByteToMessageDecoder {
    private final Checksum checksum;
    private int chunkLength;
    private int currentChecksum;
    private State currentState;
    private boolean hasChecksum;
    private boolean isCompressed;
    private int originalLength;

    private enum State {
        INIT_BLOCK,
        INIT_BLOCK_PARAMS,
        DECOMPRESS_DATA,
        CORRUPTED
    }

    public FastLzFrameDecoder() {
        this(false);
    }

    public FastLzFrameDecoder(boolean validateChecksums) {
        this(validateChecksums ? new Adler32() : null);
    }

    public FastLzFrameDecoder(Checksum checksum) {
        this.currentState = State.INIT_BLOCK;
        this.checksum = checksum;
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        ByteBuf uncompressed;
        try {
            switch (this.currentState) {
                case INIT_BLOCK:
                    if (in.readableBytes() >= 4) {
                        if (in.readUnsignedMedium() == 4607066) {
                            byte options = in.readByte();
                            this.isCompressed = (options & 1) == 1;
                            this.hasChecksum = (options & 16) == 16;
                            this.currentState = State.INIT_BLOCK_PARAMS;
                            break;
                        }
                        throw new DecompressionException("unexpected block identifier");
                    }
                    return;
                case INIT_BLOCK_PARAMS:
                    break;
                case DECOMPRESS_DATA:
                    break;
                case CORRUPTED:
                    in.skipBytes(in.readableBytes());
                    return;
                default:
                    throw new IllegalStateException();
            }
            if (in.readableBytes() >= (this.hasChecksum ? 4 : 0) + ((this.isCompressed ? 2 : 0) + 2)) {
                int readUnsignedShort;
                this.currentChecksum = this.hasChecksum ? in.readInt() : 0;
                this.chunkLength = in.readUnsignedShort();
                if (this.isCompressed) {
                    readUnsignedShort = in.readUnsignedShort();
                } else {
                    readUnsignedShort = this.chunkLength;
                }
                this.originalLength = readUnsignedShort;
                this.currentState = State.DECOMPRESS_DATA;
                int chunkLength = this.chunkLength;
                if (in.readableBytes() >= chunkLength) {
                    byte[] output;
                    int outputPtr;
                    int idx = in.readerIndex();
                    int originalLength = this.originalLength;
                    if (originalLength != 0) {
                        uncompressed = ctx.alloc().heapBuffer(originalLength, originalLength);
                        output = uncompressed.array();
                        outputPtr = uncompressed.arrayOffset() + uncompressed.writerIndex();
                    } else {
                        uncompressed = null;
                        output = EmptyArrays.EMPTY_BYTES;
                        outputPtr = 0;
                    }
                    if (this.isCompressed) {
                        byte[] input;
                        int inputPtr;
                        if (in.hasArray()) {
                            input = in.array();
                            inputPtr = in.arrayOffset() + idx;
                        } else {
                            input = new byte[chunkLength];
                            in.getBytes(idx, input);
                            inputPtr = 0;
                        }
                        if (originalLength != FastLz.decompress(input, inputPtr, chunkLength, output, outputPtr, originalLength)) {
                            throw new DecompressionException(String.format("stream corrupted: originalLength(%d) and actual length(%d) mismatch", new Object[]{Integer.valueOf(originalLength), Integer.valueOf(FastLz.decompress(input, inputPtr, chunkLength, output, outputPtr, originalLength))}));
                        }
                    }
                    in.getBytes(idx, output, outputPtr, chunkLength);
                    Checksum checksum = this.checksum;
                    if (this.hasChecksum && checksum != null) {
                        checksum.reset();
                        checksum.update(output, outputPtr, originalLength);
                        if (((int) checksum.getValue()) != this.currentChecksum) {
                            throw new DecompressionException(String.format("stream corrupted: mismatching checksum: %d (expected: %d)", new Object[]{Integer.valueOf((int) checksum.getValue()), Integer.valueOf(this.currentChecksum)}));
                        }
                    }
                    if (uncompressed != null) {
                        uncompressed.writerIndex(uncompressed.writerIndex() + originalLength);
                        out.add(uncompressed);
                    }
                    in.skipBytes(chunkLength);
                    this.currentState = State.INIT_BLOCK;
                    if (!true) {
                        uncompressed.release();
                    }
                }
            }
        } catch (Exception e) {
            this.currentState = State.CORRUPTED;
            throw e;
        } catch (Throwable th) {
            if (!false) {
                uncompressed.release();
            }
        }
    }
}
