package io.netty.handler.codec.compression;

import com.jcraft.jzlib.Deflater;
import com.jcraft.jzlib.JZlib;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelPromiseNotifier;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.internal.EmptyArrays;
import java.util.concurrent.TimeUnit;

public class JZlibEncoder extends ZlibEncoder {
    private volatile ChannelHandlerContext ctx;
    private volatile boolean finished;
    private final int wrapperOverhead;
    private final Deflater z;

    public JZlibEncoder() {
        this(6);
    }

    public JZlibEncoder(int compressionLevel) {
        this(ZlibWrapper.ZLIB, compressionLevel);
    }

    public JZlibEncoder(ZlibWrapper wrapper) {
        this(wrapper, 6);
    }

    public JZlibEncoder(ZlibWrapper wrapper, int compressionLevel) {
        this(wrapper, compressionLevel, 15, 8);
    }

    public JZlibEncoder(ZlibWrapper wrapper, int compressionLevel, int windowBits, int memLevel) {
        this.z = new Deflater();
        if (compressionLevel < 0 || compressionLevel > 9) {
            throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
        } else if (windowBits < 9 || windowBits > 15) {
            throw new IllegalArgumentException("windowBits: " + windowBits + " (expected: 9-15)");
        } else if (memLevel < 1 || memLevel > 9) {
            throw new IllegalArgumentException("memLevel: " + memLevel + " (expected: 1-9)");
        } else if (wrapper == null) {
            throw new NullPointerException("wrapper");
        } else if (wrapper == ZlibWrapper.ZLIB_OR_NONE) {
            throw new IllegalArgumentException("wrapper '" + ZlibWrapper.ZLIB_OR_NONE + "' is not " + "allowed for compression.");
        } else {
            int resultCode = this.z.init(compressionLevel, windowBits, memLevel, ZlibUtil.convertWrapperType(wrapper));
            if (resultCode != 0) {
                ZlibUtil.fail(this.z, "initialization failure", resultCode);
            }
            this.wrapperOverhead = ZlibUtil.wrapperOverhead(wrapper);
        }
    }

    public JZlibEncoder(byte[] dictionary) {
        this(6, dictionary);
    }

    public JZlibEncoder(int compressionLevel, byte[] dictionary) {
        this(compressionLevel, 15, 8, dictionary);
    }

    public JZlibEncoder(int compressionLevel, int windowBits, int memLevel, byte[] dictionary) {
        this.z = new Deflater();
        if (compressionLevel < 0 || compressionLevel > 9) {
            throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
        } else if (windowBits < 9 || windowBits > 15) {
            throw new IllegalArgumentException("windowBits: " + windowBits + " (expected: 9-15)");
        } else if (memLevel < 1 || memLevel > 9) {
            throw new IllegalArgumentException("memLevel: " + memLevel + " (expected: 1-9)");
        } else if (dictionary == null) {
            throw new NullPointerException("dictionary");
        } else {
            int resultCode = this.z.deflateInit(compressionLevel, windowBits, memLevel, JZlib.W_ZLIB);
            if (resultCode != 0) {
                ZlibUtil.fail(this.z, "initialization failure", resultCode);
            } else {
                resultCode = this.z.deflateSetDictionary(dictionary, dictionary.length);
                if (resultCode != 0) {
                    ZlibUtil.fail(this.z, "failed to set the dictionary", resultCode);
                }
            }
            this.wrapperOverhead = ZlibUtil.wrapperOverhead(ZlibWrapper.ZLIB);
        }
    }

    public ChannelFuture close() {
        return close(ctx().channel().newPromise());
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
                JZlibEncoder.this.finishEncode(JZlibEncoder.this.ctx(), p).addListener(new ChannelPromiseNotifier(promise));
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

    protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
        if (this.finished) {
            out.writeBytes(in);
            return;
        }
        int inputLength = in.readableBytes();
        if (inputLength != 0) {
            int oldNextInIndex;
            try {
                boolean inHasArray = in.hasArray();
                this.z.avail_in = inputLength;
                if (inHasArray) {
                    this.z.next_in = in.array();
                    this.z.next_in_index = in.arrayOffset() + in.readerIndex();
                } else {
                    byte[] array = new byte[inputLength];
                    in.getBytes(in.readerIndex(), array);
                    this.z.next_in = array;
                    this.z.next_in_index = 0;
                }
                oldNextInIndex = this.z.next_in_index;
                int maxOutputLength = (((int) Math.ceil(((double) inputLength) * 1.001d)) + 12) + this.wrapperOverhead;
                out.ensureWritable(maxOutputLength);
                this.z.avail_out = maxOutputLength;
                this.z.next_out = out.array();
                this.z.next_out_index = out.arrayOffset() + out.writerIndex();
                int oldNextOutIndex = this.z.next_out_index;
                int resultCode = this.z.deflate(2);
                in.skipBytes(this.z.next_in_index - oldNextInIndex);
                if (resultCode != 0) {
                    ZlibUtil.fail(this.z, "compression failure", resultCode);
                }
                int outputLength = this.z.next_out_index - oldNextOutIndex;
                if (outputLength > 0) {
                    out.writerIndex(out.writerIndex() + outputLength);
                }
                this.z.next_in = null;
                this.z.next_out = null;
            } catch (Throwable th) {
                this.z.next_in = null;
                this.z.next_out = null;
            }
        }
    }

    public void close(final ChannelHandlerContext ctx, final ChannelPromise promise) {
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
        try {
            this.z.next_in = EmptyArrays.EMPTY_BYTES;
            this.z.next_in_index = 0;
            this.z.avail_in = 0;
            byte[] out = new byte[32];
            this.z.next_out = out;
            this.z.next_out_index = 0;
            this.z.avail_out = out.length;
            int resultCode = this.z.deflate(4);
            if (resultCode == 0 || resultCode == 1) {
                ByteBuf footer;
                if (this.z.next_out_index != 0) {
                    footer = Unpooled.wrappedBuffer(out, 0, this.z.next_out_index);
                } else {
                    footer = Unpooled.EMPTY_BUFFER;
                }
                this.z.deflateEnd();
                this.z.next_in = null;
                this.z.next_out = null;
                return ctx.writeAndFlush(footer, promise);
            }
            promise.setFailure(ZlibUtil.deflaterException(this.z, "compression failure", resultCode));
            return promise;
        } finally {
            this.z.deflateEnd();
            this.z.next_in = null;
            this.z.next_out = null;
        }
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }
}
