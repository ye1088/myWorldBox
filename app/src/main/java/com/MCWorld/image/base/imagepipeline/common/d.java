package com.MCWorld.image.base.imagepipeline.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

/* compiled from: RotationOptions */
public class d {
    private static final int wA = -1;
    private static final int wB = -2;
    public static final int ww = 0;
    public static final int wx = 90;
    public static final int wy = 180;
    public static final int wz = 270;
    private final int wC;
    private final boolean wD;

    @Retention(RetentionPolicy.SOURCE)
    /* compiled from: RotationOptions */
    public @interface a {
    }

    public static d hA() {
        return new d(-1, false);
    }

    public static d hB() {
        return new d(-2, false);
    }

    public static d hC() {
        return new d(-1, true);
    }

    public static d bn(int angle) {
        return new d(angle, false);
    }

    private d(int rotation, boolean canDeferUntilRendered) {
        this.wC = rotation;
        this.wD = canDeferUntilRendered;
    }

    public boolean hD() {
        return this.wC == -1;
    }

    public boolean hE() {
        return this.wC != -2;
    }

    public int hF() {
        if (!hD()) {
            return this.wC;
        }
        throw new IllegalStateException("Rotation is set to use EXIF");
    }

    public boolean hG() {
        return this.wD;
    }

    public int hashCode() {
        return com.MCWorld.image.core.common.util.a.b(Integer.valueOf(this.wC), Boolean.valueOf(this.wD));
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof d)) {
            return false;
        }
        d that = (d) other;
        if (this.wC == that.wC && this.wD == that.wD) {
            return true;
        }
        return false;
    }

    public String toString() {
        return String.format((Locale) null, "%d defer:%b", new Object[]{Integer.valueOf(this.wC), Boolean.valueOf(this.wD)});
    }
}
