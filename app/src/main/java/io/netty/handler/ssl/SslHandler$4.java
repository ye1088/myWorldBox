package io.netty.handler.ssl;

import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.Promise;

class SslHandler$4 implements FutureListener<Channel> {
    final /* synthetic */ SslHandler this$0;
    final /* synthetic */ Promise val$newHandshakePromise;

    SslHandler$4(SslHandler sslHandler, Promise promise) {
        this.this$0 = sslHandler;
        this.val$newHandshakePromise = promise;
    }

    public void operationComplete(Future<Channel> future) throws Exception {
        if (future.isSuccess()) {
            this.val$newHandshakePromise.setSuccess(future.getNow());
        } else {
            this.val$newHandshakePromise.setFailure(future.cause());
        }
    }
}
