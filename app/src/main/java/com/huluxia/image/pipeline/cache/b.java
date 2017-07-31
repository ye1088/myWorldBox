package com.huluxia.image.pipeline.cache;

import android.net.Uri;
import com.huluxia.framework.base.utils.Objects;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.image.base.imagepipeline.common.a;
import com.huluxia.image.base.imagepipeline.common.c;
import com.huluxia.image.base.imagepipeline.common.d;
import com.huluxia.image.core.common.time.RealtimeSinceBootClock;
import java.util.Locale;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
/* compiled from: BitmapMemoryCacheKey */
public class b implements com.huluxia.image.base.cache.common.b {
    private final int EA;
    private final long EB;
    private final String Eu;
    @Nullable
    private final c Ev;
    private final d Ew;
    private final a Ex;
    @Nullable
    private final com.huluxia.image.base.cache.common.b Ey;
    @Nullable
    private final String Ez;
    private final Object ty;

    public b(String sourceString, @Nullable c resizeOptions, d rotationOptions, a imageDecodeOptions, @Nullable com.huluxia.image.base.cache.common.b postprocessorCacheKey, @Nullable String postprocessorName, Object callerContext) {
        this.Eu = (String) Preconditions.checkNotNull(sourceString);
        this.Ev = resizeOptions;
        this.Ew = rotationOptions;
        this.Ex = imageDecodeOptions;
        this.Ey = postprocessorCacheKey;
        this.Ez = postprocessorName;
        this.EA = com.huluxia.image.core.common.util.a.a(Integer.valueOf(sourceString.hashCode()), Integer.valueOf(resizeOptions != null ? resizeOptions.hashCode() : 0), Integer.valueOf(rotationOptions.hashCode()), this.Ex, this.Ey, (Object) postprocessorName);
        this.ty = callerContext;
        this.EB = RealtimeSinceBootClock.get().now();
    }

    public boolean equals(Object o) {
        if (!(o instanceof b)) {
            return false;
        }
        b otherKey = (b) o;
        if (this.EA == otherKey.EA && this.Eu.equals(otherKey.Eu) && Objects.equal(this.Ev, otherKey.Ev) && Objects.equal(this.Ew, otherKey.Ew) && Objects.equal(this.Ex, otherKey.Ex) && Objects.equal(this.Ey, otherKey.Ey) && Objects.equal(this.Ez, otherKey.Ez)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.EA;
    }

    public boolean c(Uri uri) {
        return getUriString().contains(uri.toString());
    }

    public String getUriString() {
        return this.Eu;
    }

    @Nullable
    public String lr() {
        return this.Ez;
    }

    public String toString() {
        return String.format((Locale) null, "%s_%s_%s_%s_%s_%s_%d", new Object[]{this.Eu, this.Ev, this.Ew, this.Ex, this.Ey, this.Ez, Integer.valueOf(this.EA)});
    }

    public Object gk() {
        return this.ty;
    }

    public long ls() {
        return this.EB;
    }
}
