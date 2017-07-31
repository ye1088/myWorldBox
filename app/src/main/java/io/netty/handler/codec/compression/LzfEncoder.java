package io.netty.handler.codec.compression;

import com.ning.compress.BufferRecycler;
import com.ning.compress.lzf.ChunkEncoder;
import com.ning.compress.lzf.LZFEncoder;
import com.ning.compress.lzf.util.ChunkEncoderFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class LzfEncoder extends MessageToByteEncoder<ByteBuf> {
    private static final int MIN_BLOCK_TO_COMPRESS = 16;
    private final ChunkEncoder encoder;
    private final BufferRecycler recycler;

    public LzfEncoder() {
        this(false, 65535);
    }

    public LzfEncoder(boolean safeInstance) {
        this(safeInstance, 65535);
    }

    public LzfEncoder(int totalLength) {
        this(false, totalLength);
    }

    public LzfEncoder(boolean safeInstance, int totalLength) {
        super(false);
        if (totalLength < 16 || totalLength > 65535) {
            throw new IllegalArgumentException("totalLength: " + totalLength + " (expected: " + 16 + '-' + 65535 + ')');
        }
        this.encoder = safeInstance ? ChunkEncoderFactory.safeNonAllocatingInstance(totalLength) : ChunkEncoderFactory.optimalNonAllocatingInstance(totalLength);
        this.recycler = BufferRecycler.instance();
    }

    protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
        byte[] input;
        int inputPtr;
        int length = in.readableBytes();
        int idx = in.readerIndex();
        if (in.hasArray()) {
            input = in.array();
            inputPtr = in.arrayOffset() + idx;
        } else {
            input = this.recycler.allocInputBuffer(length);
            in.getBytes(idx, input, 0, length);
            inputPtr = 0;
        }
        out.ensureWritable(LZFEncoder.estimateMaxWorkspaceSize(length));
        int outputPtr = out.arrayOffset() + out.writerIndex();
        out.writerIndex(out.writerIndex() + (LZFEncoder.appendEncoded(this.encoder, input, inputPtr, length, out.array(), outputPtr) - outputPtr));
        in.skipBytes(length);
        if (!in.hasArray()) {
            this.recycler.releaseInputBuffer(input);
        }
    }
}
