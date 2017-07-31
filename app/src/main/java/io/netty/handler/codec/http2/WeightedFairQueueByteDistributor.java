package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2Connection.PropertyKey;
import io.netty.handler.codec.http2.StreamByteDistributor.StreamState;
import io.netty.handler.codec.http2.StreamByteDistributor.Writer;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PriorityQueue;
import io.netty.util.internal.PriorityQueueNode;
import java.util.Queue;

public final class WeightedFairQueueByteDistributor implements StreamByteDistributor {
    static final /* synthetic */ boolean $assertionsDisabled = (!WeightedFairQueueByteDistributor.class.desiredAssertionStatus());
    private int allocationQuantum = 1024;
    private final State connectionState;
    private final PropertyKey stateKey;

    private final class State implements PriorityQueueNode<State> {
        static final /* synthetic */ boolean $assertionsDisabled = (!WeightedFairQueueByteDistributor.class.desiredAssertionStatus());
        boolean active;
        int activeCountForTree;
        private int priorityQueueIndex;
        long pseudoTime;
        long pseudoTimeToWrite;
        private final Queue<State> queue;
        final Http2Stream stream;
        int streamableBytes;
        long totalQueuedWeights;

        State(WeightedFairQueueByteDistributor weightedFairQueueByteDistributor, Http2Stream stream) {
            this(stream, 0);
        }

        State(Http2Stream stream, int initialSize) {
            this.priorityQueueIndex = -1;
            this.stream = stream;
            this.queue = new PriorityQueue(initialSize);
        }

        void write(int numBytes, Writer writer) throws Http2Exception {
            try {
                writer.write(this.stream, numBytes);
            } catch (Throwable t) {
                Http2Exception connectionError = Http2Exception.connectionError(Http2Error.INTERNAL_ERROR, t, "byte distribution write error", new Object[0]);
            }
        }

        void isActiveCountChangeForTree(int increment) {
            if ($assertionsDisabled || this.activeCountForTree + increment >= 0) {
                this.activeCountForTree += increment;
                if (!this.stream.isRoot()) {
                    State pState = WeightedFairQueueByteDistributor.this.state(this.stream.parent());
                    if (this.activeCountForTree == 0) {
                        pState.remove(this);
                    } else if (this.activeCountForTree - increment == 0) {
                        pState.offerAndInitializePseudoTime(this);
                    }
                    pState.isActiveCountChangeForTree(increment);
                    return;
                }
                return;
            }
            throw new AssertionError();
        }

        void updateStreamableBytes(int newStreamableBytes, boolean isActive) {
            if (this.active != isActive) {
                isActiveCountChangeForTree(isActive ? 1 : -1);
                this.active = isActive;
            }
            this.streamableBytes = newStreamableBytes;
        }

        void updatePseudoTime(State parentState, int nsent, long totalQueuedWeights) {
            if ($assertionsDisabled || (this.stream.id() != 0 && nsent >= 0)) {
                this.pseudoTimeToWrite = Math.min(this.pseudoTimeToWrite, parentState.pseudoTime) + ((((long) nsent) * totalQueuedWeights) / ((long) this.stream.weight()));
                return;
            }
            throw new AssertionError();
        }

        void offerAndInitializePseudoTime(State state) {
            state.pseudoTimeToWrite = this.pseudoTime;
            offer(state);
        }

        void offer(State state) {
            this.queue.offer(state);
            this.totalQueuedWeights += (long) state.stream.weight();
        }

        State poll() {
            State state = (State) this.queue.poll();
            this.totalQueuedWeights -= (long) state.stream.weight();
            return state;
        }

        void remove(State state) {
            if (this.queue.remove(state)) {
                this.totalQueuedWeights -= (long) state.stream.weight();
            }
        }

        State peek() {
            return (State) this.queue.peek();
        }

        void close() {
            updateStreamableBytes(0, false);
        }

        public int compareTo(State o) {
            return MathUtil.compare(this.pseudoTimeToWrite, o.pseudoTimeToWrite);
        }

        public int priorityQueueIndex() {
            return this.priorityQueueIndex;
        }

        public void priorityQueueIndex(int i) {
            this.priorityQueueIndex = i;
        }
    }

