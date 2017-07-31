package io.netty.channel.udt;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;

public interface UdtServerChannelConfig extends UdtChannelConfig {
    int getBacklog();

    UdtServerChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

    UdtServerChannelConfig setAutoClose(boolean z);

    UdtServerChannelConfig setAutoRead(boolean z);

    UdtServerChannelConfig setBacklog(int i);

    UdtServerChannelConfig setConnectTimeoutMillis(int i);

    @Deprecated
    UdtServerChannelConfig setMaxMessagesPerRead(int i);

    UdtServerChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

    UdtServerChannelConfig setProtocolReceiveBufferSize(int i);

    UdtServerChannelConfig setProtocolSendBufferSize(int i);

    UdtServerChannelConfig setReceiveBufferSize(int i);

    UdtServerChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

    UdtServerChannelConfig setReuseAddress(boolean z);

    UdtServerChannelConfig setSendBufferSize(int i);

    UdtServerChannelConfig setSoLinger(int i);

    UdtServerChannelConfig setSystemReceiveBufferSize(int i);

    UdtServerChannelConfig setSystemSendBufferSize(int i);

    @Deprecated
    UdtServerChannelConfig setWriteBufferHighWaterMark(int i);

    @Deprecated
    UdtServerChannelConfig setWriteBufferLowWaterMark(int i);

    UdtServerChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    UdtServerChannelConfig setWriteSpinCount(int i);
}
