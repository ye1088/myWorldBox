package io.netty.channel.socket;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;

public interface ServerSocketChannelConfig extends ChannelConfig {
    int getBacklog();

    int getReceiveBufferSize();

    boolean isReuseAddress();

    ServerSocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

    ServerSocketChannelConfig setAutoRead(boolean z);

    ServerSocketChannelConfig setBacklog(int i);

    ServerSocketChannelConfig setConnectTimeoutMillis(int i);

    @Deprecated
    ServerSocketChannelConfig setMaxMessagesPerRead(int i);

    ServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

    ServerSocketChannelConfig setPerformancePreferences(int i, int i2, int i3);

    ServerSocketChannelConfig setReceiveBufferSize(int i);

    ServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

    ServerSocketChannelConfig setReuseAddress(boolean z);

    @Deprecated
    ServerSocketChannelConfig setWriteBufferHighWaterMark(int i);

    @Deprecated
    ServerSocketChannelConfig setWriteBufferLowWaterMark(int i);

    ServerSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    ServerSocketChannelConfig setWriteSpinCount(int i);
}
