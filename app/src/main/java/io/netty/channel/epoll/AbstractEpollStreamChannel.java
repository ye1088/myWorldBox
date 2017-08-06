package io.netty.channel.epoll;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.EventLoop;
import io.netty.channel.epoll.AbstractEpollChannel.AbstractEpollUnsafe;
import io.netty.channel.socket.DuplexChannel;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.Socket;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledFuture;

public abstract class AbstractEpollStreamChannel extends AbstractEpollChannel implements DuplexChannel {
    static final /* synthetic */ boolean $assertionsDisabled = (!AbstractEpollStreamChannel.class.desiredAssertionStatus());
    private static final ClosedChannelException CLEAR_SPLICE_QUEUE_CLOSED_CHANNEL_EXCEPTION = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractEpollStreamChannel.class, "clearSpliceQueue()"));
    private static final ClosedChannelException DO_CLOSE_CLOSED_CHANNEL_EXCEPTION = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractEpollStreamChannel.class, "doClose()"));
    private static final String EXPECTED_TYPES = (" (expected: " + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(DefaultFileRegion.class) + ')');
    private static final ClosedChannelException FAIL_SPLICE_IF_CLOSED_CLOSED_CHANNEL_EXCEPTION = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractEpollStreamChannel.class, "failSpliceIfClosed(...)"));
    private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
    private static final ClosedChannelException SPLICE_TO_CLOSED_CHANNEL_EXCEPTION = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractEpollStreamChannel.class, "spliceTo(...)"));
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractEpollStreamChannel.class);
    private ChannelPromise connectPromise;
    private ScheduledFuture<?> connectTimeoutFuture;
    private FileDescriptor pipeIn;
    private FileDescriptor pipeOut;
    private SocketAddress requestedRemoteAddress;
    private Queue<SpliceInTask> spliceQueue;

    public /* bridge */ /* synthetic */ boolean isActive() {
        return super.isActive();
    }

    public /* bridge */ /* synthetic */ boolean isOpen() {
        return super.isOpen();
    }

    @Deprecated
    protected AbstractEpollStreamChannel(Channel parent, int fd) {
        this(parent, new Socket(fd));
    }

    @Deprecated
    protected AbstractEpollStreamChannel(int fd) {
        this(new Socket(fd));
    }

    @Deprecated
    protected AbstractEpollStreamChannel(FileDescriptor fd) {
        this(new Socket(fd.intValue()));
    }

    @Deprecated
    protected AbstractEpollStreamChannel(Socket fd) {
        this(fd, isSoErrorZero(fd));
    }

    protected AbstractEpollStreamChannel(Channel parent, Socket fd) {
        super(parent, fd, Native.EPOLLIN, true);
        this.flags |= Native.EPOLLRDHUP;
    }

    protected AbstractEpollStreamChannel(Socket fd, boolean active) {
        super(null, fd, Native.EPOLLIN, active);
        this.flags |= Native.EPOLLRDHUP;
    }

    protected AbstractEpollUnsafe newUnsafe() {
        return new EpollStreamUnsafe(this);
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    public final ChannelFuture spliceTo(AbstractEpollStreamChannel ch, int len) {
        return spliceTo(ch, len, newPromise());
    }

    public final ChannelFuture spliceTo(AbstractEpollStreamChannel ch, int len, ChannelPromise promise) {
        if (ch.eventLoop() != eventLoop()) {
            throw new IllegalArgumentException("EventLoops are not the same.");
        } else if (len < 0) {
            throw new IllegalArgumentException("len: " + len + " (expected: >= 0)");
        } else if (ch.config().getEpollMode() == EpollMode.LEVEL_TRIGGERED && config().getEpollMode() == EpollMode.LEVEL_TRIGGERED) {
            ObjectUtil.checkNotNull(promise, "promise");
            if (isOpen()) {
                addToSpliceQueue(new SpliceInChannelTask(this, ch, len, promise));
                failSpliceIfClosed(promise);
            } else {
                promise.tryFailure(SPLICE_TO_CLOSED_CHANNEL_EXCEPTION);
            }
            return promise;
        } else {
            throw new IllegalStateException("spliceTo() supported only when using " + EpollMode.LEVEL_TRIGGERED);
        }
    }

    public final ChannelFuture spliceTo(FileDescriptor ch, int offset, int len) {
        return spliceTo(ch, offset, len, newPromise());
    }

    public final ChannelFuture spliceTo(FileDescriptor ch, int offset, int len, ChannelPromise promise) {
        if (len < 0) {
            throw new IllegalArgumentException("len: " + len + " (expected: >= 0)");
        } else if (offset < 0) {
            throw new IllegalArgumentException("offset must be >= 0 but was " + offset);
        } else if (config().getEpollMode() != EpollMode.LEVEL_TRIGGERED) {
            throw new IllegalStateException("spliceTo() supported only when using " + EpollMode.LEVEL_TRIGGERED);
        } else {
            ObjectUtil.checkNotNull(promise, "promise");
            if (isOpen()) {
                addToSpliceQueue(new SpliceFdTask(this, ch, offset, len, promise));
                failSpliceIfClosed(promise);
            } else {
                promise.tryFailure(SPLICE_TO_CLOSED_CHANNEL_EXCEPTION);
            }
            return promise;
        }
    }

    private void failSpliceIfClosed(ChannelPromise promise) {
        if (!isOpen() && promise.tryFailure(FAIL_SPLICE_IF_CLOSED_CLOSED_CHANNEL_EXCEPTION)) {
            eventLoop().execute(new 1(this));
        }
    }

    private boolean writeBytes(ChannelOutboundBuffer in, ByteBuf buf, int writeSpinCount) throws Exception {
        int readableBytes = buf.readableBytes();
        if (readableBytes == 0) {
            in.remove();
            return true;
        } else if (buf.hasMemoryAddress() || buf.nioBufferCount() == 1) {
            int writtenBytes = doWriteBytes(buf, writeSpinCount);
            in.removeBytes((long) writtenBytes);
            if (writtenBytes != readableBytes) {
                return false;
            }
            return true;
        } else {
            ByteBuffer[] nioBuffers = buf.nioBuffers();
            return writeBytesMultiple(in, nioBuffers, nioBuffers.length, (long) readableBytes, writeSpinCount);
        }
    }

    private boolean writeBytesMultiple(ChannelOutboundBuffer in, IovArray array, int writeSpinCount) throws IOException {
        long expectedWrittenBytes = array.size();
        long initialExpectedWrittenBytes = expectedWrittenBytes;
        int cnt = array.count();
        if (!$assertionsDisabled && expectedWrittenBytes == 0) {
            throw new AssertionError();
        } else if ($assertionsDisabled || cnt != 0) {
            boolean done = false;
            int offset = 0;
            int end = 0 + cnt;
            for (int i = writeSpinCount - 1; i >= 0; i--) {
                long localWrittenBytes = fd().writevAddresses(array.memoryAddress(offset), cnt);
                if (localWrittenBytes == 0) {
                    break;
                }
                expectedWrittenBytes -= localWrittenBytes;
                if (expectedWrittenBytes == 0) {
                    done = true;
                    break;
                }
                do {
                    long bytes = array.processWritten(offset, localWrittenBytes);
                    if (bytes != -1) {
                        offset++;
                        cnt--;
                        localWrittenBytes -= bytes;
                        if (offset >= end) {
                            break;
                        }
                    } else {
                        break;
                    }
                } while (localWrittenBytes > 0);
            }
            in.removeBytes(initialExpectedWrittenBytes - expectedWrittenBytes);
            return done;
        } else {
            throw new AssertionError();
        }
    }

    private boolean writeBytesMultiple(ChannelOutboundBuffer in, ByteBuffer[] nioBuffers, int nioBufferCnt, long expectedWrittenBytes, int writeSpinCount) throws IOException {
        if ($assertionsDisabled || expectedWrittenBytes != 0) {
            long initialExpectedWrittenBytes = expectedWrittenBytes;
            boolean done = false;
            int offset = 0;
            int end = 0 + nioBufferCnt;
            for (int i = writeSpinCount - 1; i >= 0; i--) {
                long localWrittenBytes = fd().writev(nioBuffers, offset, nioBufferCnt);
                if (localWrittenBytes == 0) {
                    break;
                }
                expectedWrittenBytes -= localWrittenBytes;
                if (expectedWrittenBytes == 0) {
                    done = true;
                    break;
                }
                do {
                    ByteBuffer buffer = nioBuffers[offset];
                    int pos = buffer.position();
                    int bytes = buffer.limit() - pos;
                    if (((long) bytes) <= localWrittenBytes) {
                        offset++;
                        nioBufferCnt--;
                        localWrittenBytes -= (long) bytes;
                        if (offset >= end) {
                            break;
                        }
                    } else {
                        buffer.position(((int) localWrittenBytes) + pos);
                        break;
                    }
                } while (localWrittenBytes > 0);
            }
            in.removeBytes(initialExpectedWrittenBytes - expectedWrittenBytes);
            return done;
        }
        throw new AssertionError();
    }

    private boolean writeFileRegion(ChannelOutboundBuffer in, DefaultFileRegion region, int writeSpinCount) throws Exception {
        long regionCount = region.count();
        if (region.transferred() >= regionCount) {
            in.remove();
            return true;
        }
        long baseOffset = region.position();
        boolean done = false;
        long flushedAmount = 0;
        for (int i = writeSpinCount - 1; i >= 0; i--) {
            long offset = region.transferred();
            long localFlushedAmount = Native.sendfile(fd().intValue(), region, baseOffset, offset, regionCount - offset);
            if (localFlushedAmount == 0) {
                break;
            }
            flushedAmount += localFlushedAmount;
            if (region.transfered() >= regionCount) {
                done = true;
                break;
            }
        }
        if (flushedAmount > 0) {
            in.progress(flushedAmount);
        }
        if (!done) {
            return done;
        }
        in.remove();
        return done;
    }

    protected void doWrite(ChannelOutboundBuffer in) throws Exception {
        int writeSpinCount = config().getWriteSpinCount();
        while (true) {
            int msgCount = in.size();
            if (msgCount == 0) {
                clearFlag(Native.EPOLLOUT);
                return;
            } else if (msgCount <= 1 || !(in.current() instanceof ByteBuf)) {
                if (!doWriteSingle(in, writeSpinCount)) {
                    break;
                }
            } else if (!doWriteMultiple(in, writeSpinCount)) {
                break;
            }
        }
        setFlag(Native.EPOLLOUT);
    }

    protected boolean doWriteSingle(ChannelOutboundBuffer in, int writeSpinCount) throws Exception {
        ByteBuf msg = in.current();
        if (msg instanceof ByteBuf) {
            if (!writeBytes(in, msg, writeSpinCount)) {
                return false;
            }
        } else if (msg instanceof DefaultFileRegion) {
            if (!writeFileRegion(in, (DefaultFileRegion) msg, writeSpinCount)) {
                return false;
            }
        } else if (!(msg instanceof SpliceOutTask)) {
            throw new Error();
        } else if (!((SpliceOutTask) msg).spliceOut()) {
            return false;
        } else {
            in.remove();
        }
        return true;
    }

    private boolean doWriteMultiple(ChannelOutboundBuffer in, int writeSpinCount) throws Exception {
        if (PlatformDependent.hasUnsafe()) {
            IovArray array = ((EpollEventLoop) eventLoop()).cleanArray();
            in.forEachFlushedMessage(array);
            if (array.count() < 1) {
                in.removeBytes(0);
            } else if (!writeBytesMultiple(in, array, writeSpinCount)) {
                return false;
            }
        }
        ByteBuffer[] buffers = in.nioBuffers();
        int cnt = in.nioBufferCount();
        if (cnt >= 1) {
            if (!writeBytesMultiple(in, buffers, cnt, in.nioBufferSize(), writeSpinCount)) {
                return false;
            }
        }
        in.removeBytes(0);
        return true;
    }

    protected Object filterOutboundMessage(Object msg) {
        if (msg instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) msg;
            if (byteBuf.hasMemoryAddress()) {
                return byteBuf;
            }
            if (!PlatformDependent.hasUnsafe() && byteBuf.isDirect()) {
                return byteBuf;
            }
            Object newDirectBuffer;
            if (byteBuf instanceof CompositeByteBuf) {
                CompositeByteBuf comp = (CompositeByteBuf) byteBuf;
                if (comp.isDirect() && comp.nioBufferCount() <= Native.IOV_MAX) {
                    return byteBuf;
                }
                newDirectBuffer = newDirectBuffer(byteBuf);
                if ($assertionsDisabled || newDirectBuffer.hasMemoryAddress()) {
                    return newDirectBuffer;
                }
                throw new AssertionError();
            }
            newDirectBuffer = newDirectBuffer(byteBuf);
            if ($assertionsDisabled || newDirectBuffer.hasMemoryAddress()) {
                return newDirectBuffer;
            }
            throw new AssertionError();
        } else if ((msg instanceof DefaultFileRegion) || (msg instanceof SpliceOutTask)) {
            return msg;
        } else {
            throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
        }
    }

    private void shutdownOutput0(ChannelPromise promise) {
        try {
            fd().shutdown(false, true);
            promise.setSuccess();
        } catch (Throwable cause) {
            promise.setFailure(cause);
        }
    }

    private void shutdownInput0(ChannelPromise promise) {
        try {
            fd().shutdown(true, false);
            promise.setSuccess();
        } catch (Throwable cause) {
            promise.setFailure(cause);
        }
    }

    private void shutdown0(ChannelPromise promise) {
        try {
            fd().shutdown(true, true);
            promise.setSuccess();
        } catch (Throwable cause) {
            promise.setFailure(cause);
        }
    }

    public boolean isOutputShutdown() {
        return fd().isOutputShutdown();
    }

    public boolean isInputShutdown() {
        return fd().isInputShutdown();
    }

    public boolean isShutdown() {
        return fd().isShutdown();
    }

    public ChannelFuture shutdownOutput() {
        return shutdownOutput(newPromise());
    }

    public ChannelFuture shutdownOutput(ChannelPromise promise) {
        Executor closeExecutor = ((EpollStreamUnsafe) unsafe()).prepareToClose();
        if (closeExecutor != null) {
            closeExecutor.execute(new 2(this, promise));
        } else {
            EventLoop loop = eventLoop();
            if (loop.inEventLoop()) {
                shutdownOutput0(promise);
            } else {
                loop.execute(new 3(this, promise));
            }
        }
        return promise;
    }

    public ChannelFuture shutdownInput() {
        return shutdownInput(newPromise());
    }

    public ChannelFuture shutdownInput(ChannelPromise promise) {
        Executor closeExecutor = ((EpollStreamUnsafe) unsafe()).prepareToClose();
        if (closeExecutor != null) {
            closeExecutor.execute(new 4(this, promise));
        } else {
            EventLoop loop = eventLoop();
            if (loop.inEventLoop()) {
                shutdownInput0(promise);
            } else {
                loop.execute(new 5(this, promise));
            }
        }
        return promise;
    }

    public ChannelFuture shutdown() {
        return shutdown(newPromise());
    }

    public ChannelFuture shutdown(ChannelPromise promise) {
        Executor closeExecutor = ((EpollStreamUnsafe) unsafe()).prepareToClose();
        if (closeExecutor != null) {
            closeExecutor.execute(new 6(this, promise));
        } else {
            EventLoop loop = eventLoop();
            if (loop.inEventLoop()) {
                shutdown0(promise);
            } else {
                loop.execute(new 7(this, promise));
            }
        }
        return promise;
    }

    protected void doClose() throws Exception {
        try {
            ChannelPromise promise = this.connectPromise;
            if (promise != null) {
                promise.tryFailure(DO_CLOSE_CLOSED_CHANNEL_EXCEPTION);
                this.connectPromise = null;
            }
            ScheduledFuture<?> future = this.connectTimeoutFuture;
            if (future != null) {
                future.cancel(false);
                this.connectTimeoutFuture = null;
            }
            super.doClose();
        } finally {
            safeClosePipe(this.pipeIn);
            safeClosePipe(this.pipeOut);
            clearSpliceQueue();
        }
    }

    private void clearSpliceQueue() {
        if (this.spliceQueue != null) {
            while (true) {
                SpliceInTask task = (SpliceInTask) this.spliceQueue.poll();
                if (task != null) {
                    task.promise.tryFailure(CLEAR_SPLICE_QUEUE_CLOSED_CHANNEL_EXCEPTION);
                } else {
                    return;
                }
            }
        }
    }

    protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
        if (localAddress != null) {
            fd().bind(localAddress);
        }
        boolean success = false;
        try {
            boolean connected = fd().connect(remoteAddress);
            if (!connected) {
                setFlag(Native.EPOLLOUT);
            }
            success = true;
            return connected;
        } finally {
            if (!success) {
                doClose();
            }
        }
    }

    private static void safeClosePipe(FileDescriptor fd) {
        if (fd != null) {
            try {
                fd.close();
            } catch (IOException e) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Error while closing a_isRightVersion pipe", e);
                }
            }
        }
    }

    private void addToSpliceQueue(SpliceInTask task) {
        EventLoop eventLoop = eventLoop();
        if (eventLoop.inEventLoop()) {
            addToSpliceQueue0(task);
        } else {
            eventLoop.execute(new 8(this, task));
        }
    }

    private void addToSpliceQueue0(SpliceInTask task) {
        if (this.spliceQueue == null) {
            this.spliceQueue = PlatformDependent.newMpscQueue();
        }
        this.spliceQueue.add(task);
    }
}
