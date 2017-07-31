package com.huluxia.image.core.common.references;

import com.huluxia.framework.base.utils.Closeables;
import java.io.Closeable;
import java.io.IOException;

/* compiled from: CloseableReference */
class a$1 implements c<Closeable> {
    a$1() {
    }

    public /* synthetic */ void release(Object obj) {
        c((Closeable) obj);
    }

    public void c(Closeable value) {
        try {
            Closeables.close(value, true);
        } catch (IOException e) {
        }
    }
}
