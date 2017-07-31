package io.netty.channel.epoll;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.AbstractChannel;
import io.netty.channel.AbstractChannel.AbstractUnsafe;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoop;
import io.netty.channel.RecvByteBufAllocator.Handle;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.channel.unix.Socket;
import io.netty.channel.unix.UnixChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.ObjectUtil;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.UnresolvedAddressException;

abstract class AbstractEpollChannel extends AbstractChannel implements UnixChannel {
    private static final ChannelMetadata METADATA = new ChannelMetadata(false);
    protected volatile boolean active;
    private final Socket fileDescriptor;
    protected int flags;
    private final int readFlag;

    protected abstract class AbstractEpollUnsafe extends AbstractUnsafe {
        static final /* synthetic */ boolean $assertionsDisabled = (!AbstractEpollChannel.class.desiredAssertionStatus());
        private EpollRecvByteAllocatorHandle allocHandle;
        private Runnable epollInReadyRunnable;
        boolean epollInReadyRunnablePending;
        boolean maybeMoreDataToRead;
        boolean readPending;

        abstract void epollInReady();

        protected AbstractEpollUnsafe() {
            super(AbstractEpollChannel.this);
        }

        final void epollInBefore() {
            this.maybeMoreDataToRead = false;
        }

        final void epollInFinally(ChannelConfig config) {
            this.maybeMoreDataToRead = this.allocHandle.maybeMoreDataToRead();
            if (!this.readPending && !config.isAutoRead()) {
                AbstractEpollChannel.this.clearEpollIn();
            } else if (this.readPending && this.maybeMoreDataToRead && !AbstractEpollChannel.this.fd().isInputShutdown()) {
                executeEpollInReadyRunnable();
            }
        }

        final void executeEpollInReadyRunnable() {
            if (!this.epollInReadyRunnablePending) {
                this.epollInReadyRunnablePending = true;
                if (this.epollInReadyRunnable == null) {
                    this.epollInReadyRunnable = new Runnable() {
                        public void run() {
                            AbstractEpollUnsafe.this.epollInReadyRunnablePending = false;
                            AbstractEpollUnsafe.this.epollInReady();
                        }
                    };
                }
                AbstractEpollChannel.this.eventLoop().execute(this.epollInReadyRunnable);
            }
        }

        final void epollRdHupReady() {
            recvBufAllocHandle().receivedRdHup();
            if (AbstractEpollChannel.this.isActive()) {
                epollInReady();
                clearEpollRdHup();
            }
            shutdownInput();
        }

        private void clearEpollRdHup() {
            try {
                AbstractEpollChannel.this.clearFlag(Native.EPOLLRDHUP);
            } catch (IOException e) {
                AbstractEpollChannel.this.pipeline().fireExceptionCaught(e);
                close(voidPromise());
            }
        }

        void shutdownInput() {
            if (!AbstractEpollChannel.this.fd().isInputShutdown()) {
                if (Boolean.TRUE.equals(AbstractEpollChannel.this.config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
                    try {
                        AbstractEpollChannel.this.fd().shutdown(true, false);
                        clearEpollIn0();
                        AbstractEpollChannel.this.pipeline().fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
                        return;
                    } catch (IOException e) {
                        fireEventAndClose(ChannelInputShutdownEvent.INSTANCE);
                        return;
                    } catch (NotYetConnectedException e2) {
                        fireEventAndClose(ChannelInputShutdownEvent.INSTANCE);
                        return;
                    }
                }
                close(voidPromise());
            }
        }

        private void fireEventAndClose(Object evt) {
            AbstractEpollChannel.this.pipeline().fireUserEventTriggered(evt);
            close(voidPromise());
        }

        public EpollRecvByteAllocatorHandle recvBufAllocHandle() {
            if (this.allocHandle == null) {
                this.allocHandle = newEpollHandle(super.recvBufAllocHandle());
            }
            return this.allocHandle;
        }

        EpollRecvByteAllocatorHandle newEpollHandle(Handle handle) {
            return new EpollRecvByteAllocatorHandle(handle, AbstractEpollChannel.this.config());
        }

        protected void flush0() {
            if (!AbstractEpollChannel.this.isFlagSet(Native.EPOLLOUT)) {
                super.flush0();
            }
        }

        void epollOutReady() {
            if (!AbstractEpollChannel.this.fd().isOutputShutdown()) {
                super.flush0();
            }
        }

        protected final void clearEpollIn0() {
            if ($assertionsDisabled || AbstractEpollChannel.this.eventLoop().inEventLoop()) {
                try {
                    this.readPending = false;
                    AbstractEpollChannel.this.clearFlag(AbstractEpollChannel.this.readFlag);
                    return;
                } catch (IOException e) {
                    AbstractEpollChannel.this.pipeline().fireExceptionCaught(e);
                    AbstractEpollChannel.this.unsafe().close(AbstractEpollChannel.this.unsafe().voidPromise());
                    return;
                }
            }
            throw new AssertionError();
        }
    }

