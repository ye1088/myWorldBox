package com.huluxia.image.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.framework.base.utils.VisibleForTesting;
import java.util.Arrays;

/* compiled from: FadeDrawable */
public class f extends a {
    @VisibleForTesting
    public static final int Bv = 0;
    @VisibleForTesting
    public static final int Bw = 1;
    @VisibleForTesting
    public static final int Bx = 2;
    @VisibleForTesting
    long BA;
    @VisibleForTesting
    int[] BB;
    @VisibleForTesting
    int[] BC;
    @VisibleForTesting
    boolean[] BD;
    @VisibleForTesting
    int BE;
    private final Drawable[] Be;
    @VisibleForTesting
    int By;
    @VisibleForTesting
    int Bz;
    @VisibleForTesting
    int mAlpha;

    public f(Drawable[] layers) {
        boolean z = true;
        super(layers);
        if (layers.length < 1) {
            z = false;
        }
        Preconditions.checkState(z, "At least one layer required!");
        this.Be = layers;
        this.BB = new int[layers.length];
        this.BC = new int[layers.length];
        this.mAlpha = 255;
        this.BD = new boolean[layers.length];
        this.BE = 0;
        jS();
    }

    public void invalidateSelf() {
        if (this.BE == 0) {
            super.invalidateSelf();
        }
    }

    public void jP() {
        this.BE++;
    }

    public void jQ() {
        this.BE--;
        invalidateSelf();
    }

    public void bH(int durationMs) {
        this.Bz = durationMs;
        if (this.By == 1) {
            this.By = 0;
        }
    }

    public int jR() {
        return this.Bz;
    }

    private void jS() {
        this.By = 2;
        Arrays.fill(this.BB, 0);
        this.BB[0] = 255;
        Arrays.fill(this.BC, 0);
        this.BC[0] = 255;
        Arrays.fill(this.BD, false);
        this.BD[0] = true;
    }

    public void reset() {
        jS();
        invalidateSelf();
    }

    public void bI(int index) {
        this.By = 0;
        this.BD[index] = true;
        invalidateSelf();
    }

    public void bJ(int index) {
        this.By = 0;
        this.BD[index] = false;
        invalidateSelf();
    }

    public void jT() {
        this.By = 0;
        Arrays.fill(this.BD, true);
        invalidateSelf();
    }

    public void jU() {
        this.By = 0;
        Arrays.fill(this.BD, false);
        invalidateSelf();
    }

    public void bK(int index) {
        this.By = 0;
        Arrays.fill(this.BD, false);
        this.BD[index] = true;
        invalidateSelf();
    }

    public void bL(int index) {
        this.By = 0;
        Arrays.fill(this.BD, 0, index + 1, true);
        Arrays.fill(this.BD, index + 1, this.Be.length, false);
        invalidateSelf();
    }

    public void jV() {
        this.By = 2;
        for (int i = 0; i < this.Be.length; i++) {
            this.BC[i] = this.BD[i] ? 255 : 0;
        }
        invalidateSelf();
    }

    private boolean f(float ratio) {
        boolean done = true;
        int i = 0;
        while (i < this.Be.length) {
            this.BC[i] = (int) (((float) this.BB[i]) + (((float) ((this.BD[i] ? 1 : -1) * 255)) * ratio));
            if (this.BC[i] < 0) {
                this.BC[i] = 0;
            }
            if (this.BC[i] > 255) {
                this.BC[i] = 255;
            }
            if (this.BD[i] && this.BC[i] < 255) {
                done = false;
            }
            if (!this.BD[i] && this.BC[i] > 0) {
                done = false;
            }
            i++;
        }
        return done;
    }

    public void draw(Canvas canvas) {
        int i = 2;
        boolean z = false;
        boolean done = true;
        switch (this.By) {
            case 0:
                System.arraycopy(this.BC, 0, this.BB, 0, this.Be.length);
                this.BA = jW();
                done = f(this.Bz == 0 ? 1.0f : 0.0f);
                if (!done) {
                    i = 1;
                }
                this.By = i;
                break;
            case 1:
                if (this.Bz > 0) {
                    z = true;
                }
                Preconditions.checkState(z);
                done = f(((float) (jW() - this.BA)) / ((float) this.Bz));
                if (!done) {
                    i = 1;
                }
                this.By = i;
                break;
            case 2:
                done = true;
                break;
        }
        for (int i2 = 0; i2 < this.Be.length; i2++) {
            a(canvas, this.Be[i2], (this.BC[i2] * this.mAlpha) / 255);
        }
        if (!done) {
            invalidateSelf();
        }
    }

    private void a(Canvas canvas, Drawable drawable, int alpha) {
        if (drawable != null && alpha > 0) {
            this.BE++;
            drawable.mutate().setAlpha(alpha);
            this.BE--;
            drawable.draw(canvas);
        }
    }

    public void setAlpha(int alpha) {
        if (this.mAlpha != alpha) {
            this.mAlpha = alpha;
            invalidateSelf();
        }
    }

    public int getAlpha() {
        return this.mAlpha;
    }

    protected long jW() {
        return SystemClock.uptimeMillis();
    }

    @VisibleForTesting
    public int jX() {
        return this.By;
    }

    public boolean bM(int index) {
        return this.BD[index];
    }
}
