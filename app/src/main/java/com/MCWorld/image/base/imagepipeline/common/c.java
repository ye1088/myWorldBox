package com.MCWorld.image.base.imagepipeline.common;

import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.image.base.imageutils.a;
import java.util.Locale;

/* compiled from: ResizeOptions */
public class c {
    public static final float wt = 0.6666667f;
    public final int height;
    public final int width;
    public final float wu;
    public final float wv;

    public c(int width, int height) {
        this(width, height, a.xu);
    }

    public c(int width, int height, float maxBitmapSize) {
        this(width, height, maxBitmapSize, wt);
    }

    public c(int width, int height, float maxBitmapSize, float roundUpFraction) {
        boolean z = true;
        Preconditions.checkArgument(width > 0);
        if (height <= 0) {
            z = false;
        }
        Preconditions.checkArgument(z);
        this.width = width;
        this.height = height;
        this.wu = maxBitmapSize;
        this.wv = roundUpFraction;
    }

    public int hashCode() {
        return com.MCWorld.image.core.common.util.a.hashCode(this.width, this.height);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof c)) {
            return false;
        }
        c that = (c) other;
        if (this.width == that.width && this.height == that.height) {
            return true;
        }
        return false;
    }

    public String toString() {
        return String.format((Locale) null, "%getMCVersion%d", new Object[]{Integer.valueOf(this.width), Integer.valueOf(this.height)});
    }
}
