package io.netty.channel.nio;

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
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;

public abstract class AbstractNioByteChannel extends AbstractNioChannel {
    private static final String EXPECTED_TYPES = (" (expected: " + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(FileRegion.class) + ')');
    private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
    private Runnable flushTask;

    protected class NioByteUnsafe extends AbstractNioChannel$AbstractNioUnsafe {
        public final void read() {
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
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r11 = this;
            r0 = 1;
            r8 = 0;
            r9 = io.netty.channel.nio.AbstractNioByteChannel.this;
            r7 = r9.config();
            r9 = io.netty.channel.nio.AbstractNioByteChannel.this;
            r1 = r9.pipeline();
            r6 = r7.getAllocator();
            r5 = r11.recvBufAllocHandle();
            r5.reset(r7);
            r2 = 0;
            r4 = 0;
        L_0x001b:
            r2 = r5.allocate(r6);	 Catch:{ Throwable -> 0x006a, all -> 0x007f }
            r9 = io.netty.channel.nio.AbstractNioByteChannel.this;	 Catch:{ Throwable -> 0x006a, all -> 0x007f }
            r9 = r9.doReadBytes(r2);	 Catch:{ Throwable -> 0x006a, all -> 0x007f }
            r5.lastBytesRead(r9);	 Catch:{ Throwable -> 0x006a, all -> 0x007f }
            r9 = r5.lastBytesRead();	 Catch:{ Throwable -> 0x006a, all -> 0x007f }
            if (r9 > 0) goto L_0x0056;	 Catch:{ Throwable -> 0x006a, all -> 0x007f }
        L_0x002e:
            r2.release();	 Catch:{ Throwable -> 0x006a, all -> 0x007f }
            r2 = 0;	 Catch:{ Throwable -> 0x006a, all -> 0x007f }
            r9 = r5.lastBytesRead();	 Catch:{ Throwable -> 0x006a, all -> 0x007f }
            if (r9 >= 0) goto L_0x0054;	 Catch:{ Throwable -> 0x006a, all -> 0x007f }
        L_0x0038:
            r4 = r0;	 Catch:{ Throwable -> 0x006a, all -> 0x007f }
        L_0x0039:
            r5.readComplete();	 Catch:{ Throwable -> 0x006a, all -> 0x007f }
            r1.fireChannelReadComplete();	 Catch:{ Throwable -> 0x006a, all -> 0x007f }
            if (r4 == 0) goto L_0x0044;	 Catch:{ Throwable -> 0x006a, all -> 0x007f }
        L_0x0041:
            r11.closeOnRead(r1);	 Catch:{ Throwable -> 0x006a, all -> 0x007f }
        L_0x0044:
            r0 = io.netty.channel.nio.AbstractNioByteChannel.this;
            r0 = r0.readPending;
            if (r0 != 0) goto L_0x0053;
        L_0x004a:
            r0 = r7.isAutoRead();
            if (r0 != 0) goto L_0x0053;
        L_0x0050:
            r11.removeReadOp();
        L_0x0053:
            return;
        L_0x0054:
            r4 = r8;
            goto L_0x0039;
        L_0x0056:
            r9 = 1;
            r5.incMessagesRead(r9);	 Catch:{ Throwable -> 0x006a, all -> 0x007f }
            r9 = io.netty.channel.nio.AbstractNioByteChannel.this;	 Catch:{ Throwable -> 0x006a, all -> 0x007f }
            r10 = 0;	 Catch:{ Throwable -> 0x006a, all -> 0x007f }
            r9.readPending = r10;	 Catch:{ Throwable -> 0x006a, all -> 0x007f }
            r1.fireChannelRead(r2);	 Catch:{ Throwable -> 0x006a, all -> 0x007f }
            r2 = 0;	 Catch:{ Throwable -> 0x006a, all -> 0x007f }
            r9 = r5.continueReading();	 Catch:{ Throwable -> 0x006a, all -> 0x007f }
            if (r9 != 0) goto L_0x001b;
        L_0x0069:
            goto L_0x0039;
        L_0x006a:
            r3 = move-exception;
            r0 = r11;
            r0.handleReadException(r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x006a, all -> 0x007f }
            r0 = io.netty.channel.nio.AbstractNioByteChannel.this;
            r0 = r0.readPending;
            if (r0 != 0) goto L_0x0053;
        L_0x0075:
            r0 = r7.isAutoRead();
            if (r0 != 0) goto L_0x0053;
        L_0x007b:
            r11.removeReadOp();
            goto L_0x0053;
        L_0x007f:
            r0 = move-exception;
            r8 = io.netty.channel.nio.AbstractNioByteChannel.this;
            r8 = r8.readPending;
            if (r8 != 0) goto L_0x008f;
        L_0x0086:
            r8 = r7.isAutoRead();
            if (r8 != 0) goto L_0x008f;
        L_0x008c:
            r11.removeReadOp();
        L_0x008f:
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.nio.AbstractNioByteChannel.NioByteUnsafe.read():void");
        }

