package com.huluxia.mojang;

import java.util.concurrent.ThreadFactory;

class Mojang$TFactory implements ThreadFactory {
    final /* synthetic */ Mojang this$0;

    private Mojang$TFactory(Mojang mojang) {
        this.this$0 = mojang;
    }

    public Thread newThread(final Runnable r) {
        return new Thread(new Runnable() {
            public void run() {
                r.run();
            }
        }, "TFactory#Thread");
    }
}
