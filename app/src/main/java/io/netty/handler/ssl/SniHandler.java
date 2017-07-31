package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import io.netty.util.AsyncMapping;
import io.netty.util.CharsetUtil;
import io.netty.util.DomainNameMapping;
import io.netty.util.Mapping;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.IDN;
import java.net.SocketAddress;
import java.util.List;
import java.util.Locale;
import javax.net.ssl.SSLEngine;

public class SniHandler extends ByteToMessageDecoder implements ChannelOutboundHandler {
    private static final Selection EMPTY_SELECTION = new Selection(null, null);
    private static final int MAX_SSL_RECORDS = 4;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(SniHandler.class);
    private boolean handshakeFailed;
    private final AsyncMapping<String, SslContext> mapping;
    private boolean readPending;
    private volatile Selection selection;
    private boolean suppressRead;

    private static final class AsyncMappingAdapter implements AsyncMapping<String, SslContext> {
        private final Mapping<? super String, ? extends SslContext> mapping;

        private AsyncMappingAdapter(Mapping<? super String, ? extends SslContext> mapping) {
            this.mapping = (Mapping) ObjectUtil.checkNotNull(mapping, "mapping");
        }

        public Future<SslContext> map(String input, Promise<SslContext> promise) {
            try {
                return promise.setSuccess((SslContext) this.mapping.map(input));
            } catch (Throwable cause) {
                return promise.setFailure(cause);
            }
        }
    }

    private static final class Selection {
        final SslContext context;
        final String hostname;

        Selection(SslContext context, String hostname) {
            this.context = context;
            this.hostname = hostname;
        }
    }

    public SniHandler(Mapping<? super String, ? extends SslContext> mapping) {
        this(new AsyncMappingAdapter(mapping));
    }

    public SniHandler(DomainNameMapping<? extends SslContext> mapping) {
        this((Mapping) mapping);
    }

    public SniHandler(AsyncMapping<? super String, ? extends SslContext> mapping) {
        this.selection = EMPTY_SELECTION;
        this.mapping = (AsyncMapping) ObjectUtil.checkNotNull(mapping, "mapping");
    }

    public String hostname() {
        return this.selection.hostname;
    }

