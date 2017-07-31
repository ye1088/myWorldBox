package io.netty.handler.ipfilter;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.ConcurrentSet;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Set;

@Sharable
public class UniqueIpFilter extends AbstractRemoteAddressFilter<InetSocketAddress> {
    private final Set<InetAddress> connected = new ConcurrentSet();

    protected boolean accept(ChannelHandlerContext ctx, InetSocketAddress remoteAddress) throws Exception {
        InetAddress remoteIp = remoteAddress.getAddress();
        if (this.connected.contains(remoteIp)) {
            return false;
        }
        this.connected.add(remoteIp);
        ctx.channel().closeFuture().addListener(new 1(this, remoteIp));
        return true;
    }
}
