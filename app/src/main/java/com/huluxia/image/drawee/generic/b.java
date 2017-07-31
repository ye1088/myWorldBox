package com.huluxia.image.drawee.generic;

import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.image.drawee.drawable.a;
import com.huluxia.image.drawee.drawable.o.c;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;

/* compiled from: GenericDraweeHierarchyBuilder */
public class b {
    public static final int DEFAULT_FADE_DURATION = 300;
    public static final c Dd = c.CJ;
    public static final c De = c.CK;
    private RoundingParams CZ;
    private int Df;
    private float Dg;
    private Drawable Dh;
    @Nullable
    private c Di;
    private Drawable Dj;
    private c Dk;
    private Drawable Dl;
    private c Dm;
    private Drawable Dn;
    private c Do;
    private c Dp;
    private Matrix Dq;
    private PointF Dr;
    private ColorFilter Ds;
    private Drawable Dt;
    private List<Drawable> Du;
    private Drawable Dv;
    private Resources mResources;

    public b(Resources resources) {
        this.mResources = resources;
        init();
    }

    public static b a(Resources resources) {
        return new b(resources);
    }

    private void init() {
        this.Df = 300;
        this.Dg = 0.0f;
        this.Dh = null;
        this.Di = Dd;
        this.Dj = null;
        this.Dk = Dd;
        this.Dl = null;
        this.Dm = Dd;
        this.Dn = null;
        this.Do = Dd;
        this.Dp = De;
        this.Dq = null;
        this.Dr = null;
        this.Ds = null;
        this.Dt = null;
        this.Du = null;
        this.Dv = null;
        this.CZ = null;
    }

    public b kC() {
        init();
        return this;
    }

    public Resources getResources() {
        return this.mResources;
    }

    public b cb(int fadeDuration) {
        this.Df = fadeDuration;
        return this;
    }

    public int ky() {
        return this.Df;
    }

    public b i(float desiredAspectRatio) {
        this.Dg = desiredAspectRatio;
        return this;
    }

    public float kD() {
        return this.Dg;
    }

    public b o(@Nullable Drawable placeholderDrawable) {
        this.Dh = placeholderDrawable;
        return this;
    }

    public b cc(int resourceId) {
        this.Dh = this.mResources.getDrawable(resourceId);
        return this;
    }

    @Nullable
    public Drawable kE() {
        return this.Dh;
    }

    public b c(@Nullable c placeholderImageScaleType) {
        this.Di = placeholderImageScaleType;
        return this;
    }

    @Nullable
    public c kF() {
        return this.Di;
    }

    public b f(Drawable placeholderDrawable, @Nullable c placeholderImageScaleType) {
        this.Dh = placeholderDrawable;
        this.Di = placeholderImageScaleType;
        return this;
    }

    public b e(int resourceId, @Nullable c placeholderImageScaleType) {
        this.Dh = this.mResources.getDrawable(resourceId);
        this.Di = placeholderImageScaleType;
        return this;
    }

    public b p(@Nullable Drawable retryDrawable) {
        this.Dj = retryDrawable;
        return this;
    }

    public b cd(int resourceId) {
        this.Dj = this.mResources.getDrawable(resourceId);
        return this;
    }

    @Nullable
    public Drawable kG() {
        return this.Dj;
    }

    public b d(@Nullable c retryImageScaleType) {
        this.Dk = retryImageScaleType;
        return this;
    }

    @Nullable
    public c kH() {
        return this.Dk;
    }

    public b g(Drawable retryDrawable, @Nullable c retryImageScaleType) {
        this.Dj = retryDrawable;
        this.Dk = retryImageScaleType;
        return this;
    }

    public b f(int resourceId, @Nullable c retryImageScaleType) {
        this.Dj = this.mResources.getDrawable(resourceId);
        this.Dk = retryImageScaleType;
        return this;
    }

    public b q(@Nullable Drawable failureDrawable) {
        this.Dl = failureDrawable;
        return this;
    }

    public b ce(int resourceId) {
        this.Dl = this.mResources.getDrawable(resourceId);
        return this;
    }

