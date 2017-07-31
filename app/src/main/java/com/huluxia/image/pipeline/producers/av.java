package com.huluxia.image.pipeline.producers;

import com.huluxia.framework.base.utils.Preconditions;

/* compiled from: ThreadHandoffProducer */
public class av<T> implements am<T> {
    public static final String Ji = "BackgroundThreadHandoffProducer";
    private final aw Fm;
    private final am<T> IY;

    public av(am<T> inputProducer, aw inputThreadHandoffProducerQueue) {
        this.IY = (am) Preconditions.checkNotNull(inputProducer);
        this.Fm = inputThreadHandoffProducerQueue;
    }

    public void b(j<T> consumer, ao context) {
        aq producerListener = context.oB();
        String requestId = context.getId();
        final aq aqVar = producerListener;
        final String str = requestId;
        final j<T> jVar = consumer;
        final ao aoVar = context;
        final StatefulProducerRunnable<T> statefulRunnable = new StatefulProducerRunnable<T>(this, consumer, producerListener, Ji, requestId) {
            final /* synthetic */ av LJ;

            protected void o(T t) {
                aqVar.c(str, av.Ji, null);
                this.LJ.IY.b(jVar, aoVar);
            }

            protected void p(T t) {
            }

            protected T getResult() throws Exception {
                return null;
            }
        };
        context.a(new e(this) {
            final /* synthetic */ av LJ;

            public void cj() {
                statefulRunnable.cancel();
                this.LJ.Fm.f(statefulRunnable);
            }
        });
        this.Fm.e(statefulRunnable);
    }
}
