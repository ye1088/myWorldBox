package com.huluxia.image.base.imagepipeline.memory;

import com.huluxia.framework.base.utils.Throwables;
import java.io.IOException;
import java.io.OutputStream;

/* compiled from: PooledByteBufferOutputStream */
public abstract class f extends OutputStream {
    public abstract PooledByteBuffer ih();

    public abstract int size();

    public void close() {
        try {
            super.close();
        } catch (IOException ioe) {
            Throwables.propagate(ioe);
        }
    }
}
