package com.MCWorld.image.base.imagepipeline.nativecode;

import com.MCWorld.image.base.imageformat.d;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* compiled from: WebpTranscoder */
public interface a {
    void a(InputStream inputStream, OutputStream outputStream, int i) throws IOException;

    void b(InputStream inputStream, OutputStream outputStream) throws IOException;

    boolean e(d dVar);
}
