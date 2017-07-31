package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http2.Http2Connection.PropertyKey;
import io.netty.handler.codec.http2.Http2Exception.CompositeStreamException;
import io.netty.handler.codec.http2.Http2Exception.StreamException;
import io.netty.handler.codec.http2.Http2Stream.State;
import io.netty.util.internal.ObjectUtil;

public class DefaultHttp2LocalFlowController implements Http2LocalFlowController {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final float DEFAULT_WINDOW_UPDATE_RATIO = 0.5f;
    private static final FlowState REDUCED_FLOW_STATE = new FlowState() {
        public int windowSize() {
            return 0;
        }

        public int initialWindowSize() {
            return 0;
        }

        public void window(int initialWindowSize) {
            throw new UnsupportedOperationException();
        }

        public void incrementInitialStreamWindow(int delta) {
        }

        public boolean writeWindowUpdateIfNeeded() throws Http2Exception {
            throw new UnsupportedOperationException();
        }

        public boolean consumeBytes(int numBytes) throws Http2Exception {
            return false;
        }

        public int unconsumedBytes() {
            return 0;
        }

        public float windowUpdateRatio() {
            throw new UnsupportedOperationException();
        }

        public void windowUpdateRatio(float ratio) {
            throw new UnsupportedOperationException();
        }

        public void receiveFlowControlledFrame(int dataLength) throws Http2Exception {
            throw new UnsupportedOperationException();
        }

        public void incrementFlowControlWindows(int delta) throws Http2Exception {
        }

        public void endOfStream(boolean endOfStream) {
            throw new UnsupportedOperationException();
        }
    };
    private final Http2Connection connection;
    private ChannelHandlerContext ctx;
    private Http2FrameWriter frameWriter;
    private int initialWindowSize;
    private final PropertyKey stateKey;
    private float windowUpdateRatio;

    private interface FlowState {
        boolean consumeBytes(int i) throws Http2Exception;

        void endOfStream(boolean z);

        void incrementFlowControlWindows(int i) throws Http2Exception;

        void incrementInitialStreamWindow(int i);

        int initialWindowSize();

        void receiveFlowControlledFrame(int i) throws Http2Exception;

        int unconsumedBytes();

        void window(int i);

        int windowSize();

        float windowUpdateRatio();

        void windowUpdateRatio(float f);

        boolean writeWindowUpdateIfNeeded() throws Http2Exception;
    }

    private class DefaultState implements FlowState {
        static final /* synthetic */ boolean $assertionsDisabled = (!DefaultHttp2LocalFlowController.class.desiredAssertionStatus());
        private boolean endOfStream;
        private int initialStreamWindowSize;
        private int lowerBound;
        private int processedWindow;
        private final Http2Stream stream;
        private float streamWindowUpdateRatio;
        private int window;

        public DefaultState(Http2Stream stream, int initialWindowSize) {
            this.stream = stream;
            window(initialWindowSize);
            this.streamWindowUpdateRatio = DefaultHttp2LocalFlowController.this.windowUpdateRatio;
        }

        public void window(int initialWindowSize) {
            if ($assertionsDisabled || DefaultHttp2LocalFlowController.this.ctx == null || DefaultHttp2LocalFlowController.this.ctx.executor().inEventLoop()) {
                this.initialStreamWindowSize = initialWindowSize;
                this.processedWindow = initialWindowSize;
                this.window = initialWindowSize;
                return;
            }
            throw new AssertionError();
        }

        public int windowSize() {
            return this.window;
        }

        public int initialWindowSize() {
            return this.initialStreamWindowSize;
        }

        public void endOfStream(boolean endOfStream) {
            this.endOfStream = endOfStream;
        }

        public float windowUpdateRatio() {
            return this.streamWindowUpdateRatio;
        }

        public void windowUpdateRatio(float ratio) {
            if ($assertionsDisabled || DefaultHttp2LocalFlowController.this.ctx == null || DefaultHttp2LocalFlowController.this.ctx.executor().inEventLoop()) {
                this.streamWindowUpdateRatio = ratio;
                return;
            }
            throw new AssertionError();
        }

