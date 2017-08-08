package com.MCWorld.image.base.imagepipeline.image;

import android.util.Pair;
import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.framework.base.utils.Supplier;
import com.MCWorld.framework.base.utils.VisibleForTesting;
import com.MCWorld.image.base.cache.common.b;
import com.MCWorld.image.base.imagepipeline.memory.PooledByteBuffer;
import com.MCWorld.image.base.imagepipeline.memory.e;
import com.MCWorld.image.core.common.references.SharedReference;
import com.MCWorld.image.core.common.references.a;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
/* compiled from: EncodedImage */
public class d implements Closeable {
    public static final int wO = -1;
    public static final int wP = -1;
    public static final int wQ = -1;
    public static final int wR = -1;
    public static final int wS = 1;
    private int mHeight;
    private int mWidth;
    private int wN;
    @Nullable
    private final a<PooledByteBuffer> wT;
    @Nullable
    private final Supplier<FileInputStream> wU;
    private com.MCWorld.image.base.imageformat.d wV;
    private int wW;
    private int wX;
    @Nullable
    private b wY;

    public d(a<PooledByteBuffer> pooledByteBufferRef) {
        this.wV = com.MCWorld.image.base.imageformat.d.vz;
        this.wN = -1;
        this.mWidth = -1;
        this.mHeight = -1;
        this.wW = 1;
        this.wX = -1;
        Preconditions.checkArgument(a.a((a) pooledByteBufferRef));
        this.wT = pooledByteBufferRef.iu();
        this.wU = null;
    }

    public d(Supplier<FileInputStream> inputStreamSupplier) {
        this.wV = com.MCWorld.image.base.imageformat.d.vz;
        this.wN = -1;
        this.mWidth = -1;
        this.mHeight = -1;
        this.wW = 1;
        this.wX = -1;
        Preconditions.checkNotNull(inputStreamSupplier);
        this.wT = null;
        this.wU = inputStreamSupplier;
    }

    public d(Supplier<FileInputStream> inputStreamSupplier, int streamSize) {
        this((Supplier) inputStreamSupplier);
        this.wX = streamSize;
    }

    public static d a(d encodedImage) {
        return encodedImage != null ? encodedImage.hR() : null;
    }

    public d hR() {
        d encodedImage;
        if (this.wU != null) {
            encodedImage = new d(this.wU, this.wX);
        } else {
            a pooledByteBufferRef = a.b(this.wT);
            if (pooledByteBufferRef == null) {
                encodedImage = null;
            } else {
                try {
                    encodedImage = new d(pooledByteBufferRef);
                } catch (Throwable th) {
                    a.c(pooledByteBufferRef);
                }
            }
            a.c(pooledByteBufferRef);
        }
        if (encodedImage != null) {
            encodedImage.b(this);
        }
        return encodedImage;
    }

    public void close() {
        a.c(this.wT);
    }

    public synchronized boolean isValid() {
        boolean z;
        z = a.a(this.wT) || this.wU != null;
        return z;
    }

    public a<PooledByteBuffer> hS() {
        return a.b(this.wT);
    }

    public InputStream getInputStream() {
        if (this.wU != null) {
            return (InputStream) this.wU.get();
        }
        a<PooledByteBuffer> pooledByteBufferRef = a.b(this.wT);
        if (pooledByteBufferRef == null) {
            return null;
        }
        try {
            InputStream eVar = new e((PooledByteBuffer) pooledByteBufferRef.get());
            return eVar;
        } finally {
            a.c(pooledByteBufferRef);
        }
    }

    public void d(com.MCWorld.image.base.imageformat.d imageFormat) {
        this.wV = imageFormat;
    }

    public void setHeight(int height) {
        this.mHeight = height;
    }

    public void setWidth(int width) {
        this.mWidth = width;
    }

    public void bo(int rotationAngle) {
        this.wN = rotationAngle;
    }

    public void bp(int sampleSize) {
        this.wW = sampleSize;
    }

    public void bq(int streamSize) {
        this.wX = streamSize;
    }

    public void j(@Nullable b encodedCacheKey) {
        this.wY = encodedCacheKey;
    }

    public com.MCWorld.image.base.imageformat.d hT() {
        return this.wV;
    }

    public int hQ() {
        return this.wN;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int hU() {
        return this.wW;
    }

    @Nullable
    public b hV() {
        return this.wY;
    }

    public boolean br(int length) {
        if (this.wV != com.MCWorld.image.base.imageformat.b.vl || this.wU != null) {
            return true;
        }
        Preconditions.checkNotNull(this.wT);
        PooledByteBuffer buf = (PooledByteBuffer) this.wT.get();
        if (buf.bs(length - 2) == (byte) -1 && buf.bs(length - 1) == (byte) -39) {
            return true;
        }
        return false;
    }

    public int getSize() {
        if (this.wT == null || this.wT.get() == null) {
            return this.wX;
        }
        return ((PooledByteBuffer) this.wT.get()).size();
    }

    public void hW() {
        Pair<Integer, Integer> dimensions;
        com.MCWorld.image.base.imageformat.d imageFormat = com.MCWorld.image.base.imageformat.e.h(getInputStream());
        this.wV = imageFormat;
        if (com.MCWorld.image.base.imageformat.b.a(imageFormat)) {
            dimensions = hX();
        } else {
            dimensions = hY();
        }
        if (imageFormat != com.MCWorld.image.base.imageformat.b.vl || this.wN != -1) {
            this.wN = 0;
        } else if (dimensions != null) {
            this.wN = com.MCWorld.image.base.imageutils.b.bv(com.MCWorld.image.base.imageutils.b.k(getInputStream()));
        }
    }

    private Pair<Integer, Integer> hX() {
        Pair<Integer, Integer> dimensions = com.MCWorld.image.base.imageutils.e.m(getInputStream());
        if (dimensions != null) {
            this.mWidth = ((Integer) dimensions.first).intValue();
            this.mHeight = ((Integer) dimensions.second).intValue();
        }
        return dimensions;
    }

    private Pair<Integer, Integer> hY() {
        InputStream inputStream = null;
        try {
            inputStream = getInputStream();
            Pair<Integer, Integer> dimensions = com.MCWorld.image.base.imageutils.a.j(inputStream);
            if (dimensions != null) {
                this.mWidth = ((Integer) dimensions.first).intValue();
                this.mHeight = ((Integer) dimensions.second).intValue();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
            return dimensions;
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                }
            }
        }
    }

    public void b(d encodedImage) {
        this.wV = encodedImage.hT();
        this.mWidth = encodedImage.getWidth();
        this.mHeight = encodedImage.getHeight();
        this.wN = encodedImage.hQ();
        this.wW = encodedImage.hU();
        this.wX = encodedImage.getSize();
        this.wY = encodedImage.hV();
    }

    public static boolean c(d encodedImage) {
        return encodedImage.wN >= 0 && encodedImage.mWidth >= 0 && encodedImage.mHeight >= 0;
    }

    public static void d(@Nullable d encodedImage) {
        if (encodedImage != null) {
            encodedImage.close();
        }
    }

    public static boolean e(@Nullable d encodedImage) {
        return encodedImage != null && encodedImage.isValid();
    }

    @VisibleForTesting
    public synchronized SharedReference<PooledByteBuffer> hZ() {
        return this.wT != null ? this.wT.hZ() : null;
    }
}
