package io.netty.handler.codec.spdy;

import io.netty.channel.ChannelPromise;
import io.netty.util.internal.PlatformDependent;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

final class SpdySession {
    private final AtomicInteger activeLocalStreams = new AtomicInteger();
    private final AtomicInteger activeRemoteStreams = new AtomicInteger();
    private final Map<Integer, StreamState> activeStreams = PlatformDependent.newConcurrentHashMap();
    private final AtomicInteger receiveWindowSize;
    private final AtomicInteger sendWindowSize;
    private final StreamComparator streamComparator = new StreamComparator();

    public static final class PendingWrite {
        final ChannelPromise promise;
        final SpdyDataFrame spdyDataFrame;

        PendingWrite(SpdyDataFrame spdyDataFrame, ChannelPromise promise) {
            this.spdyDataFrame = spdyDataFrame;
            this.promise = promise;
        }

        void fail(Throwable cause) {
            this.spdyDataFrame.release();
            this.promise.setFailure(cause);
        }
    }

    private final class StreamComparator implements Comparator<Integer> {
        StreamComparator() {
        }

        public int compare(Integer id1, Integer id2) {
            int result = ((StreamState) SpdySession.this.activeStreams.get(id1)).getPriority() - ((StreamState) SpdySession.this.activeStreams.get(id2)).getPriority();
            return result != 0 ? result : id1.intValue() - id2.intValue();
        }
    }

    private static final class StreamState {
        private boolean localSideClosed;
        private final Queue<PendingWrite> pendingWriteQueue = new ConcurrentLinkedQueue();
        private final byte priority;
        private final AtomicInteger receiveWindowSize;
        private int receiveWindowSizeLowerBound;
        private boolean receivedReply;
        private boolean remoteSideClosed;
        private final AtomicInteger sendWindowSize;

        StreamState(byte priority, boolean remoteSideClosed, boolean localSideClosed, int sendWindowSize, int receiveWindowSize) {
            this.priority = priority;
            this.remoteSideClosed = remoteSideClosed;
            this.localSideClosed = localSideClosed;
            this.sendWindowSize = new AtomicInteger(sendWindowSize);
            this.receiveWindowSize = new AtomicInteger(receiveWindowSize);
        }

        byte getPriority() {
            return this.priority;
        }

        boolean isRemoteSideClosed() {
            return this.remoteSideClosed;
        }

        void closeRemoteSide() {
            this.remoteSideClosed = true;
        }

        boolean isLocalSideClosed() {
            return this.localSideClosed;
        }

        void closeLocalSide() {
            this.localSideClosed = true;
        }

        boolean hasReceivedReply() {
            return this.receivedReply;
        }

        void receivedReply() {
            this.receivedReply = true;
        }

        int getSendWindowSize() {
            return this.sendWindowSize.get();
        }

        int updateSendWindowSize(int deltaWindowSize) {
            return this.sendWindowSize.addAndGet(deltaWindowSize);
        }

        int updateReceiveWindowSize(int deltaWindowSize) {
            return this.receiveWindowSize.addAndGet(deltaWindowSize);
        }

        int getReceiveWindowSizeLowerBound() {
            return this.receiveWindowSizeLowerBound;
        }

        void setReceiveWindowSizeLowerBound(int receiveWindowSizeLowerBound) {
            this.receiveWindowSizeLowerBound = receiveWindowSizeLowerBound;
        }

        boolean putPendingWrite(PendingWrite msg) {
            return this.pendingWriteQueue.offer(msg);
        }

        PendingWrite getPendingWrite() {
            return (PendingWrite) this.pendingWriteQueue.peek();
        }

        PendingWrite removePendingWrite() {
            return (PendingWrite) this.pendingWriteQueue.poll();
        }

        void clearPendingWrites(Throwable cause) {
            while (true) {
                PendingWrite pendingWrite = (PendingWrite) this.pendingWriteQueue.poll();
                if (pendingWrite != null) {
                    pendingWrite.fail(cause);
                } else {
                    return;
                }
            }
        }
    }

    SpdySession(int sendWindowSize, int receiveWindowSize) {
        this.sendWindowSize = new AtomicInteger(sendWindowSize);
        this.receiveWindowSize = new AtomicInteger(receiveWindowSize);
    }

    int numActiveStreams(boolean remote) {
        if (remote) {
            return this.activeRemoteStreams.get();
        }
        return this.activeLocalStreams.get();
    }

    boolean noActiveStreams() {
        return this.activeStreams.isEmpty();
    }

    boolean isActiveStream(int streamId) {
        return this.activeStreams.containsKey(Integer.valueOf(streamId));
    }

    Map<Integer, StreamState> activeStreams() {
        Map<Integer, StreamState> streams = new TreeMap(this.streamComparator);
        streams.putAll(this.activeStreams);
        return streams;
    }

    void acceptStream(int streamId, byte priority, boolean remoteSideClosed, boolean localSideClosed, int sendWindowSize, int receiveWindowSize, boolean remote) {
        if ((remoteSideClosed && localSideClosed) || ((StreamState) this.activeStreams.put(Integer.valueOf(streamId), new StreamState(priority, remoteSideClosed, localSideClosed, sendWindowSize, receiveWindowSize))) != null) {
            return;
        }
        if (remote) {
            this.activeRemoteStreams.incrementAndGet();
        } else {
            this.activeLocalStreams.incrementAndGet();
        }
    }