        public void incrementInitialStreamWindow(int delta) {
            this.initialStreamWindowSize += ((int) Math.min(2147483647L, Math.max(0, ((long) this.initialStreamWindowSize) + ((long) delta)))) - this.initialStreamWindowSize;
        }

        public void incrementFlowControlWindows(int delta) throws Http2Exception {
            if (delta <= 0 || this.window <= Integer.MAX_VALUE - delta) {
                this.window += delta;
                this.processedWindow += delta;
                if (delta >= 0) {
                    delta = 0;
                }
                this.lowerBound = delta;
                return;
            }
            throw Http2Exception.streamError(this.stream.id(), Http2Error.FLOW_CONTROL_ERROR, "Flow control window overflowed for stream: %d", Integer.valueOf(this.stream.id()));
        }

        public void receiveFlowControlledFrame(int dataLength) throws Http2Exception {
            if ($assertionsDisabled || dataLength >= 0) {
                this.window -= dataLength;
                if (this.window < this.lowerBound) {
                    throw Http2Exception.streamError(this.stream.id(), Http2Error.FLOW_CONTROL_ERROR, "Flow control window exceeded for stream: %d", Integer.valueOf(this.stream.id()));
                }
                return;
            }
            throw new AssertionError();
        }

        private void returnProcessedBytes(int delta) throws Http2Exception {
            if (this.processedWindow - delta < this.window) {
                throw Http2Exception.streamError(this.stream.id(), Http2Error.INTERNAL_ERROR, "Attempting to return too many bytes for stream %d", Integer.valueOf(this.stream.id()));
            } else {
                this.processedWindow -= delta;
            }
        }

        public boolean consumeBytes(int numBytes) throws Http2Exception {
            returnProcessedBytes(numBytes);
            return writeWindowUpdateIfNeeded();
        }

        public int unconsumedBytes() {
            return this.processedWindow - this.window;
        }

        public boolean writeWindowUpdateIfNeeded() throws Http2Exception {
            if (this.endOfStream || this.initialStreamWindowSize <= 0 || this.processedWindow > ((int) (((float) this.initialStreamWindowSize) * this.streamWindowUpdateRatio))) {
                return false;
            }
            writeWindowUpdate();
            return true;
        }

        private void writeWindowUpdate() throws Http2Exception {
            int deltaWindowSize = this.initialStreamWindowSize - this.processedWindow;
            try {
                incrementFlowControlWindows(deltaWindowSize);
                DefaultHttp2LocalFlowController.this.frameWriter.writeWindowUpdate(DefaultHttp2LocalFlowController.this.ctx, this.stream.id(), deltaWindowSize, DefaultHttp2LocalFlowController.this.ctx.newPromise());
            } catch (Throwable t) {
                Http2Exception connectionError = Http2Exception.connectionError(Http2Error.INTERNAL_ERROR, t, "Attempting to return too many bytes for stream %d", Integer.valueOf(this.stream.id()));
            }
        }
    }

    private final class AutoRefillState extends DefaultState {
        public AutoRefillState(Http2Stream stream, int initialWindowSize) {
            super(stream, initialWindowSize);
        }

        public void receiveFlowControlledFrame(int dataLength) throws Http2Exception {
            super.receiveFlowControlledFrame(dataLength);
            super.consumeBytes(dataLength);
        }

        public boolean consumeBytes(int numBytes) throws Http2Exception {
            return false;
        }
    }

    private final class WindowUpdateVisitor implements Http2StreamVisitor {
        private CompositeStreamException compositeException;
        private final int delta;

        public WindowUpdateVisitor(int delta) {
            this.delta = delta;
        }

        public boolean visit(Http2Stream stream) throws Http2Exception {
            try {
                FlowState state = DefaultHttp2LocalFlowController.this.state(stream);
                state.incrementFlowControlWindows(this.delta);
                state.incrementInitialStreamWindow(this.delta);
            } catch (StreamException e) {
                if (this.compositeException == null) {
                    this.compositeException = new CompositeStreamException(e.error(), 4);
                }
                this.compositeException.add(e);
            }
            return true;
        }

