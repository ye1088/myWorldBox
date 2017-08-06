package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelPromiseNotifier;
import io.netty.util.concurrent.EventExecutor;
import java.util.concurrent.TimeUnit;
import java.util.zip.CRC32;
import java.util.zip.Deflater;

public class JdkZlibEncoder extends ZlibEncoder {
    private static final byte[] gzipHeader = new byte[]{(byte) 31, (byte) -117, (byte) 8, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0};
    private final CRC32 crc;
    private volatile ChannelHandlerContext ctx;
    private final Deflater deflater;
    private volatile boolean finished;
    private final ZlibWrapper wrapper;
    private boolean writeHeader;

    public JdkZlibEncoder() {
        this(6);
    }

    public JdkZlibEncoder(int compressionLevel) {
        this(ZlibWrapper.ZLIB, compressionLevel);
    }

    public JdkZlibEncoder(ZlibWrapper wrapper) {
        this(wrapper, 6);
    }

    public JdkZlibEncoder(ZlibWrapper wrapper, int compressionLevel) {
        boolean z = true;
        this.crc = new CRC32();
        this.writeHeader = true;
        if (compressionLevel < 0 || compressionLevel > 9) {
            throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
        } else if (wrapper == null) {
            throw new NullPointerException("wrapper");
        } else if (wrapper == ZlibWrapper.ZLIB_OR_NONE) {
            throw new IllegalArgumentException("wrapper '" + ZlibWrapper.ZLIB_OR_NONE + "' is not " + "allowed for compression.");
        } else {
            this.wrapper = wrapper;
            if (wrapper == ZlibWrapper.ZLIB) {
                z = false;
            }
            this.deflater = new Deflater(compressionLevel, z);
        }
    }

    public JdkZlibEncoder(byte[] dictionary) {
        this(6, dictionary);
    }

    public JdkZlibEncoder(int compressionLevel, byte[] dictionary) {
        this.crc = new CRC32();
        this.writeHeader = true;
        if (compressionLevel < 0 || compressionLevel > 9) {
            throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
        } else if (dictionary == null) {
            throw new NullPointerException("dictionary");
        } else {
            this.wrapper = ZlibWrapper.ZLIB;
            this.deflater = new Deflater(compressionLevel);
            this.deflater.setDictionary(dictionary);
        }
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
        final ChannelFuture p = ctx.newPromise();
        executor.execute(new Runnable() {
            public void run() {
                JdkZlibEncoder.this.finishEncode(JdkZlibEncoder.this.ctx(), p).addListener(new ChannelPromiseNotifier(promise));
            }
        });
        return p;
    }

    private ChannelHandlerContext ctx() {
        ChannelHandlerContext ctx = this.ctx;
        if (ctx != null) {
            return ctx;
        }
        throw new IllegalStateException("not added to a_isRightVersion pipeline");
    }

    public boolean isClosed() {
        return this.finished;
    }

    protected void encode(ChannelHandlerContext ctx, ByteBuf uncompressed, ByteBuf out) throws Exception {
        if (this.finished) {
            out.writeBytes(uncompressed);
            return;
        }
        int len = uncompressed.readableBytes();
        if (len != 0) {
            byte[] inAry;
            int offset;
            if (uncompressed.hasArray()) {
                inAry = uncompressed.array();
                offset = uncompressed.arrayOffset() + uncompressed.readerIndex();
                uncompressed.skipBytes(len);
            } else {
                inAry = new byte[len];
                uncompressed.readBytes(inAry);
                offset = 0;
            }
            if (this.writeHeader) {
                this.writeHeader = false;
                if (this.wrapper == ZlibWrapper.GZIP) {
                    out.writeBytes(gzipHeader);
                }
            }
            if (this.wrapper == ZlibWrapper.GZIP) {
                this.crc.update(inAry, offset, len);
            }
            this.deflater.setInput(inAry, offset, len);
            while (!this.deflater.needsInput()) {
                deflate(out);
            }
        }
    }

    protected final ByteBuf allocateBuffer(ChannelHandlerContext ctx, ByteBuf msg, boolean preferDirect) throws Exception {
        int sizeEstimate = ((int) Math.ceil(((double) msg.readableBytes()) * 1.001d)) + 12;
        if (this.writeHeader) {
            switch (this.wrapper) {
                case GZIP:
                    sizeEstimate += gzipHeader.length;
                    break;
                case ZLIB:
                    sizeEstimate += 2;
                    break;
            }
        }
        return ctx.alloc().heapBuffer(sizeEstimate);
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

    private ChannelFuture finishEncode(ChannelHandlerContext ctx, ChannelPromise promise) {
        if (this.finished) {
            promise.setSuccess();
            return promise;
        }
        this.finished = true;
        ByteBuf footer = ctx.alloc().heapBuffer();
        if (this.writeHeader && this.wrapper == ZlibWrapper.GZIP) {
            this.writeHeader = false;
            footer.writeBytes(gzipHeader);
        }
        this.deflater.finish();
        while (!this.deflater.finished()) {
            deflate(footer);
            if (!footer.isWritable()) {
                ctx.write(footer);
                footer = ctx.alloc().heapBuffer();
            }
        }
        if (this.wrapper == ZlibWrapper.GZIP) {
            int crcValue = (int) this.crc.getValue();
            int uncBytes = this.deflater.getTotalIn();
            footer.writeByte(crcValue);
            footer.writeByte(crcValue >>> 8);
            footer.writeByte(crcValue >>> 16);
            footer.writeByte(crcValue >>> 24);
            footer.writeByte(uncBytes);
            footer.writeByte(uncBytes >>> 8);
            footer.writeByte(uncBytes >>> 16);
            footer.writeByte(uncBytes >>> 24);
        }
        this.deflater.end();
        return ctx.writeAndFlush(footer, promise);
    }

    private void deflate(ByteBuf out) {
        int numBytes;
        do {
            int writerIndex = out.writerIndex();
            numBytes = this.deflater.deflate(out.array(), out.arrayOffset() + writerIndex, out.writableBytes(), 2);
            out.writerIndex(writerIndex + numBytes);
        } while (numBytes > 0);
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }
}
