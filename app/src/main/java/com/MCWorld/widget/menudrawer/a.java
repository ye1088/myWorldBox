package com.MCWorld.widget.menudrawer;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

/* compiled from: ColorDrawable */
class a extends Drawable {
    private a bAi;
    private final Paint mPaint;

    /* compiled from: ColorDrawable */
    static final class a extends ConstantState {
        int bAj;
        int bAk;
        int bAl;

        a(a state) {
            if (state != null) {
                this.bAj = state.bAj;
                this.bAk = state.bAk;
            }
        }

        public Drawable newDrawable() {
            return new a();
        }

        public Drawable newDrawable(Resources res) {
            return new a();
        }

        public int getChangingConfigurations() {
            return this.bAl;
        }
    }

    public a() {
        this(null);
    }

    public a(int color) {
        this(null);
        setColor(color);
    }

    private a(a state) {
        this.mPaint = new Paint();
        this.bAi = new a(state);
    }

    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.bAi.bAl;
    }

    public void draw(Canvas canvas) {
        if ((this.bAi.bAk >>> 24) != 0) {
            this.mPaint.setColor(this.bAi.bAk);
            canvas.drawRect(getBounds(), this.mPaint);
        }
    }

    public int getColor() {
        return this.bAi.bAk;
    }

    public void setColor(int color) {
        if (this.bAi.bAj != color || this.bAi.bAk != color) {
            invalidateSelf();
            a aVar = this.bAi;
            this.bAi.bAk = color;
            aVar.bAj = color;
        }
    }

    public int getAlpha() {
        return this.bAi.bAk >>> 24;
    }

    public void setAlpha(int alpha) {
        int useAlpha = ((this.bAi.bAj >>> 24) * (alpha + (alpha >> 7))) >> 8;
        int oldUseColor = this.bAi.bAk;
        this.bAi.bAk = ((this.bAi.bAj << 8) >>> 8) | (useAlpha << 24);
        if (oldUseColor != this.bAi.bAk) {
            invalidateSelf();
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public int getOpacity() {
        switch (this.bAi.bAk >>> 24) {
            case 0:
                return -2;
            case 255:
                return -1;
            default:
                return -3;
        }
    }

    public ConstantState getConstantState() {
        this.bAi.bAl = getChangingConfigurations();
        return this.bAi;
    }
}
