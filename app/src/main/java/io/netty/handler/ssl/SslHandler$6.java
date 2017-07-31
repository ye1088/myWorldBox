package io.netty.handler.ssl;

import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import java.util.concurrent.ScheduledFuture;

class SslHandler$6 implements FutureListener<Channel> {
    final /* synthetic */ SslHandler this$0;
    final /* synthetic */ ScheduledFuture val$timeoutFuture;

    SslHandler$6(SslHandler sslHandler, ScheduledFuture scheduledFuture) {
        this.this$0 = sslHandler;
        this.val$timeoutFuture = scheduledFuture;
    }

    public void operationComplete(Future<Channel> future) throws Exception {
        this.val$timeoutFuture.cancel(false);
    }
}
