package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelPromiseNotifier;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.concurrent.EventExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.tools.bzip2.BZip2Constants;

public class Bzip2Encoder extends MessageToByteEncoder<ByteBuf> {
    private Bzip2BlockCompressor blockCompressor;
    private volatile ChannelHandlerContext ctx;
    private State currentState;
    private volatile boolean finished;
    private final int streamBlockSize;
    private int streamCRC;
    private final Bzip2BitWriter writer;

    private enum State {
        INIT,
        INIT_BLOCK,
        WRITE_DATA,
        CLOSE_BLOCK
    }

    public Bzip2Encoder() {
        this(9);
    }

    public Bzip2Encoder(int blockSizeMultiplier) {
        this.currentState = State.INIT;
        this.writer = new Bzip2BitWriter();
        if (blockSizeMultiplier < 1 || blockSizeMultiplier > 9) {
            throw new IllegalArgumentException("blockSizeMultiplier: " + blockSizeMultiplier + " (expected: 1-9)");
        }
        this.streamBlockSize = BZip2Constants.baseBlockSize * blockSizeMultiplier;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void encode(io.netty.channel.ChannelHandlerContext r7, io.netty.buffer.ByteBuf r8, io.netty.buffer.ByteBuf r9) throws java.lang.Exception {
        /*
        r6 = this;
        r3 = r6.finished;
        if (r3 == 0) goto L_0x002d;
    L_0x0004:
        r9.writeBytes(r8);
    L_0x0007:
        return;
    L_0x0008:
        r0 = r6.blockCompressor;
        r3 = r8.readableBytes();
        r4 = r0.availableSize();
        r2 = java.lang.Math.min(r3, r4);
        r3 = r8.readerIndex();
        r1 = r0.write(r8, r3, r2);
        r8.skipBytes(r1);
        r3 = r0.isFull();
        if (r3 != 0) goto L_0x006f;
    L_0x0027:
        r3 = r8.isReadable();
        if (r3 == 0) goto L_0x0007;
    L_0x002d:
        r3 = io.netty.handler.codec.compression.Bzip2Encoder.AnonymousClass4.$SwitchMap$io$netty$handler$codec$compression$Bzip2Encoder$State;
        r4 = r6.currentState;
        r4 = r4.ordinal();
        r3 = r3[r4];
        switch(r3) {
            case 1: goto L_0x0040;
            case 2: goto L_0x0059;
            case 3: goto L_0x0068;
            case 4: goto L_0x0073;
            default: goto L_0x003a;
        };
    L_0x003a:
        r3 = new java.lang.IllegalStateException;
        r3.<init>();
        throw r3;
    L_0x0040:
        r3 = 4;
        r9.ensureWritable(r3);
        r3 = 4348520; // 0x425a68 float:6.093574E-39 double:2.1484543E-317;
        r9.writeMedium(r3);
        r3 = r6.streamBlockSize;
        r4 = 100000; // 0x186a0 float:1.4013E-40 double:4.94066E-319;
        r3 = r3 / r4;
        r3 = r3 + 48;
        r9.writeByte(r3);
        r3 = io.netty.handler.codec.compression.Bzip2Encoder.State.INIT_BLOCK;
        r6.currentState = r3;
    L_0x0059:
        r3 = new io.netty.handler.codec.compression.Bzip2BlockCompressor;
        r4 = r6.writer;
        r5 = r6.streamBlockSize;
        r3.<init>(r4, r5);
        r6.blockCompressor = r3;
        r3 = io.netty.handler.codec.compression.Bzip2Encoder.State.WRITE_DATA;
        r6.currentState = r3;
    L_0x0068:
        r3 = r8.isReadable();
        if (r3 != 0) goto L_0x0008;
    L_0x006e:
        goto L_0x0007;
    L_0x006f:
        r3 = io.netty.handler.codec.compression.Bzip2Encoder.State.CLOSE_BLOCK;
        r6.currentState = r3;
    L_0x0073:
        r6.closeBlock(r9);
        r3 = io.netty.handler.codec.compression.Bzip2Encoder.State.INIT_BLOCK;
        r6.currentState = r3;
        goto L_0x002d;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.compression.Bzip2Encoder.encode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, io.netty.buffer.ByteBuf):void");
    }

    private void closeBlock(ByteBuf out) {
        Bzip2BlockCompressor blockCompressor = this.blockCompressor;
        if (!blockCompressor.isEmpty()) {
            blockCompressor.close(out);
            this.streamCRC = ((this.streamCRC << 1) | (this.streamCRC >>> 31)) ^ blockCompressor.crc();
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
                Bzip2Encoder.this.finishEncode(Bzip2Encoder.this.ctx(), promise).addListener(new ChannelPromiseNotifier(promise));
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

    private ChannelFuture finishEncode(ChannelHandlerContext ctx, ChannelPromise promise) {
        if (this.finished) {
            promise.setSuccess();
            return promise;
        }
        this.finished = true;
        ByteBuf footer = ctx.alloc().buffer();
        closeBlock(footer);
        int streamCRC = this.streamCRC;
        Bzip2BitWriter writer = this.writer;
        try {
            writer.writeBits(footer, 24, 1536581);
            writer.writeBits(footer, 24, 3690640);
            writer.writeInt(footer, streamCRC);
            writer.flush(footer);
            return ctx.writeAndFlush(footer, promise);
        } finally {
            this.blockCompressor = null;
        }
    }

    private ChannelHandlerContext ctx() {
        ChannelHandlerContext ctx = this.ctx;
        if (ctx != null) {
            return ctx;
        }
        throw new IllegalStateException("not added to a pipeline");
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }
}
