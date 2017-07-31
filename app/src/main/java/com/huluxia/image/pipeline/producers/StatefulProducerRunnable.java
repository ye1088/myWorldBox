package com.huluxia.image.pipeline.producers;

import com.huluxia.image.core.common.executors.StatefulRunnable;
import java.util.Map;

public abstract class StatefulProducerRunnable<T> extends StatefulRunnable<T> {
    private final j<T> JG;
    private final aq Jb;
    private final String LD;
    private final String Lf;

    protected abstract void p(T t);

    public StatefulProducerRunnable(j<T> consumer, aq producerListener, String producerName, String requestId) {
        this.JG = consumer;
        this.Jb = producerListener;
        this.LD = producerName;
        this.Lf = requestId;
        this.Jb.n(this.Lf, this.LD);
    }

    protected void o(T result) {
        this.Jb.c(this.Lf, this.LD, this.Jb.bE(this.Lf) ? M(result) : null);
        this.JG.e(result, true);
    }

    protected void i(Exception e) {
        this.Jb.a(this.Lf, this.LD, e, this.Jb.bE(this.Lf) ? k(e) : null);
        this.JG.j(e);
    }

    protected void iq() {
        this.Jb.d(this.Lf, this.LD, this.Jb.bE(this.Lf) ? pm() : null);
        this.JG.iq();
    }

    protected Map<String, String> M(T t) {
        return null;
    }

    protected Map<String, String> k(Exception exception) {
        return null;
    }

    protected Map<String, String> pm() {
        return null;
    }
}
