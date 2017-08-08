package com.MCWorld.image.pipeline.request;

import android.net.Uri;
import java.util.ArrayList;
import java.util.List;

/* compiled from: MediaVariations */
public class c$a {
    private List<c$b> Mu;
    private boolean Mv;
    private final String mMediaId;

    private c$a(String mediaId) {
        this.Mv = false;
        this.mMediaId = mediaId;
    }

    public c$a a(Uri uri, int width, int height) {
        if (this.Mu == null) {
            this.Mu = new ArrayList();
        }
        this.Mu.add(new c$b(uri, width, height));
        return this;
    }

    public c$a aw(boolean forceRequestForSpecifiedUri) {
        this.Mv = forceRequestForSpecifiedUri;
        return this;
    }

    public c pP() {
        return new c(this, null);
    }
}
