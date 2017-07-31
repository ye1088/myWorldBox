package io.netty.channel.socket;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import java.net.InetAddress;
import java.net.NetworkInterface;

public interface DatagramChannelConfig extends ChannelConfig {
    InetAddress getInterface();

    NetworkInterface getNetworkInterface();

    int getReceiveBufferSize();

    int getSendBufferSize();

    int getTimeToLive();

    int getTrafficClass();

    boolean isBroadcast();

    boolean isLoopbackModeDisabled();

    boolean isReuseAddress();

    DatagramChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

    DatagramChannelConfig setAutoClose(boolean z);

    DatagramChannelConfig setAutoRead(boolean z);

    DatagramChannelConfig setBroadcast(boolean z);

    DatagramChannelConfig setConnectTimeoutMillis(int i);

    DatagramChannelConfig setInterface(InetAddress inetAddress);

    DatagramChannelConfig setLoopbackModeDisabled(boolean z);

    @Deprecated
    DatagramChannelConfig setMaxMessagesPerRead(int i);

    DatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

    DatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface);

    DatagramChannelConfig setReceiveBufferSize(int i);

    DatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

    DatagramChannelConfig setReuseAddress(boolean z);

    DatagramChannelConfig setSendBufferSize(int i);

    DatagramChannelConfig setTimeToLive(int i);

    DatagramChannelConfig setTrafficClass(int i);

    DatagramChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    DatagramChannelConfig setWriteSpinCount(int i);
}
