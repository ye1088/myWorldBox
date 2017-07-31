package com.huluxia.image.pipeline.producers;

import com.huluxia.image.base.imagepipeline.image.d;
import java.util.Map;
import javax.annotation.Nullable;

/* compiled from: NetworkFetcher */
public interface ah<FETCH_STATE extends s> {
    FETCH_STATE a(j<d> jVar, ao aoVar);

    @Nullable
    Map<String, String> a(FETCH_STATE fetch_state, int i);

    void a(FETCH_STATE fetch_state, a aVar);

    boolean a(FETCH_STATE fetch_state);

    void b(FETCH_STATE fetch_state, int i);
}
