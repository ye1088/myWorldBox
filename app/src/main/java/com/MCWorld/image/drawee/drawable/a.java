package com.MCWorld.image.drawee.drawable;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import com.MCWorld.framework.base.utils.Preconditions;
import javax.annotation.Nullable;

/* compiled from: ArrayDrawable */
public class a extends Drawable implements Callback, q, r {
    private r Bc;
    private final d Bd = new d();
    private final Drawable[] Be;
    private final c[] Bf;
    private boolean Bg = false;
    private boolean Bh = false;
    private boolean Bi = false;
    private final Rect mTmpRect = new Rect();

    public a(Drawable[] layers) {
        Preconditions.checkNotNull(layers);
        this.Be = layers;
        for (Drawable a : this.Be) {
            e.a(a, this, this);
        }
        this.Bf = new c[this.Be.length];
    }

    public int getNumberOfLayers() {
        return this.Be.length;
    }

    @Nullable
    public Drawable getDrawable(int index) {
        boolean z;
        boolean z2 = true;
        if (index >= 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        if (index >= this.Be.length) {
            z2 = false;
        }
        Preconditions.checkArgument(z2);
        return this.Be[index];
    }

    @Nullable
    public Drawable a(int index, @Nullable Drawable drawable) {
        boolean z;
        boolean z2 = true;
        if (index >= 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        if (index >= this.Be.length) {
            z2 = false;
        }
        Preconditions.checkArgument(z2);
        Drawable oldDrawable = this.Be[index];
        if (drawable != oldDrawable) {
            if (drawable != null && this.Bi) {
                drawable.mutate();
            }
            e.a(this.Be[index], null, null);
            e.a(drawable, null, null);
            e.a(drawable, this.Bd);
            e.b(drawable, this);
            e.a(drawable, this, this);
            this.Bh = false;
            this.Be[index] = drawable;
            invalidateSelf();
        }
        return oldDrawable;
    }

    public int getIntrinsicWidth() {
        int width = -1;
        for (Drawable drawable : this.Be) {
            if (drawable != null) {
                width = Math.max(width, drawable.getIntrinsicWidth());
            }
        }
        return width > 0 ? width : -1;
    }

    public int getIntrinsicHeight() {
        int height = -1;
        for (Drawable drawable : this.Be) {
            if (drawable != null) {
                height = Math.max(height, drawable.getIntrinsicHeight());
            }
        }
        return height > 0 ? height : -1;
    }

    protected void onBoundsChange(Rect bounds) {
        for (Drawable drawable : this.Be) {
            if (drawable != null) {
                drawable.setBounds(bounds);
            }
        }
    }

    public boolean isStateful() {
        if (!this.Bh) {
            this.Bg = false;
            for (Drawable drawable : this.Be) {
                int i;
                boolean z = this.Bg;
                if (drawable == null || !drawable.isStateful()) {
                    i = 0;
                } else {
                    i = 1;
                }
                this.Bg = i | z;
            }
            this.Bh = true;
        }
        return this.Bg;
    }

    protected boolean onStateChange(int[] state) {
        boolean stateChanged = false;
        for (Drawable drawable : this.Be) {
            if (drawable != null && drawable.setState(state)) {
                stateChanged = true;
            }
        }
        return stateChanged;
    }

    protected boolean onLevelChange(int level) {
        boolean levelChanged = false;
        for (Drawable drawable : this.Be) {
            if (drawable != null && drawable.setLevel(level)) {
                levelChanged = true;
            }
        }
        return levelChanged;
    }

    public void draw(Canvas canvas) {
        for (Drawable drawable : this.Be) {
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }
    }

    public boolean getPadding(Rect padding) {
        padding.left = 0;
        padding.top = 0;
        padding.right = 0;
        padding.bottom = 0;
        Rect rect = this.mTmpRect;
        for (Drawable drawable : this.Be) {
            if (drawable != null) {
                drawable.getPadding(rect);
                padding.left = Math.max(padding.left, rect.left);
                padding.top = Math.max(padding.top, rect.top);
                padding.right = Math.max(padding.right, rect.right);
                padding.bottom = Math.max(padding.bottom, rect.bottom);
            }
        }
        return true;
    }

    public Drawable mutate() {
        for (Drawable drawable : this.Be) {
            if (drawable != null) {
                drawable.mutate();
            }
        }
        this.Bi = true;
        return this;
    }

    public int getOpacity() {
        if (this.Be.length == 0) {
            return -2;
        }
        int opacity = -1;
        for (int i = 1; i < this.Be.length; i++) {
            Drawable drawable = this.Be[i];
            if (drawable != null) {
                opacity = Drawable.resolveOpacity(opacity, drawable.getOpacity());
            }
        }
        return opacity;
    }

    public void setAlpha(int alpha) {
        this.Bd.setAlpha(alpha);
        for (Drawable drawable : this.Be) {
            if (drawable != null) {
                drawable.setAlpha(alpha);
            }
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.Bd.setColorFilter(colorFilter);
        for (Drawable drawable : this.Be) {
            if (drawable != null) {
                drawable.setColorFilter(colorFilter);
            }
        }
    }

    public void setDither(boolean dither) {
        this.Bd.setDither(dither);
        for (Drawable drawable : this.Be) {
            if (drawable != null) {
                drawable.setDither(dither);
            }
        }
    }

    public void setFilterBitmap(boolean filterBitmap) {
        this.Bd.setFilterBitmap(filterBitmap);
        for (Drawable drawable : this.Be) {
            if (drawable != null) {
                drawable.setFilterBitmap(filterBitmap);
            }
        }
    }

    public boolean setVisible(boolean visible, boolean restart) {
        boolean changed = super.setVisible(visible, restart);
        for (Drawable drawable : this.Be) {
            if (drawable != null) {
                drawable.setVisible(visible, restart);
            }
        }
        return changed;
    }

    public c bE(int index) {
        boolean z = true;
        Preconditions.checkArgument(index >= 0);
        if (index >= this.Bf.length) {
            z = false;
        }
        Preconditions.checkArgument(z);
        if (this.Bf[index] == null) {
            this.Bf[index] = bF(index);
        }
        return this.Bf[index];
    }

    private c bF(int index) {
        return new 1(this, index);
    }

    public void invalidateDrawable(Drawable who) {
        invalidateSelf();
    }

    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        scheduleSelf(what, when);
    }

    public void unscheduleDrawable(Drawable who, Runnable what) {
        unscheduleSelf(what);
    }

    public void a(r transformCallback) {
        this.Bc = transformCallback;
    }

    public void a(Matrix transform) {
        if (this.Bc != null) {
            this.Bc.a(transform);
        } else {
            transform.reset();
        }
    }

    public void a(RectF bounds) {
        if (this.Bc != null) {
            this.Bc.a(bounds);
        } else {
            bounds.set(getBounds());
        }
    }

    @TargetApi(21)
    public void setHotspot(float x, float y) {
        for (Drawable drawable : this.Be) {
            if (drawable != null) {
                drawable.setHotspot(x, y);
            }
        }
    }
}
