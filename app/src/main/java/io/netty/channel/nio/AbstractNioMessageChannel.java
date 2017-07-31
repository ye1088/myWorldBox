package io.netty.channel.nio;

import io.netty.channel.Channel;
import io.netty.channel.ChannelOutboundBuffer;
import java.nio.channels.SelectableChannel;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNioMessageChannel extends AbstractNioChannel {
    boolean inputShutdown;

    private final class NioMessageUnsafe extends AbstractNioChannel$AbstractNioUnsafe {
        static final /* synthetic */ boolean $assertionsDisabled = (!AbstractNioMessageChannel.class.desiredAssertionStatus());
        private final List<Object> readBuf;

        public void read() {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x00ac in list [B:42:0x00a3]
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
            r13 = this;
            r9 = 1;
            r10 = 0;
            r11 = $assertionsDisabled;
            if (r11 != 0) goto L_0x0018;
        L_0x0006:
            r11 = io.netty.channel.nio.AbstractNioMessageChannel.this;
            r11 = r11.eventLoop();
            r11 = r11.inEventLoop();
            if (r11 != 0) goto L_0x0018;
        L_0x0012:
            r9 = new java.lang.AssertionError;
            r9.<init>();
            throw r9;
        L_0x0018:
            r11 = io.netty.channel.nio.AbstractNioMessageChannel.this;
            r2 = r11.config();
            r11 = io.netty.channel.nio.AbstractNioMessageChannel.this;
            r6 = r11.pipeline();
            r11 = io.netty.channel.nio.AbstractNioMessageChannel.this;
            r11 = r11.unsafe();
            r0 = r11.recvBufAllocHandle();
            r0.reset(r2);
            r1 = 0;
            r3 = 0;
        L_0x0033:
            r11 = io.netty.channel.nio.AbstractNioMessageChannel.this;	 Catch:{ Throwable -> 0x0065 }
            r12 = r13.readBuf;	 Catch:{ Throwable -> 0x0065 }
            r5 = r11.doReadMessages(r12);	 Catch:{ Throwable -> 0x0065 }
            if (r5 != 0) goto L_0x0057;
        L_0x003d:
            r11 = r13.readBuf;	 Catch:{ all -> 0x00af }
            r7 = r11.size();	 Catch:{ all -> 0x00af }
            r4 = 0;	 Catch:{ all -> 0x00af }
        L_0x0044:
            if (r4 >= r7) goto L_0x0068;	 Catch:{ all -> 0x00af }
        L_0x0046:
            r11 = io.netty.channel.nio.AbstractNioMessageChannel.this;	 Catch:{ all -> 0x00af }
            r12 = 0;	 Catch:{ all -> 0x00af }
            r11.readPending = r12;	 Catch:{ all -> 0x00af }
            r11 = r13.readBuf;	 Catch:{ all -> 0x00af }
            r11 = r11.get(r4);	 Catch:{ all -> 0x00af }
            r6.fireChannelRead(r11);	 Catch:{ all -> 0x00af }
            r4 = r4 + 1;
            goto L_0x0044;
        L_0x0057:
            if (r5 >= 0) goto L_0x005b;
        L_0x0059:
            r1 = 1;
            goto L_0x003d;
        L_0x005b:
            r0.incMessagesRead(r5);	 Catch:{ Throwable -> 0x0065 }
            r11 = r0.continueReading();	 Catch:{ Throwable -> 0x0065 }
            if (r11 != 0) goto L_0x0033;
        L_0x0064:
            goto L_0x003d;
        L_0x0065:
            r8 = move-exception;
            r3 = r8;
            goto L_0x003d;
        L_0x0068:
            r11 = r13.readBuf;	 Catch:{ all -> 0x00af }
            r11.clear();	 Catch:{ all -> 0x00af }
            r0.readComplete();	 Catch:{ all -> 0x00af }
            r6.fireChannelReadComplete();	 Catch:{ all -> 0x00af }
            if (r3 == 0) goto L_0x0087;	 Catch:{ all -> 0x00af }
        L_0x0075:
            r11 = r3 instanceof java.io.IOException;	 Catch:{ all -> 0x00af }
            if (r11 == 0) goto L_0x0084;	 Catch:{ all -> 0x00af }
        L_0x0079:
            r11 = r3 instanceof java.net.PortUnreachableException;	 Catch:{ all -> 0x00af }
            if (r11 != 0) goto L_0x0084;	 Catch:{ all -> 0x00af }
        L_0x007d:
            r11 = io.netty.channel.nio.AbstractNioMessageChannel.this;	 Catch:{ all -> 0x00af }
            r11 = r11 instanceof io.netty.channel.ServerChannel;	 Catch:{ all -> 0x00af }
            if (r11 != 0) goto L_0x00ad;	 Catch:{ all -> 0x00af }
        L_0x0083:
            r1 = r9;	 Catch:{ all -> 0x00af }
        L_0x0084:
            r6.fireExceptionCaught(r3);	 Catch:{ all -> 0x00af }
        L_0x0087:
            if (r1 == 0) goto L_0x009d;	 Catch:{ all -> 0x00af }
        L_0x0089:
            r9 = io.netty.channel.nio.AbstractNioMessageChannel.this;	 Catch:{ all -> 0x00af }
            r10 = 1;	 Catch:{ all -> 0x00af }
            r9.inputShutdown = r10;	 Catch:{ all -> 0x00af }
            r9 = io.netty.channel.nio.AbstractNioMessageChannel.this;	 Catch:{ all -> 0x00af }
            r9 = r9.isOpen();	 Catch:{ all -> 0x00af }
            if (r9 == 0) goto L_0x009d;	 Catch:{ all -> 0x00af }
        L_0x0096:
            r9 = r13.voidPromise();	 Catch:{ all -> 0x00af }
            r13.close(r9);	 Catch:{ all -> 0x00af }
        L_0x009d:
            r9 = io.netty.channel.nio.AbstractNioMessageChannel.this;
            r9 = r9.readPending;
            if (r9 != 0) goto L_0x00ac;
        L_0x00a3:
            r9 = r2.isAutoRead();
            if (r9 != 0) goto L_0x00ac;
        L_0x00a9:
            r13.removeReadOp();
        L_0x00ac:
            return;
        L_0x00ad:
            r1 = r10;
            goto L_0x0084;
        L_0x00af:
            r9 = move-exception;
            r10 = io.netty.channel.nio.AbstractNioMessageChannel.this;
            r10 = r10.readPending;
            if (r10 != 0) goto L_0x00bf;
        L_0x00b6:
            r10 = r2.isAutoRead();
            if (r10 != 0) goto L_0x00bf;
        L_0x00bc:
            r13.removeReadOp();
        L_0x00bf:
            throw r9;
            */
            throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.nio.AbstractNioMessageChannel.NioMessageUnsafe.read():void");
        }

        private NioMessageUnsafe() {
            super(AbstractNioMessageChannel.this);
            this.readBuf = new ArrayList();
        }
    }

    protected abstract int doReadMessages(List<Object> list) throws Exception;

    protected abstract boolean doWriteMessage(Object obj, ChannelOutboundBuffer channelOutboundBuffer) throws Exception;

    protected AbstractNioMessageChannel(Channel parent, SelectableChannel ch, int readInterestOp) {
        super(parent, ch, readInterestOp);
    }

    protected AbstractNioChannel$AbstractNioUnsafe newUnsafe() {
        return new NioMessageUnsafe();
    }

    protected void doBeginRead() throws Exception {
        if (!this.inputShutdown) {
            super.doBeginRead();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void doWrite(io.netty.channel.ChannelOutboundBuffer r8) throws java.lang.Exception {
        /*
        r7 = this;
        r4 = r7.selectionKey();
        r3 = r4.interestOps();
    L_0x0008:
        r5 = r8.current();
        if (r5 != 0) goto L_0x0018;
    L_0x000e:
        r6 = r3 & 4;
        if (r6 == 0) goto L_0x0017;
    L_0x0012:
        r6 = r3 & -5;
        r4.interestOps(r6);
    L_0x0017:
        return;
    L_0x0018:
        r0 = 0;
        r6 = r7.config();	 Catch:{ IOException -> 0x0032 }
        r6 = r6.getWriteSpinCount();	 Catch:{ IOException -> 0x0032 }
        r2 = r6 + -1;
    L_0x0023:
        if (r2 < 0) goto L_0x002c;
    L_0x0025:
        r6 = r7.doWriteMessage(r5, r8);	 Catch:{ IOException -> 0x0032 }
        if (r6 == 0) goto L_0x003d;
    L_0x002b:
        r0 = 1;
    L_0x002c:
        if (r0 == 0) goto L_0x0040;
    L_0x002e:
        r8.remove();	 Catch:{ IOException -> 0x0032 }
        goto L_0x0008;
    L_0x0032:
        r1 = move-exception;
        r6 = r7.continueOnWriteError();
        if (r6 == 0) goto L_0x004a;
    L_0x0039:
        r8.remove(r1);
        goto L_0x0008;
    L_0x003d:
        r2 = r2 + -1;
        goto L_0x0023;
    L_0x0040:
        r6 = r3 & 4;
        if (r6 != 0) goto L_0x0017;
    L_0x0044:
        r6 = r3 | 4;
        r4.interestOps(r6);	 Catch:{ IOException -> 0x0032 }
        goto L_0x0017;
    L_0x004a:
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.nio.AbstractNioMessageChannel.doWrite(io.netty.channel.ChannelOutboundBuffer):void");
    }

    protected boolean continueOnWriteError() {
        return false;
    }
}
