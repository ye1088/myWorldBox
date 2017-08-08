package com.MCWorld.image.pipeline.producers;

/* compiled from: SwallowResultProducer */
public class au<T> implements am<Void> {
    private final am<T> IY;

    public au(am<T> inputProducer) {
        this.IY = inputProducer;
    }

    public void b(j<Void> consumer, ao producerContext) {
        this.IY.b(new m<T, Void>(this, consumer) {
            final /* synthetic */ au LG;

            protected void d(T t, boolean isLast) {
                if (isLast) {
                    oM().e(null, isLast);
                }
            }
        }, producerContext);
    }
}
