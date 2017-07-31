package com.huluxia.image.pipeline.datasource;

import com.huluxia.image.core.datasource.c;
import com.huluxia.image.pipeline.producers.am;
import com.huluxia.image.pipeline.producers.at;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
/* compiled from: ProducerToDataSourceAdapter */
public class f<T> extends a<T> {
    public static <T> c<T> a(am<T> producer, at settableProducerContext, com.huluxia.image.pipeline.listener.c listener) {
        return new f(producer, settableProducerContext, listener);
    }

    private f(am<T> producer, at settableProducerContext, com.huluxia.image.pipeline.listener.c listener) {
        super(producer, settableProducerContext, listener);
    }
}
