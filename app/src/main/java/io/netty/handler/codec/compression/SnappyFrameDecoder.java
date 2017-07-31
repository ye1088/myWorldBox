package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

public class SnappyFrameDecoder extends ByteToMessageDecoder {
    private static final int MAX_UNCOMPRESSED_DATA_SIZE = 65540;
    private static final int SNAPPY_IDENTIFIER_LEN = 6;
    private boolean corrupted;
    private final Snappy snappy;
    private boolean started;
    private final boolean validateChecksums;

    private enum ChunkType {
        STREAM_IDENTIFIER,
        COMPRESSED_DATA,
        UNCOMPRESSED_DATA,
        RESERVED_UNSKIPPABLE,
        RESERVED_SKIPPABLE
    }

    public SnappyFrameDecoder() {
        this(false);
    }

    public SnappyFrameDecoder(boolean validateChecksums) {
        this.snappy = new Snappy();
        this.validateChecksums = validateChecksums;
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (this.corrupted) {
            in.skipBytes(in.readableBytes());
            return;
        }
        try {
            int idx = in.readerIndex();
            int inSize = in.readableBytes();
            if (inSize >= 4) {
                int chunkTypeVal = in.getUnsignedByte(idx);
                ChunkType chunkType = mapChunkType((byte) chunkTypeVal);
                int chunkLength = in.getUnsignedMediumLE(idx + 1);
                switch (chunkType) {
                    case STREAM_IDENTIFIER:
                        if (chunkLength != 6) {
                            throw new DecompressionException("Unexpected length of stream identifier: " + chunkLength);
                        } else if (inSize >= 10) {
                            in.skipBytes(4);
                            int readerIndex = in.readerIndex();
                            in.skipBytes(6);
                            int i = readerIndex + 1;
                            checkByte(in.getByte(readerIndex), (byte) 115);
                            readerIndex = i + 1;
                            checkByte(in.getByte(i), (byte) 78);
                            i = readerIndex + 1;
                            checkByte(in.getByte(readerIndex), (byte) 97);
                            readerIndex = i + 1;
                            checkByte(in.getByte(i), (byte) 80);
                            i = readerIndex + 1;
                            checkByte(in.getByte(readerIndex), (byte) 112);
                            checkByte(in.getByte(i), (byte) 89);
                            this.started = true;
                            return;
                        } else {
                            return;
                        }
                    case RESERVED_SKIPPABLE:
                        if (!this.started) {
                            throw new DecompressionException("Received RESERVED_SKIPPABLE tag before STREAM_IDENTIFIER");
                        } else if (inSize >= chunkLength + 4) {
                            in.skipBytes(chunkLength + 4);
                            return;
                        } else {
                            return;
                        }
                    case RESERVED_UNSKIPPABLE:
                        throw new DecompressionException("Found reserved unskippable chunk type: 0x" + Integer.toHexString(chunkTypeVal));
                    case UNCOMPRESSED_DATA:
                        if (!this.started) {
                            throw new DecompressionException("Received UNCOMPRESSED_DATA tag before STREAM_IDENTIFIER");
                        } else if (chunkLength > 65540) {
                            throw new DecompressionException("Received UNCOMPRESSED_DATA larger than 65540 bytes");
                        } else if (inSize >= chunkLength + 4) {
                            in.skipBytes(4);
                            if (this.validateChecksums) {
                                Snappy.validateChecksum(in.readIntLE(), in, in.readerIndex(), chunkLength - 4);
                            } else {
                                in.skipBytes(4);
                            }
                            out.add(in.readRetainedSlice(chunkLength - 4));
                            return;
                        } else {
                            return;
                        }
                    case COMPRESSED_DATA:
                        if (!this.started) {
                            throw new DecompressionException("Received COMPRESSED_DATA tag before STREAM_IDENTIFIER");
                        } else if (inSize >= chunkLength + 4) {
                            in.skipBytes(4);
                            int checksum = in.readIntLE();
                            ByteBuf uncompressed = ctx.alloc().buffer();
                            int oldWriterIndex;
                            try {
                                if (this.validateChecksums) {
                                    oldWriterIndex = in.writerIndex();
                                    in.writerIndex((in.readerIndex() + chunkLength) - 4);
                                    this.snappy.decode(in, uncompressed);
                                    in.writerIndex(oldWriterIndex);
                                    Snappy.validateChecksum(checksum, uncompressed, 0, uncompressed.writerIndex());
                                } else {
                                    this.snappy.decode(in.readSlice(chunkLength - 4), uncompressed);
                                }
                                out.add(uncompressed);
                                uncompressed = null;
                                if (uncompressed != null) {
                                    uncompressed.release();
                                }
                                this.snappy.reset();
                                return;
                            } catch (Throwable th) {
                                if (uncompressed != null) {
                                    uncompressed.release();
                                }
                            }
                        } else {
                            return;
                        }
                    default:
                        return;
                }
                this.corrupted = true;
                throw e;
            }
        } catch (Exception e) {
            this.corrupted = true;
            throw e;
        }
    }

    private static void checkByte(byte actual, byte expect) {
        if (actual != expect) {
            throw new DecompressionException("Unexpected stream identifier contents. Mismatched snappy protocol version?");
        }
    }

    private static ChunkType mapChunkType(byte type) {
        if (type == (byte) 0) {
            return ChunkType.COMPRESSED_DATA;
        }
        if (type == (byte) 1) {
            return ChunkType.UNCOMPRESSED_DATA;
        }
        if (type == (byte) -1) {
            return ChunkType.STREAM_IDENTIFIER;
        }
        if ((type & 128) == 128) {
            return ChunkType.RESERVED_SKIPPABLE;
        }
        return ChunkType.RESERVED_UNSKIPPABLE;
    }
}
