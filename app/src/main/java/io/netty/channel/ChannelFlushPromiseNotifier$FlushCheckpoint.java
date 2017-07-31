package io.netty.channel;

interface ChannelFlushPromiseNotifier$FlushCheckpoint {
    long flushCheckpoint();

    void flushCheckpoint(long j);

    ChannelPromise promise();
}
