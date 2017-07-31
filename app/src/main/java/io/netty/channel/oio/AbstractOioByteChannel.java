package io.netty.channel.oio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.FileRegion;
import io.netty.channel.RecvByteBufAllocator.Handle;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.util.internal.StringUtil;
import java.io.IOException;

public abstract class AbstractOioByteChannel extends AbstractOioChannel {
    private static final String EXPECTED_TYPES = (" (expected: " + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(FileRegion.class) + ')');
    private static final ChannelMetadata METADATA = new ChannelMetadata(false);

    protected abstract int available();

    protected void doRead() {
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
        r15 = this;
        r0 = 1;
        r13 = 0;
        r9 = r15.config();
        r14 = r15.isInputShutdown();
        if (r14 != 0) goto L_0x0010;
    L_0x000c:
        r14 = r15.readPending;
        if (r14 != 0) goto L_0x0011;
    L_0x0010:
        return;
    L_0x0011:
        r15.readPending = r13;
        r1 = r15.pipeline();
        r6 = r9.getAllocator();
        r14 = r15.unsafe();
        r5 = r14.recvBufAllocHandle();
        r5.reset(r9);
        r2 = 0;
        r4 = 0;
        r11 = 0;
        r2 = r5.allocate(r6);	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
    L_0x002d:
        r14 = r15.doReadBytes(r2);	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        r5.lastBytesRead(r14);	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        r14 = r5.lastBytesRead();	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        if (r14 > 0) goto L_0x007f;	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
    L_0x003a:
        r14 = r2.isReadable();	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        if (r14 != 0) goto L_0x004b;	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
    L_0x0040:
        r2.release();	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        r2 = 0;	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        r14 = r5.lastBytesRead();	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        if (r14 >= 0) goto L_0x007d;	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
    L_0x004a:
        r4 = r0;	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
    L_0x004b:
        if (r2 == 0) goto L_0x005a;	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
    L_0x004d:
        r0 = r2.isReadable();	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        if (r0 == 0) goto L_0x00ee;	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
    L_0x0053:
        r0 = 0;	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        r15.readPending = r0;	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        r1.fireChannelRead(r2);	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
    L_0x0059:
        r2 = 0;	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
    L_0x005a:
        if (r11 == 0) goto L_0x0062;	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
    L_0x005c:
        r5.readComplete();	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        r1.fireChannelReadComplete();	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
    L_0x0062:
        if (r4 == 0) goto L_0x0067;	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
    L_0x0064:
        r15.closeOnRead(r1);	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
    L_0x0067:
        r0 = r15.readPending;
        if (r0 != 0) goto L_0x0079;
    L_0x006b:
        r0 = r9.isAutoRead();
        if (r0 != 0) goto L_0x0079;
    L_0x0071:
        if (r11 != 0) goto L_0x0010;
    L_0x0073:
        r0 = r15.isActive();
        if (r0 == 0) goto L_0x0010;
    L_0x0079:
        r15.read();
        goto L_0x0010;
    L_0x007d:
        r4 = r13;
        goto L_0x004b;
    L_0x007f:
        r11 = 1;
        r7 = r15.available();	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        if (r7 <= 0) goto L_0x004b;	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
    L_0x0086:
        r14 = r2.isWritable();	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        if (r14 != 0) goto L_0x00a4;	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
    L_0x008c:
        r8 = r2.capacity();	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        r10 = r2.maxCapacity();	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        if (r8 != r10) goto L_0x00ab;	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
    L_0x0096:
        r14 = 1;	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        r5.incMessagesRead(r14);	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        r14 = 0;	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        r15.readPending = r14;	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        r1.fireChannelRead(r2);	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        r2 = r5.allocate(r6);	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
    L_0x00a4:
        r14 = r5.continueReading();	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        if (r14 != 0) goto L_0x002d;	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
    L_0x00aa:
        goto L_0x004b;	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
    L_0x00ab:
        r12 = r2.writerIndex();	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        r14 = r12 + r7;	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        if (r14 <= r10) goto L_0x00d3;	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
    L_0x00b3:
        r2.capacity(r10);	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        goto L_0x00a4;
    L_0x00b7:
        r3 = move-exception;
        r0 = r15;
        r0.handleReadException(r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        r0 = r15.readPending;
        if (r0 != 0) goto L_0x00ce;
    L_0x00c0:
        r0 = r9.isAutoRead();
        if (r0 != 0) goto L_0x00ce;
    L_0x00c6:
        if (r11 != 0) goto L_0x0010;
    L_0x00c8:
        r0 = r15.isActive();
        if (r0 == 0) goto L_0x0010;
    L_0x00ce:
        r15.read();
        goto L_0x0010;
    L_0x00d3:
        r2.ensureWritable(r7);	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        goto L_0x00a4;
    L_0x00d7:
        r0 = move-exception;
        r13 = r15.readPending;
        if (r13 != 0) goto L_0x00ea;
    L_0x00dc:
        r13 = r9.isAutoRead();
        if (r13 != 0) goto L_0x00ea;
    L_0x00e2:
        if (r11 != 0) goto L_0x00ed;
    L_0x00e4:
        r13 = r15.isActive();
        if (r13 == 0) goto L_0x00ed;
    L_0x00ea:
        r15.read();
    L_0x00ed:
        throw r0;
    L_0x00ee:
        r2.release();	 Catch:{ Throwable -> 0x00b7, all -> 0x00d7 }
        goto L_0x0059;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.oio.AbstractOioByteChannel.doRead():void");
    }

    protected abstract int doReadBytes(ByteBuf byteBuf) throws Exception;

    protected abstract void doWriteBytes(ByteBuf byteBuf) throws Exception;

    protected abstract void doWriteFileRegion(FileRegion fileRegion) throws Exception;

    protected abstract boolean isInputShutdown();

    protected abstract ChannelFuture shutdownInput();

    protected AbstractOioByteChannel(Channel parent) {
        super(parent);
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    private void closeOnRead(ChannelPipeline pipeline) {
        if (!isOpen()) {
            return;
        }
        if (Boolean.TRUE.equals(config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
            shutdownInput();
            pipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
            return;
        }
        unsafe().close(unsafe().voidPromise());
    }

    private void handleReadException(ChannelPipeline pipeline, ByteBuf byteBuf, Throwable cause, boolean close, Handle allocHandle) {
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
            closeOnRead(pipeline);
        }
    }

    protected void doWrite(ChannelOutboundBuffer in) throws Exception {
        while (true) {
            ByteBuf msg = in.current();
            if (msg != null) {
                if (msg instanceof ByteBuf) {
                    ByteBuf buf = msg;
                    int readableBytes = buf.readableBytes();
                    while (readableBytes > 0) {
                        doWriteBytes(buf);
                        int newReadableBytes = buf.readableBytes();
                        in.progress((long) (readableBytes - newReadableBytes));
                        readableBytes = newReadableBytes;
                    }
                    in.remove();
                } else if (msg instanceof FileRegion) {
                    FileRegion region = (FileRegion) msg;
                    long transferred = region.transferred();
                    doWriteFileRegion(region);
                    in.progress(region.transferred() - transferred);
                    in.remove();
                } else {
                    in.remove(new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg)));
                }
            } else {
                return;
            }
        }
    }

    protected final Object filterOutboundMessage(Object msg) throws Exception {
        if ((msg instanceof ByteBuf) || (msg instanceof FileRegion)) {
            return msg;
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
    }
}
