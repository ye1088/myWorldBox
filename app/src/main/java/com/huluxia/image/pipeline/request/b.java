package com.huluxia.image.pipeline.request;

/* compiled from: BaseRepeatedPostProcessor */
public abstract class b extends a implements e {
    private f Ml;

    public synchronized void a(f runner) {
        this.Ml = runner;
    }

    private synchronized f pt() {
        return this.Ml;
    }

    public void update() {
        f callback = pt();
        if (callback != null) {
            callback.update();
        }
    }
}
