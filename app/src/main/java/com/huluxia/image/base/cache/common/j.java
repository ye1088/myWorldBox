package com.huluxia.image.base.cache.common;

import com.huluxia.framework.base.utils.ByteStreams;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* compiled from: WriterCallbacks */
public class j {
    public static i d(final InputStream is) {
        return new i() {
            public void write(OutputStream os) throws IOException {
                ByteStreams.copy(is, os);
            }
        };
    }

    public static i h(final byte[] data) {
        return new i() {
            public void write(OutputStream os) throws IOException {
                os.write(data);
            }
        };
    }
}
