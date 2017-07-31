package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2Connection.PropertyKey;
import io.netty.handler.codec.http2.StreamByteDistributor.StreamState;
import io.netty.handler.codec.http2.StreamByteDistributor.Writer;
import io.netty.util.internal.ObjectUtil;
import java.util.ArrayDeque;
import java.util.Deque;

public final class UniformStreamByteDistributor implements StreamByteDistributor {
    static final int DEFAULT_MIN_ALLOCATION_CHUNK = 1024;
    private int minAllocationChunk = 1024;
    private final Deque<State> queue = new ArrayDeque(4);
    private final PropertyKey stateKey;
    private long totalStreamableBytes;

    private final class State {
        static final /* synthetic */ boolean $assertionsDisabled = (!UniformStreamByteDistributor.class.desiredAssertionStatus());
        boolean enqueued;
        final Http2Stream stream;
        int streamableBytes;
        boolean windowNegative;
        boolean writing;

        State(Http2Stream stream) {
            this.stream = stream;
        }

        void updateStreamableBytes(int newStreamableBytes, boolean hasFrame, int windowSize) {
            if ($assertionsDisabled || hasFrame || newStreamableBytes == 0) {
                int delta = newStreamableBytes - this.streamableBytes;
                if (delta != 0) {
                    this.streamableBytes = newStreamableBytes;
                    UniformStreamByteDistributor.access$214(UniformStreamByteDistributor.this, (long) delta);
                }
                this.windowNegative = windowSize < 0;
                if (!hasFrame) {
                    return;
                }
                if (windowSize > 0 || (windowSize == 0 && !this.writing)) {
                    addToQueue();
                    return;
                }
                return;
            }
            throw new AssertionError("hasFrame: " + hasFrame + " newStreamableBytes: " + newStreamableBytes);
        }

        void write(int numBytes, Writer writer) throws Http2Exception {
            this.writing = true;
            try {
                writer.write(this.stream, numBytes);
                this.writing = false;
            } catch (Throwable th) {
                this.writing = false;
            }
        }

        void addToQueue() {
            if (!this.enqueued) {
                this.enqueued = true;
                UniformStreamByteDistributor.this.queue.addLast(this);
            }
        }

        void removeFromQueue() {
            if (this.enqueued) {
                this.enqueued = false;
                UniformStreamByteDistributor.this.queue.remove(this);
            }
        }

        void close() {
            removeFromQueue();
            updateStreamableBytes(0, false, 0);
        }
    }

    static /* synthetic */ long access$214(UniformStreamByteDistributor x0, long x1) {
        long j = x0.totalStreamableBytes + x1;
        x0.totalStreamableBytes = j;
        return j;
    }

    public UniformStreamByteDistributor(Http2Connection connection) {
        this.stateKey = connection.newKey();
        Http2Stream connectionStream = connection.connectionStream();
        connectionStream.setProperty(this.stateKey, new State(connectionStream));
        connection.addListener(new Http2ConnectionAdapter() {
            public void onStreamAdded(Http2Stream stream) {
                stream.setProperty(UniformStreamByteDistributor.this.stateKey, new State(stream));
            }

            public void onStreamClosed(Http2Stream stream) {
                UniformStreamByteDistributor.this.state(stream).close();
            }
        });
    }

    public void minAllocationChunk(int minAllocationChunk) {
        if (minAllocationChunk <= 0) {
            throw new IllegalArgumentException("minAllocationChunk must be > 0");
        }
        this.minAllocationChunk = minAllocationChunk;
    }

    public void updateStreamableBytes(StreamState streamState) {
        state(streamState.stream()).updateStreamableBytes(Http2CodecUtil.streamableBytes(streamState), streamState.hasFrame(), streamState.windowSize());
    }

    public boolean distribute(int maxBytes, Writer writer) throws Http2Exception {
        ObjectUtil.checkNotNull(writer, "writer");
        int size = this.queue.size();
        if (size != 0) {
            int chunkSize = Math.max(this.minAllocationChunk, maxBytes / size);
            State state = (State) this.queue.pollFirst();
            do {
                state.enqueued = false;
                if (!state.windowNegative) {
                    if (maxBytes == 0 && state.streamableBytes > 0) {
                        this.queue.addFirst(state);
                        state.enqueued = true;
                        break;
                    }
                    int chunk = Math.min(chunkSize, Math.min(maxBytes, state.streamableBytes));
                    maxBytes -= chunk;
                    state.write(chunk, writer);
                }
                state = (State) this.queue.pollFirst();
            } while (state != null);
            if (this.totalStreamableBytes <= 0) {
                return false;
            }
            return true;
        } else if (this.totalStreamableBytes > 0) {
            return true;
        } else {
            return false;
        }
    }

    private State state(Http2Stream stream) {
        return (State) ((Http2Stream) ObjectUtil.checkNotNull(stream, "stream")).getProperty(this.stateKey);
    }

    int streamableBytes0(Http2Stream stream) {
        return state(stream).streamableBytes;
    }
}
