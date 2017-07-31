package io.netty.channel.sctp;

import com.sun.nio.sctp.SctpStandardSocketOptions.InitMaxStreams;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;

public interface SctpChannelConfig extends ChannelConfig {
    InitMaxStreams getInitMaxStreams();

    int getReceiveBufferSize();

    int getSendBufferSize();

    boolean isSctpNoDelay();

    SctpChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

    SctpChannelConfig setAutoClose(boolean z);

    SctpChannelConfig setAutoRead(boolean z);

    SctpChannelConfig setConnectTimeoutMillis(int i);

    SctpChannelConfig setInitMaxStreams(InitMaxStreams initMaxStreams);

    @Deprecated
    SctpChannelConfig setMaxMessagesPerRead(int i);

    SctpChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

    SctpChannelConfig setReceiveBufferSize(int i);

    SctpChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

    SctpChannelConfig setSctpNoDelay(boolean z);

    SctpChannelConfig setSendBufferSize(int i);

    @Deprecated
    SctpChannelConfig setWriteBufferHighWaterMark(int i);

    @Deprecated
    SctpChannelConfig setWriteBufferLowWaterMark(int i);

    SctpChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    SctpChannelConfig setWriteSpinCount(int i);
}