        protected NioByteUnsafe() {
            super(AbstractNioByteChannel.this);
        }

        private void closeOnRead(ChannelPipeline pipeline) {
            if (!AbstractNioByteChannel.this.isOpen()) {
                return;
            }
            if (Boolean.TRUE.equals(AbstractNioByteChannel.this.config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
                AbstractNioByteChannel.this.shutdownInput();
                SelectionKey key = AbstractNioByteChannel.this.selectionKey();
                key.interestOps(key.interestOps() & (AbstractNioByteChannel.this.readInterestOp ^ -1));
                pipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
                return;
            }
            close(voidPromise());
        }

        private void handleReadException(ChannelPipeline pipeline, ByteBuf byteBuf, Throwable cause, boolean close, Handle allocHandle) {
            if (byteBuf != null) {
                if (byteBuf.isReadable()) {
                    AbstractNioByteChannel.this.readPending = false;
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
    }

    protected abstract int doReadBytes(ByteBuf byteBuf) throws Exception;

    protected abstract int doWriteBytes(ByteBuf byteBuf) throws Exception;

    protected abstract long doWriteFileRegion(FileRegion fileRegion) throws Exception;

    protected abstract ChannelFuture shutdownInput();

    protected AbstractNioByteChannel(Channel parent, SelectableChannel ch) {
        super(parent, ch, 1);
    }

    protected AbstractNioChannel$AbstractNioUnsafe newUnsafe() {
        return new NioByteUnsafe();
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    protected void doWrite(ChannelOutboundBuffer in) throws Exception {
        int writeSpinCount = -1;
        boolean setOpWrite = false;
        while (true) {
            ByteBuf msg = in.current();
            if (msg == null) {
                clearOpWrite();
                return;
            } else if (msg instanceof ByteBuf) {
                ByteBuf buf = msg;
                if (buf.readableBytes() != 0) {
                    done = false;
                    flushedAmount = 0;
                    if (writeSpinCount == -1) {
                        writeSpinCount = config().getWriteSpinCount();
                    }
                    for (i = writeSpinCount - 1; i >= 0; i--) {
                        int localFlushedAmount = doWriteBytes(buf);
                        if (localFlushedAmount == 0) {
                            setOpWrite = true;
                            break;
                        }
                        flushedAmount += (long) localFlushedAmount;
                        if (!buf.isReadable()) {
                            done = true;
                            break;
                        }
                    }
                    in.progress(flushedAmount);
                    if (!done) {
                        break;
                    }
                    in.remove();
                } else {
                    in.remove();
                }
            } else if (msg instanceof FileRegion) {
                FileRegion region = (FileRegion) msg;
                done = region.transferred() >= region.count();
                if (!done) {
                    flushedAmount = 0;
                    if (writeSpinCount == -1) {
                        writeSpinCount = config().getWriteSpinCount();
                    }
                    for (i = writeSpinCount - 1; i >= 0; i--) {
                        long localFlushedAmount2 = doWriteFileRegion(region);
                        if (localFlushedAmount2 == 0) {
                            setOpWrite = true;
                            break;
                        }
                        flushedAmount += localFlushedAmount2;
                        if (region.transferred() >= region.count()) {
                            done = true;
                            break;
                        }
                    }
                    in.progress(flushedAmount);
                }
                if (!done) {
                    break;
                }
                in.remove();
            } else {
                throw new Error();
            }
        }
        incompleteWrite(setOpWrite);
    }

    protected final Object filterOutboundMessage(Object msg) {
        if (msg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf) msg;
            if (buf.isDirect()) {
                return msg;
            }
            return newDirectBuffer(buf);
        } else if (msg instanceof FileRegion) {
            return msg;
        } else {
            throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
        }
    }

    protected final void incompleteWrite(boolean setOpWrite) {
        if (setOpWrite) {
            setOpWrite();
            return;
        }
        Runnable flushTask = this.flushTask;
        if (flushTask == null) {
            flushTask = new Runnable() {
                public void run() {
                    AbstractNioByteChannel.this.flush();
                }
            };
            this.flushTask = flushTask;
        }
        eventLoop().execute(flushTask);
    }

    protected final void setOpWrite() {
        SelectionKey key = selectionKey();
        if (key.isValid()) {
            int interestOps = key.interestOps();
            if ((interestOps & 4) == 0) {
                key.interestOps(interestOps | 4);
            }
        }
    }

    protected final void clearOpWrite() {
        SelectionKey key = selectionKey();
        if (key.isValid()) {
            int interestOps = key.interestOps();
            if ((interestOps & 4) != 0) {
                key.interestOps(interestOps & -5);
            }
        }
    }
}
