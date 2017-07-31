package io.netty.channel.epoll;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ConnectTimeoutException;
import io.netty.channel.RecvByteBufAllocator.Handle;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.ConnectionPendingException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

class AbstractEpollStreamChannel$EpollStreamUnsafe extends AbstractEpollUnsafe {
    static final /* synthetic */ boolean $assertionsDisabled = (!AbstractEpollStreamChannel.class.desiredAssertionStatus());
    final /* synthetic */ AbstractEpollStreamChannel this$0;

    private void finishConnect() {
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
        r7 = this;
        r6 = 0;
        r5 = 0;
        r3 = $assertionsDisabled;
        if (r3 != 0) goto L_0x0018;
    L_0x0006:
        r3 = r7.this$0;
        r3 = r3.eventLoop();
        r3 = r3.inEventLoop();
        if (r3 != 0) goto L_0x0018;
    L_0x0012:
        r3 = new java.lang.AssertionError;
        r3.<init>();
        throw r3;
    L_0x0018:
        r0 = 0;
        r3 = r7.this$0;	 Catch:{ Throwable -> 0x0061, all -> 0x008e }
        r2 = r3.isActive();	 Catch:{ Throwable -> 0x0061, all -> 0x008e }
        r3 = r7.doFinishConnect();	 Catch:{ Throwable -> 0x0061, all -> 0x008e }
        if (r3 != 0) goto L_0x003f;
    L_0x0025:
        r0 = 1;
        if (r0 != 0) goto L_0x003e;
    L_0x0028:
        r3 = r7.this$0;
        r3 = io.netty.channel.epoll.AbstractEpollStreamChannel.access$600(r3);
        if (r3 == 0) goto L_0x0039;
    L_0x0030:
        r3 = r7.this$0;
        r3 = io.netty.channel.epoll.AbstractEpollStreamChannel.access$600(r3);
        r3.cancel(r5);
    L_0x0039:
        r3 = r7.this$0;
        io.netty.channel.epoll.AbstractEpollStreamChannel.access$402(r3, r6);
    L_0x003e:
        return;
    L_0x003f:
        r3 = r7.this$0;	 Catch:{ Throwable -> 0x0061, all -> 0x008e }
        r3 = io.netty.channel.epoll.AbstractEpollStreamChannel.access$400(r3);	 Catch:{ Throwable -> 0x0061, all -> 0x008e }
        r7.fulfillConnectPromise(r3, r2);	 Catch:{ Throwable -> 0x0061, all -> 0x008e }
        if (r0 != 0) goto L_0x003e;
    L_0x004a:
        r3 = r7.this$0;
        r3 = io.netty.channel.epoll.AbstractEpollStreamChannel.access$600(r3);
        if (r3 == 0) goto L_0x005b;
    L_0x0052:
        r3 = r7.this$0;
        r3 = io.netty.channel.epoll.AbstractEpollStreamChannel.access$600(r3);
        r3.cancel(r5);
    L_0x005b:
        r3 = r7.this$0;
        io.netty.channel.epoll.AbstractEpollStreamChannel.access$402(r3, r6);
        goto L_0x003e;
    L_0x0061:
        r1 = move-exception;
        r3 = r7.this$0;	 Catch:{ Throwable -> 0x0061, all -> 0x008e }
        r3 = io.netty.channel.epoll.AbstractEpollStreamChannel.access$400(r3);	 Catch:{ Throwable -> 0x0061, all -> 0x008e }
        r4 = r7.this$0;	 Catch:{ Throwable -> 0x0061, all -> 0x008e }
        r4 = io.netty.channel.epoll.AbstractEpollStreamChannel.access$500(r4);	 Catch:{ Throwable -> 0x0061, all -> 0x008e }
        r4 = r7.annotateConnectException(r1, r4);	 Catch:{ Throwable -> 0x0061, all -> 0x008e }
        r7.fulfillConnectPromise(r3, r4);	 Catch:{ Throwable -> 0x0061, all -> 0x008e }
        if (r0 != 0) goto L_0x003e;
    L_0x0077:
        r3 = r7.this$0;
        r3 = io.netty.channel.epoll.AbstractEpollStreamChannel.access$600(r3);
        if (r3 == 0) goto L_0x0088;
    L_0x007f:
        r3 = r7.this$0;
        r3 = io.netty.channel.epoll.AbstractEpollStreamChannel.access$600(r3);
        r3.cancel(r5);
    L_0x0088:
        r3 = r7.this$0;
        io.netty.channel.epoll.AbstractEpollStreamChannel.access$402(r3, r6);
        goto L_0x003e;
    L_0x008e:
        r3 = move-exception;
        if (r0 != 0) goto L_0x00a7;
    L_0x0091:
        r4 = r7.this$0;
        r4 = io.netty.channel.epoll.AbstractEpollStreamChannel.access$600(r4);
        if (r4 == 0) goto L_0x00a2;
    L_0x0099:
        r4 = r7.this$0;
        r4 = io.netty.channel.epoll.AbstractEpollStreamChannel.access$600(r4);
        r4.cancel(r5);
    L_0x00a2:
        r4 = r7.this$0;
        io.netty.channel.epoll.AbstractEpollStreamChannel.access$402(r4, r6);
    L_0x00a7:
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.epoll.AbstractEpollStreamChannel$EpollStreamUnsafe.finishConnect():void");
    }

