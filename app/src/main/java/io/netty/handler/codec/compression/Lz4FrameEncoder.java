package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelPromiseNotifier;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.concurrent.EventExecutor;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;
import java.util.zip.Checksum;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.xxhash.XXHashFactory;
import org.bytedeco.javacpp.swscale;

public class Lz4FrameEncoder extends MessageToByteEncoder<ByteBuf> {
    private final int blockSize;
    private ByteBuf buffer;
    private Checksum checksum;
    private final int compressedBlockSize;
    private final int compressionLevel;
    private LZ4Compressor compressor;
    private volatile ChannelHandlerContext ctx;
    private int currentBlockLength;
    private volatile boolean finished;

    public Lz4FrameEncoder() {
        this(false);
    }

    public Lz4FrameEncoder(boolean highCompressor) {
        this(LZ4Factory.fastestInstance(), highCompressor, 65536, XXHashFactory.fastestInstance().newStreamingHash32(-1756908916).asChecksum());
    }

    public Lz4FrameEncoder(LZ4Factory factory, boolean highCompressor, int blockSize, Checksum checksum) {
        if (factory == null) {
            throw new NullPointerException("factory");
        } else if (checksum == null) {
            throw new NullPointerException("checksum");
        } else {
            this.compressor = highCompressor ? factory.highCompressor() : factory.fastCompressor();
            this.checksum = checksum;
            this.compressionLevel = compressionLevel(blockSize);
            this.blockSize = blockSize;
            this.currentBlockLength = 0;
            this.compressedBlockSize = this.compressor.maxCompressedLength(blockSize) + 21;
            this.finished = false;
        }
    }

    private static int compressionLevel(int blockSize) {
        if (blockSize >= 64 && blockSize <= swscale.SWS_CPU_CAPS_SSE2) {
            return Math.max(0, (32 - Integer.numberOfLeadingZeros(blockSize - 1)) - 10);
        }
        throw new IllegalArgumentException(String.format("blockSize: %d (expected: %d-%d)", new Object[]{Integer.valueOf(blockSize), Integer.valueOf(64), Integer.valueOf(swscale.SWS_CPU_CAPS_SSE2)}));
    }

    protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
        if (this.finished) {
            out.writeBytes(in);
            return;
        }
        int length = in.readableBytes();
        ByteBuf buffer = this.buffer;
        int blockSize = buffer.capacity();
        while (this.currentBlockLength + length >= blockSize) {
            int tail = blockSize - this.currentBlockLength;
            in.getBytes(in.readerIndex(), buffer, this.currentBlockLength, tail);
            this.currentBlockLength = blockSize;
            flushBufferedData(out);
            in.skipBytes(tail);
            length -= tail;
        }
        in.readBytes(buffer, this.currentBlockLength, length);
        this.currentBlockLength += length;
    }

    private void flushBufferedData(ByteBuf out) {
        int currentBlockLength = this.currentBlockLength;
        if (currentBlockLength != 0) {
            this.checksum.reset();
            this.checksum.update(this.buffer.array(), this.buffer.arrayOffset(), currentBlockLength);
            int check = (int) this.checksum.getValue();
            out.ensureWritable(this.compressedBlockSize);
            int idx = out.writerIndex();
            try {
                int blockType;
                ByteBuffer outNioBuffer = out.internalNioBuffer(idx + 21, out.writableBytes() - 21);
                int pos = outNioBuffer.position();
                this.compressor.compress(this.buffer.internalNioBuffer(0, currentBlockLength), outNioBuffer);
                int compressedLength = outNioBuffer.position() - pos;
                if (compressedLength >= currentBlockLength) {
                    blockType = 16;
                    compressedLength = currentBlockLength;
                    out.setBytes(idx + 21, this.buffer, 0, currentBlockLength);
                } else {
                    blockType = 32;
                }
                out.setLong(idx, 5501767354678207339L);
                out.setByte(idx + 8, (byte) (this.compressionLevel | blockType));
                out.setIntLE(idx + 9, compressedLength);
                out.setIntLE(idx + 13, currentBlockLength);
                out.setIntLE(idx + 17, check);
                out.writerIndex((idx + 21) + compressedLength);
                this.currentBlockLength = 0;
            } catch (Throwable e) {
                throw new CompressionException(e);
            }
        }
    }

    private ChannelFuture finishEncode(ChannelHandlerContext ctx, ChannelPromise promise) {
        if (this.finished) {
            promise.setSuccess();
        } else {
            this.finished = true;
            try {
                ByteBuf footer = ctx.alloc().heapBuffer(this.compressor.maxCompressedLength(this.currentBlockLength) + 21);
                flushBufferedData(footer);
                int idx = footer.writerIndex();
                footer.setLong(idx, 5501767354678207339L);
                footer.setByte(idx + 8, (byte) (this.compressionLevel | 16));
                footer.setInt(idx + 9, 0);
                footer.setInt(idx + 13, 0);
                footer.setInt(idx + 17, 0);
                footer.writerIndex(idx + 21);
                promise = ctx.writeAndFlush(footer, promise);
            } finally {
                cleanup();
            }
        }
        return promise;
    }

    private void cleanup() {
        this.compressor = null;
        this.checksum = null;
        if (this.buffer != null) {
            this.buffer.release();
            this.buffer = null;
        }
    }

    public boolean isClosed() {
        return this.finished;
    }

    public ChannelFuture close() {
        return close(ctx().newPromise());
    }

    public ChannelFuture close(final ChannelPromise promise) {
        ChannelHandlerContext ctx = ctx();
        EventExecutor executor = ctx.executor();
        if (executor.inEventLoop()) {
            return finishEncode(ctx, promise);
        }
        executor.execute(new Runnable() {
            public void run() {
                Lz4FrameEncoder.this.finishEncode(Lz4FrameEncoder.this.ctx(), promise).addListener(new ChannelPromiseNotifier(promise));
            }
        });
        return promise;
    }

    public void close(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        ChannelFuture f = finishEncode(ctx, ctx.newPromise());
        f.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture f) throws Exception {
                ctx.close(promise);
            }
        });
        if (!f.isDone()) {
            ctx.executor().schedule(new Runnable() {
                public void run() {
                    ctx.close(promise);
                }
            }, 10, TimeUnit.SECONDS);
        }
    }

    private ChannelHandlerContext ctx() {
        ChannelHandlerContext ctx = this.ctx;
        if (ctx != null) {
            return ctx;
        }
        throw new IllegalStateException("not added to a_isRightVersion pipeline");
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        this.buffer = Unpooled.wrappedBuffer(new byte[this.blockSize]);
    }

    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        cleanup();
    }
}
