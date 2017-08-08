package com.MCWorld.image.base.imagepipeline.image;

import android.graphics.Bitmap;
import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.image.core.common.references.a;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
/* compiled from: CloseableStaticBitmap */
public class c extends a {
    private volatile Bitmap mBitmap;
    @GuardedBy("this")
    private a<Bitmap> wL;
    private final g wM;
    private final int wN;

    public c(Bitmap bitmap, com.MCWorld.image.core.common.references.c<Bitmap> resourceReleaser, g qualityInfo, int rotationAngle) {
        this.mBitmap = (Bitmap) Preconditions.checkNotNull(bitmap);
        this.wL = a.a(this.mBitmap, (com.MCWorld.image.core.common.references.c) Preconditions.checkNotNull(resourceReleaser));
        this.wM = qualityInfo;
        this.wN = rotationAngle;
    }

    public c(a<Bitmap> bitmapReference, g qualityInfo, int rotationAngle) {
        this.wL = (a) Preconditions.checkNotNull(bitmapReference.iv());
        this.mBitmap = (Bitmap) this.wL.get();
        this.wM = qualityInfo;
        this.wN = rotationAngle;
    }

    public void close() {
        a<Bitmap> reference = hO();
        if (reference != null) {
            reference.close();
        }
    }

    private synchronized a<Bitmap> hO() {
        a<Bitmap> reference;
        reference = this.wL;
        this.wL = null;
        this.mBitmap = null;
        return reference;
    }

    public synchronized a<Bitmap> hP() {
        Preconditions.checkNotNull(this.wL, "Cannot convert a_isRightVersion closed static bitmap");
        return hO();
    }

    public synchronized boolean isClosed() {
        return this.wL == null;
    }

    public Bitmap hM() {
        return this.mBitmap;
    }

    public int hi() {
        return com.MCWorld.image.base.imageutils.a.d(this.mBitmap);
    }

    public int getWidth() {
        Bitmap bitmap = this.mBitmap;
        return bitmap == null ? 0 : bitmap.getWidth();
    }

    public int getHeight() {
        Bitmap bitmap = this.mBitmap;
        return bitmap == null ? 0 : bitmap.getHeight();
    }

    public int hQ() {
        return this.wN;
    }

    public g hN() {
        return this.wM;
    }
}
