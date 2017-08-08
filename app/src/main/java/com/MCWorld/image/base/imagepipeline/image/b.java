package com.MCWorld.image.base.imagepipeline.image;

import com.MCWorld.framework.base.log.HLog;
import java.io.Closeable;

/* compiled from: CloseableImage */
public abstract class b implements e, Closeable {
    private static final String TAG = "CloseableImage";

    public abstract void close();

    public abstract int hi();

    public abstract boolean isClosed();

    public g hN() {
        return f.wZ;
    }

    public boolean isStateful() {
        return false;
    }

    protected void finalize() throws Throwable {
        if (!isClosed()) {
            HLog.warn(TAG, "finalize: %s %x still open.", new Object[]{getClass().getSimpleName(), Integer.valueOf(System.identityHashCode(this))});
            try {
                close();
            } finally {
                super.finalize();
            }
        }
    }
}
