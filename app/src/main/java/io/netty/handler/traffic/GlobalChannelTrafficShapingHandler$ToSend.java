package io.netty.handler.traffic;

import io.netty.channel.ChannelPromise;

final class GlobalChannelTrafficShapingHandler$ToSend {
    final ChannelPromise promise;
    final long relativeTimeAction;
    final long size;
    final Object toSend;

    private GlobalChannelTrafficShapingHandler$ToSend(long delay, Object toSend, long size, ChannelPromise promise) {
        this.relativeTimeAction = delay;
        this.toSend = toSend;
        this.size = size;
        this.promise = promise;
    }
}
