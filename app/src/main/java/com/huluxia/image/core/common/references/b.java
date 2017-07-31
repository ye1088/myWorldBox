package com.huluxia.image.core.common.references;

import java.lang.ref.SoftReference;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/* compiled from: OOMSoftReference */
public class b<T> {
    SoftReference<T> yq = null;
    SoftReference<T> yr = null;
    SoftReference<T> ys = null;

    public void set(@Nonnull T hardReference) {
        this.yq = new SoftReference(hardReference);
        this.yr = new SoftReference(hardReference);
        this.ys = new SoftReference(hardReference);
    }

    @Nullable
    public T get() {
        return this.yq == null ? null : this.yq.get();
    }

    public void clear() {
        if (this.yq != null) {
            this.yq.clear();
            this.yq = null;
        }
        if (this.yr != null) {
            this.yr.clear();
            this.yr = null;
        }
        if (this.ys != null) {
            this.ys.clear();
            this.ys = null;
        }
    }
}
