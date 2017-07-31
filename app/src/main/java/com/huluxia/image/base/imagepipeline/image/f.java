package com.huluxia.image.base.imagepipeline.image;

/* compiled from: ImmutableQualityInfo */
public class f implements g {
    public static final g wZ = a(Integer.MAX_VALUE, true, true);
    int xa;
    boolean xb;
    boolean xc;

    private f(int quality, boolean isOfGoodEnoughQuality, boolean isOfFullQuality) {
        this.xa = quality;
        this.xb = isOfGoodEnoughQuality;
        this.xc = isOfFullQuality;
    }

    public int getQuality() {
        return this.xa;
    }

    public boolean ia() {
        return this.xb;
    }

    public boolean ib() {
        return this.xc;
    }

    public int hashCode() {
        int i = 0;
        int i2 = (this.xb ? 4194304 : 0) ^ this.xa;
        if (this.xc) {
            i = 8388608;
        }
        return i2 ^ i;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof f)) {
            return false;
        }
        f that = (f) other;
        if (this.xa == that.xa && this.xb == that.xb && this.xc == that.xc) {
            return true;
        }
        return false;
    }

    public static g a(int quality, boolean isOfGoodEnoughQuality, boolean isOfFullQuality) {
        return new f(quality, isOfGoodEnoughQuality, isOfFullQuality);
    }
}
