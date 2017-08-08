package com.MCWorld.image.pipeline.producers;

import java.util.Map;
import javax.annotation.Nullable;

/* compiled from: BaseNetworkFetcher */
public abstract class c<FETCH_STATE extends s> implements ah<FETCH_STATE> {
    public boolean a(FETCH_STATE fetch_state) {
        return true;
    }

    public void b(FETCH_STATE fetch_state, int byteSize) {
    }

    @Nullable
    public Map<String, String> a(FETCH_STATE fetch_state, int byteSize) {
        return null;
    }
}
