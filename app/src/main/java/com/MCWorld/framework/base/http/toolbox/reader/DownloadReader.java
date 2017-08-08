package com.MCWorld.framework.base.http.toolbox.reader;

import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.utils.ByteArrayPool;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public abstract class DownloadReader implements Closeable {
    final ByteArrayPool mByteArrayPool;

    public abstract <E extends Throwable, T extends Throwable> void copy(InputStream inputStream, IAdapterToStreamAndRaf iAdapterToStreamAndRaf, IReaderCallback<E, T> iReaderCallback) throws IOException, Throwable, Throwable, VolleyError;

    DownloadReader(ByteArrayPool byteArrayPool) {
        this.mByteArrayPool = byteArrayPool;
    }
}