    AbstractEpollStreamChannel$EpollStreamUnsafe(AbstractEpollStreamChannel abstractEpollStreamChannel) {
        this.this$0 = abstractEpollStreamChannel;
        super();
    }

    protected Executor prepareToClose() {
        return super.prepareToClose();
    }

    private void handleReadException(ChannelPipeline pipeline, ByteBuf byteBuf, Throwable cause, boolean close, EpollRecvByteAllocatorHandle allocHandle) {
        if (byteBuf != null) {
            if (byteBuf.isReadable()) {
                this.readPending = false;
                pipeline.fireChannelRead(byteBuf);
            } else {
                byteBuf.release();
            }
        }
        allocHandle.readComplete();
        pipeline.fireChannelReadComplete();
        pipeline.fireExceptionCaught(cause);
        if (close || (cause instanceof IOException)) {
            shutdownInput();
        }
    }

    public void connect(final SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
        if (promise.setUncancellable() && ensureOpen(promise)) {
            try {
                if (AbstractEpollStreamChannel.access$400(this.this$0) != null) {
                    throw new ConnectionPendingException();
                }
                boolean wasActive = this.this$0.isActive();
                if (this.this$0.doConnect(remoteAddress, localAddress)) {
                    fulfillConnectPromise(promise, wasActive);
                    return;
                }
                AbstractEpollStreamChannel.access$402(this.this$0, promise);
                AbstractEpollStreamChannel.access$502(this.this$0, remoteAddress);
                int connectTimeoutMillis = this.this$0.config().getConnectTimeoutMillis();
                if (connectTimeoutMillis > 0) {
                    AbstractEpollStreamChannel.access$602(this.this$0, this.this$0.eventLoop().schedule(new Runnable() {
                        public void run() {
                            ChannelPromise connectPromise = AbstractEpollStreamChannel.access$400(AbstractEpollStreamChannel$EpollStreamUnsafe.this.this$0);
                            ConnectTimeoutException cause = new ConnectTimeoutException("connection timed out: " + remoteAddress);
                            if (connectPromise != null && connectPromise.tryFailure(cause)) {
                                AbstractEpollStreamChannel$EpollStreamUnsafe.this.close(AbstractEpollStreamChannel$EpollStreamUnsafe.this.voidPromise());
                            }
                        }
                    }, (long) connectTimeoutMillis, TimeUnit.MILLISECONDS));
                }
                promise.addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (future.isCancelled()) {
                            if (AbstractEpollStreamChannel.access$600(AbstractEpollStreamChannel$EpollStreamUnsafe.this.this$0) != null) {
                                AbstractEpollStreamChannel.access$600(AbstractEpollStreamChannel$EpollStreamUnsafe.this.this$0).cancel(false);
                            }
                            AbstractEpollStreamChannel.access$402(AbstractEpollStreamChannel$EpollStreamUnsafe.this.this$0, null);
                            AbstractEpollStreamChannel$EpollStreamUnsafe.this.close(AbstractEpollStreamChannel$EpollStreamUnsafe.this.voidPromise());
                        }
                    }
                });
            } catch (Throwable t) {
                closeIfClosed();
                promise.tryFailure(annotateConnectException(t, remoteAddress));
            }
        }
    }

    private void fulfillConnectPromise(ChannelPromise promise, boolean wasActive) {
        if (promise != null) {
            this.this$0.active = true;
            boolean active = this.this$0.isActive();
            boolean promiseSet = promise.trySuccess();
            if (!wasActive && active) {
                this.this$0.pipeline().fireChannelActive();
            }
            if (!promiseSet) {
                close(voidPromise());
            }
        }
    }

    private void fulfillConnectPromise(ChannelPromise promise, Throwable cause) {
        if (promise != null) {
            promise.tryFailure(cause);
            closeIfClosed();
        }
    }

    void epollOutReady() {
        if (AbstractEpollStreamChannel.access$400(this.this$0) != null) {
            finishConnect();
        } else {
            super.epollOutReady();
        }
    }

    boolean doFinishConnect() throws Exception {
        if (this.this$0.fd().finishConnect()) {
            this.this$0.clearFlag(Native.EPOLLOUT);
            return true;
        }
        this.this$0.setFlag(Native.EPOLLOUT);
        return false;
    }

    EpollRecvByteAllocatorHandle newEpollHandle(Handle handle) {
        return new EpollRecvByteAllocatorStreamingHandle(handle, this.this$0.config());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void epollInReady() {
        /*
        r12 = this;
        r0 = 1;
        r9 = 0;
        r10 = r12.this$0;
        r10 = r10.fd();
        r10 = r10.isInputShutdown();
        if (r10 == 0) goto L_0x0012;
    L_0x000e:
        r12.clearEpollIn0();
    L_0x0011:
        return;
    L_0x0012:
        r10 = r12.this$0;
        r7 = r10.config();
        r5 = r12.recvBufAllocHandle();
        r10 = r12.this$0;
        r11 = io.netty.channel.epoll.Native.EPOLLET;
        r10 = r10.isFlagSet(r11);
        r5.edgeTriggered(r10);
        r10 = r12.this$0;
        r1 = r10.pipeline();
        r6 = r7.getAllocator();
        r5.reset(r7);
        r12.epollInBefore();
        r2 = 0;
        r4 = 0;
    L_0x0039:
        r10 = r12.this$0;	 Catch:{ Throwable -> 0x00b4 }
        r10 = io.netty.channel.epoll.AbstractEpollStreamChannel.access$700(r10);	 Catch:{ Throwable -> 0x00b4 }
        if (r10 == 0) goto L_0x007b;
    L_0x0041:
        r10 = r12.this$0;	 Catch:{ Throwable -> 0x00b4 }
        r10 = io.netty.channel.epoll.AbstractEpollStreamChannel.access$700(r10);	 Catch:{ Throwable -> 0x00b4 }
        r8 = r10.peek();	 Catch:{ Throwable -> 0x00b4 }
        r8 = (io.netty.channel.epoll.AbstractEpollStreamChannel$SpliceInTask) r8;	 Catch:{ Throwable -> 0x00b4 }
        if (r8 == 0) goto L_0x007b;
    L_0x004f:
        r10 = r8.spliceIn(r5);	 Catch:{ Throwable -> 0x00b4 }
        if (r10 == 0) goto L_0x006c;
    L_0x0055:
        r10 = r12.this$0;	 Catch:{ Throwable -> 0x00b4 }
        r10 = r10.isActive();	 Catch:{ Throwable -> 0x00b4 }
        if (r10 == 0) goto L_0x0066;
    L_0x005d:
        r10 = r12.this$0;	 Catch:{ Throwable -> 0x00b4 }
        r10 = io.netty.channel.epoll.AbstractEpollStreamChannel.access$700(r10);	 Catch:{ Throwable -> 0x00b4 }
        r10.remove();	 Catch:{ Throwable -> 0x00b4 }
    L_0x0066:
        r10 = r5.continueReading();	 Catch:{ Throwable -> 0x00b4 }
        if (r10 != 0) goto L_0x0039;
    L_0x006c:
        r5.readComplete();	 Catch:{ Throwable -> 0x00b4 }
        r1.fireChannelReadComplete();	 Catch:{ Throwable -> 0x00b4 }
        if (r4 == 0) goto L_0x0077;
    L_0x0074:
        r12.shutdownInput();	 Catch:{ Throwable -> 0x00b4 }
    L_0x0077:
        r12.epollInFinally(r7);
        goto L_0x0011;
    L_0x007b:
        r2 = r5.allocate(r6);	 Catch:{ Throwable -> 0x00b4 }
        r10 = r12.this$0;	 Catch:{ Throwable -> 0x00b4 }
        r10 = r10.doReadBytes(r2);	 Catch:{ Throwable -> 0x00b4 }
        r5.lastBytesRead(r10);	 Catch:{ Throwable -> 0x00b4 }
        r10 = r5.lastBytesRead();	 Catch:{ Throwable -> 0x00b4 }
        if (r10 > 0) goto L_0x009c;
    L_0x008e:
        r2.release();	 Catch:{ Throwable -> 0x00b4 }
        r2 = 0;
        r10 = r5.lastBytesRead();	 Catch:{ Throwable -> 0x00b4 }
        if (r10 >= 0) goto L_0x009a;
    L_0x0098:
        r4 = r0;
    L_0x0099:
        goto L_0x006c;
    L_0x009a:
        r4 = r9;
        goto L_0x0099;
    L_0x009c:
        r10 = 1;
        r5.incMessagesRead(r10);	 Catch:{ Throwable -> 0x00b4 }
        r10 = 0;
        r12.readPending = r10;	 Catch:{ Throwable -> 0x00b4 }
        r1.fireChannelRead(r2);	 Catch:{ Throwable -> 0x00b4 }
        r2 = 0;
        r10 = r12.this$0;	 Catch:{ Throwable -> 0x00b4 }
        r10 = r10.fd();	 Catch:{ Throwable -> 0x00b4 }
        r10 = r10.isInputShutdown();	 Catch:{ Throwable -> 0x00b4 }
        if (r10 == 0) goto L_0x0066;
    L_0x00b3:
        goto L_0x006c;
    L_0x00b4:
        r3 = move-exception;
        r0 = r12;
        r0.handleReadException(r1, r2, r3, r4, r5);	 Catch:{ all -> 0x00be }
        r12.epollInFinally(r7);
        goto L_0x0011;
    L_0x00be:
        r0 = move-exception;
        r12.epollInFinally(r7);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.epoll.AbstractEpollStreamChannel$EpollStreamUnsafe.epollInReady():void");
    }
}
