package com.huluxia.image.pipeline.producers;

import android.util.Pair;
import com.huluxia.framework.base.utils.Preconditions;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: ThrottlingProducer */
public class ax<T> implements am<T> {
    public static final String Ji = "ThrottlingProducer";
    private final am<T> IY;
    private final int LN;
    @GuardedBy("this")
    private int LO = 0;
    @GuardedBy("this")
    private final ConcurrentLinkedQueue<Pair<j<T>, ao>> LQ = new ConcurrentLinkedQueue();
    private final Executor mExecutor;

    /* compiled from: ThrottlingProducer */
    private class a extends m<T, T> {
        final /* synthetic */ ax LR;

        private a(ax axVar, j<T> consumer) {
            this.LR = axVar;
            super(consumer);
        }

        protected void d(T newResult, boolean isLast) {
            oM().e(newResult, isLast);
            if (isLast) {
                pr();
            }
        }

        protected void h(Throwable t) {
            oM().j(t);
            pr();
        }

        protected void ns() {
            oM().iq();
            pr();
        }

        private void pr() {
            synchronized (this.LR) {
                final Pair<j<T>, ao> nextRequestPair = (Pair) this.LR.LQ.poll();
                if (nextRequestPair == null) {
                    this.LR.LO = this.LR.LO - 1;
                }
            }
            if (nextRequestPair != null) {
                this.LR.mExecutor.execute(new Runnable(this) {
                    final /* synthetic */ a LV;

                    public void run() {
                        this.LV.LR.g((j) nextRequestPair.first, (ao) nextRequestPair.second);
                    }
                });
            }
        }
    }

    public ax(int maxSimultaneousRequests, Executor executor, am<T> inputProducer) {
        this.LN = maxSimultaneousRequests;
        this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
        this.IY = (am) Preconditions.checkNotNull(inputProducer);
    }

    public void b(j<T> consumer, ao producerContext) {
        boolean delayRequest;
        producerContext.oB().n(producerContext.getId(), Ji);
        synchronized (this) {
            if (this.LO >= this.LN) {
                this.LQ.add(Pair.create(consumer, producerContext));
                delayRequest = true;
            } else {
                this.LO++;
                delayRequest = false;
            }
        }
        if (!delayRequest) {
            g(consumer, producerContext);
        }
    }

    void g(j<T> consumer, ao producerContext) {
        producerContext.oB().c(producerContext.getId(), Ji, null);
        this.IY.b(new a(consumer), producerContext);
    }
}