        public void throwIfError() throws CompositeStreamException {
            if (this.compositeException != null) {
                throw this.compositeException;
            }
        }
    }

    static {
        boolean z;
        if (DefaultHttp2LocalFlowController.class.desiredAssertionStatus()) {
            z = false;
        } else {
            z = true;
        }
        $assertionsDisabled = z;
    }

    public DefaultHttp2LocalFlowController(Http2Connection connection) {
        this(connection, 0.5f, false);
    }

    public DefaultHttp2LocalFlowController(Http2Connection connection, float windowUpdateRatio, boolean autoRefillConnectionWindow) {
        this.initialWindowSize = 65535;
        this.connection = (Http2Connection) ObjectUtil.checkNotNull(connection, "connection");
        windowUpdateRatio(windowUpdateRatio);
        this.stateKey = connection.newKey();
        connection.connectionStream().setProperty(this.stateKey, autoRefillConnectionWindow ? new AutoRefillState(connection.connectionStream(), this.initialWindowSize) : new DefaultState(connection.connectionStream(), this.initialWindowSize));
        connection.addListener(new Http2ConnectionAdapter() {
            public void onStreamAdded(Http2Stream stream) {
                stream.setProperty(DefaultHttp2LocalFlowController.this.stateKey, DefaultHttp2LocalFlowController.REDUCED_FLOW_STATE);
            }

            public void onStreamActive(Http2Stream stream) {
                stream.setProperty(DefaultHttp2LocalFlowController.this.stateKey, new DefaultState(stream, DefaultHttp2LocalFlowController.this.initialWindowSize));
            }

            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onStreamClosed(io.netty.handler.codec.http2.Http2Stream r7) {
                /*
                r6 = this;
                r3 = io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.this;	 Catch:{ Http2Exception -> 0x002e }
                r1 = r3.state(r7);	 Catch:{ Http2Exception -> 0x002e }
                r2 = r1.unconsumedBytes();	 Catch:{ Http2Exception -> 0x002e }
                r3 = io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.this;	 Catch:{ Http2Exception -> 0x002e }
                r3 = r3.ctx;	 Catch:{ Http2Exception -> 0x002e }
                if (r3 == 0) goto L_0x0020;
            L_0x0012:
                if (r2 <= 0) goto L_0x0020;
            L_0x0014:
                r3 = io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.this;	 Catch:{ Http2Exception -> 0x002e }
                r3 = r3.connectionState();	 Catch:{ Http2Exception -> 0x002e }
                r3.consumeBytes(r2);	 Catch:{ Http2Exception -> 0x002e }
                r1.consumeBytes(r2);	 Catch:{ Http2Exception -> 0x002e }
            L_0x0020:
                r3 = io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.this;
                r3 = r3.stateKey;
                r4 = io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.REDUCED_FLOW_STATE;
                r7.setProperty(r3, r4);
            L_0x002d:
                return;
            L_0x002e:
                r0 = move-exception;
                io.netty.util.internal.PlatformDependent.throwException(r0);	 Catch:{ all -> 0x0040 }
                r3 = io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.this;
                r3 = r3.stateKey;
                r4 = io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.REDUCED_FLOW_STATE;
                r7.setProperty(r3, r4);
                goto L_0x002d;
            L_0x0040:
                r3 = move-exception;
                r4 = io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.this;
                r4 = r4.stateKey;
                r5 = io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.REDUCED_FLOW_STATE;
                r7.setProperty(r4, r5);
                throw r3;
                */
                throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.http2.DefaultHttp2LocalFlowController.1.onStreamClosed(io.netty.handler.codec.http2.Http2Stream):void");
            }
        });
    }

    public DefaultHttp2LocalFlowController frameWriter(Http2FrameWriter frameWriter) {
        this.frameWriter = (Http2FrameWriter) ObjectUtil.checkNotNull(frameWriter, "frameWriter");
        return this;
    }

    public void channelHandlerContext(ChannelHandlerContext ctx) {
        this.ctx = (ChannelHandlerContext) ObjectUtil.checkNotNull(ctx, "ctx");
    }

