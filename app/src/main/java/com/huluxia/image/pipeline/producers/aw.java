package com.huluxia.image.pipeline.producers;

import com.huluxia.framework.base.utils.Preconditions;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executor;

/* compiled from: ThreadHandoffProducerQueue */
public class aw {
    private boolean LL = false;
    private final Deque<Runnable> LM;
    private final Executor mExecutor;

    public aw(Executor executor) {
        this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
        this.LM = new ArrayDeque();
    }

    public synchronized void e(Runnable runnable) {
        if (this.LL) {
            this.LM.add(runnable);
        } else {
            this.mExecutor.execute(runnable);
        }
    }

    public synchronized void pn() {
        this.LL = true;
    }

    public synchronized void po() {
        this.LL = false;
        pp();
    }

    private void pp() {
        while (!this.LM.isEmpty()) {
            this.mExecutor.execute((Runnable) this.LM.pop());
        }
        this.LM.clear();
    }

    public synchronized void f(Runnable runnable) {
        this.LM.remove(runnable);
    }

    public synchronized boolean pq() {
        return this.LL;
    }
}
