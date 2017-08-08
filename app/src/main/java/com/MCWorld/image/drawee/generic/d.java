package com.MCWorld.image.drawee.generic;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import com.MCWorld.framework.base.utils.VisibleForTesting;
import com.MCWorld.image.drawee.drawable.g;
import com.MCWorld.image.drawee.drawable.s;
import com.MCWorld.image.drawee.drawable.t;
import javax.annotation.Nullable;

/* compiled from: RootDrawable */
public class d extends g implements s {
    @VisibleForTesting
    @Nullable
    Drawable Aa = null;
    @Nullable
    private t Dw;

    public d(Drawable drawable) {
        super(drawable);
    }

    public int getIntrinsicWidth() {
        return -1;
    }

    public int getIntrinsicHeight() {
        return -1;
    }

    public void a(@Nullable t visibilityCallback) {
        this.Dw = visibilityCallback;
    }

    public boolean setVisible(boolean visible, boolean restart) {
        if (this.Dw != null) {
            this.Dw.ac(visible);
        }
        return super.setVisible(visible, restart);
    }

    @SuppressLint({"WrongCall"})
    public void draw(Canvas canvas) {
        if (isVisible()) {
            if (this.Dw != null) {
                this.Dw.onDraw();
            }
            super.draw(canvas);
            if (this.Aa != null) {
                this.Aa.setBounds(getBounds());
                this.Aa.draw(canvas);
            }
        }
    }

    public void b(@Nullable Drawable controllerOverlay) {
        this.Aa = controllerOverlay;
        invalidateSelf();
    }
}
