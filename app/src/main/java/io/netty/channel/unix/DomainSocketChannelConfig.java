package io.netty.channel.unix;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;

public interface DomainSocketChannelConfig extends ChannelConfig {
    DomainSocketReadMode getReadMode();

    DomainSocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

    DomainSocketChannelConfig setAutoClose(boolean z);

    DomainSocketChannelConfig setAutoRead(boolean z);

    DomainSocketChannelConfig setConnectTimeoutMillis(int i);

    @Deprecated
    DomainSocketChannelConfig setMaxMessagesPerRead(int i);

    DomainSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

    DomainSocketChannelConfig setReadMode(DomainSocketReadMode domainSocketReadMode);

    DomainSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

    @Deprecated
    DomainSocketChannelConfig setWriteBufferHighWaterMark(int i);

    @Deprecated
    DomainSocketChannelConfig setWriteBufferLowWaterMark(int i);

    DomainSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    DomainSocketChannelConfig setWriteSpinCount(int i);
}
