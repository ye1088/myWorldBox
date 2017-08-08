package com.MCWorld.image.pipeline.request;

import android.net.Uri;
import com.MCWorld.framework.base.utils.Objects;

/* compiled from: MediaVariations */
public final class c$b {
    private final int mHeight;
    private final Uri mUri;
    private final int mWidth;

    public c$b(Uri uri, int width, int height) {
        this.mUri = uri;
        this.mWidth = width;
        this.mHeight = height;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public boolean equals(Object o) {
        if (!(o instanceof c$b)) {
            return false;
        }
        c$b otherVariant = (c$b) o;
        if (Objects.equal(this.mUri, otherVariant.mUri) && this.mWidth == otherVariant.mWidth && this.mHeight == otherVariant.mHeight) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((this.mUri.hashCode() * 31) + this.mWidth) * 31) + this.mHeight;
    }
}
