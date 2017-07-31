package io.netty.channel.socket.oio;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.socket.SocketChannelConfig;

public interface OioSocketChannelConfig extends SocketChannelConfig {
    int getSoTimeout();

    OioSocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

    OioSocketChannelConfig setAllowHalfClosure(boolean z);

    OioSocketChannelConfig setAutoClose(boolean z);

    OioSocketChannelConfig setAutoRead(boolean z);

    OioSocketChannelConfig setConnectTimeoutMillis(int i);

    OioSocketChannelConfig setKeepAlive(boolean z);

    @Deprecated
    OioSocketChannelConfig setMaxMessagesPerRead(int i);

    OioSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

    OioSocketChannelConfig setPerformancePreferences(int i, int i2, int i3);

    OioSocketChannelConfig setReceiveBufferSize(int i);

    OioSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

    OioSocketChannelConfig setReuseAddress(boolean z);

    OioSocketChannelConfig setSendBufferSize(int i);

    OioSocketChannelConfig setSoLinger(int i);

    OioSocketChannelConfig setSoTimeout(int i);

    OioSocketChannelConfig setTcpNoDelay(boolean z);

    OioSocketChannelConfig setTrafficClass(int i);

    @Deprecated
    OioSocketChannelConfig setWriteBufferHighWaterMark(int i);

    @Deprecated
    OioSocketChannelConfig setWriteBufferLowWaterMark(int i);

    OioSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    OioSocketChannelConfig setWriteSpinCount(int i);
}
