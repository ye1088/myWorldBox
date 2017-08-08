package com.MCWorld.image.pipeline.producers;

import android.net.Uri;
import com.MCWorld.image.base.imagepipeline.image.d;

/* compiled from: FetchState */
public class s {
    private final j<d> JG;
    private final ao JO;
    private long JP = 0;

    public s(j<d> consumer, ao context) {
        this.JG = consumer;
        this.JO = context;
    }

    public j<d> oM() {
        return this.JG;
    }

    public ao oN() {
        return this.JO;
    }

    public String getId() {
        return this.JO.getId();
    }

    public aq oB() {
        return this.JO.oB();
    }

    public Uri getUri() {
        return this.JO.oA().pv();
    }

    public long oO() {
        return this.JP;
    }

    public void R(long lastIntermediateResultTimeMs) {
        this.JP = lastIntermediateResultTimeMs;
    }
}
