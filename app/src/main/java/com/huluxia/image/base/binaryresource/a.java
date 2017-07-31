package com.huluxia.image.base.binaryresource;

import java.io.IOException;
import java.io.InputStream;

/* compiled from: BinaryResource */
public interface a {
    byte[] ga() throws IOException;

    InputStream openStream() throws IOException;

    long size();
}