    @Nullable
    public Drawable kI() {
        return this.Dl;
    }

    public b e(@Nullable c failureImageScaleType) {
        this.Dm = failureImageScaleType;
        return this;
    }

    @Nullable
    public c kJ() {
        return this.Dm;
    }

    public b h(Drawable failureDrawable, @Nullable c failureImageScaleType) {
        this.Dl = failureDrawable;
        this.Dm = failureImageScaleType;
        return this;
    }

    public b g(int resourceId, @Nullable c failureImageScaleType) {
        this.Dl = this.mResources.getDrawable(resourceId);
        this.Dm = failureImageScaleType;
        return this;
    }

    public b r(@Nullable Drawable progressBarDrawable) {
        this.Dn = progressBarDrawable;
        return this;
    }

    public b cf(int resourceId) {
        this.Dn = this.mResources.getDrawable(resourceId);
        return this;
    }

    @Nullable
    public Drawable kK() {
        return this.Dn;
    }

    public b f(@Nullable c progressBarImageScaleType) {
        this.Do = progressBarImageScaleType;
        return this;
    }

    @Nullable
    public c kL() {
        return this.Do;
    }

    public b i(Drawable progressBarDrawable, @Nullable c progressBarImageScaleType) {
        this.Dn = progressBarDrawable;
        this.Do = progressBarImageScaleType;
        return this;
    }

    public b h(int resourceId, @Nullable c progressBarImageScaleType) {
        this.Dn = this.mResources.getDrawable(resourceId);
        this.Do = progressBarImageScaleType;
        return this;
    }

    public b g(@Nullable c actualImageScaleType) {
        this.Dp = actualImageScaleType;
        this.Dq = null;
        return this;
    }

    @Nullable
    public c kz() {
        return this.Dp;
    }

    @Deprecated
    public b c(@Nullable Matrix actualImageMatrix) {
        this.Dq = actualImageMatrix;
        this.Dp = null;
        return this;
    }

    @Nullable
    public Matrix kM() {
        return this.Dq;
    }

    public b d(@Nullable PointF focusPoint) {
        this.Dr = focusPoint;
        return this;
    }

    @Nullable
    public PointF kN() {
        return this.Dr;
    }

    public b b(@Nullable ColorFilter colorFilter) {
        this.Ds = colorFilter;
        return this;
    }

    @Nullable
    public ColorFilter kO() {
        return this.Ds;
    }

    @Deprecated
    public b m(@Nullable List<Drawable> backgrounds) {
        if (backgrounds == null) {
            this.Dt = null;
        } else {
            this.Dt = new a((Drawable[]) backgrounds.toArray(new Drawable[backgrounds.size()]));
        }
        return this;
    }

    public b s(@Nullable Drawable background) {
        this.Dt = background;
        return this;
    }

    @Nullable
    public Drawable getBackground() {
        return this.Dt;
    }

    public b n(@Nullable List<Drawable> overlays) {
        this.Du = overlays;
        return this;
    }

    public b t(@Nullable Drawable overlay) {
        if (overlay == null) {
            this.Du = null;
        } else {
            this.Du = Arrays.asList(new Drawable[]{overlay});
        }
        return this;
    }

    @Nullable
    public List<Drawable> getOverlays() {
        return this.Du;
    }

    public b u(@Nullable Drawable drawable) {
        if (drawable == null) {
            this.Dv = null;
        } else {
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{16842919}, drawable);
            this.Dv = stateListDrawable;
        }
        return this;
    }

    @Nullable
    public Drawable kP() {
        return this.Dv;
    }

    public b b(@Nullable RoundingParams roundingParams) {
        this.CZ = roundingParams;
        return this;
    }

    @Nullable
    public RoundingParams kB() {
        return this.CZ;
    }

    private void validate() {
        if (this.Du != null) {
            for (Drawable overlay : this.Du) {
                Preconditions.checkNotNull(overlay);
            }
        }
    }

    public a kQ() {
        validate();
        return new a(this);
    }
}