    public void initialWindowSize(int newWindowSize) throws Http2Exception {
        if ($assertionsDisabled || this.ctx == null || this.ctx.executor().inEventLoop()) {
            int delta = newWindowSize - this.initialWindowSize;
            this.initialWindowSize = newWindowSize;
            WindowUpdateVisitor visitor = new WindowUpdateVisitor(delta);
            this.connection.forEachActiveStream(visitor);
            visitor.throwIfError();
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

    public int initialWindowSize(Http2Stream stream) {
        return state(stream).initialWindowSize();
    }

    public void incrementWindowSize(Http2Stream stream, int delta) throws Http2Exception {
        if ($assertionsDisabled || (this.ctx != null && this.ctx.executor().inEventLoop())) {
            FlowState state = state(stream);
            state.incrementInitialStreamWindow(delta);
            state.writeWindowUpdateIfNeeded();
            return;
        }
        throw new AssertionError();
    }

    public boolean consumeBytes(Http2Stream stream, int numBytes) throws Http2Exception {
        if (!$assertionsDisabled && (this.ctx == null || !this.ctx.executor().inEventLoop())) {
            throw new AssertionError();
        } else if (numBytes < 0) {
            throw new IllegalArgumentException("numBytes must not be negative");
        } else if (numBytes == 0 || stream == null || isClosed(stream)) {
            return false;
        } else {
            if (stream.id() != 0) {
                return connectionState().consumeBytes(numBytes) | state(stream).consumeBytes(numBytes);
            }
            throw new UnsupportedOperationException("Returning bytes for the connection window is not supported");
        }
    }

    public int unconsumedBytes(Http2Stream stream) {
        return state(stream).unconsumedBytes();
    }

    private static void checkValidRatio(float ratio) {
        if (Double.compare((double) ratio, 0.0d) <= 0 || Double.compare((double) ratio, 1.0d) >= 0) {
            throw new IllegalArgumentException("Invalid ratio: " + ratio);
        }
    }

    public void windowUpdateRatio(float ratio) {
        if ($assertionsDisabled || this.ctx == null || this.ctx.executor().inEventLoop()) {
            checkValidRatio(ratio);
            this.windowUpdateRatio = ratio;
            return;
        }
        throw new AssertionError();
    }

    public float windowUpdateRatio() {
        return this.windowUpdateRatio;
    }

    public void windowUpdateRatio(Http2Stream stream, float ratio) throws Http2Exception {
        if ($assertionsDisabled || (this.ctx != null && this.ctx.executor().inEventLoop())) {
            checkValidRatio(ratio);
            FlowState state = state(stream);
            state.windowUpdateRatio(ratio);
            state.writeWindowUpdateIfNeeded();
            return;
        }
        throw new AssertionError();
    }

    public float windowUpdateRatio(Http2Stream stream) throws Http2Exception {
        return state(stream).windowUpdateRatio();
    }

    public void receiveFlowControlledFrame(Http2Stream stream, ByteBuf data, int padding, boolean endOfStream) throws Http2Exception {
        if ($assertionsDisabled || (this.ctx != null && this.ctx.executor().inEventLoop())) {
            int dataLength = data.readableBytes() + padding;
            FlowState connectionState = connectionState();
            connectionState.receiveFlowControlledFrame(dataLength);
            if (stream != null && !isClosed(stream)) {
                FlowState state = state(stream);
                state.endOfStream(endOfStream);
                state.receiveFlowControlledFrame(dataLength);
                return;
            } else if (dataLength > 0) {
                connectionState.consumeBytes(dataLength);
                return;
            } else {
                return;
            }
        }
        throw new AssertionError();
    }

    private FlowState connectionState() {
        return (FlowState) this.connection.connectionStream().getProperty(this.stateKey);
    }

    private FlowState state(Http2Stream stream) {
        return (FlowState) stream.getProperty(this.stateKey);
    }

    private static boolean isClosed(Http2Stream stream) {
        return stream.state() == State.CLOSED;
    }
}
