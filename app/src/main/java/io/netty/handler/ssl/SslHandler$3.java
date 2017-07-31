package io.netty.handler.ssl;

import io.netty.util.concurrent.Promise;

class SslHandler$3 implements Runnable {
    final /* synthetic */ SslHandler this$0;
    final /* synthetic */ Promise val$promise;

    SslHandler$3(SslHandler sslHandler, Promise promise) {
        this.this$0 = sslHandler;
        this.val$promise = promise;
    }

    public void run() {
        SslHandler.access$500(this.this$0, this.val$promise);
    }
}
