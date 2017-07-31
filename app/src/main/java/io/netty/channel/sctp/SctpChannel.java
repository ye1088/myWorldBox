package io.netty.channel.sctp;

import com.sun.nio.sctp.Association;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Set;

public interface SctpChannel extends Channel {
    Set<InetSocketAddress> allLocalAddresses();

    Set<InetSocketAddress> allRemoteAddresses();

    Association association();

    ChannelFuture bindAddress(InetAddress inetAddress);

    ChannelFuture bindAddress(InetAddress inetAddress, ChannelPromise channelPromise);

    SctpChannelConfig config();

    InetSocketAddress localAddress();

    SctpServerChannel parent();

    InetSocketAddress remoteAddress();

    ChannelFuture unbindAddress(InetAddress inetAddress);

    ChannelFuture unbindAddress(InetAddress inetAddress, ChannelPromise channelPromise);
}
