package com.huluxia.image.drawee.generic;

import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.image.drawee.drawable.f;
import com.huluxia.image.drawee.drawable.g;
import com.huluxia.image.drawee.drawable.h;
import com.huluxia.image.drawee.drawable.n;
import com.huluxia.image.drawee.drawable.o;
import com.huluxia.image.drawee.interfaces.c;
import javax.annotation.Nullable;

/* compiled from: GenericDraweeHierarchy */
public class a implements c {
    private static final int CQ = 0;
    private static final int CS = 1;
    private static final int CT = 2;
    private static final int CU = 3;
    private static final int CV = 4;
    private static final int CW = 5;
    private static final int CX = 6;
    private final Drawable CY = new ColorDrawable(0);
    @Nullable
    private RoundingParams CZ;
    private final d Da;
    private final f Db;
    private final g Dc;
    private final Resources mResources;

    a(b builder) {
        this.mResources = builder.getResources();
        this.CZ = builder.kB();
        this.Dc = new g(this.CY);
        int numOverlays = (builder.getOverlays() != null ? builder.getOverlays().size() : 1) + (builder.kP() != null ? 1 : 0);
        Drawable[] layers = new Drawable[(numOverlays + 6)];
        layers[0] = a(builder.getBackground(), null);
        layers[1] = a(builder.kE(), builder.kF());
        layers[2] = a(this.Dc, builder.kz(), builder.kN(), builder.kM(), builder.kO());
        layers[3] = a(builder.kK(), builder.kL());
        layers[4] = a(builder.kG(), builder.kH());
        layers[5] = a(builder.kI(), builder.kJ());
        if (numOverlays > 0) {
            int index = 0;
            if (builder.getOverlays() != null) {
                for (Drawable overlay : builder.getOverlays()) {
                    int index2 = index + 1;
                    layers[index + 6] = a(overlay, null);
                    index = index2;
                }
            } else {
                index = 1;
            }
            if (builder.kP() != null) {
                layers[index + 6] = a(builder.kP(), null);
            }
        }
        this.Db = new f(layers);
        this.Db.bH(builder.ky());
        this.Da = new d(e.a(this.Db, this.CZ));
        this.Da.mutate();
        kw();
    }

    @Nullable
    private Drawable a(Drawable drawable, @Nullable o.c scaleType, @Nullable PointF focusPoint, @Nullable Matrix matrix, @Nullable ColorFilter colorFilter) {
        drawable.setColorFilter(colorFilter);
        return e.a(e.a(drawable, scaleType, focusPoint), matrix);
    }

    @Nullable
    private Drawable a(@Nullable Drawable drawable, @Nullable o.c scaleType) {
        return e.j(e.a(drawable, this.CZ, this.mResources), scaleType);
    }

    private void kv() {
        this.Dc.d(this.CY);
    }

    private void kw() {
        if (this.Db != null) {
            this.Db.jP();
            this.Db.jT();
            kx();
            bI(1);
            this.Db.jV();
            this.Db.jQ();
        }
    }

    private void kx() {
        bJ(1);
        bJ(2);
        bJ(3);
        bJ(4);
        bJ(5);
    }

    private void bI(int index) {
        if (index >= 0) {
            this.Db.bI(index);
        }
    }

    private void bJ(int index) {
        if (index >= 0) {
            this.Db.bJ(index);
        }
    }

    private void setProgress(float progress) {
        Drawable progressBarDrawable = this.Db.getDrawable(3);
        if (progressBarDrawable != null) {
            if (progress >= 0.999f) {
                if (progressBarDrawable instanceof Animatable) {
                    ((Animatable) progressBarDrawable).stop();
                }
                bJ(3);
            } else {
                if (progressBarDrawable instanceof Animatable) {
                    ((Animatable) progressBarDrawable).start();
                }
                bI(3);
            }
            progressBarDrawable.setLevel(Math.round(10000.0f * progress));
        }
    }

    public Drawable getTopLevelDrawable() {
        return this.Da;
    }

    public void reset() {
        kv();
        kw();
    }

    public void a(Drawable drawable, float progress, boolean immediate) {
        drawable = e.a(drawable, this.CZ, this.mResources);
        drawable.mutate();
        this.Dc.d(drawable);
        this.Db.jP();
        kx();
        bI(2);
        setProgress(progress);
        if (immediate) {
            this.Db.jV();
        }
        this.Db.jQ();
    }

    public void a(float progress, boolean immediate) {
        if (this.Db.getDrawable(3) != null) {
            this.Db.jP();
            setProgress(progress);
            if (immediate) {
                this.Db.jV();
            }
            this.Db.jQ();
        }
    }

    public void f(Throwable throwable) {
        this.Db.jP();
        kx();
        if (this.Db.getDrawable(5) != null) {
            bI(5);
        } else {
            bI(1);
        }
        this.Db.jQ();
    }

