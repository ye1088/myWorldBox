package io.netty.channel.epoll;

import io.netty.channel.ChannelConfig;
import io.netty.channel.RecvByteBufAllocator.DelegatingHandle;
import io.netty.channel.RecvByteBufAllocator.Handle;

class EpollRecvByteAllocatorHandle extends DelegatingHandle {
    private final ChannelConfig config;
    private boolean isEdgeTriggered;
    private boolean receivedRdHup;

    EpollRecvByteAllocatorHandle(Handle handle, ChannelConfig config) {
        super(handle);
        this.config = config;
    }

    final void receivedRdHup() {
        this.receivedRdHup = true;
    }

    boolean maybeMoreDataToRead() {
        return this.isEdgeTriggered && lastBytesRead() > 0;
    }

    final void edgeTriggered(boolean edgeTriggered) {
        this.isEdgeTriggered = edgeTriggered;
    }

    final boolean isEdgeTriggered() {
        return this.isEdgeTriggered;
    }

    public final boolean continueReading() {
        return this.receivedRdHup || ((maybeMoreDataToRead() && this.config.isAutoRead()) || super.continueReading());
    }
}
