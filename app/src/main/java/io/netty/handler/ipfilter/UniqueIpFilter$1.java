package io.netty.handler.ipfilter;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import java.net.InetAddress;

class UniqueIpFilter$1 implements ChannelFutureListener {
    final /* synthetic */ UniqueIpFilter this$0;
    final /* synthetic */ InetAddress val$remoteIp;

    UniqueIpFilter$1(UniqueIpFilter uniqueIpFilter, InetAddress inetAddress) {
        this.this$0 = uniqueIpFilter;
        this.val$remoteIp = inetAddress;
    }

    public void operationComplete(ChannelFuture future) throws Exception {
        UniqueIpFilter.access$000(this.this$0).remove(this.val$remoteIp);
    }
}
