package io.netty.channel;

class ChannelFlushPromiseNotifier$DefaultFlushCheckpoint implements ChannelFlushPromiseNotifier$FlushCheckpoint {
    private long checkpoint;
    private final ChannelPromise future;

    ChannelFlushPromiseNotifier$DefaultFlushCheckpoint(long checkpoint, ChannelPromise future) {
        this.checkpoint = checkpoint;
        this.future = future;
    }

    public long flushCheckpoint() {
        return this.checkpoint;
    }

    public void flushCheckpoint(long checkpoint) {
        this.checkpoint = checkpoint;
    }

    public ChannelPromise promise() {
        return this.future;
    }
}
