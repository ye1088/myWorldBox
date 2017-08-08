package com.MCWorld.image.base.cache.common;

import android.net.Uri;
import com.MCWorld.framework.base.utils.Preconditions;

/* compiled from: SimpleCacheKey */
public class h implements b {
    final String tC;

    public h(String key) {
        this.tC = (String) Preconditions.checkNotNull(key);
    }

    public String toString() {
        return this.tC;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof h)) {
            return false;
        }
        return this.tC.equals(((h) o).tC);
    }

    public int hashCode() {
        return this.tC.hashCode();
    }

    public boolean c(Uri uri) {
        return this.tC.contains(uri.toString());
    }

    public String getUriString() {
        return this.tC;
    }
}
