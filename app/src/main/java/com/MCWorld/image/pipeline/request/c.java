package com.MCWorld.image.pipeline.request;

import com.MCWorld.framework.base.utils.Objects;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
/* compiled from: MediaVariations */
public final class c {
    @Nullable
    private final List<b> Mu;
    private final boolean Mv;
    private final String mMediaId;

    private c(a builder) {
        this.mMediaId = a.a(builder);
        this.Mu = a.b(builder);
        this.Mv = a.c(builder);
    }

    public String getMediaId() {
        return this.mMediaId;
    }

    @Nullable
    public List<b> pN() {
        return this.Mu;
    }

    public boolean pO() {
        return this.Mv;
    }

    public boolean equals(Object o) {
        if (!(o instanceof c)) {
            return false;
        }
        c otherVariations = (c) o;
        if (Objects.equal(this.mMediaId, otherVariations.mMediaId) && this.Mv == otherVariations.Mv && Objects.equal(this.Mu, otherVariations.Mu)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(this.mMediaId, Boolean.valueOf(this.Mv), this.Mu);
    }

    @Nullable
    public static c bM(@Nullable String mediaId) {
        if (mediaId == null) {
            return null;
        }
        return bN(mediaId).pP();
    }

    public static a bN(String mediaId) {
        return new a(mediaId, null);
    }
}
