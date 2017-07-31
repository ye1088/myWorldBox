package io.netty.handler.codec.compression;

import com.ning.compress.BufferRecycler;
import com.ning.compress.lzf.ChunkDecoder;
import com.ning.compress.lzf.util.ChunkDecoderFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

public class LzfDecoder extends ByteToMessageDecoder {
    private static final short MAGIC_NUMBER = (short) 23126;
    private int chunkLength;
    private State currentState;
    private ChunkDecoder decoder;
    private boolean isCompressed;
    private int originalLength;
    private BufferRecycler recycler;

    private enum State {
        INIT_BLOCK,
        INIT_ORIGINAL_LENGTH,
        DECOMPRESS_DATA,
        CORRUPTED
    }

    public LzfDecoder() {
        this(false);
    }

    public LzfDecoder(boolean safeInstance) {
        this.currentState = State.INIT_BLOCK;
        this.decoder = safeInstance ? ChunkDecoderFactory.safeInstance() : ChunkDecoderFactory.optimalInstance();
        this.recycler = BufferRecycler.instance();
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        ByteBuf uncompressed;
        try {
            switch (this.currentState) {
                case INIT_BLOCK:
                    if (in.readableBytes() >= 5) {
                        if (in.readUnsignedShort() != 23126) {
                            throw new DecompressionException("unexpected block identifier");
                        }
                        int type = in.readByte();
                        switch (type) {
                            case 0:
                                this.isCompressed = false;
                                this.currentState = State.DECOMPRESS_DATA;
                                break;
                            case 1:
                                this.isCompressed = true;
                                this.currentState = State.INIT_ORIGINAL_LENGTH;
                                break;
                            default:
                                throw new DecompressionException(String.format("unknown type of chunk: %d (expected: %d or %d)", new Object[]{Integer.valueOf(type), Integer.valueOf(0), Integer.valueOf(1)}));
                        }
                        this.chunkLength = in.readUnsignedShort();
                        if (type != 1) {
                            return;
                        }
                    }
                    return;
                    break;
                case INIT_ORIGINAL_LENGTH:
                    break;
                case DECOMPRESS_DATA:
                    break;
                case CORRUPTED:
                    in.skipBytes(in.readableBytes());
                    return;
                default:
                    throw new IllegalStateException();
            }
            if (in.readableBytes() >= 2) {
                this.originalLength = in.readUnsignedShort();
                this.currentState = State.DECOMPRESS_DATA;
                int chunkLength = this.chunkLength;
                if (in.readableBytes() >= chunkLength) {
                    int originalLength = this.originalLength;
                    if (this.isCompressed) {
                        byte[] inputArray;
                        int inPos;
                        int idx = in.readerIndex();
                        if (in.hasArray()) {
                            inputArray = in.array();
                            inPos = in.arrayOffset() + idx;
                        } else {
                            inputArray = this.recycler.allocInputBuffer(chunkLength);
                            in.getBytes(idx, inputArray, 0, chunkLength);
                            inPos = 0;
                        }
                        uncompressed = ctx.alloc().heapBuffer(originalLength, originalLength);
                        int outPos = uncompressed.arrayOffset() + uncompressed.writerIndex();
                        this.decoder.decodeChunk(inputArray, inPos, uncompressed.array(), outPos, outPos + originalLength);
                        uncompressed.writerIndex(uncompressed.writerIndex() + originalLength);
                        out.add(uncompressed);
                        in.skipBytes(chunkLength);
                        if (!true) {
                            uncompressed.release();
                        }
                        if (!in.hasArray()) {
                            this.recycler.releaseInputBuffer(inputArray);
                        }
                    } else if (chunkLength > 0) {
                        out.add(in.readRetainedSlice(chunkLength));
                    }
                    this.currentState = State.INIT_BLOCK;
                }
            }
        } catch (Exception e) {
            this.currentState = State.CORRUPTED;
            this.decoder = null;
            this.recycler = null;
            throw e;
        } catch (Throwable th) {
            if (!false) {
                uncompressed.release();
            }
        }
    }
}
