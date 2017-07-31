package io.netty.handler.proxy;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.PendingWriteQueue;
import io.netty.handler.codec.rtsp.RtspHeaders.Values;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.ScheduledFuture;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.SocketAddress;
import java.nio.channels.ConnectionPendingException;
import java.util.concurrent.TimeUnit;

public abstract class ProxyHandler extends ChannelDuplexHandler {
    static final String AUTH_NONE = "none";
    private static final long DEFAULT_CONNECT_TIMEOUT_MILLIS = 10000;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ProxyHandler.class);
    private final LazyChannelPromise connectPromise = new LazyChannelPromise();
    private ScheduledFuture<?> connectTimeoutFuture;
    private volatile long connectTimeoutMillis = DEFAULT_CONNECT_TIMEOUT_MILLIS;
    private volatile ChannelHandlerContext ctx;
    private volatile SocketAddress destinationAddress;
    private boolean finished;
    private boolean flushedPrematurely;
    private PendingWriteQueue pendingWrites;
    private final SocketAddress proxyAddress;
    private boolean suppressChannelReadComplete;
    private final ChannelFutureListener writeListener = new ChannelFutureListener() {
        public void operationComplete(ChannelFuture future) throws Exception {
            if (!future.isSuccess()) {
                ProxyHandler.this.setConnectFailure(future.cause());
            }
        }
    };

    private final class LazyChannelPromise extends DefaultPromise<Channel> {
        private LazyChannelPromise() {
        }

        protected EventExecutor executor() {
            if (ProxyHandler.this.ctx != null) {
                return ProxyHandler.this.ctx.executor();
            }
            throw new IllegalStateException();
        }
    }

    protected abstract void addCodec(ChannelHandlerContext channelHandlerContext) throws Exception;

    public abstract String authScheme();

    public final void channelRead(io.netty.channel.ChannelHandlerContext r5, java.lang.Object r6) throws java.lang.Exception {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(Unknown Source)
	at java.util.HashMap$KeyIterator.next(Unknown Source)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:80)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r4 = this;
        r3 = r4.finished;
        if (r3 == 0) goto L_0x000b;
    L_0x0004:
        r3 = 0;
        r4.suppressChannelReadComplete = r3;
        r5.fireChannelRead(r6);
    L_0x000a:
        return;
    L_0x000b:
        r3 = 1;
        r4.suppressChannelReadComplete = r3;
        r0 = 0;
        r1 = r4.handleResponse(r5, r6);	 Catch:{ Throwable -> 0x0021, all -> 0x002c }
        if (r1 == 0) goto L_0x0018;	 Catch:{ Throwable -> 0x0021, all -> 0x002c }
    L_0x0015:
        r4.setConnectSuccess();	 Catch:{ Throwable -> 0x0021, all -> 0x002c }
    L_0x0018:
        io.netty.util.ReferenceCountUtil.release(r6);
        if (r0 == 0) goto L_0x000a;
    L_0x001d:
        r4.setConnectFailure(r0);
        goto L_0x000a;
    L_0x0021:
        r2 = move-exception;
        r0 = r2;
        io.netty.util.ReferenceCountUtil.release(r6);
        if (r0 == 0) goto L_0x000a;
    L_0x0028:
        r4.setConnectFailure(r0);
        goto L_0x000a;
    L_0x002c:
        r3 = move-exception;
        io.netty.util.ReferenceCountUtil.release(r6);
        if (r0 == 0) goto L_0x0035;
    L_0x0032:
        r4.setConnectFailure(r0);
    L_0x0035:
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.proxy.ProxyHandler.channelRead(io.netty.channel.ChannelHandlerContext, java.lang.Object):void");
    }

    protected abstract boolean handleResponse(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception;

    protected abstract Object newInitialMessage(ChannelHandlerContext channelHandlerContext) throws Exception;

    public abstract String protocol();

    protected abstract void removeDecoder(ChannelHandlerContext channelHandlerContext) throws Exception;

    protected abstract void removeEncoder(ChannelHandlerContext channelHandlerContext) throws Exception;

    protected ProxyHandler(SocketAddress proxyAddress) {
        if (proxyAddress == null) {
            throw new NullPointerException("proxyAddress");
        }
        this.proxyAddress = proxyAddress;
    }

    public final <T extends SocketAddress> T proxyAddress() {
        return this.proxyAddress;
    }

    public final <T extends SocketAddress> T destinationAddress() {
        return this.destinationAddress;
    }

    public final boolean isConnected() {
        return this.connectPromise.isSuccess();
    }

    public final Future<Channel> connectFuture() {
        return this.connectPromise;
    }

    public final long connectTimeoutMillis() {
        return this.connectTimeoutMillis;
    }

    public final void setConnectTimeoutMillis(long connectTimeoutMillis) {
        if (connectTimeoutMillis <= 0) {
            connectTimeoutMillis = 0;
        }
        this.connectTimeoutMillis = connectTimeoutMillis;
    }

    public final void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        addCodec(ctx);
        if (ctx.channel().isActive()) {
            sendInitialMessage(ctx);
        }
    }

    public final void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        if (this.destinationAddress != null) {
            promise.setFailure(new ConnectionPendingException());
            return;
        }
        this.destinationAddress = remoteAddress;
        ctx.connect(this.proxyAddress, localAddress, promise);
    }

    public final void channelActive(ChannelHandlerContext ctx) throws Exception {
        sendInitialMessage(ctx);
        ctx.fireChannelActive();
    }

    private void sendInitialMessage(ChannelHandlerContext ctx) throws Exception {
        long connectTimeoutMillis = this.connectTimeoutMillis;
        if (connectTimeoutMillis > 0) {
            this.connectTimeoutFuture = ctx.executor().schedule(new Runnable() {
                public void run() {
                    if (!ProxyHandler.this.connectPromise.isDone()) {
                        ProxyHandler.this.setConnectFailure(new ProxyConnectException(ProxyHandler.this.exceptionMessage(Values.TIMEOUT)));
                    }
                }
            }, connectTimeoutMillis, TimeUnit.MILLISECONDS);
        }
        Object initialMessage = newInitialMessage(ctx);
        if (initialMessage != null) {
            sendToProxyServer(initialMessage);
        }
    }

    protected final void sendToProxyServer(Object msg) {
        this.ctx.writeAndFlush(msg).addListener(this.writeListener);
    }

    public final void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (this.finished) {
            ctx.fireChannelInactive();
        } else {
            setConnectFailure(new ProxyConnectException(exceptionMessage("disconnected")));
        }
    }

    public final void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (this.finished) {
            ctx.fireExceptionCaught(cause);
        } else {
            setConnectFailure(cause);
        }
    }

    private void setConnectSuccess() {
        this.finished = true;
        if (this.connectTimeoutFuture != null) {
            this.connectTimeoutFuture.cancel(false);
        }
        if (this.connectPromise.trySuccess(this.ctx.channel())) {
            boolean removedCodec = true & safeRemoveEncoder();
            this.ctx.fireUserEventTriggered(new ProxyConnectionEvent(protocol(), authScheme(), this.proxyAddress, this.destinationAddress));
            if (removedCodec & safeRemoveDecoder()) {
                writePendingWrites();
                if (this.flushedPrematurely) {
                    this.ctx.flush();
                    return;
                }
                return;
            }
            Exception cause = new ProxyConnectException("failed to remove all codec handlers added by the proxy handler; bug?");
            failPendingWrites(cause);
            this.ctx.fireExceptionCaught(cause);
            this.ctx.close();
        }
    }

    private boolean safeRemoveDecoder() {
        try {
            removeDecoder(this.ctx);
            return true;
        } catch (Throwable e) {
            logger.warn("Failed to remove proxy decoders:", e);
            return false;
        }
    }

    private boolean safeRemoveEncoder() {
        try {
            removeEncoder(this.ctx);
            return true;
        } catch (Throwable e) {
            logger.warn("Failed to remove proxy encoders:", e);
            return false;
        }
    }

    private void setConnectFailure(Throwable cause) {
        this.finished = true;
        if (this.connectTimeoutFuture != null) {
            this.connectTimeoutFuture.cancel(false);
        }
        if (!(cause instanceof ProxyConnectException)) {
            cause = new ProxyConnectException(exceptionMessage(cause.toString()), cause);
        }
        if (this.connectPromise.tryFailure(cause)) {
            safeRemoveDecoder();
            safeRemoveEncoder();
            failPendingWrites(cause);
            this.ctx.fireExceptionCaught(cause);
            this.ctx.close();
        }
    }

    protected final String exceptionMessage(String msg) {
        if (msg == null) {
            msg = "";
        }
        StringBuilder buf = new StringBuilder(msg.length() + 128).append(protocol()).append(", ").append(authScheme()).append(", ").append(this.proxyAddress).append(" => ").append(this.destinationAddress);
        if (!msg.isEmpty()) {
            buf.append(", ").append(msg);
        }
        return buf.toString();
    }

    public final void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        if (this.suppressChannelReadComplete) {
            this.suppressChannelReadComplete = false;
            if (!ctx.channel().config().isAutoRead()) {
                ctx.read();
                return;
            }
            return;
        }
        ctx.fireChannelReadComplete();
    }

    public final void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (this.finished) {
            writePendingWrites();
            ctx.write(msg, promise);
            return;
        }
        addPendingWrite(ctx, msg, promise);
    }

    public final void flush(ChannelHandlerContext ctx) throws Exception {
        if (this.finished) {
            writePendingWrites();
            ctx.flush();
            return;
        }
        this.flushedPrematurely = true;
    }

    private void writePendingWrites() {
        if (this.pendingWrites != null) {
            this.pendingWrites.removeAndWriteAll();
            this.pendingWrites = null;
        }
    }

    private void failPendingWrites(Throwable cause) {
        if (this.pendingWrites != null) {
            this.pendingWrites.removeAndFailAll(cause);
            this.pendingWrites = null;
        }
    }

    private void addPendingWrite(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        PendingWriteQueue pendingWrites = this.pendingWrites;
        if (pendingWrites == null) {
            pendingWrites = new PendingWriteQueue(ctx);
            this.pendingWrites = pendingWrites;
        }
        pendingWrites.add(msg, promise);
    }
}
