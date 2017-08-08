package com.MCWorld.image.base.imagepipeline.common;

import android.graphics.Bitmap.Config;
import java.util.Locale;
import javax.annotation.concurrent.Immutable;

@Immutable
/* compiled from: ImageDecodeOptions */
public class a {
    private static final a wg = hs().hz();
    public final int wh;
    public final boolean wi;
    public final boolean wj;
    public final boolean wk;
    public final boolean wl;
    public final Config wm;

    public a(b b) {
        this.wh = b.ht();
        this.wi = b.hu();
        this.wj = b.hv();
        this.wk = b.hw();
        this.wl = b.hx();
        this.wm = b.hy();
    }

    public static a hr() {
        return wg;
    }

    public static b hs() {
        return new b();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        a that = (a) o;
        if (this.wi != that.wi) {
            return false;
        }
        if (this.wj != that.wj) {
            return false;
        }
        if (this.wk != that.wk) {
            return false;
        }
        if (this.wl != that.wl) {
            return false;
        }
        if (this.wm != that.wm) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int i;
        int i2 = 1;
        int i3 = ((this.wh * 31) + (this.wi ? 1 : 0)) * 31;
        if (this.wj) {
            i = 1;
        } else {
            i = 0;
        }
        i3 = (i3 + i) * 31;
        if (this.wk) {
            i = 1;
        } else {
            i = 0;
        }
        i = (i3 + i) * 31;
        if (!this.wl) {
            i2 = 0;
        }
        return ((i + i2) * 31) + this.wm.ordinal();
    }

    public String toString() {
        return String.format((Locale) null, "%d-%b-%b-%b-%b-%s", new Object[]{Integer.valueOf(this.wh), Boolean.valueOf(this.wi), Boolean.valueOf(this.wj), Boolean.valueOf(this.wk), Boolean.valueOf(this.wl), this.wm.name()});
    }
}
