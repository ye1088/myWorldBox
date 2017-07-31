package com.huluxia.image.base.cache.common;

import android.net.Uri;
import com.huluxia.framework.base.utils.Preconditions;
import java.util.List;

/* compiled from: MultiCacheKey */
public class e implements b {
    final List<b> tz;

    public e(List<b> cacheKeys) {
        this.tz = (List) Preconditions.checkNotNull(cacheKeys);
    }

    public List<b> gl() {
        return this.tz;
    }

    public String toString() {
        return "MultiCacheKey:" + this.tz.toString();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof e)) {
            return false;
        }
        return this.tz.equals(((e) o).tz);
    }

    public int hashCode() {
        return this.tz.hashCode();
    }

    public boolean c(Uri uri) {
        for (int i = 0; i < this.tz.size(); i++) {
            if (((b) this.tz.get(i)).c(uri)) {
                return true;
            }
        }
        return false;
    }

    public String getUriString() {
        return ((b) this.tz.get(0)).getUriString();
    }
}