    public WeightedFairQueueByteDistributor(Http2Connection connection) {
        this.stateKey = connection.newKey();
        Http2Stream connectionStream = connection.connectionStream();
        PropertyKey propertyKey = this.stateKey;
        State state = new State(connectionStream, 16);
        this.connectionState = state;
        connectionStream.setProperty(propertyKey, state);
        connection.addListener(new Http2ConnectionAdapter() {
            public void onStreamAdded(Http2Stream stream) {
                stream.setProperty(WeightedFairQueueByteDistributor.this.stateKey, new State(WeightedFairQueueByteDistributor.this, stream));
            }

            public void onWeightChanged(Http2Stream stream, short oldWeight) {
                if (WeightedFairQueueByteDistributor.this.state(stream).activeCountForTree != 0) {
                    Http2Stream parent = stream.parent();
                    if (parent != null) {
                        State access$100 = WeightedFairQueueByteDistributor.this.state(parent);
                        access$100.totalQueuedWeights += (long) (stream.weight() - oldWeight);
                    }
                }
            }

            public void onStreamClosed(Http2Stream stream) {
                WeightedFairQueueByteDistributor.this.state(stream).close();
            }

            public void onPriorityTreeParentChanged(Http2Stream stream, Http2Stream oldParent) {
                Http2Stream parent = stream.parent();
                if (parent != null) {
                    State state = WeightedFairQueueByteDistributor.this.state(stream);
                    if (state.activeCountForTree != 0) {
                        State pState = WeightedFairQueueByteDistributor.this.state(parent);
                        pState.offerAndInitializePseudoTime(state);
                        pState.isActiveCountChangeForTree(state.activeCountForTree);
                    }
                }
            }

            public void onPriorityTreeParentChanging(Http2Stream stream, Http2Stream newParent) {
                Http2Stream parent = stream.parent();
                if (parent != null) {
                    State state = WeightedFairQueueByteDistributor.this.state(stream);
                    if (state.activeCountForTree != 0) {
                        State pState = WeightedFairQueueByteDistributor.this.state(parent);
                        pState.remove(state);
                        pState.isActiveCountChangeForTree(-state.activeCountForTree);
                    }
                }
            }
        });
    }

    public void updateStreamableBytes(StreamState state) {
        State state2 = state(state.stream());
        int streamableBytes = Http2CodecUtil.streamableBytes(state);
        boolean z = state.hasFrame() && state.windowSize() >= 0;
        state2.updateStreamableBytes(streamableBytes, z);
    }

    public boolean distribute(int maxBytes, Writer writer) throws Http2Exception {
        ObjectUtil.checkNotNull(writer, "writer");
        if (this.connectionState.activeCountForTree == 0) {
            return false;
        }
        while (true) {
            int oldIsActiveCountForTree = this.connectionState.activeCountForTree;
            maxBytes -= distributeToChildren(maxBytes, writer, this.connectionState);
            if (this.connectionState.activeCountForTree == 0 || (maxBytes <= 0 && oldIsActiveCountForTree == this.connectionState.activeCountForTree)) {
            }
        }
        if (this.connectionState.activeCountForTree != 0) {
            return true;
        }
        return false;
    }

    public void allocationQuantum(int allocationQuantum) {
        if (allocationQuantum <= 0) {
            throw new IllegalArgumentException("allocationQuantum must be > 0");
        }
        this.allocationQuantum = allocationQuantum;
    }

    private int distribute(int maxBytes, Writer writer, State state) throws Http2Exception {
        if (!state.active) {
            return distributeToChildren(maxBytes, writer, state);
        }
        int nsent = Math.min(maxBytes, state.streamableBytes);
        state.write(nsent, writer);
        if (nsent != 0 || maxBytes == 0) {
            return nsent;
        }
        state.updateStreamableBytes(state.streamableBytes, false);
        return nsent;
    }

    private int distributeToChildren(int maxBytes, Writer writer, State state) throws Http2Exception {
        long oldTotalQueuedWeights = state.totalQueuedWeights;
        State childState = state.poll();
        State nextChildState = state.peek();
        try {
            if ($assertionsDisabled || nextChildState == null || nextChildState.pseudoTimeToWrite >= childState.pseudoTimeToWrite) {
                if (nextChildState != null) {
                    maxBytes = Math.min(maxBytes, (int) Math.min((((nextChildState.pseudoTimeToWrite - childState.pseudoTimeToWrite) * ((long) childState.stream.weight())) / oldTotalQueuedWeights) + ((long) this.allocationQuantum), 2147483647L));
                }
                int nsent = distribute(maxBytes, writer, childState);
                state.pseudoTime += (long) nsent;
                childState.updatePseudoTime(state, nsent, oldTotalQueuedWeights);
                return nsent;
            }
            throw new AssertionError("nextChildState.pseudoTime(" + nextChildState.pseudoTimeToWrite + ") < " + " childState.pseudoTime(" + childState.pseudoTimeToWrite + ")");
        } finally {
            if (childState.activeCountForTree != 0) {
                state.offer(childState);
            }
        }
    }

    private State state(Http2Stream stream) {
        return (State) stream.getProperty(this.stateKey);
    }

    int streamableBytes0(Http2Stream stream) {
        return state(stream).streamableBytes;
    }
}
