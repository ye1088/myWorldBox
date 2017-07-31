package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelPromiseNotifier;
import io.netty.channel.PendingWriteQueue;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.ImmediateExecutor;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLEngineResult.Status;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

public class SslHandler extends ByteToMessageDecoder implements ChannelOutboundHandler {
    static final /* synthetic */ boolean $assertionsDisabled = (!SslHandler.class.desiredAssertionStatus());
    private static final ClosedChannelException CHANNEL_CLOSED = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), SslHandler.class, "channelInactive(...)"));
    private static final SSLException HANDSHAKE_TIMED_OUT = ((SSLException) ThrowableUtil.unknownStackTrace(new SSLException("handshake timed out"), SslHandler.class, "handshake(...)"));
    private static final Pattern IGNORABLE_CLASS_IN_STACK = Pattern.compile("^.*(?:Socket|Datagram|Sctp|Udt)Channel.*$");
    private static final Pattern IGNORABLE_ERROR_MESSAGE = Pattern.compile("^.*(?:connection.*(?:reset|closed|abort|broken)|broken.*pipe).*$", 2);
    private static final SSLException SSLENGINE_CLOSED = ((SSLException) ThrowableUtil.unknownStackTrace(new SSLException("SSLEngine closed already"), SslHandler.class, "wrap(...)"));
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(SslHandler.class);
    private volatile long closeNotifyTimeoutMillis;
    private volatile ChannelHandlerContext ctx;
    private final Executor delegatedTaskExecutor;
    private final SSLEngine engine;
    private boolean firedChannelRead;
    private boolean flushedBeforeHandshake;
    private Promise<Channel> handshakePromise;
    private volatile long handshakeTimeoutMillis;
    private final int maxPacketBufferSize;
    private boolean needsFlush;
    private boolean outboundClosed;
    private int packetLength;
    private PendingWriteQueue pendingUnencryptedWrites;
    private boolean readDuringHandshake;
    private boolean sentFirstMessage;
    private final ByteBuffer[] singleBuffer;
    private final LazyChannelPromise sslCloseFuture;
    private final boolean startTls;
    private final boolean wantsDirectBuffer;
    private final boolean wantsLargeOutboundNetworkBuffer;

    public SslHandler(SSLEngine engine) {
        this(engine, false);
    }

    public SslHandler(SSLEngine engine, boolean startTls) {
        this(engine, startTls, ImmediateExecutor.INSTANCE);
    }

    @Deprecated
    public SslHandler(SSLEngine engine, Executor delegatedTaskExecutor) {
        this(engine, false, delegatedTaskExecutor);
    }

    @Deprecated
    public SslHandler(SSLEngine engine, boolean startTls, Executor delegatedTaskExecutor) {
        boolean z = true;
        this.singleBuffer = new ByteBuffer[1];
        this.handshakePromise = new LazyChannelPromise(this, null);
        this.sslCloseFuture = new LazyChannelPromise(this, null);
        this.handshakeTimeoutMillis = 10000;
        this.closeNotifyTimeoutMillis = 3000;
        if (engine == null) {
            throw new NullPointerException("engine");
        } else if (delegatedTaskExecutor == null) {
            throw new NullPointerException("delegatedTaskExecutor");
        } else {
            this.engine = engine;
            this.delegatedTaskExecutor = delegatedTaskExecutor;
            this.startTls = startTls;
            this.maxPacketBufferSize = engine.getSession().getPacketBufferSize();
            boolean opensslEngine = engine instanceof OpenSslEngine;
            this.wantsDirectBuffer = opensslEngine;
            if (opensslEngine) {
                z = false;
            }
            this.wantsLargeOutboundNetworkBuffer = z;
            setCumulator(opensslEngine ? COMPOSITE_CUMULATOR : MERGE_CUMULATOR);
        }
    }

    public long getHandshakeTimeoutMillis() {
        return this.handshakeTimeoutMillis;
    }

    public void setHandshakeTimeout(long handshakeTimeout, TimeUnit unit) {
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        setHandshakeTimeoutMillis(unit.toMillis(handshakeTimeout));
    }

    public void setHandshakeTimeoutMillis(long handshakeTimeoutMillis) {
        if (handshakeTimeoutMillis < 0) {
            throw new IllegalArgumentException("handshakeTimeoutMillis: " + handshakeTimeoutMillis + " (expected: >= 0)");
        }
        this.handshakeTimeoutMillis = handshakeTimeoutMillis;
    }

    public long getCloseNotifyTimeoutMillis() {
        return this.closeNotifyTimeoutMillis;
    }

    public void setCloseNotifyTimeout(long closeNotifyTimeout, TimeUnit unit) {
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        setCloseNotifyTimeoutMillis(unit.toMillis(closeNotifyTimeout));
    }

    public void setCloseNotifyTimeoutMillis(long closeNotifyTimeoutMillis) {
        if (closeNotifyTimeoutMillis < 0) {
            throw new IllegalArgumentException("closeNotifyTimeoutMillis: " + closeNotifyTimeoutMillis + " (expected: >= 0)");
        }
        this.closeNotifyTimeoutMillis = closeNotifyTimeoutMillis;
    }

    public SSLEngine engine() {
        return this.engine;
    }

    public String applicationProtocol() {
        SSLSession sess = engine().getSession();
        if (sess instanceof ApplicationProtocolAccessor) {
            return ((ApplicationProtocolAccessor) sess).getApplicationProtocol();
        }
        return null;
    }

    public Future<Channel> handshakeFuture() {
        return this.handshakePromise;
    }

    public ChannelFuture close() {
        return close(this.ctx.newPromise());
    }

    public ChannelFuture close(ChannelPromise future) {
        ChannelHandlerContext ctx = this.ctx;
        ctx.executor().execute(new 1(this, ctx, future));
        return future;
    }

    public Future<Channel> sslCloseFuture() {
        return this.sslCloseFuture;
    }

    public void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {
        if (!this.pendingUnencryptedWrites.isEmpty()) {
            this.pendingUnencryptedWrites.removeAndFailAll(new ChannelException("Pending write on removal of SslHandler"));
        }
        if (this.engine instanceof ReferenceCountedOpenSslEngine) {
            ((ReferenceCountedOpenSslEngine) this.engine).release();
        }
    }

    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        ctx.bind(localAddress, promise);
    }

    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        ctx.connect(remoteAddress, localAddress, promise);
    }

    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        ctx.deregister(promise);
    }

    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        closeOutboundAndChannel(ctx, promise, true);
    }

    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        closeOutboundAndChannel(ctx, promise, false);
    }

    public void read(ChannelHandlerContext ctx) throws Exception {
        if (!this.handshakePromise.isDone()) {
            this.readDuringHandshake = true;
        }
        ctx.read();
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof ByteBuf) {
            this.pendingUnencryptedWrites.add(msg, promise);
            return;
        }
        promise.setFailure(new UnsupportedMessageTypeException(msg, new Class[]{ByteBuf.class}));
    }

    public void flush(ChannelHandlerContext ctx) throws Exception {
        if (!this.startTls || this.sentFirstMessage) {
            if (this.pendingUnencryptedWrites.isEmpty()) {
                this.pendingUnencryptedWrites.add(Unpooled.EMPTY_BUFFER, ctx.newPromise());
            }
            if (!this.handshakePromise.isDone()) {
                this.flushedBeforeHandshake = true;
            }
            try {
                wrap(ctx, false);
            } catch (Throwable cause) {
                this.pendingUnencryptedWrites.removeAndFailAll(cause);
                PlatformDependent.throwException(cause);
            } finally {
                ctx.flush();
            }
        } else {
            this.sentFirstMessage = true;
            this.pendingUnencryptedWrites.removeAndWriteAll();
            ctx.flush();
        }
    }

    private void wrap(ChannelHandlerContext ctx, boolean inUnwrap) throws SSLException {
        ByteBuf out = null;
        ChannelPromise promise = null;
        ByteBufAllocator alloc = ctx.alloc();
        while (true) {
            Object msg = this.pendingUnencryptedWrites.current();
            if (msg == null) {
                finishWrap(ctx, out, promise, inUnwrap, false);
                return;
            }
            ByteBuf buf = (ByteBuf) msg;
            if (out == null) {
                out = allocateOutNetBuf(ctx, buf.readableBytes());
            }
            SSLEngineResult result = wrap(alloc, this.engine, buf, out);
            if (result.getStatus() == Status.CLOSED) {
                this.pendingUnencryptedWrites.removeAndFailAll(SSLENGINE_CLOSED);
                finishWrap(ctx, out, promise, inUnwrap, false);
                return;
            }
            try {
                if (buf.isReadable()) {
                    promise = null;
                } else {
                    promise = this.pendingUnencryptedWrites.remove();
                }
                switch (9.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[result.getHandshakeStatus().ordinal()]) {
                    case 1:
                        runDelegatedTasks();
                        continue;
                    case 2:
                        setHandshakeSuccess();
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        finishWrap(ctx, out, promise, inUnwrap, true);
                        return;
                    default:
                        throw new IllegalStateException("Unknown handshake status: " + result.getHandshakeStatus());
                }
                setHandshakeSuccessIfStillHandshaking();
                finishWrap(ctx, out, promise, inUnwrap, false);
                promise = null;
                out = null;
                continue;
            } catch (Throwable e) {
                setHandshakeFailure(ctx, e);
                throw e;
            } catch (Throwable th) {
                Throwable th2 = th;
                finishWrap(ctx, out, promise, inUnwrap, false);
            }
        }
    }

    private void finishWrap(ChannelHandlerContext ctx, ByteBuf out, ChannelPromise promise, boolean inUnwrap, boolean needUnwrap) {
        if (out == null) {
            out = Unpooled.EMPTY_BUFFER;
        } else if (!out.isReadable()) {
            out.release();
            out = Unpooled.EMPTY_BUFFER;
        }
        if (promise != null) {
            ctx.write(out, promise);
        } else {
            ctx.write(out);
        }
        if (inUnwrap) {
            this.needsFlush = true;
        }
        if (needUnwrap) {
            readIfNeeded(ctx);
        }
    }

    private void wrapNonAppData(ChannelHandlerContext ctx, boolean inUnwrap) throws SSLException {
        ByteBuf out = null;
        ByteBufAllocator alloc = ctx.alloc();
        while (true) {
            if (out == null) {
                try {
                    out = allocateOutNetBuf(ctx, 0);
                } catch (SSLException e) {
                    setHandshakeFailure(ctx, e);
                    flushIfNeeded(ctx);
                    throw e;
                } catch (Throwable th) {
                    if (out != null) {
                        out.release();
                    }
                }
            }
            SSLEngineResult result = wrap(alloc, this.engine, Unpooled.EMPTY_BUFFER, out);
            if (result.bytesProduced() > 0) {
                ctx.write(out);
                if (inUnwrap) {
                    this.needsFlush = true;
                }
                out = null;
            }
            switch (9.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[result.getHandshakeStatus().ordinal()]) {
                case 1:
                    runDelegatedTasks();
                    break;
                case 2:
                    setHandshakeSuccess();
                    break;
                case 3:
                    setHandshakeSuccessIfStillHandshaking();
                    if (!inUnwrap) {
                        unwrapNonAppData(ctx);
                        break;
                    }
                    break;
                case 4:
                    break;
                case 5:
                    if (!inUnwrap) {
                        unwrapNonAppData(ctx);
                        break;
                    }
                    break;
                default:
                    throw new IllegalStateException("Unknown handshake status: " + result.getHandshakeStatus());
            }
            if (result.bytesProduced() != 0) {
                if (result.bytesConsumed() == 0 && result.getHandshakeStatus() == HandshakeStatus.NOT_HANDSHAKING) {
                }
            }
            if (out != null) {
                out.release();
                return;
            }
            return;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private javax.net.ssl.SSLEngineResult wrap(io.netty.buffer.ByteBufAllocator r11, javax.net.ssl.SSLEngine r12, io.netty.buffer.ByteBuf r13, io.netty.buffer.ByteBuf r14) throws javax.net.ssl.SSLException {
        /*
        r10 = this;
        r9 = 0;
        r8 = 0;
        r1 = 0;
        r4 = r13.readerIndex();	 Catch:{ all -> 0x007c }
        r3 = r13.readableBytes();	 Catch:{ all -> 0x007c }
        r6 = r13.isDirect();	 Catch:{ all -> 0x007c }
        if (r6 != 0) goto L_0x0015;
    L_0x0011:
        r6 = r10.wantsDirectBuffer;	 Catch:{ all -> 0x007c }
        if (r6 != 0) goto L_0x006a;
    L_0x0015:
        r6 = r13 instanceof io.netty.buffer.CompositeByteBuf;	 Catch:{ all -> 0x007c }
        if (r6 != 0) goto L_0x0065;
    L_0x0019:
        r6 = r13.nioBufferCount();	 Catch:{ all -> 0x007c }
        r7 = 1;
        if (r6 != r7) goto L_0x0065;
    L_0x0020:
        r0 = r10.singleBuffer;	 Catch:{ all -> 0x007c }
        r6 = 0;
        r7 = r13.internalNioBuffer(r4, r3);	 Catch:{ all -> 0x007c }
        r0[r6] = r7;	 Catch:{ all -> 0x007c }
    L_0x0029:
        r6 = r14.writerIndex();	 Catch:{ all -> 0x007c }
        r7 = r14.writableBytes();	 Catch:{ all -> 0x007c }
        r2 = r14.nioBuffer(r6, r7);	 Catch:{ all -> 0x007c }
        r5 = r12.wrap(r0, r2);	 Catch:{ all -> 0x007c }
        r6 = r5.bytesConsumed();	 Catch:{ all -> 0x007c }
        r13.skipBytes(r6);	 Catch:{ all -> 0x007c }
        r6 = r14.writerIndex();	 Catch:{ all -> 0x007c }
        r7 = r5.bytesProduced();	 Catch:{ all -> 0x007c }
        r6 = r6 + r7;
        r14.writerIndex(r6);	 Catch:{ all -> 0x007c }
        r6 = io.netty.handler.ssl.SslHandler.9.$SwitchMap$javax$net$ssl$SSLEngineResult$Status;	 Catch:{ all -> 0x007c }
        r7 = r5.getStatus();	 Catch:{ all -> 0x007c }
        r7 = r7.ordinal();	 Catch:{ all -> 0x007c }
        r6 = r6[r7];	 Catch:{ all -> 0x007c }
        switch(r6) {
            case 1: goto L_0x0087;
            default: goto L_0x005b;
        };
    L_0x005b:
        r6 = r10.singleBuffer;
        r6[r8] = r9;
        if (r1 == 0) goto L_0x0064;
    L_0x0061:
        r1.release();
    L_0x0064:
        return r5;
    L_0x0065:
        r0 = r13.nioBuffers();	 Catch:{ all -> 0x007c }
        goto L_0x0029;
    L_0x006a:
        r1 = r11.directBuffer(r3);	 Catch:{ all -> 0x007c }
        r1.writeBytes(r13, r4, r3);	 Catch:{ all -> 0x007c }
        r0 = r10.singleBuffer;	 Catch:{ all -> 0x007c }
        r6 = 0;
        r7 = 0;
        r7 = r1.internalNioBuffer(r7, r3);	 Catch:{ all -> 0x007c }
        r0[r6] = r7;	 Catch:{ all -> 0x007c }
        goto L_0x0029;
    L_0x007c:
        r6 = move-exception;
        r7 = r10.singleBuffer;
        r7[r8] = r9;
        if (r1 == 0) goto L_0x0086;
    L_0x0083:
        r1.release();
    L_0x0086:
        throw r6;
    L_0x0087:
        r6 = r10.maxPacketBufferSize;	 Catch:{ all -> 0x007c }
        r14.ensureWritable(r6);	 Catch:{ all -> 0x007c }
        goto L_0x0029;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.SslHandler.wrap(io.netty.buffer.ByteBufAllocator, javax.net.ssl.SSLEngine, io.netty.buffer.ByteBuf, io.netty.buffer.ByteBuf):javax.net.ssl.SSLEngineResult");
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        setHandshakeFailure(ctx, CHANNEL_CLOSED, !this.outboundClosed);
        super.channelInactive(ctx);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (ignoreException(cause)) {
            if (logger.isDebugEnabled()) {
                logger.debug("{} Swallowing a harmless 'connection reset by peer / broken pipe' error that occurred while writing close_notify in response to the peer's close_notify", ctx.channel(), cause);
            }
            if (ctx.channel().isActive()) {
                ctx.close();
                return;
            }
            return;
        }
        ctx.fireExceptionCaught(cause);
    }

    private boolean ignoreException(Throwable t) {
        if (!(t instanceof SSLException) && (t instanceof IOException) && this.sslCloseFuture.isDone()) {
            if (IGNORABLE_ERROR_MESSAGE.matcher(String.valueOf(t.getMessage()).toLowerCase()).matches()) {
                return true;
            }
            for (StackTraceElement element : t.getStackTrace()) {
                String classname = element.getClassName();
                String methodname = element.getMethodName();
                if (!classname.startsWith("io.netty.") && "read".equals(methodname)) {
                    if (IGNORABLE_CLASS_IN_STACK.matcher(classname).matches()) {
                        return true;
                    }
                    try {
                        Class<?> clazz = PlatformDependent.getClassLoader(getClass()).loadClass(classname);
                        if (SocketChannel.class.isAssignableFrom(clazz) || DatagramChannel.class.isAssignableFrom(clazz)) {
                            return true;
                        }
                        if (PlatformDependent.javaVersion() >= 7 && "com.sun.nio.sctp.SctpChannel".equals(clazz.getSuperclass().getName())) {
                            return true;
                        }
                    } catch (ClassNotFoundException e) {
                    }
                }
            }
        }
        return false;
    }

    public static boolean isEncrypted(ByteBuf buffer) {
        if (buffer.readableBytes() >= 5) {
            return SslUtils.getEncryptedPacketLength(buffer, buffer.readerIndex()) != -1;
        } else {
            throw new IllegalArgumentException("buffer must have at least 5 readable bytes");
        }
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws SSLException {
        int startOffset = in.readerIndex();
        int endOffset = in.writerIndex();
        int offset = startOffset;
        int totalLength = 0;
        if (this.packetLength > 0) {
            if (endOffset - startOffset >= this.packetLength) {
                offset += this.packetLength;
                totalLength = this.packetLength;
                this.packetLength = 0;
            } else {
                return;
            }
        }
        boolean nonSslRecord = false;
        while (totalLength < 18713) {
            int readableBytes = endOffset - offset;
            if (readableBytes < 5) {
                break;
            }
            int packetLength = SslUtils.getEncryptedPacketLength(in, offset);
            if (packetLength == -1) {
                nonSslRecord = true;
                break;
            } else if ($assertionsDisabled || packetLength > 0) {
                if (packetLength <= readableBytes) {
                    int newTotalLength = totalLength + packetLength;
                    if (newTotalLength > 18713) {
                        break;
                    }
                    offset += packetLength;
                    totalLength = newTotalLength;
                } else {
                    this.packetLength = packetLength;
                    break;
                }
            } else {
                throw new AssertionError();
            }
        }
        if (totalLength > 0) {
            in.skipBytes(totalLength);
            boolean decoded = unwrap(ctx, in, startOffset, totalLength);
            if (!this.firedChannelRead) {
                this.firedChannelRead = decoded;
            }
        }
        if (nonSslRecord) {
            NotSslRecordException e = new NotSslRecordException("not an SSL/TLS record: " + ByteBufUtil.hexDump(in));
            in.skipBytes(in.readableBytes());
            ctx.fireExceptionCaught(e);
            setHandshakeFailure(ctx, e);
        }
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        discardSomeReadBytes();
        flushIfNeeded(ctx);
        readIfNeeded(ctx);
        this.firedChannelRead = false;
        ctx.fireChannelReadComplete();
    }

    private void readIfNeeded(ChannelHandlerContext ctx) {
        if (!ctx.channel().config().isAutoRead()) {
            if (!this.firedChannelRead || !this.handshakePromise.isDone()) {
                ctx.read();
            }
        }
    }

    private void flushIfNeeded(ChannelHandlerContext ctx) {
        if (this.needsFlush) {
            this.needsFlush = false;
            ctx.flush();
        }
    }

    private void unwrapNonAppData(ChannelHandlerContext ctx) throws SSLException {
        unwrap(ctx, Unpooled.EMPTY_BUFFER, 0, 0);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean unwrap(io.netty.channel.ChannelHandlerContext r20, io.netty.buffer.ByteBuf r21, int r22, int r23) throws javax.net.ssl.SSLException {
        /*
        r19 = this;
        r10 = 0;
        r18 = 0;
        r13 = 0;
        r0 = r19;
        r1 = r20;
        r2 = r23;
        r8 = r0.allocate(r1, r2);
    L_0x000e:
        r0 = r19;
        r4 = r0.engine;	 Catch:{ SSLException -> 0x0062 }
        r3 = r19;
        r5 = r21;
        r6 = r22;
        r7 = r23;
        r16 = r3.unwrap(r4, r5, r6, r7, r8);	 Catch:{ SSLException -> 0x0062 }
        r17 = r16.getStatus();	 Catch:{ SSLException -> 0x0062 }
        r12 = r16.getHandshakeStatus();	 Catch:{ SSLException -> 0x0062 }
        r14 = r16.bytesProduced();	 Catch:{ SSLException -> 0x0062 }
        r9 = r16.bytesConsumed();	 Catch:{ SSLException -> 0x0062 }
        r22 = r22 + r9;
        r23 = r23 - r9;
        r3 = io.netty.handler.ssl.SslHandler.9.$SwitchMap$javax$net$ssl$SSLEngineResult$Status;	 Catch:{ SSLException -> 0x0062 }
        r4 = r17.ordinal();	 Catch:{ SSLException -> 0x0062 }
        r3 = r3[r4];	 Catch:{ SSLException -> 0x0062 }
        switch(r3) {
            case 1: goto L_0x0079;
            case 2: goto L_0x00a0;
            default: goto L_0x003d;
        };	 Catch:{ SSLException -> 0x0062 }
    L_0x003d:
        r3 = io.netty.handler.ssl.SslHandler.9.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus;	 Catch:{ SSLException -> 0x0062 }
        r4 = r12.ordinal();	 Catch:{ SSLException -> 0x0062 }
        r3 = r3[r4];	 Catch:{ SSLException -> 0x0062 }
        switch(r3) {
            case 1: goto L_0x00df;
            case 2: goto L_0x00e3;
            case 3: goto L_0x00e9;
            case 4: goto L_0x00a2;
            case 5: goto L_0x00aa;
            default: goto L_0x0048;
        };	 Catch:{ SSLException -> 0x0062 }
    L_0x0048:
        r3 = new java.lang.IllegalStateException;	 Catch:{ SSLException -> 0x0062 }
        r4 = new java.lang.StringBuilder;	 Catch:{ SSLException -> 0x0062 }
        r4.<init>();	 Catch:{ SSLException -> 0x0062 }
        r5 = "unknown handshake status: ";
        r4 = r4.append(r5);	 Catch:{ SSLException -> 0x0062 }
        r4 = r4.append(r12);	 Catch:{ SSLException -> 0x0062 }
        r4 = r4.toString();	 Catch:{ SSLException -> 0x0062 }
        r3.<init>(r4);	 Catch:{ SSLException -> 0x0062 }
        throw r3;	 Catch:{ SSLException -> 0x0062 }
    L_0x0062:
        r11 = move-exception;
        r0 = r19;
        r1 = r20;
        r0.setHandshakeFailure(r1, r11);	 Catch:{ all -> 0x006b }
        throw r11;	 Catch:{ all -> 0x006b }
    L_0x006b:
        r3 = move-exception;
        r4 = r8.isReadable();
        if (r4 == 0) goto L_0x0105;
    L_0x0072:
        r10 = 1;
        r0 = r20;
        r0.fireChannelRead(r8);
    L_0x0078:
        throw r3;
    L_0x0079:
        r15 = r8.readableBytes();	 Catch:{ SSLException -> 0x0062 }
        if (r15 <= 0) goto L_0x009c;
    L_0x007f:
        r10 = 1;
        r0 = r20;
        r0.fireChannelRead(r8);	 Catch:{ SSLException -> 0x0062 }
    L_0x0085:
        r0 = r19;
        r3 = r0.engine;	 Catch:{ SSLException -> 0x0062 }
        r3 = r3.getSession();	 Catch:{ SSLException -> 0x0062 }
        r3 = r3.getApplicationBufferSize();	 Catch:{ SSLException -> 0x0062 }
        r3 = r3 - r15;
        r0 = r19;
        r1 = r20;
        r8 = r0.allocate(r1, r3);	 Catch:{ SSLException -> 0x0062 }
        goto L_0x000e;
    L_0x009c:
        r8.release();	 Catch:{ SSLException -> 0x0062 }
        goto L_0x0085;
    L_0x00a0:
        r13 = 1;
        goto L_0x003d;
    L_0x00a2:
        r3 = 1;
        r0 = r19;
        r1 = r20;
        r0.wrapNonAppData(r1, r3);	 Catch:{ SSLException -> 0x0062 }
    L_0x00aa:
        r3 = javax.net.ssl.SSLEngineResult.Status.BUFFER_UNDERFLOW;	 Catch:{ SSLException -> 0x0062 }
        r0 = r17;
        if (r0 == r3) goto L_0x00b4;
    L_0x00b0:
        if (r9 != 0) goto L_0x000e;
    L_0x00b2:
        if (r14 != 0) goto L_0x000e;
    L_0x00b4:
        r3 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_UNWRAP;	 Catch:{ SSLException -> 0x0062 }
        if (r12 != r3) goto L_0x00bb;
    L_0x00b8:
        r19.readIfNeeded(r20);	 Catch:{ SSLException -> 0x0062 }
    L_0x00bb:
        if (r18 == 0) goto L_0x00c5;
    L_0x00bd:
        r3 = 1;
        r0 = r19;
        r1 = r20;
        r0.wrap(r1, r3);	 Catch:{ SSLException -> 0x0062 }
    L_0x00c5:
        if (r13 == 0) goto L_0x00d2;
    L_0x00c7:
        r0 = r19;
        r3 = r0.sslCloseFuture;	 Catch:{ SSLException -> 0x0062 }
        r4 = r20.channel();	 Catch:{ SSLException -> 0x0062 }
        r3.trySuccess(r4);	 Catch:{ SSLException -> 0x0062 }
    L_0x00d2:
        r3 = r8.isReadable();
        if (r3 == 0) goto L_0x0101;
    L_0x00d8:
        r10 = 1;
        r0 = r20;
        r0.fireChannelRead(r8);
    L_0x00de:
        return r10;
    L_0x00df:
        r19.runDelegatedTasks();	 Catch:{ SSLException -> 0x0062 }
        goto L_0x00aa;
    L_0x00e3:
        r19.setHandshakeSuccess();	 Catch:{ SSLException -> 0x0062 }
        r18 = 1;
        goto L_0x00aa;
    L_0x00e9:
        r3 = r19.setHandshakeSuccessIfStillHandshaking();	 Catch:{ SSLException -> 0x0062 }
        if (r3 == 0) goto L_0x00f3;
    L_0x00ef:
        r18 = 1;
        goto L_0x000e;
    L_0x00f3:
        r0 = r19;
        r3 = r0.flushedBeforeHandshake;	 Catch:{ SSLException -> 0x0062 }
        if (r3 == 0) goto L_0x00aa;
    L_0x00f9:
        r3 = 0;
        r0 = r19;
        r0.flushedBeforeHandshake = r3;	 Catch:{ SSLException -> 0x0062 }
        r18 = 1;
        goto L_0x00aa;
    L_0x0101:
        r8.release();
        goto L_0x00de;
    L_0x0105:
        r8.release();
        goto L_0x0078;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.SslHandler.unwrap(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, int, int):boolean");
    }

    private SSLEngineResult unwrap(SSLEngine engine, ByteBuf in, int readerIndex, int len, ByteBuf out) throws SSLException {
        SSLEngineResult result;
        int nioBufferCount = in.nioBufferCount();
        int writerIndex = out.writerIndex();
        if (!(engine instanceof OpenSslEngine) || nioBufferCount <= 1) {
            result = engine.unwrap(toByteBuffer(in, readerIndex, len), toByteBuffer(out, writerIndex, out.writableBytes()));
        } else {
            OpenSslEngine opensslEngine = (OpenSslEngine) engine;
            try {
                this.singleBuffer[0] = toByteBuffer(out, writerIndex, out.writableBytes());
                result = opensslEngine.unwrap(in.nioBuffers(readerIndex, len), this.singleBuffer);
                out.writerIndex(result.bytesProduced() + writerIndex);
            } finally {
                this.singleBuffer[0] = null;
            }
        }
        out.writerIndex(result.bytesProduced() + writerIndex);
        return result;
    }

    private static ByteBuffer toByteBuffer(ByteBuf out, int index, int len) {
        return out.nioBufferCount() == 1 ? out.internalNioBuffer(index, len) : out.nioBuffer(index, len);
    }

    private void runDelegatedTasks() {
        Runnable task;
        if (this.delegatedTaskExecutor == ImmediateExecutor.INSTANCE) {
            while (true) {
                task = this.engine.getDelegatedTask();
                if (task != null) {
                    task.run();
                } else {
                    return;
                }
            }
        }
        List<Runnable> tasks = new ArrayList(2);
        while (true) {
            task = this.engine.getDelegatedTask();
            if (task == null) {
                break;
            }
            tasks.add(task);
        }
        if (!tasks.isEmpty()) {
            CountDownLatch latch = new CountDownLatch(1);
            this.delegatedTaskExecutor.execute(new 2(this, tasks, latch));
            boolean interrupted = false;
            while (latch.getCount() != 0) {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    interrupted = true;
                }
            }
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private boolean setHandshakeSuccessIfStillHandshaking() {
        if (this.handshakePromise.isDone()) {
            return false;
        }
        setHandshakeSuccess();
        return true;
    }

    private void setHandshakeSuccess() {
        this.handshakePromise.trySuccess(this.ctx.channel());
        if (logger.isDebugEnabled()) {
            logger.debug("{} HANDSHAKEN: {}", this.ctx.channel(), this.engine.getSession().getCipherSuite());
        }
        this.ctx.fireUserEventTriggered(SslHandshakeCompletionEvent.SUCCESS);
        if (this.readDuringHandshake && !this.ctx.channel().config().isAutoRead()) {
            this.readDuringHandshake = false;
            this.ctx.read();
        }
    }

    private void setHandshakeFailure(ChannelHandlerContext ctx, Throwable cause) {
        setHandshakeFailure(ctx, cause, true);
    }

    private void setHandshakeFailure(ChannelHandlerContext ctx, Throwable cause, boolean closeInbound) {
        this.engine.closeOutbound();
        if (closeInbound) {
            try {
                this.engine.closeInbound();
            } catch (SSLException e) {
                String msg = e.getMessage();
                if (msg == null || !msg.contains("possible truncation attack")) {
                    logger.debug("{} SSLEngine.closeInbound() raised an exception.", ctx.channel(), e);
                }
            }
        }
        notifyHandshakeFailure(cause);
        this.pendingUnencryptedWrites.removeAndFailAll(cause);
    }

    private void notifyHandshakeFailure(Throwable cause) {
        if (this.handshakePromise.tryFailure(cause)) {
            SslUtils.notifyHandshakeFailure(this.ctx, cause);
        }
    }

    private void closeOutboundAndChannel(ChannelHandlerContext ctx, ChannelPromise promise, boolean disconnect) throws Exception {
        if (ctx.channel().isActive()) {
            this.outboundClosed = true;
            this.engine.closeOutbound();
            ChannelPromise closeNotifyFuture = ctx.newPromise();
            try {
                write(ctx, Unpooled.EMPTY_BUFFER, closeNotifyFuture);
                flush(ctx);
            } finally {
                safeClose(ctx, closeNotifyFuture, promise);
            }
        } else if (disconnect) {
            ctx.disconnect(promise);
        } else {
            ctx.close(promise);
        }
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        this.pendingUnencryptedWrites = new PendingWriteQueue(ctx);
        if (ctx.channel().isActive() && this.engine.getUseClientMode()) {
            handshake(null);
        }
    }

    public Future<Channel> renegotiate() {
        ChannelHandlerContext ctx = this.ctx;
        if (ctx != null) {
            return renegotiate(ctx.executor().newPromise());
        }
        throw new IllegalStateException();
    }

    public Future<Channel> renegotiate(Promise<Channel> promise) {
        if (promise == null) {
            throw new NullPointerException("promise");
        }
        ChannelHandlerContext ctx = this.ctx;
        if (ctx == null) {
            throw new IllegalStateException();
        }
        EventExecutor executor = ctx.executor();
        if (executor.inEventLoop()) {
            handshake(promise);
        } else {
            executor.execute(new 3(this, promise));
        }
        return promise;
    }

    private void handshake(Promise<Channel> newHandshakePromise) {
        Promise<Channel> p;
        if (newHandshakePromise != null) {
            Promise<Channel> oldHandshakePromise = this.handshakePromise;
            if (oldHandshakePromise.isDone()) {
                p = newHandshakePromise;
                this.handshakePromise = newHandshakePromise;
            } else {
                oldHandshakePromise.addListener(new 4(this, newHandshakePromise));
                return;
            }
        } else if (this.engine.getHandshakeStatus() == HandshakeStatus.NOT_HANDSHAKING) {
            p = this.handshakePromise;
            if (!$assertionsDisabled && p.isDone()) {
                throw new AssertionError();
            }
        } else {
            return;
        }
        ChannelHandlerContext ctx = this.ctx;
        try {
            this.engine.beginHandshake();
            wrapNonAppData(ctx, false);
            ctx.flush();
        } catch (Exception e) {
            notifyHandshakeFailure(e);
        }
        long handshakeTimeoutMillis = this.handshakeTimeoutMillis;
        if (handshakeTimeoutMillis > 0 && !p.isDone()) {
            p.addListener(new 6(this, ctx.executor().schedule(new 5(this, p), handshakeTimeoutMillis, TimeUnit.MILLISECONDS)));
        }
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (!this.startTls && this.engine.getUseClientMode()) {
            handshake(null);
        }
        ctx.fireChannelActive();
    }

    private void safeClose(ChannelHandlerContext ctx, ChannelFuture flushFuture, ChannelPromise promise) {
        if (ctx.channel().isActive()) {
            ScheduledFuture<?> timeoutFuture;
            if (this.closeNotifyTimeoutMillis > 0) {
                timeoutFuture = ctx.executor().schedule(new 7(this, ctx, promise), this.closeNotifyTimeoutMillis, TimeUnit.MILLISECONDS);
            } else {
                timeoutFuture = null;
            }
            flushFuture.addListener(new 8(this, timeoutFuture, ctx, promise));
            return;
        }
        ctx.close(promise);
    }

    private static void addCloseListener(ChannelFuture future, ChannelPromise promise) {
        future.addListener(new ChannelPromiseNotifier(false, new ChannelPromise[]{promise}));
    }

    private ByteBuf allocate(ChannelHandlerContext ctx, int capacity) {
        ByteBufAllocator alloc = ctx.alloc();
        if (this.wantsDirectBuffer) {
            return alloc.directBuffer(capacity);
        }
        return alloc.buffer(capacity);
    }

    private ByteBuf allocateOutNetBuf(ChannelHandlerContext ctx, int pendingBytes) {
        if (this.wantsLargeOutboundNetworkBuffer) {
            return allocate(ctx, this.maxPacketBufferSize);
        }
        return allocate(ctx, Math.min(pendingBytes + 2329, this.maxPacketBufferSize));
    }
}