    private StreamState removeActiveStream(int streamId, boolean remote) {
        StreamState state = (StreamState) this.activeStreams.remove(Integer.valueOf(streamId));
        if (state != null) {
            if (remote) {
                this.activeRemoteStreams.decrementAndGet();
            } else {
                this.activeLocalStreams.decrementAndGet();
            }
        }
        return state;
    }

    void removeStream(int streamId, Throwable cause, boolean remote) {
        StreamState state = removeActiveStream(streamId, remote);
        if (state != null) {
            state.clearPendingWrites(cause);
        }
    }

    boolean isRemoteSideClosed(int streamId) {
        StreamState state = (StreamState) this.activeStreams.get(Integer.valueOf(streamId));
        return state == null || state.isRemoteSideClosed();
    }

    void closeRemoteSide(int streamId, boolean remote) {
        StreamState state = (StreamState) this.activeStreams.get(Integer.valueOf(streamId));
        if (state != null) {
            state.closeRemoteSide();
            if (state.isLocalSideClosed()) {
                removeActiveStream(streamId, remote);
            }
        }
    }

    boolean isLocalSideClosed(int streamId) {
        StreamState state = (StreamState) this.activeStreams.get(Integer.valueOf(streamId));
        return state == null || state.isLocalSideClosed();
    }

    void closeLocalSide(int streamId, boolean remote) {
        StreamState state = (StreamState) this.activeStreams.get(Integer.valueOf(streamId));
        if (state != null) {
            state.closeLocalSide();
            if (state.isRemoteSideClosed()) {
                removeActiveStream(streamId, remote);
            }
        }
    }

    boolean hasReceivedReply(int streamId) {
        StreamState state = (StreamState) this.activeStreams.get(Integer.valueOf(streamId));
        return state != null && state.hasReceivedReply();
    }

    void receivedReply(int streamId) {
        StreamState state = (StreamState) this.activeStreams.get(Integer.valueOf(streamId));
        if (state != null) {
            state.receivedReply();
        }
    }

    int getSendWindowSize(int streamId) {
        if (streamId == 0) {
            return this.sendWindowSize.get();
        }
        StreamState state = (StreamState) this.activeStreams.get(Integer.valueOf(streamId));
        return state != null ? state.getSendWindowSize() : -1;
    }

    int updateSendWindowSize(int streamId, int deltaWindowSize) {
        if (streamId == 0) {
            return this.sendWindowSize.addAndGet(deltaWindowSize);
        }
        StreamState state = (StreamState) this.activeStreams.get(Integer.valueOf(streamId));
        return state != null ? state.updateSendWindowSize(deltaWindowSize) : -1;
    }

    int updateReceiveWindowSize(int streamId, int deltaWindowSize) {
        if (streamId == 0) {
            return this.receiveWindowSize.addAndGet(deltaWindowSize);
        }
        StreamState state = (StreamState) this.activeStreams.get(Integer.valueOf(streamId));
        if (state == null) {
            return -1;
        }
        if (deltaWindowSize > 0) {
            state.setReceiveWindowSizeLowerBound(0);
        }
        return state.updateReceiveWindowSize(deltaWindowSize);
    }

    int getReceiveWindowSizeLowerBound(int streamId) {
        if (streamId == 0) {
            return 0;
        }
        StreamState state = (StreamState) this.activeStreams.get(Integer.valueOf(streamId));
        if (state != null) {
            return state.getReceiveWindowSizeLowerBound();
        }
        return 0;
    }

    void updateAllSendWindowSizes(int deltaWindowSize) {
        for (StreamState state : this.activeStreams.values()) {
            state.updateSendWindowSize(deltaWindowSize);
        }
    }

    void updateAllReceiveWindowSizes(int deltaWindowSize) {
        for (StreamState state : this.activeStreams.values()) {
            state.updateReceiveWindowSize(deltaWindowSize);
            if (deltaWindowSize < 0) {
                state.setReceiveWindowSizeLowerBound(deltaWindowSize);
            }
        }
    }

    boolean putPendingWrite(int streamId, PendingWrite pendingWrite) {
        StreamState state = (StreamState) this.activeStreams.get(Integer.valueOf(streamId));
        return state != null && state.putPendingWrite(pendingWrite);
    }

    PendingWrite getPendingWrite(int streamId) {
        StreamState state;
        if (streamId == 0) {
            for (Entry<Integer, StreamState> e : activeStreams().entrySet()) {
                state = (StreamState) e.getValue();
                if (state.getSendWindowSize() > 0) {
                    PendingWrite pendingWrite = state.getPendingWrite();
                    if (pendingWrite != null) {
                        return pendingWrite;
                    }
                }
            }
            return null;
        }
        state = (StreamState) this.activeStreams.get(Integer.valueOf(streamId));
        if (state != null) {
            return state.getPendingWrite();
        }
        return null;
    }

    PendingWrite removePendingWrite(int streamId) {
        StreamState state = (StreamState) this.activeStreams.get(Integer.valueOf(streamId));
        return state != null ? state.removePendingWrite() : null;
    }
}
