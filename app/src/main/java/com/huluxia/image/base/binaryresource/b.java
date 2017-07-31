package com.huluxia.image.base.binaryresource;

import com.huluxia.framework.base.utils.Preconditions;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: ByteArrayBinaryResource */
public class b implements a {
    private final byte[] tx;

    public b(byte[] bytes) {
        this.tx = (byte[]) Preconditions.checkNotNull(bytes);
    }

    public long size() {
        return (long) this.tx.length;
    }

    public InputStream openStream() throws IOException {
        return new ByteArrayInputStream(this.tx);
    }

    public byte[] ga() {
        return this.tx;
    }
}
