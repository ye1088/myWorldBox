package com.MCWorld.image.pipeline.producers;

/* compiled from: DelegatingConsumer */
public abstract class m<I, O> extends b<I> {
    private final j<O> JG;

    public m(j<O> consumer) {
        this.JG = consumer;
    }

    public j<O> oM() {
        return this.JG;
    }

    protected void h(Throwable t) {
        this.JG.j(t);
    }

    protected void ns() {
        this.JG.iq();
    }

    protected void n(float progress) {
        this.JG.onProgressUpdate(progress);
    }
}
