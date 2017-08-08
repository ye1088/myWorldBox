package io.netty.handler.codec.compression;

import com.MCWorld.video.recorder.b;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import java.util.zip.Checksum;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;
import net.jpountz.xxhash.XXHashFactory;
import org.bytedeco.javacpp.swscale;

public class Lz4FrameDecoder extends ByteToMessageDecoder {
    private int blockType;
    private ByteBufChecksum checksum;
    private int compressedLength;
    private int currentChecksum;
    private State currentState;
    private int decompressedLength;
    private LZ4FastDecompressor decompressor;

    private enum State {
        INIT_BLOCK,
        DECOMPRESS_DATA,
        FINISHED,
        CORRUPTED
    }

    public Lz4FrameDecoder() {
        this(false);
    }

    public Lz4FrameDecoder(boolean validateChecksums) {
        this(LZ4Factory.fastestInstance(), validateChecksums);
    }

    public Lz4FrameDecoder(LZ4Factory factory, boolean validateChecksums) {
        this(factory, validateChecksums ? XXHashFactory.fastestInstance().newStreamingHash32(-1756908916).asChecksum() : null);
    }

    public Lz4FrameDecoder(LZ4Factory factory, Checksum checksum) {
        this.currentState = State.INIT_BLOCK;
        if (factory == null) {
            throw new NullPointerException("factory");
        }
        this.decompressor = factory.fastDecompressor();
        this.checksum = checksum == null ? null : ByteBufChecksum.wrapChecksum(checksum);
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        try {
            int blockType;
            int compressedLength;
            int decompressedLength;
            int currentChecksum;
            switch (this.currentState) {
                case INIT_BLOCK:
                    if (in.readableBytes() >= 21) {
                        if (in.readLong() != 5501767354678207339L) {
                            throw new DecompressionException("unexpected block identifier");
                        }
                        int token = in.readByte();
                        int compressionLevel = (token & 15) + 10;
                        blockType = token & b.bpd;
                        compressedLength = Integer.reverseBytes(in.readInt());
                        if (compressedLength < 0 || compressedLength > swscale.SWS_CPU_CAPS_SSE2) {
                            throw new DecompressionException(String.format("invalid compressedLength: %d (expected: 0-%d)", new Object[]{Integer.valueOf(compressedLength), Integer.valueOf(swscale.SWS_CPU_CAPS_SSE2)}));
                        }
                        decompressedLength = Integer.reverseBytes(in.readInt());
                        int maxDecompressedLength = 1 << compressionLevel;
                        if (decompressedLength < 0 || decompressedLength > maxDecompressedLength) {
                            throw new DecompressionException(String.format("invalid decompressedLength: %d (expected: 0-%d)", new Object[]{Integer.valueOf(decompressedLength), Integer.valueOf(maxDecompressedLength)}));
                        } else if ((decompressedLength != 0 || compressedLength == 0) && ((decompressedLength == 0 || compressedLength != 0) && (blockType != 16 || decompressedLength == compressedLength))) {
                            currentChecksum = Integer.reverseBytes(in.readInt());
                            if (decompressedLength != 0 || compressedLength != 0) {
                                this.blockType = blockType;
                                this.compressedLength = compressedLength;
                                this.decompressedLength = decompressedLength;
                                this.currentChecksum = currentChecksum;
                                this.currentState = State.DECOMPRESS_DATA;
                            } else if (currentChecksum != 0) {
                                throw new DecompressionException("stream corrupted: checksum error");
                            } else {
                                this.currentState = State.FINISHED;
                                this.decompressor = null;
                                this.checksum = null;
                                return;
                            }
                        } else {
                            throw new DecompressionException(String.format("stream corrupted: compressedLength(%d) and decompressedLength(%d) mismatch", new Object[]{Integer.valueOf(compressedLength), Integer.valueOf(decompressedLength)}));
                        }
                    }
                    return;
                    break;
                case DECOMPRESS_DATA:
                    blockType = this.blockType;
                    compressedLength = this.compressedLength;
                    decompressedLength = this.decompressedLength;
                    currentChecksum = this.currentChecksum;
                    if (in.readableBytes() >= compressedLength) {
                        ByteBufChecksum checksum = this.checksum;
                        ByteBuf uncompressed = null;
                        switch (blockType) {
                            case 16:
                                uncompressed = in.retainedSlice(in.readerIndex(), decompressedLength);
                                break;
                            case 32:
                                uncompressed = ctx.alloc().buffer(decompressedLength, decompressedLength);
                                this.decompressor.decompress(CompressionUtil.safeNioBuffer(in), uncompressed.internalNioBuffer(uncompressed.writerIndex(), decompressedLength));
                                uncompressed.writerIndex(uncompressed.writerIndex() + decompressedLength);
                                break;
                            default:
                                try {
                                    throw new DecompressionException(String.format("unexpected blockType: %d (expected: %d or %d)", new Object[]{Integer.valueOf(blockType), Integer.valueOf(16), Integer.valueOf(32)}));
                                } catch (Throwable e) {
                                    throw new DecompressionException(e);
                                } catch (Throwable th) {
                                    if (uncompressed != null) {
                                        uncompressed.release();
                                    }
                                }
                        }
                        in.skipBytes(compressedLength);
                        if (checksum != null) {
                            CompressionUtil.checkChecksum(checksum, uncompressed, currentChecksum);
                        }
                        out.add(uncompressed);
                        uncompressed = null;
                        this.currentState = State.INIT_BLOCK;
                        if (uncompressed != null) {
                            uncompressed.release();
                            return;
                        }
                        return;
                    }
                    return;
                case FINISHED:
                case CORRUPTED:
                    in.skipBytes(in.readableBytes());
                    return;
                default:
                    throw new IllegalStateException();
            }
        } catch (Exception e2) {
            this.currentState = State.CORRUPTED;
            throw e2;
        }
        this.currentState = State.CORRUPTED;
        throw e2;
    }

    public boolean isClosed() {
        return this.currentState == State.FINISHED;
    }
}
