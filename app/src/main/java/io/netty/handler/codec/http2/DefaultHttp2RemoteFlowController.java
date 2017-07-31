package io.netty.handler.codec.http2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http2.Http2Connection.PropertyKey;
import io.netty.handler.codec.http2.Http2RemoteFlowController.FlowControlled;
import io.netty.handler.codec.http2.Http2RemoteFlowController.Listener;
import io.netty.handler.codec.http2.Http2Stream.State;
import io.netty.handler.codec.http2.StreamByteDistributor.StreamState;
import io.netty.handler.codec.http2.StreamByteDistributor.Writer;
import io.netty.util.BooleanSupplier;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.ArrayDeque;
import java.util.Deque;

public class DefaultHttp2RemoteFlowController implements Http2RemoteFlowController {
    static final /* synthetic */ boolean $assertionsDisabled = (!DefaultHttp2RemoteFlowController.class.desiredAssertionStatus());
    private static final int MIN_WRITABLE_CHUNK = 32768;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultHttp2RemoteFlowController.class);
    private final Http2Connection connection;
    private final FlowState connectionState;
    private ChannelHandlerContext ctx;
    private int initialWindowSize;
    private WritabilityMonitor monitor;
    private final PropertyKey stateKey;
    private final StreamByteDistributor streamByteDistributor;

    private final class FlowState implements StreamState {
        static final /* synthetic */ boolean $assertionsDisabled = (!DefaultHttp2RemoteFlowController.class.desiredAssertionStatus());
        private boolean cancelled;
        private BooleanSupplier isWritableSupplier = new BooleanSupplier() {
            public boolean get() throws Exception {
                return FlowState.this.windowSize() > FlowState.this.pendingBytes();
            }
        };
        private boolean markedWritable;
        private int pendingBytes;
        private final Deque<FlowControlled> pendingWriteQueue;
        private final Http2Stream stream;
        private int window;
        private boolean writing;

        FlowState(Http2Stream stream) {
            this.stream = stream;
            this.pendingWriteQueue = new ArrayDeque(2);
        }

        boolean isWritable() {
            try {
                return this.isWritableSupplier.get();
            } catch (Throwable cause) {
                Error error = new Error("isWritableSupplier should never throw!", cause);
            }
        }

        public Http2Stream stream() {
            return this.stream;
        }

        boolean markedWritability() {
            return this.markedWritable;
        }

        void markedWritability(boolean isWritable) {
            this.markedWritable = isWritable;
        }

        public int windowSize() {
            return this.window;
        }

        void windowSize(int initialWindowSize) {
            this.window = initialWindowSize;
        }

        int writeAllocatedBytes(int allocated) {
            int writtenBytes;
            int initialAllocated = allocated;
            if ($assertionsDisabled || !this.writing) {
                this.writing = true;
                boolean writeOccurred = false;
                while (!this.cancelled) {
                    FlowControlled frame = peek();
                    if (frame == null) {
                        break;
                    }
                    int maxBytes = Math.min(allocated, writableWindow());
                    if (maxBytes <= 0 && frame.size() > 0) {
                        break;
                    }
                    writeOccurred = true;
                    int initialFrameSize = frame.size();
                    try {
                        frame.write(DefaultHttp2RemoteFlowController.this.ctx, Math.max(0, maxBytes));
                        if (frame.size() == 0) {
                            this.pendingWriteQueue.remove();
                            frame.writeComplete();
                        }
                        allocated -= initialFrameSize - frame.size();
                    } catch (Throwable th) {
                        this.writing = false;
                        writtenBytes = initialAllocated - allocated;
                        decrementPendingBytes(writtenBytes, false);
                        decrementFlowControlWindow(writtenBytes);
                        if (this.cancelled) {
                            cancel(null);
                        }
                    }
                }
                if (writeOccurred) {
                    this.writing = false;
                    writtenBytes = initialAllocated - allocated;
                    decrementPendingBytes(writtenBytes, false);
                    decrementFlowControlWindow(writtenBytes);
                    if (this.cancelled) {
                        cancel(null);
                    }
                    return writtenBytes;
                }
                this.writing = false;
                writtenBytes = initialAllocated - allocated;
                decrementPendingBytes(writtenBytes, false);
                decrementFlowControlWindow(writtenBytes);
                if (!this.cancelled) {
                    return -1;
                }
                cancel(null);
                return -1;
            }
            throw new AssertionError();
        }

        int incrementStreamWindow(int delta) throws Http2Exception {
            if (delta <= 0 || Integer.MAX_VALUE - delta >= this.window) {
                this.window += delta;
                DefaultHttp2RemoteFlowController.this.streamByteDistributor.updateStreamableBytes(this);
                return this.window;
            }
            throw Http2Exception.streamError(this.stream.id(), Http2Error.FLOW_CONTROL_ERROR, "Window size overflow for stream: %d", Integer.valueOf(this.stream.id()));
        }

        private int writableWindow() {
            return Math.min(this.window, DefaultHttp2RemoteFlowController.this.connectionWindowSize());
        }

        public int pendingBytes() {
            return this.pendingBytes;
        }

        void enqueueFrame(FlowControlled frame) {
            FlowControlled last = (FlowControlled) this.pendingWriteQueue.peekLast();
            if (last == null) {
                enqueueFrameWithoutMerge(frame);
                return;
            }
            int lastSize = last.size();
            if (last.merge(DefaultHttp2RemoteFlowController.this.ctx, frame)) {
                incrementPendingBytes(last.size() - lastSize, true);
            } else {
                enqueueFrameWithoutMerge(frame);
            }
        }

        private void enqueueFrameWithoutMerge(FlowControlled frame) {
            this.pendingWriteQueue.offer(frame);
            incrementPendingBytes(frame.size(), true);
        }

        public boolean hasFrame() {
            return !this.pendingWriteQueue.isEmpty();
        }

        private FlowControlled peek() {
            return (FlowControlled) this.pendingWriteQueue.peek();
        }

        void cancel() {
            cancel(null);
        }

        private void cancel(Throwable cause) {
            this.cancelled = true;
            if (!this.writing) {
                while (true) {
                    FlowControlled frame = (FlowControlled) this.pendingWriteQueue.poll();
                    if (frame == null) {
                        DefaultHttp2RemoteFlowController.this.streamByteDistributor.updateStreamableBytes(this);
                        this.isWritableSupplier = BooleanSupplier.FALSE_SUPPLIER;
                        DefaultHttp2RemoteFlowController.this.monitor.stateCancelled(this);
                        return;
                    }
                    writeError(frame, Http2Exception.streamError(this.stream.id(), Http2Error.INTERNAL_ERROR, cause, "Stream closed before write could take place", new Object[0]));
                }
            }
        }

        private void incrementPendingBytes(int numBytes, boolean updateStreamableBytes) {
            this.pendingBytes += numBytes;
            DefaultHttp2RemoteFlowController.this.monitor.incrementPendingBytes(numBytes);
            if (updateStreamableBytes) {
                DefaultHttp2RemoteFlowController.this.streamByteDistributor.updateStreamableBytes(this);
            }
        }

        private void decrementPendingBytes(int bytes, boolean updateStreamableBytes) {
            incrementPendingBytes(-bytes, updateStreamableBytes);
        }

        private void decrementFlowControlWindow(int bytes) {
            int negativeBytes = -bytes;
            try {
                DefaultHttp2RemoteFlowController.this.connectionState.incrementStreamWindow(negativeBytes);
                incrementStreamWindow(negativeBytes);
            } catch (Http2Exception e) {
                throw new IllegalStateException("Invalid window state when writing frame: " + e.getMessage(), e);
            }
        }

        private void writeError(FlowControlled frame, Http2Exception cause) {
            if ($assertionsDisabled || DefaultHttp2RemoteFlowController.this.ctx != null) {
                decrementPendingBytes(frame.size(), true);
                frame.error(DefaultHttp2RemoteFlowController.this.ctx, cause);
                return;
            }
            throw new AssertionError();
        }
    }

    private class WritabilityMonitor {
        private boolean inWritePendingBytes;
        private long totalPendingBytes;
        private final Writer writer;

        private WritabilityMonitor() {
            this.writer = new Writer() {
                public void write(Http2Stream stream, int numBytes) {
                    DefaultHttp2RemoteFlowController.this.state(stream).writeAllocatedBytes(numBytes);
                }
            };
        }

        void channelWritabilityChange() throws Http2Exception {
        }

        void stateCancelled(FlowState state) {
        }

        void windowSize(FlowState state, int initialWindowSize) {
            state.windowSize(initialWindowSize);
        }

        void incrementWindowSize(FlowState state, int delta) throws Http2Exception {
            state.incrementStreamWindow(delta);
        }

        void enqueueFrame(FlowState state, FlowControlled frame) throws Http2Exception {
            state.enqueueFrame(frame);
        }

        final void incrementPendingBytes(int delta) {
            this.totalPendingBytes += (long) delta;
        }

        final boolean isWritable(FlowState state) {
            return isWritableConnection() && state.isWritable();
        }

        final void writePendingBytes() throws Http2Exception {
            if (!this.inWritePendingBytes) {
                this.inWritePendingBytes = true;
                try {
                    int bytesToWrite = DefaultHttp2RemoteFlowController.this.writableBytes();
                    while (DefaultHttp2RemoteFlowController.this.streamByteDistributor.distribute(bytesToWrite, this.writer)) {
                        bytesToWrite = DefaultHttp2RemoteFlowController.this.writableBytes();
                        if (bytesToWrite > 0) {
                            if (!DefaultHttp2RemoteFlowController.this.isChannelWritable0()) {
                                break;
                            }
                        }
                        break;
                    }
                    this.inWritePendingBytes = false;
                } catch (Throwable th) {
                    this.inWritePendingBytes = false;
                }
            }
        }

        void initialWindowSize(int newWindowSize) throws Http2Exception {
            if (newWindowSize < 0) {
                throw new IllegalArgumentException("Invalid initial window size: " + newWindowSize);
            }
            final int delta = newWindowSize - DefaultHttp2RemoteFlowController.this.initialWindowSize;
            DefaultHttp2RemoteFlowController.this.initialWindowSize = newWindowSize;
            DefaultHttp2RemoteFlowController.this.connection.forEachActiveStream(new Http2StreamVisitor() {
                public boolean visit(Http2Stream stream) throws Http2Exception {
                    DefaultHttp2RemoteFlowController.this.state(stream).incrementStreamWindow(delta);
                    return true;
                }
            });
            if (delta > 0 && DefaultHttp2RemoteFlowController.this.isChannelWritable()) {
                writePendingBytes();
            }
        }

        final boolean isWritableConnection() {
            return ((long) DefaultHttp2RemoteFlowController.this.connectionState.windowSize()) - this.totalPendingBytes > 0 && DefaultHttp2RemoteFlowController.this.isChannelWritable();
        }
    }

    private final class ListenerWritabilityMonitor extends WritabilityMonitor {
        private final Http2StreamVisitor checkStreamWritabilityVisitor = new Http2StreamVisitor() {
            public boolean visit(Http2Stream stream) throws Http2Exception {
                FlowState state = DefaultHttp2RemoteFlowController.this.state(stream);
                if (ListenerWritabilityMonitor.this.isWritable(state) != state.markedWritability()) {
                    ListenerWritabilityMonitor.this.notifyWritabilityChanged(state);
                }
                return true;
            }
        };
        private final Listener listener;

        ListenerWritabilityMonitor(Listener listener) {
            super();
            this.listener = listener;
        }

        void windowSize(FlowState state, int initialWindowSize) {
            super.windowSize(state, initialWindowSize);
            try {
                checkStateWritability(state);
            } catch (Http2Exception e) {
                throw new RuntimeException("Caught unexpected exception from window", e);
            }
        }

        void incrementWindowSize(FlowState state, int delta) throws Http2Exception {
            super.incrementWindowSize(state, delta);
            checkStateWritability(state);
        }

        void initialWindowSize(int newWindowSize) throws Http2Exception {
            super.initialWindowSize(newWindowSize);
            if (isWritableConnection()) {
                checkAllWritabilityChanged();
            }
        }

        void enqueueFrame(FlowState state, FlowControlled frame) throws Http2Exception {
            super.enqueueFrame(state, frame);
            checkConnectionThenStreamWritabilityChanged(state);
        }

        void stateCancelled(FlowState state) {
            try {
                checkConnectionThenStreamWritabilityChanged(state);
            } catch (Http2Exception e) {
                throw new RuntimeException("Caught unexpected exception from checkAllWritabilityChanged", e);
            }
        }

        void channelWritabilityChange() throws Http2Exception {
            if (DefaultHttp2RemoteFlowController.this.connectionState.markedWritability() != DefaultHttp2RemoteFlowController.this.isChannelWritable()) {
                checkAllWritabilityChanged();
            }
        }

        private void checkStateWritability(FlowState state) throws Http2Exception {
            if (isWritable(state) == state.markedWritability()) {
                return;
            }
            if (state == DefaultHttp2RemoteFlowController.this.connectionState) {
                checkAllWritabilityChanged();
            } else {
                notifyWritabilityChanged(state);
            }
        }

        private void notifyWritabilityChanged(FlowState state) {
            state.markedWritability(!state.markedWritability());
            try {
                this.listener.writabilityChanged(state.stream);
            } catch (Throwable cause) {
                DefaultHttp2RemoteFlowController.logger.error("Caught Throwable from listener.writabilityChanged", cause);
            }
        }

        private void checkConnectionThenStreamWritabilityChanged(FlowState state) throws Http2Exception {
            if (isWritableConnection() != DefaultHttp2RemoteFlowController.this.connectionState.markedWritability()) {
                checkAllWritabilityChanged();
            } else if (isWritable(state) != state.markedWritability()) {
                notifyWritabilityChanged(state);
            }
        }

        private void checkAllWritabilityChanged() throws Http2Exception {
            DefaultHttp2RemoteFlowController.this.connectionState.markedWritability(isWritableConnection());
            DefaultHttp2RemoteFlowController.this.connection.forEachActiveStream(this.checkStreamWritabilityVisitor);
        }
    }

    public DefaultHttp2RemoteFlowController(Http2Connection connection) {
        this(connection, (Listener) null);
    }

    public DefaultHttp2RemoteFlowController(Http2Connection connection, StreamByteDistributor streamByteDistributor) {
        this(connection, streamByteDistributor, null);
    }

    public DefaultHttp2RemoteFlowController(Http2Connection connection, Listener listener) {
        this(connection, new WeightedFairQueueByteDistributor(connection), listener);
    }

    public DefaultHttp2RemoteFlowController(Http2Connection connection, StreamByteDistributor streamByteDistributor, Listener listener) {
        this.initialWindowSize = 65535;
        this.connection = (Http2Connection) ObjectUtil.checkNotNull(connection, "connection");
        this.streamByteDistributor = (StreamByteDistributor) ObjectUtil.checkNotNull(streamByteDistributor, "streamWriteDistributor");
        this.stateKey = connection.newKey();
        this.connectionState = new FlowState(connection.connectionStream());
        connection.connectionStream().setProperty(this.stateKey, this.connectionState);
        listener(listener);
        this.monitor.windowSize(this.connectionState, this.initialWindowSize);
        connection.addListener(new Http2ConnectionAdapter() {
            public void onStreamAdded(Http2Stream stream) {
                stream.setProperty(DefaultHttp2RemoteFlowController.this.stateKey, new FlowState(stream));
            }

            public void onStreamActive(Http2Stream stream) {
                DefaultHttp2RemoteFlowController.this.monitor.windowSize(DefaultHttp2RemoteFlowController.this.state(stream), DefaultHttp2RemoteFlowController.this.initialWindowSize);
            }

            public void onStreamClosed(Http2Stream stream) {
                DefaultHttp2RemoteFlowController.this.state(stream).cancel();
            }

            public void onStreamHalfClosed(Http2Stream stream) {
                if (State.HALF_CLOSED_LOCAL.equals(stream.state())) {
                    DefaultHttp2RemoteFlowController.this.state(stream).cancel();
                }
            }
        });
    }

    public void channelHandlerContext(ChannelHandlerContext ctx) throws Http2Exception {
        this.ctx = (ChannelHandlerContext) ObjectUtil.checkNotNull(ctx, "ctx");
        channelWritabilityChanged();
        if (isChannelWritable()) {
            writePendingBytes();
        }
    }

    public ChannelHandlerContext channelHandlerContext() {
        return this.ctx;
    }

    public void initialWindowSize(int newWindowSize) throws Http2Exception {
        if ($assertionsDisabled || this.ctx == null || this.ctx.executor().inEventLoop()) {
            this.monitor.initialWindowSize(newWindowSize);
            return;
        }
        throw new AssertionError();
    }

    public int initialWindowSize() {
        return this.initialWindowSize;
    }

    public int windowSize(Http2Stream stream) {
        return state(stream).windowSize();
    }

    public boolean isWritable(Http2Stream stream) {
        return this.monitor.isWritable(state(stream));
    }

    public void channelWritabilityChanged() throws Http2Exception {
        this.monitor.channelWritabilityChange();
    }

    private boolean isChannelWritable() {
        return this.ctx != null && isChannelWritable0();
    }

    private boolean isChannelWritable0() {
        return this.ctx.channel().isWritable();
    }

    public void listener(Listener listener) {
        this.monitor = listener == null ? new WritabilityMonitor() : new ListenerWritabilityMonitor(listener);
    }

    public void incrementWindowSize(Http2Stream stream, int delta) throws Http2Exception {
        if ($assertionsDisabled || this.ctx == null || this.ctx.executor().inEventLoop()) {
            this.monitor.incrementWindowSize(state(stream), delta);
            return;
        }
        throw new AssertionError();
    }

    public void addFlowControlled(Http2Stream stream, FlowControlled frame) {
        if ($assertionsDisabled || this.ctx == null || this.ctx.executor().inEventLoop()) {
            ObjectUtil.checkNotNull(frame, "frame");
            try {
                this.monitor.enqueueFrame(state(stream), frame);
                return;
            } catch (Throwable t) {
                frame.error(this.ctx, t);
                return;
            }
        }
        throw new AssertionError();
    }

    public boolean hasFlowControlled(Http2Stream stream) {
        return state(stream).hasFrame();
    }

    private FlowState state(Http2Stream stream) {
        return (FlowState) stream.getProperty(this.stateKey);
    }

    private int connectionWindowSize() {
        return this.connectionState.windowSize();
    }

    private int minUsableChannelBytes() {
        return Math.max(this.ctx.channel().config().getWriteBufferLowWaterMark(), 32768);
    }

    private int maxUsableChannelBytes() {
        int channelWritableBytes = (int) Math.min(2147483647L, this.ctx.channel().bytesBeforeUnwritable());
        return Math.min(this.connectionState.windowSize(), channelWritableBytes > 0 ? Math.max(channelWritableBytes, minUsableChannelBytes()) : 0);
    }

    private int writableBytes() {
        return Math.min(connectionWindowSize(), maxUsableChannelBytes());
    }

    public void writePendingBytes() throws Http2Exception {
        this.monitor.writePendingBytes();
    }
}