    public abstract EpollChannelConfig config();

    protected abstract AbstractEpollUnsafe newUnsafe();

    AbstractEpollChannel(Socket fd, int flag) {
        this(null, fd, flag, false);
    }

    AbstractEpollChannel(Channel parent, Socket fd, int flag, boolean active) {
        super(parent);
        this.flags = Native.EPOLLET;
        this.fileDescriptor = (Socket) ObjectUtil.checkNotNull(fd, "fd");
        this.readFlag = flag;
        this.flags |= flag;
        this.active = active;
    }

    static boolean isSoErrorZero(Socket fd) {
        try {
            return fd.getSoError() == 0;
        } catch (Throwable e) {
            throw new ChannelException(e);
        }
    }

    void setFlag(int flag) throws IOException {
        if (!isFlagSet(flag)) {
            this.flags |= flag;
            modifyEvents();
        }
    }

    void clearFlag(int flag) throws IOException {
        if (isFlagSet(flag)) {
            this.flags &= flag ^ -1;
            modifyEvents();
        }
    }

    boolean isFlagSet(int flag) {
        return (this.flags & flag) != 0;
    }

    public final Socket fd() {
        return this.fileDescriptor;
    }

    public boolean isActive() {
        return this.active;
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    protected void doClose() throws Exception {
        this.active = false;
        try {
            doDeregister();
        } finally {
            this.fileDescriptor.close();
        }
    }

    protected void doDisconnect() throws Exception {
        doClose();
    }

    protected boolean isCompatible(EventLoop loop) {
        return loop instanceof EpollEventLoop;
    }

    public boolean isOpen() {
        return this.fileDescriptor.isOpen();
    }

    protected void doDeregister() throws Exception {
        ((EpollEventLoop) eventLoop()).remove(this);
    }

    protected final void doBeginRead() throws Exception {
        AbstractEpollUnsafe unsafe = (AbstractEpollUnsafe) unsafe();
        unsafe.readPending = true;
        setFlag(this.readFlag);
        if (unsafe.maybeMoreDataToRead) {
            unsafe.executeEpollInReadyRunnable();
        }
    }

    final void clearEpollIn() {
        if (isRegistered()) {
            EventLoop loop = eventLoop();
            final AbstractEpollUnsafe unsafe = (AbstractEpollUnsafe) unsafe();
            if (loop.inEventLoop()) {
                unsafe.clearEpollIn0();
                return;
            } else {
                loop.execute(new Runnable() {
                    public void run() {
                        if (!unsafe.readPending && !AbstractEpollChannel.this.config().isAutoRead()) {
                            unsafe.clearEpollIn0();
                        }
                    }
                });
                return;
            }
        }
        this.flags &= this.readFlag ^ -1;
    }

    private void modifyEvents() throws IOException {
        if (isOpen() && isRegistered()) {
            ((EpollEventLoop) eventLoop()).modify(this);
        }
    }

    protected void doRegister() throws Exception {
        EpollEventLoop loop = (EpollEventLoop) eventLoop();
        ((AbstractEpollUnsafe) unsafe()).epollInReadyRunnablePending = false;
        loop.add(this);
    }

    protected final ByteBuf newDirectBuffer(ByteBuf buf) {
        return newDirectBuffer(buf, buf);
    }

    protected final ByteBuf newDirectBuffer(Object holder, ByteBuf buf) {
        int readableBytes = buf.readableBytes();
        if (readableBytes == 0) {
            ReferenceCountUtil.safeRelease(holder);
            return Unpooled.EMPTY_BUFFER;
        }
        ByteBufAllocator alloc = alloc();
        if (alloc.isDirectBufferPooled()) {
            return newDirectBuffer0(holder, buf, alloc, readableBytes);
        }
        ByteBuf directBuf = ByteBufUtil.threadLocalDirectBuffer();
        if (directBuf == null) {
            return newDirectBuffer0(holder, buf, alloc, readableBytes);
        }
        directBuf.writeBytes(buf, buf.readerIndex(), readableBytes);
        ReferenceCountUtil.safeRelease(holder);
        return directBuf;
    }

    private static ByteBuf newDirectBuffer0(Object holder, ByteBuf buf, ByteBufAllocator alloc, int capacity) {
        ByteBuf directBuf = alloc.directBuffer(capacity);
        directBuf.writeBytes(buf, buf.readerIndex(), capacity);
        ReferenceCountUtil.safeRelease(holder);
        return directBuf;
    }

    protected static void checkResolvable(InetSocketAddress addr) {
        if (addr.isUnresolved()) {
            throw new UnresolvedAddressException();
        }
    }

    protected final int doReadBytes(ByteBuf byteBuf) throws Exception {
        int localReadAmount;
        int writerIndex = byteBuf.writerIndex();
        unsafe().recvBufAllocHandle().attemptedBytesRead(byteBuf.writableBytes());
        if (byteBuf.hasMemoryAddress()) {
            localReadAmount = this.fileDescriptor.readAddress(byteBuf.memoryAddress(), writerIndex, byteBuf.capacity());
        } else {
            ByteBuffer buf = byteBuf.internalNioBuffer(writerIndex, byteBuf.writableBytes());
            localReadAmount = this.fileDescriptor.read(buf, buf.position(), buf.limit());
        }
        if (localReadAmount > 0) {
            byteBuf.writerIndex(writerIndex + localReadAmount);
        }
        return localReadAmount;
    }

    protected final int doWriteBytes(ByteBuf buf, int writeSpinCount) throws Exception {
        int readableBytes = buf.readableBytes();
        int writtenBytes = 0;
        int i;
        int localFlushedAmount;
        if (!buf.hasMemoryAddress()) {
            ByteBuffer nioBuf;
            if (buf.nioBufferCount() == 1) {
                nioBuf = buf.internalNioBuffer(buf.readerIndex(), buf.readableBytes());
            } else {
                nioBuf = buf.nioBuffer();
            }
            for (i = writeSpinCount - 1; i >= 0; i--) {
                int pos = nioBuf.position();
                localFlushedAmount = this.fileDescriptor.write(nioBuf, pos, nioBuf.limit());
                if (localFlushedAmount <= 0) {
                    break;
                }
                nioBuf.position(pos + localFlushedAmount);
                writtenBytes += localFlushedAmount;
                if (writtenBytes == readableBytes) {
                    return writtenBytes;
                }
            }
        } else {
            long memoryAddress = buf.memoryAddress();
            int readerIndex = buf.readerIndex();
            int writerIndex = buf.writerIndex();
            for (i = writeSpinCount - 1; i >= 0; i--) {
                localFlushedAmount = this.fileDescriptor.writeAddress(memoryAddress, readerIndex, writerIndex);
                if (localFlushedAmount <= 0) {
                    break;
                }
                writtenBytes += localFlushedAmount;
                if (writtenBytes == readableBytes) {
                    return writtenBytes;
                }
                readerIndex += localFlushedAmount;
            }
        }
        if (writtenBytes < readableBytes) {
            setFlag(Native.EPOLLOUT);
        }
        return writtenBytes;
    }
}
