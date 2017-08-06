package io.netty.handler.stream;

import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayDeque;
import java.util.Queue;

public class ChunkedWriteHandler extends ChannelDuplexHandler {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ChunkedWriteHandler.class);
    private volatile ChannelHandlerContext ctx;
    private PendingWrite currentWrite;
    private final Queue<PendingWrite> queue = new ArrayDeque();

    @Deprecated
    public ChunkedWriteHandler(int maxPendingWrites) {
        if (maxPendingWrites <= 0) {
            throw new IllegalArgumentException("maxPendingWrites: " + maxPendingWrites + " (expected: > 0)");
        }
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }

    public void resumeTransfer() {
        ChannelHandlerContext ctx = this.ctx;
        if (ctx != null) {
            if (ctx.executor().inEventLoop()) {
                try {
                    doFlush(ctx);
                    return;
                } catch (Exception e) {
                    if (logger.isWarnEnabled()) {
                        logger.warn("Unexpected exception while sending chunks.", e);
                        return;
                    }
                    return;
                }
            }
            ctx.executor().execute(new 1(this, ctx));
        }
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        this.queue.add(new PendingWrite(msg, promise));
    }

    public void flush(ChannelHandlerContext ctx) throws Exception {
        if (!doFlush(ctx)) {
            ctx.flush();
        }
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        doFlush(ctx);
        ctx.fireChannelInactive();
    }

    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        if (ctx.channel().isWritable()) {
            doFlush(ctx);
        }
        ctx.fireChannelWritabilityChanged();
    }

    private void discard(Throwable cause) {
        while (true) {
            PendingWrite currentWrite = this.currentWrite;
            if (this.currentWrite == null) {
                currentWrite = (PendingWrite) this.queue.poll();
            } else {
                this.currentWrite = null;
            }
            if (currentWrite != null) {
                ChunkedInput<?> message = currentWrite.msg;
                if (message instanceof ChunkedInput) {
                    ChunkedInput<?> in = message;
                    try {
                        if (in.isEndOfInput()) {
                            currentWrite.success(in.length());
                        } else {
                            if (cause == null) {
                                cause = new ClosedChannelException();
                            }
                            currentWrite.fail(cause);
                        }
                        closeInput(in);
                    } catch (Exception e) {
                        currentWrite.fail(e);
                        logger.warn(ChunkedInput.class.getSimpleName() + ".isEndOfInput() failed", e);
                        closeInput(in);
                    }
                } else {
                    if (cause == null) {
                        cause = new ClosedChannelException();
                    }
                    currentWrite.fail(cause);
                }
            } else {
                return;
            }
        }
    }

    private boolean doFlush(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        if (channel.isActive()) {
            boolean flushed = false;
            ByteBufAllocator allocator = ctx.alloc();
            while (channel.isWritable()) {
                if (this.currentWrite == null) {
                    this.currentWrite = (PendingWrite) this.queue.poll();
                }
                if (this.currentWrite == null) {
                    return flushed;
                }
                PendingWrite currentWrite = this.currentWrite;
                ChunkedInput<?> pendingMessage = currentWrite.msg;
                if (pendingMessage instanceof ChunkedInput) {
                    ChunkedInput<?> chunks = pendingMessage;
                    Object obj = null;
                    try {
                        boolean suspend;
                        obj = chunks.readChunk(allocator);
                        boolean endOfInput = chunks.isEndOfInput();
                        if (obj != null) {
                            suspend = false;
                        } else if (endOfInput) {
                            suspend = false;
                        } else {
                            suspend = true;
                        }
                        if (suspend) {
                            return flushed;
                        }
                        if (obj == null) {
                            obj = Unpooled.EMPTY_BUFFER;
                        }
                        ChannelFuture f = ctx.write(obj);
                        if (endOfInput) {
                            this.currentWrite = null;
                            f.addListener(new 2(this, currentWrite, chunks));
                        } else if (channel.isWritable()) {
                            f.addListener(new 3(this, pendingMessage, currentWrite, chunks));
                        } else {
                            f.addListener(new 4(this, pendingMessage, currentWrite, chunks, channel));
                        }
                    } catch (Throwable t) {
                        this.currentWrite = null;
                        if (obj != null) {
                            ReferenceCountUtil.release(obj);
                        }
                        currentWrite.fail(t);
                        closeInput(chunks);
                        return flushed;
                    }
                }
                ctx.write(pendingMessage, currentWrite.promise);
                this.currentWrite = null;
                ctx.flush();
                flushed = true;
                if (!channel.isActive()) {
                    discard(new ClosedChannelException());
                    return true;
                }
            }
            return flushed;
        }
        discard(null);
        return false;
    }

    static void closeInput(ChunkedInput<?> chunks) {
        try {
            chunks.close();
        } catch (Throwable t) {
            if (logger.isWarnEnabled()) {
                logger.warn("Failed to close a_isRightVersion chunked input.", t);
            }
        }
    }
}
