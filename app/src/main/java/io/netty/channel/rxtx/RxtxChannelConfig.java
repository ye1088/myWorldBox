package io.netty.channel.rxtx;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;

public interface RxtxChannelConfig extends ChannelConfig {
    int getBaudrate();

    Databits getDatabits();

    Paritybit getParitybit();

    int getReadTimeout();

    Stopbits getStopbits();

    int getWaitTimeMillis();

    boolean isDtr();

    boolean isRts();

    RxtxChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

    RxtxChannelConfig setAutoClose(boolean z);

    RxtxChannelConfig setAutoRead(boolean z);

    RxtxChannelConfig setBaudrate(int i);

    RxtxChannelConfig setConnectTimeoutMillis(int i);

    RxtxChannelConfig setDatabits(Databits databits);

    RxtxChannelConfig setDtr(boolean z);

    @Deprecated
    RxtxChannelConfig setMaxMessagesPerRead(int i);

    RxtxChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

    RxtxChannelConfig setParitybit(Paritybit paritybit);

    RxtxChannelConfig setReadTimeout(int i);

    RxtxChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

    RxtxChannelConfig setRts(boolean z);

    RxtxChannelConfig setStopbits(Stopbits stopbits);

    RxtxChannelConfig setWaitTimeMillis(int i);

    @Deprecated
    RxtxChannelConfig setWriteBufferHighWaterMark(int i);

    @Deprecated
    RxtxChannelConfig setWriteBufferLowWaterMark(int i);

    RxtxChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    RxtxChannelConfig setWriteSpinCount(int i);
}
