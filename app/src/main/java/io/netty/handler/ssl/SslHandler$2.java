package io.netty.handler.ssl;

import java.util.List;
import java.util.concurrent.CountDownLatch;

class SslHandler$2 implements Runnable {
    final /* synthetic */ SslHandler this$0;
    final /* synthetic */ CountDownLatch val$latch;
    final /* synthetic */ List val$tasks;

    SslHandler$2(SslHandler sslHandler, List list, CountDownLatch countDownLatch) {
        this.this$0 = sslHandler;
        this.val$tasks = list;
        this.val$latch = countDownLatch;
    }

    public void run() {
        try {
            for (Runnable task : this.val$tasks) {
                task.run();
            }
        } catch (Exception e) {
            SslHandler.access$400(this.this$0).fireExceptionCaught(e);
        } finally {
            this.val$latch.countDown();
        }
    }
}