    public void g(Throwable throwable) {
        this.Db.jP();
        kx();
        if (this.Db.getDrawable(4) != null) {
            bI(4);
        } else {
            bI(1);
        }
        this.Db.jQ();
    }

    public void b(@Nullable Drawable drawable) {
        this.Da.b(drawable);
    }

    private com.huluxia.image.drawee.drawable.c bT(int index) {
        com.huluxia.image.drawee.drawable.c parent = this.Db.bE(index);
        if (parent.getDrawable() instanceof h) {
            parent = (h) parent.getDrawable();
        }
        if (parent.getDrawable() instanceof n) {
            return (n) parent.getDrawable();
        }
        return parent;
    }

    private void b(int index, @Nullable Drawable drawable) {
        if (drawable == null) {
            this.Db.a(index, null);
            return;
        }
        bT(index).d(e.a(drawable, this.CZ, this.mResources));
    }

    private n bU(int index) {
        com.huluxia.image.drawee.drawable.c parent = bT(index);
        if (parent instanceof n) {
            return (n) parent;
        }
        return e.a(parent, o.c.CE);
    }

    private boolean bV(int index) {
        return bT(index) instanceof n;
    }

    public void bW(int durationMs) {
        this.Db.bH(durationMs);
    }

    public int ky() {
        return this.Db.jR();
    }

    public void b(PointF focusPoint) {
        Preconditions.checkNotNull(focusPoint);
        bU(2).a(focusPoint);
    }

    public void b(o.c scaleType) {
        Preconditions.checkNotNull(scaleType);
        bU(2).a(scaleType);
    }

    @Nullable
    public o.c kz() {
        if (bV(2)) {
            return bU(2).ko();
        }
        return null;
    }

    public void a(ColorFilter colorfilter) {
        this.Dc.setColorFilter(colorfilter);
    }

    public void c(RectF outBounds) {
        this.Dc.b(outBounds);
    }

    public void i(@Nullable Drawable drawable) {
        b(1, drawable);
    }

    public void b(Drawable drawable, o.c scaleType) {
        b(1, drawable);
        bU(1).a(scaleType);
    }

    public boolean kA() {
        return this.Db.getDrawable(1) != null;
    }

    public void c(PointF focusPoint) {
        Preconditions.checkNotNull(focusPoint);
        bU(1).a(focusPoint);
    }

    public void bX(int resourceId) {
        i(this.mResources.getDrawable(resourceId));
    }

    public void a(int resourceId, o.c scaleType) {
        b(this.mResources.getDrawable(resourceId), scaleType);
    }

    public void j(@Nullable Drawable drawable) {
        b(5, drawable);
    }

    public void c(Drawable drawable, o.c scaleType) {
        b(5, drawable);
        bU(5).a(scaleType);
    }

    public void bY(int resourceId) {
        j(this.mResources.getDrawable(resourceId));
    }

    public void b(int resourceId, o.c scaleType) {
        c(this.mResources.getDrawable(resourceId), scaleType);
    }

    public void k(@Nullable Drawable drawable) {
        b(4, drawable);
    }

    public void d(Drawable drawable, o.c scaleType) {
        b(4, drawable);
        bU(4).a(scaleType);
    }

    public void bZ(int resourceId) {
        k(this.mResources.getDrawable(resourceId));
    }

    public void c(int resourceId, o.c scaleType) {
        d(this.mResources.getDrawable(resourceId), scaleType);
    }

    public void l(@Nullable Drawable drawable) {
        b(3, drawable);
    }

    public void e(Drawable drawable, o.c scaleType) {
        b(3, drawable);
        bU(3).a(scaleType);
    }

    public void ca(int resourceId) {
        l(this.mResources.getDrawable(resourceId));
    }

    public void d(int resourceId, o.c scaleType) {
        e(this.mResources.getDrawable(resourceId), scaleType);
    }

    public void m(@Nullable Drawable drawable) {
        b(0, drawable);
    }

    public void c(int index, @Nullable Drawable drawable) {
        boolean z = index >= 0 && index + 6 < this.Db.getNumberOfLayers();
        Preconditions.checkArgument(z, "The given index does not correspond to an overlay image.");
        b(index + 6, drawable);
    }

    public void n(@Nullable Drawable drawable) {
        c(0, drawable);
    }

    public void a(@Nullable RoundingParams roundingParams) {
        this.CZ = roundingParams;
        e.a(this.Da, this.CZ);
        for (int i = 0; i < this.Db.getNumberOfLayers(); i++) {
            e.a(bT(i), this.CZ, this.mResources);
        }
    }

    @Nullable
    public RoundingParams kB() {
        return this.CZ;
    }
}
