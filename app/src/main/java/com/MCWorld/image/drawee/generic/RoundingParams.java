package com.MCWorld.image.drawee.generic;

import android.support.annotation.j;
import com.MCWorld.framework.base.utils.Preconditions;
import java.util.Arrays;

public class RoundingParams {
    private float Cg = 0.0f;
    private int Ch = 0;
    private float Ci = 0.0f;
    private int Cq = 0;
    private RoundingMethod Dx = RoundingMethod.BITMAP_ONLY;
    private boolean Dy = false;
    private float[] Dz = null;

    public enum RoundingMethod {
        OVERLAY_COLOR,
        BITMAP_ONLY
    }

    public RoundingParams ad(boolean roundAsCircle) {
        this.Dy = roundAsCircle;
        return this;
    }

    public boolean kR() {
        return this.Dy;
    }

    public RoundingParams j(float radius) {
        Arrays.fill(kU(), radius);
        return this;
    }

    public RoundingParams a(float topLeft, float topRight, float bottomRight, float bottomLeft) {
        float[] radii = kU();
        radii[1] = topLeft;
        radii[0] = topLeft;
        radii[3] = topRight;
        radii[2] = topRight;
        radii[5] = bottomRight;
        radii[4] = bottomRight;
        radii[7] = bottomLeft;
        radii[6] = bottomLeft;
        return this;
    }

    public RoundingParams b(float[] radii) {
        boolean z;
        Preconditions.checkNotNull(radii);
        if (radii.length == 8) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "radii should have exactly 8 values");
        System.arraycopy(radii, 0, kU(), 0, 8);
        return this;
    }

    public float[] kS() {
        return this.Dz;
    }

    public RoundingParams a(RoundingMethod roundingMethod) {
        this.Dx = roundingMethod;
        return this;
    }

    public RoundingMethod kT() {
        return this.Dx;
    }

    public RoundingParams cg(@j int overlayColor) {
        this.Cq = overlayColor;
        this.Dx = RoundingMethod.OVERLAY_COLOR;
        return this;
    }

    public int kn() {
        return this.Cq;
    }

    private float[] kU() {
        if (this.Dz == null) {
            this.Dz = new float[8];
        }
        return this.Dz;
    }

    public static RoundingParams kV() {
        return new RoundingParams().ad(true);
    }

    public static RoundingParams k(float radius) {
        return new RoundingParams().j(radius);
    }

    public static RoundingParams b(float topLeft, float topRight, float bottomRight, float bottomLeft) {
        return new RoundingParams().a(topLeft, topRight, bottomRight, bottomLeft);
    }

    public static RoundingParams c(float[] radii) {
        return new RoundingParams().b(radii);
    }

    public RoundingParams l(float width) {
        Preconditions.checkArgument(width >= 0.0f, "the border width cannot be < 0");
        this.Cg = width;
        return this;
    }

    public float kh() {
        return this.Cg;
    }

    public RoundingParams ch(@j int color) {
        this.Ch = color;
        return this;
    }

    public int kg() {
        return this.Ch;
    }

    public RoundingParams b(@j int color, float width) {
        Preconditions.checkArgument(width >= 0.0f, "the border width cannot be < 0");
        this.Cg = width;
        this.Ch = color;
        return this;
    }

    public RoundingParams m(float padding) {
        Preconditions.checkArgument(padding >= 0.0f, "the padding cannot be < 0");
        this.Ci = padding;
        return this;
    }

    public float ki() {
        return this.Ci;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoundingParams that = (RoundingParams) o;
        if (this.Dy == that.Dy && this.Cq == that.Cq && Float.compare(that.Cg, this.Cg) == 0 && this.Ch == that.Ch && Float.compare(that.Ci, this.Ci) == 0 && this.Dx == that.Dx) {
            return Arrays.equals(this.Dz, that.Dz);
        }
        return false;
    }

    public int hashCode() {
        int result;
        int i;
        int i2 = 0;
        if (this.Dx != null) {
            result = this.Dx.hashCode();
        } else {
            result = 0;
        }
        int i3 = result * 31;
        if (this.Dy) {
            i = 1;
        } else {
            i = 0;
        }
        i3 = (i3 + i) * 31;
        if (this.Dz != null) {
            i = Arrays.hashCode(this.Dz);
        } else {
            i = 0;
        }
        i3 = (((i3 + i) * 31) + this.Cq) * 31;
        if (this.Cg != 0.0f) {
            i = Float.floatToIntBits(this.Cg);
        } else {
            i = 0;
        }
        i = (((i3 + i) * 31) + this.Ch) * 31;
        if (this.Ci != 0.0f) {
            i2 = Float.floatToIntBits(this.Ci);
        }
        return i + i2;
    }
}
