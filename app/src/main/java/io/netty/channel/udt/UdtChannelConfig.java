package io.netty.channel.udt;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;

public interface UdtChannelConfig extends ChannelConfig {
    int getProtocolReceiveBufferSize();

    int getProtocolSendBufferSize();

    int getReceiveBufferSize();

    int getSendBufferSize();

    int getSoLinger();

    int getSystemReceiveBufferSize();

    int getSystemSendBufferSize();

    boolean isReuseAddress();

    UdtChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

    UdtChannelConfig setAutoClose(boolean z);

    UdtChannelConfig setAutoRead(boolean z);

    UdtChannelConfig setConnectTimeoutMillis(int i);

    @Deprecated
    UdtChannelConfig setMaxMessagesPerRead(int i);

    UdtChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

    UdtChannelConfig setProtocolReceiveBufferSize(int i);

    UdtChannelConfig setProtocolSendBufferSize(int i);

    UdtChannelConfig setReceiveBufferSize(int i);

    UdtChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

    UdtChannelConfig setReuseAddress(boolean z);

    UdtChannelConfig setSendBufferSize(int i);

    UdtChannelConfig setSoLinger(int i);

    UdtChannelConfig setSystemReceiveBufferSize(int i);

    UdtChannelConfig setSystemSendBufferSize(int i);

    @Deprecated
    UdtChannelConfig setWriteBufferHighWaterMark(int i);

    @Deprecated
    UdtChannelConfig setWriteBufferLowWaterMark(int i);

    UdtChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    UdtChannelConfig setWriteSpinCount(int i);
}
