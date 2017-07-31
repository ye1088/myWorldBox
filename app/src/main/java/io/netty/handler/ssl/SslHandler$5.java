package io.netty.handler.ssl;

import io.netty.util.concurrent.Promise;

class SslHandler$5 implements Runnable {
    final /* synthetic */ SslHandler this$0;
    final /* synthetic */ Promise val$p;

    SslHandler$5(SslHandler sslHandler, Promise promise) {
        this.this$0 = sslHandler;
        this.val$p = promise;
    }

    public void run() {
        if (!this.val$p.isDone()) {
            SslHandler.access$700(this.this$0, SslHandler.access$600());
        }
    }
}
