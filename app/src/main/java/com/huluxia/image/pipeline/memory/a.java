package com.huluxia.image.pipeline.memory;

import android.graphics.Bitmap;
import android.os.Build.VERSION;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.framework.base.utils.Throwables;
import com.huluxia.image.base.imagepipeline.common.TooManyBitmapsException;
import com.huluxia.image.core.common.references.c;
import com.huluxia.image.pipeline.nativecode.Bitmaps;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: BitmapCounter */
public class a {
    @GuardedBy("this")
    private int Ht;
    private final int Hv;
    private final int Hw;
    private final c<Bitmap> Hx;
    private final c Hy;
    @GuardedBy("this")
    private long uH;

    public a(int maxCount, int maxSize, @Nullable c tracker) {
        boolean z = true;
        Preconditions.checkArgument(maxCount > 0);
        if (maxSize <= 0) {
            z = false;
        }
        Preconditions.checkArgument(z);
        this.Hv = maxCount;
        this.Hw = maxSize;
        this.Hx = new c<Bitmap>(this) {
            final /* synthetic */ a Hz;

            {
                this.Hz = this$0;
            }

            public /* synthetic */ void release(Object obj) {
                c((Bitmap) obj);
            }

            public void c(Bitmap value) {
                try {
                    this.Hz.g(value);
                } finally {
                    value.recycle();
                }
            }
        };
        this.Hy = tracker;
    }

    public synchronized boolean f(Bitmap bitmap) {
        boolean z;
        int bitmapSize = com.huluxia.image.base.imageutils.a.d(bitmap);
        if (this.Ht >= this.Hv || this.uH + ((long) bitmapSize) > ((long) this.Hw)) {
            z = false;
        } else {
            this.Ht++;
            this.uH += (long) bitmapSize;
            if (this.Hy != null) {
                this.Hy.a(this);
            }
            z = true;
        }
        return z;
    }

    public synchronized void g(Bitmap bitmap) {
        boolean z = true;
        synchronized (this) {
            int bitmapSize = com.huluxia.image.base.imageutils.a.d(bitmap);
            Preconditions.checkArgument(this.Ht > 0, "No bitmaps registered.");
            if (((long) bitmapSize) > this.uH) {
                z = false;
            }
            Preconditions.checkArgument(z, "Bitmap size bigger than the total registered size: %d, %d", new Object[]{Integer.valueOf(bitmapSize), Long.valueOf(this.uH)});
            this.uH -= (long) bitmapSize;
            this.Ht--;
            if (this.Hy != null) {
                this.Hy.b(this);
            }
        }
    }

    public synchronized int getCount() {
        return this.Ht;
    }

    public synchronized long getSize() {
        return this.uH;
    }

    public synchronized int nP() {
        return this.Hv;
    }

    public synchronized int getMaxSize() {
        return this.Hw;
    }

    public c<Bitmap> nQ() {
        return this.Hx;
    }

    public List<com.huluxia.image.core.common.references.a<Bitmap>> p(List<Bitmap> bitmaps) {
        Bitmap bitmap;
        int countedBitmaps = 0;
        while (countedBitmaps < bitmaps.size()) {
            try {
                bitmap = (Bitmap) bitmaps.get(countedBitmaps);
                if (VERSION.SDK_INT < 21) {
                    Bitmaps.k(bitmap);
                }
                if (f(bitmap)) {
                    countedBitmaps++;
                } else {
                    throw new TooManyBitmapsException();
                }
            } catch (Exception exception) {
                if (bitmaps != null) {
                    for (Bitmap bitmap2 : bitmaps) {
                        int countedBitmaps2 = countedBitmaps - 1;
                        if (countedBitmaps > 0) {
                            g(bitmap2);
                        }
                        bitmap2.recycle();
                        countedBitmaps = countedBitmaps2;
                    }
                }
                throw Throwables.propagate(exception);
            }
        }
        List<com.huluxia.image.core.common.references.a<Bitmap>> ret = new ArrayList(bitmaps.size());
        for (Bitmap bitmap22 : bitmaps) {
            ret.add(com.huluxia.image.core.common.references.a.a(bitmap22, this.Hx));
        }
        return ret;
    }
}