    public SslContext sslContext() {
        return this.selection.context;
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        if (!this.suppressRead && !this.handshakeFailed) {
            int writerIndex = in.writerIndex();
            int i = 0;
            while (i < 4) {
                int readerIndex = in.readerIndex();
                int readableBytes = writerIndex - readerIndex;
                if (readableBytes >= 5) {
                    switch (in.getUnsignedByte(readerIndex)) {
                        case 20:
                        case 21:
                            int len = SslUtils.getEncryptedPacketLength(in, readerIndex);
                            if (len == -1) {
                                this.handshakeFailed = true;
                                NotSslRecordException e = new NotSslRecordException("not an SSL/TLS record: " + ByteBufUtil.hexDump(in));
                                in.skipBytes(in.readableBytes());
                                ctx.fireExceptionCaught(e);
                                SslUtils.notifyHandshakeFailure(ctx, e);
                                return;
                            } else if ((writerIndex - readerIndex) - 5 >= len) {
                                try {
                                    in.skipBytes(len);
                                    i++;
                                } catch (Throwable e2) {
                                    if (logger.isDebugEnabled()) {
                                        logger.debug("Unexpected client hello packet: " + ByteBufUtil.hexDump(in), e2);
                                        break;
                                    }
                                }
                            } else {
                                return;
                            }
                            break;
                        case 22:
                            if (in.getUnsignedByte(readerIndex + 1) == 3) {
                                int packetLength = in.getUnsignedShort(readerIndex + 3) + 5;
                                if (readableBytes >= packetLength) {
                                    int endOffset = readerIndex + packetLength;
                                    int offset = readerIndex + 43;
                                    if (endOffset - offset >= 6) {
                                        offset += in.getUnsignedByte(offset) + 1;
                                        offset += in.getUnsignedShort(offset) + 2;
                                        offset += in.getUnsignedByte(offset) + 1;
                                        offset += 2;
                                        int extensionsLimit = offset + in.getUnsignedShort(offset);
                                        if (extensionsLimit <= endOffset) {
                                            while (extensionsLimit - offset >= 4) {
                                                int extensionType = in.getUnsignedShort(offset);
                                                offset += 2;
                                                int extensionLength = in.getUnsignedShort(offset);
                                                offset += 2;
                                                if (extensionsLimit - offset < extensionLength) {
                                                    break;
                                                } else if (extensionType == 0) {
                                                    offset += 2;
                                                    if (extensionsLimit - offset >= 3) {
                                                        int serverNameType = in.getUnsignedByte(offset);
                                                        offset++;
                                                        if (serverNameType == 0) {
                                                            int serverNameLength = in.getUnsignedShort(offset);
                                                            offset += 2;
                                                            if (extensionsLimit - offset >= serverNameLength) {
                                                                select(ctx, IDN.toASCII(in.toString(offset, serverNameLength, CharsetUtil.UTF_8), 1).toLowerCase(Locale.US));
                                                                return;
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    offset += extensionLength;
                                                }
                                            }
                                            break;
                                        }
                                    }
                                }
                                return;
                            }
                            break;
                        default:
                            break;
                    }
                    select(ctx, null);
                }
                return;
            }
            select(ctx, null);
        }
    }

    private void select(final ChannelHandlerContext ctx, final String hostname) {
        Future<SslContext> future = this.mapping.map(hostname, ctx.executor().newPromise());
        if (!future.isDone()) {
            this.suppressRead = true;
            future.addListener(new FutureListener<SslContext>() {
                public void operationComplete(io.netty.util.concurrent.Future<io.netty.handler.ssl.SslContext> r7) throws java.lang.Exception {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0033 in list [B:7:0x0029]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                    /*
                    r6 = this;
                    r5 = 0;
                    r0 = io.netty.handler.ssl.SniHandler.this;	 Catch:{ all -> 0x0059 }
                    r1 = 0;	 Catch:{ all -> 0x0059 }
                    r0.suppressRead = r1;	 Catch:{ all -> 0x0059 }
                    r0 = r7.isSuccess();	 Catch:{ all -> 0x0059 }
                    if (r0 == 0) goto L_0x0034;	 Catch:{ all -> 0x0059 }
                L_0x000d:
                    r1 = io.netty.handler.ssl.SniHandler.this;	 Catch:{ all -> 0x0059 }
                    r2 = r5;	 Catch:{ all -> 0x0059 }
                    r3 = new io.netty.handler.ssl.SniHandler$Selection;	 Catch:{ all -> 0x0059 }
                    r0 = r7.getNow();	 Catch:{ all -> 0x0059 }
                    r0 = (io.netty.handler.ssl.SslContext) r0;	 Catch:{ all -> 0x0059 }
                    r4 = r6;	 Catch:{ all -> 0x0059 }
                    r3.<init>(r0, r4);	 Catch:{ all -> 0x0059 }
                    r1.replaceHandler(r2, r3);	 Catch:{ all -> 0x0059 }
                L_0x0021:
                    r0 = io.netty.handler.ssl.SniHandler.this;
                    r0 = r0.readPending;
                    if (r0 == 0) goto L_0x0033;
                L_0x0029:
                    r0 = io.netty.handler.ssl.SniHandler.this;
                    r0.readPending = r5;
                    r0 = r5;
                    r0.read();
                L_0x0033:
                    return;
                L_0x0034:
                    r0 = r5;	 Catch:{ all -> 0x0059 }
                    r1 = new io.netty.handler.codec.DecoderException;	 Catch:{ all -> 0x0059 }
                    r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0059 }
                    r2.<init>();	 Catch:{ all -> 0x0059 }
                    r3 = "failed to get the SslContext for ";	 Catch:{ all -> 0x0059 }
                    r2 = r2.append(r3);	 Catch:{ all -> 0x0059 }
                    r3 = r6;	 Catch:{ all -> 0x0059 }
                    r2 = r2.append(r3);	 Catch:{ all -> 0x0059 }
                    r2 = r2.toString();	 Catch:{ all -> 0x0059 }
                    r3 = r7.cause();	 Catch:{ all -> 0x0059 }
                    r1.<init>(r2, r3);	 Catch:{ all -> 0x0059 }
                    r0.fireExceptionCaught(r1);	 Catch:{ all -> 0x0059 }
                    goto L_0x0021;
                L_0x0059:
                    r0 = move-exception;
                    r1 = io.netty.handler.ssl.SniHandler.this;
                    r1 = r1.readPending;
                    if (r1 == 0) goto L_0x006c;
                L_0x0062:
                    r1 = io.netty.handler.ssl.SniHandler.this;
                    r1.readPending = r5;
                    r1 = r5;
                    r1.read();
                L_0x006c:
                    throw r0;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.SniHandler.1.operationComplete(io.netty.util.concurrent.Future):void");
                }
            });
        } else if (future.isSuccess()) {
            replaceHandler(ctx, new Selection((SslContext) future.getNow(), hostname));
        } else {
            throw new DecoderException("failed to get the SslContext for " + hostname, future.cause());
        }
    }

    private void replaceHandler(ChannelHandlerContext ctx, Selection selection) {
        SSLEngine sslEngine = null;
        this.selection = selection;
        try {
            sslEngine = selection.context.newEngine(ctx.alloc());
            ChannelPipeline pipeline = ctx.pipeline();
            String name = SslHandler.class.getName();
            SslContext sslContext = selection.context;
            pipeline.replace(this, name, SslContext.newHandler(sslEngine));
        } catch (Throwable cause) {
            this.selection = EMPTY_SELECTION;
            ReferenceCountUtil.safeRelease(sslEngine);
            ctx.fireExceptionCaught(cause);
        }
    }

    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        ctx.bind(localAddress, promise);
    }

    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        ctx.connect(remoteAddress, localAddress, promise);
    }

    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        ctx.disconnect(promise);
    }

    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        ctx.close(promise);
    }

    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        ctx.deregister(promise);
    }

    public void read(ChannelHandlerContext ctx) throws Exception {
        if (this.suppressRead) {
            this.readPending = true;
        } else {
            ctx.read();
        }
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ctx.write(msg, promise);
    }

    public void flush(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
