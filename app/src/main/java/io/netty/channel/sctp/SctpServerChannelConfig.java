package io.netty.channel.sctp;

import com.sun.nio.sctp.SctpStandardSocketOptions.InitMaxStreams;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;

public interface SctpServerChannelConfig extends ChannelConfig {
    int getBacklog();

    InitMaxStreams getInitMaxStreams();

    int getReceiveBufferSize();

    int getSendBufferSize();

    SctpServerChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

    SctpServerChannelConfig setAutoClose(boolean z);

    SctpServerChannelConfig setAutoRead(boolean z);

    SctpServerChannelConfig setBacklog(int i);

    SctpServerChannelConfig setConnectTimeoutMillis(int i);

    SctpServerChannelConfig setInitMaxStreams(InitMaxStreams initMaxStreams);

    @Deprecated
    SctpServerChannelConfig setMaxMessagesPerRead(int i);

    SctpServerChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

    SctpServerChannelConfig setReceiveBufferSize(int i);

    SctpServerChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

    SctpServerChannelConfig setSendBufferSize(int i);

    @Deprecated
    SctpServerChannelConfig setWriteBufferHighWaterMark(int i);

    @Deprecated
    SctpServerChannelConfig setWriteBufferLowWaterMark(int i);

    SctpServerChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    SctpServerChannelConfig setWriteSpinCount(int i);
}
